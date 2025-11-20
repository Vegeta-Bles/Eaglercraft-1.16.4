package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.system.MemoryUtil;

@Environment(EnvType.CLIENT)
public class BufferRenderer {
   public static void draw(BufferBuilder bufferBuilder) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            Pair<BufferBuilder.DrawArrayParameters, ByteBuffer> pairx = bufferBuilder.popData();
            BufferBuilder.DrawArrayParameters lvx = (BufferBuilder.DrawArrayParameters)pairx.getFirst();
            draw((ByteBuffer)pairx.getSecond(), lvx.getMode(), lvx.getVertexFormat(), lvx.getCount());
         });
      } else {
         Pair<BufferBuilder.DrawArrayParameters, ByteBuffer> pair = bufferBuilder.popData();
         BufferBuilder.DrawArrayParameters lv = (BufferBuilder.DrawArrayParameters)pair.getFirst();
         draw((ByteBuffer)pair.getSecond(), lv.getMode(), lv.getVertexFormat(), lv.getCount());
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
