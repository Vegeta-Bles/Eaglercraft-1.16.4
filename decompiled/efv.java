public class efv<T extends bhl> extends eeu<T> {
   private static final vk e = new vk("textures/entity/minecart.png");
   protected final duc<T> a = new duu<>();

   public efv(eet var1) {
      super(_snowman);
      this.c = 0.7F;
   }

   public void a(T var1, float var2, float var3, dfm var4, eag var5, int var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.a();
      long _snowman = (long)_snowman.Y() * 493286711L;
      _snowman = _snowman * _snowman * 4392167121L + _snowman * 98761L;
      float _snowmanx = (((float)(_snowman >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float _snowmanxx = (((float)(_snowman >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float _snowmanxxx = (((float)(_snowman >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      _snowman.a((double)_snowmanx, (double)_snowmanxx, (double)_snowmanxxx);
      double _snowmanxxxx = afm.d((double)_snowman, _snowman.D, _snowman.cD());
      double _snowmanxxxxx = afm.d((double)_snowman, _snowman.E, _snowman.cE());
      double _snowmanxxxxxx = afm.d((double)_snowman, _snowman.F, _snowman.cH());
      double _snowmanxxxxxxx = 0.3F;
      dcn _snowmanxxxxxxxx = _snowman.p(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      float _snowmanxxxxxxxxx = afm.g(_snowman, _snowman.s, _snowman.q);
      if (_snowmanxxxxxxxx != null) {
         dcn _snowmanxxxxxxxxxx = _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, 0.3F);
         dcn _snowmanxxxxxxxxxxx = _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, -0.3F);
         if (_snowmanxxxxxxxxxx == null) {
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxx;
         }

         if (_snowmanxxxxxxxxxxx == null) {
            _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx;
         }

         _snowman.a(_snowmanxxxxxxxx.b - _snowmanxxxx, (_snowmanxxxxxxxxxx.c + _snowmanxxxxxxxxxxx.c) / 2.0 - _snowmanxxxxx, _snowmanxxxxxxxx.d - _snowmanxxxxxx);
         dcn _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.b(-_snowmanxxxxxxxxxx.b, -_snowmanxxxxxxxxxx.c, -_snowmanxxxxxxxxxx.d);
         if (_snowmanxxxxxxxxxxxx.f() != 0.0) {
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.d();
            _snowman = (float)(Math.atan2(_snowmanxxxxxxxxxxxx.d, _snowmanxxxxxxxxxxxx.b) * 180.0 / Math.PI);
            _snowmanxxxxxxxxx = (float)(Math.atan(_snowmanxxxxxxxxxxxx.c) * 73.0);
         }
      }

      _snowman.a(0.0, 0.375, 0.0);
      _snowman.a(g.d.a(180.0F - _snowman));
      _snowman.a(g.f.a(-_snowmanxxxxxxxxx));
      float _snowmanxxxxxxxxxxxx = (float)_snowman.m() - _snowman;
      float _snowmanxxxxxxxxxxxxx = _snowman.k() - _snowman;
      if (_snowmanxxxxxxxxxxxxx < 0.0F) {
         _snowmanxxxxxxxxxxxxx = 0.0F;
      }

      if (_snowmanxxxxxxxxxxxx > 0.0F) {
         _snowman.a(g.b.a(afm.a(_snowmanxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx / 10.0F * (float)_snowman.n()));
      }

      int _snowmanxxxxxxxxxxxxxx = _snowman.r();
      ceh _snowmanxxxxxxxxxxxxxxx = _snowman.p();
      if (_snowmanxxxxxxxxxxxxxxx.h() != bzh.a) {
         _snowman.a();
         float _snowmanxxxxxxxxxxxxxxxx = 0.75F;
         _snowman.a(0.75F, 0.75F, 0.75F);
         _snowman.a(-0.5, (double)((float)(_snowmanxxxxxxxxxxxxxx - 8) / 16.0F), 0.5);
         _snowman.a(g.d.a(90.0F));
         this.a(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman);
         _snowman.b();
      }

      _snowman.a(-1.0F, -1.0F, 1.0F);
      this.a.a(_snowman, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      dfq _snowmanxxxxxxxxxxxxxxxx = _snowman.getBuffer(this.a.a(this.a(_snowman)));
      this.a.a(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.b();
   }

   public vk a(T var1) {
      return e;
   }

   protected void a(T var1, float var2, ceh var3, dfm var4, eag var5, int var6) {
      djz.C().ab().a(_snowman, _snowman, _snowman, _snowman, ejw.a);
   }
}
