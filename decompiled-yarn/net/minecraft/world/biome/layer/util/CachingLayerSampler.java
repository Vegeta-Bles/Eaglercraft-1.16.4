package net.minecraft.world.biome.layer.util;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.util.math.ChunkPos;

public final class CachingLayerSampler implements LayerSampler {
   private final LayerOperator operator;
   private final Long2IntLinkedOpenHashMap cache;
   private final int cacheCapacity;

   public CachingLayerSampler(Long2IntLinkedOpenHashMap cache, int cacheCapacity, LayerOperator operator) {
      this.cache = cache;
      this.cacheCapacity = cacheCapacity;
      this.operator = operator;
   }

   @Override
   public int sample(int x, int z) {
      long _snowman = ChunkPos.toLong(x, z);
      synchronized (this.cache) {
         int _snowmanx = this.cache.get(_snowman);
         if (_snowmanx != Integer.MIN_VALUE) {
            return _snowmanx;
         } else {
            int _snowmanxx = this.operator.apply(x, z);
            this.cache.put(_snowman, _snowmanxx);
            if (this.cache.size() > this.cacheCapacity) {
               for (int _snowmanxxx = 0; _snowmanxxx < this.cacheCapacity / 16; _snowmanxxx++) {
                  this.cache.removeFirstInt();
               }
            }

            return _snowmanxx;
         }
      }
   }

   public int getCapacity() {
      return this.cacheCapacity;
   }
}
