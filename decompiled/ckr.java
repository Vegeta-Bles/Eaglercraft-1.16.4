import com.mojang.serialization.Codec;
import java.util.Random;

public class ckr extends cjl<cmk> {
   public ckr(Codec<cmk> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmk var5) {
      boolean _snowman = false;
      int _snowmanx = _snowman.nextInt(8) - _snowman.nextInt(8);
      int _snowmanxx = _snowman.nextInt(8) - _snowman.nextInt(8);
      int _snowmanxxx = _snowman.a(chn.a.d, _snowman.u() + _snowmanx, _snowman.w() + _snowmanxx);
      fx _snowmanxxxx = new fx(_snowman.u() + _snowmanx, _snowmanxxx, _snowman.w() + _snowmanxx);
      if (_snowman.d_(_snowmanxxxx).a(bup.A)) {
         boolean _snowmanxxxxx = _snowman.nextDouble() < (double)_snowman.c;
         ceh _snowmanxxxxxx = _snowmanxxxxx ? bup.aV.n() : bup.aU.n();
         if (_snowmanxxxxxx.a(_snowman, _snowmanxxxx)) {
            if (_snowmanxxxxx) {
               ceh _snowmanxxxxxxx = _snowmanxxxxxx.a(cax.b, cfd.a);
               fx _snowmanxxxxxxxx = _snowmanxxxx.b();
               if (_snowman.d_(_snowmanxxxxxxxx).a(bup.A)) {
                  _snowman.a(_snowmanxxxx, _snowmanxxxxxx, 2);
                  _snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxx, 2);
               }
            } else {
               _snowman.a(_snowmanxxxx, _snowmanxxxxxx, 2);
            }

            _snowman = true;
         }
      }

      return _snowman;
   }
}
