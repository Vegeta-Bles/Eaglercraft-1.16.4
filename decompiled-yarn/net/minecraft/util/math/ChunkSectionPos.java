package net.minecraft.util.math;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.entity.Entity;
import net.minecraft.util.CuboidBlockIterator;

public class ChunkSectionPos extends Vec3i {
   private ChunkSectionPos(int x, int y, int z) {
      super(x, y, z);
   }

   public static ChunkSectionPos from(int x, int y, int z) {
      return new ChunkSectionPos(x, y, z);
   }

   public static ChunkSectionPos from(BlockPos pos) {
      return new ChunkSectionPos(getSectionCoord(pos.getX()), getSectionCoord(pos.getY()), getSectionCoord(pos.getZ()));
   }

   public static ChunkSectionPos from(ChunkPos chunkPos, int y) {
      return new ChunkSectionPos(chunkPos.x, y, chunkPos.z);
   }

   public static ChunkSectionPos from(Entity entity) {
      return new ChunkSectionPos(
         getSectionCoord(MathHelper.floor(entity.getX())), getSectionCoord(MathHelper.floor(entity.getY())), getSectionCoord(MathHelper.floor(entity.getZ()))
      );
   }

   public static ChunkSectionPos from(long packed) {
      return new ChunkSectionPos(unpackX(packed), unpackY(packed), unpackZ(packed));
   }

   public static long offset(long packed, Direction direction) {
      return offset(packed, direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
   }

   public static long offset(long packed, int x, int y, int z) {
      return asLong(unpackX(packed) + x, unpackY(packed) + y, unpackZ(packed) + z);
   }

   public static int getSectionCoord(int coord) {
      return coord >> 4;
   }

   public static int getLocalCoord(int coord) {
      return coord & 15;
   }

   public static short packLocal(BlockPos pos) {
      int _snowman = getLocalCoord(pos.getX());
      int _snowmanx = getLocalCoord(pos.getY());
      int _snowmanxx = getLocalCoord(pos.getZ());
      return (short)(_snowman << 8 | _snowmanxx << 4 | _snowmanx << 0);
   }

   public static int unpackLocalX(short packedLocalPos) {
      return packedLocalPos >>> 8 & 15;
   }

   public static int unpackLocalY(short packedLocalPos) {
      return packedLocalPos >>> 0 & 15;
   }

   public static int unpackLocalZ(short packedLocalPos) {
      return packedLocalPos >>> 4 & 15;
   }

   public int unpackBlockX(short packedLocalPos) {
      return this.getMinX() + unpackLocalX(packedLocalPos);
   }

   public int unpackBlockY(short packedLocalPos) {
      return this.getMinY() + unpackLocalY(packedLocalPos);
   }

   public int unpackBlockZ(short packedLocalPos) {
      return this.getMinZ() + unpackLocalZ(packedLocalPos);
   }

   public BlockPos unpackBlockPos(short packedLocalPos) {
      return new BlockPos(this.unpackBlockX(packedLocalPos), this.unpackBlockY(packedLocalPos), this.unpackBlockZ(packedLocalPos));
   }

   public static int getBlockCoord(int sectionCoord) {
      return sectionCoord << 4;
   }

   public static int unpackX(long packed) {
      return (int)(packed << 0 >> 42);
   }

   public static int unpackY(long packed) {
      return (int)(packed << 44 >> 44);
   }

   public static int unpackZ(long packed) {
      return (int)(packed << 22 >> 42);
   }

   public int getSectionX() {
      return this.getX();
   }

   public int getSectionY() {
      return this.getY();
   }

   public int getSectionZ() {
      return this.getZ();
   }

   public int getMinX() {
      return this.getSectionX() << 4;
   }

   public int getMinY() {
      return this.getSectionY() << 4;
   }

   public int getMinZ() {
      return this.getSectionZ() << 4;
   }

   public int getMaxX() {
      return (this.getSectionX() << 4) + 15;
   }

   public int getMaxY() {
      return (this.getSectionY() << 4) + 15;
   }

   public int getMaxZ() {
      return (this.getSectionZ() << 4) + 15;
   }

   public static long fromBlockPos(long blockPos) {
      return asLong(
         getSectionCoord(BlockPos.unpackLongX(blockPos)), getSectionCoord(BlockPos.unpackLongY(blockPos)), getSectionCoord(BlockPos.unpackLongZ(blockPos))
      );
   }

   public static long withZeroY(long pos) {
      return pos & -1048576L;
   }

   public BlockPos getMinPos() {
      return new BlockPos(getBlockCoord(this.getSectionX()), getBlockCoord(this.getSectionY()), getBlockCoord(this.getSectionZ()));
   }

   public BlockPos getCenterPos() {
      int _snowman = 8;
      return this.getMinPos().add(8, 8, 8);
   }

   public ChunkPos toChunkPos() {
      return new ChunkPos(this.getSectionX(), this.getSectionZ());
   }

   public static long asLong(int x, int y, int z) {
      long _snowman = 0L;
      _snowman |= ((long)x & 4194303L) << 42;
      _snowman |= ((long)y & 1048575L) << 0;
      return _snowman | ((long)z & 4194303L) << 20;
   }

   public long asLong() {
      return asLong(this.getSectionX(), this.getSectionY(), this.getSectionZ());
   }

   public Stream<BlockPos> streamBlocks() {
      return BlockPos.stream(this.getMinX(), this.getMinY(), this.getMinZ(), this.getMaxX(), this.getMaxY(), this.getMaxZ());
   }

   public static Stream<ChunkSectionPos> stream(ChunkSectionPos center, int radius) {
      int _snowman = center.getSectionX();
      int _snowmanx = center.getSectionY();
      int _snowmanxx = center.getSectionZ();
      return stream(_snowman - radius, _snowmanx - radius, _snowmanxx - radius, _snowman + radius, _snowmanx + radius, _snowmanxx + radius);
   }

   public static Stream<ChunkSectionPos> stream(ChunkPos center, int radius) {
      int _snowman = center.x;
      int _snowmanx = center.z;
      return stream(_snowman - radius, 0, _snowmanx - radius, _snowman + radius, 15, _snowmanx + radius);
   }

   public static Stream<ChunkSectionPos> stream(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      return StreamSupport.stream(new AbstractSpliterator<ChunkSectionPos>((long)((maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1)), 64) {
         final CuboidBlockIterator iterator = new CuboidBlockIterator(minX, minY, minZ, maxX, maxY, maxZ);

         @Override
         public boolean tryAdvance(Consumer<? super ChunkSectionPos> consumer) {
            if (this.iterator.step()) {
               consumer.accept(new ChunkSectionPos(this.iterator.getX(), this.iterator.getY(), this.iterator.getZ()));
               return true;
            } else {
               return false;
            }
         }
      }, false);
   }
}
