import com.mojang.serialization.Codec;
import java.util.Random;

public class cth extends ctt<ctu> {
   public cth(Codec<ctu> var1) {
      super(_snowman);
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.a(), _snowman.b(), _snowman.c(), _snowman);
   }

   protected void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, ceh var11, ceh var12, ceh var13, int var14) {
      ceh _snowman = _snowman;
      ceh _snowmanx = _snowman;
      fx.a _snowmanxx = new fx.a();
      int _snowmanxxx = -1;
      int _snowmanxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      int _snowmanxxxxx = _snowman & 15;
      int _snowmanxxxxxx = _snowman & 15;

      for (int _snowmanxxxxxxx = _snowman; _snowmanxxxxxxx >= 0; _snowmanxxxxxxx--) {
         _snowmanxx.d(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
         ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxx);
         if (_snowmanxxxxxxxx.g()) {
            _snowmanxxx = -1;
         } else if (_snowmanxxxxxxxx.a(_snowman.b())) {
            if (_snowmanxxx == -1) {
               if (_snowmanxxxx <= 0) {
                  _snowman = bup.a.n();
                  _snowmanx = _snowman;
               } else if (_snowmanxxxxxxx >= _snowman - 4 && _snowmanxxxxxxx <= _snowman + 1) {
                  _snowman = _snowman;
                  _snowmanx = _snowman;
               }

               if (_snowmanxxxxxxx < _snowman && (_snowman == null || _snowman.g())) {
                  if (_snowman.a(_snowmanxx.d(_snowman, _snowmanxxxxxxx, _snowman)) < 0.15F) {
                     _snowman = bup.cD.n();
                  } else {
                     _snowman = _snowman;
                  }

                  _snowmanxx.d(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
               }

               _snowmanxxx = _snowmanxxxx;
               if (_snowmanxxxxxxx >= _snowman - 1) {
                  _snowman.a(_snowmanxx, _snowman, false);
               } else if (_snowmanxxxxxxx < _snowman - 7 - _snowmanxxxx) {
                  _snowman = bup.a.n();
                  _snowmanx = _snowman;
                  _snowman.a(_snowmanxx, _snowman, false);
               } else {
                  _snowman.a(_snowmanxx, _snowmanx, false);
               }
            } else if (_snowmanxxx > 0) {
               _snowmanxxx--;
               _snowman.a(_snowmanxx, _snowmanx, false);
               if (_snowmanxxx == 0 && _snowmanx.a(bup.C) && _snowmanxxxx > 1) {
                  _snowmanxxx = _snowman.nextInt(4) + Math.max(0, _snowmanxxxxxxx - 63);
                  _snowmanx = _snowmanx.a(bup.D) ? bup.hG.n() : bup.at.n();
               }
            }
         }
      }
   }
}
