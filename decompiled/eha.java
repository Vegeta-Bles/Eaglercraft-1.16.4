public class eha extends edz<bgc> {
   public static final vk a = new vk("textures/entity/projectiles/arrow.png");
   public static final vk e = new vk("textures/entity/projectiles/tipped_arrow.png");

   public eha(eet var1) {
      super(_snowman);
   }

   public vk a(bgc var1) {
      return _snowman.u() > 0 ? e : a;
   }
}
