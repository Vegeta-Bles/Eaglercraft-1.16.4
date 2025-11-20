package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.WorldView;

@Environment(EnvType.CLIENT)
public class WaterDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;

   public WaterDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      BlockPos lv = this.client.player.getBlockPos();
      WorldView lv2 = this.client.player.world;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);

      for (BlockPos lv3 : BlockPos.iterate(lv.add(-10, -10, -10), lv.add(10, 10, 10))) {
         FluidState lv4 = lv2.getFluidState(lv3);
         if (lv4.isIn(FluidTags.WATER)) {
            double g = (double)((float)lv3.getY() + lv4.getHeight(lv2, lv3));
            DebugRenderer.drawBox(
               new Box(
                     (double)((float)lv3.getX() + 0.01F),
                     (double)((float)lv3.getY() + 0.01F),
                     (double)((float)lv3.getZ() + 0.01F),
                     (double)((float)lv3.getX() + 0.99F),
                     g,
                     (double)((float)lv3.getZ() + 0.99F)
                  )
                  .offset(-cameraX, -cameraY, -cameraZ),
               1.0F,
               1.0F,
               1.0F,
               0.2F
            );
         }
      }

      for (BlockPos lv5 : BlockPos.iterate(lv.add(-10, -10, -10), lv.add(10, 10, 10))) {
         FluidState lv6 = lv2.getFluidState(lv5);
         if (lv6.isIn(FluidTags.WATER)) {
            DebugRenderer.drawString(
               String.valueOf(lv6.getLevel()),
               (double)lv5.getX() + 0.5,
               (double)((float)lv5.getY() + lv6.getHeight(lv2, lv5)),
               (double)lv5.getZ() + 0.5,
               -16777216
            );
         }
      }

      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }
}
