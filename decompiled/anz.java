import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class anz {
   private static final Logger a = LogManager.getLogger();
   private final LongSupplier b;
   private final long c;
   private int d;
   private final File e;
   private anu f;

   public anw a() {
      this.f = new anp(this.b, () -> this.d, false);
      this.d++;
      return this.f;
   }

   public void b() {
      if (this.f != ant.a) {
         anv _snowman = this.f.d();
         this.f = ant.a;
         if (_snowman.g() >= this.c) {
            File _snowmanx = new File(this.e, "tick-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
            _snowman.a(_snowmanx);
            a.info("Recorded long tick -- wrote info to: {}", _snowmanx.getAbsolutePath());
         }
      }
   }

   @Nullable
   public static anz a(String var0) {
      return null;
   }

   public static anw a(anw var0, @Nullable anz var1) {
      return _snowman != null ? anw.a(_snowman.a(), _snowman) : _snowman;
   }
}
