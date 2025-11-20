package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.WorldView;

public class WaterDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;

   public WaterDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      BlockPos _snowman = this.client.player.getBlockPos();
      WorldView _snowmanx = this.client.player.world;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);

      for (BlockPos _snowmanxx : BlockPos.iterate(_snowman.add(-10, -10, -10), _snowman.add(10, 10, 10))) {
         FluidState _snowmanxxx = _snowmanx.getFluidState(_snowmanxx);
         if (_snowmanxxx.isIn(FluidTags.WATER)) {
            double _snowmanxxxx = (double)((float)_snowmanxx.getY() + _snowmanxxx.getHeight(_snowmanx, _snowmanxx));
            DebugRenderer.drawBox(
               new Box(
                     (double)((float)_snowmanxx.getX() + 0.01F),
                     (double)((float)_snowmanxx.getY() + 0.01F),
                     (double)((float)_snowmanxx.getZ() + 0.01F),
                     (double)((float)_snowmanxx.getX() + 0.99F),
                     _snowmanxxxx,
                     (double)((float)_snowmanxx.getZ() + 0.99F)
                  )
                  .offset(-cameraX, -cameraY, -cameraZ),
               1.0F,
               1.0F,
               1.0F,
               0.2F
            );
         }
      }

      for (BlockPos _snowmanxxx : BlockPos.iterate(_snowman.add(-10, -10, -10), _snowman.add(10, 10, 10))) {
         FluidState _snowmanxxxx = _snowmanx.getFluidState(_snowmanxxx);
         if (_snowmanxxxx.isIn(FluidTags.WATER)) {
            DebugRenderer.drawString(
               String.valueOf(_snowmanxxxx.getLevel()),
               (double)_snowmanxxx.getX() + 0.5,
               (double)((float)_snowmanxxx.getY() + _snowmanxxxx.getHeight(_snowmanx, _snowmanxxx)),
               (double)_snowmanxxx.getZ() + 0.5,
               -16777216
            );
         }
      }

      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }
}
