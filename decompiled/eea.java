public class eea extends efw<azu, dtk> {
   private static final vk a = new vk("textures/entity/bat.png");

   public eea(eet var1) {
      super(_snowman, new dtk(), 0.25F);
   }

   public vk a(azu var1) {
      return a;
   }

   protected void a(azu var1, dfm var2, float var3) {
      _snowman.a(0.35F, 0.35F, 0.35F);
   }

   protected void a(azu var1, dfm var2, float var3, float var4, float var5) {
      if (_snowman.eI()) {
         _snowman.a(0.0, -0.1F, 0.0);
      } else {
         _snowman.a(0.0, (double)(afm.b(_snowman * 0.3F) * 0.1F), 0.0);
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
