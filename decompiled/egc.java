public class egc extends efw<bds, dva<bds>> {
   private static final vk a = new vk("textures/entity/phantom.png");

   public egc(eet var1) {
      super(_snowman, new dva<>(), 0.75F);
      this.a(new eis<>(this));
   }

   public vk a(bds var1) {
      return a;
   }

   protected void a(bds var1, dfm var2, float var3) {
      int _snowman = _snowman.m();
      float _snowmanx = 1.0F + 0.15F * (float)_snowman;
      _snowman.a(_snowmanx, _snowmanx, _snowmanx);
      _snowman.a(0.0, 1.3125, 0.1875);
   }

   protected void a(bds var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.a(g.b.a(_snowman.q));
   }
}
