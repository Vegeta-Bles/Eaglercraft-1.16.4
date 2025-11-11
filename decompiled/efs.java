public class efs extends efw<bbe, dus<bbe>> {
   private static final vk[] a = new vk[]{
      new vk("textures/entity/llama/creamy.png"),
      new vk("textures/entity/llama/white.png"),
      new vk("textures/entity/llama/brown.png"),
      new vk("textures/entity/llama/gray.png")
   };

   public efs(eet var1) {
      super(_snowman, new dus<>(0.0F), 0.7F);
      this.a(new eio(this));
   }

   public vk a(bbe var1) {
      return a[_snowman.fx()];
   }
}
