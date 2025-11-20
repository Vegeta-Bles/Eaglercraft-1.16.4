package net.minecraft.util.math;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public class ChunkPos {
   public static final long MARKER = toLong(1875016, 1875016);
   public final int x;
   public final int z;

   public ChunkPos(int x, int z) {
      this.x = x;
      this.z = z;
   }

   public ChunkPos(BlockPos pos) {
      this.x = pos.getX() >> 4;
      this.z = pos.getZ() >> 4;
   }

   public ChunkPos(long pos) {
      this.x = (int)pos;
      this.z = (int)(pos >> 32);
   }

   public long toLong() {
      return toLong(this.x, this.z);
   }

   public static long toLong(int chunkX, int chunkZ) {
      return (long)chunkX & 4294967295L | ((long)chunkZ & 4294967295L) << 32;
   }

   public static int getPackedX(long pos) {
      return (int)(pos & 4294967295L);
   }

   public static int getPackedZ(long pos) {
      return (int)(pos >>> 32 & 4294967295L);
   }

   @Override
   public int hashCode() {
      int _snowman = 1664525 * this.x + 1013904223;
      int _snowmanx = 1664525 * (this.z ^ -559038737) + 1013904223;
      return _snowman ^ _snowmanx;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ChunkPos)) {
         return false;
      } else {
         ChunkPos _snowman = (ChunkPos)o;
         return this.x == _snowman.x && this.z == _snowman.z;
      }
   }

   public int getStartX() {
      return this.x << 4;
   }

   public int getStartZ() {
      return this.z << 4;
   }

   public int getEndX() {
      return (this.x << 4) + 15;
   }

   public int getEndZ() {
      return (this.z << 4) + 15;
   }

   public int getRegionX() {
      return this.x >> 5;
   }

   public int getRegionZ() {
      return this.z >> 5;
   }

   public int getRegionRelativeX() {
      return this.x & 31;
   }

   public int getRegionRelativeZ() {
      return this.z & 31;
   }

   @Override
   public String toString() {
      return "[" + this.x + ", " + this.z + "]";
   }

   public BlockPos getStartPos() {
      return new BlockPos(this.getStartX(), 0, this.getStartZ());
   }

   public int method_24022(ChunkPos _snowman) {
      return Math.max(Math.abs(this.x - _snowman.x), Math.abs(this.z - _snowman.z));
   }

   public static Stream<ChunkPos> stream(ChunkPos center, int radius) {
      return stream(new ChunkPos(center.x - radius, center.z - radius), new ChunkPos(center.x + radius, center.z + radius));
   }

   public static Stream<ChunkPos> stream(ChunkPos pos1, ChunkPos pos2) {
      int _snowman = Math.abs(pos1.x - pos2.x) + 1;
      int _snowmanx = Math.abs(pos1.z - pos2.z) + 1;
      final int _snowmanxx = pos1.x < pos2.x ? 1 : -1;
      final int _snowmanxxx = pos1.z < pos2.z ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<ChunkPos>((long)(_snowman * _snowmanx), 64) {
         @Nullable
         private ChunkPos position;

         @Override
         public boolean tryAdvance(Consumer<? super ChunkPos> consumer) {
            if (this.position == null) {
               this.position = pos1;
            } else {
               int _snowman = this.position.x;
               int _snowmanx = this.position.z;
               if (_snowman == pos2.x) {
                  if (_snowmanx == pos2.z) {
                     return false;
                  }

                  this.position = new ChunkPos(pos1.x, _snowmanx + _snowman);
               } else {
                  this.position = new ChunkPos(_snowman + _snowman, _snowmanx);
               }
            }

            consumer.accept(this.position);
            return true;
         }
      }, false);
   }
}
