package net.minecraft.client.gl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlProgramManager {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void useProgram(int _snowman) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager.useProgram(_snowman);
   }

   public static void deleteProgram(GlProgram _snowman) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      _snowman.getFragmentShader().release();
      _snowman.getVertexShader().release();
      GlStateManager.deleteProgram(_snowman.getProgramRef());
   }

   public static int createProgram() throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      int _snowman = GlStateManager.createProgram();
      if (_snowman <= 0) {
         throw new IOException("Could not create shader program (returned program ID " + _snowman + ")");
      } else {
         return _snowman;
      }
   }

   public static void linkProgram(GlProgram _snowman) throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      _snowman.getFragmentShader().attachTo(_snowman);
      _snowman.getVertexShader().attachTo(_snowman);
      GlStateManager.linkProgram(_snowman.getProgramRef());
      int _snowmanx = GlStateManager.getProgram(_snowman.getProgramRef(), 35714);
      if (_snowmanx == 0) {
         LOGGER.warn(
            "Error encountered when linking program containing VS {} and FS {}. Log output:", _snowman.getVertexShader().getName(), _snowman.getFragmentShader().getName()
         );
         LOGGER.warn(GlStateManager.getProgramInfoLog(_snowman.getProgramRef(), 32768));
      }
   }
}
