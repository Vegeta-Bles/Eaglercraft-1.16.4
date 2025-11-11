import java.util.function.Function;
import javax.annotation.Nullable;

public class bho {
   public static int[][] a(gc var0) {
      gc _snowman = _snowman.g();
      gc _snowmanx = _snowman.f();
      gc _snowmanxx = _snowman.f();
      return new int[][]{
         {_snowman.i(), _snowman.k()},
         {_snowmanx.i(), _snowmanx.k()},
         {_snowmanxx.i() + _snowman.i(), _snowmanxx.k() + _snowman.k()},
         {_snowmanxx.i() + _snowmanx.i(), _snowmanxx.k() + _snowmanx.k()},
         {_snowman.i() + _snowman.i(), _snowman.k() + _snowman.k()},
         {_snowman.i() + _snowmanx.i(), _snowman.k() + _snowmanx.k()},
         {_snowmanxx.i(), _snowmanxx.k()},
         {_snowman.i(), _snowman.k()}
      };
   }

   public static boolean a(double var0) {
      return !Double.isInfinite(_snowman) && _snowman < 1.0;
   }

   public static boolean a(brg var0, aqm var1, dci var2) {
      return _snowman.b(_snowman, _snowman).allMatch(ddh::b);
   }

   @Nullable
   public static dcn a(brg var0, double var1, double var3, double var5, aqm var7, aqx var8) {
      if (a(_snowman)) {
         dcn _snowman = new dcn(_snowman, _snowman, _snowman);
         if (a(_snowman, _snowman, _snowman.f(_snowman).c(_snowman))) {
            return _snowman;
         }
      }

      return null;
   }

   public static ddh a(brc var0, fx var1) {
      ceh _snowman = _snowman.d_(_snowman);
      return !_snowman.a(aed.at) && (!(_snowman.b() instanceof cbb) || !_snowman.c(cbb.a)) ? _snowman.k(_snowman, _snowman) : dde.a();
   }

   public static double a(fx var0, int var1, Function<fx, ddh> var2) {
      fx.a _snowman = _snowman.i();
      int _snowmanx = 0;

      while (_snowmanx < _snowman) {
         ddh _snowmanxx = _snowman.apply(_snowman);
         if (!_snowmanxx.b()) {
            return (double)(_snowman.v() + _snowmanx) + _snowmanxx.b(gc.a.b);
         }

         _snowmanx++;
         _snowman.c(gc.b);
      }

      return Double.POSITIVE_INFINITY;
   }

   @Nullable
   public static dcn a(aqe<?> var0, brg var1, fx var2, boolean var3) {
      if (_snowman && _snowman.a(_snowman.d_(_snowman))) {
         return null;
      } else {
         double _snowman = _snowman.a(a((brc)_snowman, _snowman), () -> a((brc)_snowman, _snowman.c()));
         if (!a(_snowman)) {
            return null;
         } else if (_snowman && _snowman <= 0.0 && _snowman.a(_snowman.d_(_snowman.c()))) {
            return null;
         } else {
            dcn _snowmanx = dcn.a(_snowman, _snowman);
            return _snowman.b(null, _snowman.l().a(_snowmanx)).allMatch(ddh::b) ? _snowmanx : null;
         }
      }
   }
}
