public class ehh extends efw<bfj, dwf<bfj>> {
   private static final vk a = new vk("textures/entity/villager/villager.png");

   public ehh(eet var1, acf var2) {
      super(_snowman, new dwf<>(0.0F), 0.5F);
      this.a(new ehz<>(this));
      this.a(new eje<>(this, _snowman, "villager"));
      this.a(new ehy<>(this));
   }

   public vk a(bfj var1) {
      return a;
   }

   protected void a(bfj var1, dfm var2, float var3) {
      float _snowman = 0.9375F;
      if (_snowman.w_()) {
         _snowman = (float)((double)_snowman * 0.5);
         this.c = 0.25F;
      } else {
         this.c = 0.5F;
      }

      _snowman.a(_snowman, _snowman, _snowman);
   }
}
