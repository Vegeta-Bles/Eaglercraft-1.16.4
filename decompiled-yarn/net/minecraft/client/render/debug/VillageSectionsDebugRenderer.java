package net.minecraft.client.render.debug;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;

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
      BlockPos _snowman = new BlockPos(cameraX, cameraY, cameraZ);
      this.sections.forEach(_snowmanx -> {
         if (_snowman.isWithinDistance(_snowmanx.getCenterPos(), 60.0)) {
            drawBoxAtCenterOf(_snowmanx);
         }
      });
   }

   private static void drawBoxAtCenterOf(ChunkSectionPos pos) {
      float _snowman = 1.0F;
      BlockPos _snowmanx = pos.getCenterPos();
      BlockPos _snowmanxx = _snowmanx.add(-1.0, -1.0, -1.0);
      BlockPos _snowmanxxx = _snowmanx.add(1.0, 1.0, 1.0);
      DebugRenderer.drawBox(_snowmanxx, _snowmanxxx, 0.2F, 1.0F, 0.2F, 0.15F);
   }
}
