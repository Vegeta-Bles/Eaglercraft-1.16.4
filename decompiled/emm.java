public class emm extends emd {
   private final bdm n;

   public emm(bdm var1) {
      super(adq.fu, adr.f);
      this.n = _snowman;
      this.k = emt.a.a;
      this.i = true;
      this.j = 0;
   }

   @Override
   public boolean t() {
      return !this.n.aA();
   }

   @Override
   public void r() {
      if (!this.n.y && this.n.A() == null) {
         this.f = (double)((float)this.n.cD());
         this.g = (double)((float)this.n.cE());
         this.h = (double)((float)this.n.cH());
         float _snowman = this.n.A(0.0F);
         this.d = 0.0F + 1.0F * _snowman * _snowman;
         this.e = 0.7F + 0.5F * _snowman;
      } else {
         this.o();
      }
   }
}
