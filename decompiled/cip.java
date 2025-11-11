import com.mojang.serialization.Codec;
import java.util.Random;

public class cip extends cjl<cls> {
   public cip(Codec<cls> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cls var5) {
      while (_snowman.v() > 3) {
         if (!_snowman.w(_snowman.c())) {
            buo _snowman = _snowman.d_(_snowman.c()).b();
            if (b(_snowman) || a(_snowman)) {
               break;
            }
         }

         _snowman = _snowman.c();
      }

      if (_snowman.v() <= 3) {
         return false;
      } else {
         for (int _snowman = 0; _snowman < 3; _snowman++) {
            int _snowmanx = _snowman.nextInt(2);
            int _snowmanxx = _snowman.nextInt(2);
            int _snowmanxxx = _snowman.nextInt(2);
            float _snowmanxxxx = (float)(_snowmanx + _snowmanxx + _snowmanxxx) * 0.333F + 0.5F;

            for (fx _snowmanxxxxx : fx.a(_snowman.b(-_snowmanx, -_snowmanxx, -_snowmanxxx), _snowman.b(_snowmanx, _snowmanxx, _snowmanxxx))) {
               if (_snowmanxxxxx.j(_snowman) <= (double)(_snowmanxxxx * _snowmanxxxx)) {
                  _snowman.a(_snowmanxxxxx, _snowman.b, 4);
               }
            }

            _snowman = _snowman.b(-1 + _snowman.nextInt(2), -_snowman.nextInt(2), -1 + _snowman.nextInt(2));
         }

         return true;
      }
   }
}
