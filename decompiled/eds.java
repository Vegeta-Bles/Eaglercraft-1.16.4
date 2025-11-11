import com.mojang.blaze3d.systems.RenderSystem;

public class eds implements edh.a {
   private final djz a;

   public eds(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      fx _snowman = this.a.s.cB();
      brz _snowmanx = this.a.s.l;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);

      for (fx _snowmanxx : fx.a(_snowman.b(-10, -10, -10), _snowman.b(10, 10, 10))) {
         cux _snowmanxxx = _snowmanx.b(_snowmanxx);
         if (_snowmanxxx.a(aef.b)) {
            double _snowmanxxxx = (double)((float)_snowmanxx.v() + _snowmanxxx.a(_snowmanx, _snowmanxx));
            edh.a(
               new dci(
                     (double)((float)_snowmanxx.u() + 0.01F),
                     (double)((float)_snowmanxx.v() + 0.01F),
                     (double)((float)_snowmanxx.w() + 0.01F),
                     (double)((float)_snowmanxx.u() + 0.99F),
                     _snowmanxxxx,
                     (double)((float)_snowmanxx.w() + 0.99F)
                  )
                  .d(-_snowman, -_snowman, -_snowman),
               1.0F,
               1.0F,
               1.0F,
               0.2F
            );
         }
      }

      for (fx _snowmanxxx : fx.a(_snowman.b(-10, -10, -10), _snowman.b(10, 10, 10))) {
         cux _snowmanxxxx = _snowmanx.b(_snowmanxxx);
         if (_snowmanxxxx.a(aef.b)) {
            edh.a(String.valueOf(_snowmanxxxx.e()), (double)_snowmanxxx.u() + 0.5, (double)((float)_snowmanxxx.v() + _snowmanxxxx.a(_snowmanx, _snowmanxxx)), (double)_snowmanxxx.w() + 0.5, -16777216);
         }
      }

      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }
}
