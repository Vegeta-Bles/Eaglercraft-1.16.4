import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class hl {
   private static final Logger a = LogManager.getLogger();
   private final Collection<Path> b;
   private final Path c;
   private final List<hm> d = Lists.newArrayList();

   public hl(Path var1, Collection<Path> var2) {
      this.c = _snowman;
      this.b = _snowman;
   }

   public Collection<Path> a() {
      return this.b;
   }

   public Path b() {
      return this.c;
   }

   public void c() throws IOException {
      hn _snowman = new hn(this.c, "cache");
      _snowman.c(this.b().resolve("version.json"));
      Stopwatch _snowmanx = Stopwatch.createStarted();
      Stopwatch _snowmanxx = Stopwatch.createUnstarted();

      for (hm _snowmanxxx : this.d) {
         a.info("Starting provider: {}", _snowmanxxx.a());
         _snowmanxx.start();
         _snowmanxxx.a(_snowman);
         _snowmanxx.stop();
         a.info("{} finished after {} ms", _snowmanxxx.a(), _snowmanxx.elapsed(TimeUnit.MILLISECONDS));
         _snowmanxx.reset();
      }

      a.info("All providers took: {} ms", _snowmanx.elapsed(TimeUnit.MILLISECONDS));
      _snowman.a();
   }

   public void a(hm var1) {
      this.d.add(_snowman);
   }

   static {
      vm.a();
   }
}
