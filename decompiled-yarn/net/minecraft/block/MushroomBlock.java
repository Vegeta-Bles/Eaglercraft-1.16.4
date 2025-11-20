package net.minecraft.block;

import java.util.Map;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class MushroomBlock extends Block {
   public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
   public static final BooleanProperty EAST = ConnectingBlock.EAST;
   public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
   public static final BooleanProperty WEST = ConnectingBlock.WEST;
   public static final BooleanProperty UP = ConnectingBlock.UP;
   public static final BooleanProperty DOWN = ConnectingBlock.DOWN;
   private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES;

   public MushroomBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(NORTH, Boolean.valueOf(true))
            .with(EAST, Boolean.valueOf(true))
            .with(SOUTH, Boolean.valueOf(true))
            .with(WEST, Boolean.valueOf(true))
            .with(UP, Boolean.valueOf(true))
            .with(DOWN, Boolean.valueOf(true))
      );
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockView _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      return this.getDefaultState()
         .with(DOWN, Boolean.valueOf(this != _snowman.getBlockState(_snowmanx.down()).getBlock()))
         .with(UP, Boolean.valueOf(this != _snowman.getBlockState(_snowmanx.up()).getBlock()))
         .with(NORTH, Boolean.valueOf(this != _snowman.getBlockState(_snowmanx.north()).getBlock()))
         .with(EAST, Boolean.valueOf(this != _snowman.getBlockState(_snowmanx.east()).getBlock()))
         .with(SOUTH, Boolean.valueOf(this != _snowman.getBlockState(_snowmanx.south()).getBlock()))
         .with(WEST, Boolean.valueOf(this != _snowman.getBlockState(_snowmanx.west()).getBlock()));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return newState.isOf(this)
         ? state.with(FACING_PROPERTIES.get(direction), Boolean.valueOf(false))
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING_PROPERTIES.get(rotation.rotate(Direction.NORTH)), state.get(NORTH))
         .with(FACING_PROPERTIES.get(rotation.rotate(Direction.SOUTH)), state.get(SOUTH))
         .with(FACING_PROPERTIES.get(rotation.rotate(Direction.EAST)), state.get(EAST))
         .with(FACING_PROPERTIES.get(rotation.rotate(Direction.WEST)), state.get(WEST))
         .with(FACING_PROPERTIES.get(rotation.rotate(Direction.UP)), state.get(UP))
         .with(FACING_PROPERTIES.get(rotation.rotate(Direction.DOWN)), state.get(DOWN));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.with(FACING_PROPERTIES.get(mirror.apply(Direction.NORTH)), state.get(NORTH))
         .with(FACING_PROPERTIES.get(mirror.apply(Direction.SOUTH)), state.get(SOUTH))
         .with(FACING_PROPERTIES.get(mirror.apply(Direction.EAST)), state.get(EAST))
         .with(FACING_PROPERTIES.get(mirror.apply(Direction.WEST)), state.get(WEST))
         .with(FACING_PROPERTIES.get(mirror.apply(Direction.UP)), state.get(UP))
         .with(FACING_PROPERTIES.get(mirror.apply(Direction.DOWN)), state.get(DOWN));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
   }
}
