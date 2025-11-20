package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class AbstractPlantPartBlock extends Block {
   protected final Direction growthDirection;
   protected final boolean tickWater;
   protected final VoxelShape outlineShape;

   protected AbstractPlantPartBlock(AbstractBlock.Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater) {
      super(settings);
      this.growthDirection = growthDirection;
      this.outlineShape = outlineShape;
      this.tickWater = tickWater;
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState lv = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(this.growthDirection));
      return !lv.isOf(this.getStem()) && !lv.isOf(this.getPlant()) ? this.getRandomGrowthState(ctx.getWorld()) : this.getPlant().getDefaultState();
   }

   public BlockState getRandomGrowthState(WorldAccess arg) {
      return this.getDefaultState();
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos lv = pos.offset(this.growthDirection.getOpposite());
      BlockState lv2 = world.getBlockState(lv);
      Block lv3 = lv2.getBlock();
      return !this.canAttachTo(lv3) ? false : lv3 == this.getStem() || lv3 == this.getPlant() || lv2.isSideSolidFullSquare(world, lv, this.growthDirection);
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.breakBlock(pos, true);
      }
   }

   protected boolean canAttachTo(Block block) {
      return true;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.outlineShape;
   }

   protected abstract AbstractPlantStemBlock getStem();

   protected abstract Block getPlant();
}
