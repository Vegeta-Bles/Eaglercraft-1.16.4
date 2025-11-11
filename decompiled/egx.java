public class egx extends efw<bed, dvy<bed>> {
   private static final vk a = new vk("textures/entity/strider/strider.png");
   private static final vk g = new vk("textures/entity/strider/strider_cold.png");

   public egx(eet var1) {
      super(_snowman, new dvy<>(), 0.5F);
      this.a(new eiu<>(this, new dvy<>(), new vk("textures/entity/strider/strider_saddle.png")));
   }

   public vk a(bed var1) {
      return _snowman.eK() ? g : a;
   }

   protected void a(bed var1, dfm var2, float var3) {
      if (_snowman.w_()) {
         _snowman.a(0.5F, 0.5F, 0.5F);
         this.c = 0.25F;
      } else {
         this.c = 0.5F;
      }
   }

   protected boolean b(bed var1) {
      return _snowman.eK();
   }
}
