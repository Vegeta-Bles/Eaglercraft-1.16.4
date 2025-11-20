package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum AddRiversLayer implements MergingLayer, IdentityCoordinateTransformer {
   INSTANCE;

   private AddRiversLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
      int k = sampler1.sample(this.transformX(x), this.transformZ(z));
      int l = sampler2.sample(this.transformX(x), this.transformZ(z));
      if (BiomeLayers.isOcean(k)) {
         return k;
      } else if (l == 7) {
         if (k == 12) {
            return 11;
         } else {
            return k != 14 && k != 15 ? l & 0xFF : 15;
         }
      } else {
         return k;
      }
   }
}
