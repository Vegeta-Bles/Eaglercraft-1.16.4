public class eml extends emd {
   private final aqa n;

   public eml(adp var1, adr var2, aqa var3) {
      this(_snowman, _snowman, 1.0F, 1.0F, _snowman);
   }

   public eml(adp var1, adr var2, float var3, float var4, aqa var5) {
      super(_snowman, _snowman);
      this.d = _snowman;
      this.e = _snowman;
      this.n = _snowman;
      this.f = (double)((float)this.n.cD());
      this.g = (double)((float)this.n.cE());
      this.h = (double)((float)this.n.cH());
   }

   @Override
   public boolean t() {
      return !this.n.aA();
   }

   @Override
   public void r() {
      if (this.n.y) {
         this.o();
      } else {
         this.f = (double)((float)this.n.cD());
         this.g = (double)((float)this.n.cE());
         this.h = (double)((float)this.n.cH());
      }
   }
}
