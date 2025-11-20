package net.minecraft.client.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class BiomeColorCache {
   private final ThreadLocal<BiomeColorCache.Last> last = ThreadLocal.withInitial(() -> new BiomeColorCache.Last());
   private final Long2ObjectLinkedOpenHashMap<int[]> colors = new Long2ObjectLinkedOpenHashMap(256, 0.25F);
   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

   public BiomeColorCache() {
   }

   public int getBiomeColor(BlockPos pos, IntSupplier colorFactory) {
      int _snowman = pos.getX() >> 4;
      int _snowmanx = pos.getZ() >> 4;
      BiomeColorCache.Last _snowmanxx = this.last.get();
      if (_snowmanxx.x != _snowman || _snowmanxx.z != _snowmanx) {
         _snowmanxx.x = _snowman;
         _snowmanxx.z = _snowmanx;
         _snowmanxx.colors = this.getColorArray(_snowman, _snowmanx);
      }

      int _snowmanxxx = pos.getX() & 15;
      int _snowmanxxxx = pos.getZ() & 15;
      int _snowmanxxxxx = _snowmanxxxx << 4 | _snowmanxxx;
      int _snowmanxxxxxx = _snowmanxx.colors[_snowmanxxxxx];
      if (_snowmanxxxxxx != -1) {
         return _snowmanxxxxxx;
      } else {
         int _snowmanxxxxxxx = colorFactory.getAsInt();
         _snowmanxx.colors[_snowmanxxxxx] = _snowmanxxxxxxx;
         return _snowmanxxxxxxx;
      }
   }

   public void reset(int chunkX, int chunkZ) {
      try {
         this.lock.writeLock().lock();

         for (int _snowman = -1; _snowman <= 1; _snowman++) {
            for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
               long _snowmanxx = ChunkPos.toLong(chunkX + _snowman, chunkZ + _snowmanx);
               this.colors.remove(_snowmanxx);
            }
         }
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   public void reset() {
      try {
         this.lock.writeLock().lock();
         this.colors.clear();
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   private int[] getColorArray(int chunkX, int chunkZ) {
      long _snowman = ChunkPos.toLong(chunkX, chunkZ);
      this.lock.readLock().lock();

      int[] _snowmanx;
      try {
         _snowmanx = (int[])this.colors.get(_snowman);
      } finally {
         this.lock.readLock().unlock();
      }

      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         int[] _snowmanxx = new int[256];
         Arrays.fill(_snowmanxx, -1);

         try {
            this.lock.writeLock().lock();
            if (this.colors.size() >= 256) {
               this.colors.removeFirst();
            }

            this.colors.put(_snowman, _snowmanxx);
         } finally {
            this.lock.writeLock().unlock();
         }

         return _snowmanxx;
      }
   }

   static class Last {
      public int x = Integer.MIN_VALUE;
      public int z = Integer.MIN_VALUE;
      public int[] colors;

      private Last() {
      }
   }
}
