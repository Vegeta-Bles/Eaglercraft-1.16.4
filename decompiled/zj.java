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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zj implements Runnable {
   private static final Logger a = LogManager.getLogger();
   private final zg b;
   private final long c;

   public zj(zg var1) {
      this.b = _snowman;
      this.c = _snowman.bf();
   }

   @Override
   public void run() {
      while (this.b.v()) {
         long _snowman = this.b.ay();
         long _snowmanx = x.b();
         long _snowmanxx = _snowmanx - _snowman;
         if (_snowmanxx > this.c) {
            a.fatal(
               "A single server tick took {} seconds (should be max {})",
               String.format(Locale.ROOT, "%.2f", (float)_snowmanxx / 1000.0F),
               String.format(Locale.ROOT, "%.2f", 0.05F)
            );
            a.fatal("Considering it to be crashed, server will forcibly shutdown.");
            ThreadMXBean _snowmanxxx = ManagementFactory.getThreadMXBean();
            ThreadInfo[] _snowmanxxxx = _snowmanxxx.dumpAllThreads(true, true);
            StringBuilder _snowmanxxxxx = new StringBuilder();
            Error _snowmanxxxxxx = new Error("Watchdog");

            for (ThreadInfo _snowmanxxxxxxx : _snowmanxxxx) {
               if (_snowmanxxxxxxx.getThreadId() == this.b.aw().getId()) {
                  _snowmanxxxxxx.setStackTrace(_snowmanxxxxxxx.getStackTrace());
               }

               _snowmanxxxxx.append(_snowmanxxxxxxx);
               _snowmanxxxxx.append("\n");
            }

            l _snowmanxxxxxxx = new l("Watching Server", _snowmanxxxxxx);
            this.b.b(_snowmanxxxxxxx);
            m _snowmanxxxxxxxx = _snowmanxxxxxxx.a("Thread Dump");
            _snowmanxxxxxxxx.a("Threads", _snowmanxxxxx);
            m _snowmanxxxxxxxxx = _snowmanxxxxxxx.a("Performance stats");
            _snowmanxxxxxxxxx.a("Random tick rate", () -> this.b.aX().q().a(brt.m).toString());
            _snowmanxxxxxxxxx.a("Level stats", () -> Streams.stream(this.b.G()).map(var0 -> var0.Y() + ": " + var0.F()).collect(Collectors.joining(",\n")));
            vm.a("Crash report:\n" + _snowmanxxxxxxx.e());
            File _snowmanxxxxxxxxxx = new File(
               new File(this.b.B(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt"
            );
            if (_snowmanxxxxxxx.a(_snowmanxxxxxxxxxx)) {
               a.error("This crash report has been saved to: {}", _snowmanxxxxxxxxxx.getAbsolutePath());
            } else {
               a.error("We were unable to save this crash report to disk.");
            }

            this.a();
         }

         try {
            Thread.sleep(_snowman + this.c - _snowmanx);
         } catch (InterruptedException var15) {
         }
      }
   }

   private void a() {
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
