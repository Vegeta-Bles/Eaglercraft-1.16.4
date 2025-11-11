import com.mojang.serialization.Codec;
import java.util.Random;

public class cir extends cjl<cmh> {
   public cir(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      if (_snowman.v() > _snowman.t_() - 1) {
         return false;
      } else if (!_snowman.d_(_snowman).a(bup.A) && !_snowman.d_(_snowman.c()).a(bup.A)) {
         return false;
      } else {
         boolean _snowman = false;

         for (gc _snowmanx : gc.values()) {
            if (_snowmanx != gc.a && _snowman.d_(_snowman.a(_snowmanx)).a(bup.gT)) {
               _snowman = true;
               break;
            }
         }

         if (!_snowman) {
            return false;
         } else {
            _snowman.a(_snowman, bup.kV.n(), 2);

            for (int _snowmanxx = 0; _snowmanxx < 200; _snowmanxx++) {
               int _snowmanxxx = _snowman.nextInt(5) - _snowman.nextInt(6);
               int _snowmanxxxx = 3;
               if (_snowmanxxx < 2) {
                  _snowmanxxxx += _snowmanxxx / 2;
               }

               if (_snowmanxxxx >= 1) {
                  fx _snowmanxxxxx = _snowman.b(_snowman.nextInt(_snowmanxxxx) - _snowman.nextInt(_snowmanxxxx), _snowmanxxx, _snowman.nextInt(_snowmanxxxx) - _snowman.nextInt(_snowmanxxxx));
                  ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
                  if (_snowmanxxxxxx.c() == cva.a || _snowmanxxxxxx.a(bup.A) || _snowmanxxxxxx.a(bup.gT) || _snowmanxxxxxx.a(bup.cD)) {
                     for (gc _snowmanxxxxxxx : gc.values()) {
                        ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxxx.a(_snowmanxxxxxxx));
                        if (_snowmanxxxxxxxx.a(bup.kV)) {
                           _snowman.a(_snowmanxxxxx, bup.kV.n(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
