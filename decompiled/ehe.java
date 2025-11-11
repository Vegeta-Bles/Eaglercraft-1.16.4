public class ehe extends efw<bax, dwc<bax>> {
   private static final vk a = new vk("textures/entity/turtle/big_sea_turtle.png");

   public ehe(eet var1) {
      super(_snowman, new dwc<>(0.0F), 0.7F);
   }

   public void a(bax var1, float var2, float var3, dfm var4, eag var5, int var6) {
      if (_snowman.w_()) {
         this.c *= 0.5F;
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bax var1) {
      return a;
   }
}
