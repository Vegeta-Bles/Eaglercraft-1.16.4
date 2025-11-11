import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;

public class bkj extends blx {
   public bkj(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      fx _snowmanxx = _snowmanx.a(_snowman.j());
      if (a(_snowman.m(), _snowman, _snowmanx)) {
         if (!_snowman.v) {
            _snowman.c(2005, _snowmanx, 0);
         }

         return aou.a(_snowman.v);
      } else {
         ceh _snowmanxxx = _snowman.d_(_snowmanx);
         boolean _snowmanxxxx = _snowmanxxx.d(_snowman, _snowmanx, _snowman.j());
         if (_snowmanxxxx && a(_snowman.m(), _snowman, _snowmanxx, _snowman.j())) {
            if (!_snowman.v) {
               _snowman.c(2005, _snowmanxx, 0);
            }

            return aou.a(_snowman.v);
         } else {
            return aou.c;
         }
      }
   }

   public static boolean a(bmb var0, brx var1, fx var2) {
      ceh _snowman = _snowman.d_(_snowman);
      if (_snowman.b() instanceof buq) {
         buq _snowmanx = (buq)_snowman.b();
         if (_snowmanx.a(_snowman, _snowman, _snowman, _snowman.v)) {
            if (_snowman instanceof aag) {
               if (_snowmanx.a(_snowman, _snowman.t, _snowman, _snowman)) {
                  _snowmanx.a((aag)_snowman, _snowman.t, _snowman, _snowman);
               }

               _snowman.g(1);
            }

            return true;
         }
      }

      return false;
   }

   public static boolean a(bmb var0, brx var1, fx var2, @Nullable gc var3) {
      if (_snowman.d_(_snowman).a(bup.A) && _snowman.b(_snowman).e() == 8) {
         if (!(_snowman instanceof aag)) {
            return true;
         } else {
            label80:
            for (int _snowman = 0; _snowman < 128; _snowman++) {
               fx _snowmanx = _snowman;
               ceh _snowmanxx = bup.aU.n();

               for (int _snowmanxxx = 0; _snowmanxxx < _snowman / 16; _snowmanxxx++) {
                  _snowmanx = _snowmanx.b(h.nextInt(3) - 1, (h.nextInt(3) - 1) * h.nextInt(3) / 2, h.nextInt(3) - 1);
                  if (_snowman.d_(_snowmanx).r(_snowman, _snowmanx)) {
                     continue label80;
                  }
               }

               Optional<vj<bsv>> _snowmanxxxx = _snowman.i(_snowmanx);
               if (Objects.equals(_snowmanxxxx, Optional.of(btb.S)) || Objects.equals(_snowmanxxxx, Optional.of(btb.V))) {
                  if (_snowman == 0 && _snowman != null && _snowman.n().d()) {
                     _snowmanxx = aed.Z.a(_snowman.t).n().a(buc.a, _snowman);
                  } else if (h.nextInt(4) == 0) {
                     _snowmanxx = aed.X.a(h).n();
                  }
               }

               if (_snowmanxx.b().a(aed.Z)) {
                  for (int _snowmanxxxxx = 0; !_snowmanxx.a((brz)_snowman, _snowmanx) && _snowmanxxxxx < 4; _snowmanxxxxx++) {
                     _snowmanxx = _snowmanxx.a(buc.a, gc.c.a.a(h));
                  }
               }

               if (_snowmanxx.a((brz)_snowman, _snowmanx)) {
                  ceh _snowmanxxxxx = _snowman.d_(_snowmanx);
                  if (_snowmanxxxxx.a(bup.A) && _snowman.b(_snowmanx).e() == 8) {
                     _snowman.a(_snowmanx, _snowmanxx, 3);
                  } else if (_snowmanxxxxx.a(bup.aU) && h.nextInt(10) == 0) {
                     ((buq)bup.aU).a((aag)_snowman, h, _snowmanx, _snowmanxxxxx);
                  }
               }
            }

            _snowman.g(1);
            return true;
         }
      } else {
         return false;
      }
   }

   public static void a(bry var0, fx var1, int var2) {
      if (_snowman == 0) {
         _snowman = 15;
      }

      ceh _snowman = _snowman.d_(_snowman);
      if (!_snowman.g()) {
         double _snowmanx = 0.5;
         double _snowmanxx;
         if (_snowman.a(bup.A)) {
            _snowman *= 3;
            _snowmanxx = 1.0;
            _snowmanx = 3.0;
         } else if (_snowman.i(_snowman, _snowman)) {
            _snowman = _snowman.b();
            _snowman *= 3;
            _snowmanx = 3.0;
            _snowmanxx = 1.0;
         } else {
            _snowmanxx = _snowman.j(_snowman, _snowman).c(gc.a.b);
         }

         _snowman.a(hh.E, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, 0.0, 0.0, 0.0);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman; _snowmanxxx++) {
            double _snowmanxxxx = h.nextGaussian() * 0.02;
            double _snowmanxxxxx = h.nextGaussian() * 0.02;
            double _snowmanxxxxxx = h.nextGaussian() * 0.02;
            double _snowmanxxxxxxx = 0.5 - _snowmanx;
            double _snowmanxxxxxxxx = (double)_snowman.u() + _snowmanxxxxxxx + h.nextDouble() * _snowmanx * 2.0;
            double _snowmanxxxxxxxxx = (double)_snowman.v() + h.nextDouble() * _snowmanxx;
            double _snowmanxxxxxxxxxx = (double)_snowman.w() + _snowmanxxxxxxx + h.nextDouble() * _snowmanx * 2.0;
            if (!_snowman.d_(new fx(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx).c()).g()) {
               _snowman.a(hh.E, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
            }
         }
      }
   }
}
