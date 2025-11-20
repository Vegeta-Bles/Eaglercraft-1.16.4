package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class CoralFeature extends Feature<DefaultFeatureConfig> {
   public CoralFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      BlockState _snowmanxxxxx = BlockTags.CORAL_BLOCKS.getRandom(_snowman).getDefaultState();
      return this.spawnCoral(_snowman, _snowman, _snowman, _snowmanxxxxx);
   }

   protected abstract boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state);

   protected boolean spawnCoralPiece(WorldAccess world, Random random, BlockPos pos, BlockState state) {
      BlockPos _snowman = pos.up();
      BlockState _snowmanx = world.getBlockState(pos);
      if ((_snowmanx.isOf(Blocks.WATER) || _snowmanx.isIn(BlockTags.CORALS)) && world.getBlockState(_snowman).isOf(Blocks.WATER)) {
         world.setBlockState(pos, state, 3);
         if (random.nextFloat() < 0.25F) {
            world.setBlockState(_snowman, BlockTags.CORALS.getRandom(random).getDefaultState(), 2);
         } else if (random.nextFloat() < 0.05F) {
            world.setBlockState(_snowman, Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(random.nextInt(4) + 1)), 2);
         }

         for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() < 0.2F) {
               BlockPos _snowmanxxx = pos.offset(_snowmanxx);
               if (world.getBlockState(_snowmanxxx).isOf(Blocks.WATER)) {
                  BlockState _snowmanxxxx = BlockTags.WALL_CORALS.getRandom(random).getDefaultState().with(DeadCoralWallFanBlock.FACING, _snowmanxx);
                  world.setBlockState(_snowmanxxx, _snowmanxxxx, 2);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
