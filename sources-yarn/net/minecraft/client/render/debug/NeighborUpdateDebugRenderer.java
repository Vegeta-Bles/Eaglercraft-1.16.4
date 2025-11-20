package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

@Environment(EnvType.CLIENT)
public class NeighborUpdateDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;
   private final Map<Long, Map<BlockPos, Integer>> neighborUpdates = Maps.newTreeMap(Ordering.natural().reverse());

   NeighborUpdateDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   public void addNeighborUpdate(long time, BlockPos pos) {
      Map<BlockPos, Integer> map = this.neighborUpdates.computeIfAbsent(time, long_ -> Maps.newHashMap());
      int i = map.getOrDefault(pos, 0);
      map.put(pos, i + 1);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      long l = this.client.world.getTime();
      int i = 200;
      double g = 0.0025;
      Set<BlockPos> set = Sets.newHashSet();
      Map<BlockPos, Integer> map = Maps.newHashMap();
      VertexConsumer lv = vertexConsumers.getBuffer(RenderLayer.getLines());
      Iterator<Entry<Long, Map<BlockPos, Integer>>> iterator = this.neighborUpdates.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<Long, Map<BlockPos, Integer>> entry = iterator.next();
         Long long_ = entry.getKey();
         Map<BlockPos, Integer> map2 = entry.getValue();
         long m = l - long_;
         if (m > 200L) {
            iterator.remove();
         } else {
            for (Entry<BlockPos, Integer> entry2 : map2.entrySet()) {
               BlockPos lv2 = entry2.getKey();
               Integer integer = entry2.getValue();
               if (set.add(lv2)) {
                  Box lv3 = new Box(BlockPos.ORIGIN)
                     .expand(0.002)
                     .contract(0.0025 * (double)m)
                     .offset((double)lv2.getX(), (double)lv2.getY(), (double)lv2.getZ())
                     .offset(-cameraX, -cameraY, -cameraZ);
                  WorldRenderer.drawBox(matrices, lv, lv3.minX, lv3.minY, lv3.minZ, lv3.maxX, lv3.maxY, lv3.maxZ, 1.0F, 1.0F, 1.0F, 1.0F);
                  map.put(lv2, integer);
               }
            }
         }
      }

      for (Entry<BlockPos, Integer> entry3 : map.entrySet()) {
         BlockPos lv4 = entry3.getKey();
         Integer integer2 = entry3.getValue();
         DebugRenderer.drawString(String.valueOf(integer2), lv4.getX(), lv4.getY(), lv4.getZ(), -1);
      }
   }
}
