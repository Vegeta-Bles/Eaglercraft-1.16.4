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

   protected StorageIoWorker(File file, boolean bl, String string) {
      this.storage = new RegionBasedStorage(file, bl);
      this.field_24468 = new TaskExecutor<>(
         new TaskQueue.Prioritized(StorageIoWorker.Priority.values().length), Util.getIoWorkerExecutor(), "IOWorker-" + string
      );
   }

   public CompletableFuture<Void> setResult(ChunkPos pos, CompoundTag nbt) {
      return this.run(() -> {
         StorageIoWorker.Result lv = this.results.computeIfAbsent(pos, arg2 -> new StorageIoWorker.Result(nbt));
         lv.nbt = nbt;
         return Either.left(lv.future);
      }).thenCompose(Function.identity());
   }

   @Nullable
   public CompoundTag getNbt(ChunkPos pos) throws IOException {
      CompletableFuture<CompoundTag> completableFuture = this.run(() -> {
         StorageIoWorker.Result lv = this.results.get(pos);
         if (lv != null) {
            return Either.left(lv.nbt);
         } else {
            try {
               CompoundTag lv2 = this.storage.getTagAt(pos);
               return Either.left(lv2);
            } catch (Exception var4x) {
               LOGGER.warn("Failed to read chunk {}", pos, var4x);
               return Either.right(var4x);
            }
         }
      });

      try {
         return completableFuture.join();
      } catch (CompletionException var4) {
         if (var4.getCause() instanceof IOException) {
            throw (IOException)var4.getCause();
         } else {
            throw var4;
         }
      }
   }

   public CompletableFuture<Void> completeAll() {
      CompletableFuture<Void> completableFuture = this.run(
            () -> Either.left(CompletableFuture.allOf(this.results.values().stream().map(arg -> arg.future).toArray(CompletableFuture[]::new)))
         )
         .thenCompose(Function.identity());
      return completableFuture.thenCompose(void_ -> this.run(() -> {
            try {
               this.storage.method_26982();
               return Either.left(null);
            } catch (Exception var2) {
               LOGGER.warn("Failed to synchronized chunks", var2);
               return Either.right(var2);
            }
         }));
   }

   private <T> CompletableFuture<T> run(Supplier<Either<T, Exception>> supplier) {
      return this.field_24468.method_27918(arg -> new TaskQueue.PrioritizedTask(StorageIoWorker.Priority.HIGH.ordinal(), () -> {
            if (!this.closed.get()) {
               arg.send(supplier.get());
            }

            this.method_27945();
         }));
   }

   private void writeResult() {
      Iterator<Entry<ChunkPos, StorageIoWorker.Result>> iterator = this.results.entrySet().iterator();
      if (iterator.hasNext()) {
         Entry<ChunkPos, StorageIoWorker.Result> entry = iterator.next();
         iterator.remove();
         this.write(entry.getKey(), entry.getValue());
         this.method_27945();
      }
   }

   private void method_27945() {
      this.field_24468.send(new TaskQueue.PrioritizedTask(StorageIoWorker.Priority.LOW.ordinal(), this::writeResult));
   }

   private void write(ChunkPos pos, StorageIoWorker.Result arg2) {
      try {
         this.storage.write(pos, arg2.nbt);
         arg2.future.complete(null);
      } catch (Exception var4) {
         LOGGER.error("Failed to store chunk {}", pos, var4);
         arg2.future.completeExceptionally(var4);
      }
   }

   @Override
   public void close() throws IOException {
      if (this.closed.compareAndSet(false, true)) {
         CompletableFuture<Unit> completableFuture = this.field_24468
            .ask(arg -> new TaskQueue.PrioritizedTask(StorageIoWorker.Priority.HIGH.ordinal(), () -> arg.send(Unit.INSTANCE)));

         try {
            completableFuture.join();
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

      public Result(CompoundTag arg) {
         this.nbt = arg;
      }
   }
}
