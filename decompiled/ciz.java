import com.mojang.serialization.Codec;
import java.util.Random;

public class ciz extends ciy {
   public ciz(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   protected boolean a(bry var1, Random var2, fx var3, ceh var4) {
      int _snowman = _snowman.nextInt(3) + 3;
      int _snowmanx = _snowman.nextInt(3) + 3;
      int _snowmanxx = _snowman.nextInt(3) + 3;
      int _snowmanxxx = _snowman.nextInt(3) + 1;
      fx.a _snowmanxxxx = _snowman.i();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx <= _snowmanx; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= _snowman; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= _snowmanxx; _snowmanxxxxxxx++) {
               _snowmanxxxx.d(_snowmanxxxxx + _snowman.u(), _snowmanxxxxxx + _snowman.v(), _snowmanxxxxxxx + _snowman.w());
               _snowmanxxxx.c(gc.a, _snowmanxxx);
               if ((_snowmanxxxxx != 0 && _snowmanxxxxx != _snowmanx || _snowmanxxxxxx != 0 && _snowmanxxxxxx != _snowman)
                  && (_snowmanxxxxxxx != 0 && _snowmanxxxxxxx != _snowmanxx || _snowmanxxxxxx != 0 && _snowmanxxxxxx != _snowman)
                  && (_snowmanxxxxx != 0 && _snowmanxxxxx != _snowmanx || _snowmanxxxxxxx != 0 && _snowmanxxxxxxx != _snowmanxx)
                  && (_snowmanxxxxx == 0 || _snowmanxxxxx == _snowmanx || _snowmanxxxxxx == 0 || _snowmanxxxxxx == _snowman || _snowmanxxxxxxx == 0 || _snowmanxxxxxxx == _snowmanxx)
                  && !(_snowman.nextFloat() < 0.1F)
                  && !this.b(_snowman, _snowman, _snowmanxxxx, _snowman)) {
               }
            }
         }
      }

      return true;
   }
}
