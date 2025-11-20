package net.minecraft.block;

import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FrostedIceBlock extends IceBlock {
   public static final IntProperty AGE = Properties.AGE_3;

   public FrostedIceBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(AGE, Integer.valueOf(0)));
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      this.scheduledTick(state, world, pos, random);
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if ((random.nextInt(3) == 0 || this.canMelt(world, pos, 4))
         && world.getLightLevel(pos) > 11 - state.get(AGE) - state.getOpacity(world, pos)
         && this.increaseAge(state, world, pos)) {
         BlockPos.Mutable _snowman = new BlockPos.Mutable();

         for (Direction _snowmanx : Direction.values()) {
            _snowman.set(pos, _snowmanx);
            BlockState _snowmanxx = world.getBlockState(_snowman);
            if (_snowmanxx.isOf(this) && !this.increaseAge(_snowmanxx, world, _snowman)) {
               world.getBlockTickScheduler().schedule(_snowman, this, MathHelper.nextInt(random, 20, 40));
            }
         }
      } else {
         world.getBlockTickScheduler().schedule(pos, this, MathHelper.nextInt(random, 20, 40));
      }
   }

   private boolean increaseAge(BlockState state, World world, BlockPos pos) {
      int _snowman = state.get(AGE);
      if (_snowman < 3) {
         world.setBlockState(pos, state.with(AGE, Integer.valueOf(_snowman + 1)), 2);
         return false;
      } else {
         this.melt(state, world, pos);
         return true;
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (block == this && this.canMelt(world, pos, 2)) {
         this.melt(state, world, pos);
      }

      super.neighborUpdate(state, world, pos, block, fromPos, notify);
   }

   private boolean canMelt(BlockView world, BlockPos pos, int maxNeighbors) {
      int _snowman = 0;
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (Direction _snowmanxx : Direction.values()) {
         _snowmanx.set(pos, _snowmanxx);
         if (world.getBlockState(_snowmanx).isOf(this)) {
            if (++_snowman >= maxNeighbors) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }

   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return ItemStack.EMPTY;
   }
}
