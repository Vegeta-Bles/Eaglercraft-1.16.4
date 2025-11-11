public class bgl extends bgm {
   public bgl(aqe<? extends bgl> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgl(brx var1, bbe var2) {
      this(aqe.R, _snowman);
      super.b(_snowman);
      this.d(
         _snowman.cD() - (double)(_snowman.cy() + 1.0F) * 0.5 * (double)afm.a(_snowman.aA * (float) (Math.PI / 180.0)),
         _snowman.cG() - 0.1F,
         _snowman.cH() + (double)(_snowman.cy() + 1.0F) * 0.5 * (double)afm.b(_snowman.aA * (float) (Math.PI / 180.0))
      );
   }

   public bgl(brx var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(aqe.R, _snowman);
      this.d(_snowman, _snowman, _snowman);

      for (int _snowman = 0; _snowman < 7; _snowman++) {
         double _snowmanx = 0.4 + 0.1 * (double)_snowman;
         _snowman.a(hh.U, _snowman, _snowman, _snowman, _snowman * _snowmanx, _snowman, _snowman * _snowmanx);
      }

      this.n(_snowman, _snowman, _snowman);
   }

   @Override
   public void j() {
      super.j();
      dcn _snowman = this.cC();
      dcl _snowmanx = bgn.a(this, this::a);
      if (_snowmanx != null) {
         this.a(_snowmanx);
      }

      double _snowmanxx = this.cD() + _snowman.b;
      double _snowmanxxx = this.cE() + _snowman.c;
      double _snowmanxxxx = this.cH() + _snowman.d;
      this.x();
      float _snowmanxxxxx = 0.99F;
      float _snowmanxxxxxx = 0.06F;
      if (this.l.a(this.cc()).noneMatch(ceg.a::g)) {
         this.ad();
      } else if (this.aH()) {
         this.ad();
      } else {
         this.f(_snowman.a(0.99F));
         if (!this.aB()) {
            this.f(this.cC().b(0.0, -0.06F, 0.0));
         }

         this.d(_snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      aqa _snowman = this.v();
      if (_snowman instanceof aqm) {
         _snowman.a().a(apk.a(this, (aqm)_snowman).c(), 1.0F);
      }
   }

   @Override
   protected void a(dcj var1) {
      super.a(_snowman);
      if (!this.l.v) {
         this.ad();
      }
   }

   @Override
   protected void e() {
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
