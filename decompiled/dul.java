public class dul extends dvt {
   private final dwn b = new dwn(this, 32, 0);

   public dul() {
      super(0, 0, 64, 64);
      this.b.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.25F);
      this.b.a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void a(float var1, float var2, float var3) {
      super.a(_snowman, _snowman, _snowman);
      this.b.e = this.a.e;
      this.b.d = this.a.d;
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
