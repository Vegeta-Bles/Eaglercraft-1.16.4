import com.mojang.serialization.Codec;
import java.util.Random;

public class ckd extends cjl<clr> {
   public ckd(Codec<clr> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, clr var5) {
      return a(_snowman, _snowman, _snowman, _snowman, 8, 4);
   }

   public static boolean a(bry var0, Random var1, fx var2, clr var3, int var4, int var5) {
      buo _snowman = _snowman.d_(_snowman.c()).b();
      if (!_snowman.a(aed.ao)) {
         return false;
      } else {
         int _snowmanx = _snowman.v();
         if (_snowmanx >= 1 && _snowmanx + 1 < 256) {
            int _snowmanxx = 0;

            for (int _snowmanxxx = 0; _snowmanxxx < _snowman * _snowman; _snowmanxxx++) {
               fx _snowmanxxxx = _snowman.b(_snowman.nextInt(_snowman) - _snowman.nextInt(_snowman), _snowman.nextInt(_snowman) - _snowman.nextInt(_snowman), _snowman.nextInt(_snowman) - _snowman.nextInt(_snowman));
               ceh _snowmanxxxxx = _snowman.b.a(_snowman, _snowmanxxxx);
               if (_snowman.w(_snowmanxxxx) && _snowmanxxxx.v() > 0 && _snowmanxxxxx.a(_snowman, _snowmanxxxx)) {
                  _snowman.a(_snowmanxxxx, _snowmanxxxxx, 2);
                  _snowmanxx++;
               }
            }

            return _snowmanxx > 0;
         } else {
            return false;
         }
      }
   }
}
