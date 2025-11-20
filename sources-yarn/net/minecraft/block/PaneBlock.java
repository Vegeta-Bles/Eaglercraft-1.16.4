package net.minecraft.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   protected PaneBlock(AbstractBlock.Settings arg) {
      super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, arg);
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
      BlockView lv = ctx.getWorld();
      BlockPos lv2 = ctx.getBlockPos();
      FluidState lv3 = ctx.getWorld().getFluidState(ctx.getBlockPos());
      BlockPos lv4 = lv2.north();
      BlockPos lv5 = lv2.south();
      BlockPos lv6 = lv2.west();
      BlockPos lv7 = lv2.east();
      BlockState lv8 = lv.getBlockState(lv4);
      BlockState lv9 = lv.getBlockState(lv5);
      BlockState lv10 = lv.getBlockState(lv6);
      BlockState lv11 = lv.getBlockState(lv7);
      return this.getDefaultState()
         .with(NORTH, Boolean.valueOf(this.connectsTo(lv8, lv8.isSideSolidFullSquare(lv, lv4, Direction.SOUTH))))
         .with(SOUTH, Boolean.valueOf(this.connectsTo(lv9, lv9.isSideSolidFullSquare(lv, lv5, Direction.NORTH))))
         .with(WEST, Boolean.valueOf(this.connectsTo(lv10, lv10.isSideSolidFullSquare(lv, lv6, Direction.EAST))))
         .with(EAST, Boolean.valueOf(this.connectsTo(lv11, lv11.isSideSolidFullSquare(lv, lv7, Direction.WEST))))
         .with(WATERLOGGED, Boolean.valueOf(lv3.getFluid() == Fluids.WATER));
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

   @Environment(EnvType.CLIENT)
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

   public final boolean connectsTo(BlockState state, boolean bl) {
      Block lv = state.getBlock();
      return !cannotConnect(lv) && bl || lv instanceof PaneBlock || lv.isIn(BlockTags.WALLS);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
   }
}
