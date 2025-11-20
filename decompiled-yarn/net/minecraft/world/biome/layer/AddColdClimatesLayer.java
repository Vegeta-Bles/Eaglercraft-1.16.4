package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.SouthEastSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum AddColdClimatesLayer implements SouthEastSamplingLayer {
   INSTANCE;

   private AddColdClimatesLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, int se) {
      if (BiomeLayers.isShallowOcean(se)) {
         return se;
      } else {
         int _snowman = context.nextInt(6);
         if (_snowman == 0) {
            return 4;
         } else {
            return _snowman == 1 ? 3 : 1;
         }
      }
   }
}
