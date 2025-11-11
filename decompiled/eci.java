public class eci extends ece<ccq> {
   public static final elr a = new elr(ekb.d, new vk("entity/conduit/base"));
   public static final elr c = new elr(ekb.d, new vk("entity/conduit/cage"));
   public static final elr d = new elr(ekb.d, new vk("entity/conduit/wind"));
   public static final elr e = new elr(ekb.d, new vk("entity/conduit/wind_vertical"));
   public static final elr f = new elr(ekb.d, new vk("entity/conduit/open_eye"));
   public static final elr g = new elr(ekb.d, new vk("entity/conduit/closed_eye"));
   private final dwn h = new dwn(16, 16, 0, 0);
   private final dwn i;
   private final dwn j;
   private final dwn k;

   public eci(ecd var1) {
      super(_snowman);
      this.h.a(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.01F);
      this.i = new dwn(64, 32, 0, 0);
      this.i.a(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
      this.j = new dwn(32, 16, 0, 0);
      this.j.a(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      this.k = new dwn(32, 16, 0, 0);
      this.k.a(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
   }

   public void a(ccq var1, float var2, dfm var3, eag var4, int var5, int var6) {
      float _snowman = (float)_snowman.a + _snowman;
      if (!_snowman.d()) {
         float _snowmanx = _snowman.a(0.0F);
         dfq _snowmanxx = a.a(_snowman, eao::b);
         _snowman.a();
         _snowman.a(0.5, 0.5, 0.5);
         _snowman.a(g.d.a(_snowmanx));
         this.j.a(_snowman, _snowmanxx, _snowman, _snowman);
         _snowman.b();
      } else {
         float _snowmanx = _snowman.a(_snowman) * (180.0F / (float)Math.PI);
         float _snowmanxx = afm.a(_snowman * 0.1F) / 2.0F + 0.5F;
         _snowmanxx = _snowmanxx * _snowmanxx + _snowmanxx;
         _snowman.a();
         _snowman.a(0.5, (double)(0.3F + _snowmanxx * 0.2F), 0.5);
         g _snowmanxxx = new g(0.5F, 1.0F, 0.5F);
         _snowmanxxx.d();
         _snowman.a(new d(_snowmanxxx, _snowmanx, true));
         this.k.a(_snowman, c.a(_snowman, eao::d), _snowman, _snowman);
         _snowman.b();
         int _snowmanxxxx = _snowman.a / 66 % 3;
         _snowman.a();
         _snowman.a(0.5, 0.5, 0.5);
         if (_snowmanxxxx == 1) {
            _snowman.a(g.b.a(90.0F));
         } else if (_snowmanxxxx == 2) {
            _snowman.a(g.f.a(90.0F));
         }

         dfq _snowmanxxxxx = (_snowmanxxxx == 1 ? e : d).a(_snowman, eao::d);
         this.i.a(_snowman, _snowmanxxxxx, _snowman, _snowman);
         _snowman.b();
         _snowman.a();
         _snowman.a(0.5, 0.5, 0.5);
         _snowman.a(0.875F, 0.875F, 0.875F);
         _snowman.a(g.b.a(180.0F));
         _snowman.a(g.f.a(180.0F));
         this.i.a(_snowman, _snowmanxxxxx, _snowman, _snowman);
         _snowman.b();
         djk _snowmanxxxxxx = this.b.d;
         _snowman.a();
         _snowman.a(0.5, (double)(0.3F + _snowmanxx * 0.2F), 0.5);
         _snowman.a(0.5F, 0.5F, 0.5F);
         float _snowmanxxxxxxx = -_snowmanxxxxxx.e();
         _snowman.a(g.d.a(_snowmanxxxxxxx));
         _snowman.a(g.b.a(_snowmanxxxxxx.d()));
         _snowman.a(g.f.a(180.0F));
         float _snowmanxxxxxxxx = 1.3333334F;
         _snowman.a(1.3333334F, 1.3333334F, 1.3333334F);
         this.h.a(_snowman, (_snowman.f() ? f : g).a(_snowman, eao::d), _snowman, _snowman);
         _snowman.b();
      }
   }
}
