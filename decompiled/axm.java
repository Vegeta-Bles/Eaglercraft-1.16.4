public class axm extends awf {
   private final bej b;
   private int c;

   public axm(bej var1, double var2, boolean var4) {
      super(_snowman, _snowman, _snowman);
      this.b = _snowman;
   }

   @Override
   public void c() {
      super.c();
      this.c = 0;
   }

   @Override
   public void d() {
      super.d();
      this.b.s(false);
   }

   @Override
   public void e() {
      super.e();
      this.c++;
      if (this.c >= 5 && this.j() < this.k() / 2) {
         this.b.s(true);
      } else {
         this.b.s(false);
      }
   }
}
