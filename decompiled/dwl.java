public class dwl extends dvt {
   private final dwn b;
   private final dwn c;

   public dwl(float var1) {
      this.r = 256;
      this.s = 256;
      float _snowman = -16.0F;
      this.b = new dwn(this);
      this.b.a("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, _snowman, 176, 44);
      this.b.a("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, _snowman, 112, 30);
      this.b.g = true;
      this.b.a("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, _snowman, 0, 0);
      this.b.a("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, _snowman, 112, 0);
      this.b.g = false;
      this.b.a("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, _snowman, 0, 0);
      this.b.a("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, _snowman, 112, 0);
      this.c = new dwn(this);
      this.c.a(0.0F, 4.0F, -8.0F);
      this.c.a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, _snowman, 176, 65);
      this.b.b(this.c);
   }

   @Override
   public void a(float var1, float var2, float var3) {
      this.c.d = (float)(Math.sin((double)(_snowman * (float) Math.PI * 0.2F)) + 1.0) * 0.2F;
      this.b.e = _snowman * (float) (Math.PI / 180.0);
      this.b.d = _snowman * (float) (Math.PI / 180.0);
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      _snowman.a();
      _snowman.a(0.0, -0.374375F, 0.0);
      _snowman.a(0.75F, 0.75F, 0.75F);
      this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.b();
   }
}
