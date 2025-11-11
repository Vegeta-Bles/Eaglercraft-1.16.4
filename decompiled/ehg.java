public class ehg extends efh<bee, dwd> {
   private static final vk a = new vk("textures/entity/illager/vex.png");
   private static final vk g = new vk("textures/entity/illager/vex_charging.png");

   public ehg(eet var1) {
      super(_snowman, new dwd(), 0.3F);
   }

   protected int a(bee var1, fx var2) {
      return 15;
   }

   public vk a(bee var1) {
      return _snowman.eM() ? g : a;
   }

   protected void a(bee var1, dfm var2, float var3) {
      _snowman.a(0.4F, 0.4F, 0.4F);
   }
}
