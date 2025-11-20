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
      int _snowman = sampler1.sample(this.transformX(x), this.transformZ(z));
      int _snowmanx = sampler2.sample(this.transformX(x), this.transformZ(z));
      if (!BiomeLayers.isOcean(_snowman)) {
         return _snowman;
      } else {
         int _snowmanxx = 8;
         int _snowmanxxx = 4;

         for (int _snowmanxxxx = -8; _snowmanxxxx <= 8; _snowmanxxxx += 4) {
            for (int _snowmanxxxxx = -8; _snowmanxxxxx <= 8; _snowmanxxxxx += 4) {
               int _snowmanxxxxxx = sampler1.sample(this.transformX(x + _snowmanxxxx), this.transformZ(z + _snowmanxxxxx));
               if (!BiomeLayers.isOcean(_snowmanxxxxxx)) {
                  if (_snowmanx == 44) {
                     return 45;
                  }

                  if (_snowmanx == 10) {
                     return 46;
                  }
               }
            }
         }

         if (_snowman == 24) {
            if (_snowmanx == 45) {
               return 48;
            }

            if (_snowmanx == 0) {
               return 24;
            }

            if (_snowmanx == 46) {
               return 49;
            }

            if (_snowmanx == 10) {
               return 50;
            }
         }

         return _snowmanx;
      }
   }
}
