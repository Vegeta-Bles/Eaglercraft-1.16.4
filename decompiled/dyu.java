public class dyu extends dxt {
   private dyu(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.u = 0.5F;
   }

   @Override
   public void a() {
      super.a();
      this.k = this.k - (0.004 + 0.04 * (double)this.u);
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dyu(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
