public class bwf extends bwa {
   private static final gw c = new gv();

   public bwf(ceg.c var1) {
      super(_snowman);
   }

   @Override
   protected gw a(bmb var1) {
      return c;
   }

   @Override
   public ccj a(brc var1) {
      return new cct();
   }

   @Override
   protected void a(aag var1, fx var2) {
      fz _snowman = new fz(_snowman, _snowman);
      ccs _snowmanx = _snowman.g();
      int _snowmanxx = _snowmanx.h();
      if (_snowmanxx < 0) {
         _snowman.c(1001, _snowman, 0);
      } else {
         bmb _snowmanxxx = _snowmanx.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            gc _snowmanxxxx = _snowman.d_(_snowman).c(a);
            aon _snowmanxxxxx = ccy.b(_snowman, _snowman.a(_snowmanxxxx));
            bmb _snowmanxxxxxx;
            if (_snowmanxxxxx == null) {
               _snowmanxxxxxx = c.dispense(_snowman, _snowmanxxx);
            } else {
               _snowmanxxxxxx = ccy.a(_snowmanx, _snowmanxxxxx, _snowmanxxx.i().a(1), _snowmanxxxx.f());
               if (_snowmanxxxxxx.a()) {
                  _snowmanxxxxxx = _snowmanxxx.i();
                  _snowmanxxxxxx.g(1);
               } else {
                  _snowmanxxxxxx = _snowmanxxx.i();
               }
            }

            _snowmanx.a(_snowmanxx, _snowmanxxxxxx);
         }
      }
   }
}
