package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;

public class BufferRenderer {
   public static void draw(BufferBuilder bufferBuilder) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            Pair<BufferBuilder.DrawArrayParameters, ByteBuffer> _snowmanx = bufferBuilder.popData();
            BufferBuilder.DrawArrayParameters _snowmanx = (BufferBuilder.DrawArrayParameters)_snowmanx.getFirst();
            draw((ByteBuffer)_snowmanx.getSecond(), _snowmanx.getMode(), _snowmanx.getVertexFormat(), _snowmanx.getCount());
         });
      } else {
         Pair<BufferBuilder.DrawArrayParameters, ByteBuffer> _snowman = bufferBuilder.popData();
         BufferBuilder.DrawArrayParameters _snowmanx = (BufferBuilder.DrawArrayParameters)_snowman.getFirst();
         draw((ByteBuffer)_snowman.getSecond(), _snowmanx.getMode(), _snowmanx.getVertexFormat(), _snowmanx.getCount());
      }
   }

   private static void draw(ByteBuffer buffer, int mode, VertexFormat vertexFormat, int count) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ((Buffer)buffer).clear();
      if (count > 0) {
         vertexFormat.startDrawing(MemoryUtil.memAddress(buffer));
         GlStateManager.drawArrays(mode, 0, count);
         vertexFormat.endDrawing();
      }
   }
}
