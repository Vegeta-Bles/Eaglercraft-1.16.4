import com.mojang.serialization.Codec;
import java.util.Random;

public class ckl extends cjl<cmn> {
   public ckl(Codec<cmn> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmn var5) {
      ceh _snowman = _snowman.b.a(_snowman, _snowman);
      fx _snowmanx;
      if (_snowman.l) {
         _snowmanx = _snowman.a(chn.a.a, _snowman);
      } else {
         _snowmanx = _snowman;
      }

      int _snowmanxx = 0;
      fx.a _snowmanxxx = new fx.a();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.f; _snowmanxxxx++) {
         _snowmanxxx.a(_snowmanx, _snowman.nextInt(_snowman.g + 1) - _snowman.nextInt(_snowman.g + 1), _snowman.nextInt(_snowman.h + 1) - _snowman.nextInt(_snowman.h + 1), _snowman.nextInt(_snowman.i + 1) - _snowman.nextInt(_snowman.i + 1));
         fx _snowmanxxxxx = _snowmanxxx.c();
         ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
         if ((_snowman.w(_snowmanxxx) || _snowman.j && _snowman.d_(_snowmanxxx).c().e())
            && _snowman.a(_snowman, _snowmanxxx)
            && (_snowman.d.isEmpty() || _snowman.d.contains(_snowmanxxxxxx.b()))
            && !_snowman.e.contains(_snowmanxxxxxx)
            && (!_snowman.m || _snowman.b(_snowmanxxxxx.f()).a(aef.b) || _snowman.b(_snowmanxxxxx.g()).a(aef.b) || _snowman.b(_snowmanxxxxx.d()).a(aef.b) || _snowman.b(_snowmanxxxxx.e()).a(aef.b))) {
            _snowman.c.a(_snowman, _snowmanxxx, _snowman, _snowman);
            _snowmanxx++;
         }
      }

      return _snowmanxx > 0;
   }
}
