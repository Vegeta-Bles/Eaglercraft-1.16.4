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

   public SlabBlock(AbstractBlock.Settings arg) {
      super(arg);
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
      SlabType lv = state.get(TYPE);
      switch (lv) {
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
      BlockPos lv = ctx.getBlockPos();
      BlockState lv2 = ctx.getWorld().getBlockState(lv);
      if (lv2.isOf(this)) {
         return lv2.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, Boolean.valueOf(false));
      } else {
         FluidState lv3 = ctx.getWorld().getFluidState(lv);
         BlockState lv4 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, Boolean.valueOf(lv3.getFluid() == Fluids.WATER));
         Direction lv5 = ctx.getSide();
         return lv5 != Direction.DOWN && (lv5 == Direction.UP || !(ctx.getHitPos().y - (double)lv.getY() > 0.5)) ? lv4 : lv4.with(TYPE, SlabType.TOP);
      }
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      ItemStack lv = context.getStack();
      SlabType lv2 = state.get(TYPE);
      if (lv2 == SlabType.DOUBLE || lv.getItem() != this.asItem()) {
         return false;
      } else if (context.canReplaceExisting()) {
         boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5;
         Direction lv3 = context.getSide();
         return lv2 == SlabType.BOTTOM
            ? lv3 == Direction.UP || bl && lv3.getAxis().isHorizontal()
            : lv3 == Direction.DOWN || !bl && lv3.getAxis().isHorizontal();
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
