import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cfx implements bsx.a {
   private static final Logger d = LogManager.getLogger();
   private static final int e = (int)Math.round(Math.log(16.0) / Math.log(2.0)) - 2;
   private static final int f = (int)Math.round(Math.log(256.0) / Math.log(2.0)) - 2;
   public static final int a = 1 << e + e + f;
   public static final int b = (1 << e) - 1;
   public static final int c = (1 << f) - 1;
   private final gg<bsv> g;
   private final bsv[] h;

   public cfx(gg<bsv> var1, bsv[] var2) {
      this.g = _snowman;
      this.h = _snowman;
   }

   private cfx(gg<bsv> var1) {
      this(_snowman, new bsv[a]);
   }

   public cfx(gg<bsv> var1, int[] var2) {
      this(_snowman);

      for (int _snowman = 0; _snowman < this.h.length; _snowman++) {
         int _snowmanx = _snowman[_snowman];
         bsv _snowmanxx = _snowman.a(_snowmanx);
         if (_snowmanxx == null) {
            d.warn("Received invalid biome id: " + _snowmanx);
            this.h[_snowman] = _snowman.a(0);
         } else {
            this.h[_snowman] = _snowmanxx;
         }
      }
   }

   public cfx(gg<bsv> var1, brd var2, bsy var3) {
      this(_snowman);
      int _snowman = _snowman.d() >> 2;
      int _snowmanx = _snowman.e() >> 2;

      for (int _snowmanxx = 0; _snowmanxx < this.h.length; _snowmanxx++) {
         int _snowmanxxx = _snowmanxx & b;
         int _snowmanxxxx = _snowmanxx >> e + e & c;
         int _snowmanxxxxx = _snowmanxx >> e & b;
         this.h[_snowmanxx] = _snowman.b(_snowman + _snowmanxxx, _snowmanxxxx, _snowmanx + _snowmanxxxxx);
      }
   }

   public cfx(gg<bsv> var1, brd var2, bsy var3, @Nullable int[] var4) {
      this(_snowman);
      int _snowman = _snowman.d() >> 2;
      int _snowmanx = _snowman.e() >> 2;
      if (_snowman != null) {
         for (int _snowmanxx = 0; _snowmanxx < _snowman.length; _snowmanxx++) {
            this.h[_snowmanxx] = _snowman.a(_snowman[_snowmanxx]);
            if (this.h[_snowmanxx] == null) {
               int _snowmanxxx = _snowmanxx & b;
               int _snowmanxxxx = _snowmanxx >> e + e & c;
               int _snowmanxxxxx = _snowmanxx >> e & b;
               this.h[_snowmanxx] = _snowman.b(_snowman + _snowmanxxx, _snowmanxxxx, _snowmanx + _snowmanxxxxx);
            }
         }
      } else {
         for (int _snowmanxxx = 0; _snowmanxxx < this.h.length; _snowmanxxx++) {
            int _snowmanxxxx = _snowmanxxx & b;
            int _snowmanxxxxx = _snowmanxxx >> e + e & c;
            int _snowmanxxxxxx = _snowmanxxx >> e & b;
            this.h[_snowmanxxx] = _snowman.b(_snowman + _snowmanxxxx, _snowmanxxxxx, _snowmanx + _snowmanxxxxxx);
         }
      }
   }

   public int[] a() {
      int[] _snowman = new int[this.h.length];

      for (int _snowmanx = 0; _snowmanx < this.h.length; _snowmanx++) {
         _snowman[_snowmanx] = this.g.a(this.h[_snowmanx]);
      }

      return _snowman;
   }

   @Override
   public bsv b(int var1, int var2, int var3) {
      int _snowman = _snowman & b;
      int _snowmanx = afm.a(_snowman, 0, c);
      int _snowmanxx = _snowman & b;
      return this.h[_snowmanx << e + e | _snowmanxx << e | _snowman];
   }
}
