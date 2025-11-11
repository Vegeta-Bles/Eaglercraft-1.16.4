public class emk extends emd {
   private final dzm n;
   private int o;

   public emk(dzm var1) {
      super(adq.dl, adr.h);
      this.n = _snowman;
      this.i = true;
      this.j = 0;
      this.d = 0.1F;
   }

   @Override
   public void r() {
      this.o++;
      if (!this.n.y && (this.o <= 20 || this.n.ef())) {
         this.f = (double)((float)this.n.cD());
         this.g = (double)((float)this.n.cE());
         this.h = (double)((float)this.n.cH());
         float _snowman = (float)this.n.cC().g();
         if ((double)_snowman >= 1.0E-7) {
            this.d = afm.a(_snowman / 4.0F, 0.0F, 1.0F);
         } else {
            this.d = 0.0F;
         }

         if (this.o < 20) {
            this.d = 0.0F;
         } else if (this.o < 40) {
            this.d = (float)((double)this.d * ((double)(this.o - 20) / 20.0));
         }

         float _snowmanx = 0.8F;
         if (this.d > 0.8F) {
            this.e = 1.0F + (this.d - 0.8F);
         } else {
            this.e = 1.0F;
         }
      } else {
         this.o();
      }
   }
}
