package net.minecraft.block;

import java.util.Random;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class NetherrackBlock extends Block implements Fertilizable {
   public NetherrackBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      if (!world.getBlockState(pos.up()).isTranslucent(world, pos)) {
         return false;
      } else {
         for (BlockPos _snowman : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            if (world.getBlockState(_snowman).isIn(BlockTags.NYLIUM)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      boolean _snowman = false;
      boolean _snowmanx = false;

      for (BlockPos _snowmanxx : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
         BlockState _snowmanxxx = world.getBlockState(_snowmanxx);
         if (_snowmanxxx.isOf(Blocks.WARPED_NYLIUM)) {
            _snowmanx = true;
         }

         if (_snowmanxxx.isOf(Blocks.CRIMSON_NYLIUM)) {
            _snowman = true;
         }

         if (_snowmanx && _snowman) {
            break;
         }
      }

      if (_snowmanx && _snowman) {
         world.setBlockState(pos, random.nextBoolean() ? Blocks.WARPED_NYLIUM.getDefaultState() : Blocks.CRIMSON_NYLIUM.getDefaultState(), 3);
      } else if (_snowmanx) {
         world.setBlockState(pos, Blocks.WARPED_NYLIUM.getDefaultState(), 3);
      } else if (_snowman) {
         world.setBlockState(pos, Blocks.CRIMSON_NYLIUM.getDefaultState(), 3);
      }
   }
}
