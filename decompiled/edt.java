import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;

public class edt implements edh.a {
   private final List<fx> a = Lists.newArrayList();
   private final List<Float> b = Lists.newArrayList();
   private final List<Float> c = Lists.newArrayList();
   private final List<Float> d = Lists.newArrayList();
   private final List<Float> e = Lists.newArrayList();
   private final List<Float> f = Lists.newArrayList();

   public edt() {
   }

   public void a(fx var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.add(_snowman);
      this.b.add(_snowman);
      this.c.add(_snowman);
      this.d.add(_snowman);
      this.e.add(_snowman);
      this.f.add(_snowman);
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      _snowmanx.a(5, dfk.l);

      for (int _snowmanxx = 0; _snowmanxx < this.a.size(); _snowmanxx++) {
         fx _snowmanxxx = this.a.get(_snowmanxx);
         Float _snowmanxxxx = this.b.get(_snowmanxx);
         float _snowmanxxxxx = _snowmanxxxx / 2.0F;
         eae.a(
            _snowmanx,
            (double)((float)_snowmanxxx.u() + 0.5F - _snowmanxxxxx) - _snowman,
            (double)((float)_snowmanxxx.v() + 0.5F - _snowmanxxxxx) - _snowman,
            (double)((float)_snowmanxxx.w() + 0.5F - _snowmanxxxxx) - _snowman,
            (double)((float)_snowmanxxx.u() + 0.5F + _snowmanxxxxx) - _snowman,
            (double)((float)_snowmanxxx.v() + 0.5F + _snowmanxxxxx) - _snowman,
            (double)((float)_snowmanxxx.w() + 0.5F + _snowmanxxxxx) - _snowman,
            this.d.get(_snowmanxx),
            this.e.get(_snowmanxx),
            this.f.get(_snowmanxx),
            this.c.get(_snowmanxx)
         );
      }

      _snowman.b();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}
