import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cij extends cjl<cmb> {
   public cij(Codec<cmb> var1) {
      super(_snowman);
   }

   protected void a(bry var1, Random var2, fx var3, cmb var4, int var5, fx.a var6) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         _snowman.g(_snowman).c(gc.b, _snowman);
         if (!_snowman.d_(_snowman).i(_snowman, _snowman)) {
            this.a(_snowman, _snowman, _snowman.c.a(_snowman, _snowman));
         }
      }
   }

   protected int a(Random var1) {
      int _snowman = _snowman.nextInt(3) + 4;
      if (_snowman.nextInt(12) == 0) {
         _snowman *= 2;
      }

      return _snowman;
   }

   protected boolean a(bry var1, fx var2, int var3, fx.a var4, cmb var5) {
      int _snowman = _snowman.v();
      if (_snowman >= 1 && _snowman + _snowman + 1 < 256) {
         buo _snowmanx = _snowman.d_(_snowman.c()).b();
         if (!b(_snowmanx) && !_snowmanx.a(aed.aD)) {
            return false;
         } else {
            for (int _snowmanxx = 0; _snowmanxx <= _snowman; _snowmanxx++) {
               int _snowmanxxx = this.a(-1, -1, _snowman.d, _snowmanxx);

               for (int _snowmanxxxx = -_snowmanxxx; _snowmanxxxx <= _snowmanxxx; _snowmanxxxx++) {
                  for (int _snowmanxxxxx = -_snowmanxxx; _snowmanxxxxx <= _snowmanxxx; _snowmanxxxxx++) {
                     ceh _snowmanxxxxxx = _snowman.d_(_snowman.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxx));
                     if (!_snowmanxxxxxx.g() && !_snowmanxxxxxx.a(aed.I)) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmb var5) {
      int _snowman = this.a(_snowman);
      fx.a _snowmanx = new fx.a();
      if (!this.a(_snowman, _snowman, _snowman, _snowmanx, _snowman)) {
         return false;
      } else {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowman);
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx);
         return true;
      }
   }

   protected abstract int a(int var1, int var2, int var3, int var4);

   protected abstract void a(bry var1, Random var2, fx var3, int var4, fx.a var5, cmb var6);
}
