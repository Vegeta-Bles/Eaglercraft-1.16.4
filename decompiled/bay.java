public abstract class bay extends aqu {
   protected bay(aqe<? extends bay> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(cwz.h, 0.0F);
   }

   @Override
   public boolean cM() {
      return true;
   }

   @Override
   public aqq dC() {
      return aqq.e;
   }

   @Override
   public boolean a(brz var1) {
      return _snowman.j(this);
   }

   @Override
   public int D() {
      return 120;
   }

   @Override
   protected int d(bfw var1) {
      return 1 + this.l.t.nextInt(3);
   }

   protected void a(int var1) {
      if (this.aX() && !this.aH()) {
         this.j(_snowman - 1);
         if (this.bI() == -20) {
            this.j(0);
            this.a(apk.h, 2.0F);
         }
      } else {
         this.j(300);
      }
   }

   @Override
   public void ag() {
      int _snowman = this.bI();
      super.ag();
      this.a(_snowman);
   }

   @Override
   public boolean bV() {
      return false;
   }

   @Override
   public boolean a(bfw var1) {
      return false;
   }
}
