package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class SkyLightDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;

   public SkyLightDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      World _snowman = this.client.world;
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      BlockPos _snowmanx = new BlockPos(cameraX, cameraY, cameraZ);
      LongSet _snowmanxx = new LongOpenHashSet();

      for (BlockPos _snowmanxxx : BlockPos.iterate(_snowmanx.add(-10, -10, -10), _snowmanx.add(10, 10, 10))) {
         int _snowmanxxxx = _snowman.getLightLevel(LightType.SKY, _snowmanxxx);
         float _snowmanxxxxx = (float)(15 - _snowmanxxxx) / 15.0F * 0.5F + 0.16F;
         int _snowmanxxxxxx = MathHelper.hsvToRgb(_snowmanxxxxx, 0.9F, 0.9F);
         long _snowmanxxxxxxx = ChunkSectionPos.fromBlockPos(_snowmanxxx.asLong());
         if (_snowmanxx.add(_snowmanxxxxxxx)) {
            DebugRenderer.drawString(
               _snowman.getChunkManager().getLightingProvider().displaySectionLevel(LightType.SKY, ChunkSectionPos.from(_snowmanxxxxxxx)),
               (double)(ChunkSectionPos.unpackX(_snowmanxxxxxxx) * 16 + 8),
               (double)(ChunkSectionPos.unpackY(_snowmanxxxxxxx) * 16 + 8),
               (double)(ChunkSectionPos.unpackZ(_snowmanxxxxxxx) * 16 + 8),
               16711680,
               0.3F
            );
         }

         if (_snowmanxxxx != 15) {
            DebugRenderer.drawString(String.valueOf(_snowmanxxxx), (double)_snowmanxxx.getX() + 0.5, (double)_snowmanxxx.getY() + 0.25, (double)_snowmanxxx.getZ() + 0.5, _snowmanxxxxxx);
         }
      }

      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}
