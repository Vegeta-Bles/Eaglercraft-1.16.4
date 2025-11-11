public class eeh extends efw<bac, dtr<bac>> {
   private static final vk a = new vk("textures/entity/chicken.png");

   public eeh(eet var1) {
      super(_snowman, new dtr<>(), 0.3F);
   }

   public vk a(bac var1) {
      return a;
   }

   protected float a(bac var1, float var2) {
      float _snowman = afm.g(_snowman, _snowman.br, _snowman.bo);
      float _snowmanx = afm.g(_snowman, _snowman.bq, _snowman.bp);
      return (afm.a(_snowman) + 1.0F) * _snowmanx;
   }
}
