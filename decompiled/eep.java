public class eep extends eeu<bbq> {
   private static final vk a = new vk("textures/entity/end_crystal/end_crystal.png");
   private static final eao e = eao.d(a);
   private static final float f = (float)Math.sin(Math.PI / 4);
   private final dwn g;
   private final dwn h;
   private final dwn i;

   public eep(eet var1) {
      super(_snowman);
      this.c = 0.5F;
      this.h = new dwn(64, 32, 0, 0);
      this.h.a(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.g = new dwn(64, 32, 32, 0);
      this.g.a(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.i = new dwn(64, 32, 0, 16);
      this.i.a(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F);
   }

   public void a(bbq var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      float _snowman = a(_snowman, _snowman);
      float _snowmanx = ((float)_snowman.b + _snowman) * 3.0F;
      dfq _snowmanxx = _snowman.getBuffer(e);
      _snowman.a();
      _snowman.a(2.0F, 2.0F, 2.0F);
      _snowman.a(0.0, -0.5, 0.0);
      int _snowmanxxx = ejw.a;
      if (_snowman.h()) {
         this.i.a(_snowman, _snowmanxx, _snowman, _snowmanxxx);
      }

      _snowman.a(g.d.a(_snowmanx));
      _snowman.a(0.0, (double)(1.5F + _snowman / 2.0F), 0.0);
      _snowman.a(new d(new g(f, 0.0F, f), 60.0F, true));
      this.h.a(_snowman, _snowmanxx, _snowman, _snowmanxxx);
      float _snowmanxxxx = 0.875F;
      _snowman.a(0.875F, 0.875F, 0.875F);
      _snowman.a(new d(new g(f, 0.0F, f), 60.0F, true));
      _snowman.a(g.d.a(_snowmanx));
      this.h.a(_snowman, _snowmanxx, _snowman, _snowmanxxx);
      _snowman.a(0.875F, 0.875F, 0.875F);
      _snowman.a(new d(new g(f, 0.0F, f), 60.0F, true));
      _snowman.a(g.d.a(_snowmanx));
      this.g.a(_snowman, _snowmanxx, _snowman, _snowmanxxx);
      _snowman.b();
      _snowman.b();
      fx _snowmanxxxxx = _snowman.g();
      if (_snowmanxxxxx != null) {
         float _snowmanxxxxxx = (float)_snowmanxxxxx.u() + 0.5F;
         float _snowmanxxxxxxx = (float)_snowmanxxxxx.v() + 0.5F;
         float _snowmanxxxxxxxx = (float)_snowmanxxxxx.w() + 0.5F;
         float _snowmanxxxxxxxxx = (float)((double)_snowmanxxxxxx - _snowman.cD());
         float _snowmanxxxxxxxxxx = (float)((double)_snowmanxxxxxxx - _snowman.cE());
         float _snowmanxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxx - _snowman.cH());
         _snowman.a((double)_snowmanxxxxxxxxx, (double)_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxx);
         eeq.a(-_snowmanxxxxxxxxx, -_snowmanxxxxxxxxxx + _snowman, -_snowmanxxxxxxxxxxx, _snowman, _snowman.b, _snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static float a(bbq var0, float var1) {
      float _snowman = (float)_snowman.b + _snowman;
      float _snowmanx = afm.a(_snowman * 0.2F) / 2.0F + 0.5F;
      _snowmanx = (_snowmanx * _snowmanx + _snowmanx) * 0.4F;
      return _snowmanx - 1.4F;
   }

   public vk a(bbq var1) {
      return a;
   }

   public boolean a(bbq var1, ecz var2, double var3, double var5, double var7) {
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman) || _snowman.g() != null;
   }
}
