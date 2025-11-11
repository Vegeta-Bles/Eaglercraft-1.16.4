public class dtg {
   public static void a(dwn var0, dwn var1, dwn var2, boolean var3) {
      dwn _snowman = _snowman ? _snowman : _snowman;
      dwn _snowmanx = _snowman ? _snowman : _snowman;
      _snowman.e = (_snowman ? -0.3F : 0.3F) + _snowman.e;
      _snowmanx.e = (_snowman ? 0.6F : -0.6F) + _snowman.e;
      _snowman.d = (float) (-Math.PI / 2) + _snowman.d + 0.1F;
      _snowmanx.d = -1.5F + _snowman.d;
   }

   public static void a(dwn var0, dwn var1, aqm var2, boolean var3) {
      dwn _snowman = _snowman ? _snowman : _snowman;
      dwn _snowmanx = _snowman ? _snowman : _snowman;
      _snowman.e = _snowman ? -0.8F : 0.8F;
      _snowman.d = -0.97079635F;
      _snowmanx.d = _snowman.d;
      float _snowmanxx = (float)bkt.g(_snowman.dY());
      float _snowmanxxx = afm.a((float)_snowman.ea(), 0.0F, _snowmanxx);
      float _snowmanxxxx = _snowmanxxx / _snowmanxx;
      _snowmanx.e = afm.g(_snowmanxxxx, 0.4F, 0.85F) * (float)(_snowman ? 1 : -1);
      _snowmanx.d = afm.g(_snowmanxxxx, _snowmanx.d, (float) (-Math.PI / 2));
   }

   public static <T extends aqn> void a(dwn var0, dwn var1, T var2, float var3, float var4) {
      float _snowman = afm.a(_snowman * (float) Math.PI);
      float _snowmanx = afm.a((1.0F - (1.0F - _snowman) * (1.0F - _snowman)) * (float) Math.PI);
      _snowman.f = 0.0F;
      _snowman.f = 0.0F;
      _snowman.e = (float) (Math.PI / 20);
      _snowman.e = (float) (-Math.PI / 20);
      if (_snowman.dV() == aqi.b) {
         _snowman.d = -1.8849558F + afm.b(_snowman * 0.09F) * 0.15F;
         _snowman.d = -0.0F + afm.b(_snowman * 0.19F) * 0.5F;
         _snowman.d += _snowman * 2.2F - _snowmanx * 0.4F;
         _snowman.d += _snowman * 1.2F - _snowmanx * 0.4F;
      } else {
         _snowman.d = -0.0F + afm.b(_snowman * 0.19F) * 0.5F;
         _snowman.d = -1.8849558F + afm.b(_snowman * 0.09F) * 0.15F;
         _snowman.d += _snowman * 1.2F - _snowmanx * 0.4F;
         _snowman.d += _snowman * 2.2F - _snowmanx * 0.4F;
      }

      a(_snowman, _snowman, _snowman);
   }

   public static void a(dwn var0, dwn var1, float var2) {
      _snowman.f = _snowman.f + afm.b(_snowman * 0.09F) * 0.05F + 0.05F;
      _snowman.f = _snowman.f - (afm.b(_snowman * 0.09F) * 0.05F + 0.05F);
      _snowman.d = _snowman.d + afm.a(_snowman * 0.067F) * 0.05F;
      _snowman.d = _snowman.d - afm.a(_snowman * 0.067F) * 0.05F;
   }

   public static void a(dwn var0, dwn var1, boolean var2, float var3, float var4) {
      float _snowman = afm.a(_snowman * (float) Math.PI);
      float _snowmanx = afm.a((1.0F - (1.0F - _snowman) * (1.0F - _snowman)) * (float) Math.PI);
      _snowman.f = 0.0F;
      _snowman.f = 0.0F;
      _snowman.e = -(0.1F - _snowman * 0.6F);
      _snowman.e = 0.1F - _snowman * 0.6F;
      float _snowmanxx = (float) -Math.PI / (_snowman ? 1.5F : 2.25F);
      _snowman.d = _snowmanxx;
      _snowman.d = _snowmanxx;
      _snowman.d += _snowman * 1.2F - _snowmanx * 0.4F;
      _snowman.d += _snowman * 1.2F - _snowmanx * 0.4F;
      a(_snowman, _snowman, _snowman);
   }
}
