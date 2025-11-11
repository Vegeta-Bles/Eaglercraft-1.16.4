public class efc extends efw<bdk, duf<bdk>> {
   private static final vk a = new vk("textures/entity/ghast/ghast.png");
   private static final vk g = new vk("textures/entity/ghast/ghast_shooting.png");

   public efc(eet var1) {
      super(_snowman, new duf<>(), 1.5F);
   }

   public vk a(bdk var1) {
      return _snowman.m() ? g : a;
   }

   protected void a(bdk var1, dfm var2, float var3) {
      float _snowman = 1.0F;
      float _snowmanx = 4.5F;
      float _snowmanxx = 4.5F;
      _snowman.a(4.5F, 4.5F, 4.5F);
   }
}
