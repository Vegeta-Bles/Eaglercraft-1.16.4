import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class cil extends cjl<clt> {
   private static final ImmutableList<buo> a = ImmutableList.of(bup.B, bup.z, bup.iJ, bup.cM, bup.dV, bup.dW, bup.dX, bup.dY, bup.bR, bup.bP);

   public cil(Codec<clt> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, clt var5) {
      int _snowman = _snowman.f();
      if (!a(_snowman, _snowman, _snowman.i())) {
         return false;
      } else {
         int _snowmanx = _snowman.b().a(_snowman);
         boolean _snowmanxx = _snowman.nextFloat() < 0.9F;
         int _snowmanxxx = Math.min(_snowmanx, _snowmanxx ? 5 : 8);
         int _snowmanxxxx = _snowmanxx ? 50 : 15;
         boolean _snowmanxxxxx = false;

         for (fx _snowmanxxxxxx : fx.a(_snowman, _snowmanxxxx, _snowman.u() - _snowmanxxx, _snowman.v(), _snowman.w() - _snowmanxxx, _snowman.u() + _snowmanxxx, _snowman.v(), _snowman.w() + _snowmanxxx)) {
            int _snowmanxxxxxxx = _snowmanx - _snowmanxxxxxx.k(_snowman);
            if (_snowmanxxxxxxx >= 0) {
               _snowmanxxxxx |= this.a(_snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxx, _snowman.am_().a(_snowman));
            }
         }

         return _snowmanxxxxx;
      }
   }

   private boolean a(bry var1, int var2, fx var3, int var4, int var5) {
      boolean _snowman = false;

      for (fx _snowmanx : fx.b(_snowman.u() - _snowman, _snowman.v(), _snowman.w() - _snowman, _snowman.u() + _snowman, _snowman.v(), _snowman.w() + _snowman)) {
         int _snowmanxx = _snowmanx.k(_snowman);
         fx _snowmanxxx = a(_snowman, _snowman, _snowmanx) ? a(_snowman, _snowman, _snowmanx.i(), _snowmanxx) : a(_snowman, _snowmanx.i(), _snowmanxx);
         if (_snowmanxxx != null) {
            int _snowmanxxxx = _snowman - _snowmanxx / 2;

            for (fx.a _snowmanxxxxx = _snowmanxxx.i(); _snowmanxxxx >= 0; _snowmanxxxx--) {
               if (a(_snowman, _snowman, (fx)_snowmanxxxxx)) {
                  this.a(_snowman, _snowmanxxxxx, bup.cO.n());
                  _snowmanxxxxx.c(gc.b);
                  _snowman = true;
               } else {
                  if (!_snowman.d_(_snowmanxxxxx).a(bup.cO)) {
                     break;
                  }

                  _snowmanxxxxx.c(gc.b);
               }
            }
         }
      }

      return _snowman;
   }

   @Nullable
   private static fx a(bry var0, int var1, fx.a var2, int var3) {
      while (_snowman.v() > 1 && _snowman > 0) {
         _snowman--;
         if (a(_snowman, _snowman, _snowman)) {
            return _snowman;
         }

         _snowman.c(gc.a);
      }

      return null;
   }

   private static boolean a(bry var0, int var1, fx.a var2) {
      if (!a(_snowman, _snowman, (fx)_snowman)) {
         return false;
      } else {
         ceh _snowman = _snowman.d_(_snowman.c(gc.a));
         _snowman.c(gc.b);
         return !_snowman.g() && !a.contains(_snowman.b());
      }
   }

   @Nullable
   private static fx a(bry var0, fx.a var1, int var2) {
      while (_snowman.v() < _snowman.L() && _snowman > 0) {
         _snowman--;
         ceh _snowman = _snowman.d_(_snowman);
         if (a.contains(_snowman.b())) {
            return null;
         }

         if (_snowman.g()) {
            return _snowman;
         }

         _snowman.c(gc.b);
      }

      return null;
   }

   private static boolean a(bry var0, int var1, fx var2) {
      ceh _snowman = _snowman.d_(_snowman);
      return _snowman.g() || _snowman.a(bup.B) && _snowman.v() <= _snowman;
   }
}
