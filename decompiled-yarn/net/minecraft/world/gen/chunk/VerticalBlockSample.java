package net.minecraft.world.gen.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public final class VerticalBlockSample implements BlockView {
   private final BlockState[] states;

   public VerticalBlockSample(BlockState[] states) {
      this.states = states;
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos pos) {
      return null;
   }

   @Override
   public BlockState getBlockState(BlockPos pos) {
      int _snowman = pos.getY();
      return _snowman >= 0 && _snowman < this.states.length ? this.states[_snowman] : Blocks.AIR.getDefaultState();
   }

   @Override
   public FluidState getFluidState(BlockPos pos) {
      return this.getBlockState(pos).getFluidState();
   }
}
