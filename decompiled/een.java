public class een extends edw<bde, dty<bde>> {
   private static final vk a = new vk("textures/entity/zombie/drowned.png");

   public een(eet var1) {
      super(_snowman, new dty<>(0.0F, 0.0F, 64, 64), new dty<>(0.5F, true), new dty<>(1.0F, true));
      this.a(new eic<>(this));
   }

   @Override
   public vk a(bej var1) {
      return a;
   }

   protected void a(bde var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = _snowman.a(_snowman);
      if (_snowman > 0.0F) {
         _snowman.a(g.b.a(afm.g(_snowman, _snowman.q, -10.0F - _snowman.q)));
      }
   }
}
