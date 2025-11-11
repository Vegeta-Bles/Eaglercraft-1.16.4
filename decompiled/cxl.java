import java.util.Comparator;
import java.util.Optional;

public class cxl {
   private final aag a;

   public cxl(aag var1) {
      this.a = _snowman;
   }

   public Optional<i.a> a(fx var1, boolean var2) {
      azo _snowman = this.a.y();
      int _snowmanx = _snowman ? 16 : 128;
      _snowman.a(this.a, _snowman, _snowmanx);
      Optional<azp> _snowmanxx = _snowman.b(var0 -> var0 == azr.v, _snowman, _snowmanx, azo.b.c)
         .sorted(Comparator.<azp>comparingDouble(var1x -> var1x.f().j(_snowman)).thenComparingInt(var0 -> var0.f().v()))
         .filter(var1x -> this.a.d_(var1x.f()).b(cex.E))
         .findFirst();
      return _snowmanxx.map(var1x -> {
         fx _snowmanxxx = var1x.f();
         this.a.i().a(aal.f, new brd(_snowmanxxx), 3, _snowmanxxx);
         ceh _snowmanx = this.a.d_(_snowmanxxx);
         return i.a(_snowmanxxx, _snowmanx.c(cex.E), 21, gc.a.b, 21, var2x -> this.a.d_(var2x) == _snowman);
      });
   }

   public Optional<i.a> a(fx var1, gc.a var2) {
      gc _snowman = gc.a(gc.b.a, _snowman);
      double _snowmanx = -1.0;
      fx _snowmanxx = null;
      double _snowmanxxx = -1.0;
      fx _snowmanxxxx = null;
      cfu _snowmanxxxxx = this.a.f();
      int _snowmanxxxxxx = this.a.ae() - 1;
      fx.a _snowmanxxxxxxx = _snowman.i();

      for (fx.a _snowmanxxxxxxxx : fx.a(_snowman, 16, gc.f, gc.d)) {
         int _snowmanxxxxxxxxx = Math.min(_snowmanxxxxxx, this.a.a(chn.a.e, _snowmanxxxxxxxx.u(), _snowmanxxxxxxxx.w()));
         int _snowmanxxxxxxxxxx = 1;
         if (_snowmanxxxxx.a(_snowmanxxxxxxxx) && _snowmanxxxxx.a(_snowmanxxxxxxxx.c(_snowman, 1))) {
            _snowmanxxxxxxxx.c(_snowman.f(), 1);

            for (int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxx--) {
               _snowmanxxxxxxxx.p(_snowmanxxxxxxxxxxx);
               if (this.a.w(_snowmanxxxxxxxx)) {
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;

                  while (_snowmanxxxxxxxxxxx > 0 && this.a.w(_snowmanxxxxxxxx.c(gc.a))) {
                     _snowmanxxxxxxxxxxx--;
                  }

                  if (_snowmanxxxxxxxxxxx + 4 <= _snowmanxxxxxx) {
                     int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxxxx;
                     if (_snowmanxxxxxxxxxxxxx <= 0 || _snowmanxxxxxxxxxxxxx >= 3) {
                        _snowmanxxxxxxxx.p(_snowmanxxxxxxxxxxx);
                        if (this.a(_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowman, 0)) {
                           double _snowmanxxxxxxxxxxxxxx = _snowman.j(_snowmanxxxxxxxx);
                           if (this.a(_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowman, -1) && this.a(_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowman, 1) && (_snowmanx == -1.0 || _snowmanx > _snowmanxxxxxxxxxxxxxx)) {
                              _snowmanx = _snowmanxxxxxxxxxxxxxx;
                              _snowmanxx = _snowmanxxxxxxxx.h();
                           }

                           if (_snowmanx == -1.0 && (_snowmanxxx == -1.0 || _snowmanxxx > _snowmanxxxxxxxxxxxxxx)) {
                              _snowmanxxx = _snowmanxxxxxxxxxxxxxx;
                              _snowmanxxxx = _snowmanxxxxxxxx.h();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (_snowmanx == -1.0 && _snowmanxxx != -1.0) {
         _snowmanxx = _snowmanxxxx;
         _snowmanx = _snowmanxxx;
      }

      if (_snowmanx == -1.0) {
         _snowmanxx = new fx(_snowman.u(), afm.a(_snowman.v(), 70, this.a.ae() - 10), _snowman.w()).h();
         gc _snowmanxxxxxxxxx = _snowman.g();
         if (!_snowmanxxxxx.a(_snowmanxx)) {
            return Optional.empty();
         }

         for (int _snowmanxxxxxxxxxx = -1; _snowmanxxxxxxxxxx < 2; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxx++) {
                  ceh _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx < 0 ? bup.bK.n() : bup.a.n();
                  _snowmanxxxxxxx.a(_snowmanxx, _snowmanxxxxxxxxxxxx * _snowman.i() + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxx.i(), _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx * _snowman.k() + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxx.k());
                  this.a.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
               }
            }
         }
      }

      for (int _snowmanxxxxxxxxx = -1; _snowmanxxxxxxxxx < 3; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = -1; _snowmanxxxxxxxxxx < 4; _snowmanxxxxxxxxxx++) {
            if (_snowmanxxxxxxxxx == -1 || _snowmanxxxxxxxxx == 2 || _snowmanxxxxxxxxxx == -1 || _snowmanxxxxxxxxxx == 3) {
               _snowmanxxxxxxx.a(_snowmanxx, _snowmanxxxxxxxxx * _snowman.i(), _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx * _snowman.k());
               this.a.a(_snowmanxxxxxxx, bup.bK.n(), 3);
            }
         }
      }

      ceh _snowmanxxxxxxxxx = bup.cT.n().a(byj.a, _snowman);

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxx++) {
            _snowmanxxxxxxx.a(_snowmanxx, _snowmanxxxxxxxxxxxx * _snowman.i(), _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx * _snowman.k());
            this.a.a(_snowmanxxxxxxx, _snowmanxxxxxxxxx, 18);
         }
      }

      return Optional.of(new i.a(_snowmanxx.h(), 2, 3));
   }

   private boolean a(fx var1, fx.a var2, gc var3, int var4) {
      gc _snowman = _snowman.g();

      for (int _snowmanx = -1; _snowmanx < 3; _snowmanx++) {
         for (int _snowmanxx = -1; _snowmanxx < 4; _snowmanxx++) {
            _snowman.a(_snowman, _snowman.i() * _snowmanx + _snowman.i() * _snowman, _snowmanxx, _snowman.k() * _snowmanx + _snowman.k() * _snowman);
            if (_snowmanxx < 0 && !this.a.d_(_snowman).c().b()) {
               return false;
            }

            if (_snowmanxx >= 0 && !this.a.w(_snowman)) {
               return false;
            }
         }
      }

      return true;
   }
}
