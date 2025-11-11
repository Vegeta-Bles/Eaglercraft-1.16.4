public class ecc extends ece<cch> {
   public static final elr a = new elr(ekb.d, new vk("entity/bell/bell_body"));
   private final dwn c = new dwn(32, 32, 0, 0);

   public ecc(ecd var1) {
      super(_snowman);
      this.c.a(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F);
      this.c.a(8.0F, 12.0F, 8.0F);
      dwn _snowman = new dwn(32, 32, 0, 13);
      _snowman.a(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F);
      _snowman.a(-8.0F, -12.0F, -8.0F);
      this.c.b(_snowman);
   }

   public void a(cch var1, float var2, dfm var3, eag var4, int var5, int var6) {
      float _snowman = (float)_snowman.a + _snowman;
      float _snowmanx = 0.0F;
      float _snowmanxx = 0.0F;
      if (_snowman.b) {
         float _snowmanxxx = afm.a(_snowman / (float) Math.PI) / (4.0F + _snowman / 3.0F);
         if (_snowman.c == gc.c) {
            _snowmanx = -_snowmanxxx;
         } else if (_snowman.c == gc.d) {
            _snowmanx = _snowmanxxx;
         } else if (_snowman.c == gc.f) {
            _snowmanxx = -_snowmanxxx;
         } else if (_snowman.c == gc.e) {
            _snowmanxx = _snowmanxxx;
         }
      }

      this.c.d = _snowmanx;
      this.c.f = _snowmanxx;
      dfq _snowmanxxx = a.a(_snowman, eao::b);
      this.c.a(_snowman, _snowmanxxx, _snowman, _snowman);
   }
}
