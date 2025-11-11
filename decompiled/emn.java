public class emn extends emd {
   private final bhl n;
   private float o = 0.0F;

   public emn(bhl var1) {
      super(adq.hJ, adr.g);
      this.n = _snowman;
      this.i = true;
      this.j = 0;
      this.d = 0.0F;
      this.f = (double)((float)_snowman.cD());
      this.g = (double)((float)_snowman.cE());
      this.h = (double)((float)_snowman.cH());
   }

   @Override
   public boolean t() {
      return !this.n.aA();
   }

   @Override
   public boolean s() {
      return true;
   }

   @Override
   public void r() {
      if (this.n.y) {
         this.o();
      } else {
         this.f = (double)((float)this.n.cD());
         this.g = (double)((float)this.n.cE());
         this.h = (double)((float)this.n.cH());
         float _snowman = afm.a(aqa.c(this.n.cC()));
         if ((double)_snowman >= 0.01) {
            this.o = afm.a(this.o + 0.0025F, 0.0F, 1.0F);
            this.d = afm.g(afm.a(_snowman, 0.0F, 0.5F), 0.0F, 0.7F);
         } else {
            this.o = 0.0F;
            this.d = 0.0F;
         }
      }
   }
}
