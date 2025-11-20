package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class TallPlantBlock extends PlantBlock {
   public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

   public TallPlantBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      DoubleBlockHalf _snowman = state.get(HALF);
      if (direction.getAxis() != Direction.Axis.Y
         || _snowman == DoubleBlockHalf.LOWER != (direction == Direction.UP)
         || newState.isOf(this) && newState.get(HALF) != _snowman) {
         return _snowman == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
            ? Blocks.AIR.getDefaultState()
            : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         return Blocks.AIR.getDefaultState();
      }
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockPos _snowman = ctx.getBlockPos();
      return _snowman.getY() < 255 && ctx.getWorld().getBlockState(_snowman.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), 3);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      if (state.get(HALF) != DoubleBlockHalf.UPPER) {
         return super.canPlaceAt(state, world, pos);
      } else {
         BlockState _snowman = world.getBlockState(pos.down());
         return _snowman.isOf(this) && _snowman.get(HALF) == DoubleBlockHalf.LOWER;
      }
   }

   public void placeAt(WorldAccess world, BlockPos pos, int flags) {
      world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER), flags);
      world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), flags);
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient) {
         if (player.isCreative()) {
            onBreakInCreative(world, pos, state, player);
         } else {
            dropStacks(state, world, pos, null, player, player.getMainHandStack());
         }
      }

      super.onBreak(world, pos, state, player);
   }

   @Override
   public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
      super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
   }

   protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      DoubleBlockHalf _snowman = state.get(HALF);
      if (_snowman == DoubleBlockHalf.UPPER) {
         BlockPos _snowmanx = pos.down();
         BlockState _snowmanxx = world.getBlockState(_snowmanx);
         if (_snowmanxx.getBlock() == state.getBlock() && _snowmanxx.get(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockState(_snowmanx, Blocks.AIR.getDefaultState(), 35);
            world.syncWorldEvent(player, 2001, _snowmanx, Block.getRawIdFromState(_snowmanxx));
         }
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(HALF);
   }

   @Override
   public AbstractBlock.OffsetType getOffsetType() {
      return AbstractBlock.OffsetType.XZ;
   }

   @Override
   public long getRenderingSeed(BlockState state, BlockPos pos) {
      return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
   }
}
