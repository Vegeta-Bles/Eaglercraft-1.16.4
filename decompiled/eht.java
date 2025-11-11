public class eht<T extends aqm, M extends dvd<T>> extends ejc<T, M> {
   private static final vk a = new vk("textures/entity/bee/bee_stinger.png");

   public eht(efr<T, M> var1) {
      super(_snowman);
   }

   @Override
   protected int a(T var1) {
      return _snowman.dz();
   }

   @Override
   protected void a(dfm var1, eag var2, int var3, aqa var4, float var5, float var6, float var7, float var8) {
      float _snowman = afm.c(_snowman * _snowman + _snowman * _snowman);
      float _snowmanx = (float)(Math.atan2((double)_snowman, (double)_snowman) * 180.0F / (float)Math.PI);
      float _snowmanxx = (float)(Math.atan2((double)_snowman, (double)_snowman) * 180.0F / (float)Math.PI);
      _snowman.a(0.0, 0.0, 0.0);
      _snowman.a(g.d.a(_snowmanx - 90.0F));
      _snowman.a(g.f.a(_snowmanxx));
      float _snowmanxxx = 0.0F;
      float _snowmanxxxx = 0.125F;
      float _snowmanxxxxx = 0.0F;
      float _snowmanxxxxxx = 0.0625F;
      float _snowmanxxxxxxx = 0.03125F;
      _snowman.a(g.b.a(45.0F));
      _snowman.a(0.03125F, 0.03125F, 0.03125F);
      _snowman.a(2.5, 0.0, 0.0);
      dfq _snowmanxxxxxxxx = _snowman.getBuffer(eao.d(a));

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 4; _snowmanxxxxxxxxx++) {
         _snowman.a(g.b.a(90.0F));
         dfm.a _snowmanxxxxxxxxxx = _snowman.c();
         b _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.a();
         a _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.b();
         a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, -4.5F, -1, 0.0F, 0.0F, _snowman);
         a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 4.5F, -1, 0.125F, 0.0F, _snowman);
         a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 4.5F, 1, 0.125F, 0.0625F, _snowman);
         a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, -4.5F, 1, 0.0F, 0.0625F, _snowman);
      }
   }

   private static void a(dfq var0, b var1, a var2, float var3, int var4, float var5, float var6, int var7) {
      _snowman.a(_snowman, _snowman, (float)_snowman, 0.0F).a(255, 255, 255, 255).a(_snowman, _snowman).b(ejw.a).a(_snowman).a(_snowman, 0.0F, 1.0F, 0.0F).d();
   }
}
