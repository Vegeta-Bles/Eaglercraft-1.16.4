package net.minecraft.client.render.debug;

import com.google.common.collect.Lists;
import java.util.Collection;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class RaidCenterDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;
   private Collection<BlockPos> raidCenters = Lists.newArrayList();

   public RaidCenterDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   public void setRaidCenters(Collection<BlockPos> centers) {
      this.raidCenters = centers;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      BlockPos _snowman = this.method_23125().getBlockPos();

      for (BlockPos _snowmanx : this.raidCenters) {
         if (_snowman.isWithinDistance(_snowmanx, 160.0)) {
            method_23122(_snowmanx);
         }
      }
   }

   private static void method_23122(BlockPos _snowman) {
      DebugRenderer.drawBox(_snowman.add(-0.5, -0.5, -0.5), _snowman.add(1.5, 1.5, 1.5), 1.0F, 0.0F, 0.0F, 0.15F);
      int _snowmanx = -65536;
      method_23123("Raid center", _snowman, -65536);
   }

   private static void method_23123(String _snowman, BlockPos _snowman, int _snowman) {
      double _snowmanxxx = (double)_snowman.getX() + 0.5;
      double _snowmanxxxx = (double)_snowman.getY() + 1.3;
      double _snowmanxxxxx = (double)_snowman.getZ() + 0.5;
      DebugRenderer.drawString(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman, 0.04F, true, 0.0F, true);
   }

   private Camera method_23125() {
      return this.client.gameRenderer.getCamera();
   }
}
