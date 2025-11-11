import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;

public class edr implements edh.a {
   private final Set<gp> a = Sets.newHashSet();

   edr() {
   }

   @Override
   public void a() {
      this.a.clear();
   }

   public void a(gp var1) {
      this.a.add(_snowman);
   }

   public void b(gp var1) {
      this.a.remove(_snowman);
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.a(_snowman, _snowman, _snowman);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   private void a(double var1, double var3, double var5) {
      fx _snowman = new fx(_snowman, _snowman, _snowman);
      this.a.forEach(var1x -> {
         if (_snowman.a(var1x.q(), 60.0)) {
            c(var1x);
         }
      });
   }

   private static void c(gp var0) {
      float _snowman = 1.0F;
      fx _snowmanx = _snowman.q();
      fx _snowmanxx = _snowmanx.a(-1.0, -1.0, -1.0);
      fx _snowmanxxx = _snowmanx.a(1.0, 1.0, 1.0);
      edh.a(_snowmanxx, _snowmanxxx, 0.2F, 1.0F, 0.2F, 0.15F);
   }
}
