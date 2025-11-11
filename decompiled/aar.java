import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aar implements aap {
   private static final Logger a = LogManager.getLogger();
   private final int b;
   private int c;
   private long d;
   private long e = Long.MAX_VALUE;

   public aar(int var1) {
      int _snowman = _snowman * 2 + 1;
      this.b = _snowman * _snowman;
   }

   @Override
   public void a(brd var1) {
      this.e = x.b();
      this.d = this.e;
   }

   @Override
   public void a(brd var1, @Nullable cga var2) {
      if (_snowman == cga.m) {
         this.c++;
      }

      int _snowman = this.c();
      if (x.b() > this.e) {
         this.e += 500L;
         a.info(new of("menu.preparingSpawn", afm.a(_snowman, 0, 100)).getString());
      }
   }

   @Override
   public void b() {
      a.info("Time elapsed: {} ms", x.b() - this.d);
      this.e = Long.MAX_VALUE;
   }

   public int c() {
      return afm.d((float)this.c * 100.0F / (float)this.b);
   }
}
