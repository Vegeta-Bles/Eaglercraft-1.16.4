import javax.annotation.Nullable;
import net.minecraft.world.level.ColorResolver;

public class ecv implements bra {
   protected final int a;
   protected final int b;
   protected final fx c;
   protected final int d;
   protected final int e;
   protected final int f;
   protected final cgh[][] g;
   protected final ceh[] h;
   protected final cux[] i;
   protected final brx j;

   @Nullable
   public static ecv a(brx var0, fx var1, fx var2, int var3) {
      int _snowman = _snowman.u() - _snowman >> 4;
      int _snowmanx = _snowman.w() - _snowman >> 4;
      int _snowmanxx = _snowman.u() + _snowman >> 4;
      int _snowmanxxx = _snowman.w() + _snowman >> 4;
      cgh[][] _snowmanxxxx = new cgh[_snowmanxx - _snowman + 1][_snowmanxxx - _snowmanx + 1];

      for (int _snowmanxxxxx = _snowman; _snowmanxxxxx <= _snowmanxx; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = _snowmanx; _snowmanxxxxxx <= _snowmanxxx; _snowmanxxxxxx++) {
            _snowmanxxxx[_snowmanxxxxx - _snowman][_snowmanxxxxxx - _snowmanx] = _snowman.d(_snowmanxxxxx, _snowmanxxxxxx);
         }
      }

      if (a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxxx)) {
         return null;
      } else {
         int _snowmanxxxxx = 1;
         fx _snowmanxxxxxx = _snowman.b(-1, -1, -1);
         fx _snowmanxxxxxxx = _snowman.b(1, 1, 1);
         return new ecv(_snowman, _snowman, _snowmanx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
      }
   }

   public static boolean a(fx var0, fx var1, int var2, int var3, cgh[][] var4) {
      for (int _snowman = _snowman.u() >> 4; _snowman <= _snowman.u() >> 4; _snowman++) {
         for (int _snowmanx = _snowman.w() >> 4; _snowmanx <= _snowman.w() >> 4; _snowmanx++) {
            cgh _snowmanxx = _snowman[_snowman - _snowman][_snowmanx - _snowman];
            if (!_snowmanxx.a(_snowman.v(), _snowman.v())) {
               return false;
            }
         }
      }

      return true;
   }

   public ecv(brx var1, int var2, int var3, cgh[][] var4, fx var5, fx var6) {
      this.j = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.g = _snowman;
      this.c = _snowman;
      this.d = _snowman.u() - _snowman.u() + 1;
      this.e = _snowman.v() - _snowman.v() + 1;
      this.f = _snowman.w() - _snowman.w() + 1;
      this.h = new ceh[this.d * this.e * this.f];
      this.i = new cux[this.d * this.e * this.f];

      for (fx _snowman : fx.a(_snowman, _snowman)) {
         int _snowmanx = (_snowman.u() >> 4) - _snowman;
         int _snowmanxx = (_snowman.w() >> 4) - _snowman;
         cgh _snowmanxxx = _snowman[_snowmanx][_snowmanxx];
         int _snowmanxxxx = this.a(_snowman);
         this.h[_snowmanxxxx] = _snowmanxxx.d_(_snowman);
         this.i[_snowmanxxxx] = _snowmanxxx.b(_snowman);
      }
   }

   protected final int a(fx var1) {
      return this.a(_snowman.u(), _snowman.v(), _snowman.w());
   }

   protected int a(int var1, int var2, int var3) {
      int _snowman = _snowman - this.c.u();
      int _snowmanx = _snowman - this.c.v();
      int _snowmanxx = _snowman - this.c.w();
      return _snowmanxx * this.d * this.e + _snowmanx * this.d + _snowman;
   }

   @Override
   public ceh d_(fx var1) {
      return this.h[this.a(_snowman)];
   }

   @Override
   public cux b(fx var1) {
      return this.i[this.a(_snowman)];
   }

   @Override
   public float a(gc var1, boolean var2) {
      return this.j.a(_snowman, _snowman);
   }

   @Override
   public cuo e() {
      return this.j.e();
   }

   @Nullable
   @Override
   public ccj c(fx var1) {
      return this.a(_snowman, cgh.a.a);
   }

   @Nullable
   public ccj a(fx var1, cgh.a var2) {
      int _snowman = (_snowman.u() >> 4) - this.a;
      int _snowmanx = (_snowman.w() >> 4) - this.b;
      return this.g[_snowman][_snowmanx].a(_snowman, _snowman);
   }

   @Override
   public int a(fx var1, ColorResolver var2) {
      return this.j.a(_snowman, _snowman);
   }
}
