package net.minecraft.world.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.thread.TaskExecutor;
import net.minecraft.util.thread.TaskQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StorageIoWorker implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final AtomicBoolean closed = new AtomicBoolean();
   private final TaskExecutor<TaskQueue.PrioritizedTask> field_24468;
   private final RegionBasedStorage storage;
   private final Map<ChunkPos, StorageIoWorker.Result> results = Maps.newLinkedHashMap();

   protected StorageIoWorker(File _snowman, boolean _snowman, String _snowman) {
      this.storage = new RegionBasedStorage(_snowman, _snowman);
      this.field_24468 = new TaskExecutor<>(new TaskQueue.Prioritized(StorageIoWorker.Priority.values().length), Util.getIoWorkerExecutor(), "IOWorker-" + _snowman);
   }

   public CompletableFuture<Void> setResult(ChunkPos pos, CompoundTag nbt) {
      return this.run(() -> {
         StorageIoWorker.Result _snowmanxx = this.results.computeIfAbsent(pos, _snowmanxxx -> new StorageIoWorker.Result(nbt));
         _snowmanxx.nbt = nbt;
         return Either.left(_snowmanxx.future);
      }).thenCompose(Function.identity());
   }

   @Nullable
   public CompoundTag getNbt(ChunkPos pos) throws IOException {
      CompletableFuture<CompoundTag> _snowman = this.run(() -> {
         StorageIoWorker.Result _snowmanx = this.results.get(pos);
         if (_snowmanx != null) {
            return Either.left(_snowmanx.nbt);
         } else {
            try {
               CompoundTag _snowmanx = this.storage.getTagAt(pos);
               return Either.left(_snowmanx);
            } catch (Exception var4x) {
               LOGGER.warn("Failed to read chunk {}", pos, var4x);
               return Either.right(var4x);
            }
         }
      });

      try {
         return _snowman.join();
      } catch (CompletionException var4) {
         if (var4.getCause() instanceof IOException) {
            throw (IOException)var4.getCause();
         } else {
            throw var4;
         }
      }
   }

   public CompletableFuture<Void> completeAll() {
      CompletableFuture<Void> _snowman = this.run(
            () -> Either.left(CompletableFuture.allOf(this.results.values().stream().map(_snowmanx -> _snowmanx.future).toArray(CompletableFuture[]::new)))
         )
         .thenCompose(Function.identity());
      return _snowman.thenCompose(_snowmanx -> this.run(() -> {
            try {
               this.storage.method_26982();
               return Either.left(null);
            } catch (Exception var2) {
               LOGGER.warn("Failed to synchronized chunks", var2);
               return Either.right(var2);
            }
         }));
   }

   private <T> CompletableFuture<T> run(Supplier<Either<T, Exception>> _snowman) {
      return this.field_24468.method_27918(_snowmanx -> new TaskQueue.PrioritizedTask(StorageIoWorker.Priority.HIGH.ordinal(), () -> {
            if (!this.closed.get()) {
               _snowmanx.send(_snowman.get());
            }

            this.method_27945();
         }));
   }

   private void writeResult() {
      Iterator<Entry<ChunkPos, StorageIoWorker.Result>> _snowman = this.results.entrySet().iterator();
      if (_snowman.hasNext()) {
         Entry<ChunkPos, StorageIoWorker.Result> _snowmanx = _snowman.next();
         _snowman.remove();
         this.write(_snowmanx.getKey(), _snowmanx.getValue());
         this.method_27945();
      }
   }

   private void method_27945() {
      this.field_24468.send(new TaskQueue.PrioritizedTask(StorageIoWorker.Priority.LOW.ordinal(), this::writeResult));
   }

   private void write(ChunkPos pos, StorageIoWorker.Result _snowman) {
      try {
         this.storage.write(pos, _snowman.nbt);
         _snowman.future.complete(null);
      } catch (Exception var4) {
         LOGGER.error("Failed to store chunk {}", pos, var4);
         _snowman.future.completeExceptionally(var4);
      }
   }

   @Override
   public void close() throws IOException {
      if (this.closed.compareAndSet(false, true)) {
         CompletableFuture<Unit> _snowman = this.field_24468
            .ask(_snowmanx -> new TaskQueue.PrioritizedTask(StorageIoWorker.Priority.HIGH.ordinal(), () -> _snowman.send(Unit.INSTANCE)));

         try {
            _snowman.join();
         } catch (CompletionException var4) {
            if (var4.getCause() instanceof IOException) {
               throw (IOException)var4.getCause();
            }

            throw var4;
         }

         this.field_24468.close();
         this.results.forEach(this::write);
         this.results.clear();

         try {
            this.storage.close();
         } catch (Exception var3) {
            LOGGER.error("Failed to close storage", var3);
         }
      }
   }

   static enum Priority {
      HIGH,
      LOW;

      private Priority() {
      }
   }

   static class Result {
      private CompoundTag nbt;
      private final CompletableFuture<Void> future = new CompletableFuture<>();

      public Result(CompoundTag _snowman) {
         this.nbt = _snowman;
      }
   }
}
