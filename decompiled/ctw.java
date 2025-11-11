import com.mojang.serialization.Codec;
import java.util.Random;

public class ctw extends ctt<ctu> {
   public ctw(Codec<ctu> var1) {
      super(_snowman);
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      double _snowman = bsv.f.a((double)_snowman * 0.25, (double)_snowman * 0.25, false);
      if (_snowman > 0.0) {
         int _snowmanx = _snowman & 15;
         int _snowmanxx = _snowman & 15;
         fx.a _snowmanxxx = new fx.a();

         for (int _snowmanxxxx = _snowman; _snowmanxxxx >= 0; _snowmanxxxx--) {
            _snowmanxxx.d(_snowmanx, _snowmanxxxx, _snowmanxx);
            if (!_snowman.d_(_snowmanxxx).g()) {
               if (_snowmanxxxx == 62 && !_snowman.d_(_snowmanxxx).a(_snowman.b())) {
                  _snowman.a(_snowmanxxx, _snowman, false);
               }
               break;
            }
         }
      }

      ctt.v.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
