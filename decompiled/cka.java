import com.mojang.serialization.Codec;
import java.util.Random;

public class cka extends cjl<cls> {
   private static final ceh a = bup.lb.n();

   public cka(Codec<cls> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cls var5) {
      while (_snowman.v() > 5 && _snowman.w(_snowman)) {
         _snowman = _snowman.c();
      }

      if (_snowman.v() <= 4) {
         return false;
      } else {
         _snowman = _snowman.c(4);
         if (_snowman.a(gp.a(_snowman), cla.q).findAny().isPresent()) {
            return false;
         } else {
            boolean[] _snowman = new boolean[2048];
            int _snowmanx = _snowman.nextInt(4) + 4;

            for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
               double _snowmanxxx = _snowman.nextDouble() * 6.0 + 3.0;
               double _snowmanxxxx = _snowman.nextDouble() * 4.0 + 2.0;
               double _snowmanxxxxx = _snowman.nextDouble() * 6.0 + 3.0;
               double _snowmanxxxxxx = _snowman.nextDouble() * (16.0 - _snowmanxxx - 2.0) + 1.0 + _snowmanxxx / 2.0;
               double _snowmanxxxxxxx = _snowman.nextDouble() * (8.0 - _snowmanxxxx - 4.0) + 2.0 + _snowmanxxxx / 2.0;
               double _snowmanxxxxxxxx = _snowman.nextDouble() * (16.0 - _snowmanxxxxx - 2.0) + 1.0 + _snowmanxxxxx / 2.0;

               for (int _snowmanxxxxxxxxx = 1; _snowmanxxxxxxxxx < 15; _snowmanxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxx = 1; _snowmanxxxxxxxxxx < 15; _snowmanxxxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxx < 7; _snowmanxxxxxxxxxxx++) {
                        double _snowmanxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxx - _snowmanxxxxxx) / (_snowmanxxx / 2.0);
                        double _snowmanxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxx - _snowmanxxxxxxx) / (_snowmanxxxx / 2.0);
                        double _snowmanxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxx - _snowmanxxxxxxxx) / (_snowmanxxxxx / 2.0);
                        double _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxxxxxx < 1.0) {
                           _snowman[(_snowmanxxxxxxxxx * 16 + _snowmanxxxxxxxxxx) * 8 + _snowmanxxxxxxxxxxx] = true;
                        }
                     }
                  }
               }
            }

            for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
               for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
                  for (int _snowmanxxxx = 0; _snowmanxxxx < 8; _snowmanxxxx++) {
                     boolean _snowmanxxxxx = !_snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + _snowmanxxxx]
                        && (
                           _snowmanxx < 15 && _snowman[((_snowmanxx + 1) * 16 + _snowmanxxx) * 8 + _snowmanxxxx]
                              || _snowmanxx > 0 && _snowman[((_snowmanxx - 1) * 16 + _snowmanxxx) * 8 + _snowmanxxxx]
                              || _snowmanxxx < 15 && _snowman[(_snowmanxx * 16 + _snowmanxxx + 1) * 8 + _snowmanxxxx]
                              || _snowmanxxx > 0 && _snowman[(_snowmanxx * 16 + (_snowmanxxx - 1)) * 8 + _snowmanxxxx]
                              || _snowmanxxxx < 7 && _snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + _snowmanxxxx + 1]
                              || _snowmanxxxx > 0 && _snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + (_snowmanxxxx - 1)]
                        );
                     if (_snowmanxxxxx) {
                        cva _snowmanxxxxxx = _snowman.d_(_snowman.b(_snowmanxx, _snowmanxxxx, _snowmanxxx)).c();
                        if (_snowmanxxxx >= 4 && _snowmanxxxxxx.a()) {
                           return false;
                        }

                        if (_snowmanxxxx < 4 && !_snowmanxxxxxx.b() && _snowman.d_(_snowman.b(_snowmanxx, _snowmanxxxx, _snowmanxxx)) != _snowman.b) {
                           return false;
                        }
                     }
                  }
               }
            }

            for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
               for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
                  for (int _snowmanxxxxx = 0; _snowmanxxxxx < 8; _snowmanxxxxx++) {
                     if (_snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + _snowmanxxxxx]) {
                        _snowman.a(_snowman.b(_snowmanxx, _snowmanxxxxx, _snowmanxxx), _snowmanxxxxx >= 4 ? a : _snowman.b, 2);
                     }
                  }
               }
            }

            for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
               for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
                  for (int _snowmanxxxxxxx = 4; _snowmanxxxxxxx < 8; _snowmanxxxxxxx++) {
                     if (_snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + _snowmanxxxxxxx]) {
                        fx _snowmanxxxxxxxx = _snowman.b(_snowmanxx, _snowmanxxxxxxx - 1, _snowmanxxx);
                        if (b(_snowman.d_(_snowmanxxxxxxxx).b()) && _snowman.a(bsf.a, _snowman.b(_snowmanxx, _snowmanxxxxxxx, _snowmanxxx)) > 0) {
                           bsv _snowmanxxxxxxxxx = _snowman.v(_snowmanxxxxxxxx);
                           if (_snowmanxxxxxxxxx.e().e().a().a(bup.dT)) {
                              _snowman.a(_snowmanxxxxxxxx, bup.dT.n(), 2);
                           } else {
                              _snowman.a(_snowmanxxxxxxxx, bup.i.n(), 2);
                           }
                        }
                     }
                  }
               }
            }

            if (_snowman.b.c() == cva.l) {
               for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
                  for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
                     for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 8; _snowmanxxxxxxxx++) {
                        boolean _snowmanxxxxxxxxx = !_snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + _snowmanxxxxxxxx]
                           && (
                              _snowmanxx < 15 && _snowman[((_snowmanxx + 1) * 16 + _snowmanxxx) * 8 + _snowmanxxxxxxxx]
                                 || _snowmanxx > 0 && _snowman[((_snowmanxx - 1) * 16 + _snowmanxxx) * 8 + _snowmanxxxxxxxx]
                                 || _snowmanxxx < 15 && _snowman[(_snowmanxx * 16 + _snowmanxxx + 1) * 8 + _snowmanxxxxxxxx]
                                 || _snowmanxxx > 0 && _snowman[(_snowmanxx * 16 + (_snowmanxxx - 1)) * 8 + _snowmanxxxxxxxx]
                                 || _snowmanxxxxxxxx < 7 && _snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + _snowmanxxxxxxxx + 1]
                                 || _snowmanxxxxxxxx > 0 && _snowman[(_snowmanxx * 16 + _snowmanxxx) * 8 + (_snowmanxxxxxxxx - 1)]
                           );
                        if (_snowmanxxxxxxxxx && (_snowmanxxxxxxxx < 4 || _snowman.nextInt(2) != 0) && _snowman.d_(_snowman.b(_snowmanxx, _snowmanxxxxxxxx, _snowmanxxx)).c().b()) {
                           _snowman.a(_snowman.b(_snowmanxx, _snowmanxxxxxxxx, _snowmanxxx), bup.b.n(), 2);
                        }
                     }
                  }
               }
            }

            if (_snowman.b.c() == cva.j) {
               for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
                  for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
                     int _snowmanxxxxxxxxx = 4;
                     fx _snowmanxxxxxxxxxx = _snowman.b(_snowmanxx, 4, _snowmanxxx);
                     if (_snowman.v(_snowmanxxxxxxxxxx).a(_snowman, _snowmanxxxxxxxxxx, false)) {
                        _snowman.a(_snowmanxxxxxxxxxx, bup.cD.n(), 2);
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
