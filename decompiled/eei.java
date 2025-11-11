public class eei extends efw<bad, dts<bad>> {
   private static final vk a = new vk("textures/entity/fish/cod.png");

   public eei(eet var1) {
      super(_snowman, new dts<>(), 0.3F);
   }

   public vk a(bad var1) {
      return a;
   }

   protected void a(bad var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = 4.3F * afm.a(0.6F * _snowman);
      _snowman.a(g.d.a(_snowman));
      if (!_snowman.aE()) {
         _snowman.a(0.1F, 0.1F, -0.1F);
         _snowman.a(g.f.a(90.0F));
      }
   }
}
