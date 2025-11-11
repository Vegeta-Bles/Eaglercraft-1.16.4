public abstract class edw<T extends bej, M extends dwj<T>> extends efh<T, M> {
   private static final vk a = new vk("textures/entity/zombie/zombie.png");

   protected edw(eet var1, M var2, M var3, M var4) {
      super(_snowman, _snowman, 0.5F);
      this.a(new eik<>(this, _snowman, _snowman));
   }

   public vk a(bej var1) {
      return a;
   }

   protected boolean b(T var1) {
      return _snowman.eT();
   }
}
