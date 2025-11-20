package net.minecraft.block;

import java.util.Random;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

public class MushroomPlantBlock extends PlantBlock implements Fertilizable {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

   public MushroomPlantBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (random.nextInt(25) == 0) {
         int _snowman = 5;
         int _snowmanx = 4;

         for (BlockPos _snowmanxx : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (world.getBlockState(_snowmanxx).isOf(this)) {
               if (--_snowman <= 0) {
                  return;
               }
            }
         }

         BlockPos _snowmanxxx = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

         for (int _snowmanxxxx = 0; _snowmanxxxx < 4; _snowmanxxxx++) {
            if (world.isAir(_snowmanxxx) && state.canPlaceAt(world, _snowmanxxx)) {
               pos = _snowmanxxx;
            }

            _snowmanxxx = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
         }

         if (world.isAir(_snowmanxxx) && state.canPlaceAt(world, _snowmanxxx)) {
            world.setBlockState(_snowmanxxx, state, 2);
         }
      }
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      return floor.isOpaqueFullCube(world, pos);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      return _snowmanx.isIn(BlockTags.MUSHROOM_GROW_BLOCK) ? true : world.getBaseLightLevel(pos, 0) < 13 && this.canPlantOnTop(_snowmanx, world, _snowman);
   }

   public boolean trySpawningBigMushroom(ServerWorld _snowman, BlockPos pos, BlockState state, Random random) {
      _snowman.removeBlock(pos, false);
      ConfiguredFeature<?, ?> _snowmanx;
      if (this == Blocks.BROWN_MUSHROOM) {
         _snowmanx = ConfiguredFeatures.HUGE_BROWN_MUSHROOM;
      } else {
         if (this != Blocks.RED_MUSHROOM) {
            _snowman.setBlockState(pos, state, 3);
            return false;
         }

         _snowmanx = ConfiguredFeatures.HUGE_RED_MUSHROOM;
      }

      if (_snowmanx.generate(_snowman, _snowman.getChunkManager().getChunkGenerator(), random, pos)) {
         return true;
      } else {
         _snowman.setBlockState(pos, state, 3);
         return false;
      }
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return (double)random.nextFloat() < 0.4;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      this.trySpawningBigMushroom(world, pos, state, random);
   }
}
