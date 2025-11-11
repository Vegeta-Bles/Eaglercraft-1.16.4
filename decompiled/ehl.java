public class ehl extends efw<bcl, dwh<bcl>> {
   private static final vk a = new vk("textures/entity/wither/wither_invulnerable.png");
   private static final vk g = new vk("textures/entity/wither/wither.png");

   public ehl(eet var1) {
      super(_snowman, new dwh<>(0.0F), 1.0F);
      this.a(new ejg(this));
   }

   protected int a(bcl var1, fx var2) {
      return 15;
   }

   public vk a(bcl var1) {
      int _snowman = _snowman.eL();
      return _snowman > 0 && (_snowman > 80 || _snowman / 5 % 2 != 1) ? a : g;
   }

   protected void a(bcl var1, dfm var2, float var3) {
      float _snowman = 2.0F;
      int _snowmanx = _snowman.eL();
      if (_snowmanx > 0) {
         _snowman -= ((float)_snowmanx - _snowman) / 220.0F * 0.5F;
      }

      _snowman.a(_snowman, _snowman, _snowman);
   }
}
