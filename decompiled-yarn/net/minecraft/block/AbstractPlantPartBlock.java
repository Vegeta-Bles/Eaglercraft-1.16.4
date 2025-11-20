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
      BlockState _snowman = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(this.growthDirection));
      return !_snowman.isOf(this.getStem()) && !_snowman.isOf(this.getPlant()) ? this.getRandomGrowthState(ctx.getWorld()) : this.getPlant().getDefaultState();
   }

   public BlockState getRandomGrowthState(WorldAccess _snowman) {
      return this.getDefaultState();
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos _snowman = pos.offset(this.growthDirection.getOpposite());
      BlockState _snowmanx = world.getBlockState(_snowman);
      Block _snowmanxx = _snowmanx.getBlock();
      return !this.canAttachTo(_snowmanxx) ? false : _snowmanxx == this.getStem() || _snowmanxx == this.getPlant() || _snowmanx.isSideSolidFullSquare(world, _snowman, this.growthDirection);
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
