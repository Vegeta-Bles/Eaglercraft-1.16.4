public abstract class emh extends emd {
   protected final baa n;
   private boolean o;

   public emh(baa var1, adp var2, adr var3) {
      super(_snowman, _snowman);
      this.n = _snowman;
      this.f = (double)((float)_snowman.cD());
      this.g = (double)((float)_snowman.cE());
      this.h = (double)((float)_snowman.cH());
      this.i = true;
      this.j = 0;
      this.d = 0.0F;
   }

   @Override
   public void r() {
      boolean _snowman = this.q();
      if (_snowman && !this.n()) {
         djz.C().W().a((emu)this.p());
         this.o = true;
      }

      if (!this.n.y && !this.o) {
         this.f = (double)((float)this.n.cD());
         this.g = (double)((float)this.n.cE());
         this.h = (double)((float)this.n.cH());
         float _snowmanx = afm.a(aqa.c(this.n.cC()));
         if ((double)_snowmanx >= 0.01) {
            this.e = afm.g(afm.a(_snowmanx, this.u(), this.v()), this.u(), this.v());
            this.d = afm.g(afm.a(_snowmanx, 0.0F, 0.5F), 0.0F, 1.2F);
         } else {
            this.e = 0.0F;
            this.d = 0.0F;
         }
      } else {
         this.o();
      }
   }

   private float u() {
      return this.n.w_() ? 1.1F : 0.7F;
   }

   private float v() {
      return this.n.w_() ? 1.5F : 1.1F;
   }

   @Override
   public boolean s() {
      return true;
   }

   @Override
   public boolean t() {
      return !this.n.aA();
   }

   protected abstract emd p();

   protected abstract boolean q();
}
