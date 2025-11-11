import com.mojang.serialization.Codec;
import java.util.Random;

public class ciq extends cjl<clr> {
   public ciq(Codec<clr> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, clr var5) {
      if (_snowman.v() < 5) {
         return false;
      } else {
         int _snowman = 2 + _snowman.nextInt(2);
         int _snowmanx = 2 + _snowman.nextInt(2);

         for (fx _snowmanxx : fx.a(_snowman.b(-_snowman, 0, -_snowmanx), _snowman.b(_snowman, 1, _snowmanx))) {
            int _snowmanxxx = _snowman.u() - _snowmanxx.u();
            int _snowmanxxxx = _snowman.w() - _snowmanxx.w();
            if ((float)(_snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx) <= _snowman.nextFloat() * 10.0F - _snowman.nextFloat() * 6.0F) {
               this.a(_snowman, _snowmanxx, _snowman, _snowman);
            } else if ((double)_snowman.nextFloat() < 0.031) {
               this.a(_snowman, _snowmanxx, _snowman, _snowman);
            }
         }

         return true;
      }
   }

   private boolean a(bry var1, fx var2, Random var3) {
      fx _snowman = _snowman.c();
      ceh _snowmanx = _snowman.d_(_snowman);
      return _snowmanx.a(bup.iE) ? _snowman.nextBoolean() : _snowmanx.d(_snowman, _snowman, gc.b);
   }

   private void a(bry var1, fx var2, Random var3, clr var4) {
      if (_snowman.w(_snowman) && this.a(_snowman, _snowman, _snowman)) {
         _snowman.a(_snowman, _snowman.b.a(_snowman, _snowman), 4);
      }
   }
}
