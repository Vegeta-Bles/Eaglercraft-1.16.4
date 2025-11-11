import java.util.Random;

public class cht implements brj {
   private int a;

   public cht() {
   }

   @Override
   public int a(aag var1, boolean var2, boolean var3) {
      if (!_snowman) {
         return 0;
      } else if (!_snowman.V().b(brt.D)) {
         return 0;
      } else {
         Random _snowman = _snowman.t;
         this.a--;
         if (this.a > 0) {
            return 0;
         } else {
            this.a = this.a + 12000 + _snowman.nextInt(1200);
            long _snowmanx = _snowman.U() / 24000L;
            if (_snowmanx < 5L || !_snowman.M()) {
               return 0;
            } else if (_snowman.nextInt(5) != 0) {
               return 0;
            } else {
               int _snowmanxx = _snowman.x().size();
               if (_snowmanxx < 1) {
                  return 0;
               } else {
                  bfw _snowmanxxx = _snowman.x().get(_snowman.nextInt(_snowmanxx));
                  if (_snowmanxxx.a_()) {
                     return 0;
                  } else if (_snowman.a(_snowmanxxx.cB(), 2)) {
                     return 0;
                  } else {
                     int _snowmanxxxx = (24 + _snowman.nextInt(24)) * (_snowman.nextBoolean() ? -1 : 1);
                     int _snowmanxxxxx = (24 + _snowman.nextInt(24)) * (_snowman.nextBoolean() ? -1 : 1);
                     fx.a _snowmanxxxxxx = _snowmanxxx.cB().i().e(_snowmanxxxx, 0, _snowmanxxxxx);
                     if (!_snowman.a(_snowmanxxxxxx.u() - 10, _snowmanxxxxxx.v() - 10, _snowmanxxxxxx.w() - 10, _snowmanxxxxxx.u() + 10, _snowmanxxxxxx.v() + 10, _snowmanxxxxxx.w() + 10)) {
                        return 0;
                     } else {
                        bsv _snowmanxxxxxxx = _snowman.v(_snowmanxxxxxx);
                        bsv.b _snowmanxxxxxxxx = _snowmanxxxxxxx.t();
                        if (_snowmanxxxxxxxx == bsv.b.p) {
                           return 0;
                        } else {
                           int _snowmanxxxxxxxxx = 0;
                           int _snowmanxxxxxxxxxx = (int)Math.ceil((double)_snowman.d(_snowmanxxxxxx).b()) + 1;

                           for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx++) {
                              _snowmanxxxxxxxxx++;
                              _snowmanxxxxxx.p(_snowman.a(chn.a.f, _snowmanxxxxxx).v());
                              if (_snowmanxxxxxxxxxxx == 0) {
                                 if (!this.a(_snowman, _snowmanxxxxxx, _snowman, true)) {
                                    break;
                                 }
                              } else {
                                 this.a(_snowman, _snowmanxxxxxx, _snowman, false);
                              }

                              _snowmanxxxxxx.o(_snowmanxxxxxx.u() + _snowman.nextInt(5) - _snowman.nextInt(5));
                              _snowmanxxxxxx.q(_snowmanxxxxxx.w() + _snowman.nextInt(5) - _snowman.nextInt(5));
                           }

                           return _snowmanxxxxxxxxx;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean a(aag var1, fx var2, Random var3, boolean var4) {
      ceh _snowman = _snowman.d_(_snowman);
      if (!bsg.a(_snowman, _snowman, _snowman, _snowman.m(), aqe.ak)) {
         return false;
      } else if (!bdr.b(aqe.ak, _snowman, aqp.p, _snowman, _snowman)) {
         return false;
      } else {
         bdr _snowmanx = aqe.ak.a(_snowman);
         if (_snowmanx != null) {
            if (_snowman) {
               _snowmanx.t(true);
               _snowmanx.eU();
            }

            _snowmanx.d((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
            _snowmanx.a(_snowman, _snowman.d(_snowman), aqp.p, null, null);
            _snowman.l(_snowmanx);
            return true;
         } else {
            return false;
         }
      }
   }
}
