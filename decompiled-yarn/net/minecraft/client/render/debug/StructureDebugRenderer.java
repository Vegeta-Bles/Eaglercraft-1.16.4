package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;

public class StructureDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient field_4624;
   private final Map<DimensionType, Map<String, BlockBox>> field_4626 = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, BlockBox>> field_4627 = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, Boolean>> field_4625 = Maps.newIdentityHashMap();

   public StructureDebugRenderer(MinecraftClient _snowman) {
      this.field_4624 = _snowman;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      Camera _snowman = this.field_4624.gameRenderer.getCamera();
      WorldAccess _snowmanx = this.field_4624.world;
      DimensionType _snowmanxx = _snowmanx.getDimension();
      BlockPos _snowmanxxx = new BlockPos(_snowman.getPos().x, 0.0, _snowman.getPos().z);
      VertexConsumer _snowmanxxxx = vertexConsumers.getBuffer(RenderLayer.getLines());
      if (this.field_4626.containsKey(_snowmanxx)) {
         for (BlockBox _snowmanxxxxx : this.field_4626.get(_snowmanxx).values()) {
            if (_snowmanxxx.isWithinDistance(_snowmanxxxxx.getCenter(), 500.0)) {
               WorldRenderer.drawBox(
                  matrices,
                  _snowmanxxxx,
                  (double)_snowmanxxxxx.minX - cameraX,
                  (double)_snowmanxxxxx.minY - cameraY,
                  (double)_snowmanxxxxx.minZ - cameraZ,
                  (double)(_snowmanxxxxx.maxX + 1) - cameraX,
                  (double)(_snowmanxxxxx.maxY + 1) - cameraY,
                  (double)(_snowmanxxxxx.maxZ + 1) - cameraZ,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F
               );
            }
         }
      }

      if (this.field_4627.containsKey(_snowmanxx)) {
         for (Entry<String, BlockBox> _snowmanxxxxxx : this.field_4627.get(_snowmanxx).entrySet()) {
            String _snowmanxxxxxxx = _snowmanxxxxxx.getKey();
            BlockBox _snowmanxxxxxxxx = _snowmanxxxxxx.getValue();
            Boolean _snowmanxxxxxxxxx = this.field_4625.get(_snowmanxx).get(_snowmanxxxxxxx);
            if (_snowmanxxx.isWithinDistance(_snowmanxxxxxxxx.getCenter(), 500.0)) {
               if (_snowmanxxxxxxxxx) {
                  WorldRenderer.drawBox(
                     matrices,
                     _snowmanxxxx,
                     (double)_snowmanxxxxxxxx.minX - cameraX,
                     (double)_snowmanxxxxxxxx.minY - cameraY,
                     (double)_snowmanxxxxxxxx.minZ - cameraZ,
                     (double)(_snowmanxxxxxxxx.maxX + 1) - cameraX,
                     (double)(_snowmanxxxxxxxx.maxY + 1) - cameraY,
                     (double)(_snowmanxxxxxxxx.maxZ + 1) - cameraZ,
                     0.0F,
                     1.0F,
                     0.0F,
                     1.0F,
                     0.0F,
                     1.0F,
                     0.0F
                  );
               } else {
                  WorldRenderer.drawBox(
                     matrices,
                     _snowmanxxxx,
                     (double)_snowmanxxxxxxxx.minX - cameraX,
                     (double)_snowmanxxxxxxxx.minY - cameraY,
                     (double)_snowmanxxxxxxxx.minZ - cameraZ,
                     (double)(_snowmanxxxxxxxx.maxX + 1) - cameraX,
                     (double)(_snowmanxxxxxxxx.maxY + 1) - cameraY,
                     (double)(_snowmanxxxxxxxx.maxZ + 1) - cameraZ,
                     0.0F,
                     0.0F,
                     1.0F,
                     1.0F,
                     0.0F,
                     0.0F,
                     1.0F
                  );
               }
            }
         }
      }
   }

   public void method_3871(BlockBox _snowman, List<BlockBox> _snowman, List<Boolean> _snowman, DimensionType _snowman) {
      if (!this.field_4626.containsKey(_snowman)) {
         this.field_4626.put(_snowman, Maps.newHashMap());
      }

      if (!this.field_4627.containsKey(_snowman)) {
         this.field_4627.put(_snowman, Maps.newHashMap());
         this.field_4625.put(_snowman, Maps.newHashMap());
      }

      this.field_4626.get(_snowman).put(_snowman.toString(), _snowman);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         BlockBox _snowmanxxxxx = _snowman.get(_snowmanxxxx);
         Boolean _snowmanxxxxxx = _snowman.get(_snowmanxxxx);
         this.field_4627.get(_snowman).put(_snowmanxxxxx.toString(), _snowmanxxxxx);
         this.field_4625.get(_snowman).put(_snowmanxxxxx.toString(), _snowmanxxxxxx);
      }
   }

   @Override
   public void clear() {
      this.field_4626.clear();
      this.field_4627.clear();
      this.field_4625.clear();
   }
}
