package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   protected AbstractPlantBlock(AbstractBlock.Settings arg, Direction arg2, VoxelShape arg3, boolean bl) {
      super(arg, arg2, arg3, bl);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      AbstractPlantStemBlock lv = this.getStem();
      if (direction == this.growthDirection) {
         Block lv2 = newState.getBlock();
         if (lv2 != this && lv2 != lv) {
            return lv.getRandomGrowthState(world);
         }
      }

      if (this.tickWater) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return new ItemStack(this.getStem());
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      Optional<BlockPos> optional = this.method_25960(world, pos, state);
      return optional.isPresent() && this.getStem().chooseStemState(world.getBlockState(optional.get().offset(this.growthDirection)));
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      Optional<BlockPos> optional = this.method_25960(world, pos, state);
      if (optional.isPresent()) {
         BlockState lv = world.getBlockState(optional.get());
         ((AbstractPlantStemBlock)lv.getBlock()).grow(world, random, optional.get(), lv);
      }
   }

   private Optional<BlockPos> method_25960(BlockView arg, BlockPos arg2, BlockState arg3) {
      BlockPos lv = arg2;

      Block lv2;
      do {
         lv = lv.offset(this.growthDirection);
         lv2 = arg.getBlockState(lv).getBlock();
      } while (lv2 == arg3.getBlock());

      return lv2 == this.getStem() ? Optional.of(lv) : Optional.empty();
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      boolean bl = super.canReplace(state, context);
      return bl && context.getStack().getItem() == this.getStem().asItem() ? false : bl;
   }

   @Override
   protected Block getPlant() {
      return this;
   }
}
