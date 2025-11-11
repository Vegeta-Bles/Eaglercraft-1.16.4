public class dxl extends dzb {
   private final dyw a;

   private dxl(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
      this.t = 4;
      this.u = 0.008F;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.b(_snowman);
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.k = this.k - (double)this.u;
         this.a(this.j, this.k, this.l);
         this.b(this.a);
      }
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxl(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
