import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;

public class azj {
   @Nullable
   public static dcn a(aqu var0, int var1, int var2) {
      return a(_snowman, _snowman, _snowman, 0, null, true, (float) (Math.PI / 2), _snowman::f, false, 0, 0, true);
   }

   @Nullable
   public static dcn a(aqu var0, int var1, int var2, int var3, @Nullable dcn var4, double var5) {
      return a(_snowman, _snowman, _snowman, _snowman, _snowman, true, _snowman, _snowman::f, true, 0, 0, false);
   }

   @Nullable
   public static dcn b(aqu var0, int var1, int var2) {
      return a(_snowman, _snowman, _snowman, _snowman::f);
   }

   @Nullable
   public static dcn a(aqu var0, int var1, int var2, ToDoubleFunction<fx> var3) {
      return a(_snowman, _snowman, _snowman, 0, null, false, 0.0, _snowman, true, 0, 0, true);
   }

   @Nullable
   public static dcn a(aqu var0, int var1, int var2, dcn var3, float var4, int var5, int var6) {
      return a(_snowman, _snowman, _snowman, 0, _snowman, false, (double)_snowman, _snowman::f, true, _snowman, _snowman, true);
   }

   @Nullable
   public static dcn a(aqu var0, int var1, int var2, dcn var3) {
      dcn _snowman = _snowman.a(_snowman.cD(), _snowman.cE(), _snowman.cH());
      return a(_snowman, _snowman, _snowman, 0, _snowman, false, (float) (Math.PI / 2), _snowman::f, true, 0, 0, true);
   }

   @Nullable
   public static dcn b(aqu var0, int var1, int var2, dcn var3) {
      dcn _snowman = _snowman.a(_snowman.cD(), _snowman.cE(), _snowman.cH());
      return a(_snowman, _snowman, _snowman, 0, _snowman, true, (float) (Math.PI / 2), _snowman::f, false, 0, 0, true);
   }

   @Nullable
   public static dcn a(aqu var0, int var1, int var2, dcn var3, double var4) {
      dcn _snowman = _snowman.a(_snowman.cD(), _snowman.cE(), _snowman.cH());
      return a(_snowman, _snowman, _snowman, 0, _snowman, true, _snowman, _snowman::f, false, 0, 0, true);
   }

   @Nullable
   public static dcn b(aqu var0, int var1, int var2, int var3, dcn var4, double var5) {
      dcn _snowman = _snowman.a(_snowman.cD(), _snowman.cE(), _snowman.cH());
      return a(_snowman, _snowman, _snowman, _snowman, _snowman, false, _snowman, _snowman::f, true, 0, 0, false);
   }

   @Nullable
   public static dcn c(aqu var0, int var1, int var2, dcn var3) {
      dcn _snowman = _snowman.cA().d(_snowman);
      return a(_snowman, _snowman, _snowman, 0, _snowman, true, (float) (Math.PI / 2), _snowman::f, false, 0, 0, true);
   }

   @Nullable
   public static dcn d(aqu var0, int var1, int var2, dcn var3) {
      dcn _snowman = _snowman.cA().d(_snowman);
      return a(_snowman, _snowman, _snowman, 0, _snowman, false, (float) (Math.PI / 2), _snowman::f, true, 0, 0, true);
   }

   @Nullable
   private static dcn a(
      aqu var0,
      int var1,
      int var2,
      int var3,
      @Nullable dcn var4,
      boolean var5,
      double var6,
      ToDoubleFunction<fx> var8,
      boolean var9,
      int var10,
      int var11,
      boolean var12
   ) {
      ayj _snowman = _snowman.x();
      Random _snowmanx = _snowman.cY();
      boolean _snowmanxx;
      if (_snowman.ez()) {
         _snowmanxx = _snowman.ew().a(_snowman.cA(), (double)(_snowman.ex() + (float)_snowman) + 1.0);
      } else {
         _snowmanxx = false;
      }

      boolean _snowmanxxx = false;
      double _snowmanxxxx = Double.NEGATIVE_INFINITY;
      fx _snowmanxxxxx = _snowman.cB();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 10; _snowmanxxxxxx++) {
         fx _snowmanxxxxxxx = a(_snowmanx, _snowman, _snowman, _snowman, _snowman, _snowman);
         if (_snowmanxxxxxxx != null) {
            int _snowmanxxxxxxxx = _snowmanxxxxxxx.u();
            int _snowmanxxxxxxxxx = _snowmanxxxxxxx.v();
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxx.w();
            if (_snowman.ez() && _snowman > 1) {
               fx _snowmanxxxxxxxxxxx = _snowman.ew();
               if (_snowman.cD() > (double)_snowmanxxxxxxxxxxx.u()) {
                  _snowmanxxxxxxxx -= _snowmanx.nextInt(_snowman / 2);
               } else {
                  _snowmanxxxxxxxx += _snowmanx.nextInt(_snowman / 2);
               }

               if (_snowman.cH() > (double)_snowmanxxxxxxxxxxx.w()) {
                  _snowmanxxxxxxxxxx -= _snowmanx.nextInt(_snowman / 2);
               } else {
                  _snowmanxxxxxxxxxx += _snowmanx.nextInt(_snowman / 2);
               }
            }

            fx _snowmanxxxxxxxxxxxx = new fx((double)_snowmanxxxxxxxx + _snowman.cD(), (double)_snowmanxxxxxxxxx + _snowman.cE(), (double)_snowmanxxxxxxxxxx + _snowman.cH());
            if (_snowmanxxxxxxxxxxxx.v() >= 0 && _snowmanxxxxxxxxxxxx.v() <= _snowman.l.L() && (!_snowmanxx || _snowman.a(_snowmanxxxxxxxxxxxx)) && (!_snowman || _snowman.a(_snowmanxxxxxxxxxxxx))) {
               if (_snowman) {
                  _snowmanxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxx, _snowmanx.nextInt(_snowman + 1) + _snowman, _snowman.l.L(), var1x -> _snowman.l.d_(var1x).c().b());
               }

               if (_snowman || !_snowman.l.b(_snowmanxxxxxxxxxxxx).a(aef.b)) {
                  cwz _snowmanxxxxxxxxxxxxx = cxj.a(_snowman.l, _snowmanxxxxxxxxxxxx.i());
                  if (_snowman.a(_snowmanxxxxxxxxxxxxx) == 0.0F) {
                     double _snowmanxxxxxxxxxxxxxx = _snowman.applyAsDouble(_snowmanxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxx > _snowmanxxxx) {
                        _snowmanxxxx = _snowmanxxxxxxxxxxxxxx;
                        _snowmanxxxxx = _snowmanxxxxxxxxxxxx;
                        _snowmanxxx = true;
                     }
                  }
               }
            }
         }
      }

      return _snowmanxxx ? dcn.c(_snowmanxxxxx) : null;
   }

   @Nullable
   private static fx a(Random var0, int var1, int var2, int var3, @Nullable dcn var4, double var5) {
      if (_snowman != null && !(_snowman >= Math.PI)) {
         double _snowman = afm.d(_snowman.d, _snowman.b) - (float) (Math.PI / 2);
         double _snowmanx = _snowman + (double)(2.0F * _snowman.nextFloat() - 1.0F) * _snowman;
         double _snowmanxx = Math.sqrt(_snowman.nextDouble()) * (double)afm.a * (double)_snowman;
         double _snowmanxxx = -_snowmanxx * Math.sin(_snowmanx);
         double _snowmanxxxx = _snowmanxx * Math.cos(_snowmanx);
         if (!(Math.abs(_snowmanxxx) > (double)_snowman) && !(Math.abs(_snowmanxxxx) > (double)_snowman)) {
            int _snowmanxxxxx = _snowman.nextInt(2 * _snowman + 1) - _snowman + _snowman;
            return new fx(_snowmanxxx, (double)_snowmanxxxxx, _snowmanxxxx);
         } else {
            return null;
         }
      } else {
         int _snowman = _snowman.nextInt(2 * _snowman + 1) - _snowman;
         int _snowmanx = _snowman.nextInt(2 * _snowman + 1) - _snowman + _snowman;
         int _snowmanxx = _snowman.nextInt(2 * _snowman + 1) - _snowman;
         return new fx(_snowman, _snowmanx, _snowmanxx);
      }
   }

   static fx a(fx var0, int var1, int var2, Predicate<fx> var3) {
      if (_snowman < 0) {
         throw new IllegalArgumentException("aboveSolidAmount was " + _snowman + ", expected >= 0");
      } else if (!_snowman.test(_snowman)) {
         return _snowman;
      } else {
         fx _snowman = _snowman.b();

         while (_snowman.v() < _snowman && _snowman.test(_snowman)) {
            _snowman = _snowman.b();
         }

         fx _snowmanx = _snowman;

         while (_snowmanx.v() < _snowman && _snowmanx.v() - _snowman.v() < _snowman) {
            fx _snowmanxx = _snowmanx.b();
            if (_snowman.test(_snowmanxx)) {
               break;
            }

            _snowmanx = _snowmanxx;
         }

         return _snowmanx;
      }
   }
}
