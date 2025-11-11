public class dxz extends dye {
   private int a;
   private final int b = 8;

   private dxz(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
   }

   @Override
   public void a() {
      for (int _snowman = 0; _snowman < 6; _snowman++) {
         double _snowmanx = this.g + (this.r.nextDouble() - this.r.nextDouble()) * 4.0;
         double _snowmanxx = this.h + (this.r.nextDouble() - this.r.nextDouble()) * 4.0;
         double _snowmanxxx = this.i + (this.r.nextDouble() - this.r.nextDouble()) * 4.0;
         this.c.a(hh.w, _snowmanx, _snowmanxx, _snowmanxxx, (double)((float)this.a / (float)this.b), 0.0, 0.0);
      }

      this.a++;
      if (this.a == this.b) {
         this.j();
      }
   }

   public static class a implements dyj<hi> {
      public a() {
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxz(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
