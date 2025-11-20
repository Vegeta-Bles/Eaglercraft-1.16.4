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

   public MushroomPlantBlock(AbstractBlock.Settings arg) {
      super(arg);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (random.nextInt(25) == 0) {
         int i = 5;
         int j = 4;

         for (BlockPos lv : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (world.getBlockState(lv).isOf(this)) {
               if (--i <= 0) {
                  return;
               }
            }
         }

         BlockPos lv2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

         for (int k = 0; k < 4; k++) {
            if (world.isAir(lv2) && state.canPlaceAt(world, lv2)) {
               pos = lv2;
            }

            lv2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
         }

         if (world.isAir(lv2) && state.canPlaceAt(world, lv2)) {
            world.setBlockState(lv2, state, 2);
         }
      }
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      return floor.isOpaqueFullCube(world, pos);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos lv = pos.down();
      BlockState lv2 = world.getBlockState(lv);
      return lv2.isIn(BlockTags.MUSHROOM_GROW_BLOCK) ? true : world.getBaseLightLevel(pos, 0) < 13 && this.canPlantOnTop(lv2, world, lv);
   }

   public boolean trySpawningBigMushroom(ServerWorld arg, BlockPos pos, BlockState state, Random random) {
      arg.removeBlock(pos, false);
      ConfiguredFeature<?, ?> lv;
      if (this == Blocks.BROWN_MUSHROOM) {
         lv = ConfiguredFeatures.HUGE_BROWN_MUSHROOM;
      } else {
         if (this != Blocks.RED_MUSHROOM) {
            arg.setBlockState(pos, state, 3);
            return false;
         }

         lv = ConfiguredFeatures.HUGE_RED_MUSHROOM;
      }

      if (lv.generate(arg, arg.getChunkManager().getChunkGenerator(), random, pos)) {
         return true;
      } else {
         arg.setBlockState(pos, state, 3);
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
