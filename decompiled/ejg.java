public class ejg extends eif<bcl, dwh<bcl>> {
   private static final vk a = new vk("textures/entity/wither/wither_armor.png");
   private final dwh<bcl> b = new dwh<>(0.5F);

   public ejg(egk<bcl, dwh<bcl>> var1) {
      super(_snowman);
   }

   @Override
   protected float a(float var1) {
      return afm.b(_snowman * 0.02F) * 3.0F;
   }

   @Override
   protected vk a() {
      return a;
   }

   @Override
   protected duc<bcl> b() {
      return this.b;
   }
}
