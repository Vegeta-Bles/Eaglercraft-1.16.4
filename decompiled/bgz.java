public class bgz extends bgb {
   private static final us<Boolean> e = uv.a(bgz.class, uu.i);

   public bgz(aqe<? extends bgz> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgz(brx var1, aqm var2, double var3, double var5, double var7) {
      super(aqe.aV, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public bgz(brx var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(aqe.aV, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected float i() {
      return this.k() ? 0.73F : super.i();
   }

   @Override
   public boolean bq() {
      return false;
   }

   @Override
   public float a(brp var1, brc var2, fx var3, ceh var4, cux var5, float var6) {
      return this.k() && bcl.c(_snowman) ? Math.min(0.8F, _snowman) : _snowman;
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      if (!this.l.v) {
         aqa _snowman = _snowman.a();
         aqa _snowmanx = this.v();
         boolean _snowmanxx;
         if (_snowmanx instanceof aqm) {
            aqm _snowmanxxx = (aqm)_snowmanx;
            _snowmanxx = _snowman.a(apk.a(this, _snowmanxxx), 8.0F);
            if (_snowmanxx) {
               if (_snowman.aX()) {
                  this.a(_snowmanxxx, _snowman);
               } else {
                  _snowmanxxx.b(5.0F);
               }
            }
         } else {
            _snowmanxx = _snowman.a(apk.o, 5.0F);
         }

         if (_snowmanxx && _snowman instanceof aqm) {
            int _snowmanxxx = 0;
            if (this.l.ad() == aor.c) {
               _snowmanxxx = 10;
            } else if (this.l.ad() == aor.d) {
               _snowmanxxx = 40;
            }

            if (_snowmanxxx > 0) {
               ((aqm)_snowman).c(new apu(apw.t, 20 * _snowmanxxx, 1));
            }
         }
      }
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      if (!this.l.v) {
         brp.a _snowman = this.l.V().b(brt.b) ? brp.a.c : brp.a.a;
         this.l.a(this, this.cD(), this.cE(), this.cH(), 1.0F, false, _snowman);
         this.ad();
      }
   }

   @Override
   public boolean aT() {
      return false;
   }

   @Override
   public boolean a(apk var1, float var2) {
      return false;
   }

   @Override
   protected void e() {
      this.R.a(e, false);
   }

   @Override
   public boolean k() {
      return this.R.a(e);
   }

   public void a(boolean var1) {
      this.R.b(e, _snowman);
   }

   @Override
   protected boolean W_() {
      return false;
   }
}
