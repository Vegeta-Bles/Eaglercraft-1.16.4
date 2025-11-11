import com.mojang.serialization.Codec;
import java.util.Random;

public class cjj extends cjl<cmh> {
   public cjj(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      float _snowman = (float)(_snowman.nextInt(3) + 4);

      for (int _snowmanx = 0; _snowman > 0.5F; _snowmanx--) {
         for (int _snowmanxx = afm.d(-_snowman); _snowmanxx <= afm.f(_snowman); _snowmanxx++) {
            for (int _snowmanxxx = afm.d(-_snowman); _snowmanxxx <= afm.f(_snowman); _snowmanxxx++) {
               if ((float)(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx) <= (_snowman + 1.0F) * (_snowman + 1.0F)) {
                  this.a(_snowman, _snowman.b(_snowmanxx, _snowmanx, _snowmanxxx), bup.ee.n());
               }
            }
         }

         _snowman = (float)((double)_snowman - ((double)_snowman.nextInt(2) + 0.5));
      }

      return true;
   }
}
