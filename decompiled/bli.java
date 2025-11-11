public class bli extends blx {
   public bli(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (!_snowmanxx.a(bup.ed) || _snowmanxx.c(bwj.b)) {
         return aou.c;
      } else if (_snowman.v) {
         return aou.a;
      } else {
         ceh _snowmanxxx = _snowmanxx.a(bwj.b, Boolean.valueOf(true));
         buo.a(_snowmanxx, _snowmanxxx, _snowman, _snowmanx);
         _snowman.a(_snowmanx, _snowmanxxx, 2);
         _snowman.c(_snowmanx, bup.ed);
         _snowman.m().g(1);
         _snowman.c(1503, _snowmanx, 0);
         cem.b _snowmanxxxx = bwj.c().a(_snowman, _snowmanx);
         if (_snowmanxxxx != null) {
            fx _snowmanxxxxx = _snowmanxxxx.a().b(-3, 0, -3);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 3; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 3; _snowmanxxxxxxx++) {
                  _snowman.a(_snowmanxxxxx.b(_snowmanxxxxxx, 0, _snowmanxxxxxxx), bup.ec.n(), 2);
               }
            }

            _snowman.b(1038, _snowmanxxxxx.b(1, 0, 1), 0);
         }

         return aou.b;
      }
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      dcl _snowmanx = a(_snowman, _snowman, brf.b.a);
      if (_snowmanx.c() == dcl.a.b && _snowman.d_(((dcj)_snowmanx).a()).a(bup.ed)) {
         return aov.c(_snowman);
      } else {
         _snowman.c(_snowman);
         if (_snowman instanceof aag) {
            fx _snowmanxx = ((aag)_snowman).i().g().a((aag)_snowman, cla.k, _snowman.cB(), 100, false);
            if (_snowmanxx != null) {
               bgf _snowmanxxx = new bgf(_snowman, _snowman.cD(), _snowman.e(0.5), _snowman.cH());
               _snowmanxxx.b(_snowman);
               _snowmanxxx.a(_snowmanxx);
               _snowman.c(_snowmanxxx);
               if (_snowman instanceof aah) {
                  ac.m.a((aah)_snowman, _snowmanxx);
               }

               _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.dx, adr.g, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
               _snowman.a(null, 1003, _snowman.cB(), 0);
               if (!_snowman.bC.d) {
                  _snowman.g(1);
               }

               _snowman.b(aea.c.b(this));
               _snowman.a(_snowman, true);
               return aov.a(_snowman);
            }
         }

         return aov.b(_snowman);
      }
   }
}
