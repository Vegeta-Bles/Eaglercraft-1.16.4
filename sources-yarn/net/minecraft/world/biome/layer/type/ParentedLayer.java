package net.minecraft.world.biome.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface ParentedLayer extends CoordinateTransformer {
   default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent) {
      return () -> {
         R lv = parent.make();
         return context.createSampler((i, j) -> {
            context.initSeed((long)i, (long)j);
            return this.sample(context, lv, i, j);
         }, lv);
      };
   }

   int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z);
}
