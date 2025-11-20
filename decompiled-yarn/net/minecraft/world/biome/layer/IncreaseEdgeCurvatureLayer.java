package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum IncreaseEdgeCurvatureLayer implements DiagonalCrossSamplingLayer {
   INSTANCE;

   private IncreaseEdgeCurvatureLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, int sw, int se, int ne, int nw, int center) {
      if (!BiomeLayers.isShallowOcean(center)
         || BiomeLayers.isShallowOcean(nw) && BiomeLayers.isShallowOcean(ne) && BiomeLayers.isShallowOcean(sw) && BiomeLayers.isShallowOcean(se)) {
         if (!BiomeLayers.isShallowOcean(center)
            && (BiomeLayers.isShallowOcean(nw) || BiomeLayers.isShallowOcean(sw) || BiomeLayers.isShallowOcean(ne) || BiomeLayers.isShallowOcean(se))
            && context.nextInt(5) == 0) {
            if (BiomeLayers.isShallowOcean(nw)) {
               return center == 4 ? 4 : nw;
            }

            if (BiomeLayers.isShallowOcean(sw)) {
               return center == 4 ? 4 : sw;
            }

            if (BiomeLayers.isShallowOcean(ne)) {
               return center == 4 ? 4 : ne;
            }

            if (BiomeLayers.isShallowOcean(se)) {
               return center == 4 ? 4 : se;
            }
         }

         return center;
      } else {
         int _snowman = 1;
         int _snowmanx = 1;
         if (!BiomeLayers.isShallowOcean(nw) && context.nextInt(_snowman++) == 0) {
            _snowmanx = nw;
         }

         if (!BiomeLayers.isShallowOcean(ne) && context.nextInt(_snowman++) == 0) {
            _snowmanx = ne;
         }

         if (!BiomeLayers.isShallowOcean(sw) && context.nextInt(_snowman++) == 0) {
            _snowmanx = sw;
         }

         if (!BiomeLayers.isShallowOcean(se) && context.nextInt(_snowman++) == 0) {
            _snowmanx = se;
         }

         if (context.nextInt(3) == 0) {
            return _snowmanx;
         } else {
            return _snowmanx == 4 ? 4 : center;
         }
      }
   }
}
