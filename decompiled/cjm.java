import com.mojang.serialization.Codec;
import java.util.Random;

public class cjm extends cjl<cmd> {
   public cjm(Codec<cmd> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmd var5) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
            int _snowmanxxx = _snowman.u() + _snowmanx;
            int _snowmanxxxx = _snowman.w() + _snowmanxx;
            int _snowmanxxxxx = _snowman.b;
            _snowman.d(_snowmanxxx, _snowmanxxxxx, _snowmanxxxx);
            if (_snowman.d_(_snowman).g()) {
               _snowman.a(_snowman, _snowman.c, 2);
            }
         }
      }

      return true;
   }
}
