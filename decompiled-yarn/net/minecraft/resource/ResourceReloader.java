package net.minecraft.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.DummyProfiler;

public class ResourceReloader<S> implements ResourceReloadMonitor {
   protected final ResourceManager manager;
   protected final CompletableFuture<Unit> prepareStageFuture = new CompletableFuture<>();
   protected final CompletableFuture<List<S>> applyStageFuture;
   private final Set<ResourceReloadListener> waitingListeners;
   private final int listenerCount;
   private int applyingCount;
   private int appliedCount;
   private final AtomicInteger preparingCount = new AtomicInteger();
   private final AtomicInteger preparedCount = new AtomicInteger();

   public static ResourceReloader<Void> create(
      ResourceManager manager, List<ResourceReloadListener> listeners, Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage
   ) {
      return new ResourceReloader<>(
         prepareExecutor,
         applyExecutor,
         manager,
         listeners,
         (_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx) -> _snowmanxxx.reload(_snowmanx, _snowmanxx, DummyProfiler.INSTANCE, DummyProfiler.INSTANCE, prepareExecutor, _snowmanxxxxx),
         initialStage
      );
   }

   protected ResourceReloader(
      Executor prepareExecutor,
      Executor applyExecutor,
      ResourceManager manager,
      List<ResourceReloadListener> listeners,
      ResourceReloader.Factory<S> creator,
      CompletableFuture<Unit> initialStage
   ) {
      this.manager = manager;
      this.listenerCount = listeners.size();
      this.preparingCount.incrementAndGet();
      initialStage.thenRun(this.preparedCount::incrementAndGet);
      List<CompletableFuture<S>> _snowman = Lists.newArrayList();
      CompletableFuture<?> _snowmanx = initialStage;
      this.waitingListeners = Sets.newHashSet(listeners);

      for (final ResourceReloadListener _snowmanxx : listeners) {
         final CompletableFuture<?> _snowmanxxx = _snowmanx;
         CompletableFuture<S> _snowmanxxxx = creator.create(new ResourceReloadListener.Synchronizer() {
            @Override
            public <T> CompletableFuture<T> whenPrepared(T preparedObject) {
               applyExecutor.execute(() -> {
                  ResourceReloader.this.waitingListeners.remove(_snowman);
                  if (ResourceReloader.this.waitingListeners.isEmpty()) {
                     ResourceReloader.this.prepareStageFuture.complete(Unit.INSTANCE);
                  }
               });
               return ResourceReloader.this.prepareStageFuture.thenCombine((CompletionStage<? extends T>)_snowman, (_snowmanx, _snowmanxx) -> preparedObject);
            }
         }, manager, _snowmanxx, _snowmanxxxxx -> {
            this.preparingCount.incrementAndGet();
            prepareExecutor.execute(() -> {
               _snowmanx.run();
               this.preparedCount.incrementAndGet();
            });
         }, _snowmanxxxxx -> {
            this.applyingCount++;
            applyExecutor.execute(() -> {
               _snowmanx.run();
               this.appliedCount++;
            });
         });
         _snowman.add(_snowmanxxxx);
         _snowmanx = _snowmanxxxx;
      }

      this.applyStageFuture = Util.combine(_snowman);
   }

   @Override
   public CompletableFuture<Unit> whenComplete() {
      return this.applyStageFuture.thenApply(_snowman -> Unit.INSTANCE);
   }

   @Override
   public float getProgress() {
      int _snowman = this.listenerCount - this.waitingListeners.size();
      float _snowmanx = (float)(this.preparedCount.get() * 2 + this.appliedCount * 2 + _snowman * 1);
      float _snowmanxx = (float)(this.preparingCount.get() * 2 + this.applyingCount * 2 + this.listenerCount * 1);
      return _snowmanx / _snowmanxx;
   }

   @Override
   public boolean isPrepareStageComplete() {
      return this.prepareStageFuture.isDone();
   }

   @Override
   public boolean isApplyStageComplete() {
      return this.applyStageFuture.isDone();
   }

   @Override
   public void throwExceptions() {
      if (this.applyStageFuture.isCompletedExceptionally()) {
         this.applyStageFuture.join();
      }
   }

   public interface Factory<S> {
      CompletableFuture<S> create(
         ResourceReloadListener.Synchronizer helper, ResourceManager manager, ResourceReloadListener listener, Executor prepareExecutor, Executor applyExecutor
      );
   }
}
