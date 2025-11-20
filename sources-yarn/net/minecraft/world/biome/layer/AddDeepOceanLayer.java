package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum AddDeepOceanLayer implements CrossSamplingLayer {
   INSTANCE;

   private AddDeepOceanLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
      if (BiomeLayers.isShallowOcean(center)) {
         int nx = 0;
         if (BiomeLayers.isShallowOcean(n)) {
            nx++;
         }

         if (BiomeLayers.isShallowOcean(e)) {
            nx++;
         }

         if (BiomeLayers.isShallowOcean(w)) {
            nx++;
         }

         if (BiomeLayers.isShallowOcean(s)) {
            nx++;
         }

         if (nx > 3) {
            if (center == 44) {
               return 47;
            }

            if (center == 45) {
               return 48;
            }

            if (center == 0) {
               return 24;
            }

            if (center == 46) {
               return 49;
            }

            if (center == 10) {
               return 50;
            }

            return 24;
         }
      }

      return center;
   }
}
