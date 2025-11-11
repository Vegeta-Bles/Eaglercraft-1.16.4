public class bmj extends blx {
   private static final gw a = new gv() {
      private final gv b = new gv();

      @Override
      public bmb a(fy var1, bmb var2) {
         gc _snowman = _snowman.e().c(bwa.a);
         brx _snowmanx = _snowman.h();
         double _snowmanxx = _snowman.a() + (double)_snowman.i() * 1.125;
         double _snowmanxxx = Math.floor(_snowman.b()) + (double)_snowman.j();
         double _snowmanxxxx = _snowman.c() + (double)_snowman.k() * 1.125;
         fx _snowmanxxxxx = _snowman.d().a(_snowman);
         ceh _snowmanxxxxxx = _snowmanx.d_(_snowmanxxxxx);
         cfk _snowmanxxxxxxx = _snowmanxxxxxx.b() instanceof bug ? _snowmanxxxxxx.c(((bug)_snowmanxxxxxx.b()).d()) : cfk.a;
         double _snowmanxxxxxxxx;
         if (_snowmanxxxxxx.a(aed.H)) {
            if (_snowmanxxxxxxx.c()) {
               _snowmanxxxxxxxx = 0.6;
            } else {
               _snowmanxxxxxxxx = 0.1;
            }
         } else {
            if (!_snowmanxxxxxx.g() || !_snowmanx.d_(_snowmanxxxxx.c()).a(aed.H)) {
               return this.b.dispense(_snowman, _snowman);
            }

            ceh _snowmanxxxxxxxxx = _snowmanx.d_(_snowmanxxxxx.c());
            cfk _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.b() instanceof bug ? _snowmanxxxxxxxxx.c(((bug)_snowmanxxxxxxxxx.b()).d()) : cfk.a;
            if (_snowman != gc.a && _snowmanxxxxxxxxxx.c()) {
               _snowmanxxxxxxxx = -0.4;
            } else {
               _snowmanxxxxxxxx = -0.9;
            }
         }

         bhl _snowmanxxxxxxxxx = bhl.a(_snowmanx, _snowmanxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx, ((bmj)_snowman.b()).b);
         if (_snowman.t()) {
            _snowmanxxxxxxxxx.a(_snowman.r());
         }

         _snowmanx.c(_snowmanxxxxxxxxx);
         _snowman.g(1);
         return _snowman;
      }

      @Override
      protected void a(fy var1) {
         _snowman.h().c(1000, _snowman.d(), 0);
      }
   };
   private final bhl.a b;

   public bmj(bhl.a var1, blx.a var2) {
      super(_snowman);
      this.b = _snowman;
      bwa.a(this, a);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (!_snowmanxx.a(aed.H)) {
         return aou.d;
      } else {
         bmb _snowmanxxx = _snowman.m();
         if (!_snowman.v) {
            cfk _snowmanxxxx = _snowmanxx.b() instanceof bug ? _snowmanxx.c(((bug)_snowmanxx.b()).d()) : cfk.a;
            double _snowmanxxxxx = 0.0;
            if (_snowmanxxxx.c()) {
               _snowmanxxxxx = 0.5;
            }

            bhl _snowmanxxxxxx = bhl.a(_snowman, (double)_snowmanx.u() + 0.5, (double)_snowmanx.v() + 0.0625 + _snowmanxxxxx, (double)_snowmanx.w() + 0.5, this.b);
            if (_snowmanxxx.t()) {
               _snowmanxxxxxx.a(_snowmanxxx.r());
            }

            _snowman.c(_snowmanxxxxxx);
         }

         _snowmanxxx.g(1);
         return aou.a(_snowman.v);
      }
   }
}
