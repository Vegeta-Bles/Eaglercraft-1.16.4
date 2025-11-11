public class eew<T extends bea> extends efj<T> {
   private static final vk a = new vk("textures/entity/illager/evoker.png");

   public eew(eet var1) {
      super(_snowman, new dun<>(0.0F, 0.0F, 64, 64), 0.5F);
      this.a(new ein<T, dun<T>>(this) {
         public void a(dfm var1, eag var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
            if (_snowman.eW()) {
               super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            }
         }
      });
   }

   public vk a(T var1) {
      return a;
   }
}
