import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class edd implements edh.a {
   private final Map<fx, fx> a = Maps.newHashMap();
   private final Map<fx, Float> b = Maps.newHashMap();
   private final List<fx> c = Lists.newArrayList();

   public edd() {
   }

   public void a(fx var1, List<fx> var2, List<Float> var3) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         this.a.put(_snowman.get(_snowman), _snowman);
         this.b.put(_snowman.get(_snowman), _snowman.get(_snowman));
      }

      this.c.add(_snowman);
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      fx _snowman = new fx(_snowman, 0.0, _snowman);
      dfo _snowmanx = dfo.a();
      dfh _snowmanxx = _snowmanx.c();
      _snowmanxx.a(5, dfk.l);

      for (Entry<fx, fx> _snowmanxxx : this.a.entrySet()) {
         fx _snowmanxxxx = _snowmanxxx.getKey();
         fx _snowmanxxxxx = _snowmanxxx.getValue();
         float _snowmanxxxxxx = (float)(_snowmanxxxxx.u() * 128 % 256) / 256.0F;
         float _snowmanxxxxxxx = (float)(_snowmanxxxxx.v() * 128 % 256) / 256.0F;
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxx.w() * 128 % 256) / 256.0F;
         float _snowmanxxxxxxxxx = this.b.get(_snowmanxxxx);
         if (_snowman.a(_snowmanxxxx, 160.0)) {
            eae.a(
               _snowmanxx,
               (double)((float)_snowmanxxxx.u() + 0.5F) - _snowman - (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.v() + 0.5F) - _snowman - (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.w() + 0.5F) - _snowman - (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.u() + 0.5F) - _snowman + (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.v() + 0.5F) - _snowman + (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.w() + 0.5F) - _snowman + (double)_snowmanxxxxxxxxx,
               _snowmanxxxxxx,
               _snowmanxxxxxxx,
               _snowmanxxxxxxxx,
               0.5F
            );
         }
      }

      for (fx _snowmanxxxx : this.c) {
         if (_snowman.a(_snowmanxxxx, 160.0)) {
            eae.a(
               _snowmanxx,
               (double)_snowmanxxxx.u() - _snowman,
               (double)_snowmanxxxx.v() - _snowman,
               (double)_snowmanxxxx.w() - _snowman,
               (double)((float)_snowmanxxxx.u() + 1.0F) - _snowman,
               (double)((float)_snowmanxxxx.v() + 1.0F) - _snowman,
               (double)((float)_snowmanxxxx.w() + 1.0F) - _snowman,
               1.0F,
               1.0F,
               1.0F,
               1.0F
            );
         }
      }

      _snowmanx.b();
      RenderSystem.enableDepthTest();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}
