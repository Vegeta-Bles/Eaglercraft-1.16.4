package net.minecraft.client.render.debug;

import com.google.common.collect.Lists;
import java.util.Collection;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
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
      BlockPos lv = this.method_23125().getBlockPos();

      for (BlockPos lv2 : this.raidCenters) {
         if (lv.isWithinDistance(lv2, 160.0)) {
            method_23122(lv2);
         }
      }
   }

   private static void method_23122(BlockPos arg) {
      DebugRenderer.drawBox(arg.add(-0.5, -0.5, -0.5), arg.add(1.5, 1.5, 1.5), 1.0F, 0.0F, 0.0F, 0.15F);
      int i = -65536;
      method_23123("Raid center", arg, -65536);
   }

   private static void method_23123(String string, BlockPos arg, int i) {
      double d = (double)arg.getX() + 0.5;
      double e = (double)arg.getY() + 1.3;
      double f = (double)arg.getZ() + 0.5;
      DebugRenderer.drawString(string, d, e, f, i, 0.04F, true, 0.0F, true);
   }

   private Camera method_23125() {
      return this.client.gameRenderer.getCamera();
   }
}
