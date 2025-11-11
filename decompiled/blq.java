public class blq extends blx {
   public blq(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      bfw _snowman = _snowman.n();
      brx _snowmanx = _snowman.p();
      fx _snowmanxx = _snowman.a();
      ceh _snowmanxxx = _snowmanx.d_(_snowmanxx);
      if (buy.h(_snowmanxxx)) {
         _snowmanx.a(_snowman, _snowmanxx, adq.eo, adr.e, 1.0F, h.nextFloat() * 0.4F + 0.8F);
         _snowmanx.a(_snowmanxx, _snowmanxxx.a(cex.r, Boolean.valueOf(true)), 11);
         if (_snowman != null) {
            _snowman.m().a(1, _snowman, var1x -> var1x.d(_snowman.o()));
         }

         return aou.a(_snowmanx.s_());
      } else {
         fx _snowmanxxxx = _snowmanxx.a(_snowman.j());
         if (bue.a(_snowmanx, _snowmanxxxx, _snowman.f())) {
            _snowmanx.a(_snowman, _snowmanxxxx, adq.eo, adr.e, 1.0F, h.nextFloat() * 0.4F + 0.8F);
            ceh _snowmanxxxxx = bue.a(_snowmanx, _snowmanxxxx);
            _snowmanx.a(_snowmanxxxx, _snowmanxxxxx, 11);
            bmb _snowmanxxxxxx = _snowman.m();
            if (_snowman instanceof aah) {
               ac.y.a((aah)_snowman, _snowmanxxxx, _snowmanxxxxxx);
               _snowmanxxxxxx.a(1, _snowman, var1x -> var1x.d(_snowman.o()));
            }

            return aou.a(_snowmanx.s_());
         } else {
            return aou.d;
         }
      }
   }
}
