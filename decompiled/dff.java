import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dff {
   private static final Logger a = LogManager.getLogger();

   public static void a(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      dem.g(_snowman);
   }

   public static void a(dfd var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      _snowman.d().a();
      _snowman.c().a();
      dem.h(_snowman.a());
   }

   public static int a() throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      int _snowman = dem.p();
      if (_snowman <= 0) {
         throw new IOException("Could not create shader program (returned program ID " + _snowman + ")");
      } else {
         return _snowman;
      }
   }

   public static void b(dfd var0) throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      _snowman.d().a(_snowman);
      _snowman.c().a(_snowman);
      dem.i(_snowman.a());
      int _snowman = dem.c(_snowman.a(), 35714);
      if (_snowman == 0) {
         a.warn("Error encountered when linking program containing VS {} and FS {}. Log output:", _snowman.c().b(), _snowman.d().b());
         a.warn(dem.j(_snowman.a(), 32768));
      }
   }
}
