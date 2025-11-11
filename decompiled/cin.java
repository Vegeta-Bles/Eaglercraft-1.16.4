import com.mojang.serialization.Codec;
import java.util.Random;

public class cin extends cjl<cly> {
   public cin(Codec<cly> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cly var5) {
      boolean _snowman = false;
      int _snowmanx = _snowman.c.a(_snowman);

      for (int _snowmanxx = _snowman.u() - _snowmanx; _snowmanxx <= _snowman.u() + _snowmanx; _snowmanxx++) {
         for (int _snowmanxxx = _snowman.w() - _snowmanx; _snowmanxxx <= _snowman.w() + _snowmanx; _snowmanxxx++) {
            int _snowmanxxxx = _snowmanxx - _snowman.u();
            int _snowmanxxxxx = _snowmanxxx - _snowman.w();
            if (_snowmanxxxx * _snowmanxxxx + _snowmanxxxxx * _snowmanxxxxx <= _snowmanx * _snowmanx) {
               for (int _snowmanxxxxxx = _snowman.v() - _snowman.d; _snowmanxxxxxx <= _snowman.v() + _snowman.d; _snowmanxxxxxx++) {
                  fx _snowmanxxxxxxx = new fx(_snowmanxx, _snowmanxxxxxx, _snowmanxxx);
                  buo _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxxxxx).b();

                  for (ceh _snowmanxxxxxxxxx : _snowman.e) {
                     if (_snowmanxxxxxxxxx.a(_snowmanxxxxxxxx)) {
                        _snowman.a(_snowmanxxxxxxx, _snowman.b, 2);
                        _snowman = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      return _snowman;
   }
}
