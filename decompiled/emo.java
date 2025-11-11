public class emo extends emd {
   private final bfw n;
   private final bhl o;

   public emo(bfw var1, bhl var2) {
      super(adq.hI, adr.g);
      this.n = _snowman;
      this.o = _snowman;
      this.k = emt.a.a;
      this.i = true;
      this.j = 0;
      this.d = 0.0F;
   }

   @Override
   public boolean t() {
      return !this.o.aA();
   }

   @Override
   public boolean s() {
      return true;
   }

   @Override
   public void r() {
      if (!this.o.y && this.n.br() && this.n.ct() == this.o) {
         float _snowman = afm.a(aqa.c(this.o.cC()));
         if ((double)_snowman >= 0.01) {
            this.d = 0.0F + afm.a(_snowman, 0.0F, 1.0F) * 0.75F;
         } else {
            this.d = 0.0F;
         }
      } else {
         this.o();
      }
   }
}
