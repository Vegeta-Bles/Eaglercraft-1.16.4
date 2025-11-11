import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

public class eaq {
   private static final vk a = new vk("textures/misc/underwater.png");

   public static void a(djz var0, dfm var1) {
      RenderSystem.disableAlphaTest();
      bfw _snowman = _snowman.s;
      if (!_snowman.H) {
         ceh _snowmanx = a(_snowman);
         if (_snowmanx != null) {
            a(_snowman, _snowman.ab().a().a(_snowmanx), _snowman);
         }
      }

      if (!_snowman.s.a_()) {
         if (_snowman.s.a(aef.b)) {
            b(_snowman, _snowman);
         }

         if (_snowman.s.bq()) {
            c(_snowman, _snowman);
         }
      }

      RenderSystem.enableAlphaTest();
   }

   @Nullable
   private static ceh a(bfw var0) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
         double _snowmanxx = _snowman.cD() + (double)(((float)((_snowmanx >> 0) % 2) - 0.5F) * _snowman.cy() * 0.8F);
         double _snowmanxxx = _snowman.cG() + (double)(((float)((_snowmanx >> 1) % 2) - 0.5F) * 0.1F);
         double _snowmanxxxx = _snowman.cH() + (double)(((float)((_snowmanx >> 2) % 2) - 0.5F) * _snowman.cy() * 0.8F);
         _snowman.c(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         ceh _snowmanxxxxx = _snowman.l.d_(_snowman);
         if (_snowmanxxxxx.h() != bzh.a && _snowmanxxxxx.p(_snowman.l, _snowman)) {
            return _snowmanxxxxx;
         }
      }

      return null;
   }

   private static void a(djz var0, ekc var1, dfm var2) {
      _snowman.M().a(_snowman.m().g());
      dfh _snowman = dfo.a().c();
      float _snowmanx = 0.1F;
      float _snowmanxx = -1.0F;
      float _snowmanxxx = 1.0F;
      float _snowmanxxxx = -1.0F;
      float _snowmanxxxxx = 1.0F;
      float _snowmanxxxxxx = -0.5F;
      float _snowmanxxxxxxx = _snowman.h();
      float _snowmanxxxxxxxx = _snowman.i();
      float _snowmanxxxxxxxxx = _snowman.j();
      float _snowmanxxxxxxxxxx = _snowman.k();
      b _snowmanxxxxxxxxxxx = _snowman.c().a();
      _snowman.a(7, dfk.o);
      _snowman.a(_snowmanxxxxxxxxxxx, -1.0F, -1.0F, -0.5F).a(0.1F, 0.1F, 0.1F, 1.0F).a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx).d();
      _snowman.a(_snowmanxxxxxxxxxxx, 1.0F, -1.0F, -0.5F).a(0.1F, 0.1F, 0.1F, 1.0F).a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx).d();
      _snowman.a(_snowmanxxxxxxxxxxx, 1.0F, 1.0F, -0.5F).a(0.1F, 0.1F, 0.1F, 1.0F).a(_snowmanxxxxxxx, _snowmanxxxxxxxxx).d();
      _snowman.a(_snowmanxxxxxxxxxxx, -1.0F, 1.0F, -0.5F).a(0.1F, 0.1F, 0.1F, 1.0F).a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx).d();
      _snowman.c();
      dfi.a(_snowman);
   }

   private static void b(djz var0, dfm var1) {
      RenderSystem.enableTexture();
      _snowman.M().a(a);
      dfh _snowman = dfo.a().c();
      float _snowmanx = _snowman.s.aR();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      float _snowmanxx = 4.0F;
      float _snowmanxxx = -1.0F;
      float _snowmanxxxx = 1.0F;
      float _snowmanxxxxx = -1.0F;
      float _snowmanxxxxxx = 1.0F;
      float _snowmanxxxxxxx = -0.5F;
      float _snowmanxxxxxxxx = -_snowman.s.p / 64.0F;
      float _snowmanxxxxxxxxx = _snowman.s.q / 64.0F;
      b _snowmanxxxxxxxxxx = _snowman.c().a();
      _snowman.a(7, dfk.o);
      _snowman.a(_snowmanxxxxxxxxxx, -1.0F, -1.0F, -0.5F).a(_snowmanx, _snowmanx, _snowmanx, 0.1F).a(4.0F + _snowmanxxxxxxxx, 4.0F + _snowmanxxxxxxxxx).d();
      _snowman.a(_snowmanxxxxxxxxxx, 1.0F, -1.0F, -0.5F).a(_snowmanx, _snowmanx, _snowmanx, 0.1F).a(0.0F + _snowmanxxxxxxxx, 4.0F + _snowmanxxxxxxxxx).d();
      _snowman.a(_snowmanxxxxxxxxxx, 1.0F, 1.0F, -0.5F).a(_snowmanx, _snowmanx, _snowmanx, 0.1F).a(0.0F + _snowmanxxxxxxxx, 0.0F + _snowmanxxxxxxxxx).d();
      _snowman.a(_snowmanxxxxxxxxxx, -1.0F, 1.0F, -0.5F).a(_snowmanx, _snowmanx, _snowmanx, 0.1F).a(4.0F + _snowmanxxxxxxxx, 0.0F + _snowmanxxxxxxxxx).d();
      _snowman.c();
      dfi.a(_snowman);
      RenderSystem.disableBlend();
   }

   private static void c(djz var0, dfm var1) {
      dfh _snowman = dfo.a().c();
      RenderSystem.depthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableTexture();
      ekc _snowmanx = els.b.c();
      _snowman.M().a(_snowmanx.m().g());
      float _snowmanxx = _snowmanx.h();
      float _snowmanxxx = _snowmanx.i();
      float _snowmanxxxx = (_snowmanxx + _snowmanxxx) / 2.0F;
      float _snowmanxxxxx = _snowmanx.j();
      float _snowmanxxxxxx = _snowmanx.k();
      float _snowmanxxxxxxx = (_snowmanxxxxx + _snowmanxxxxxx) / 2.0F;
      float _snowmanxxxxxxxx = _snowmanx.p();
      float _snowmanxxxxxxxxx = afm.g(_snowmanxxxxxxxx, _snowmanxx, _snowmanxxxx);
      float _snowmanxxxxxxxxxx = afm.g(_snowmanxxxxxxxx, _snowmanxxx, _snowmanxxxx);
      float _snowmanxxxxxxxxxxx = afm.g(_snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
      float _snowmanxxxxxxxxxxxx = afm.g(_snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
      float _snowmanxxxxxxxxxxxxx = 1.0F;

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxx++) {
         _snowman.a();
         float _snowmanxxxxxxxxxxxxxxx = -0.5F;
         float _snowmanxxxxxxxxxxxxxxxx = 0.5F;
         float _snowmanxxxxxxxxxxxxxxxxx = -0.5F;
         float _snowmanxxxxxxxxxxxxxxxxxx = 0.5F;
         float _snowmanxxxxxxxxxxxxxxxxxxx = -0.5F;
         _snowman.a((double)((float)(-(_snowmanxxxxxxxxxxxxxx * 2 - 1)) * 0.24F), -0.3F, 0.0);
         _snowman.a(g.d.a((float)(_snowmanxxxxxxxxxxxxxx * 2 - 1) * 10.0F));
         b _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.c().a();
         _snowman.a(7, dfk.o);
         _snowman.a(_snowmanxxxxxxxxxxxxxxxxxxxx, -0.5F, -0.5F, -0.5F).a(1.0F, 1.0F, 1.0F, 0.9F).a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx).d();
         _snowman.a(_snowmanxxxxxxxxxxxxxxxxxxxx, 0.5F, -0.5F, -0.5F).a(1.0F, 1.0F, 1.0F, 0.9F).a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx).d();
         _snowman.a(_snowmanxxxxxxxxxxxxxxxxxxxx, 0.5F, 0.5F, -0.5F).a(1.0F, 1.0F, 1.0F, 0.9F).a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx).d();
         _snowman.a(_snowmanxxxxxxxxxxxxxxxxxxxx, -0.5F, 0.5F, -0.5F).a(1.0F, 1.0F, 1.0F, 0.9F).a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx).d();
         _snowman.c();
         dfi.a(_snowman);
         _snowman.b();
      }

      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
   }
}
