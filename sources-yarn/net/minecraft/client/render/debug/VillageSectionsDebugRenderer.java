package net.minecraft.client.render.debug;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;

@Environment(EnvType.CLIENT)
public class VillageSectionsDebugRenderer implements DebugRenderer.Renderer {
   private final Set<ChunkSectionPos> sections = Sets.newHashSet();

   VillageSectionsDebugRenderer() {
   }

   @Override
   public void clear() {
      this.sections.clear();
   }

   public void addSection(ChunkSectionPos pos) {
      this.sections.add(pos);
   }

   public void removeSection(ChunkSectionPos pos) {
      this.sections.remove(pos);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.drawSections(cameraX, cameraY, cameraZ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   private void drawSections(double cameraX, double cameraY, double cameraZ) {
      BlockPos lv = new BlockPos(cameraX, cameraY, cameraZ);
      this.sections.forEach(arg2 -> {
         if (lv.isWithinDistance(arg2.getCenterPos(), 60.0)) {
            drawBoxAtCenterOf(arg2);
         }
      });
   }

   private static void drawBoxAtCenterOf(ChunkSectionPos pos) {
      float f = 1.0F;
      BlockPos lv = pos.getCenterPos();
      BlockPos lv2 = lv.add(-1.0, -1.0, -1.0);
      BlockPos lv3 = lv.add(1.0, 1.0, 1.0);
      DebugRenderer.drawBox(lv2, lv3, 0.2F, 1.0F, 0.2F, 0.15F);
   }
}
