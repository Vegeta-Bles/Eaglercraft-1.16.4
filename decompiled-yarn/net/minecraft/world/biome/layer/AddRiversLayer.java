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
      int _snowman = sampler1.sample(this.transformX(x), this.transformZ(z));
      int _snowmanx = sampler2.sample(this.transformX(x), this.transformZ(z));
      if (BiomeLayers.isOcean(_snowman)) {
         return _snowman;
      } else if (_snowmanx == 7) {
         if (_snowman == 12) {
            return 11;
         } else {
            return _snowman != 14 && _snowman != 15 ? _snowmanx & 0xFF : 15;
         }
      } else {
         return _snowman;
      }
   }
}
