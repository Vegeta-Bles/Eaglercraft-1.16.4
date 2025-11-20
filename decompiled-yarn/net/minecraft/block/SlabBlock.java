package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class SlabBlock extends Block implements Waterloggable {
   public static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

   public SlabBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public boolean hasSidedTransparency(BlockState state) {
      return state.get(TYPE) != SlabType.DOUBLE;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(TYPE, WATERLOGGED);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      SlabType _snowman = state.get(TYPE);
      switch (_snowman) {
         case DOUBLE:
            return VoxelShapes.fullCube();
         case TOP:
            return TOP_SHAPE;
         default:
            return BOTTOM_SHAPE;
      }
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockPos _snowman = ctx.getBlockPos();
      BlockState _snowmanx = ctx.getWorld().getBlockState(_snowman);
      if (_snowmanx.isOf(this)) {
         return _snowmanx.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, Boolean.valueOf(false));
      } else {
         FluidState _snowmanxx = ctx.getWorld().getFluidState(_snowman);
         BlockState _snowmanxxx = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, Boolean.valueOf(_snowmanxx.getFluid() == Fluids.WATER));
         Direction _snowmanxxxx = ctx.getSide();
         return _snowmanxxxx != Direction.DOWN && (_snowmanxxxx == Direction.UP || !(ctx.getHitPos().y - (double)_snowman.getY() > 0.5)) ? _snowmanxxx : _snowmanxxx.with(TYPE, SlabType.TOP);
      }
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      ItemStack _snowman = context.getStack();
      SlabType _snowmanx = state.get(TYPE);
      if (_snowmanx == SlabType.DOUBLE || _snowman.getItem() != this.asItem()) {
         return false;
      } else if (context.canReplaceExisting()) {
         boolean _snowmanxx = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5;
         Direction _snowmanxxx = context.getSide();
         return _snowmanx == SlabType.BOTTOM
            ? _snowmanxxx == Direction.UP || _snowmanxx && _snowmanxxx.getAxis().isHorizontal()
            : _snowmanxxx == Direction.DOWN || !_snowmanxx && _snowmanxxx.getAxis().isHorizontal();
      } else {
         return true;
      }
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
      return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState) : false;
   }

   @Override
   public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
      return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.canFillWithFluid(world, pos, state, fluid) : false;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      switch (type) {
         case LAND:
            return false;
         case WATER:
            return world.getFluidState(pos).isIn(FluidTags.WATER);
         case AIR:
            return false;
         default:
            return false;
      }
   }
}
