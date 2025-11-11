public class bll extends blx {
   public bll(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      boolean _snowmanxxx = false;
      if (buy.h(_snowmanxx)) {
         this.a(_snowman, _snowmanx);
         _snowman.a(_snowmanx, _snowmanxx.a(buy.b, Boolean.valueOf(true)));
         _snowmanxxx = true;
      } else {
         _snowmanx = _snowmanx.a(_snowman.j());
         if (bue.a(_snowman, _snowmanx, _snowman.f())) {
            this.a(_snowman, _snowmanx);
            _snowman.a(_snowmanx, bue.a(_snowman, _snowmanx));
            _snowmanxxx = true;
         }
      }

      if (_snowmanxxx) {
         _snowman.m().g(1);
         return aou.a(_snowman.v);
      } else {
         return aou.d;
      }
   }

   private void a(brx var1, fx var2) {
      _snowman.a(null, _snowman, adq.dZ, adr.e, 1.0F, (h.nextFloat() - h.nextFloat()) * 0.2F + 1.0F);
   }
}
