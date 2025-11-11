import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.CubicSampler;

public class dzy {
   private static float a;
   private static float b;
   private static float c;
   private static int d = -1;
   private static int e = -1;
   private static long f = -1L;

   public static void a(djk var0, float var1, dwt var2, int var3, float var4) {
      cux _snowman = _snowman.k();
      if (_snowman.a(aef.b)) {
         long _snowmanx = x.b();
         int _snowmanxx = _snowman.v(new fx(_snowman.b())).n();
         if (f < 0L) {
            d = _snowmanxx;
            e = _snowmanxx;
            f = _snowmanx;
         }

         int _snowmanxxx = d >> 16 & 0xFF;
         int _snowmanxxxx = d >> 8 & 0xFF;
         int _snowmanxxxxx = d & 0xFF;
         int _snowmanxxxxxx = e >> 16 & 0xFF;
         int _snowmanxxxxxxx = e >> 8 & 0xFF;
         int _snowmanxxxxxxxx = e & 0xFF;
         float _snowmanxxxxxxxxx = afm.a((float)(_snowmanx - f) / 5000.0F, 0.0F, 1.0F);
         float _snowmanxxxxxxxxxx = afm.g(_snowmanxxxxxxxxx, (float)_snowmanxxxxxx, (float)_snowmanxxx);
         float _snowmanxxxxxxxxxxx = afm.g(_snowmanxxxxxxxxx, (float)_snowmanxxxxxxx, (float)_snowmanxxxx);
         float _snowmanxxxxxxxxxxxx = afm.g(_snowmanxxxxxxxxx, (float)_snowmanxxxxxxxx, (float)_snowmanxxxxx);
         a = _snowmanxxxxxxxxxx / 255.0F;
         b = _snowmanxxxxxxxxxxx / 255.0F;
         c = _snowmanxxxxxxxxxxxx / 255.0F;
         if (d != _snowmanxx) {
            d = _snowmanxx;
            e = afm.d(_snowmanxxxxxxxxxx) << 16 | afm.d(_snowmanxxxxxxxxxxx) << 8 | afm.d(_snowmanxxxxxxxxxxxx);
            f = _snowmanx;
         }
      } else if (_snowman.a(aef.c)) {
         a = 0.6F;
         b = 0.1F;
         c = 0.0F;
         f = -1L;
      } else {
         float _snowmanxxx = 0.25F + 0.75F * (float)_snowman / 32.0F;
         _snowmanxxx = 1.0F - (float)Math.pow((double)_snowmanxxx, 0.25);
         dcn _snowmanxxxx = _snowman.a(_snowman.c(), _snowman);
         float _snowmanxxxxx = (float)_snowmanxxxx.b;
         float _snowmanxxxxxx = (float)_snowmanxxxx.c;
         float _snowmanxxxxxxx = (float)_snowmanxxxx.d;
         float _snowmanxxxxxxxx = afm.a(afm.b(_snowman.f(_snowman) * (float) (Math.PI * 2)) * 2.0F + 0.5F, 0.0F, 1.0F);
         bsx _snowmanxxxxxxxxx = _snowman.d();
         dcn _snowmanxxxxxxxxxx = _snowman.b().a(2.0, 2.0, 2.0).a(0.25);
         dcn _snowmanxxxxxxxxxxx = CubicSampler.a(_snowmanxxxxxxxxxx, (var3x, var4x, var5x) -> _snowman.a().a(dcn.a(_snowman.a(var3x, var4x, var5x).f()), _snowman));
         a = (float)_snowmanxxxxxxxxxxx.a();
         b = (float)_snowmanxxxxxxxxxxx.b();
         c = (float)_snowmanxxxxxxxxxxx.c();
         if (_snowman >= 4) {
            float _snowmanxxxxxxxxxxxx = afm.a(_snowman.a(_snowman)) > 0.0F ? -1.0F : 1.0F;
            g _snowmanxxxxxxxxxxxxx = new g(_snowmanxxxxxxxxxxxx, 0.0F, 0.0F);
            float _snowmanxxxxxxxxxxxxxx = _snowman.l().c(_snowmanxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx < 0.0F) {
               _snowmanxxxxxxxxxxxxxx = 0.0F;
            }

            if (_snowmanxxxxxxxxxxxxxx > 0.0F) {
               float[] _snowmanxxxxxxxxxxxxxxx = _snowman.a().a(_snowman.f(_snowman), _snowman);
               if (_snowmanxxxxxxxxxxxxxxx != null) {
                  _snowmanxxxxxxxxxxxxxx *= _snowmanxxxxxxxxxxxxxxx[3];
                  a = a * (1.0F - _snowmanxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxx[0] * _snowmanxxxxxxxxxxxxxx;
                  b = b * (1.0F - _snowmanxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxx[1] * _snowmanxxxxxxxxxxxxxx;
                  c = c * (1.0F - _snowmanxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxx[2] * _snowmanxxxxxxxxxxxxxx;
               }
            }
         }

         a = a + (_snowmanxxxxx - a) * _snowmanxxx;
         b = b + (_snowmanxxxxxx - b) * _snowmanxxx;
         c = c + (_snowmanxxxxxxx - c) * _snowmanxxx;
         float _snowmanxxxxxxxxxxxxxxx = _snowman.d(_snowman);
         if (_snowmanxxxxxxxxxxxxxxx > 0.0F) {
            float _snowmanxxxxxxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxxxxxxx * 0.5F;
            float _snowmanxxxxxxxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxxxxxxx * 0.4F;
            a *= _snowmanxxxxxxxxxxxxxxxx;
            b *= _snowmanxxxxxxxxxxxxxxxx;
            c *= _snowmanxxxxxxxxxxxxxxxxx;
         }

         float _snowmanxxxxxxxxxxxxxxxx = _snowman.b(_snowman);
         if (_snowmanxxxxxxxxxxxxxxxx > 0.0F) {
            float _snowmanxxxxxxxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxxxxxxxx * 0.5F;
            a *= _snowmanxxxxxxxxxxxxxxxxx;
            b *= _snowmanxxxxxxxxxxxxxxxxx;
            c *= _snowmanxxxxxxxxxxxxxxxxx;
         }

         f = -1L;
      }

      double _snowmanxxxxxxxxxxxxxxxx = _snowman.b().c * _snowman.w().h();
      if (_snowman.g() instanceof aqm && ((aqm)_snowman.g()).a(apw.o)) {
         int _snowmanxxxxxxxxxxxxxxxxx = ((aqm)_snowman.g()).b(apw.o).b();
         if (_snowmanxxxxxxxxxxxxxxxxx < 20) {
            _snowmanxxxxxxxxxxxxxxxx *= (double)(1.0F - (float)_snowmanxxxxxxxxxxxxxxxxx / 20.0F);
         } else {
            _snowmanxxxxxxxxxxxxxxxx = 0.0;
         }
      }

      if (_snowmanxxxxxxxxxxxxxxxx < 1.0 && !_snowman.a(aef.c)) {
         if (_snowmanxxxxxxxxxxxxxxxx < 0.0) {
            _snowmanxxxxxxxxxxxxxxxx = 0.0;
         }

         _snowmanxxxxxxxxxxxxxxxx *= _snowmanxxxxxxxxxxxxxxxx;
         a = (float)((double)a * _snowmanxxxxxxxxxxxxxxxx);
         b = (float)((double)b * _snowmanxxxxxxxxxxxxxxxx);
         c = (float)((double)c * _snowmanxxxxxxxxxxxxxxxx);
      }

      if (_snowman > 0.0F) {
         a = a * (1.0F - _snowman) + a * 0.7F * _snowman;
         b = b * (1.0F - _snowman) + b * 0.6F * _snowman;
         c = c * (1.0F - _snowman) + c * 0.6F * _snowman;
      }

      if (_snowman.a(aef.b)) {
         float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;
         if (_snowman.g() instanceof dzm) {
            dzm _snowmanxxxxxxxxxxxxxxxxxx = (dzm)_snowman.g();
            _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.N();
         }

         float _snowmanxxxxxxxxxxxxxxxxxx = Math.min(1.0F / a, Math.min(1.0F / b, 1.0F / c));
         a = a * (1.0F - _snowmanxxxxxxxxxxxxxxxxx) + a * _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         b = b * (1.0F - _snowmanxxxxxxxxxxxxxxxxx) + b * _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         c = c * (1.0F - _snowmanxxxxxxxxxxxxxxxxx) + c * _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
      } else if (_snowman.g() instanceof aqm && ((aqm)_snowman.g()).a(apw.p)) {
         float _snowmanxxxxxxxxxxxxxxxxx = dzz.a((aqm)_snowman.g(), _snowman);
         float _snowmanxxxxxxxxxxxxxxxxxx = Math.min(1.0F / a, Math.min(1.0F / b, 1.0F / c));
         a = a * (1.0F - _snowmanxxxxxxxxxxxxxxxxx) + a * _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         b = b * (1.0F - _snowmanxxxxxxxxxxxxxxxxx) + b * _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         c = c * (1.0F - _snowmanxxxxxxxxxxxxxxxxx) + c * _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
      }

      RenderSystem.clearColor(a, b, c, 0.0F);
   }

   public static void a() {
      RenderSystem.fogDensity(0.0F);
      RenderSystem.fogMode(dem.m.c);
   }

   public static void a(djk var0, dzy.a var1, float var2, boolean var3) {
      cux _snowman = _snowman.k();
      aqa _snowmanx = _snowman.g();
      if (_snowman.a(aef.b)) {
         float _snowmanxx = 1.0F;
         _snowmanxx = 0.05F;
         if (_snowmanx instanceof dzm) {
            dzm _snowmanxxx = (dzm)_snowmanx;
            _snowmanxx -= _snowmanxxx.N() * _snowmanxxx.N() * 0.03F;
            bsv _snowmanxxxx = _snowmanxxx.l.v(_snowmanxxx.cB());
            if (_snowmanxxxx.t() == bsv.b.o) {
               _snowmanxx += 0.005F;
            }
         }

         RenderSystem.fogDensity(_snowmanxx);
         RenderSystem.fogMode(dem.m.c);
      } else {
         float _snowmanxx;
         float _snowmanxxx;
         if (_snowman.a(aef.c)) {
            if (_snowmanx instanceof aqm && ((aqm)_snowmanx).a(apw.l)) {
               _snowmanxx = 0.0F;
               _snowmanxxx = 3.0F;
            } else {
               _snowmanxx = 0.25F;
               _snowmanxxx = 1.0F;
            }
         } else if (_snowmanx instanceof aqm && ((aqm)_snowmanx).a(apw.o)) {
            int _snowmanxxxx = ((aqm)_snowmanx).b(apw.o).b();
            float _snowmanxxxxx = afm.g(Math.min(1.0F, (float)_snowmanxxxx / 20.0F), _snowman, 5.0F);
            if (_snowman == dzy.a.a) {
               _snowmanxx = 0.0F;
               _snowmanxxx = _snowmanxxxxx * 0.8F;
            } else {
               _snowmanxx = _snowmanxxxxx * 0.25F;
               _snowmanxxx = _snowmanxxxxx;
            }
         } else if (_snowman) {
            _snowmanxx = _snowman * 0.05F;
            _snowmanxxx = Math.min(_snowman, 192.0F) * 0.5F;
         } else if (_snowman == dzy.a.a) {
            _snowmanxx = 0.0F;
            _snowmanxxx = _snowman;
         } else {
            _snowmanxx = _snowman * 0.75F;
            _snowmanxxx = _snowman;
         }

         RenderSystem.fogStart(_snowmanxx);
         RenderSystem.fogEnd(_snowmanxxx);
         RenderSystem.fogMode(dem.m.a);
         RenderSystem.setupNvFogDistance();
      }
   }

   public static void b() {
      RenderSystem.fog(2918, a, b, c, 1.0F);
   }

   public static enum a {
      a,
      b;

      private a() {
      }
   }
}
