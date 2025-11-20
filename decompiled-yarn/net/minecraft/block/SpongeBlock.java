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
   protected SpongeBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
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
      Queue<Pair<BlockPos, Integer>> _snowman = Lists.newLinkedList();
      _snowman.add(new Pair<>(pos, 0));
      int _snowmanx = 0;

      while (!_snowman.isEmpty()) {
         Pair<BlockPos, Integer> _snowmanxx = _snowman.poll();
         BlockPos _snowmanxxx = _snowmanxx.getLeft();
         int _snowmanxxxx = _snowmanxx.getRight();

         for (Direction _snowmanxxxxx : Direction.values()) {
            BlockPos _snowmanxxxxxx = _snowmanxxx.offset(_snowmanxxxxx);
            BlockState _snowmanxxxxxxx = world.getBlockState(_snowmanxxxxxx);
            FluidState _snowmanxxxxxxxx = world.getFluidState(_snowmanxxxxxx);
            Material _snowmanxxxxxxxxx = _snowmanxxxxxxx.getMaterial();
            if (_snowmanxxxxxxxx.isIn(FluidTags.WATER)) {
               if (_snowmanxxxxxxx.getBlock() instanceof FluidDrainable
                  && ((FluidDrainable)_snowmanxxxxxxx.getBlock()).tryDrainFluid(world, _snowmanxxxxxx, _snowmanxxxxxxx) != Fluids.EMPTY) {
                  _snowmanx++;
                  if (_snowmanxxxx < 6) {
                     _snowman.add(new Pair<>(_snowmanxxxxxx, _snowmanxxxx + 1));
                  }
               } else if (_snowmanxxxxxxx.getBlock() instanceof FluidBlock) {
                  world.setBlockState(_snowmanxxxxxx, Blocks.AIR.getDefaultState(), 3);
                  _snowmanx++;
                  if (_snowmanxxxx < 6) {
                     _snowman.add(new Pair<>(_snowmanxxxxxx, _snowmanxxxx + 1));
                  }
               } else if (_snowmanxxxxxxxxx == Material.UNDERWATER_PLANT || _snowmanxxxxxxxxx == Material.REPLACEABLE_UNDERWATER_PLANT) {
                  BlockEntity _snowmanxxxxxxxxxx = _snowmanxxxxxxx.getBlock().hasBlockEntity() ? world.getBlockEntity(_snowmanxxxxxx) : null;
                  dropStacks(_snowmanxxxxxxx, world, _snowmanxxxxxx, _snowmanxxxxxxxxxx);
                  world.setBlockState(_snowmanxxxxxx, Blocks.AIR.getDefaultState(), 3);
                  _snowmanx++;
                  if (_snowmanxxxx < 6) {
                     _snowman.add(new Pair<>(_snowmanxxxxxx, _snowmanxxxx + 1));
                  }
               }
            }
         }

         if (_snowmanx > 64) {
            break;
         }
      }

      return _snowmanx > 0;
   }
}
