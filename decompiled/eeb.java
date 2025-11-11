public class eeb extends efw<baa, dtl<baa>> {
   private static final vk a = new vk("textures/entity/bee/bee_angry.png");
   private static final vk g = new vk("textures/entity/bee/bee_angry_nectar.png");
   private static final vk h = new vk("textures/entity/bee/bee.png");
   private static final vk i = new vk("textures/entity/bee/bee_nectar.png");

   public eeb(eet var1) {
      super(_snowman, new dtl<>(), 0.4F);
   }

   public vk a(baa var1) {
      if (_snowman.H_()) {
         return _snowman.eX() ? g : a;
      } else {
         return _snowman.eX() ? i : h;
      }
   }
}
