package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;

@Environment(EnvType.CLIENT)
public class HeightmapDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;

   public HeightmapDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      WorldAccess lv = this.client.world;
      RenderSystem.pushMatrix();
      RenderSystem.disableBlend();
      RenderSystem.disableTexture();
      RenderSystem.enableDepthTest();
      BlockPos lv2 = new BlockPos(cameraX, 0.0, cameraZ);
      Tessellator lv3 = Tessellator.getInstance();
      BufferBuilder lv4 = lv3.getBuffer();
      lv4.begin(5, VertexFormats.POSITION_COLOR);

      for (int i = -32; i <= 32; i += 16) {
         for (int j = -32; j <= 32; j += 16) {
            Chunk lv5 = lv.getChunk(lv2.add(i, 0, j));

            for (Entry<Heightmap.Type, Heightmap> entry : lv5.getHeightmaps()) {
               Heightmap.Type lv6 = entry.getKey();
               ChunkPos lv7 = lv5.getPos();
               Vector3f lv8 = this.method_27037(lv6);

               for (int k = 0; k < 16; k++) {
                  for (int l = 0; l < 16; l++) {
                     int m = lv7.x * 16 + k;
                     int n = lv7.z * 16 + l;
                     float g = (float)((double)((float)lv.getTopY(lv6, m, n) + (float)lv6.ordinal() * 0.09375F) - cameraY);
                     WorldRenderer.drawBox(
                        lv4,
                        (double)((float)m + 0.25F) - cameraX,
                        (double)g,
                        (double)((float)n + 0.25F) - cameraZ,
                        (double)((float)m + 0.75F) - cameraX,
                        (double)(g + 0.09375F),
                        (double)((float)n + 0.75F) - cameraZ,
                        lv8.getX(),
                        lv8.getY(),
                        lv8.getZ(),
                        1.0F
                     );
                  }
               }
            }
         }
      }

      lv3.draw();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }

   private Vector3f method_27037(Heightmap.Type arg) {
      switch (arg) {
         case WORLD_SURFACE_WG:
            return new Vector3f(1.0F, 1.0F, 0.0F);
         case OCEAN_FLOOR_WG:
            return new Vector3f(1.0F, 0.0F, 1.0F);
         case WORLD_SURFACE:
            return new Vector3f(0.0F, 0.7F, 0.0F);
         case OCEAN_FLOOR:
            return new Vector3f(0.0F, 0.0F, 0.5F);
         case MOTION_BLOCKING:
            return new Vector3f(0.0F, 0.3F, 0.3F);
         case MOTION_BLOCKING_NO_LEAVES:
            return new Vector3f(0.0F, 0.5F, 0.5F);
         default:
            return new Vector3f(0.0F, 0.0F, 0.0F);
      }
   }
}
