import com.mojang.serialization.Codec;
import java.util.Random;

public class clg extends cjl<cmh> {
   private static final gc[] a = gc.values();

   public clg(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      fx.a _snowman = _snowman.i();

      for (int _snowmanx = 64; _snowmanx < 256; _snowmanx++) {
         _snowman.g(_snowman);
         _snowman.e(_snowman.nextInt(4) - _snowman.nextInt(4), 0, _snowman.nextInt(4) - _snowman.nextInt(4));
         _snowman.p(_snowmanx);
         if (_snowman.w(_snowman)) {
            for (gc _snowmanxx : a) {
               if (_snowmanxx != gc.a && cbi.a(_snowman, _snowman, _snowmanxx)) {
                  _snowman.a(_snowman, bup.dP.n().a(cbi.a(_snowmanxx), Boolean.valueOf(true)), 2);
                  break;
               }
            }
         }
      }

      return true;
   }
}
