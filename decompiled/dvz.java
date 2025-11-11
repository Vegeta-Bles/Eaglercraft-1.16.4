public class dvz extends duv {
   public static final vk a = new vk("textures/entity/trident.png");
   private final dwn b = new dwn(32, 32, 0, 6);

   public dvz() {
      super(eao::b);
      this.b.a(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, 0.0F);
      dwn _snowman = new dwn(32, 32, 4, 0);
      _snowman.a(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F);
      this.b.b(_snowman);
      dwn _snowmanx = new dwn(32, 32, 4, 3);
      _snowmanx.a(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.b.b(_snowmanx);
      dwn _snowmanxx = new dwn(32, 32, 0, 0);
      _snowmanxx.a(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.b.b(_snowmanxx);
      dwn _snowmanxxx = new dwn(32, 32, 4, 3);
      _snowmanxxx.g = true;
      _snowmanxxx.a(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.b.b(_snowmanxxx);
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
