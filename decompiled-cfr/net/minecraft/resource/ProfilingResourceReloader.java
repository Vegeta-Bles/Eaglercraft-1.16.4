/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.resource;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.ProfilerSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfilingResourceReloader
extends ResourceReloader<Summary> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Stopwatch reloadTimer = Stopwatch.createUnstarted();

    public ProfilingResourceReloader(ResourceManager manager, List<ResourceReloadListener> listeners, Executor prepareExecutor2, Executor applyExecutor2, CompletableFuture<Unit> completableFuture) {
        super(prepareExecutor2, applyExecutor2, manager, listeners, (synchronizer, resourceManager, resourceReloadListener, prepareExecutor, applyExecutor) -> {
            AtomicLong atomicLong = new AtomicLong();
            _snowman = new AtomicLong();
            ProfilerSystem _snowman2 = new ProfilerSystem(Util.nanoTimeSupplier, () -> 0, false);
            ProfilerSystem _snowman3 = new ProfilerSystem(Util.nanoTimeSupplier, () -> 0, false);
            CompletableFuture<Void> _snowman4 = resourceReloadListener.reload(synchronizer, resourceManager, _snowman2, _snowman3, runnable -> prepareExecutor.execute(() -> {
                long l = Util.getMeasuringTimeNano();
                runnable.run();
                atomicLong.addAndGet(Util.getMeasuringTimeNano() - l);
            }), runnable -> applyExecutor.execute(() -> {
                long l = Util.getMeasuringTimeNano();
                runnable.run();
                _snowman.addAndGet(Util.getMeasuringTimeNano() - l);
            }));
            return _snowman4.thenApplyAsync(void_ -> new Summary(resourceReloadListener.getName(), _snowman2.getResult(), _snowman3.getResult(), atomicLong, _snowman), applyExecutor2);
        }, completableFuture);
        this.reloadTimer.start();
        this.applyStageFuture.thenAcceptAsync(this::finish, applyExecutor2);
    }

    private void finish(List<Summary> summaries) {
        this.reloadTimer.stop();
        int n = 0;
        LOGGER.info("Resource reload finished after " + this.reloadTimer.elapsed(TimeUnit.MILLISECONDS) + " ms");
        for (Summary summary : summaries) {
            ProfileResult profileResult = summary.prepareProfile;
            _snowman = summary.applyProfile;
            int _snowman2 = (int)((double)summary.prepareTimeMs.get() / 1000000.0);
            int _snowman3 = (int)((double)summary.applyTimeMs.get() / 1000000.0);
            int _snowman4 = _snowman2 + _snowman3;
            String _snowman5 = summary.name;
            LOGGER.info(_snowman5 + " took approximately " + _snowman4 + " ms (" + _snowman2 + " ms preparing, " + _snowman3 + " ms applying)");
            n += _snowman3;
        }
        LOGGER.info("Total blocking time: " + n + " ms");
    }

    public static class Summary {
        private final String name;
        private final ProfileResult prepareProfile;
        private final ProfileResult applyProfile;
        private final AtomicLong prepareTimeMs;
        private final AtomicLong applyTimeMs;

        private Summary(String name, ProfileResult prepareProfile, ProfileResult applyProfile, AtomicLong prepareTimeMs, AtomicLong applyTimeMs) {
            this.name = name;
            this.prepareProfile = prepareProfile;
            this.applyProfile = applyProfile;
            this.prepareTimeMs = prepareTimeMs;
            this.applyTimeMs = applyTimeMs;
        }
    }
}

