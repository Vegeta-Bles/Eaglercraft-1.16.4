import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bie extends bja {
   private static final Logger g = LogManager.getLogger();
   private int h;
   private String i;
   private final biq j = biq.a();

   public bie(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public bie(int var1, bfv var2, bim var3) {
      super(bje.h, _snowman, _snowman, _snowman);
      this.a(this.j);
   }

   @Override
   protected boolean a(ceh var1) {
      return _snowman.a(aed.G);
   }

   @Override
   protected boolean b(bfw var1, boolean var2) {
      return (_snowman.bC.d || _snowman.bD >= this.j.b()) && this.j.b() > 0;
   }

   @Override
   protected bmb a(bfw var1, bmb var2) {
      if (!_snowman.bC.d) {
         _snowman.c(-this.j.b());
      }

      this.d.a(0, bmb.b);
      if (this.h > 0) {
         bmb _snowman = this.d.a(1);
         if (!_snowman.a() && _snowman.E() > this.h) {
            _snowman.g(this.h);
            this.d.a(1, _snowman);
         } else {
            this.d.a(1, bmb.b);
         }
      } else {
         this.d.a(1, bmb.b);
      }

      this.j.a(0);
      this.e.a((var1x, var2x) -> {
         ceh _snowman = var1x.d_(var2x);
         if (!_snowman.bC.d && _snowman.a(aed.G) && _snowman.cY().nextFloat() < 0.12F) {
            ceh _snowmanx = bts.c(_snowman);
            if (_snowmanx == null) {
               var1x.a(var2x, false);
               var1x.c(1029, var2x, 0);
            } else {
               var1x.a(var2x, _snowmanx, 2);
               var1x.c(1030, var2x, 0);
            }
         } else {
            var1x.c(1030, var2x, 0);
         }
      });
      return _snowman;
   }

   @Override
   public void e() {
      bmb _snowman = this.d.a(0);
      this.j.a(1);
      int _snowmanx = 0;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;
      if (_snowman.a()) {
         this.c.a(0, bmb.b);
         this.j.a(0);
      } else {
         bmb _snowmanxxxx = _snowman.i();
         bmb _snowmanxxxxx = this.d.a(1);
         Map<bps, Integer> _snowmanxxxxxx = bpu.a(_snowmanxxxx);
         _snowmanxx += _snowman.B() + (_snowmanxxxxx.a() ? 0 : _snowmanxxxxx.B());
         this.h = 0;
         if (!_snowmanxxxxx.a()) {
            boolean _snowmanxxxxxxx = _snowmanxxxxx.b() == bmd.pq && !blf.d(_snowmanxxxxx).isEmpty();
            if (_snowmanxxxx.e() && _snowmanxxxx.b().a(_snowman, _snowmanxxxxx)) {
               int _snowmanxxxxxxxx = Math.min(_snowmanxxxx.g(), _snowmanxxxx.h() / 4);
               if (_snowmanxxxxxxxx <= 0) {
                  this.c.a(0, bmb.b);
                  this.j.a(0);
                  return;
               }

               int _snowmanxxxxxxxxx;
               for (_snowmanxxxxxxxxx = 0; _snowmanxxxxxxxx > 0 && _snowmanxxxxxxxxx < _snowmanxxxxx.E(); _snowmanxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxx = _snowmanxxxx.g() - _snowmanxxxxxxxx;
                  _snowmanxxxx.b(_snowmanxxxxxxxxxx);
                  _snowmanx++;
                  _snowmanxxxxxxxx = Math.min(_snowmanxxxx.g(), _snowmanxxxx.h() / 4);
               }

               this.h = _snowmanxxxxxxxxx;
            } else {
               if (!_snowmanxxxxxxx && (_snowmanxxxx.b() != _snowmanxxxxx.b() || !_snowmanxxxx.e())) {
                  this.c.a(0, bmb.b);
                  this.j.a(0);
                  return;
               }

               if (_snowmanxxxx.e() && !_snowmanxxxxxxx) {
                  int _snowmanxxxxxxxxx = _snowman.h() - _snowman.g();
                  int _snowmanxxxxxxxxxx = _snowmanxxxxx.h() - _snowmanxxxxx.g();
                  int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx + _snowmanxxxx.h() * 12 / 100;
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxx = _snowmanxxxx.h() - _snowmanxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxx < 0) {
                     _snowmanxxxxxxxxxxxxx = 0;
                  }

                  if (_snowmanxxxxxxxxxxxxx < _snowmanxxxx.g()) {
                     _snowmanxxxx.b(_snowmanxxxxxxxxxxxxx);
                     _snowmanx += 2;
                  }
               }

               Map<bps, Integer> _snowmanxxxxxxxxxxxxxx = bpu.a(_snowmanxxxxx);
               boolean _snowmanxxxxxxxxxxxxxxx = false;
               boolean _snowmanxxxxxxxxxxxxxxxx = false;

               for (bps _snowmanxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx.keySet()) {
                  if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                     int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.getOrDefault(_snowmanxxxxxxxxxxxxxxxxx, 0);
                     int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxxxxxxx
                        ? _snowmanxxxxxxxxxxxxxxxxxxx + 1
                        : Math.max(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
                     boolean _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.a(_snowman);
                     if (this.f.bC.d || _snowman.b() == bmd.pq) {
                        _snowmanxxxxxxxxxxxxxxxxxxxx = true;
                     }

                     for (bps _snowmanxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxx.keySet()) {
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxx)) {
                           _snowmanxxxxxxxxxxxxxxxxxxxx = false;
                           _snowmanx++;
                        }
                     }

                     if (!_snowmanxxxxxxxxxxxxxxxxxxxx) {
                        _snowmanxxxxxxxxxxxxxxxx = true;
                     } else {
                        _snowmanxxxxxxxxxxxxxxx = true;
                        if (_snowmanxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxxx.a()) {
                           _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.a();
                        }

                        _snowmanxxxxxx.put(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0;
                        switch (_snowmanxxxxxxxxxxxxxxxxx.d()) {
                           case a:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 1;
                              break;
                           case b:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 2;
                              break;
                           case c:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 4;
                              break;
                           case d:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 8;
                        }

                        if (_snowmanxxxxxxx) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxx = Math.max(1, _snowmanxxxxxxxxxxxxxxxxxxxxxx / 2);
                        }

                        _snowmanx += _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
                        if (_snowman.E() > 1) {
                           _snowmanx = 40;
                        }
                     }
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxx) {
                  this.c.a(0, bmb.b);
                  this.j.a(0);
                  return;
               }
            }
         }

         if (StringUtils.isBlank(this.i)) {
            if (_snowman.t()) {
               _snowmanxxx = 1;
               _snowmanx += _snowmanxxx;
               _snowmanxxxx.s();
            }
         } else if (!this.i.equals(_snowman.r().getString())) {
            _snowmanxxx = 1;
            _snowmanx += _snowmanxxx;
            _snowmanxxxx.a(new oe(this.i));
         }

         this.j.a(_snowmanxx + _snowmanx);
         if (_snowmanx <= 0) {
            _snowmanxxxx = bmb.b;
         }

         if (_snowmanxxx == _snowmanx && _snowmanxxx > 0 && this.j.b() >= 40) {
            this.j.a(39);
         }

         if (this.j.b() >= 40 && !this.f.bC.d) {
            _snowmanxxxx = bmb.b;
         }

         if (!_snowmanxxxx.a()) {
            int _snowmanxxxxxxx = _snowmanxxxx.B();
            if (!_snowmanxxxxx.a() && _snowmanxxxxxxx < _snowmanxxxxx.B()) {
               _snowmanxxxxxxx = _snowmanxxxxx.B();
            }

            if (_snowmanxxx != _snowmanx || _snowmanxxx == 0) {
               _snowmanxxxxxxx = d(_snowmanxxxxxxx);
            }

            _snowmanxxxx.c(_snowmanxxxxxxx);
            bpu.a(_snowmanxxxxxx, _snowmanxxxx);
         }

         this.c.a(0, _snowmanxxxx);
         this.c();
      }
   }

   public static int d(int var0) {
      return _snowman * 2 + 1;
   }

   public void a(String var1) {
      this.i = _snowman;
      if (this.a(2).f()) {
         bmb _snowman = this.a(2).e();
         if (StringUtils.isBlank(_snowman)) {
            _snowman.s();
         } else {
            _snowman.a(new oe(this.i));
         }
      }

      this.e();
   }

   public int f() {
      return this.j.b();
   }
}
