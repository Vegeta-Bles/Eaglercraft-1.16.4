/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.DummyProfiler;

public class ResourceReloader<S>
implements ResourceReloadMonitor {
    protected final ResourceManager manager;
    protected final CompletableFuture<Unit> prepareStageFuture = new CompletableFuture();
    protected final CompletableFuture<List<S>> applyStageFuture;
    private final Set<ResourceReloadListener> waitingListeners;
    private final int listenerCount;
    private int applyingCount;
    private int appliedCount;
    private final AtomicInteger preparingCount = new AtomicInteger();
    private final AtomicInteger preparedCount = new AtomicInteger();

    public static ResourceReloader<Void> create(ResourceManager manager, List<ResourceReloadListener> listeners, Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage) {
        return new ResourceReloader<Void>(prepareExecutor, applyExecutor, manager, listeners, (synchronizer, resourceManager, resourceReloadListener, executor2, executor3) -> resourceReloadListener.reload(synchronizer, resourceManager, DummyProfiler.INSTANCE, DummyProfiler.INSTANCE, prepareExecutor, executor3), initialStage);
    }

    protected ResourceReloader(Executor prepareExecutor, Executor applyExecutor, ResourceManager manager, List<ResourceReloadListener> listeners, Factory<S> creator, CompletableFuture<Unit> initialStage) {
        this.manager = manager;
        this.listenerCount = listeners.size();
        this.preparingCount.incrementAndGet();
        initialStage.thenRun(this.preparedCount::incrementAndGet);
        ArrayList arrayList = Lists.newArrayList();
        CompletableFuture<Unit> _snowman2 = initialStage;
        this.waitingListeners = Sets.newHashSet(listeners);
        for (ResourceReloadListener resourceReloadListener : listeners) {
            CompletableFuture<Unit> completableFuture = _snowman2;
            CompletableFuture<S> _snowman3 = creator.create(new ResourceReloadListener.Synchronizer(this, applyExecutor, resourceReloadListener, completableFuture){
                final /* synthetic */ Executor field_18050;
                final /* synthetic */ ResourceReloadListener field_18051;
                final /* synthetic */ CompletableFuture field_18052;
                final /* synthetic */ ResourceReloader field_18053;
                {
                    this.field_18053 = resourceReloader;
                    this.field_18050 = executor;
                    this.field_18051 = resourceReloadListener;
                    this.field_18052 = completableFuture;
                }

                public <T> CompletableFuture<T> whenPrepared(T preparedObject) {
                    this.field_18050.execute(() -> {
                        ResourceReloader.method_18370(this.field_18053).remove(this.field_18051);
                        if (ResourceReloader.method_18370(this.field_18053).isEmpty()) {
                            this.field_18053.prepareStageFuture.complete(Unit.INSTANCE);
                        }
                    });
                    return this.field_18053.prepareStageFuture.thenCombine((CompletionStage)this.field_18052, (unit, object2) -> preparedObject);
                }
            }, manager, resourceReloadListener, runnable -> {
                this.preparingCount.incrementAndGet();
                prepareExecutor.execute(() -> {
                    runnable.run();
                    this.preparedCount.incrementAndGet();
                });
            }, runnable -> {
                ++this.applyingCount;
                applyExecutor.execute(() -> {
                    runnable.run();
                    ++this.appliedCount;
                });
            });
            arrayList.add(_snowman3);
            _snowman2 = _snowman3;
        }
        this.applyStageFuture = Util.combine(arrayList);
    }

    @Override
    public CompletableFuture<Unit> whenComplete() {
        return this.applyStageFuture.thenApply(list -> Unit.INSTANCE);
    }

    @Override
    public float getProgress() {
        int n = this.listenerCount - this.waitingListeners.size();
        float _snowman2 = this.preparedCount.get() * 2 + this.appliedCount * 2 + n * 1;
        float _snowman3 = this.preparingCount.get() * 2 + this.applyingCount * 2 + this.listenerCount * 1;
        return _snowman2 / _snowman3;
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

    static /* synthetic */ Set method_18370(ResourceReloader resourceReloader) {
        return resourceReloader.waitingListeners;
    }

    public static interface Factory<S> {
        public CompletableFuture<S> create(ResourceReloadListener.Synchronizer var1, ResourceManager var2, ResourceReloadListener var3, Executor var4, Executor var5);
    }
}

