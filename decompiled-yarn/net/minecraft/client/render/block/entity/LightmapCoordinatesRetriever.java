package net.minecraft.client.render.block.entity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;

public class LightmapCoordinatesRetriever<S extends BlockEntity> implements DoubleBlockProperties.PropertyRetriever<S, Int2IntFunction> {
   public LightmapCoordinatesRetriever() {
   }

   public Int2IntFunction getFromBoth(S _snowman, S _snowman) {
      return _snowmanxxxxxxxxx -> {
         int _snowmanxxx = WorldRenderer.getLightmapCoordinates(_snowman.getWorld(), _snowman.getPos());
         int _snowmanxxxx = WorldRenderer.getLightmapCoordinates(_snowman.getWorld(), _snowman.getPos());
         int _snowmanxxxxx = LightmapTextureManager.getBlockLightCoordinates(_snowmanxxx);
         int _snowmanxxxxxx = LightmapTextureManager.getBlockLightCoordinates(_snowmanxxxx);
         int _snowmanxxxxxxx = LightmapTextureManager.getSkyLightCoordinates(_snowmanxxx);
         int _snowmanxxxxxxxx = LightmapTextureManager.getSkyLightCoordinates(_snowmanxxxx);
         return LightmapTextureManager.pack(Math.max(_snowmanxxxxx, _snowmanxxxxxx), Math.max(_snowmanxxxxxxx, _snowmanxxxxxxxx));
      };
   }

   public Int2IntFunction getFrom(S _snowman) {
      return _snowmanx -> _snowmanx;
   }

   public Int2IntFunction getFallback() {
      return _snowman -> _snowman;
   }
}
