package net.minecraft.world.chunk;

import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

public class ChunkCache implements BlockView, CollisionView {
   protected final int minX;
   protected final int minZ;
   protected final Chunk[][] chunks;
   protected boolean empty;
   protected final World world;

   public ChunkCache(World world, BlockPos minPos, BlockPos maxPos) {
      this.world = world;
      this.minX = minPos.getX() >> 4;
      this.minZ = minPos.getZ() >> 4;
      int _snowman = maxPos.getX() >> 4;
      int _snowmanx = maxPos.getZ() >> 4;
      this.chunks = new Chunk[_snowman - this.minX + 1][_snowmanx - this.minZ + 1];
      ChunkManager _snowmanxx = world.getChunkManager();
      this.empty = true;

      for (int _snowmanxxx = this.minX; _snowmanxxx <= _snowman; _snowmanxxx++) {
         for (int _snowmanxxxx = this.minZ; _snowmanxxxx <= _snowmanx; _snowmanxxxx++) {
            this.chunks[_snowmanxxx - this.minX][_snowmanxxxx - this.minZ] = _snowmanxx.getWorldChunk(_snowmanxxx, _snowmanxxxx);
         }
      }

      for (int _snowmanxxx = minPos.getX() >> 4; _snowmanxxx <= maxPos.getX() >> 4; _snowmanxxx++) {
         for (int _snowmanxxxx = minPos.getZ() >> 4; _snowmanxxxx <= maxPos.getZ() >> 4; _snowmanxxxx++) {
            Chunk _snowmanxxxxx = this.chunks[_snowmanxxx - this.minX][_snowmanxxxx - this.minZ];
            if (_snowmanxxxxx != null && !_snowmanxxxxx.areSectionsEmptyBetween(minPos.getY(), maxPos.getY())) {
               this.empty = false;
               return;
            }
         }
      }
   }

   private Chunk method_22354(BlockPos _snowman) {
      return this.method_22353(_snowman.getX() >> 4, _snowman.getZ() >> 4);
   }

   private Chunk method_22353(int _snowman, int _snowman) {
      int _snowmanxx = _snowman - this.minX;
      int _snowmanxxx = _snowman - this.minZ;
      if (_snowmanxx >= 0 && _snowmanxx < this.chunks.length && _snowmanxxx >= 0 && _snowmanxxx < this.chunks[_snowmanxx].length) {
         Chunk _snowmanxxxx = this.chunks[_snowmanxx][_snowmanxxx];
         return (Chunk)(_snowmanxxxx != null ? _snowmanxxxx : new EmptyChunk(this.world, new ChunkPos(_snowman, _snowman)));
      } else {
         return new EmptyChunk(this.world, new ChunkPos(_snowman, _snowman));
      }
   }

   @Override
   public WorldBorder getWorldBorder() {
      return this.world.getWorldBorder();
   }

   @Override
   public BlockView getExistingChunk(int chunkX, int chunkZ) {
      return this.method_22353(chunkX, chunkZ);
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos pos) {
      Chunk _snowman = this.method_22354(pos);
      return _snowman.getBlockEntity(pos);
   }

   @Override
   public BlockState getBlockState(BlockPos pos) {
      if (World.isOutOfBuildLimitVertically(pos)) {
         return Blocks.AIR.getDefaultState();
      } else {
         Chunk _snowman = this.method_22354(pos);
         return _snowman.getBlockState(pos);
      }
   }

   @Override
   public Stream<VoxelShape> getEntityCollisions(@Nullable Entity _snowman, Box _snowman, Predicate<Entity> _snowman) {
      return Stream.empty();
   }

   @Override
   public Stream<VoxelShape> getCollisions(@Nullable Entity _snowman, Box _snowman, Predicate<Entity> _snowman) {
      return this.getBlockCollisions(_snowman, _snowman);
   }

   @Override
   public FluidState getFluidState(BlockPos pos) {
      if (World.isOutOfBuildLimitVertically(pos)) {
         return Fluids.EMPTY.getDefaultState();
      } else {
         Chunk _snowman = this.method_22354(pos);
         return _snowman.getFluidState(pos);
      }
   }
}
