public class bae extends azz {
   public bae(aqe<? extends bae> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      this.bk.a(0, new avp(this));
      this.bk.a(1, new awp(this, 2.0));
      this.bk.a(2, new avi(this, 1.0));
      this.bk.a(3, new axf(this, 1.25, bon.a(bmd.kW), false));
      this.bk.a(4, new avu(this, 1.25));
      this.bk.a(5, new axk(this, 1.0));
      this.bk.a(6, new awd(this, bfw.class, 6.0F));
      this.bk.a(7, new aws(this));
   }

   public static ark.a eK() {
      return aqn.p().a(arl.a, 10.0).a(arl.d, 0.2F);
   }

   @Override
   protected adp I() {
      return adq.ci;
   }

   @Override
   protected adp e(apk var1) {
      return adq.ck;
   }

   @Override
   protected adp dq() {
      return adq.cj;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.cm, 0.15F, 1.0F);
   }

   @Override
   protected float dG() {
      return 0.4F;
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.lK && !this.w_()) {
         _snowman.a(adq.cl, 1.0F, 1.0F);
         bmb _snowmanx = bmc.a(_snowman, _snowman, bmd.lT.r());
         _snowman.a(_snowman, _snowmanx);
         return aou.a(this.l.v);
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   public bae b(aag var1, apy var2) {
      return aqe.l.a(_snowman);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return this.w_() ? _snowman.b * 0.95F : 1.3F;
   }
}
