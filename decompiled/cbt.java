import java.util.Random;

public class cbt extends buo {
   protected cbt(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (_snowman.k().d()) {
         _snowman.a(_snowman, bup.an.n(), 3);
         _snowman.c(2009, _snowman, 0);
         _snowman.a(null, _snowman, adq.ej, adr.e, 1.0F, (1.0F + _snowman.u_().nextFloat() * 0.2F) * 0.7F);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      gc _snowman = gc.a(_snowman);
      if (_snowman != gc.b) {
         fx _snowmanx = _snowman.a(_snowman);
         ceh _snowmanxx = _snowman.d_(_snowmanx);
         if (!_snowman.l() || !_snowmanxx.d(_snowman, _snowmanx, _snowman.f())) {
            double _snowmanxxx = (double)_snowman.u();
            double _snowmanxxxx = (double)_snowman.v();
            double _snowmanxxxxx = (double)_snowman.w();
            if (_snowman == gc.a) {
               _snowmanxxxx -= 0.05;
               _snowmanxxx += _snowman.nextDouble();
               _snowmanxxxxx += _snowman.nextDouble();
            } else {
               _snowmanxxxx += _snowman.nextDouble() * 0.8;
               if (_snowman.n() == gc.a.a) {
                  _snowmanxxxxx += _snowman.nextDouble();
                  if (_snowman == gc.f) {
                     _snowmanxxx++;
                  } else {
                     _snowmanxxx += 0.05;
                  }
               } else {
                  _snowmanxxx += _snowman.nextDouble();
                  if (_snowman == gc.d) {
                     _snowmanxxxxx++;
                  } else {
                     _snowmanxxxxx += 0.05;
                  }
               }
            }

            _snowman.a(hh.m, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
         }
      }
   }
}
