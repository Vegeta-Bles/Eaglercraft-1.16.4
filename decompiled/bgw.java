public class bgw extends bgs {
   public bgw(aqe<? extends bgw> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgw(brx var1, aqm var2) {
      super(aqe.aI, _snowman, _snowman);
   }

   public bgw(brx var1, double var2, double var4, double var6) {
      super(aqe.aI, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected blx h() {
      return bmd.oR;
   }

   @Override
   protected float k() {
      return 0.07F;
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      if (!this.l.v) {
         this.l.c(2002, this.cB(), bnv.a(bnw.b));
         int _snowman = 3 + this.l.t.nextInt(5) + this.l.t.nextInt(5);

         while (_snowman > 0) {
            int _snowmanx = aqg.a(_snowman);
            _snowman -= _snowmanx;
            this.l.c(new aqg(this.l, this.cD(), this.cE(), this.cH(), _snowmanx));
         }

         this.ad();
      }
   }
}
