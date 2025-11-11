public class bbi extends avv {
   private final bbh a;

   public bbi(bbh var1) {
      this.a = _snowman;
   }

   @Override
   public boolean a() {
      return this.a.l.a(this.a.cD(), this.a.cE(), this.a.cH(), 10.0);
   }

   @Override
   public void e() {
      aag _snowman = (aag)this.a.l;
      aos _snowmanx = _snowman.d(this.a.cB());
      this.a.t(false);
      this.a.u(true);
      this.a.c_(0);
      aql _snowmanxx = aqe.P.a(_snowman);
      _snowmanxx.b(this.a.cD(), this.a.cE(), this.a.cH());
      _snowmanxx.a(true);
      _snowman.c(_snowmanxx);
      bdy _snowmanxxx = this.a(_snowmanx, this.a);
      _snowmanxxx.m(this.a);
      _snowman.l(_snowmanxxx);

      for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
         bbb _snowmanxxxxx = this.a(_snowmanx);
         bdy _snowmanxxxxxx = this.a(_snowmanx, _snowmanxxxxx);
         _snowmanxxxxxx.m(_snowmanxxxxx);
         _snowmanxxxxx.i(this.a.cY().nextGaussian() * 0.5, 0.0, this.a.cY().nextGaussian() * 0.5);
         _snowman.l(_snowmanxxxxx);
      }
   }

   private bbb a(aos var1) {
      bbh _snowman = aqe.aw.a(this.a.l);
      _snowman.a((aag)this.a.l, _snowman, aqp.k, null, null);
      _snowman.d(this.a.cD(), this.a.cE(), this.a.cH());
      _snowman.P = 60;
      _snowman.es();
      _snowman.u(true);
      _snowman.c_(0);
      return _snowman;
   }

   private bdy a(aos var1, bbb var2) {
      bdy _snowman = aqe.av.a(_snowman.l);
      _snowman.a((aag)_snowman.l, _snowman, aqp.k, null, null);
      _snowman.d(_snowman.cD(), _snowman.cE(), _snowman.cH());
      _snowman.P = 60;
      _snowman.es();
      if (_snowman.b(aqf.f).a()) {
         _snowman.a(aqf.f, new bmb(bmd.lg));
      }

      _snowman.a(aqf.a, bpu.a(_snowman.cY(), this.a(_snowman.dD()), (int)(5.0F + _snowman.d() * (float)_snowman.cY().nextInt(18)), false));
      _snowman.a(aqf.f, bpu.a(_snowman.cY(), this.a(_snowman.b(aqf.f)), (int)(5.0F + _snowman.d() * (float)_snowman.cY().nextInt(18)), false));
      return _snowman;
   }

   private bmb a(bmb var1) {
      _snowman.c("Enchantments");
      return _snowman;
   }
}
