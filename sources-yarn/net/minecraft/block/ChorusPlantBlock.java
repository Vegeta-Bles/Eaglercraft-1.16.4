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
   protected ChorusPlantBlock(AbstractBlock.Settings arg) {
      super(0.3125F, arg);
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
      Block lv = world.getBlockState(pos.down()).getBlock();
      Block lv2 = world.getBlockState(pos.up()).getBlock();
      Block lv3 = world.getBlockState(pos.north()).getBlock();
      Block lv4 = world.getBlockState(pos.east()).getBlock();
      Block lv5 = world.getBlockState(pos.south()).getBlock();
      Block lv6 = world.getBlockState(pos.west()).getBlock();
      return this.getDefaultState()
         .with(DOWN, Boolean.valueOf(lv == this || lv == Blocks.CHORUS_FLOWER || lv == Blocks.END_STONE))
         .with(UP, Boolean.valueOf(lv2 == this || lv2 == Blocks.CHORUS_FLOWER))
         .with(NORTH, Boolean.valueOf(lv3 == this || lv3 == Blocks.CHORUS_FLOWER))
         .with(EAST, Boolean.valueOf(lv4 == this || lv4 == Blocks.CHORUS_FLOWER))
         .with(SOUTH, Boolean.valueOf(lv5 == this || lv5 == Blocks.CHORUS_FLOWER))
         .with(WEST, Boolean.valueOf(lv6 == this || lv6 == Blocks.CHORUS_FLOWER));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (!state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         boolean bl = newState.getBlock() == this || newState.isOf(Blocks.CHORUS_FLOWER) || direction == Direction.DOWN && newState.isOf(Blocks.END_STONE);
         return state.with(FACING_PROPERTIES.get(direction), Boolean.valueOf(bl));
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
      BlockState lv = world.getBlockState(pos.down());
      boolean bl = !world.getBlockState(pos.up()).isAir() && !lv.isAir();

      for (Direction lv2 : Direction.Type.HORIZONTAL) {
         BlockPos lv3 = pos.offset(lv2);
         Block lv4 = world.getBlockState(lv3).getBlock();
         if (lv4 == this) {
            if (bl) {
               return false;
            }

            Block lv5 = world.getBlockState(lv3.down()).getBlock();
            if (lv5 == this || lv5 == Blocks.END_STONE) {
               return true;
            }
         }
      }

      Block lv6 = lv.getBlock();
      return lv6 == this || lv6 == Blocks.END_STONE;
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
