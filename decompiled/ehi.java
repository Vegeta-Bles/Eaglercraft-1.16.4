public class ehi extends efj<bef> {
   private static final vk a = new vk("textures/entity/illager/vindicator.png");

   public ehi(eet var1) {
      super(_snowman, new dun<>(0.0F, 0.0F, 64, 64), 0.5F);
      this.a(new ein<bef, dun<bef>>(this) {
         public void a(dfm var1, eag var2, int var3, bef var4, float var5, float var6, float var7, float var8, float var9, float var10) {
            if (_snowman.eF()) {
               super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            }
         }
      });
   }

   public vk a(bef var1) {
      return a;
   }
}
