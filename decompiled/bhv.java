public class bhv extends bhl {
   private int b = -1;

   public bhv(aqe<? extends bhv> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bhv(brx var1, double var2, double var4, double var6) {
      super(aqe.Z, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public bhl.a o() {
      return bhl.a.d;
   }

   @Override
   public ceh q() {
      return bup.bH.n();
   }

   @Override
   public void j() {
      super.j();
      if (this.b > 0) {
         this.b--;
         this.l.a(hh.S, this.cD(), this.cE() + 0.5, this.cH(), 0.0, 0.0, 0.0);
      } else if (this.b == 0) {
         this.h(c(this.cC()));
      }

      if (this.u) {
         double _snowman = c(this.cC());
         if (_snowman >= 0.01F) {
            this.h(_snowman);
         }
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      aqa _snowman = _snowman.j();
      if (_snowman instanceof bga) {
         bga _snowmanx = (bga)_snowman;
         if (_snowmanx.bq()) {
            this.h(_snowmanx.cC().g());
         }
      }

      return super.a(_snowman, _snowman);
   }

   @Override
   public void a(apk var1) {
      double _snowman = c(this.cC());
      if (!_snowman.p() && !_snowman.d() && !(_snowman >= 0.01F)) {
         super.a(_snowman);
         if (!_snowman.d() && this.l.V().b(brt.g)) {
            this.a(bup.bH);
         }
      } else {
         if (this.b < 0) {
            this.u();
            this.b = this.J.nextInt(20) + this.J.nextInt(20);
         }
      }
   }

   protected void h(double var1) {
      if (!this.l.v) {
         double _snowman = Math.sqrt(_snowman);
         if (_snowman > 5.0) {
            _snowman = 5.0;
         }

         this.l.a(this, this.cD(), this.cE(), this.cH(), (float)(4.0 + this.J.nextDouble() * 1.5 * _snowman), brp.a.b);
         this.ad();
      }
   }

   @Override
   public boolean b(float var1, float var2) {
      if (_snowman >= 3.0F) {
         float _snowman = _snowman / 10.0F;
         this.h((double)(_snowman * _snowman));
      }

      return super.b(_snowman, _snowman);
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      if (_snowman && this.b < 0) {
         this.u();
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 10) {
         this.u();
      } else {
         super.a(_snowman);
      }
   }

   public void u() {
      this.b = 80;
      if (!this.l.v) {
         this.l.a(this, (byte)10);
         if (!this.aA()) {
            this.l.a(null, this.cD(), this.cE(), this.cH(), adq.pb, adr.e, 1.0F, 1.0F);
         }
      }
   }

   public int v() {
      return this.b;
   }

   public boolean x() {
      return this.b > -1;
   }

   @Override
   public float a(brp var1, brc var2, fx var3, ceh var4, cux var5, float var6) {
      return !this.x() || !_snowman.a(aed.H) && !_snowman.d_(_snowman.b()).a(aed.H) ? super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman) : 0.0F;
   }

   @Override
   public boolean a(brp var1, brc var2, fx var3, ceh var4, float var5) {
      return !this.x() || !_snowman.a(aed.H) && !_snowman.d_(_snowman.b()).a(aed.H) ? super.a(_snowman, _snowman, _snowman, _snowman, _snowman) : false;
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("TNTFuse", 99)) {
         this.b = _snowman.h("TNTFuse");
      }
   }

   @Override
   protected void b(md var1) {
      super.b(_snowman);
      _snowman.b("TNTFuse", this.b);
   }
}
