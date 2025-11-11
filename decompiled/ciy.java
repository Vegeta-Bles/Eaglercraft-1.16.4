import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class ciy extends cjl<cmh> {
   public ciy(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      ceh _snowman = aed.Y.a(_snowman).n();
      return this.a(_snowman, _snowman, _snowman, _snowman);
   }

   protected abstract boolean a(bry var1, Random var2, fx var3, ceh var4);

   protected boolean b(bry var1, Random var2, fx var3, ceh var4) {
      fx _snowman = _snowman.b();
      ceh _snowmanx = _snowman.d_(_snowman);
      if ((_snowmanx.a(bup.A) || _snowmanx.a(aed.ab)) && _snowman.d_(_snowman).a(bup.A)) {
         _snowman.a(_snowman, _snowman, 3);
         if (_snowman.nextFloat() < 0.25F) {
            _snowman.a(_snowman, aed.ab.a(_snowman).n(), 2);
         } else if (_snowman.nextFloat() < 0.05F) {
            _snowman.a(_snowman, bup.kU.n().a(bzq.a, Integer.valueOf(_snowman.nextInt(4) + 1)), 2);
         }

         for (gc _snowmanxx : gc.c.a) {
            if (_snowman.nextFloat() < 0.2F) {
               fx _snowmanxxx = _snowman.a(_snowmanxx);
               if (_snowman.d_(_snowmanxxx).a(bup.A)) {
                  ceh _snowmanxxxx = aed.Z.a(_snowman).n().a(buc.a, _snowmanxx);
                  _snowman.a(_snowmanxxx, _snowmanxxxx, 2);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
