public abstract class dld extends dlh {
   public dld(int var1, int var2, int var3, int var4, nr var5) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public abstract void b();

   @Override
   public void a(double var1, double var3) {
      this.b();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (!this.o || !this.p) {
         return false;
      } else if (_snowman != 257 && _snowman != 32 && _snowman != 335) {
         return false;
      } else {
         this.a(djz.C().W());
         this.b();
         return true;
      }
   }
}
