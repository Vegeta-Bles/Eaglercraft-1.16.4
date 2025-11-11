public class awa extends avv {
   private final bat a;
   private aah b;
   private boolean c;

   public awa(bat var1) {
      this.a = _snowman;
   }

   @Override
   public boolean a() {
      aah _snowman = (aah)this.a.eN();
      boolean _snowmanx = _snowman != null && !_snowman.a_() && !_snowman.bC.b && !_snowman.aE();
      return !this.a.eO() && _snowmanx && this.a.eY();
   }

   @Override
   public boolean C_() {
      return !this.c;
   }

   @Override
   public void c() {
      this.b = (aah)this.a.eN();
      this.c = false;
   }

   @Override
   public void e() {
      if (!this.c && !this.a.eM() && !this.a.eB()) {
         if (this.a.cc().c(this.b.cc())) {
            this.c = this.a.d(this.b);
         }
      }
   }
}
