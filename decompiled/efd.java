public class efd extends efw<bdl, dum<bdl>> {
   private static final vk a = new vk("textures/entity/zombie/zombie.png");
   private final float g;

   public efd(eet var1, float var2) {
      super(_snowman, new dug(), 0.5F * _snowman);
      this.g = _snowman;
      this.a(new ein<>(this));
      this.a(new eik<>(this, new dug(0.5F, true), new dug(1.0F, true)));
   }

   protected void a(bdl var1, dfm var2, float var3) {
      _snowman.a(this.g, this.g, this.g);
   }

   public vk a(bdl var1) {
      return a;
   }
}
