public class byz extends can {
   protected byz(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.ng) {
         if (!_snowman.v) {
            gc _snowmanx = _snowman.b();
            gc _snowmanxx = _snowmanx.n() == gc.a.b ? _snowman.bZ().f() : _snowmanx;
            _snowman.a(null, _snowman, adq.lX, adr.e, 1.0F, 1.0F);
            _snowman.a(_snowman, bup.cU.n().a(bvb.a, _snowmanxx), 11);
            bcv _snowmanxxx = new bcv(
               _snowman, (double)_snowman.u() + 0.5 + (double)_snowmanxx.i() * 0.65, (double)_snowman.v() + 0.1, (double)_snowman.w() + 0.5 + (double)_snowmanxx.k() * 0.65, new bmb(bmd.nj, 4)
            );
            _snowmanxxx.n(0.05 * (double)_snowmanxx.i() + _snowman.t.nextDouble() * 0.02, 0.05, 0.05 * (double)_snowmanxx.k() + _snowman.t.nextDouble() * 0.02);
            _snowman.c(_snowmanxxx);
            _snowman.a(1, _snowman, var1x -> var1x.d(_snowman));
         }

         return aou.a(_snowman.v);
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public cam c() {
      return (cam)bup.dN;
   }

   @Override
   public btt d() {
      return (btt)bup.dL;
   }
}
