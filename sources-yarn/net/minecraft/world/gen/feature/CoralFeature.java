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
   public CoralFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DefaultFeatureConfig arg4) {
      BlockState lv = BlockTags.CORAL_BLOCKS.getRandom(random).getDefaultState();
      return this.spawnCoral(arg, random, arg3, lv);
   }

   protected abstract boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state);

   protected boolean spawnCoralPiece(WorldAccess world, Random random, BlockPos pos, BlockState state) {
      BlockPos lv = pos.up();
      BlockState lv2 = world.getBlockState(pos);
      if ((lv2.isOf(Blocks.WATER) || lv2.isIn(BlockTags.CORALS)) && world.getBlockState(lv).isOf(Blocks.WATER)) {
         world.setBlockState(pos, state, 3);
         if (random.nextFloat() < 0.25F) {
            world.setBlockState(lv, BlockTags.CORALS.getRandom(random).getDefaultState(), 2);
         } else if (random.nextFloat() < 0.05F) {
            world.setBlockState(lv, Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(random.nextInt(4) + 1)), 2);
         }

         for (Direction lv3 : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() < 0.2F) {
               BlockPos lv4 = pos.offset(lv3);
               if (world.getBlockState(lv4).isOf(Blocks.WATER)) {
                  BlockState lv5 = BlockTags.WALL_CORALS.getRandom(random).getDefaultState().with(DeadCoralWallFanBlock.FACING, lv3);
                  world.setBlockState(lv4, lv5, 2);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
