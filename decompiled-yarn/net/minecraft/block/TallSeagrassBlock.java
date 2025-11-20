package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class TallSeagrassBlock extends TallPlantBlock implements FluidFillable {
   public static final EnumProperty<DoubleBlockHalf> HALF = TallPlantBlock.HALF;
   protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   public TallSeagrassBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      return floor.isSideSolidFullSquare(world, pos, Direction.UP) && !floor.isOf(Blocks.MAGMA_BLOCK);
   }

   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return new ItemStack(Blocks.SEAGRASS);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = super.getPlacementState(ctx);
      if (_snowman != null) {
         FluidState _snowmanx = ctx.getWorld().getFluidState(ctx.getBlockPos().up());
         if (_snowmanx.isIn(FluidTags.WATER) && _snowmanx.getLevel() == 8) {
            return _snowman;
         }
      }

      return null;
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      if (state.get(HALF) == DoubleBlockHalf.UPPER) {
         BlockState _snowman = world.getBlockState(pos.down());
         return _snowman.isOf(this) && _snowman.get(HALF) == DoubleBlockHalf.LOWER;
      } else {
         FluidState _snowman = world.getFluidState(pos);
         return super.canPlaceAt(state, world, pos) && _snowman.isIn(FluidTags.WATER) && _snowman.getLevel() == 8;
      }
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return Fluids.WATER.getStill(false);
   }

   @Override
   public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
      return false;
   }

   @Override
   public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
      return false;
   }
}
