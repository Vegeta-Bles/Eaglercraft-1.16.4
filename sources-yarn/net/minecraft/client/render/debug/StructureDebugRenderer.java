package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class StructureDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient field_4624;
   private final Map<DimensionType, Map<String, BlockBox>> field_4626 = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, BlockBox>> field_4627 = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, Boolean>> field_4625 = Maps.newIdentityHashMap();

   public StructureDebugRenderer(MinecraftClient arg) {
      this.field_4624 = arg;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      Camera lv = this.field_4624.gameRenderer.getCamera();
      WorldAccess lv2 = this.field_4624.world;
      DimensionType lv3 = lv2.getDimension();
      BlockPos lv4 = new BlockPos(lv.getPos().x, 0.0, lv.getPos().z);
      VertexConsumer lv5 = vertexConsumers.getBuffer(RenderLayer.getLines());
      if (this.field_4626.containsKey(lv3)) {
         for (BlockBox lv6 : this.field_4626.get(lv3).values()) {
            if (lv4.isWithinDistance(lv6.getCenter(), 500.0)) {
               WorldRenderer.drawBox(
                  matrices,
                  lv5,
                  (double)lv6.minX - cameraX,
                  (double)lv6.minY - cameraY,
                  (double)lv6.minZ - cameraZ,
                  (double)(lv6.maxX + 1) - cameraX,
                  (double)(lv6.maxY + 1) - cameraY,
                  (double)(lv6.maxZ + 1) - cameraZ,
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

      if (this.field_4627.containsKey(lv3)) {
         for (Entry<String, BlockBox> entry : this.field_4627.get(lv3).entrySet()) {
            String string = entry.getKey();
            BlockBox lv7 = entry.getValue();
            Boolean boolean_ = this.field_4625.get(lv3).get(string);
            if (lv4.isWithinDistance(lv7.getCenter(), 500.0)) {
               if (boolean_) {
                  WorldRenderer.drawBox(
                     matrices,
                     lv5,
                     (double)lv7.minX - cameraX,
                     (double)lv7.minY - cameraY,
                     (double)lv7.minZ - cameraZ,
                     (double)(lv7.maxX + 1) - cameraX,
                     (double)(lv7.maxY + 1) - cameraY,
                     (double)(lv7.maxZ + 1) - cameraZ,
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
                     lv5,
                     (double)lv7.minX - cameraX,
                     (double)lv7.minY - cameraY,
                     (double)lv7.minZ - cameraZ,
                     (double)(lv7.maxX + 1) - cameraX,
                     (double)(lv7.maxY + 1) - cameraY,
                     (double)(lv7.maxZ + 1) - cameraZ,
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

   public void method_3871(BlockBox arg, List<BlockBox> list, List<Boolean> list2, DimensionType arg2) {
      if (!this.field_4626.containsKey(arg2)) {
         this.field_4626.put(arg2, Maps.newHashMap());
      }

      if (!this.field_4627.containsKey(arg2)) {
         this.field_4627.put(arg2, Maps.newHashMap());
         this.field_4625.put(arg2, Maps.newHashMap());
      }

      this.field_4626.get(arg2).put(arg.toString(), arg);

      for (int i = 0; i < list.size(); i++) {
         BlockBox lv = list.get(i);
         Boolean boolean_ = list2.get(i);
         this.field_4627.get(arg2).put(lv.toString(), lv);
         this.field_4625.get(arg2).put(lv.toString(), boolean_);
      }
   }

   @Override
   public void clear() {
      this.field_4626.clear();
      this.field_4627.clear();
      this.field_4625.clear();
   }
}
