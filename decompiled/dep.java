import com.mojang.blaze3d.systems.RenderSystem;

public class dep {
   private static final g a = x.a(new g(0.2F, 1.0F, -0.7F), g::d);
   private static final g b = x.a(new g(-0.2F, 1.0F, 0.7F), g::d);
   private static final g c = x.a(new g(0.2F, 1.0F, -0.7F), g::d);
   private static final g d = x.a(new g(-0.2F, -1.0F, 0.7F), g::d);

   public static void a() {
      RenderSystem.enableLighting();
      RenderSystem.enableColorMaterial();
      RenderSystem.colorMaterial(1032, 5634);
   }

   public static void b() {
      RenderSystem.disableLighting();
      RenderSystem.disableColorMaterial();
   }

   public static void a(b var0) {
      RenderSystem.setupLevelDiffuseLighting(c, d, _snowman);
   }

   public static void b(b var0) {
      RenderSystem.setupLevelDiffuseLighting(a, b, _snowman);
   }

   public static void c() {
      RenderSystem.setupGuiFlatDiffuseLighting(a, b);
   }

   public static void d() {
      RenderSystem.setupGui3DDiffuseLighting(a, b);
   }
}
