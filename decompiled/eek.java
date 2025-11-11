public class eek extends efw<bdc, dtw<bdc>> {
   private static final vk a = new vk("textures/entity/creeper/creeper.png");

   public eek(eet var1) {
      super(_snowman, new dtw<>(), 0.5F);
      this.a(new ehx(this));
   }

   protected void a(bdc var1, dfm var2, float var3) {
      float _snowman = _snowman.y(_snowman);
      float _snowmanx = 1.0F + afm.a(_snowman * 100.0F) * _snowman * 0.01F;
      _snowman = afm.a(_snowman, 0.0F, 1.0F);
      _snowman *= _snowman;
      _snowman *= _snowman;
      float _snowmanxx = (1.0F + _snowman * 0.4F) * _snowmanx;
      float _snowmanxxx = (1.0F + _snowman * 0.1F) / _snowmanx;
      _snowman.a(_snowmanxx, _snowmanxxx, _snowmanxx);
   }

   protected float a(bdc var1, float var2) {
      float _snowman = _snowman.y(_snowman);
      return (int)(_snowman * 10.0F) % 2 == 0 ? 0.0F : afm.a(_snowman, 0.5F, 1.0F);
   }

   public vk a(bdc var1) {
      return a;
   }
}
