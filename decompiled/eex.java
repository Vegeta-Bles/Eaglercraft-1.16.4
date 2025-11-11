public class eex extends eeu<aqg> {
   private static final vk a = new vk("textures/entity/experience_orb.png");
   private static final eao e = eao.f(a);

   public eex(eet var1) {
      super(_snowman);
      this.c = 0.15F;
      this.d = 0.75F;
   }

   protected int a(aqg var1, fx var2) {
      return afm.a(super.a(_snowman, _snowman) + 7, 0, 15);
   }

   public void a(aqg var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      int _snowman = _snowman.h();
      float _snowmanx = (float)(_snowman % 4 * 16 + 0) / 64.0F;
      float _snowmanxx = (float)(_snowman % 4 * 16 + 16) / 64.0F;
      float _snowmanxxx = (float)(_snowman / 4 * 16 + 0) / 64.0F;
      float _snowmanxxxx = (float)(_snowman / 4 * 16 + 16) / 64.0F;
      float _snowmanxxxxx = 1.0F;
      float _snowmanxxxxxx = 0.5F;
      float _snowmanxxxxxxx = 0.25F;
      float _snowmanxxxxxxxx = 255.0F;
      float _snowmanxxxxxxxxx = ((float)_snowman.b + _snowman) / 2.0F;
      int _snowmanxxxxxxxxxx = (int)((afm.a(_snowmanxxxxxxxxx + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int _snowmanxxxxxxxxxxx = 255;
      int _snowmanxxxxxxxxxxxx = (int)((afm.a(_snowmanxxxxxxxxx + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
      _snowman.a(0.0, 0.1F, 0.0);
      _snowman.a(this.b.b());
      _snowman.a(g.d.a(180.0F));
      float _snowmanxxxxxxxxxxxxx = 0.3F;
      _snowman.a(0.3F, 0.3F, 0.3F);
      dfq _snowmanxxxxxxxxxxxxxx = _snowman.getBuffer(e);
      dfm.a _snowmanxxxxxxxxxxxxxxx = _snowman.c();
      b _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.a();
      a _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.b();
      a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -0.5F, -0.25F, _snowmanxxxxxxxxxx, 255, _snowmanxxxxxxxxxxxx, _snowmanx, _snowmanxxxx, _snowman);
      a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, 0.5F, -0.25F, _snowmanxxxxxxxxxx, 255, _snowmanxxxxxxxxxxxx, _snowmanxx, _snowmanxxxx, _snowman);
      a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, 0.5F, 0.75F, _snowmanxxxxxxxxxx, 255, _snowmanxxxxxxxxxxxx, _snowmanxx, _snowmanxxx, _snowman);
      a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -0.5F, 0.75F, _snowmanxxxxxxxxxx, 255, _snowmanxxxxxxxxxxxx, _snowmanx, _snowmanxxx, _snowman);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void a(dfq var0, b var1, a var2, float var3, float var4, int var5, int var6, int var7, float var8, float var9, int var10) {
      _snowman.a(_snowman, _snowman, _snowman, 0.0F).a(_snowman, _snowman, _snowman, 128).a(_snowman, _snowman).b(ejw.a).a(_snowman).a(_snowman, 0.0F, 1.0F, 0.0F).d();
   }

   public vk a(aqg var1) {
      return a;
   }
}
