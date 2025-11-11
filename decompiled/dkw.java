import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.BiConsumer;

public abstract class dkw {
   public static final vk f = new vk("textures/gui/options_background.png");
   public static final vk g = new vk("textures/gui/container/stats_icons.png");
   public static final vk h = new vk("textures/gui/icons.png");
   private int a;

   public dkw() {
   }

   protected void a(dfm var1, int var2, int var3, int var4, int var5) {
      if (_snowman < _snowman) {
         int _snowman = _snowman;
         _snowman = _snowman;
         _snowman = _snowman;
      }

      a(_snowman, _snowman, _snowman, _snowman + 1, _snowman + 1, _snowman);
   }

   protected void b(dfm var1, int var2, int var3, int var4, int var5) {
      if (_snowman < _snowman) {
         int _snowman = _snowman;
         _snowman = _snowman;
         _snowman = _snowman;
      }

      a(_snowman, _snowman, _snowman + 1, _snowman + 1, _snowman, _snowman);
   }

   public static void a(dfm var0, int var1, int var2, int var3, int var4, int var5) {
      a(_snowman.c().a(), _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void a(b var0, int var1, int var2, int var3, int var4, int var5) {
      if (_snowman < _snowman) {
         int _snowman = _snowman;
         _snowman = _snowman;
         _snowman = _snowman;
      }

      if (_snowman < _snowman) {
         int _snowman = _snowman;
         _snowman = _snowman;
         _snowman = _snowman;
      }

      float _snowman = (float)(_snowman >> 24 & 0xFF) / 255.0F;
      float _snowmanx = (float)(_snowman >> 16 & 0xFF) / 255.0F;
      float _snowmanxx = (float)(_snowman >> 8 & 0xFF) / 255.0F;
      float _snowmanxxx = (float)(_snowman & 0xFF) / 255.0F;
      dfh _snowmanxxxx = dfo.a().c();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      _snowmanxxxx.a(7, dfk.l);
      _snowmanxxxx.a(_snowman, (float)_snowman, (float)_snowman, 0.0F).a(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).d();
      _snowmanxxxx.a(_snowman, (float)_snowman, (float)_snowman, 0.0F).a(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).d();
      _snowmanxxxx.a(_snowman, (float)_snowman, (float)_snowman, 0.0F).a(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).d();
      _snowmanxxxx.a(_snowman, (float)_snowman, (float)_snowman, 0.0F).a(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).d();
      _snowmanxxxx.c();
      dfi.a(_snowmanxxxx);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   protected void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      RenderSystem.disableTexture();
      RenderSystem.enableBlend();
      RenderSystem.disableAlphaTest();
      RenderSystem.defaultBlendFunc();
      RenderSystem.shadeModel(7425);
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      _snowmanx.a(7, dfk.l);
      a(_snowman.c().a(), _snowmanx, _snowman, _snowman, _snowman, _snowman, this.a, _snowman, _snowman);
      _snowman.b();
      RenderSystem.shadeModel(7424);
      RenderSystem.disableBlend();
      RenderSystem.enableAlphaTest();
      RenderSystem.enableTexture();
   }

   protected static void a(b var0, dfh var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      float _snowman = (float)(_snowman >> 24 & 0xFF) / 255.0F;
      float _snowmanx = (float)(_snowman >> 16 & 0xFF) / 255.0F;
      float _snowmanxx = (float)(_snowman >> 8 & 0xFF) / 255.0F;
      float _snowmanxxx = (float)(_snowman & 0xFF) / 255.0F;
      float _snowmanxxxx = (float)(_snowman >> 24 & 0xFF) / 255.0F;
      float _snowmanxxxxx = (float)(_snowman >> 16 & 0xFF) / 255.0F;
      float _snowmanxxxxxx = (float)(_snowman >> 8 & 0xFF) / 255.0F;
      float _snowmanxxxxxxx = (float)(_snowman & 0xFF) / 255.0F;
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).d();
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowmanx, _snowmanxx, _snowmanxxx, _snowman).d();
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxx).d();
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxx).d();
   }

   public static void a(dfm var0, dku var1, String var2, int var3, int var4, int var5) {
      _snowman.a(_snowman, _snowman, (float)(_snowman - _snowman.b(_snowman) / 2), (float)_snowman, _snowman);
   }

   public static void a(dfm var0, dku var1, nr var2, int var3, int var4, int var5) {
      afa _snowman = _snowman.f();
      _snowman.a(_snowman, _snowman, (float)(_snowman - _snowman.a(_snowman) / 2), (float)_snowman, _snowman);
   }

   public static void b(dfm var0, dku var1, String var2, int var3, int var4, int var5) {
      _snowman.a(_snowman, _snowman, (float)_snowman, (float)_snowman, _snowman);
   }

   public static void b(dfm var0, dku var1, nr var2, int var3, int var4, int var5) {
      _snowman.a(_snowman, _snowman, (float)_snowman, (float)_snowman, _snowman);
   }

   public void a(int var1, int var2, BiConsumer<Integer, Integer> var3) {
      RenderSystem.blendFuncSeparate(dem.r.o, dem.j.j, dem.r.l, dem.j.j);
      _snowman.accept(_snowman + 1, _snowman);
      _snowman.accept(_snowman - 1, _snowman);
      _snowman.accept(_snowman, _snowman + 1);
      _snowman.accept(_snowman, _snowman - 1);
      RenderSystem.blendFunc(dem.r.l, dem.j.j);
      _snowman.accept(_snowman, _snowman);
   }

   public static void a(dfm var0, int var1, int var2, int var3, int var4, int var5, ekc var6) {
      a(_snowman.c().a(), _snowman, _snowman + _snowman, _snowman, _snowman + _snowman, _snowman, _snowman.h(), _snowman.i(), _snowman.j(), _snowman.k());
   }

   public void b(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      a(_snowman, _snowman, _snowman, this.a, (float)_snowman, (float)_snowman, _snowman, _snowman, 256, 256);
   }

   public static void a(dfm var0, int var1, int var2, int var3, float var4, float var5, int var6, int var7, int var8, int var9) {
      a(_snowman, _snowman, _snowman + _snowman, _snowman, _snowman + _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(dfm var0, int var1, int var2, int var3, int var4, float var5, float var6, int var7, int var8, int var9, int var10) {
      a(_snowman, _snowman, _snowman + _snowman, _snowman, _snowman + _snowman, 0, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(dfm var0, int var1, int var2, float var3, float var4, int var5, int var6, int var7, int var8) {
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void a(dfm var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8, float var9, int var10, int var11) {
      a(_snowman.c().a(), _snowman, _snowman, _snowman, _snowman, _snowman, (_snowman + 0.0F) / (float)_snowman, (_snowman + (float)_snowman) / (float)_snowman, (_snowman + 0.0F) / (float)_snowman, (_snowman + (float)_snowman) / (float)_snowman);
   }

   private static void a(b var0, int var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, float var9) {
      dfh _snowman = dfo.a().c();
      _snowman.a(7, dfk.n);
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowman, _snowman).d();
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowman, _snowman).d();
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowman, _snowman).d();
      _snowman.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).a(_snowman, _snowman).d();
      _snowman.c();
      RenderSystem.enableAlphaTest();
      dfi.a(_snowman);
   }

   public int v() {
      return this.a;
   }

   public void d(int var1) {
      this.a = _snowman;
   }
}
