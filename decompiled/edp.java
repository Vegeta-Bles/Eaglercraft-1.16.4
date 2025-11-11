import com.mojang.blaze3d.systems.RenderSystem;

public class edp implements edh.a {
   private final djz a;

   public edp(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      brc _snowman = this.a.s.l;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.lineWidth(2.0F);
      RenderSystem.disableTexture();
      RenderSystem.depthMask(false);
      fx _snowmanx = new fx(_snowman, _snowman, _snowman);

      for (fx _snowmanxx : fx.a(_snowmanx.b(-6, -6, -6), _snowmanx.b(6, 6, 6))) {
         ceh _snowmanxxx = _snowman.d_(_snowmanxx);
         if (!_snowmanxxx.a(bup.a)) {
            ddh _snowmanxxxx = _snowmanxxx.j(_snowman, _snowmanxx);

            for (dci _snowmanxxxxx : _snowmanxxxx.d()) {
               dci _snowmanxxxxxx = _snowmanxxxxx.a(_snowmanxx).g(0.002).d(-_snowman, -_snowman, -_snowman);
               double _snowmanxxxxxxx = _snowmanxxxxxx.a;
               double _snowmanxxxxxxxx = _snowmanxxxxxx.b;
               double _snowmanxxxxxxxxx = _snowmanxxxxxx.c;
               double _snowmanxxxxxxxxxx = _snowmanxxxxxx.d;
               double _snowmanxxxxxxxxxxx = _snowmanxxxxxx.e;
               double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx.f;
               float _snowmanxxxxxxxxxxxxx = 1.0F;
               float _snowmanxxxxxxxxxxxxxx = 0.0F;
               float _snowmanxxxxxxxxxxxxxxx = 0.0F;
               float _snowmanxxxxxxxxxxxxxxxx = 0.5F;
               if (_snowmanxxx.d(_snowman, _snowmanxx, gc.e)) {
                  dfo _snowmanxxxxxxxxxxxxxxxxx = dfo.a();
                  dfh _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.c();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(5, dfk.l);
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxx.b();
               }

               if (_snowmanxxx.d(_snowman, _snowmanxx, gc.d)) {
                  dfo _snowmanxxxxxxxxxxxxxxxxx = dfo.a();
                  dfh _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.c();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(5, dfk.l);
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxx.b();
               }

               if (_snowmanxxx.d(_snowman, _snowmanxx, gc.f)) {
                  dfo _snowmanxxxxxxxxxxxxxxxxx = dfo.a();
                  dfh _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.c();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(5, dfk.l);
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxx.b();
               }

               if (_snowmanxxx.d(_snowman, _snowmanxx, gc.c)) {
                  dfo _snowmanxxxxxxxxxxxxxxxxx = dfo.a();
                  dfh _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.c();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(5, dfk.l);
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxx.b();
               }

               if (_snowmanxxx.d(_snowman, _snowmanxx, gc.a)) {
                  dfo _snowmanxxxxxxxxxxxxxxxxx = dfo.a();
                  dfh _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.c();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(5, dfk.l);
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxx.b();
               }

               if (_snowmanxxx.d(_snowman, _snowmanxx, gc.b)) {
                  dfo _snowmanxxxxxxxxxxxxxxxxx = dfo.a();
                  dfh _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.c();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(5, dfk.l);
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).a(1.0F, 0.0F, 0.0F, 0.5F).d();
                  _snowmanxxxxxxxxxxxxxxxxx.b();
               }
            }
         }
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }
}
