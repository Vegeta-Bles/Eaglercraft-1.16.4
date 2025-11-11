import com.mojang.serialization.Codec;
import java.util.Random;

public class cju extends cjl<cmh> {
   public cju(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      while (_snowman.w(_snowman) && _snowman.v() > 2) {
         _snowman = _snowman.c();
      }

      if (!_snowman.d_(_snowman).a(bup.cE)) {
         return false;
      } else {
         _snowman = _snowman.b(_snowman.nextInt(4));
         int _snowman = _snowman.nextInt(4) + 7;
         int _snowmanx = _snowman / 4 + _snowman.nextInt(2);
         if (_snowmanx > 1 && _snowman.nextInt(60) == 0) {
            _snowman = _snowman.b(10 + _snowman.nextInt(30));
         }

         for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
            float _snowmanxxx = (1.0F - (float)_snowmanxx / (float)_snowman) * (float)_snowmanx;
            int _snowmanxxxx = afm.f(_snowmanxxx);

            for (int _snowmanxxxxx = -_snowmanxxxx; _snowmanxxxxx <= _snowmanxxxx; _snowmanxxxxx++) {
               float _snowmanxxxxxx = (float)afm.a(_snowmanxxxxx) - 0.25F;

               for (int _snowmanxxxxxxx = -_snowmanxxxx; _snowmanxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxx++) {
                  float _snowmanxxxxxxxx = (float)afm.a(_snowmanxxxxxxx) - 0.25F;
                  if ((_snowmanxxxxx == 0 && _snowmanxxxxxxx == 0 || !(_snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx > _snowmanxxx * _snowmanxxx))
                     && (_snowmanxxxxx != -_snowmanxxxx && _snowmanxxxxx != _snowmanxxxx && _snowmanxxxxxxx != -_snowmanxxxx && _snowmanxxxxxxx != _snowmanxxxx || !(_snowman.nextFloat() > 0.75F))) {
                     ceh _snowmanxxxxxxxxx = _snowman.d_(_snowman.b(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxxx));
                     buo _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.b();
                     if (_snowmanxxxxxxxxx.g() || b(_snowmanxxxxxxxxxx) || _snowmanxxxxxxxxxx == bup.cE || _snowmanxxxxxxxxxx == bup.cD) {
                        this.a(_snowman, _snowman.b(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxxx), bup.gT.n());
                     }

                     if (_snowmanxx != 0 && _snowmanxxxx > 1) {
                        _snowmanxxxxxxxxx = _snowman.d_(_snowman.b(_snowmanxxxxx, -_snowmanxx, _snowmanxxxxxxx));
                        _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.b();
                        if (_snowmanxxxxxxxxx.g() || b(_snowmanxxxxxxxxxx) || _snowmanxxxxxxxxxx == bup.cE || _snowmanxxxxxxxxxx == bup.cD) {
                           this.a(_snowman, _snowman.b(_snowmanxxxxx, -_snowmanxx, _snowmanxxxxxxx), bup.gT.n());
                        }
                     }
                  }
               }
            }
         }

         int _snowmanxx = _snowmanx - 1;
         if (_snowmanxx < 0) {
            _snowmanxx = 0;
         } else if (_snowmanxx > 1) {
            _snowmanxx = 1;
         }

         for (int _snowmanxxx = -_snowmanxx; _snowmanxxx <= _snowmanxx; _snowmanxxx++) {
            for (int _snowmanxxxx = -_snowmanxx; _snowmanxxxx <= _snowmanxx; _snowmanxxxx++) {
               fx _snowmanxxxxx = _snowman.b(_snowmanxxx, -1, _snowmanxxxx);
               int _snowmanxxxxxx = 50;
               if (Math.abs(_snowmanxxx) == 1 && Math.abs(_snowmanxxxx) == 1) {
                  _snowmanxxxxxx = _snowman.nextInt(5);
               }

               while (_snowmanxxxxx.v() > 50) {
                  ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxxx);
                  buo _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.b();
                  if (!_snowmanxxxxxxxx.g() && !b(_snowmanxxxxxxxxxxx) && _snowmanxxxxxxxxxxx != bup.cE && _snowmanxxxxxxxxxxx != bup.cD && _snowmanxxxxxxxxxxx != bup.gT) {
                     break;
                  }

                  this.a(_snowman, _snowmanxxxxx, bup.gT.n());
                  _snowmanxxxxx = _snowmanxxxxx.c();
                  if (--_snowmanxxxxxx <= 0) {
                     _snowmanxxxxx = _snowmanxxxxx.c(_snowman.nextInt(5) + 1);
                     _snowmanxxxxxx = _snowman.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}
