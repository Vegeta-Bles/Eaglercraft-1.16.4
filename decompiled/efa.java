public class efa extends eeu<bgi> {
   private static final vk a = new vk("textures/entity/fishing_hook.png");
   private static final eao e = eao.c(a);

   public efa(eet var1) {
      super(_snowman);
   }

   public void a(bgi var1, float var2, float var3, dfm var4, eag var5, int var6) {
      bfw _snowman = _snowman.i();
      if (_snowman != null) {
         _snowman.a();
         _snowman.a();
         _snowman.a(0.5F, 0.5F, 0.5F);
         _snowman.a(this.b.b());
         _snowman.a(g.d.a(180.0F));
         dfm.a _snowmanx = _snowman.c();
         b _snowmanxx = _snowmanx.a();
         a _snowmanxxx = _snowmanx.b();
         dfq _snowmanxxxx = _snowman.getBuffer(e);
         a(_snowmanxxxx, _snowmanxx, _snowmanxxx, _snowman, 0.0F, 0, 0, 1);
         a(_snowmanxxxx, _snowmanxx, _snowmanxxx, _snowman, 1.0F, 0, 1, 1);
         a(_snowmanxxxx, _snowmanxx, _snowmanxxx, _snowman, 1.0F, 1, 1, 0);
         a(_snowmanxxxx, _snowmanxx, _snowmanxxx, _snowman, 0.0F, 1, 0, 0);
         _snowman.b();
         int _snowmanxxxxx = _snowman.dV() == aqi.b ? 1 : -1;
         bmb _snowmanxxxxxx = _snowman.dD();
         if (_snowmanxxxxxx.b() != bmd.mi) {
            _snowmanxxxxx = -_snowmanxxxxx;
         }

         float _snowmanxxxxxxx = _snowman.r(_snowman);
         float _snowmanxxxxxxxx = afm.a(afm.c(_snowmanxxxxxxx) * (float) Math.PI);
         float _snowmanxxxxxxxxx = afm.g(_snowman, _snowman.aB, _snowman.aA) * (float) (Math.PI / 180.0);
         double _snowmanxxxxxxxxxx = (double)afm.a(_snowmanxxxxxxxxx);
         double _snowmanxxxxxxxxxxx = (double)afm.b(_snowmanxxxxxxxxx);
         double _snowmanxxxxxxxxxxxx = (double)_snowmanxxxxx * 0.35;
         double _snowmanxxxxxxxxxxxxx = 0.8;
         double _snowmanxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxx;
         if ((this.b.d == null || this.b.d.g().a()) && _snowman == djz.C().s) {
            double _snowmanxxxxxxxxxxxxxxxxxx = this.b.d.aO;
            _snowmanxxxxxxxxxxxxxxxxxx /= 100.0;
            dcn _snowmanxxxxxxxxxxxxxxxxxxx = new dcn((double)_snowmanxxxxx * -0.36 * _snowmanxxxxxxxxxxxxxxxxxx, -0.045 * _snowmanxxxxxxxxxxxxxxxxxx, 0.4);
            _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.a(-afm.g(_snowman, _snowman.s, _snowman.q) * (float) (Math.PI / 180.0));
            _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.b(-afm.g(_snowman, _snowman.r, _snowman.p) * (float) (Math.PI / 180.0));
            _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxx * 0.5F);
            _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.a(-_snowmanxxxxxxxx * 0.7F);
            _snowmanxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.m, _snowman.cD()) + _snowmanxxxxxxxxxxxxxxxxxxx.b;
            _snowmanxxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.n, _snowman.cE()) + _snowmanxxxxxxxxxxxxxxxxxxx.c;
            _snowmanxxxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.o, _snowman.cH()) + _snowmanxxxxxxxxxxxxxxxxxxx.d;
            _snowmanxxxxxxxxxxxxxxxxx = _snowman.ce();
         } else {
            _snowmanxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.m, _snowman.cD()) - _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxxx * 0.8;
            _snowmanxxxxxxxxxxxxxxx = _snowman.n + (double)_snowman.ce() + (_snowman.cE() - _snowman.n) * (double)_snowman - 0.45;
            _snowmanxxxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.o, _snowman.cH()) - _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxx * 0.8;
            _snowmanxxxxxxxxxxxxxxxxx = _snowman.bz() ? -0.1875F : 0.0F;
         }

         double _snowmanxxxxxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.m, _snowman.cD());
         double _snowmanxxxxxxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.n, _snowman.cE()) + 0.25;
         double _snowmanxxxxxxxxxxxxxxxxxxxx = afm.d((double)_snowman, _snowman.o, _snowman.cH());
         float _snowmanxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
         float _snowmanxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxx);
         dfq _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(eao.t());
         b _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.c().a();
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = 16;

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
            a(
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
               a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 16)
            );
            a(
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
               a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1, 16)
            );
         }

         _snowman.b();
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private static float a(int var0, int var1) {
      return (float)_snowman / (float)_snowman;
   }

   private static void a(dfq var0, b var1, a var2, int var3, float var4, int var5, int var6, int var7) {
      _snowman.a(_snowman, _snowman - 0.5F, (float)_snowman - 0.5F, 0.0F).a(255, 255, 255, 255).a((float)_snowman, (float)_snowman).b(ejw.a).a(_snowman).a(_snowman, 0.0F, 1.0F, 0.0F).d();
   }

   private static void a(float var0, float var1, float var2, dfq var3, b var4, float var5) {
      _snowman.a(_snowman, _snowman * _snowman, _snowman * (_snowman * _snowman + _snowman) * 0.5F + 0.25F, _snowman * _snowman).a(0, 0, 0, 255).d();
   }

   public vk a(bgi var1) {
      return a;
   }
}
