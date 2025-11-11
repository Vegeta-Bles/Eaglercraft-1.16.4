public class efu extends efw<bdp, dup<bdp>> {
   private static final vk a = new vk("textures/entity/slime/magmacube.png");

   public efu(eet var1) {
      super(_snowman, new dup<>(), 0.25F);
   }

   protected int a(bdp var1, fx var2) {
      return 15;
   }

   public vk a(bdp var1) {
      return a;
   }

   protected void a(bdp var1, dfm var2, float var3) {
      int _snowman = _snowman.eP();
      float _snowmanx = afm.g(_snowman, _snowman.d, _snowman.c) / ((float)_snowman * 0.5F + 1.0F);
      float _snowmanxx = 1.0F / (_snowmanx + 1.0F);
      _snowman.a(_snowmanxx * (float)_snowman, 1.0F / _snowmanxx * (float)_snowman, _snowmanxx * (float)_snowman);
   }
}
