public class egr extends efw<bdz, dvu<bdz>> {
   private static final vk a = new vk("textures/entity/slime/slime.png");

   public egr(eet var1) {
      super(_snowman, new dvu<>(16), 0.25F);
      this.a(new eix<>(this));
   }

   public void a(bdz var1, float var2, float var3, dfm var4, eag var5, int var6) {
      this.c = 0.25F * (float)_snowman.eP();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected void a(bdz var1, dfm var2, float var3) {
      float _snowman = 0.999F;
      _snowman.a(0.999F, 0.999F, 0.999F);
      _snowman.a(0.0, 0.001F, 0.0);
      float _snowmanx = (float)_snowman.eP();
      float _snowmanxx = afm.g(_snowman, _snowman.d, _snowman.c) / (_snowmanx * 0.5F + 1.0F);
      float _snowmanxxx = 1.0F / (_snowmanxx + 1.0F);
      _snowman.a(_snowmanxxx * _snowmanx, 1.0F / _snowmanxxx * _snowmanx, _snowmanxxx * _snowmanx);
   }

   public vk a(bdz var1) {
      return a;
   }
}
