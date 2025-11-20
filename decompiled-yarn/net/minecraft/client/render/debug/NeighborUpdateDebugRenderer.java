package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class NeighborUpdateDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;
   private final Map<Long, Map<BlockPos, Integer>> neighborUpdates = Maps.newTreeMap(Ordering.natural().reverse());

   NeighborUpdateDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   public void addNeighborUpdate(long time, BlockPos pos) {
      Map<BlockPos, Integer> _snowman = this.neighborUpdates.computeIfAbsent(time, _snowmanx -> Maps.newHashMap());
      int _snowmanx = _snowman.getOrDefault(pos, 0);
      _snowman.put(pos, _snowmanx + 1);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      long _snowman = this.client.world.getTime();
      int _snowmanx = 200;
      double _snowmanxx = 0.0025;
      Set<BlockPos> _snowmanxxx = Sets.newHashSet();
      Map<BlockPos, Integer> _snowmanxxxx = Maps.newHashMap();
      VertexConsumer _snowmanxxxxx = vertexConsumers.getBuffer(RenderLayer.getLines());
      Iterator<Entry<Long, Map<BlockPos, Integer>>> _snowmanxxxxxx = this.neighborUpdates.entrySet().iterator();

      while (_snowmanxxxxxx.hasNext()) {
         Entry<Long, Map<BlockPos, Integer>> _snowmanxxxxxxx = _snowmanxxxxxx.next();
         Long _snowmanxxxxxxxx = _snowmanxxxxxxx.getKey();
         Map<BlockPos, Integer> _snowmanxxxxxxxxx = _snowmanxxxxxxx.getValue();
         long _snowmanxxxxxxxxxx = _snowman - _snowmanxxxxxxxx;
         if (_snowmanxxxxxxxxxx > 200L) {
            _snowmanxxxxxx.remove();
         } else {
            for (Entry<BlockPos, Integer> _snowmanxxxxxxxxxxx : _snowmanxxxxxxxxx.entrySet()) {
               BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getKey();
               Integer _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getValue();
               if (_snowmanxxx.add(_snowmanxxxxxxxxxxxx)) {
                  Box _snowmanxxxxxxxxxxxxxx = new Box(BlockPos.ORIGIN)
                     .expand(0.002)
                     .contract(0.0025 * (double)_snowmanxxxxxxxxxx)
                     .offset((double)_snowmanxxxxxxxxxxxx.getX(), (double)_snowmanxxxxxxxxxxxx.getY(), (double)_snowmanxxxxxxxxxxxx.getZ())
                     .offset(-cameraX, -cameraY, -cameraZ);
                  WorldRenderer.drawBox(
                     matrices,
                     _snowmanxxxxx,
                     _snowmanxxxxxxxxxxxxxx.minX,
                     _snowmanxxxxxxxxxxxxxx.minY,
                     _snowmanxxxxxxxxxxxxxx.minZ,
                     _snowmanxxxxxxxxxxxxxx.maxX,
                     _snowmanxxxxxxxxxxxxxx.maxY,
                     _snowmanxxxxxxxxxxxxxx.maxZ,
                     1.0F,
                     1.0F,
                     1.0F,
                     1.0F
                  );
                  _snowmanxxxx.put(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
               }
            }
         }
      }

      for (Entry<BlockPos, Integer> _snowmanxxxxxxx : _snowmanxxxx.entrySet()) {
         BlockPos _snowmanxxxxxxxx = _snowmanxxxxxxx.getKey();
         Integer _snowmanxxxxxxxxx = _snowmanxxxxxxx.getValue();
         DebugRenderer.drawString(String.valueOf(_snowmanxxxxxxxxx), _snowmanxxxxxxxx.getX(), _snowmanxxxxxxxx.getY(), _snowmanxxxxxxxx.getZ(), -1);
      }
   }
}
