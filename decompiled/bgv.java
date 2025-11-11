import javax.annotation.Nullable;

public class bgv extends bgs {
   public bgv(aqe<? extends bgv> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgv(brx var1, aqm var2) {
      super(aqe.aH, _snowman, _snowman);
   }

   public bgv(brx var1, double var2, double var4, double var6) {
      super(aqe.aH, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected blx h() {
      return bmd.nq;
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      _snowman.a().a(apk.b(this, this.v()), 0.0F);
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      aqa _snowman = this.v();

      for (int _snowmanx = 0; _snowmanx < 32; _snowmanx++) {
         this.l.a(hh.Q, this.cD(), this.cE() + this.J.nextDouble() * 2.0, this.cH(), this.J.nextGaussian(), 0.0, this.J.nextGaussian());
      }

      if (!this.l.v && !this.y) {
         if (_snowman instanceof aah) {
            aah _snowmanx = (aah)_snowman;
            if (_snowmanx.b.a().h() && _snowmanx.l == this.l && !_snowmanx.em()) {
               if (this.J.nextFloat() < 0.05F && this.l.V().b(brt.d)) {
                  bdh _snowmanxx = aqe.v.a(this.l);
                  _snowmanxx.t(true);
                  _snowmanxx.b(_snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman.p, _snowman.q);
                  this.l.c(_snowmanxx);
               }

               if (_snowman.br()) {
                  _snowman.l();
               }

               _snowman.a(this.cD(), this.cE(), this.cH());
               _snowman.C = 0.0F;
               _snowman.a(apk.k, 5.0F);
            }
         } else if (_snowman != null) {
            _snowman.a(this.cD(), this.cE(), this.cH());
            _snowman.C = 0.0F;
         }

         this.ad();
      }
   }

   @Override
   public void j() {
      aqa _snowman = this.v();
      if (_snowman instanceof bfw && !_snowman.aX()) {
         this.ad();
      } else {
         super.j();
      }
   }

   @Nullable
   @Override
   public aqa b(aag var1) {
      aqa _snowman = this.v();
      if (_snowman != null && _snowman.l.Y() != _snowman.Y()) {
         this.b(null);
      }

      return super.b(_snowman);
   }
}
