import com.mojang.serialization.Codec;
import java.util.Random;

public class cik extends cjl<cmk> {
   private static final ceh a = bup.kY.n().a(btu.d, Integer.valueOf(1)).a(btu.e, ceu.a).a(btu.f, Integer.valueOf(0));
   private static final ceh ab = a.a(btu.e, ceu.c).a(btu.f, Integer.valueOf(1));
   private static final ceh ac = a.a(btu.e, ceu.c);
   private static final ceh ad = a.a(btu.e, ceu.b);

   public cik(Codec<cmk> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmk var5) {
      int _snowman = 0;
      fx.a _snowmanx = _snowman.i();
      fx.a _snowmanxx = _snowman.i();
      if (_snowman.w(_snowmanx)) {
         if (bup.kY.n().a(_snowman, _snowmanx)) {
            int _snowmanxxx = _snowman.nextInt(12) + 5;
            if (_snowman.nextFloat() < _snowman.c) {
               int _snowmanxxxx = _snowman.nextInt(4) + 1;

               for (int _snowmanxxxxx = _snowman.u() - _snowmanxxxx; _snowmanxxxxx <= _snowman.u() + _snowmanxxxx; _snowmanxxxxx++) {
                  for (int _snowmanxxxxxx = _snowman.w() - _snowmanxxxx; _snowmanxxxxxx <= _snowman.w() + _snowmanxxxx; _snowmanxxxxxx++) {
                     int _snowmanxxxxxxx = _snowmanxxxxx - _snowman.u();
                     int _snowmanxxxxxxxx = _snowmanxxxxxx - _snowman.w();
                     if (_snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx <= _snowmanxxxx * _snowmanxxxx) {
                        _snowmanxx.d(_snowmanxxxxx, _snowman.a(chn.a.b, _snowmanxxxxx, _snowmanxxxxxx) - 1, _snowmanxxxxxx);
                        if (b(_snowman.d_(_snowmanxx).b())) {
                           _snowman.a(_snowmanxx, bup.l.n(), 2);
                        }
                     }
                  }
               }
            }

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx && _snowman.w(_snowmanx); _snowmanxxxx++) {
               _snowman.a(_snowmanx, a, 2);
               _snowmanx.c(gc.b, 1);
            }

            if (_snowmanx.v() - _snowman.v() >= 3) {
               _snowman.a(_snowmanx, ab, 2);
               _snowman.a(_snowmanx.c(gc.a, 1), ac, 2);
               _snowman.a(_snowmanx.c(gc.a, 1), ad, 2);
            }
         }

         _snowman++;
      }

      return _snowman > 0;
   }
}
