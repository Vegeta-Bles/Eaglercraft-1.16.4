import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

public class edl implements edh.a {
   private final djz a;

   public edl(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      brx _snowman = this.a.r;
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      fx _snowmanx = new fx(_snowman, _snowman, _snowman);
      LongSet _snowmanxx = new LongOpenHashSet();

      for (fx _snowmanxxx : fx.a(_snowmanx.b(-10, -10, -10), _snowmanx.b(10, 10, 10))) {
         int _snowmanxxxx = _snowman.a(bsf.a, _snowmanxxx);
         float _snowmanxxxxx = (float)(15 - _snowmanxxxx) / 15.0F * 0.5F + 0.16F;
         int _snowmanxxxxxx = afm.f(_snowmanxxxxx, 0.9F, 0.9F);
         long _snowmanxxxxxxx = gp.e(_snowmanxxx.a());
         if (_snowmanxx.add(_snowmanxxxxxxx)) {
            edh.a(
               _snowman.H().l().a(bsf.a, gp.a(_snowmanxxxxxxx)),
               (double)(gp.b(_snowmanxxxxxxx) * 16 + 8),
               (double)(gp.c(_snowmanxxxxxxx) * 16 + 8),
               (double)(gp.d(_snowmanxxxxxxx) * 16 + 8),
               16711680,
               0.3F
            );
         }

         if (_snowmanxxxx != 15) {
            edh.a(String.valueOf(_snowmanxxxx), (double)_snowmanxxx.u() + 0.5, (double)_snowmanxxx.v() + 0.25, (double)_snowmanxxx.w() + 0.5, _snowmanxxxxxx);
         }
      }

      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}
