package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum SmoothLayer implements CrossSamplingLayer {
   INSTANCE;

   private SmoothLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
      boolean _snowman = e == w;
      boolean _snowmanx = n == s;
      if (_snowman == _snowmanx) {
         if (_snowman) {
            return context.nextInt(2) == 0 ? w : n;
         } else {
            return center;
         }
      } else {
         return _snowman ? w : n;
      }
   }
}
