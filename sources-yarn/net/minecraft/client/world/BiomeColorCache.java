package net.minecraft.client.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

@Environment(EnvType.CLIENT)
public class BiomeColorCache {
   private final ThreadLocal<BiomeColorCache.Last> last = ThreadLocal.withInitial(() -> new BiomeColorCache.Last());
   private final Long2ObjectLinkedOpenHashMap<int[]> colors = new Long2ObjectLinkedOpenHashMap(256, 0.25F);
   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

   public BiomeColorCache() {
   }

   public int getBiomeColor(BlockPos pos, IntSupplier colorFactory) {
      int i = pos.getX() >> 4;
      int j = pos.getZ() >> 4;
      BiomeColorCache.Last lv = this.last.get();
      if (lv.x != i || lv.z != j) {
         lv.x = i;
         lv.z = j;
         lv.colors = this.getColorArray(i, j);
      }

      int k = pos.getX() & 15;
      int l = pos.getZ() & 15;
      int m = l << 4 | k;
      int n = lv.colors[m];
      if (n != -1) {
         return n;
      } else {
         int o = colorFactory.getAsInt();
         lv.colors[m] = o;
         return o;
      }
   }

   public void reset(int chunkX, int chunkZ) {
      try {
         this.lock.writeLock().lock();

         for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
               long m = ChunkPos.toLong(chunkX + k, chunkZ + l);
               this.colors.remove(m);
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
      long l = ChunkPos.toLong(chunkX, chunkZ);
      this.lock.readLock().lock();

      int[] is;
      try {
         is = (int[])this.colors.get(l);
      } finally {
         this.lock.readLock().unlock();
      }

      if (is != null) {
         return is;
      } else {
         int[] ks = new int[256];
         Arrays.fill(ks, -1);

         try {
            this.lock.writeLock().lock();
            if (this.colors.size() >= 256) {
               this.colors.removeFirst();
            }

            this.colors.put(l, ks);
         } finally {
            this.lock.writeLock().unlock();
         }

         return ks;
      }
   }

   @Environment(EnvType.CLIENT)
   static class Last {
      public int x = Integer.MIN_VALUE;
      public int z = Integer.MIN_VALUE;
      public int[] colors;

      private Last() {
      }
   }
}
