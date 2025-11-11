import com.mojang.blaze3d.systems.RenderSystem;

public class ede implements edh.a {
   private final djz a;

   public ede(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      RenderSystem.enableDepthTest();
      RenderSystem.shadeModel(7425);
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      aqa _snowman = this.a.h.k().g();
      dfo _snowmanx = dfo.a();
      dfh _snowmanxx = _snowmanx.c();
      double _snowmanxxx = 0.0 - _snowman;
      double _snowmanxxxx = 256.0 - _snowman;
      RenderSystem.disableTexture();
      RenderSystem.disableBlend();
      double _snowmanxxxxx = (double)(_snowman.V << 4) - _snowman;
      double _snowmanxxxxxx = (double)(_snowman.X << 4) - _snowman;
      RenderSystem.lineWidth(1.0F);
      _snowmanxx.a(3, dfk.l);

      for (int _snowmanxxxxxxx = -16; _snowmanxxxxxxx <= 32; _snowmanxxxxxxx += 16) {
         for (int _snowmanxxxxxxxx = -16; _snowmanxxxxxxxx <= 32; _snowmanxxxxxxxx += 16) {
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.0F).d();
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.0F).d();
         }
      }

      for (int _snowmanxxxxxxx = 2; _snowmanxxxxxxx < 16; _snowmanxxxxxxx += 2) {
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + 16.0).a(1.0F, 1.0F, 0.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + 16.0).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + 16.0).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + 16.0).a(1.0F, 1.0F, 0.0F, 0.0F).d();
      }

      for (int _snowmanxxxxxxx = 2; _snowmanxxxxxxx < 16; _snowmanxxxxxxx += 2) {
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
      }

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= 256; _snowmanxxxxxxx += 2) {
         double _snowmanxxxxxxxx = (double)_snowmanxxxxxxx - _snowman;
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(1.0F, 1.0F, 0.0F, 0.0F).d();
      }

      _snowmanx.b();
      RenderSystem.lineWidth(2.0F);
      _snowmanxx.a(3, dfk.l);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= 16; _snowmanxxxxxxx += 16) {
         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx <= 16; _snowmanxxxxxxxx += 16) {
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(0.25F, 0.25F, 1.0F, 0.0F).d();
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(0.25F, 0.25F, 1.0F, 1.0F).d();
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(0.25F, 0.25F, 1.0F, 1.0F).d();
            _snowmanxx.a(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).a(0.25F, 0.25F, 1.0F, 0.0F).d();
         }
      }

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= 256; _snowmanxxxxxxx += 16) {
         double _snowmanxxxxxxxx = (double)_snowmanxxxxxxx - _snowman;
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(0.25F, 0.25F, 1.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(0.25F, 0.25F, 1.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).a(0.25F, 0.25F, 1.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).a(0.25F, 0.25F, 1.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx).a(0.25F, 0.25F, 1.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(0.25F, 0.25F, 1.0F, 1.0F).d();
         _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).a(0.25F, 0.25F, 1.0F, 0.0F).d();
      }

      _snowmanx.b();
      RenderSystem.lineWidth(1.0F);
      RenderSystem.enableBlend();
      RenderSystem.enableTexture();
      RenderSystem.shadeModel(7424);
   }
}
