import com.mojang.serialization.Codec;
import java.util.Random;

public class cle extends cjl<cmh> {
   public cle(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      return a(_snowman, _snowman, _snowman, 8, 4, 8);
   }

   public static boolean a(bry var0, Random var1, fx var2, int var3, int var4, int var5) {
      if (a(_snowman, _snowman)) {
         return false;
      } else {
         b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         return true;
      }
   }

   private static void b(bry var0, Random var1, fx var2, int var3, int var4, int var5) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = 0; _snowmanx < _snowman * _snowman; _snowmanx++) {
         _snowman.g(_snowman).e(afm.a(_snowman, -_snowman, _snowman), afm.a(_snowman, -_snowman, _snowman), afm.a(_snowman, -_snowman, _snowman));
         if (a(_snowman, _snowman) && !a(_snowman, (fx)_snowman)) {
            int _snowmanxx = afm.a(_snowman, 1, _snowman);
            if (_snowman.nextInt(6) == 0) {
               _snowmanxx *= 2;
            }

            if (_snowman.nextInt(5) == 0) {
               _snowmanxx = 1;
            }

            int _snowmanxxx = 17;
            int _snowmanxxxx = 25;
            a(_snowman, _snowman, _snowman, _snowmanxx, 17, 25);
         }
      }
   }

   private static boolean a(bry var0, fx.a var1) {
      do {
         _snowman.e(0, -1, 0);
         if (brx.m(_snowman)) {
            return false;
         }
      } while (_snowman.d_(_snowman).g());

      _snowman.e(0, 1, 0);
      return true;
   }

   public static void a(bry var0, Random var1, fx.a var2, int var3, int var4, int var5) {
      for (int _snowman = 1; _snowman <= _snowman; _snowman++) {
         if (_snowman.w(_snowman)) {
            if (_snowman == _snowman || !_snowman.w(_snowman.b())) {
               _snowman.a(_snowman, bup.mz.n().a(bxh.d, Integer.valueOf(afm.a(_snowman, _snowman, _snowman))), 2);
               break;
            }

            _snowman.a(_snowman, bup.mA.n(), 2);
         }

         _snowman.c(gc.b);
      }
   }

   private static boolean a(bry var0, fx var1) {
      if (!_snowman.w(_snowman)) {
         return true;
      } else {
         ceh _snowman = _snowman.d_(_snowman.c());
         return !_snowman.a(bup.cL) && !_snowman.a(bup.ml) && !_snowman.a(bup.mn);
      }
   }
}
