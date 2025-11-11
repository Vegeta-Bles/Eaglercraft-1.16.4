import com.mojang.serialization.Codec;
import java.util.Random;

public class cjz extends cjl<cmh> {
   public cjz(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      int _snowman = 0;
      int _snowmanx = _snowman.a(chn.a.d, _snowman.u(), _snowman.w());
      fx _snowmanxx = new fx(_snowman.u(), _snowmanx, _snowman.w());
      if (_snowman.d_(_snowmanxx).a(bup.A)) {
         ceh _snowmanxxx = bup.kc.n();
         ceh _snowmanxxxx = bup.kd.n();
         int _snowmanxxxxx = 1 + _snowman.nextInt(10);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= _snowmanxxxxx; _snowmanxxxxxx++) {
            if (_snowman.d_(_snowmanxx).a(bup.A) && _snowman.d_(_snowmanxx.b()).a(bup.A) && _snowmanxxxx.a(_snowman, _snowmanxx)) {
               if (_snowmanxxxxxx == _snowmanxxxxx) {
                  _snowman.a(_snowmanxx, _snowmanxxx.a(bxt.d, Integer.valueOf(_snowman.nextInt(4) + 20)), 2);
                  _snowman++;
               } else {
                  _snowman.a(_snowmanxx, _snowmanxxxx, 2);
               }
            } else if (_snowmanxxxxxx > 0) {
               fx _snowmanxxxxxxx = _snowmanxx.c();
               if (_snowmanxxx.a(_snowman, _snowmanxxxxxxx) && !_snowman.d_(_snowmanxxxxxxx.c()).a(bup.kc)) {
                  _snowman.a(_snowmanxxxxxxx, _snowmanxxx.a(bxt.d, Integer.valueOf(_snowman.nextInt(4) + 20)), 2);
                  _snowman++;
               }
               break;
            }

            _snowmanxx = _snowmanxx.b();
         }
      }

      return _snowman > 0;
   }
}
