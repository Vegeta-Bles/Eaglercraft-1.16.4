public class ehx extends eif<bdc, dtw<bdc>> {
   private static final vk a = new vk("textures/entity/creeper/creeper_armor.png");
   private final dtw<bdc> b = new dtw<>(2.0F);

   public ehx(egk<bdc, dtw<bdc>> var1) {
      super(_snowman);
   }

   @Override
   protected float a(float var1) {
      return _snowman * 0.01F;
   }

   @Override
   protected vk a() {
      return a;
   }

   @Override
   protected duc<bdc> b() {
      return this.b;
   }
}
