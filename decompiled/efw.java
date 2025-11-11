public abstract class efw<T extends aqn, M extends duc<T>> extends efr<T, M> {
   public efw(eet var1, M var2, float var3) {
      super(_snowman, _snowman, _snowman);
   }

   protected boolean b(T var1) {
      return super.b(_snowman) && (_snowman.bY() || _snowman.S() && _snowman == this.b.c);
   }

   public boolean a(T var1, ecz var2, double var3, double var5, double var7) {
      if (super.a(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else {
         aqa _snowman = _snowman.eC();
         return _snowman != null ? _snowman.a(_snowman.cd()) : false;
      }
   }

   public void a(T var1, float var2, float var3, dfm var4, eag var5, int var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      aqa _snowman = _snowman.eC();
      if (_snowman != null) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private <E extends aqa> void a(T var1, float var2, dfm var3, eag var4, E var5) {
      _snowman.a();
      dcn _snowman = _snowman.o(_snowman);
      double _snowmanx = (double)(afm.g(_snowman, _snowman.aA, _snowman.aB) * (float) (Math.PI / 180.0)) + (Math.PI / 2);
      dcn _snowmanxx = _snowman.cf();
      double _snowmanxxx = Math.cos(_snowmanx) * _snowmanxx.d + Math.sin(_snowmanx) * _snowmanxx.b;
      double _snowmanxxxx = Math.sin(_snowmanx) * _snowmanxx.d - Math.cos(_snowmanx) * _snowmanxx.b;
      double _snowmanxxxxx = afm.d((double)_snowman, _snowman.m, _snowman.cD()) + _snowmanxxx;
      double _snowmanxxxxxx = afm.d((double)_snowman, _snowman.n, _snowman.cE()) + _snowmanxx.c;
      double _snowmanxxxxxxx = afm.d((double)_snowman, _snowman.o, _snowman.cH()) + _snowmanxxxx;
      _snowman.a(_snowmanxxx, _snowmanxx.c, _snowmanxxxx);
      float _snowmanxxxxxxxx = (float)(_snowman.b - _snowmanxxxxx);
      float _snowmanxxxxxxxxx = (float)(_snowman.c - _snowmanxxxxxx);
      float _snowmanxxxxxxxxxx = (float)(_snowman.d - _snowmanxxxxxxx);
      float _snowmanxxxxxxxxxxx = 0.025F;
      dfq _snowmanxxxxxxxxxxxx = _snowman.getBuffer(eao.i());
      b _snowmanxxxxxxxxxxxxx = _snowman.c().a();
      float _snowmanxxxxxxxxxxxxxx = afm.i(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx) * 0.025F / 2.0F;
      float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx * _snowmanxxxxxxxxxxxxxx;
      fx _snowmanxxxxxxxxxxxxxxxxx = new fx(_snowman.j(_snowman));
      fx _snowmanxxxxxxxxxxxxxxxxxx = new fx(_snowman.j(_snowman));
      int _snowmanxxxxxxxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxx = this.b.a(_snowman).a(_snowman, _snowmanxxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.l.a(bsf.a, _snowmanxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.l.a(bsf.a, _snowmanxxxxxxxxxxxxxxxxxx);
      a(
         _snowmanxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxx,
         _snowmanxxxxxxxx,
         _snowmanxxxxxxxxx,
         _snowmanxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxx,
         0.025F,
         0.025F,
         _snowmanxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxx
      );
      a(
         _snowmanxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxx,
         _snowmanxxxxxxxx,
         _snowmanxxxxxxxxx,
         _snowmanxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxx,
         0.025F,
         0.0F,
         _snowmanxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxx
      );
      _snowman.b();
   }

   public static void a(
      dfq var0, b var1, float var2, float var3, float var4, int var5, int var6, int var7, int var8, float var9, float var10, float var11, float var12
   ) {
      int _snowman = 24;

      for (int _snowmanx = 0; _snowmanx < 24; _snowmanx++) {
         float _snowmanxx = (float)_snowmanx / 23.0F;
         int _snowmanxxx = (int)afm.g(_snowmanxx, (float)_snowman, (float)_snowman);
         int _snowmanxxxx = (int)afm.g(_snowmanxx, (float)_snowman, (float)_snowman);
         int _snowmanxxxxx = eaf.a(_snowmanxxx, _snowmanxxxx);
         a(_snowman, _snowman, _snowmanxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, 24, _snowmanx, false, _snowman, _snowman);
         a(_snowman, _snowman, _snowmanxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, 24, _snowmanx + 1, true, _snowman, _snowman);
      }
   }

   public static void a(
      dfq var0, b var1, int var2, float var3, float var4, float var5, float var6, float var7, int var8, int var9, boolean var10, float var11, float var12
   ) {
      float _snowman = 0.5F;
      float _snowmanx = 0.4F;
      float _snowmanxx = 0.3F;
      if (_snowman % 2 == 0) {
         _snowman *= 0.7F;
         _snowmanx *= 0.7F;
         _snowmanxx *= 0.7F;
      }

      float _snowmanxxx = (float)_snowman / (float)_snowman;
      float _snowmanxxxx = _snowman * _snowmanxxx;
      float _snowmanxxxxx = _snowman > 0.0F ? _snowman * _snowmanxxx * _snowmanxxx : _snowman - _snowman * (1.0F - _snowmanxxx) * (1.0F - _snowmanxxx);
      float _snowmanxxxxxx = _snowman * _snowmanxxx;
      if (!_snowman) {
         _snowman.a(_snowman, _snowmanxxxx + _snowman, _snowmanxxxxx + _snowman - _snowman, _snowmanxxxxxx - _snowman).a(_snowman, _snowmanx, _snowmanxx, 1.0F).a(_snowman).d();
      }

      _snowman.a(_snowman, _snowmanxxxx - _snowman, _snowmanxxxxx + _snowman, _snowmanxxxxxx + _snowman).a(_snowman, _snowmanx, _snowmanxx, 1.0F).a(_snowman).d();
      if (_snowman) {
         _snowman.a(_snowman, _snowmanxxxx + _snowman, _snowmanxxxxx + _snowman - _snowman, _snowmanxxxxxx - _snowman).a(_snowman, _snowmanx, _snowmanxx, 1.0F).a(_snowman).d();
      }
   }
}
