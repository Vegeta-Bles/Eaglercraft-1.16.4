package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum NoiseToRiverLayer implements CrossSamplingLayer {
   INSTANCE;

   private NoiseToRiverLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
      int nx = isValidForRiver(center);
      return nx == isValidForRiver(w) && nx == isValidForRiver(n) && nx == isValidForRiver(e) && nx == isValidForRiver(s) ? -1 : 7;
   }

   private static int isValidForRiver(int value) {
      return value >= 2 ? 2 + (value & 1) : value;
   }
}
