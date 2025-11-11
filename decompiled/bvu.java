import java.util.Random;

public class bvu extends buo {
   public bvu(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.nextInt(5) == 0) {
         gc _snowman = gc.a(_snowman);
         if (_snowman != gc.b) {
            fx _snowmanx = _snowman.a(_snowman);
            ceh _snowmanxx = _snowman.d_(_snowmanx);
            if (!_snowman.l() || !_snowmanxx.d(_snowman, _snowmanx, _snowman.f())) {
               double _snowmanxxx = _snowman.i() == 0 ? _snowman.nextDouble() : 0.5 + (double)_snowman.i() * 0.6;
               double _snowmanxxxx = _snowman.j() == 0 ? _snowman.nextDouble() : 0.5 + (double)_snowman.j() * 0.6;
               double _snowmanxxxxx = _snowman.k() == 0 ? _snowman.nextDouble() : 0.5 + (double)_snowman.k() * 0.6;
               _snowman.a(hh.ap, (double)_snowman.u() + _snowmanxxx, (double)_snowman.v() + _snowmanxxxx, (double)_snowman.w() + _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }
      }
   }
}
