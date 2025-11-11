public class gy extends gx {
   public gy() {
   }

   @Override
   protected bmb a(fy var1, bmb var2) {
      brx _snowman = _snowman.h();
      if (!_snowman.s_()) {
         fx _snowmanx = _snowman.d().a(_snowman.e().c(bwa.a));
         this.a(a((aag)_snowman, _snowmanx) || b((aag)_snowman, _snowmanx));
         if (this.a() && _snowman.a(1, _snowman.u_(), null)) {
            _snowman.e(0);
         }
      }

      return _snowman;
   }

   private static boolean a(aag var0, fx var1) {
      ceh _snowman = _snowman.d_(_snowman);
      if (_snowman.a(aed.aj)) {
         int _snowmanx = _snowman.c(buk.b);
         if (_snowmanx >= 5) {
            _snowman.a(null, _snowman, adq.aH, adr.e, 1.0F, 1.0F);
            buk.a(_snowman, _snowman);
            ((buk)_snowman.b()).a(_snowman, _snowman, _snowman, null, ccg.b.b);
            return true;
         }
      }

      return false;
   }

   private static boolean b(aag var0, fx var1) {
      for (aqm _snowman : _snowman.a(aqm.class, new dci(_snowman), aqd.g)) {
         if (_snowman instanceof arb) {
            arb _snowmanx = (arb)_snowman;
            if (_snowmanx.K_()) {
               _snowmanx.a(adr.e);
               return true;
            }
         }
      }

      return false;
   }
}
