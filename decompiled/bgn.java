import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public final class bgn {
   public static dcl a(aqa var0, Predicate<aqa> var1) {
      dcn _snowman = _snowman.cC();
      brx _snowmanx = _snowman.l;
      dcn _snowmanxx = _snowman.cA();
      dcn _snowmanxxx = _snowmanxx.e(_snowman);
      dcl _snowmanxxxx = _snowmanx.a(new brf(_snowmanxx, _snowmanxxx, brf.a.a, brf.b.a, _snowman));
      if (_snowmanxxxx.c() != dcl.a.a) {
         _snowmanxxx = _snowmanxxxx.e();
      }

      dcl _snowmanxxxxx = a(_snowmanx, _snowman, _snowmanxx, _snowmanxxx, _snowman.cc().b(_snowman.cC()).g(1.0), _snowman);
      if (_snowmanxxxxx != null) {
         _snowmanxxxx = _snowmanxxxxx;
      }

      return _snowmanxxxx;
   }

   @Nullable
   public static dck a(aqa var0, dcn var1, dcn var2, dci var3, Predicate<aqa> var4, double var5) {
      brx _snowman = _snowman.l;
      double _snowmanx = _snowman;
      aqa _snowmanxx = null;
      dcn _snowmanxxx = null;

      for (aqa _snowmanxxxx : _snowman.a(_snowman, _snowman, _snowman)) {
         dci _snowmanxxxxx = _snowmanxxxx.cc().g((double)_snowmanxxxx.bg());
         Optional<dcn> _snowmanxxxxxx = _snowmanxxxxx.b(_snowman, _snowman);
         if (_snowmanxxxxx.d(_snowman)) {
            if (_snowmanx >= 0.0) {
               _snowmanxx = _snowmanxxxx;
               _snowmanxxx = _snowmanxxxxxx.orElse(_snowman);
               _snowmanx = 0.0;
            }
         } else if (_snowmanxxxxxx.isPresent()) {
            dcn _snowmanxxxxxxx = _snowmanxxxxxx.get();
            double _snowmanxxxxxxxx = _snowman.g(_snowmanxxxxxxx);
            if (_snowmanxxxxxxxx < _snowmanx || _snowmanx == 0.0) {
               if (_snowmanxxxx.cr() == _snowman.cr()) {
                  if (_snowmanx == 0.0) {
                     _snowmanxx = _snowmanxxxx;
                     _snowmanxxx = _snowmanxxxxxxx;
                  }
               } else {
                  _snowmanxx = _snowmanxxxx;
                  _snowmanxxx = _snowmanxxxxxxx;
                  _snowmanx = _snowmanxxxxxxxx;
               }
            }
         }
      }

      return _snowmanxx == null ? null : new dck(_snowmanxx, _snowmanxxx);
   }

   @Nullable
   public static dck a(brx var0, aqa var1, dcn var2, dcn var3, dci var4, Predicate<aqa> var5) {
      double _snowman = Double.MAX_VALUE;
      aqa _snowmanx = null;

      for (aqa _snowmanxx : _snowman.a(_snowman, _snowman, _snowman)) {
         dci _snowmanxxx = _snowmanxx.cc().g(0.3F);
         Optional<dcn> _snowmanxxxx = _snowmanxxx.b(_snowman, _snowman);
         if (_snowmanxxxx.isPresent()) {
            double _snowmanxxxxx = _snowman.g(_snowmanxxxx.get());
            if (_snowmanxxxxx < _snowman) {
               _snowmanx = _snowmanxx;
               _snowman = _snowmanxxxxx;
            }
         }
      }

      return _snowmanx == null ? null : new dck(_snowmanx);
   }

   public static final void a(aqa var0, float var1) {
      dcn _snowman = _snowman.cC();
      if (_snowman.g() != 0.0) {
         float _snowmanx = afm.a(aqa.c(_snowman));
         _snowman.p = (float)(afm.d(_snowman.d, _snowman.b) * 180.0F / (float)Math.PI) + 90.0F;
         _snowman.q = (float)(afm.d((double)_snowmanx, _snowman.c) * 180.0F / (float)Math.PI) - 90.0F;

         while (_snowman.q - _snowman.s < -180.0F) {
            _snowman.s -= 360.0F;
         }

         while (_snowman.q - _snowman.s >= 180.0F) {
            _snowman.s += 360.0F;
         }

         while (_snowman.p - _snowman.r < -180.0F) {
            _snowman.r -= 360.0F;
         }

         while (_snowman.p - _snowman.r >= 180.0F) {
            _snowman.r += 360.0F;
         }

         _snowman.q = afm.g(_snowman, _snowman.s, _snowman.q);
         _snowman.p = afm.g(_snowman, _snowman.r, _snowman.p);
      }
   }

   public static aot a(aqm var0, blx var1) {
      return _snowman.dD().b() == _snowman ? aot.a : aot.b;
   }

   public static bga a(aqm var0, bmb var1, float var2) {
      bkc _snowman = (bkc)(_snowman.b() instanceof bkc ? _snowman.b() : bmd.kd);
      bga _snowmanx = _snowman.a(_snowman.l, _snowman, _snowman);
      _snowmanx.a(_snowman, _snowman);
      if (_snowman.b() == bmd.ql && _snowmanx instanceof bgc) {
         ((bgc)_snowmanx).b(_snowman);
      }

      return _snowmanx;
   }
}
