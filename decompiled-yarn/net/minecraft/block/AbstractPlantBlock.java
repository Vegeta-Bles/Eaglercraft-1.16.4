package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class AbstractPlantBlock extends AbstractPlantPartBlock implements Fertilizable {
   protected AbstractPlantBlock(AbstractBlock.Settings _snowman, Direction _snowman, VoxelShape _snowman, boolean _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      AbstractPlantStemBlock _snowman = this.getStem();
      if (direction == this.growthDirection) {
         Block _snowmanx = newState.getBlock();
         if (_snowmanx != this && _snowmanx != _snowman) {
            return _snowman.getRandomGrowthState(world);
         }
      }

      if (this.tickWater) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return new ItemStack(this.getStem());
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      Optional<BlockPos> _snowman = this.method_25960(world, pos, state);
      return _snowman.isPresent() && this.getStem().chooseStemState(world.getBlockState(_snowman.get().offset(this.growthDirection)));
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      Optional<BlockPos> _snowman = this.method_25960(world, pos, state);
      if (_snowman.isPresent()) {
         BlockState _snowmanx = world.getBlockState(_snowman.get());
         ((AbstractPlantStemBlock)_snowmanx.getBlock()).grow(world, random, _snowman.get(), _snowmanx);
      }
   }

   private Optional<BlockPos> method_25960(BlockView _snowman, BlockPos _snowman, BlockState _snowman) {
      BlockPos _snowmanxxx = _snowman;

      Block _snowmanxxxx;
      do {
         _snowmanxxx = _snowmanxxx.offset(this.growthDirection);
         _snowmanxxxx = _snowman.getBlockState(_snowmanxxx).getBlock();
      } while (_snowmanxxxx == _snowman.getBlock());

      return _snowmanxxxx == this.getStem() ? Optional.of(_snowmanxxx) : Optional.empty();
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      boolean _snowman = super.canReplace(state, context);
      return _snowman && context.getStack().getItem() == this.getStem().asItem() ? false : _snowman;
   }

   @Override
   protected Block getPlant() {
      return this;
   }
}
