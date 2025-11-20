package net.minecraft.block;

import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   public TallPlantBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(this.stateManager.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      DoubleBlockHalf lv = state.get(HALF);
      if (direction.getAxis() != Direction.Axis.Y
         || lv == DoubleBlockHalf.LOWER != (direction == Direction.UP)
         || newState.isOf(this) && newState.get(HALF) != lv) {
         return lv == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
            ? Blocks.AIR.getDefaultState()
            : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         return Blocks.AIR.getDefaultState();
      }
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockPos lv = ctx.getBlockPos();
      return lv.getY() < 255 && ctx.getWorld().getBlockState(lv.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
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
         BlockState lv = world.getBlockState(pos.down());
         return lv.isOf(this) && lv.get(HALF) == DoubleBlockHalf.LOWER;
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
      DoubleBlockHalf lv = state.get(HALF);
      if (lv == DoubleBlockHalf.UPPER) {
         BlockPos lv2 = pos.down();
         BlockState lv3 = world.getBlockState(lv2);
         if (lv3.getBlock() == state.getBlock() && lv3.get(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockState(lv2, Blocks.AIR.getDefaultState(), 35);
            world.syncWorldEvent(player, 2001, lv2, Block.getRawIdFromState(lv3));
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

   @Environment(EnvType.CLIENT)
   @Override
   public long getRenderingSeed(BlockState state, BlockPos pos) {
      return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
   }
}
