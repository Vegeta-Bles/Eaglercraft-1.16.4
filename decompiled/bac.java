public class bac extends azz {
   private static final bon bv = bon.a(bmd.kV, bmd.nk, bmd.nj, bmd.qg);
   public float bo;
   public float bp;
   public float bq;
   public float br;
   public float bs = 1.0F;
   public int bt = this.J.nextInt(6000) + 6000;
   public boolean bu;

   public bac(aqe<? extends bac> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(cwz.h, 0.0F);
   }

   @Override
   protected void o() {
      this.bk.a(0, new avp(this));
      this.bk.a(1, new awp(this, 1.4));
      this.bk.a(2, new avi(this, 1.0));
      this.bk.a(3, new axf(this, 1.0, false, bv));
      this.bk.a(4, new avu(this, 1.1));
      this.bk.a(5, new axk(this, 1.0));
      this.bk.a(6, new awd(this, bfw.class, 6.0F));
      this.bk.a(7, new aws(this));
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return this.w_() ? _snowman.b * 0.85F : _snowman.b * 0.92F;
   }

   public static ark.a eK() {
      return aqn.p().a(arl.a, 4.0).a(arl.d, 0.25);
   }

   @Override
   public void k() {
      super.k();
      this.br = this.bo;
      this.bq = this.bp;
      this.bp = (float)((double)this.bp + (double)(this.t ? -1 : 4) * 0.3);
      this.bp = afm.a(this.bp, 0.0F, 1.0F);
      if (!this.t && this.bs < 1.0F) {
         this.bs = 1.0F;
      }

      this.bs = (float)((double)this.bs * 0.9);
      dcn _snowman = this.cC();
      if (!this.t && _snowman.c < 0.0) {
         this.f(_snowman.d(1.0, 0.6, 1.0));
      }

      this.bo = this.bo + this.bs * 2.0F;
      if (!this.l.v && this.aX() && !this.w_() && !this.eL() && --this.bt <= 0) {
         this.a(adq.bJ, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
         this.a(bmd.mg);
         this.bt = this.J.nextInt(6000) + 6000;
      }
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   protected adp I() {
      return adq.bH;
   }

   @Override
   protected adp e(apk var1) {
      return adq.bK;
   }

   @Override
   protected adp dq() {
      return adq.bI;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.bL, 0.15F, 1.0F);
   }

   public bac b(aag var1, apy var2) {
      return aqe.j.a(_snowman);
   }

   @Override
   public boolean k(bmb var1) {
      return bv.a(_snowman);
   }

   @Override
   protected int d(bfw var1) {
      return this.eL() ? 10 : super.d(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.bu = _snowman.q("IsChickenJockey");
      if (_snowman.e("EggLayTime")) {
         this.bt = _snowman.h("EggLayTime");
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("IsChickenJockey", this.bu);
      _snowman.b("EggLayTime", this.bt);
   }

   @Override
   public boolean h(double var1) {
      return this.eL();
   }

   @Override
   public void k(aqa var1) {
      super.k(_snowman);
      float _snowman = afm.a(this.aA * (float) (Math.PI / 180.0));
      float _snowmanx = afm.b(this.aA * (float) (Math.PI / 180.0));
      float _snowmanxx = 0.1F;
      float _snowmanxxx = 0.0F;
      _snowman.d(this.cD() + (double)(0.1F * _snowman), this.e(0.5) + _snowman.bb() + 0.0, this.cH() - (double)(0.1F * _snowmanx));
      if (_snowman instanceof aqm) {
         ((aqm)_snowman).aA = this.aA;
      }
   }

   public boolean eL() {
      return this.bu;
   }

   public void t(boolean var1) {
      this.bu = _snowman;
   }
}
