package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.Queue;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SpongeBlock extends Block {
   protected SpongeBlock(AbstractBlock.Settings arg) {
      super(arg);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!oldState.isOf(state.getBlock())) {
         this.update(world, pos);
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      this.update(world, pos);
      super.neighborUpdate(state, world, pos, block, fromPos, notify);
   }

   protected void update(World world, BlockPos pos) {
      if (this.absorbWater(world, pos)) {
         world.setBlockState(pos, Blocks.WET_SPONGE.getDefaultState(), 2);
         world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.WATER.getDefaultState()));
      }
   }

   private boolean absorbWater(World world, BlockPos pos) {
      Queue<Pair<BlockPos, Integer>> queue = Lists.newLinkedList();
      queue.add(new Pair<>(pos, 0));
      int i = 0;

      while (!queue.isEmpty()) {
         Pair<BlockPos, Integer> lv = queue.poll();
         BlockPos lv2 = lv.getLeft();
         int j = lv.getRight();

         for (Direction lv3 : Direction.values()) {
            BlockPos lv4 = lv2.offset(lv3);
            BlockState lv5 = world.getBlockState(lv4);
            FluidState lv6 = world.getFluidState(lv4);
            Material lv7 = lv5.getMaterial();
            if (lv6.isIn(FluidTags.WATER)) {
               if (lv5.getBlock() instanceof FluidDrainable && ((FluidDrainable)lv5.getBlock()).tryDrainFluid(world, lv4, lv5) != Fluids.EMPTY) {
                  i++;
                  if (j < 6) {
                     queue.add(new Pair<>(lv4, j + 1));
                  }
               } else if (lv5.getBlock() instanceof FluidBlock) {
                  world.setBlockState(lv4, Blocks.AIR.getDefaultState(), 3);
                  i++;
                  if (j < 6) {
                     queue.add(new Pair<>(lv4, j + 1));
                  }
               } else if (lv7 == Material.UNDERWATER_PLANT || lv7 == Material.REPLACEABLE_UNDERWATER_PLANT) {
                  BlockEntity lv8 = lv5.getBlock().hasBlockEntity() ? world.getBlockEntity(lv4) : null;
                  dropStacks(lv5, world, lv4, lv8);
                  world.setBlockState(lv4, Blocks.AIR.getDefaultState(), 3);
                  i++;
                  if (j < 6) {
                     queue.add(new Pair<>(lv4, j + 1));
                  }
               }
            }
         }

         if (i > 64) {
            break;
         }
      }

      return i > 0;
   }
}
