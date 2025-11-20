/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.thread.MessageListener;
import net.minecraft.util.thread.TaskExecutor;
import net.minecraft.util.thread.TaskQueue;
import net.minecraft.world.storage.RegionBasedStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StorageIoWorker
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AtomicBoolean closed = new AtomicBoolean();
    private final TaskExecutor<TaskQueue.PrioritizedTask> field_24468;
    private final RegionBasedStorage storage;
    private final Map<ChunkPos, Result> results = Maps.newLinkedHashMap();

    protected StorageIoWorker(File file, boolean bl, String string) {
        this.storage = new RegionBasedStorage(file, bl);
        this.field_24468 = new TaskExecutor<TaskQueue.PrioritizedTask>(new TaskQueue.Prioritized(Priority.values().length), Util.getIoWorkerExecutor(), "IOWorker-" + string);
    }

    public CompletableFuture<Void> setResult(ChunkPos pos, CompoundTag nbt) {
        return this.run(() -> {
            Result result = this.results.computeIfAbsent(pos, chunkPos -> new Result(nbt));
            result.nbt = nbt;
            return Either.left((Object)result.future);
        }).thenCompose(Function.identity());
    }

    @Nullable
    public CompoundTag getNbt(ChunkPos pos) throws IOException {
        CompletableFuture completableFuture = this.run(() -> {
            Result result = this.results.get(pos);
            if (result != null) {
                return Either.left((Object)result.nbt);
            }
            try {
                CompoundTag compoundTag = this.storage.getTagAt(pos);
                return Either.left((Object)compoundTag);
            }
            catch (Exception exception) {
                LOGGER.warn("Failed to read chunk {}", (Object)pos, (Object)exception);
                return Either.right((Object)exception);
            }
        });
        try {
            return (CompoundTag)completableFuture.join();
        }
        catch (CompletionException _snowman2) {
            if (_snowman2.getCause() instanceof IOException) {
                throw (IOException)_snowman2.getCause();
            }
            throw _snowman2;
        }
    }

    public CompletableFuture<Void> completeAll() {
        CompletionStage completionStage = this.run(() -> Either.left(CompletableFuture.allOf((CompletableFuture[])this.results.values().stream().map(result -> ((Result)result).future).toArray(CompletableFuture[]::new)))).thenCompose(Function.identity());
        return ((CompletableFuture)completionStage).thenCompose(void_ -> this.run(() -> {
            try {
                this.storage.method_26982();
                return Either.left(null);
            }
            catch (Exception exception) {
                LOGGER.warn("Failed to synchronized chunks", (Throwable)exception);
                return Either.right((Object)exception);
            }
        }));
    }

    private <T> CompletableFuture<T> run(Supplier<Either<T, Exception>> supplier) {
        return this.field_24468.method_27918(messageListener -> new TaskQueue.PrioritizedTask(Priority.HIGH.ordinal(), () -> this.method_27939(messageListener, (Supplier)supplier)));
    }

    private void writeResult() {
        Iterator<Map.Entry<ChunkPos, Result>> iterator = this.results.entrySet().iterator();
        if (!iterator.hasNext()) {
            return;
        }
        Map.Entry<ChunkPos, Result> _snowman2 = iterator.next();
        iterator.remove();
        this.write(_snowman2.getKey(), _snowman2.getValue());
        this.method_27945();
    }

    private void method_27945() {
        this.field_24468.send(new TaskQueue.PrioritizedTask(Priority.LOW.ordinal(), this::writeResult));
    }

    private void write(ChunkPos pos, Result result) {
        try {
            this.storage.write(pos, result.nbt);
            result.future.complete(null);
        }
        catch (Exception exception) {
            LOGGER.error("Failed to store chunk {}", (Object)pos, (Object)exception);
            result.future.completeExceptionally(exception);
        }
    }

    @Override
    public void close() throws IOException {
        if (!this.closed.compareAndSet(false, true)) {
            return;
        }
        CompletableFuture completableFuture = this.field_24468.ask(messageListener -> new TaskQueue.PrioritizedTask(Priority.HIGH.ordinal(), () -> messageListener.send(Unit.INSTANCE)));
        try {
            completableFuture.join();
        }
        catch (CompletionException _snowman2) {
            if (_snowman2.getCause() instanceof IOException) {
                throw (IOException)_snowman2.getCause();
            }
            throw _snowman2;
        }
        this.field_24468.close();
        this.results.forEach(this::write);
        this.results.clear();
        try {
            this.storage.close();
        }
        catch (Exception _snowman3) {
            LOGGER.error("Failed to close storage", (Throwable)_snowman3);
        }
    }

    private /* synthetic */ void method_27939(MessageListener messageListener, Supplier supplier) {
        if (!this.closed.get()) {
            messageListener.send(supplier.get());
        }
        this.method_27945();
    }

    static class Result {
        private CompoundTag nbt;
        private final CompletableFuture<Void> future = new CompletableFuture();

        public Result(CompoundTag compoundTag) {
            this.nbt = compoundTag;
        }
    }

    static enum Priority {
        HIGH,
        LOW;

    }
}

