import java.util.Random;

public class bxx extends buo {
   public static final cfg a = cex.an;
   public static final cey b = cex.v;

   public bxx(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(7)).a(b, Boolean.valueOf(false)));
   }

   @Override
   public ddh e(ceh var1, brc var2, fx var3) {
      return dde.a();
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(a) == 7 && !_snowman.c(b);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.c(b) && _snowman.c(a) == 7) {
         c(_snowman, _snowman, _snowman);
         _snowman.a(_snowman, false);
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      _snowman.a(_snowman, a(_snowman, _snowman, _snowman), 3);
   }

   @Override
   public int f(ceh var1, brc var2, fx var3) {
      return 1;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      int _snowman = h(_snowman) + 1;
      if (_snowman != 1 || _snowman.c(a) != _snowman) {
         _snowman.J().a(_snowman, this, 1);
      }

      return _snowman;
   }

   private static ceh a(ceh var0, bry var1, fx var2) {
      int _snowman = 7;
      fx.a _snowmanx = new fx.a();

      for (gc _snowmanxx : gc.values()) {
         _snowmanx.a(_snowman, _snowmanxx);
         _snowman = Math.min(_snowman, h(_snowman.d_(_snowmanx)) + 1);
         if (_snowman == 1) {
            break;
         }
      }

      return _snowman.a(a, Integer.valueOf(_snowman));
   }

   private static int h(ceh var0) {
      if (aed.s.a(_snowman.b())) {
         return 0;
      } else {
         return _snowman.b() instanceof bxx ? _snowman.c(a) : 7;
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.t(_snowman.b())) {
         if (_snowman.nextInt(15) == 1) {
            fx _snowman = _snowman.c();
            ceh _snowmanx = _snowman.d_(_snowman);
            if (!_snowmanx.l() || !_snowmanx.d(_snowman, _snowman, gc.b)) {
               double _snowmanxx = (double)_snowman.u() + _snowman.nextDouble();
               double _snowmanxxx = (double)_snowman.v() - 0.05;
               double _snowmanxxxx = (double)_snowman.w() + _snowman.nextDouble();
               _snowman.a(hh.m, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   @Override
   public ceh a(bny var1) {
      return a(this.n().a(b, Boolean.valueOf(true)), _snowman.p(), _snowman.a());
   }
}
