package net.minecraft.block;

import java.util.Random;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class SugarCaneBlock extends Block {
   public static final IntProperty AGE = Properties.AGE_15;
   protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   protected SugarCaneBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(this.stateManager.getDefaultState().with(AGE, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.breakBlock(pos, true);
      }
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (world.isAir(pos.up())) {
         int i = 1;

         while (world.getBlockState(pos.down(i)).isOf(this)) {
            i++;
         }

         if (i < 3) {
            int j = state.get(AGE);
            if (j == 15) {
               world.setBlockState(pos.up(), this.getDefaultState());
               world.setBlockState(pos, state.with(AGE, Integer.valueOf(0)), 4);
            } else {
               world.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
            }
         }
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (!state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockState lv = world.getBlockState(pos.down());
      if (lv.getBlock() == this) {
         return true;
      } else {
         if (lv.isOf(Blocks.GRASS_BLOCK)
            || lv.isOf(Blocks.DIRT)
            || lv.isOf(Blocks.COARSE_DIRT)
            || lv.isOf(Blocks.PODZOL)
            || lv.isOf(Blocks.SAND)
            || lv.isOf(Blocks.RED_SAND)) {
            BlockPos lv2 = pos.down();

            for (Direction lv3 : Direction.Type.HORIZONTAL) {
               BlockState lv4 = world.getBlockState(lv2.offset(lv3));
               FluidState lv5 = world.getFluidState(lv2.offset(lv3));
               if (lv5.isIn(FluidTags.WATER) || lv4.isOf(Blocks.FROSTED_ICE)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }
}
