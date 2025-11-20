package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ChorusPlantBlock extends ConnectingBlock {
   protected ChorusPlantBlock(AbstractBlock.Settings _snowman) {
      super(0.3125F, _snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(NORTH, Boolean.valueOf(false))
            .with(EAST, Boolean.valueOf(false))
            .with(SOUTH, Boolean.valueOf(false))
            .with(WEST, Boolean.valueOf(false))
            .with(UP, Boolean.valueOf(false))
            .with(DOWN, Boolean.valueOf(false))
      );
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.withConnectionProperties(ctx.getWorld(), ctx.getBlockPos());
   }

   public BlockState withConnectionProperties(BlockView world, BlockPos pos) {
      Block _snowman = world.getBlockState(pos.down()).getBlock();
      Block _snowmanx = world.getBlockState(pos.up()).getBlock();
      Block _snowmanxx = world.getBlockState(pos.north()).getBlock();
      Block _snowmanxxx = world.getBlockState(pos.east()).getBlock();
      Block _snowmanxxxx = world.getBlockState(pos.south()).getBlock();
      Block _snowmanxxxxx = world.getBlockState(pos.west()).getBlock();
      return this.getDefaultState()
         .with(DOWN, Boolean.valueOf(_snowman == this || _snowman == Blocks.CHORUS_FLOWER || _snowman == Blocks.END_STONE))
         .with(UP, Boolean.valueOf(_snowmanx == this || _snowmanx == Blocks.CHORUS_FLOWER))
         .with(NORTH, Boolean.valueOf(_snowmanxx == this || _snowmanxx == Blocks.CHORUS_FLOWER))
         .with(EAST, Boolean.valueOf(_snowmanxxx == this || _snowmanxxx == Blocks.CHORUS_FLOWER))
         .with(SOUTH, Boolean.valueOf(_snowmanxxxx == this || _snowmanxxxx == Blocks.CHORUS_FLOWER))
         .with(WEST, Boolean.valueOf(_snowmanxxxxx == this || _snowmanxxxxx == Blocks.CHORUS_FLOWER));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (!state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         boolean _snowman = newState.getBlock() == this || newState.isOf(Blocks.CHORUS_FLOWER) || direction == Direction.DOWN && newState.isOf(Blocks.END_STONE);
         return state.with(FACING_PROPERTIES.get(direction), Boolean.valueOf(_snowman));
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.breakBlock(pos, true);
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos.down());
      boolean _snowmanx = !world.getBlockState(pos.up()).isAir() && !_snowman.isAir();

      for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
         BlockPos _snowmanxxx = pos.offset(_snowmanxx);
         Block _snowmanxxxx = world.getBlockState(_snowmanxxx).getBlock();
         if (_snowmanxxxx == this) {
            if (_snowmanx) {
               return false;
            }

            Block _snowmanxxxxx = world.getBlockState(_snowmanxxx.down()).getBlock();
            if (_snowmanxxxxx == this || _snowmanxxxxx == Blocks.END_STONE) {
               return true;
            }
         }
      }

      Block _snowmanxxx = _snowman.getBlock();
      return _snowmanxxx == this || _snowmanxxx == Blocks.END_STONE;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
