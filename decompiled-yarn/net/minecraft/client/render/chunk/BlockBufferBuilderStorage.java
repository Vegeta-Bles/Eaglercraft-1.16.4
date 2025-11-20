package net.minecraft.client.render.chunk;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;

public class BlockBufferBuilderStorage {
   private final Map<RenderLayer, BufferBuilder> builders = RenderLayer.getBlockLayers()
      .stream()
      .collect(Collectors.toMap(_snowman -> (RenderLayer)_snowman, _snowman -> new BufferBuilder(_snowman.getExpectedBufferSize())));

   public BlockBufferBuilderStorage() {
   }

   public BufferBuilder get(RenderLayer layer) {
      return this.builders.get(layer);
   }

   public void clear() {
      this.builders.values().forEach(BufferBuilder::clear);
   }

   public void reset() {
      this.builders.values().forEach(BufferBuilder::reset);
   }
}
