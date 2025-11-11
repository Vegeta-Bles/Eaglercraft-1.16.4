import com.mojang.serialization.Codec;
import java.util.Random;

public class cjo extends cjl<cmh> {
   public cjo(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      if (!_snowman.w(_snowman)) {
         return false;
      } else {
         ceh _snowman = _snowman.d_(_snowman.b());
         if (!_snowman.a(bup.cL) && !_snowman.a(bup.cO) && !_snowman.a(bup.np)) {
            return false;
         } else {
            _snowman.a(_snowman, bup.cS.n(), 2);

            for (int _snowmanx = 0; _snowmanx < 1500; _snowmanx++) {
               fx _snowmanxx = _snowman.b(_snowman.nextInt(8) - _snowman.nextInt(8), -_snowman.nextInt(12), _snowman.nextInt(8) - _snowman.nextInt(8));
               if (_snowman.d_(_snowmanxx).g()) {
                  int _snowmanxxx = 0;

                  for (gc _snowmanxxxx : gc.values()) {
                     if (_snowman.d_(_snowmanxx.a(_snowmanxxxx)).a(bup.cS)) {
                        _snowmanxxx++;
                     }

                     if (_snowmanxxx > 1) {
                        break;
                     }
                  }

                  if (_snowmanxxx == 1) {
                     _snowman.a(_snowmanxx, bup.cS.n(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}
