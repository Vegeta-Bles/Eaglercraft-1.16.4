import java.util.Random;

public abstract class azw extends bay {
   private static final us<Boolean> b = uv.a(azw.class, uu.i);

   public azw(aqe<? extends azw> var1, brx var2) {
      super(_snowman, _snowman);
      this.bh = new azw.a(this);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.65F;
   }

   public static ark.a m() {
      return aqn.p().a(arl.a, 3.0);
   }

   @Override
   public boolean K() {
      return super.K() || this.eN();
   }

   public static boolean b(aqe<? extends azw> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.d_(_snowman).a(bup.A) && _snowman.d_(_snowman.b()).a(bup.A);
   }

   @Override
   public boolean h(double var1) {
      return !this.eN() && !this.S();
   }

   @Override
   public int eq() {
      return 8;
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, false);
   }

   private boolean eN() {
      return this.R.a(b);
   }

   public void t(boolean var1) {
      this.R.b(b, _snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("FromBucket", this.eN());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.q("FromBucket"));
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new awp(this, 1.25));
      this.bk.a(2, new avd<>(this, bfw.class, 8.0F, 1.6, 1.4, aqd.g::test));
      this.bk.a(4, new azw.b(this));
   }

   @Override
   protected ayj b(brx var1) {
      return new ayl(this, _snowman);
   }

   @Override
   public void g(dcn var1) {
      if (this.dS() && this.aE()) {
         this.a(0.01F, _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a(0.9));
         if (this.A() == null) {
            this.f(this.cC().b(0.0, -0.005, 0.0));
         }
      } else {
         super.g(_snowman);
      }
   }

   @Override
   public void k() {
      if (!this.aE() && this.t && this.v) {
         this.f(this.cC().b((double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4F, (double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.05F)));
         this.t = false;
         this.Z = true;
         this.a(this.eM(), this.dG(), this.dH());
      }

      super.k();
   }

   @Override
   protected aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.lL && this.aX()) {
         this.a(adq.bn, 1.0F, 1.0F);
         _snowman.g(1);
         bmb _snowmanx = this.eK();
         this.k(_snowmanx);
         if (!this.l.v) {
            ac.j.a((aah)_snowman, _snowmanx);
         }

         if (_snowman.a()) {
            _snowman.a(_snowman, _snowmanx);
         } else if (!_snowman.bm.e(_snowmanx)) {
            _snowman.a(_snowmanx, false);
         }

         this.ad();
         return aou.a(this.l.v);
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   protected void k(bmb var1) {
      if (this.S()) {
         _snowman.a(this.T());
      }
   }

   protected abstract bmb eK();

   protected boolean eL() {
      return true;
   }

   protected abstract adp eM();

   @Override
   protected adp av() {
      return adq.ek;
   }

   @Override
   protected void b(fx var1, ceh var2) {
   }

   static class a extends avb {
      private final azw i;

      a(azw var1) {
         super(_snowman);
         this.i = _snowman;
      }

      @Override
      public void a() {
         if (this.i.a(aef.b)) {
            this.i.f(this.i.cC().b(0.0, 0.005, 0.0));
         }

         if (this.h == avb.a.b && !this.i.x().m()) {
            float _snowman = (float)(this.e * this.i.b(arl.d));
            this.i.q(afm.g(0.125F, this.i.dN(), _snowman));
            double _snowmanx = this.b - this.i.cD();
            double _snowmanxx = this.c - this.i.cE();
            double _snowmanxxx = this.d - this.i.cH();
            if (_snowmanxx != 0.0) {
               double _snowmanxxxx = (double)afm.a(_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
               this.i.f(this.i.cC().b(0.0, (double)this.i.dN() * (_snowmanxx / _snowmanxxxx) * 0.1, 0.0));
            }

            if (_snowmanx != 0.0 || _snowmanxxx != 0.0) {
               float _snowmanxxxx = (float)(afm.d(_snowmanxxx, _snowmanx) * 180.0F / (float)Math.PI) - 90.0F;
               this.i.p = this.a(this.i.p, _snowmanxxxx, 90.0F);
               this.i.aA = this.i.p;
            }
         } else {
            this.i.q(0.0F);
         }
      }
   }

   static class b extends awu {
      private final azw h;

      public b(azw var1) {
         super(_snowman, 1.0, 40);
         this.h = _snowman;
      }

      @Override
      public boolean a() {
         return this.h.eL() && super.a();
      }
   }
}
