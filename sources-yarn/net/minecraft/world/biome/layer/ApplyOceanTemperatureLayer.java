package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum ApplyOceanTemperatureLayer implements MergingLayer, IdentityCoordinateTransformer {
   INSTANCE;

   private ApplyOceanTemperatureLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
      int k = sampler1.sample(this.transformX(x), this.transformZ(z));
      int l = sampler2.sample(this.transformX(x), this.transformZ(z));
      if (!BiomeLayers.isOcean(k)) {
         return k;
      } else {
         int m = 8;
         int n = 4;

         for (int o = -8; o <= 8; o += 4) {
            for (int p = -8; p <= 8; p += 4) {
               int q = sampler1.sample(this.transformX(x + o), this.transformZ(z + p));
               if (!BiomeLayers.isOcean(q)) {
                  if (l == 44) {
                     return 45;
                  }

                  if (l == 10) {
                     return 46;
                  }
               }
            }
         }

         if (k == 24) {
            if (l == 45) {
               return 48;
            }

            if (l == 0) {
               return 24;
            }

            if (l == 46) {
               return 49;
            }

            if (l == 10) {
               return 50;
            }
         }

         return l;
      }
   }
}
