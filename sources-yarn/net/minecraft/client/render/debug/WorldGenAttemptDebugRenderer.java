package net.minecraft.client.render.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class WorldGenAttemptDebugRenderer implements DebugRenderer.Renderer {
   private final List<BlockPos> field_4640 = Lists.newArrayList();
   private final List<Float> field_4635 = Lists.newArrayList();
   private final List<Float> field_4637 = Lists.newArrayList();
   private final List<Float> field_4639 = Lists.newArrayList();
   private final List<Float> field_4636 = Lists.newArrayList();
   private final List<Float> field_4638 = Lists.newArrayList();

   public WorldGenAttemptDebugRenderer() {
   }

   public void method_3872(BlockPos arg, float f, float g, float h, float i, float j) {
      this.field_4640.add(arg);
      this.field_4635.add(f);
      this.field_4637.add(j);
      this.field_4639.add(g);
      this.field_4636.add(h);
      this.field_4638.add(i);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      Tessellator lv = Tessellator.getInstance();
      BufferBuilder lv2 = lv.getBuffer();
      lv2.begin(5, VertexFormats.POSITION_COLOR);

      for (int i = 0; i < this.field_4640.size(); i++) {
         BlockPos lv3 = this.field_4640.get(i);
         Float float_ = this.field_4635.get(i);
         float g = float_ / 2.0F;
         WorldRenderer.drawBox(
            lv2,
            (double)((float)lv3.getX() + 0.5F - g) - cameraX,
            (double)((float)lv3.getY() + 0.5F - g) - cameraY,
            (double)((float)lv3.getZ() + 0.5F - g) - cameraZ,
            (double)((float)lv3.getX() + 0.5F + g) - cameraX,
            (double)((float)lv3.getY() + 0.5F + g) - cameraY,
            (double)((float)lv3.getZ() + 0.5F + g) - cameraZ,
            this.field_4639.get(i),
            this.field_4636.get(i),
            this.field_4638.get(i),
            this.field_4637.get(i)
         );
      }

      lv.draw();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}
