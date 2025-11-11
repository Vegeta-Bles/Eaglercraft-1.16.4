public class ehr extends efh<bek, dwk<bek>> {
   private static final vk a = new vk("textures/entity/zombie_villager/zombie_villager.png");

   public ehr(eet var1, acf var2) {
      super(_snowman, new dwk<>(0.0F, false), 0.5F);
      this.a(new eik<>(this, new dwk(0.5F, true), new dwk(1.0F, true)));
      this.a(new eje<>(this, _snowman, "zombie_villager"));
   }

   public vk a(bek var1) {
      return a;
   }

   protected boolean b(bek var1) {
      return _snowman.eW();
   }
}
