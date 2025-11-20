package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SeagrassBlock extends PlantBlock implements Fertilizable, FluidFillable {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

   protected SeagrassBlock(AbstractBlock.Settings _snowman) {
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

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      FluidState _snowman = ctx.getWorld().getFluidState(ctx.getBlockPos());
      return _snowman.isIn(FluidTags.WATER) && _snowman.getLevel() == 8 ? super.getPlacementState(ctx) : null;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      BlockState _snowman = super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      if (!_snowman.isAir()) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return _snowman;
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return Fluids.WATER.getStill(false);
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      BlockState _snowman = Blocks.TALL_SEAGRASS.getDefaultState();
      BlockState _snowmanx = _snowman.with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
      BlockPos _snowmanxx = pos.up();
      if (world.getBlockState(_snowmanxx).isOf(Blocks.WATER)) {
         world.setBlockState(pos, _snowman, 2);
         world.setBlockState(_snowmanxx, _snowmanx, 2);
      }
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
