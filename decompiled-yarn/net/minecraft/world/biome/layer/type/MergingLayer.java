package net.minecraft.world.biome.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface MergingLayer extends CoordinateTransformer {
   default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> layer1, LayerFactory<R> layer2) {
      return () -> {
         R _snowmanxxx = layer1.make();
         R _snowmanx = layer2.make();
         return context.createSampler((_snowmanxxxx, _snowmanxxx) -> {
            context.initSeed((long)_snowmanxxxx, (long)_snowmanxxx);
            return this.sample(context, _snowman, _snowman, _snowmanxxxx, _snowmanxxx);
         }, _snowmanxxx, _snowmanx);
      };
   }

   int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z);
}
