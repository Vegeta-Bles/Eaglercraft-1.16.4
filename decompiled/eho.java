public class eho extends efw<baz, dwi<baz>> {
   private static final vk a = new vk("textures/entity/wolf/wolf.png");
   private static final vk g = new vk("textures/entity/wolf/wolf_tame.png");
   private static final vk h = new vk("textures/entity/wolf/wolf_angry.png");

   public eho(eet var1) {
      super(_snowman, new dwi<>(), 0.5F);
      this.a(new ejh(this));
   }

   protected float a(baz var1, float var2) {
      return _snowman.eW();
   }

   public void a(baz var1, float var2, float var3, dfm var4, eag var5, int var6) {
      if (_snowman.eV()) {
         float _snowman = _snowman.y(_snowman);
         this.e.a(_snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.eV()) {
         this.e.a(1.0F, 1.0F, 1.0F);
      }
   }

   public vk a(baz var1) {
      if (_snowman.eK()) {
         return g;
      } else {
         return _snowman.H_() ? h : a;
      }
   }
}
