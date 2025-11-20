package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;

public abstract class ChunkToNibbleArrayMap<M extends ChunkToNibbleArrayMap<M>> {
   private final long[] cachePositions = new long[2];
   private final ChunkNibbleArray[] cacheArrays = new ChunkNibbleArray[2];
   private boolean cacheEnabled;
   protected final Long2ObjectOpenHashMap<ChunkNibbleArray> arrays;

   protected ChunkToNibbleArrayMap(Long2ObjectOpenHashMap<ChunkNibbleArray> arrays) {
      this.arrays = arrays;
      this.clearCache();
      this.cacheEnabled = true;
   }

   public abstract M copy();

   public void replaceWithCopy(long pos) {
      this.arrays.put(pos, ((ChunkNibbleArray)this.arrays.get(pos)).copy());
      this.clearCache();
   }

   public boolean containsKey(long chunkPos) {
      return this.arrays.containsKey(chunkPos);
   }

   @Nullable
   public ChunkNibbleArray get(long chunkPos) {
      if (this.cacheEnabled) {
         for (int _snowman = 0; _snowman < 2; _snowman++) {
            if (chunkPos == this.cachePositions[_snowman]) {
               return this.cacheArrays[_snowman];
            }
         }
      }

      ChunkNibbleArray _snowmanx = (ChunkNibbleArray)this.arrays.get(chunkPos);
      if (_snowmanx == null) {
         return null;
      } else {
         if (this.cacheEnabled) {
            for (int _snowmanxx = 1; _snowmanxx > 0; _snowmanxx--) {
               this.cachePositions[_snowmanxx] = this.cachePositions[_snowmanxx - 1];
               this.cacheArrays[_snowmanxx] = this.cacheArrays[_snowmanxx - 1];
            }

            this.cachePositions[0] = chunkPos;
            this.cacheArrays[0] = _snowmanx;
         }

         return _snowmanx;
      }
   }

   @Nullable
   public ChunkNibbleArray removeChunk(long chunkPos) {
      return (ChunkNibbleArray)this.arrays.remove(chunkPos);
   }

   public void put(long pos, ChunkNibbleArray data) {
      this.arrays.put(pos, data);
   }

   public void clearCache() {
      for (int _snowman = 0; _snowman < 2; _snowman++) {
         this.cachePositions[_snowman] = Long.MAX_VALUE;
         this.cacheArrays[_snowman] = null;
      }
   }

   public void disableCache() {
      this.cacheEnabled = false;
   }
}
