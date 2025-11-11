import com.mojang.serialization.Codec;
import java.util.Random;

public class ctx extends cte {
   private static final ceh K = bup.fF.n();
   private static final ceh L = bup.fG.n();
   private static final ceh M = bup.gR.n();

   public ctx(Codec<ctu> var1) {
      super(_snowman);
   }

   @Override
   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      int _snowman = _snowman & 15;
      int _snowmanx = _snowman & 15;
      ceh _snowmanxx = K;
      ctv _snowmanxxx = _snowman.e().e();
      ceh _snowmanxxxx = _snowmanxxx.b();
      ceh _snowmanxxxxx = _snowmanxxx.a();
      ceh _snowmanxxxxxx = _snowmanxxxx;
      int _snowmanxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      boolean _snowmanxxxxxxxx = Math.cos(_snowman / 3.0 * Math.PI) > 0.0;
      int _snowmanxxxxxxxxx = -1;
      boolean _snowmanxxxxxxxxxx = false;
      int _snowmanxxxxxxxxxxx = 0;
      fx.a _snowmanxxxxxxxxxxxx = new fx.a();

      for (int _snowmanxxxxxxxxxxxxx = _snowman; _snowmanxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxx--) {
         if (_snowmanxxxxxxxxxxx < 15) {
            _snowmanxxxxxxxxxxxx.d(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanx);
            ceh _snowmanxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx.g()) {
               _snowmanxxxxxxxxx = -1;
            } else if (_snowmanxxxxxxxxxxxxxx.a(_snowman.b())) {
               if (_snowmanxxxxxxxxx == -1) {
                  _snowmanxxxxxxxxxx = false;
                  if (_snowmanxxxxxxx <= 0) {
                     _snowmanxx = bup.a.n();
                     _snowmanxxxxxx = _snowman;
                  } else if (_snowmanxxxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxxxx <= _snowman + 1) {
                     _snowmanxx = K;
                     _snowmanxxxxxx = _snowmanxxxx;
                  }

                  if (_snowmanxxxxxxxxxxxxx < _snowman && (_snowmanxx == null || _snowmanxx.g())) {
                     _snowmanxx = _snowman;
                  }

                  _snowmanxxxxxxxxx = _snowmanxxxxxxx + Math.max(0, _snowmanxxxxxxxxxxxxx - _snowman);
                  if (_snowmanxxxxxxxxxxxxx < _snowman - 1) {
                     _snowman.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxx, false);
                     if (_snowmanxxxxxx == K) {
                        _snowman.a(_snowmanxxxxxxxxxxxx, L, false);
                     }
                  } else if (_snowmanxxxxxxxxxxxxx > 86 + _snowmanxxxxxxx * 2) {
                     if (_snowmanxxxxxxxx) {
                        _snowman.a(_snowmanxxxxxxxxxxxx, bup.k.n(), false);
                     } else {
                        _snowman.a(_snowmanxxxxxxxxxxxx, bup.i.n(), false);
                     }
                  } else if (_snowmanxxxxxxxxxxxxx <= _snowman + 3 + _snowmanxxxxxxx) {
                     _snowman.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxx, false);
                     _snowmanxxxxxxxxxx = true;
                  } else {
                     ceh _snowmanxxxxxxxxxxxxxxx;
                     if (_snowmanxxxxxxxxxxxxx < 64 || _snowmanxxxxxxxxxxxxx > 127) {
                        _snowmanxxxxxxxxxxxxxxx = L;
                     } else if (_snowmanxxxxxxxx) {
                        _snowmanxxxxxxxxxxxxxxx = M;
                     } else {
                        _snowmanxxxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowman);
                     }

                     _snowman.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, false);
                  }
               } else if (_snowmanxxxxxxxxx > 0) {
                  _snowmanxxxxxxxxx--;
                  if (_snowmanxxxxxxxxxx) {
                     _snowman.a(_snowmanxxxxxxxxxxxx, L, false);
                  } else {
                     _snowman.a(_snowmanxxxxxxxxxxxx, this.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowman), false);
                  }
               }

               _snowmanxxxxxxxxxxx++;
            }
         }
      }
   }
}
