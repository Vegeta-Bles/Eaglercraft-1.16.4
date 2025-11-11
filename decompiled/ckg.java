import com.mojang.serialization.Codec;
import java.util.Random;

public class ckg extends cjl<cmj> {
   ckg(Codec<cmj> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmj var5) {
      int _snowman = _snowman.nextInt(_snowman.c + 1);
      fx.a _snowmanx = new fx.a();

      for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
         this.a(_snowmanx, _snowman, _snowman, Math.min(_snowmanxx, 7));
         if (_snowman.b.a(_snowman.d_(_snowmanx), _snowman) && !this.a(_snowman, _snowmanx)) {
            _snowman.a(_snowmanx, _snowman.d, 2);
         }
      }

      return true;
   }

   private void a(fx.a var1, Random var2, fx var3, int var4) {
      int _snowman = this.a(_snowman, _snowman);
      int _snowmanx = this.a(_snowman, _snowman);
      int _snowmanxx = this.a(_snowman, _snowman);
      _snowman.a(_snowman, _snowman, _snowmanx, _snowmanxx);
   }

   private int a(Random var1, int var2) {
      return Math.round((_snowman.nextFloat() - _snowman.nextFloat()) * (float)_snowman);
   }

   private boolean a(bry var1, fx var2) {
      fx.a _snowman = new fx.a();

      for (gc _snowmanx : gc.values()) {
         _snowman.a(_snowman, _snowmanx);
         if (_snowman.d_(_snowman).g()) {
            return true;
         }
      }

      return false;
   }
}
