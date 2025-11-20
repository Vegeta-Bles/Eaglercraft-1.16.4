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

   protected SugarCaneBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
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
         int _snowman = 1;

         while (world.getBlockState(pos.down(_snowman)).isOf(this)) {
            _snowman++;
         }

         if (_snowman < 3) {
            int _snowmanx = state.get(AGE);
            if (_snowmanx == 15) {
               world.setBlockState(pos.up(), this.getDefaultState());
               world.setBlockState(pos, state.with(AGE, Integer.valueOf(0)), 4);
            } else {
               world.setBlockState(pos, state.with(AGE, Integer.valueOf(_snowmanx + 1)), 4);
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
      BlockState _snowman = world.getBlockState(pos.down());
      if (_snowman.getBlock() == this) {
         return true;
      } else {
         if (_snowman.isOf(Blocks.GRASS_BLOCK)
            || _snowman.isOf(Blocks.DIRT)
            || _snowman.isOf(Blocks.COARSE_DIRT)
            || _snowman.isOf(Blocks.PODZOL)
            || _snowman.isOf(Blocks.SAND)
            || _snowman.isOf(Blocks.RED_SAND)) {
            BlockPos _snowmanx = pos.down();

            for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
               BlockState _snowmanxxx = world.getBlockState(_snowmanx.offset(_snowmanxx));
               FluidState _snowmanxxxx = world.getFluidState(_snowmanx.offset(_snowmanxx));
               if (_snowmanxxxx.isIn(FluidTags.WATER) || _snowmanxxx.isOf(Blocks.FROSTED_ICE)) {
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
