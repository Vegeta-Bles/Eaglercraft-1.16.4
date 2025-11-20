package net.minecraft.world.biome.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface ParentedLayer extends CoordinateTransformer {
   default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent) {
      return () -> {
         R _snowmanxx = parent.make();
         return context.createSampler((_snowmanxxx, _snowmanxx) -> {
            context.initSeed((long)_snowmanxxx, (long)_snowmanxx);
            return this.sample(context, _snowman, _snowmanxxx, _snowmanxx);
         }, _snowmanxx);
      };
   }

   int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z);
}
