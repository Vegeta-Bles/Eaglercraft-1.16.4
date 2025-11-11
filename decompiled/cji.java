import com.mojang.serialization.Codec;
import java.util.Random;

public class cji extends cjl<clz> {
   public cji(Codec<clz> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, clz var5) {
      for (fx _snowman : fx.a(_snowman.b(-1, -2, -1), _snowman.b(1, 2, 1))) {
         boolean _snowmanx = _snowman.u() == _snowman.u();
         boolean _snowmanxx = _snowman.v() == _snowman.v();
         boolean _snowmanxxx = _snowman.w() == _snowman.w();
         boolean _snowmanxxxx = Math.abs(_snowman.v() - _snowman.v()) == 2;
         if (_snowmanx && _snowmanxx && _snowmanxxx) {
            fx _snowmanxxxxx = _snowman.h();
            this.a(_snowman, _snowmanxxxxx, bup.iF.n());
            _snowman.c().ifPresent(var3x -> {
               ccj _snowmanxxxxxx = _snowman.c(_snowman);
               if (_snowmanxxxxxx instanceof cdk) {
                  cdk _snowmanx = (cdk)_snowmanxxxxxx;
                  _snowmanx.a(var3x, _snowman.d());
                  _snowmanxxxxxx.X_();
               }
            });
         } else if (_snowmanxx) {
            this.a(_snowman, _snowman, bup.a.n());
         } else if (_snowmanxxxx && _snowmanx && _snowmanxxx) {
            this.a(_snowman, _snowman, bup.z.n());
         } else if ((_snowmanx || _snowmanxxx) && !_snowmanxxxx) {
            this.a(_snowman, _snowman, bup.z.n());
         } else {
            this.a(_snowman, _snowman, bup.a.n());
         }
      }

      return true;
   }
}
