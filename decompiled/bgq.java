public class bgq extends bgs {
   public bgq(aqe<? extends bgq> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgq(brx var1, aqm var2) {
      super(aqe.aA, _snowman, _snowman);
   }

   public bgq(brx var1, double var2, double var4, double var6) {
      super(aqe.aA, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected blx h() {
      return bmd.lQ;
   }

   private hf m() {
      bmb _snowman = this.i();
      return (hf)(_snowman.a() ? hh.K : new he(hh.I, _snowman));
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 3) {
         hf _snowman = this.m();

         for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
            this.l.a(_snowman, this.cD(), this.cE(), this.cH(), 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      aqa _snowman = _snowman.a();
      int _snowmanx = _snowman instanceof bda ? 3 : 0;
      _snowman.a(apk.b(this, this.v()), (float)_snowmanx);
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      if (!this.l.v) {
         this.l.a(this, (byte)3);
         this.ad();
      }
   }
}
