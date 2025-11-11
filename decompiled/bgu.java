public class bgu extends bgs {
   public bgu(aqe<? extends bgu> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgu(brx var1, aqm var2) {
      super(aqe.aG, _snowman, _snowman);
   }

   public bgu(brx var1, double var2, double var4, double var6) {
      super(aqe.aG, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 3) {
         double _snowman = 0.08;

         for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
            this.l
               .a(
                  new he(hh.I, this.g()),
                  this.cD(),
                  this.cE(),
                  this.cH(),
                  ((double)this.J.nextFloat() - 0.5) * 0.08,
                  ((double)this.J.nextFloat() - 0.5) * 0.08,
                  ((double)this.J.nextFloat() - 0.5) * 0.08
               );
         }
      }
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      _snowman.a().a(apk.b(this, this.v()), 0.0F);
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      if (!this.l.v) {
         if (this.J.nextInt(8) == 0) {
            int _snowman = 1;
            if (this.J.nextInt(32) == 0) {
               _snowman = 4;
            }

            for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
               bac _snowmanxx = aqe.j.a(this.l);
               _snowmanxx.c_(-24000);
               _snowmanxx.b(this.cD(), this.cE(), this.cH(), this.p, 0.0F);
               this.l.c(_snowmanxx);
            }
         }

         this.l.a(this, (byte)3);
         this.ad();
      }
   }

   @Override
   protected blx h() {
      return bmd.mg;
   }
}
