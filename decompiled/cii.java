import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cii<U extends cma> extends cjl<U> {
   public cii(Codec<U> var1) {
      super(_snowman);
   }

   @Override
   public boolean a(bsr var1, cfy var2, Random var3, fx var4, U var5) {
      ceh _snowman = this.b(_snowman, _snowman, _snowman);
      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < this.a(_snowman); _snowmanxx++) {
         fx _snowmanxxx = this.a(_snowman, _snowman, _snowman);
         if (_snowman.w(_snowmanxxx) && _snowmanxxx.v() < 255 && _snowman.a(_snowman, _snowmanxxx) && this.a(_snowman, _snowmanxxx, _snowman)) {
            _snowman.a(_snowmanxxx, _snowman, 2);
            _snowmanx++;
         }
      }

      return _snowmanx > 0;
   }

   public abstract boolean a(bry var1, fx var2, U var3);

   public abstract int a(U var1);

   public abstract fx a(Random var1, fx var2, U var3);

   public abstract ceh b(Random var1, fx var2, U var3);
}
