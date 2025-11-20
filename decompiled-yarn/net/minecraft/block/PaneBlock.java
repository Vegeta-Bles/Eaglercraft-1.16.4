package net.minecraft.block;

import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class PaneBlock extends HorizontalConnectingBlock {
   protected PaneBlock(AbstractBlock.Settings _snowman) {
      super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, _snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(NORTH, Boolean.valueOf(false))
            .with(EAST, Boolean.valueOf(false))
            .with(SOUTH, Boolean.valueOf(false))
            .with(WEST, Boolean.valueOf(false))
            .with(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockView _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      FluidState _snowmanxx = ctx.getWorld().getFluidState(ctx.getBlockPos());
      BlockPos _snowmanxxx = _snowmanx.north();
      BlockPos _snowmanxxxx = _snowmanx.south();
      BlockPos _snowmanxxxxx = _snowmanx.west();
      BlockPos _snowmanxxxxxx = _snowmanx.east();
      BlockState _snowmanxxxxxxx = _snowman.getBlockState(_snowmanxxx);
      BlockState _snowmanxxxxxxxx = _snowman.getBlockState(_snowmanxxxx);
      BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxx);
      BlockState _snowmanxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxx);
      return this.getDefaultState()
         .with(NORTH, Boolean.valueOf(this.connectsTo(_snowmanxxxxxxx, _snowmanxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxx, Direction.SOUTH))))
         .with(SOUTH, Boolean.valueOf(this.connectsTo(_snowmanxxxxxxxx, _snowmanxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxx, Direction.NORTH))))
         .with(WEST, Boolean.valueOf(this.connectsTo(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxxx, Direction.EAST))))
         .with(EAST, Boolean.valueOf(this.connectsTo(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxxxx, Direction.WEST))))
         .with(WATERLOGGED, Boolean.valueOf(_snowmanxx.getFluid() == Fluids.WATER));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return direction.getAxis().isHorizontal()
         ? state.with(
            FACING_PROPERTIES.get(direction),
            Boolean.valueOf(this.connectsTo(newState, newState.isSideSolidFullSquare(world, posFrom, direction.getOpposite())))
         )
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return VoxelShapes.empty();
   }

   @Override
   public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
      if (stateFrom.isOf(this)) {
         if (!direction.getAxis().isHorizontal()) {
            return true;
         }

         if (state.get(FACING_PROPERTIES.get(direction)) && stateFrom.get(FACING_PROPERTIES.get(direction.getOpposite()))) {
            return true;
         }
      }

      return super.isSideInvisible(state, stateFrom, direction);
   }

   public final boolean connectsTo(BlockState state, boolean _snowman) {
      Block _snowmanx = state.getBlock();
      return !cannotConnect(_snowmanx) && _snowman || _snowmanx instanceof PaneBlock || _snowmanx.isIn(BlockTags.WALLS);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
   }
}
