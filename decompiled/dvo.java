public class dvo extends duv {
   private final dwn a;
   private final dwn b;

   public dvo() {
      super(eao::b);
      this.r = 64;
      this.s = 64;
      this.a = new dwn(this, 0, 0);
      this.a.a(-6.0F, -11.0F, -2.0F, 12.0F, 22.0F, 1.0F, 0.0F);
      this.b = new dwn(this, 26, 0);
      this.b.a(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, 0.0F);
   }

   public dwn a() {
      return this.a;
   }

   public dwn b() {
      return this.b;
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
