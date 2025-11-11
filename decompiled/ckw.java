import com.mojang.serialization.Codec;
import java.util.Random;

public class ckw extends cjl<cmh> {
   public ckw(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      fx.a _snowman = new fx.a();
      fx.a _snowmanx = new fx.a();

      for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
            int _snowmanxxxx = _snowman.u() + _snowmanxx;
            int _snowmanxxxxx = _snowman.w() + _snowmanxxx;
            int _snowmanxxxxxx = _snowman.a(chn.a.e, _snowmanxxxx, _snowmanxxxxx);
            _snowman.d(_snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx);
            _snowmanx.g(_snowman).c(gc.a, 1);
            bsv _snowmanxxxxxxx = _snowman.v(_snowman);
            if (_snowmanxxxxxxx.a(_snowman, _snowmanx, false)) {
               _snowman.a(_snowmanx, bup.cD.n(), 2);
            }

            if (_snowmanxxxxxxx.b(_snowman, _snowman)) {
               _snowman.a(_snowman, bup.cC.n(), 2);
               ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanx);
               if (_snowmanxxxxxxxx.b(cab.a)) {
                  _snowman.a(_snowmanx, _snowmanxxxxxxxx.a(cab.a, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}
