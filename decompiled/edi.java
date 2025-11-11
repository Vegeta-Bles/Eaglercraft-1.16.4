import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;

public class edi implements edh.a {
   private final Map<fx, edi.a> a = Maps.newHashMap();

   public edi() {
   }

   public void a(fx var1, int var2, String var3, int var4) {
      this.a.put(_snowman, new edi.a(_snowman, _snowman, x.b() + (long)_snowman));
   }

   @Override
   public void a() {
      this.a.clear();
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      long _snowman = x.b();
      this.a.entrySet().removeIf(var2x -> _snowman > var2x.getValue().c);
      this.a.forEach(this::a);
   }

   private void a(fx var1, edi.a var2) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(dem.r.l, dem.j.j, dem.r.e, dem.j.n);
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      edh.a(_snowman, 0.02F, _snowman.a(), _snowman.b(), _snowman.c(), _snowman.d());
      if (!_snowman.b.isEmpty()) {
         double _snowman = (double)_snowman.u() + 0.5;
         double _snowmanx = (double)_snowman.v() + 1.2;
         double _snowmanxx = (double)_snowman.w() + 0.5;
         edh.a(_snowman.b, _snowman, _snowmanx, _snowmanxx, -1, 0.01F, true, 0.0F, true);
      }

      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   static class a {
      public int a;
      public String b;
      public long c;

      public a(int var1, String var2, long var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public float a() {
         return (float)(this.a >> 16 & 0xFF) / 255.0F;
      }

      public float b() {
         return (float)(this.a >> 8 & 0xFF) / 255.0F;
      }

      public float c() {
         return (float)(this.a & 0xFF) / 255.0F;
      }

      public float d() {
         return (float)(this.a >> 24 & 0xFF) / 255.0F;
      }
   }
}
