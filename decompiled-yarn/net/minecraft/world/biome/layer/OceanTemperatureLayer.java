package net.minecraft.world.biome.layer;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum OceanTemperatureLayer implements InitLayer {
   INSTANCE;

   private OceanTemperatureLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, int x, int y) {
      PerlinNoiseSampler _snowman = context.getNoiseSampler();
      double _snowmanx = _snowman.sample((double)x / 8.0, (double)y / 8.0, 0.0, 0.0, 0.0);
      if (_snowmanx > 0.4) {
         return 44;
      } else if (_snowmanx > 0.2) {
         return 45;
      } else if (_snowmanx < -0.4) {
         return 10;
      } else {
         return _snowmanx < -0.2 ? 46 : 0;
      }
   }
}
