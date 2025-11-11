public class eja<T extends aqm> extends eit<T, dvd<T>> {
   public static final vk a = new vk("textures/entity/trident_riptide.png");
   private final dwn b = new dwn(64, 64, 0, 0);

   public eja(egk<T, dvd<T>> var1) {
      super(_snowman);
      this.b.a(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F);
   }

   public void a(dfm var1, eag var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (_snowman.dR()) {
         dfq _snowman = _snowman.getBuffer(eao.d(a));

         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            _snowman.a();
            float _snowmanxx = _snowman * (float)(-(45 + _snowmanx * 5));
            _snowman.a(g.d.a(_snowmanxx));
            float _snowmanxxx = 0.75F * (float)_snowmanx;
            _snowman.a(_snowmanxxx, _snowmanxxx, _snowmanxxx);
            _snowman.a(0.0, (double)(-0.2F + 0.6F * (float)_snowmanx), 0.0);
            this.b.a(_snowman, _snowman, _snowman, ejw.a);
            _snowman.b();
         }
      }
   }
}
