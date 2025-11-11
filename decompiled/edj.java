import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;

public class edj implements edh.a {
   private final djz a;
   private final Map<Integer, List<edj.a>> b = Maps.newHashMap();

   @Override
   public void a() {
      this.b.clear();
   }

   public void a(int var1, List<edj.a> var2) {
      this.b.put(_snowman, _snowman);
   }

   public edj(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      djk _snowman = this.a.h.k();
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      fx _snowmanx = new fx(_snowman.b().b, 0.0, _snowman.b().d);
      this.b.forEach((var1x, var2x) -> {
         for (int _snowmanxx = 0; _snowmanxx < var2x.size(); _snowmanxx++) {
            edj.a _snowmanx = var2x.get(_snowmanxx);
            if (_snowman.a(_snowmanx.a, 160.0)) {
               double _snowmanxx = (double)_snowmanx.a.u() + 0.5;
               double _snowmanxxx = (double)_snowmanx.a.v() + 2.0 + (double)_snowmanxx * 0.25;
               double _snowmanxxxx = (double)_snowmanx.a.w() + 0.5;
               int _snowmanxxxxx = _snowmanx.d ? -16711936 : -3355444;
               edh.a(_snowmanx.c, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
            }
         }
      });
      RenderSystem.enableDepthTest();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }

   public static class a {
      public final fx a;
      public final int b;
      public final String c;
      public final boolean d;

      public a(fx var1, int var2, String var3, boolean var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }
   }
}
