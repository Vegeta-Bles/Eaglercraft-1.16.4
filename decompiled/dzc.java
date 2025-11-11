public class dzc extends dyp {
   private dzc(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, -0.05F);
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.B *= 0.75F;
      this.t = 60 + this.r.nextInt(12);
      this.b(_snowman);
      if (this.r.nextInt(4) == 0) {
         this.a(0.6F + this.r.nextFloat() * 0.2F, 0.6F + this.r.nextFloat() * 0.3F, this.r.nextFloat() * 0.2F);
      } else {
         this.a(0.1F + this.r.nextFloat() * 0.2F, 0.4F + this.r.nextFloat() * 0.3F, this.r.nextFloat() * 0.2F);
      }

      this.f(0.6F);
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dzc(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
