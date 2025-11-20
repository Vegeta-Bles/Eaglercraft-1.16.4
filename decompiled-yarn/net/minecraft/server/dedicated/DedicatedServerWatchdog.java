package net.minecraft.server.dedicated;

import com.google.common.collect.Streams;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.minecraft.Bootstrap;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServerWatchdog implements Runnable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftDedicatedServer server;
   private final long maxTickTime;

   public DedicatedServerWatchdog(MinecraftDedicatedServer server) {
      this.server = server;
      this.maxTickTime = server.getMaxTickTime();
   }

   @Override
   public void run() {
      while (this.server.isRunning()) {
         long _snowman = this.server.getServerStartTime();
         long _snowmanx = Util.getMeasuringTimeMs();
         long _snowmanxx = _snowmanx - _snowman;
         if (_snowmanxx > this.maxTickTime) {
            LOGGER.fatal(
               "A single server tick took {} seconds (should be max {})",
               String.format(Locale.ROOT, "%.2f", (float)_snowmanxx / 1000.0F),
               String.format(Locale.ROOT, "%.2f", 0.05F)
            );
            LOGGER.fatal("Considering it to be crashed, server will forcibly shutdown.");
            ThreadMXBean _snowmanxxx = ManagementFactory.getThreadMXBean();
            ThreadInfo[] _snowmanxxxx = _snowmanxxx.dumpAllThreads(true, true);
            StringBuilder _snowmanxxxxx = new StringBuilder();
            Error _snowmanxxxxxx = new Error("Watchdog");

            for (ThreadInfo _snowmanxxxxxxx : _snowmanxxxx) {
               if (_snowmanxxxxxxx.getThreadId() == this.server.getThread().getId()) {
                  _snowmanxxxxxx.setStackTrace(_snowmanxxxxxxx.getStackTrace());
               }

               _snowmanxxxxx.append(_snowmanxxxxxxx);
               _snowmanxxxxx.append("\n");
            }

            CrashReport _snowmanxxxxxxx = new CrashReport("Watching Server", _snowmanxxxxxx);
            this.server.populateCrashReport(_snowmanxxxxxxx);
            CrashReportSection _snowmanxxxxxxxx = _snowmanxxxxxxx.addElement("Thread Dump");
            _snowmanxxxxxxxx.add("Threads", _snowmanxxxxx);
            CrashReportSection _snowmanxxxxxxxxx = _snowmanxxxxxxx.addElement("Performance stats");
            _snowmanxxxxxxxxx.add("Random tick rate", () -> this.server.getSaveProperties().getGameRules().get(GameRules.RANDOM_TICK_SPEED).toString());
            _snowmanxxxxxxxxx.add(
               "Level stats",
               () -> Streams.stream(this.server.getWorlds())
                     .map(_snowmanxxxxxxxxxx -> _snowmanxxxxxxxxxx.getRegistryKey() + ": " + _snowmanxxxxxxxxxx.method_31268())
                     .collect(Collectors.joining(",\n"))
            );
            Bootstrap.println("Crash report:\n" + _snowmanxxxxxxx.asString());
            File _snowmanxxxxxxxxxx = new File(
               new File(this.server.getRunDirectory(), "crash-reports"),
               "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt"
            );
            if (_snowmanxxxxxxx.writeToFile(_snowmanxxxxxxxxxx)) {
               LOGGER.error("This crash report has been saved to: {}", _snowmanxxxxxxxxxx.getAbsolutePath());
            } else {
               LOGGER.error("We were unable to save this crash report to disk.");
            }

            this.shutdown();
         }

         try {
            Thread.sleep(_snowman + this.maxTickTime - _snowmanx);
         } catch (InterruptedException var15) {
         }
      }
   }

   private void shutdown() {
      try {
         Timer _snowman = new Timer();
         _snowman.schedule(new TimerTask() {
            @Override
            public void run() {
               Runtime.getRuntime().halt(1);
            }
         }, 10000L);
         System.exit(1);
      } catch (Throwable var2) {
         Runtime.getRuntime().halt(1);
      }
   }
}
