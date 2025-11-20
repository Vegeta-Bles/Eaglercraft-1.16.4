package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map.Entry;
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

public class HeightmapDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;

   public HeightmapDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      WorldAccess _snowman = this.client.world;
      RenderSystem.pushMatrix();
      RenderSystem.disableBlend();
      RenderSystem.disableTexture();
      RenderSystem.enableDepthTest();
      BlockPos _snowmanx = new BlockPos(cameraX, 0.0, cameraZ);
      Tessellator _snowmanxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxx = _snowmanxx.getBuffer();
      _snowmanxxx.begin(5, VertexFormats.POSITION_COLOR);

      for (int _snowmanxxxx = -32; _snowmanxxxx <= 32; _snowmanxxxx += 16) {
         for (int _snowmanxxxxx = -32; _snowmanxxxxx <= 32; _snowmanxxxxx += 16) {
            Chunk _snowmanxxxxxx = _snowman.getChunk(_snowmanx.add(_snowmanxxxx, 0, _snowmanxxxxx));

            for (Entry<Heightmap.Type, Heightmap> _snowmanxxxxxxx : _snowmanxxxxxx.getHeightmaps()) {
               Heightmap.Type _snowmanxxxxxxxx = _snowmanxxxxxxx.getKey();
               ChunkPos _snowmanxxxxxxxxx = _snowmanxxxxxx.getPos();
               Vector3f _snowmanxxxxxxxxxx = this.method_27037(_snowmanxxxxxxxx);

               for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxx++) {
                     int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.x * 16 + _snowmanxxxxxxxxxxx;
                     int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.z * 16 + _snowmanxxxxxxxxxxxx;
                     float _snowmanxxxxxxxxxxxxxxx = (float)(
                        (double)((float)_snowman.getTopY(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx) + (float)_snowmanxxxxxxxx.ordinal() * 0.09375F) - cameraY
                     );
                     WorldRenderer.drawBox(
                        _snowmanxxx,
                        (double)((float)_snowmanxxxxxxxxxxxxx + 0.25F) - cameraX,
                        (double)_snowmanxxxxxxxxxxxxxxx,
                        (double)((float)_snowmanxxxxxxxxxxxxxx + 0.25F) - cameraZ,
                        (double)((float)_snowmanxxxxxxxxxxxxx + 0.75F) - cameraX,
                        (double)(_snowmanxxxxxxxxxxxxxxx + 0.09375F),
                        (double)((float)_snowmanxxxxxxxxxxxxxx + 0.75F) - cameraZ,
                        _snowmanxxxxxxxxxx.getX(),
                        _snowmanxxxxxxxxxx.getY(),
                        _snowmanxxxxxxxxxx.getZ(),
                        1.0F
                     );
                  }
               }
            }
         }
      }

      _snowmanxx.draw();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }

   private Vector3f method_27037(Heightmap.Type _snowman) {
      switch (_snowman) {
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
