public class bpz extends bps {
   public bpz(bps.a var1, aqf... var2) {
      super(_snowman, bpt.b, _snowman);
   }

   @Override
   public int a(int var1) {
      return _snowman * 10;
   }

   @Override
   public int b(int var1) {
      return this.a(_snowman) + 15;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public int a() {
      return 2;
   }

   public static void a(aqm var0, brx var1, fx var2, int var3) {
      if (_snowman.ao()) {
         ceh _snowman = bup.iI.n();
         float _snowmanx = (float)Math.min(16, 2 + _snowman);
         fx.a _snowmanxx = new fx.a();

         for (fx _snowmanxxx : fx.a(_snowman.a((double)(-_snowmanx), -1.0, (double)(-_snowmanx)), _snowman.a((double)_snowmanx, -1.0, (double)_snowmanx))) {
            if (_snowmanxxx.a(_snowman.cA(), (double)_snowmanx)) {
               _snowmanxx.d(_snowmanxxx.u(), _snowmanxxx.v() + 1, _snowmanxxx.w());
               ceh _snowmanxxxx = _snowman.d_(_snowmanxx);
               if (_snowmanxxxx.g()) {
                  ceh _snowmanxxxxx = _snowman.d_(_snowmanxxx);
                  if (_snowmanxxxxx.c() == cva.j && _snowmanxxxxx.c(byb.a) == 0 && _snowman.a((brz)_snowman, _snowmanxxx) && _snowman.a(_snowman, _snowmanxxx, dcs.a())) {
                     _snowman.a(_snowmanxxx, _snowman);
                     _snowman.J().a(_snowmanxxx, bup.iI, afm.a(_snowman.cY(), 60, 120));
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean a(bps var1) {
      return super.a(_snowman) && _snowman != bpw.i;
   }
}
