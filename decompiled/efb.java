public class efb extends efw<bah, due<bah>> {
   private static final vk a = new vk("textures/entity/fox/fox.png");
   private static final vk g = new vk("textures/entity/fox/fox_sleep.png");
   private static final vk h = new vk("textures/entity/fox/snow_fox.png");
   private static final vk i = new vk("textures/entity/fox/snow_fox_sleep.png");

   public efb(eet var1) {
      super(_snowman, new due<>(), 0.4F);
      this.a(new eih(this));
   }

   protected void a(bah var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.eO() || _snowman.eN()) {
         float _snowman = -afm.g(_snowman, _snowman.s, _snowman.q);
         _snowman.a(g.b.a(_snowman));
      }
   }

   public vk a(bah var1) {
      if (_snowman.eL() == bah.v.a) {
         return _snowman.em() ? g : a;
      } else {
         return _snowman.em() ? i : h;
      }
   }
}
