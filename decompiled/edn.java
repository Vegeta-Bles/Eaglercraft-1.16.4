import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;
import java.util.Map;

public class edn implements edh.a {
   private final Map<Integer, cxd> a = Maps.newHashMap();
   private final Map<Integer, Float> b = Maps.newHashMap();
   private final Map<Integer, Long> c = Maps.newHashMap();

   public edn() {
   }

   public void a(int var1, cxd var2, float var3) {
      this.a.put(_snowman, _snowman);
      this.c.put(_snowman, x.b());
      this.b.put(_snowman, _snowman);
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      if (!this.a.isEmpty()) {
         long _snowman = x.b();

         for (Integer _snowmanx : this.a.keySet()) {
            cxd _snowmanxx = this.a.get(_snowmanx);
            float _snowmanxxx = this.b.get(_snowmanx);
            a(_snowmanxx, _snowmanxxx, true, true, _snowman, _snowman, _snowman);
         }

         for (Integer _snowmanx : this.c.keySet().toArray(new Integer[0])) {
            if (_snowman - this.c.get(_snowmanx) > 5000L) {
               this.a.remove(_snowmanx);
               this.c.remove(_snowmanx);
            }
         }
      }
   }

   public static void a(cxd var0, float var1, boolean var2, boolean var3, double var4, double var6, double var8) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);
      b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   private static void b(cxd var0, float var1, boolean var2, boolean var3, double var4, double var6, double var8) {
      a(_snowman, _snowman, _snowman, _snowman);
      fx _snowman = _snowman.m();
      if (a(_snowman, _snowman, _snowman, _snowman) <= 80.0F) {
         edh.a(
            new dci(
                  (double)((float)_snowman.u() + 0.25F),
                  (double)((float)_snowman.v() + 0.25F),
                  (double)_snowman.w() + 0.25,
                  (double)((float)_snowman.u() + 0.75F),
                  (double)((float)_snowman.v() + 0.75F),
                  (double)((float)_snowman.w() + 0.75F)
               )
               .d(-_snowman, -_snowman, -_snowman),
            0.0F,
            1.0F,
            0.0F,
            0.5F
         );

         for (int _snowmanx = 0; _snowmanx < _snowman.e(); _snowmanx++) {
            cxb _snowmanxx = _snowman.a(_snowmanx);
            if (a(_snowmanxx.a(), _snowman, _snowman, _snowman) <= 80.0F) {
               float _snowmanxxx = _snowmanx == _snowman.f() ? 1.0F : 0.0F;
               float _snowmanxxxx = _snowmanx == _snowman.f() ? 0.0F : 1.0F;
               edh.a(
                  new dci(
                        (double)((float)_snowmanxx.a + 0.5F - _snowman),
                        (double)((float)_snowmanxx.b + 0.01F * (float)_snowmanx),
                        (double)((float)_snowmanxx.c + 0.5F - _snowman),
                        (double)((float)_snowmanxx.a + 0.5F + _snowman),
                        (double)((float)_snowmanxx.b + 0.25F + 0.01F * (float)_snowmanx),
                        (double)((float)_snowmanxx.c + 0.5F + _snowman)
                     )
                     .d(-_snowman, -_snowman, -_snowman),
                  _snowmanxxx,
                  0.0F,
                  _snowmanxxxx,
                  0.5F
               );
            }
         }
      }

      if (_snowman) {
         for (cxb _snowmanxx : _snowman.l()) {
            if (a(_snowmanxx.a(), _snowman, _snowman, _snowman) <= 80.0F) {
               edh.a(
                  new dci(
                        (double)((float)_snowmanxx.a + 0.5F - _snowman / 2.0F),
                        (double)((float)_snowmanxx.b + 0.01F),
                        (double)((float)_snowmanxx.c + 0.5F - _snowman / 2.0F),
                        (double)((float)_snowmanxx.a + 0.5F + _snowman / 2.0F),
                        (double)_snowmanxx.b + 0.1,
                        (double)((float)_snowmanxx.c + 0.5F + _snowman / 2.0F)
                     )
                     .d(-_snowman, -_snowman, -_snowman),
                  1.0F,
                  0.8F,
                  0.8F,
                  0.5F
               );
            }
         }

         for (cxb _snowmanxxx : _snowman.k()) {
            if (a(_snowmanxxx.a(), _snowman, _snowman, _snowman) <= 80.0F) {
               edh.a(
                  new dci(
                        (double)((float)_snowmanxxx.a + 0.5F - _snowman / 2.0F),
                        (double)((float)_snowmanxxx.b + 0.01F),
                        (double)((float)_snowmanxxx.c + 0.5F - _snowman / 2.0F),
                        (double)((float)_snowmanxxx.a + 0.5F + _snowman / 2.0F),
                        (double)_snowmanxxx.b + 0.1,
                        (double)((float)_snowmanxxx.c + 0.5F + _snowman / 2.0F)
                     )
                     .d(-_snowman, -_snowman, -_snowman),
                  0.8F,
                  1.0F,
                  1.0F,
                  0.5F
               );
            }
         }
      }

      if (_snowman) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.e(); _snowmanxxxx++) {
            cxb _snowmanxxxxx = _snowman.a(_snowmanxxxx);
            if (a(_snowmanxxxxx.a(), _snowman, _snowman, _snowman) <= 80.0F) {
               edh.a(String.format("%s", _snowmanxxxxx.l), (double)_snowmanxxxxx.a + 0.5, (double)_snowmanxxxxx.b + 0.75, (double)_snowmanxxxxx.c + 0.5, -1, 0.02F, true, 0.0F, true);
               edh.a(
                  String.format(Locale.ROOT, "%.2f", _snowmanxxxxx.k),
                  (double)_snowmanxxxxx.a + 0.5,
                  (double)_snowmanxxxxx.b + 0.25,
                  (double)_snowmanxxxxx.c + 0.5,
                  -1,
                  0.02F,
                  true,
                  0.0F,
                  true
               );
            }
         }
      }
   }

   public static void a(cxd var0, double var1, double var3, double var5) {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      _snowmanx.a(3, dfk.l);

      for (int _snowmanxx = 0; _snowmanxx < _snowman.e(); _snowmanxx++) {
         cxb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!(a(_snowmanxxx.a(), _snowman, _snowman, _snowman) > 80.0F)) {
            float _snowmanxxxx = (float)_snowmanxx / (float)_snowman.e() * 0.33F;
            int _snowmanxxxxx = _snowmanxx == 0 ? 0 : afm.f(_snowmanxxxx, 0.9F, 0.9F);
            int _snowmanxxxxxx = _snowmanxxxxx >> 16 & 0xFF;
            int _snowmanxxxxxxx = _snowmanxxxxx >> 8 & 0xFF;
            int _snowmanxxxxxxxx = _snowmanxxxxx & 0xFF;
            _snowmanx.a((double)_snowmanxxx.a - _snowman + 0.5, (double)_snowmanxxx.b - _snowman + 0.5, (double)_snowmanxxx.c - _snowman + 0.5).a(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 255).d();
         }
      }

      _snowman.b();
   }

   private static float a(fx var0, double var1, double var3, double var5) {
      return (float)(Math.abs((double)_snowman.u() - _snowman) + Math.abs((double)_snowman.v() - _snowman) + Math.abs((double)_snowman.w() - _snowman));
   }
}
