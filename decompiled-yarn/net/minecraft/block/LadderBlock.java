package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class LadderBlock extends Block implements Waterloggable {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

   protected LadderBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      switch ((Direction)state.get(FACING)) {
         case NORTH:
            return NORTH_SHAPE;
         case SOUTH:
            return SOUTH_SHAPE;
         case WEST:
            return WEST_SHAPE;
         case EAST:
         default:
            return EAST_SHAPE;
      }
   }

   private boolean canPlaceOn(BlockView world, BlockPos pos, Direction side) {
      BlockState _snowman = world.getBlockState(pos);
      return _snowman.isSideSolidFullSquare(world, pos, side);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      Direction _snowman = state.get(FACING);
      return this.canPlaceOn(world, pos.offset(_snowman.getOpposite()), _snowman);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
         return Blocks.AIR.getDefaultState();
      } else {
         if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
         }

         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      }
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      if (!ctx.canReplaceExisting()) {
         BlockState _snowman = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getSide().getOpposite()));
         if (_snowman.isOf(this) && _snowman.get(FACING) == ctx.getSide()) {
            return null;
         }
      }

      BlockState _snowman = this.getDefaultState();
      WorldView _snowmanx = ctx.getWorld();
      BlockPos _snowmanxx = ctx.getBlockPos();
      FluidState _snowmanxxx = ctx.getWorld().getFluidState(ctx.getBlockPos());

      for (Direction _snowmanxxxx : ctx.getPlacementDirections()) {
         if (_snowmanxxxx.getAxis().isHorizontal()) {
            _snowman = _snowman.with(FACING, _snowmanxxxx.getOpposite());
            if (_snowman.canPlaceAt(_snowmanx, _snowmanxx)) {
               return _snowman.with(WATERLOGGED, Boolean.valueOf(_snowmanxxx.getFluid() == Fluids.WATER));
            }
         }
      }

      return null;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, WATERLOGGED);
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }
}
