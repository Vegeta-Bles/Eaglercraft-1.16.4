package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public abstract class AbstractRedstoneGateBlock extends HorizontalFacingBlock {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final BooleanProperty POWERED = Properties.POWERED;

   protected AbstractRedstoneGateBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return hasTopRim(world, pos.down());
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!this.isLocked(world, pos, state)) {
         boolean _snowman = state.get(POWERED);
         boolean _snowmanx = this.hasPower(world, pos, state);
         if (_snowman && !_snowmanx) {
            world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(false)), 2);
         } else if (!_snowman) {
            world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(true)), 2);
            if (!_snowmanx) {
               world.getBlockTickScheduler().schedule(pos, this, this.getUpdateDelayInternal(state), TickPriority.VERY_HIGH);
            }
         }
      }
   }

   @Override
   public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return state.getWeakRedstonePower(world, pos, direction);
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      if (!state.get(POWERED)) {
         return 0;
      } else {
         return state.get(FACING) == direction ? this.getOutputLevel(world, pos, state) : 0;
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (state.canPlaceAt(world, pos)) {
         this.updatePowered(world, pos, state);
      } else {
         BlockEntity _snowman = this.hasBlockEntity() ? world.getBlockEntity(pos) : null;
         dropStacks(state, world, pos, _snowman);
         world.removeBlock(pos, false);

         for (Direction _snowmanx : Direction.values()) {
            world.updateNeighborsAlways(pos.offset(_snowmanx), this);
         }
      }
   }

   protected void updatePowered(World world, BlockPos pos, BlockState state) {
      if (!this.isLocked(world, pos, state)) {
         boolean _snowman = state.get(POWERED);
         boolean _snowmanx = this.hasPower(world, pos, state);
         if (_snowman != _snowmanx && !world.getBlockTickScheduler().isTicking(pos, this)) {
            TickPriority _snowmanxx = TickPriority.HIGH;
            if (this.isTargetNotAligned(world, pos, state)) {
               _snowmanxx = TickPriority.EXTREMELY_HIGH;
            } else if (_snowman) {
               _snowmanxx = TickPriority.VERY_HIGH;
            }

            world.getBlockTickScheduler().schedule(pos, this, this.getUpdateDelayInternal(state), _snowmanxx);
         }
      }
   }

   public boolean isLocked(WorldView _snowman, BlockPos pos, BlockState state) {
      return false;
   }

   protected boolean hasPower(World world, BlockPos pos, BlockState state) {
      return this.getPower(world, pos, state) > 0;
   }

   protected int getPower(World world, BlockPos pos, BlockState state) {
      Direction _snowman = state.get(FACING);
      BlockPos _snowmanx = pos.offset(_snowman);
      int _snowmanxx = world.getEmittedRedstonePower(_snowmanx, _snowman);
      if (_snowmanxx >= 15) {
         return _snowmanxx;
      } else {
         BlockState _snowmanxxx = world.getBlockState(_snowmanx);
         return Math.max(_snowmanxx, _snowmanxxx.isOf(Blocks.REDSTONE_WIRE) ? _snowmanxxx.get(RedstoneWireBlock.POWER) : 0);
      }
   }

   protected int getMaxInputLevelSides(WorldView world, BlockPos pos, BlockState state) {
      Direction _snowman = state.get(FACING);
      Direction _snowmanx = _snowman.rotateYClockwise();
      Direction _snowmanxx = _snowman.rotateYCounterclockwise();
      return Math.max(this.getInputLevel(world, pos.offset(_snowmanx), _snowmanx), this.getInputLevel(world, pos.offset(_snowmanxx), _snowmanxx));
   }

   protected int getInputLevel(WorldView world, BlockPos pos, Direction dir) {
      BlockState _snowman = world.getBlockState(pos);
      if (this.isValidInput(_snowman)) {
         if (_snowman.isOf(Blocks.REDSTONE_BLOCK)) {
            return 15;
         } else {
            return _snowman.isOf(Blocks.REDSTONE_WIRE) ? _snowman.get(RedstoneWireBlock.POWER) : world.getStrongRedstonePower(pos, dir);
         }
      } else {
         return 0;
      }
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return true;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      if (this.hasPower(world, pos, state)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      this.updateTarget(world, pos, state);
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!moved && !state.isOf(newState.getBlock())) {
         super.onStateReplaced(state, world, pos, newState, moved);
         this.updateTarget(world, pos, state);
      }
   }

   protected void updateTarget(World world, BlockPos pos, BlockState state) {
      Direction _snowman = state.get(FACING);
      BlockPos _snowmanx = pos.offset(_snowman.getOpposite());
      world.updateNeighbor(_snowmanx, this, pos);
      world.updateNeighborsExcept(_snowmanx, this, _snowman);
   }

   protected boolean isValidInput(BlockState state) {
      return state.emitsRedstonePower();
   }

   protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
      return 15;
   }

   public static boolean isRedstoneGate(BlockState state) {
      return state.getBlock() instanceof AbstractRedstoneGateBlock;
   }

   public boolean isTargetNotAligned(BlockView world, BlockPos pos, BlockState state) {
      Direction _snowman = state.get(FACING).getOpposite();
      BlockState _snowmanx = world.getBlockState(pos.offset(_snowman));
      return isRedstoneGate(_snowmanx) && _snowmanx.get(FACING) != _snowman;
   }

   protected abstract int getUpdateDelayInternal(BlockState state);
}
