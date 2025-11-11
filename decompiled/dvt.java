public class dvt extends duv {
   protected final dwn a;

   public dvt() {
      this(0, 35, 64, 64);
   }

   public dvt(int var1, int var2, int var3, int var4) {
      super(eao::h);
      this.r = _snowman;
      this.s = _snowman;
      this.a = new dwn(this, _snowman, _snowman);
      this.a.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);
      this.a.a(0.0F, 0.0F, 0.0F);
   }

   public void a(float var1, float var2, float var3) {
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.a.d = _snowman * (float) (Math.PI / 180.0);
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
