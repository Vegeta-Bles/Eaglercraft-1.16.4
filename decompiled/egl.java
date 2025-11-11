public class egl extends efw<bar, dvl<bar>> {
   private static final vk a = new vk("textures/entity/fish/salmon.png");

   public egl(eet var1) {
      super(_snowman, new dvl<>(), 0.4F);
   }

   public vk a(bar var1) {
      return a;
   }

   protected void a(bar var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = 1.0F;
      float _snowmanx = 1.0F;
      if (!_snowman.aE()) {
         _snowman = 1.3F;
         _snowmanx = 1.7F;
      }

      float _snowmanxx = _snowman * 4.3F * afm.a(_snowmanx * 0.6F * _snowman);
      _snowman.a(g.d.a(_snowmanxx));
      _snowman.a(0.0, 0.0, -0.4F);
      if (!_snowman.aE()) {
         _snowman.a(0.2F, 0.1F, 0.0);
         _snowman.a(g.f.a(90.0F));
      }
   }
}
