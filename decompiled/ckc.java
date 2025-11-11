import com.mojang.serialization.Codec;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ckc extends cjl<cmh> {
   private static final Logger a = LogManager.getLogger();
   private static final aqe<?>[] ab = new aqe[]{aqe.av, aqe.aY, aqe.aY, aqe.aC};
   private static final ceh ac = bup.lb.n();

   public ckc(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      int _snowman = 3;
      int _snowmanx = _snowman.nextInt(2) + 2;
      int _snowmanxx = -_snowmanx - 1;
      int _snowmanxxx = _snowmanx + 1;
      int _snowmanxxxx = -1;
      int _snowmanxxxxx = 4;
      int _snowmanxxxxxx = _snowman.nextInt(2) + 2;
      int _snowmanxxxxxxx = -_snowmanxxxxxx - 1;
      int _snowmanxxxxxxxx = _snowmanxxxxxx + 1;
      int _snowmanxxxxxxxxx = 0;

      for (int _snowmanxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxx <= _snowmanxxx; _snowmanxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxx <= 4; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
               fx _snowmanxxxxxxxxxxxxx = _snowman.b(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               cva _snowmanxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxxx).c();
               boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.b();
               if (_snowmanxxxxxxxxxxx == -1 && !_snowmanxxxxxxxxxxxxxxx) {
                  return false;
               }

               if (_snowmanxxxxxxxxxxx == 4 && !_snowmanxxxxxxxxxxxxxxx) {
                  return false;
               }

               if ((_snowmanxxxxxxxxxx == _snowmanxx || _snowmanxxxxxxxxxx == _snowmanxxx || _snowmanxxxxxxxxxxxx == _snowmanxxxxxxx || _snowmanxxxxxxxxxxxx == _snowmanxxxxxxxx)
                  && _snowmanxxxxxxxxxxx == 0
                  && _snowman.w(_snowmanxxxxxxxxxxxxx)
                  && _snowman.w(_snowmanxxxxxxxxxxxxx.b())) {
                  _snowmanxxxxxxxxx++;
               }
            }
         }
      }

      if (_snowmanxxxxxxxxx >= 1 && _snowmanxxxxxxxxx <= 5) {
         for (int _snowmanxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxx <= _snowmanxxx; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = 3; _snowmanxxxxxxxxxxx >= -1; _snowmanxxxxxxxxxxx--) {
               for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
                  fx _snowmanxxxxxxxxxxxxxxxx = _snowman.b(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
                  ceh _snowmanxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxx != _snowmanxx
                     && _snowmanxxxxxxxxxxx != -1
                     && _snowmanxxxxxxxxxxxx != _snowmanxxxxxxx
                     && _snowmanxxxxxxxxxx != _snowmanxxx
                     && _snowmanxxxxxxxxxxx != 4
                     && _snowmanxxxxxxxxxxxx != _snowmanxxxxxxxx) {
                     if (!_snowmanxxxxxxxxxxxxxxxxx.a(bup.bR) && !_snowmanxxxxxxxxxxxxxxxxx.a(bup.bP)) {
                        _snowman.a(_snowmanxxxxxxxxxxxxxxxx, ac, 2);
                     }
                  } else if (_snowmanxxxxxxxxxxxxxxxx.v() >= 0 && !_snowman.d_(_snowmanxxxxxxxxxxxxxxxx.c()).c().b()) {
                     _snowman.a(_snowmanxxxxxxxxxxxxxxxx, ac, 2);
                  } else if (_snowmanxxxxxxxxxxxxxxxxx.c().b() && !_snowmanxxxxxxxxxxxxxxxxx.a(bup.bR)) {
                     if (_snowmanxxxxxxxxxxx == -1 && _snowman.nextInt(4) != 0) {
                        _snowman.a(_snowmanxxxxxxxxxxxxxxxx, bup.bJ.n(), 2);
                     } else {
                        _snowman.a(_snowmanxxxxxxxxxxxxxxxx, bup.m.n(), 2);
                     }
                  }
               }
            }
         }

         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 2; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxxxx = _snowman.u() + _snowman.nextInt(_snowmanx * 2 + 1) - _snowmanx;
               int _snowmanxxxxxxxxxxxxxxxxx = _snowman.v();
               int _snowmanxxxxxxxxxxxxxxxxxx = _snowman.w() + _snowman.nextInt(_snowmanxxxxxx * 2 + 1) - _snowmanxxxxxx;
               fx _snowmanxxxxxxxxxxxxxxxxxxx = new fx(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
               if (_snowman.w(_snowmanxxxxxxxxxxxxxxxxxxx)) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = 0;

                  for (gc _snowmanxxxxxxxxxxxxxxxxxxxxx : gc.c.a) {
                     if (_snowman.d_(_snowmanxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxx)).c().b()) {
                        _snowmanxxxxxxxxxxxxxxxxxxxx++;
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxx == 1) {
                     _snowman.a(_snowmanxxxxxxxxxxxxxxxxxxx, cru.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxx, bup.bR.n()), 2);
                     cdd.a(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxx, cyq.d);
                     break;
                  }
               }
            }
         }

         _snowman.a(_snowman, bup.bP.n(), 2);
         ccj _snowmanxxxxxxxxxx = _snowman.c(_snowman);
         if (_snowmanxxxxxxxxxx instanceof cdi) {
            ((cdi)_snowmanxxxxxxxxxx).d().a(this.a(_snowman));
         } else {
            a.error("Failed to fetch mob spawner entity at ({}, {}, {})", _snowman.u(), _snowman.v(), _snowman.w());
         }

         return true;
      } else {
         return false;
      }
   }

   private aqe<?> a(Random var1) {
      return x.a(ab, _snowman);
   }
}
