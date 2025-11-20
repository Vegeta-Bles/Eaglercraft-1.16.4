package net.minecraft.client.render.debug;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class CaveDebugRenderer implements DebugRenderer.Renderer {
   private final Map<BlockPos, BlockPos> field_4507 = Maps.newHashMap();
   private final Map<BlockPos, Float> field_4508 = Maps.newHashMap();
   private final List<BlockPos> field_4506 = Lists.newArrayList();

   public CaveDebugRenderer() {
   }

   public void method_3704(BlockPos _snowman, List<BlockPos> _snowman, List<Float> _snowman) {
      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         this.field_4507.put(_snowman.get(_snowmanxxx), _snowman);
         this.field_4508.put(_snowman.get(_snowmanxxx), _snowman.get(_snowmanxxx));
      }

      this.field_4506.add(_snowman);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      BlockPos _snowman = new BlockPos(cameraX, 0.0, cameraZ);
      Tessellator _snowmanx = Tessellator.getInstance();
      BufferBuilder _snowmanxx = _snowmanx.getBuffer();
      _snowmanxx.begin(5, VertexFormats.POSITION_COLOR);

      for (Entry<BlockPos, BlockPos> _snowmanxxx : this.field_4507.entrySet()) {
         BlockPos _snowmanxxxx = _snowmanxxx.getKey();
         BlockPos _snowmanxxxxx = _snowmanxxx.getValue();
         float _snowmanxxxxxx = (float)(_snowmanxxxxx.getX() * 128 % 256) / 256.0F;
         float _snowmanxxxxxxx = (float)(_snowmanxxxxx.getY() * 128 % 256) / 256.0F;
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxx.getZ() * 128 % 256) / 256.0F;
         float _snowmanxxxxxxxxx = this.field_4508.get(_snowmanxxxx);
         if (_snowman.isWithinDistance(_snowmanxxxx, 160.0)) {
            WorldRenderer.drawBox(
               _snowmanxx,
               (double)((float)_snowmanxxxx.getX() + 0.5F) - cameraX - (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.getY() + 0.5F) - cameraY - (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.getZ() + 0.5F) - cameraZ - (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.getX() + 0.5F) - cameraX + (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.getY() + 0.5F) - cameraY + (double)_snowmanxxxxxxxxx,
               (double)((float)_snowmanxxxx.getZ() + 0.5F) - cameraZ + (double)_snowmanxxxxxxxxx,
               _snowmanxxxxxx,
               _snowmanxxxxxxx,
               _snowmanxxxxxxxx,
               0.5F
            );
         }
      }

      for (BlockPos _snowmanxxxx : this.field_4506) {
         if (_snowman.isWithinDistance(_snowmanxxxx, 160.0)) {
            WorldRenderer.drawBox(
               _snowmanxx,
               (double)_snowmanxxxx.getX() - cameraX,
               (double)_snowmanxxxx.getY() - cameraY,
               (double)_snowmanxxxx.getZ() - cameraZ,
               (double)((float)_snowmanxxxx.getX() + 1.0F) - cameraX,
               (double)((float)_snowmanxxxx.getY() + 1.0F) - cameraY,
               (double)((float)_snowmanxxxx.getZ() + 1.0F) - cameraZ,
               1.0F,
               1.0F,
               1.0F,
               1.0F
            );
         }
      }

      _snowmanx.draw();
      RenderSystem.enableDepthTest();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}
