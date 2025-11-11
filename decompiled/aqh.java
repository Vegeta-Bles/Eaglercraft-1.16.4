public abstract class aqh extends aqn {
   protected aqh(aqe<? extends aqh> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
   }

   @Override
   public void g(dcn var1) {
      if (this.aE()) {
         this.a(0.02F, _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a(0.8F));
      } else if (this.aQ()) {
         this.a(0.02F, _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a(0.5));
      } else {
         float _snowman = 0.91F;
         if (this.t) {
            _snowman = this.l.d_(new fx(this.cD(), this.cE() - 1.0, this.cH())).b().j() * 0.91F;
         }

         float _snowmanx = 0.16277137F / (_snowman * _snowman * _snowman);
         _snowman = 0.91F;
         if (this.t) {
            _snowman = this.l.d_(new fx(this.cD(), this.cE() - 1.0, this.cH())).b().j() * 0.91F;
         }

         this.a(this.t ? 0.1F * _snowmanx : 0.02F, _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a((double)_snowman));
      }

      this.a(this, false);
   }

   @Override
   public boolean c_() {
      return false;
   }
}
