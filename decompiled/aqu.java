public abstract class aqu extends aqn {
   protected aqu(aqe<? extends aqu> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public float f(fx var1) {
      return this.a(_snowman, this.l);
   }

   public float a(fx var1, brz var2) {
      return 0.0F;
   }

   @Override
   public boolean a(bry var1, aqp var2) {
      return this.a(this.cB(), _snowman) >= 0.0F;
   }

   public boolean eI() {
      return !this.x().m();
   }

   @Override
   protected void eA() {
      super.eA();
      aqa _snowman = this.eC();
      if (_snowman != null && _snowman.l == this.l) {
         this.a(_snowman.cB(), 5);
         float _snowmanx = this.g(_snowman);
         if (this instanceof are && ((are)this).eM()) {
            if (_snowmanx > 10.0F) {
               this.a(true, true);
            }

            return;
         }

         this.x(_snowmanx);
         if (_snowmanx > 10.0F) {
            this.a(true, true);
            this.bk.a(avv.a.a);
         } else if (_snowmanx > 6.0F) {
            double _snowmanxx = (_snowman.cD() - this.cD()) / (double)_snowmanx;
            double _snowmanxxx = (_snowman.cE() - this.cE()) / (double)_snowmanx;
            double _snowmanxxxx = (_snowman.cH() - this.cH()) / (double)_snowmanx;
            this.f(this.cC().b(Math.copySign(_snowmanxx * _snowmanxx * 0.4, _snowmanxx), Math.copySign(_snowmanxxx * _snowmanxxx * 0.4, _snowmanxxx), Math.copySign(_snowmanxxxx * _snowmanxxxx * 0.4, _snowmanxxxx)));
         } else {
            this.bk.b(avv.a.a);
            float _snowmanxx = 2.0F;
            dcn _snowmanxxx = new dcn(_snowman.cD() - this.cD(), _snowman.cE() - this.cE(), _snowman.cH() - this.cH()).d().a((double)Math.max(_snowmanx - 2.0F, 0.0F));
            this.x().a(this.cD() + _snowmanxxx.b, this.cE() + _snowmanxxx.c, this.cH() + _snowmanxxx.d, this.eJ());
         }
      }
   }

   protected double eJ() {
      return 1.0;
   }

   protected void x(float var1) {
   }
}
