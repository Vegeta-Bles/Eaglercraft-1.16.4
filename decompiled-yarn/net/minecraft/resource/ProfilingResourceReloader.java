package net.minecraft.resource;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.ProfilerSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfilingResourceReloader extends ResourceReloader<ProfilingResourceReloader.Summary> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Stopwatch reloadTimer = Stopwatch.createUnstarted();

   public ProfilingResourceReloader(
      ResourceManager manager, List<ResourceReloadListener> listeners, Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> _snowman
   ) {
      super(
         prepareExecutor,
         applyExecutor,
         manager,
         listeners,
         (_snowmanxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx, prepareExecutorx, applyExecutorx) -> {
            AtomicLong _snowmanxxxx = new AtomicLong();
            AtomicLong _snowmanxxxxx = new AtomicLong();
            ProfilerSystem _snowmanxxxxxx = new ProfilerSystem(Util.nanoTimeSupplier, () -> 0, false);
            ProfilerSystem _snowmanxxxxxxx = new ProfilerSystem(Util.nanoTimeSupplier, () -> 0, false);
            CompletableFuture<Void> _snowmanxxxxxxxx = _snowmanxxxxxxx.reload(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxx -> prepareExecutorx.execute(() -> {
                  long _snowmanxx = Util.getMeasuringTimeNano();
                  _snowmanxxxxxxxxxxx.run();
                  _snowmanxxxxxx.addAndGet(Util.getMeasuringTimeNano() - _snowmanxx);
               }), _snowmanxxxxxxxxxxx -> applyExecutorx.execute(() -> {
                  long _snowmanxx = Util.getMeasuringTimeNano();
                  _snowmanxxxxxxxxxxx.run();
                  _snowmanxxxxx.addAndGet(Util.getMeasuringTimeNano() - _snowmanxx);
               }));
            return _snowmanxxxxxxxx.thenApplyAsync(
               _snowmanxxxxxxxxxxx -> new ProfilingResourceReloader.Summary(_snowmanxxxxxxx.getName(), _snowmanxxxx.getResult(), _snowmanxxx.getResult(), _snowmanxxxxxx, _snowmanxxxxx), applyExecutor
            );
         },
         _snowman
      );
      this.reloadTimer.start();
      this.applyStageFuture.thenAcceptAsync(this::finish, applyExecutor);
   }

   private void finish(List<ProfilingResourceReloader.Summary> summaries) {
      this.reloadTimer.stop();
      int _snowman = 0;
      LOGGER.info("Resource reload finished after " + this.reloadTimer.elapsed(TimeUnit.MILLISECONDS) + " ms");

      for (ProfilingResourceReloader.Summary _snowmanx : summaries) {
         ProfileResult _snowmanxx = _snowmanx.prepareProfile;
         ProfileResult _snowmanxxx = _snowmanx.applyProfile;
         int _snowmanxxxx = (int)((double)_snowmanx.prepareTimeMs.get() / 1000000.0);
         int _snowmanxxxxx = (int)((double)_snowmanx.applyTimeMs.get() / 1000000.0);
         int _snowmanxxxxxx = _snowmanxxxx + _snowmanxxxxx;
         String _snowmanxxxxxxx = _snowmanx.name;
         LOGGER.info(_snowmanxxxxxxx + " took approximately " + _snowmanxxxxxx + " ms (" + _snowmanxxxx + " ms preparing, " + _snowmanxxxxx + " ms applying)");
         _snowman += _snowmanxxxxx;
      }

      LOGGER.info("Total blocking time: " + _snowman + " ms");
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
