package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class AbstractPressurePlateBlock extends Block {
   protected static final VoxelShape PRESSED_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 0.5, 15.0);
   protected static final VoxelShape DEFAULT_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
   protected static final Box BOX = new Box(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

   protected AbstractPressurePlateBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.getRedstoneOutput(state) > 0 ? PRESSED_SHAPE : DEFAULT_SHAPE;
   }

   protected int getTickRate() {
      return 20;
   }

   @Override
   public boolean canMobSpawnInside() {
      return true;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return direction == Direction.DOWN && !state.canPlaceAt(world, pos)
         ? Blocks.AIR.getDefaultState()
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      return hasTopRim(world, _snowman) || sideCoversSmallSquare(world, _snowman, Direction.UP);
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      int _snowman = this.getRedstoneOutput(state);
      if (_snowman > 0) {
         this.updatePlateState(world, pos, state, _snowman);
      }
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      if (!world.isClient) {
         int _snowman = this.getRedstoneOutput(state);
         if (_snowman == 0) {
            this.updatePlateState(world, pos, state, _snowman);
         }
      }
   }

   protected void updatePlateState(World world, BlockPos pos, BlockState state, int rsOut) {
      int _snowman = this.getRedstoneOutput(world, pos);
      boolean _snowmanx = rsOut > 0;
      boolean _snowmanxx = _snowman > 0;
      if (rsOut != _snowman) {
         BlockState _snowmanxxx = this.setRedstoneOutput(state, _snowman);
         world.setBlockState(pos, _snowmanxxx, 2);
         this.updateNeighbors(world, pos);
         world.scheduleBlockRerenderIfNeeded(pos, state, _snowmanxxx);
      }

      if (!_snowmanxx && _snowmanx) {
         this.playDepressSound(world, pos);
      } else if (_snowmanxx && !_snowmanx) {
         this.playPressSound(world, pos);
      }

      if (_snowmanxx) {
         world.getBlockTickScheduler().schedule(new BlockPos(pos), this, this.getTickRate());
      }
   }

   protected abstract void playPressSound(WorldAccess world, BlockPos pos);

   protected abstract void playDepressSound(WorldAccess world, BlockPos pos);

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!moved && !state.isOf(newState.getBlock())) {
         if (this.getRedstoneOutput(state) > 0) {
            this.updateNeighbors(world, pos);
         }

         super.onStateReplaced(state, world, pos, newState, moved);
      }
   }

   protected void updateNeighbors(World world, BlockPos pos) {
      world.updateNeighborsAlways(pos, this);
      world.updateNeighborsAlways(pos.down(), this);
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return this.getRedstoneOutput(state);
   }

   @Override
   public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return direction == Direction.UP ? this.getRedstoneOutput(state) : 0;
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return true;
   }

   @Override
   public PistonBehavior getPistonBehavior(BlockState state) {
      return PistonBehavior.DESTROY;
   }

   protected abstract int getRedstoneOutput(World world, BlockPos pos);

   protected abstract int getRedstoneOutput(BlockState state);

   protected abstract BlockState setRedstoneOutput(BlockState state, int rsOut);
}
