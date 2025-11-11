import com.mojang.serialization.Codec;
import java.util.Random;

public class cti extends cte {
   private static final ceh K = bup.fF.n();
   private static final ceh L = bup.fG.n();
   private static final ceh M = bup.gR.n();

   public cti(Codec<ctu> var1) {
      super(_snowman);
   }

   @Override
   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      double _snowman = 0.0;
      double _snowmanx = Math.min(Math.abs(_snowman), this.c.a((double)_snowman * 0.25, (double)_snowman * 0.25, false) * 15.0);
      if (_snowmanx > 0.0) {
         double _snowmanxx = 0.001953125;
         double _snowmanxxx = Math.abs(this.d.a((double)_snowman * 0.001953125, (double)_snowman * 0.001953125, false));
         _snowman = _snowmanx * _snowmanx * 2.5;
         double _snowmanxxxx = Math.ceil(_snowmanxxx * 50.0) + 14.0;
         if (_snowman > _snowmanxxxx) {
            _snowman = _snowmanxxxx;
         }

         _snowman += 64.0;
      }

      int _snowmanxx = _snowman & 15;
      int _snowmanxxx = _snowman & 15;
      ceh _snowmanxxxx = K;
      ctv _snowmanxxxxx = _snowman.e().e();
      ceh _snowmanxxxxxx = _snowmanxxxxx.b();
      ceh _snowmanxxxxxxx = _snowmanxxxxx.a();
      ceh _snowmanxxxxxxxx = _snowmanxxxxxx;
      int _snowmanxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      boolean _snowmanxxxxxxxxxx = Math.cos(_snowman / 3.0 * Math.PI) > 0.0;
      int _snowmanxxxxxxxxxxx = -1;
      boolean _snowmanxxxxxxxxxxxx = false;
      fx.a _snowmanxxxxxxxxxxxxx = new fx.a();

      for (int _snowmanxxxxxxxxxxxxxx = Math.max(_snowman, (int)_snowman + 1); _snowmanxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxx--) {
         _snowmanxxxxxxxxxxxxx.d(_snowmanxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxx);
         if (_snowman.d_(_snowmanxxxxxxxxxxxxx).g() && _snowmanxxxxxxxxxxxxxx < (int)_snowman) {
            _snowman.a(_snowmanxxxxxxxxxxxxx, _snowman, false);
         }

         ceh _snowmanxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxx.g()) {
            _snowmanxxxxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxxxxx.a(_snowman.b())) {
            if (_snowmanxxxxxxxxxxx == -1) {
               _snowmanxxxxxxxxxxxx = false;
               if (_snowmanxxxxxxxxx <= 0) {
                  _snowmanxxxx = bup.a.n();
                  _snowmanxxxxxxxx = _snowman;
               } else if (_snowmanxxxxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxxxxx <= _snowman + 1) {
                  _snowmanxxxx = K;
                  _snowmanxxxxxxxx = _snowmanxxxxxx;
               }

               if (_snowmanxxxxxxxxxxxxxx < _snowman && (_snowmanxxxx == null || _snowmanxxxx.g())) {
                  _snowmanxxxx = _snowman;
               }

               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx + Math.max(0, _snowmanxxxxxxxxxxxxxx - _snowman);
               if (_snowmanxxxxxxxxxxxxxx >= _snowman - 1) {
                  if (_snowmanxxxxxxxxxxxxxx > _snowman + 3 + _snowmanxxxxxxxxx) {
                     ceh _snowmanxxxxxxxxxxxxxxxx;
                     if (_snowmanxxxxxxxxxxxxxx < 64 || _snowmanxxxxxxxxxxxxxx > 127) {
                        _snowmanxxxxxxxxxxxxxxxx = L;
                     } else if (_snowmanxxxxxxxxxx) {
                        _snowmanxxxxxxxxxxxxxxxx = M;
                     } else {
                        _snowmanxxxxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxxxxxx, _snowman);
                     }

                     _snowman.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, false);
                  } else {
                     _snowman.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxx, false);
                     _snowmanxxxxxxxxxxxx = true;
                  }
               } else {
                  _snowman.a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxx, false);
                  buo _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.b();
                  if (_snowmanxxxxxxxxxxxxxxxx == bup.fF
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fG
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fH
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fI
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fJ
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fK
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fL
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fM
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fN
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fO
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fP
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fQ
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fR
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fS
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fT
                     || _snowmanxxxxxxxxxxxxxxxx == bup.fU) {
                     _snowman.a(_snowmanxxxxxxxxxxxxx, L, false);
                  }
               }
            } else if (_snowmanxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxx--;
               if (_snowmanxxxxxxxxxxxx) {
                  _snowman.a(_snowmanxxxxxxxxxxxxx, L, false);
               } else {
                  _snowman.a(_snowmanxxxxxxxxxxxxx, this.a(_snowman, _snowmanxxxxxxxxxxxxxx, _snowman), false);
               }
            }
         }
      }
   }
}
