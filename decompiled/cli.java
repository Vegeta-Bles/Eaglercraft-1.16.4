import com.mojang.serialization.Codec;
import java.util.Random;

public class cli extends cjl<cmh> {
   private static final gc[] a = gc.values();

   public cli(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      if (!_snowman.w(_snowman)) {
         return false;
      } else {
         ceh _snowman = _snowman.d_(_snowman.b());
         if (!_snowman.a(bup.cL) && !_snowman.a(bup.iK)) {
            return false;
         } else {
            this.a(_snowman, _snowman, _snowman);
            this.b(_snowman, _snowman, _snowman);
            return true;
         }
      }
   }

   private void a(bry var1, Random var2, fx var3) {
      _snowman.a(_snowman, bup.iK.n(), 2);
      fx.a _snowman = new fx.a();
      fx.a _snowmanx = new fx.a();

      for (int _snowmanxx = 0; _snowmanxx < 200; _snowmanxx++) {
         _snowman.a(_snowman, _snowman.nextInt(6) - _snowman.nextInt(6), _snowman.nextInt(2) - _snowman.nextInt(5), _snowman.nextInt(6) - _snowman.nextInt(6));
         if (_snowman.w(_snowman)) {
            int _snowmanxxx = 0;

            for (gc _snowmanxxxx : a) {
               ceh _snowmanxxxxx = _snowman.d_(_snowmanx.a(_snowman, _snowmanxxxx));
               if (_snowmanxxxxx.a(bup.cL) || _snowmanxxxxx.a(bup.iK)) {
                  _snowmanxxx++;
               }

               if (_snowmanxxx > 1) {
                  break;
               }
            }

            if (_snowmanxxx == 1) {
               _snowman.a(_snowman, bup.iK.n(), 2);
            }
         }
      }
   }

   private void b(bry var1, Random var2, fx var3) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = 0; _snowmanx < 100; _snowmanx++) {
         _snowman.a(_snowman, _snowman.nextInt(8) - _snowman.nextInt(8), _snowman.nextInt(2) - _snowman.nextInt(7), _snowman.nextInt(8) - _snowman.nextInt(8));
         if (_snowman.w(_snowman)) {
            ceh _snowmanxx = _snowman.d_(_snowman.b());
            if (_snowmanxx.a(bup.cL) || _snowmanxx.a(bup.iK)) {
               int _snowmanxxx = afm.a(_snowman, 1, 8);
               if (_snowman.nextInt(6) == 0) {
                  _snowmanxxx *= 2;
               }

               if (_snowman.nextInt(5) == 0) {
                  _snowmanxxx = 1;
               }

               int _snowmanxxxx = 17;
               int _snowmanxxxxx = 25;
               a(_snowman, _snowman, _snowman, _snowmanxxx, 17, 25);
            }
         }
      }
   }

   public static void a(bry var0, Random var1, fx.a var2, int var3, int var4, int var5) {
      for (int _snowman = 0; _snowman <= _snowman; _snowman++) {
         if (_snowman.w(_snowman)) {
            if (_snowman == _snowman || !_snowman.w(_snowman.c())) {
               _snowman.a(_snowman, bup.mx.n().a(bxh.d, Integer.valueOf(afm.a(_snowman, _snowman, _snowman))), 2);
               break;
            }

            _snowman.a(_snowman, bup.my.n(), 2);
         }

         _snowman.c(gc.a);
      }
   }
}
