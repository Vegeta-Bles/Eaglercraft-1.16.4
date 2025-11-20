package net.minecraft.client.render.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.level.ColorResolver;

public class ChunkRendererRegion implements BlockRenderView {
   protected final int chunkXOffset;
   protected final int chunkZOffset;
   protected final BlockPos offset;
   protected final int xSize;
   protected final int ySize;
   protected final int zSize;
   protected final WorldChunk[][] chunks;
   protected final BlockState[] blockStates;
   protected final FluidState[] fluidStates;
   protected final World world;

   @Nullable
   public static ChunkRendererRegion create(World world, BlockPos startPos, BlockPos endPos, int chunkRadius) {
      int _snowman = startPos.getX() - chunkRadius >> 4;
      int _snowmanx = startPos.getZ() - chunkRadius >> 4;
      int _snowmanxx = endPos.getX() + chunkRadius >> 4;
      int _snowmanxxx = endPos.getZ() + chunkRadius >> 4;
      WorldChunk[][] _snowmanxxxx = new WorldChunk[_snowmanxx - _snowman + 1][_snowmanxxx - _snowmanx + 1];

      for (int _snowmanxxxxx = _snowman; _snowmanxxxxx <= _snowmanxx; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = _snowmanx; _snowmanxxxxxx <= _snowmanxxx; _snowmanxxxxxx++) {
            _snowmanxxxx[_snowmanxxxxx - _snowman][_snowmanxxxxxx - _snowmanx] = world.getChunk(_snowmanxxxxx, _snowmanxxxxxx);
         }
      }

      if (method_30000(startPos, endPos, _snowman, _snowmanx, _snowmanxxxx)) {
         return null;
      } else {
         int _snowmanxxxxx = 1;
         BlockPos _snowmanxxxxxx = startPos.add(-1, -1, -1);
         BlockPos _snowmanxxxxxxx = endPos.add(1, 1, 1);
         return new ChunkRendererRegion(world, _snowman, _snowmanx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
      }
   }

   public static boolean method_30000(BlockPos _snowman, BlockPos _snowman, int _snowman, int _snowman, WorldChunk[][] _snowman) {
      for (int _snowmanxxxxx = _snowman.getX() >> 4; _snowmanxxxxx <= _snowman.getX() >> 4; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = _snowman.getZ() >> 4; _snowmanxxxxxx <= _snowman.getZ() >> 4; _snowmanxxxxxx++) {
            WorldChunk _snowmanxxxxxxx = _snowman[_snowmanxxxxx - _snowman][_snowmanxxxxxx - _snowman];
            if (!_snowmanxxxxxxx.areSectionsEmptyBetween(_snowman.getY(), _snowman.getY())) {
               return false;
            }
         }
      }

      return true;
   }

   public ChunkRendererRegion(World world, int chunkX, int chunkZ, WorldChunk[][] chunks, BlockPos startPos, BlockPos endPos) {
      this.world = world;
      this.chunkXOffset = chunkX;
      this.chunkZOffset = chunkZ;
      this.chunks = chunks;
      this.offset = startPos;
      this.xSize = endPos.getX() - startPos.getX() + 1;
      this.ySize = endPos.getY() - startPos.getY() + 1;
      this.zSize = endPos.getZ() - startPos.getZ() + 1;
      this.blockStates = new BlockState[this.xSize * this.ySize * this.zSize];
      this.fluidStates = new FluidState[this.xSize * this.ySize * this.zSize];

      for (BlockPos _snowman : BlockPos.iterate(startPos, endPos)) {
         int _snowmanx = (_snowman.getX() >> 4) - chunkX;
         int _snowmanxx = (_snowman.getZ() >> 4) - chunkZ;
         WorldChunk _snowmanxxx = chunks[_snowmanx][_snowmanxx];
         int _snowmanxxxx = this.getIndex(_snowman);
         this.blockStates[_snowmanxxxx] = _snowmanxxx.getBlockState(_snowman);
         this.fluidStates[_snowmanxxxx] = _snowmanxxx.getFluidState(_snowman);
      }
   }

   protected final int getIndex(BlockPos pos) {
      return this.getIndex(pos.getX(), pos.getY(), pos.getZ());
   }

   protected int getIndex(int x, int y, int z) {
      int _snowman = x - this.offset.getX();
      int _snowmanx = y - this.offset.getY();
      int _snowmanxx = z - this.offset.getZ();
      return _snowmanxx * this.xSize * this.ySize + _snowmanx * this.xSize + _snowman;
   }

   @Override
   public BlockState getBlockState(BlockPos pos) {
      return this.blockStates[this.getIndex(pos)];
   }

   @Override
   public FluidState getFluidState(BlockPos pos) {
      return this.fluidStates[this.getIndex(pos)];
   }

   @Override
   public float getBrightness(Direction direction, boolean shaded) {
      return this.world.getBrightness(direction, shaded);
   }

   @Override
   public LightingProvider getLightingProvider() {
      return this.world.getLightingProvider();
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos pos) {
      return this.getBlockEntity(pos, WorldChunk.CreationType.IMMEDIATE);
   }

   @Nullable
   public BlockEntity getBlockEntity(BlockPos _snowman, WorldChunk.CreationType _snowman) {
      int _snowmanxx = (_snowman.getX() >> 4) - this.chunkXOffset;
      int _snowmanxxx = (_snowman.getZ() >> 4) - this.chunkZOffset;
      return this.chunks[_snowmanxx][_snowmanxxx].getBlockEntity(_snowman, _snowman);
   }

   @Override
   public int getColor(BlockPos pos, ColorResolver colorResolver) {
      return this.world.getColor(pos, colorResolver);
   }
}
