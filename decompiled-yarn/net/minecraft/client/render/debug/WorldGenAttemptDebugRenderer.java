package net.minecraft.client.render.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class WorldGenAttemptDebugRenderer implements DebugRenderer.Renderer {
   private final List<BlockPos> field_4640 = Lists.newArrayList();
   private final List<Float> field_4635 = Lists.newArrayList();
   private final List<Float> field_4637 = Lists.newArrayList();
   private final List<Float> field_4639 = Lists.newArrayList();
   private final List<Float> field_4636 = Lists.newArrayList();
   private final List<Float> field_4638 = Lists.newArrayList();

   public WorldGenAttemptDebugRenderer() {
   }

   public void method_3872(BlockPos _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.field_4640.add(_snowman);
      this.field_4635.add(_snowman);
      this.field_4637.add(_snowman);
      this.field_4639.add(_snowman);
      this.field_4636.add(_snowman);
      this.field_4638.add(_snowman);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      _snowmanx.begin(5, VertexFormats.POSITION_COLOR);

      for (int _snowmanxx = 0; _snowmanxx < this.field_4640.size(); _snowmanxx++) {
         BlockPos _snowmanxxx = this.field_4640.get(_snowmanxx);
         Float _snowmanxxxx = this.field_4635.get(_snowmanxx);
         float _snowmanxxxxx = _snowmanxxxx / 2.0F;
         WorldRenderer.drawBox(
            _snowmanx,
            (double)((float)_snowmanxxx.getX() + 0.5F - _snowmanxxxxx) - cameraX,
            (double)((float)_snowmanxxx.getY() + 0.5F - _snowmanxxxxx) - cameraY,
            (double)((float)_snowmanxxx.getZ() + 0.5F - _snowmanxxxxx) - cameraZ,
            (double)((float)_snowmanxxx.getX() + 0.5F + _snowmanxxxxx) - cameraX,
            (double)((float)_snowmanxxx.getY() + 0.5F + _snowmanxxxxx) - cameraY,
            (double)((float)_snowmanxxx.getZ() + 0.5F + _snowmanxxxxx) - cameraZ,
            this.field_4639.get(_snowmanxx),
            this.field_4636.get(_snowmanxx),
            this.field_4638.get(_snowmanxx),
            this.field_4637.get(_snowmanxx)
         );
      }

      _snowman.draw();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}
