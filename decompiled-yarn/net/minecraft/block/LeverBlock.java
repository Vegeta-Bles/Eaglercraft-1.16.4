package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class LeverBlock extends WallMountedBlock {
   public static final BooleanProperty POWERED = Properties.POWERED;
   protected static final VoxelShape NORTH_WALL_SHAPE = Block.createCuboidShape(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
   protected static final VoxelShape SOUTH_WALL_SHAPE = Block.createCuboidShape(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
   protected static final VoxelShape WEST_WALL_SHAPE = Block.createCuboidShape(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
   protected static final VoxelShape EAST_WALL_SHAPE = Block.createCuboidShape(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
   protected static final VoxelShape FLOOR_Z_AXIS_SHAPE = Block.createCuboidShape(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
   protected static final VoxelShape FLOOR_X_AXIS_SHAPE = Block.createCuboidShape(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
   protected static final VoxelShape CEILING_Z_AXIS_SHAPE = Block.createCuboidShape(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
   protected static final VoxelShape CEILING_X_AXIS_SHAPE = Block.createCuboidShape(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

   protected LeverBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)).with(FACE, WallMountLocation.WALL)
      );
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      switch ((WallMountLocation)state.get(FACE)) {
         case FLOOR:
            switch (state.get(FACING).getAxis()) {
               case X:
                  return FLOOR_X_AXIS_SHAPE;
               case Z:
               default:
                  return FLOOR_Z_AXIS_SHAPE;
            }
         case WALL:
            switch ((Direction)state.get(FACING)) {
               case EAST:
                  return EAST_WALL_SHAPE;
               case WEST:
                  return WEST_WALL_SHAPE;
               case SOUTH:
                  return SOUTH_WALL_SHAPE;
               case NORTH:
               default:
                  return NORTH_WALL_SHAPE;
            }
         case CEILING:
         default:
            switch (state.get(FACING).getAxis()) {
               case X:
                  return CEILING_X_AXIS_SHAPE;
               case Z:
               default:
                  return CEILING_Z_AXIS_SHAPE;
            }
      }
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {
         BlockState _snowman = state.cycle(POWERED);
         if (_snowman.get(POWERED)) {
            spawnParticles(_snowman, world, pos, 1.0F);
         }

         return ActionResult.SUCCESS;
      } else {
         BlockState _snowman = this.method_21846(state, world, pos);
         float _snowmanx = _snowman.get(POWERED) ? 0.6F : 0.5F;
         world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, _snowmanx);
         return ActionResult.CONSUME;
      }
   }

   public BlockState method_21846(BlockState _snowman, World _snowman, BlockPos _snowman) {
      _snowman = _snowman.cycle(POWERED);
      _snowman.setBlockState(_snowman, _snowman, 3);
      this.updateNeighbors(_snowman, _snowman, _snowman);
      return _snowman;
   }

   private static void spawnParticles(BlockState state, WorldAccess world, BlockPos pos, float alpha) {
      Direction _snowman = state.get(FACING).getOpposite();
      Direction _snowmanx = getDirection(state).getOpposite();
      double _snowmanxx = (double)pos.getX() + 0.5 + 0.1 * (double)_snowman.getOffsetX() + 0.2 * (double)_snowmanx.getOffsetX();
      double _snowmanxxx = (double)pos.getY() + 0.5 + 0.1 * (double)_snowman.getOffsetY() + 0.2 * (double)_snowmanx.getOffsetY();
      double _snowmanxxxx = (double)pos.getZ() + 0.5 + 0.1 * (double)_snowman.getOffsetZ() + 0.2 * (double)_snowmanx.getOffsetZ();
      world.addParticle(new DustParticleEffect(1.0F, 0.0F, 0.0F, alpha), _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(POWERED) && random.nextFloat() < 0.25F) {
         spawnParticles(state, world, pos, 0.5F);
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!moved && !state.isOf(newState.getBlock())) {
         if (state.get(POWERED)) {
            this.updateNeighbors(state, world, pos);
         }

         super.onStateReplaced(state, world, pos, newState, moved);
      }
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return state.get(POWERED) ? 15 : 0;
   }

   @Override
   public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return state.get(POWERED) && getDirection(state) == direction ? 15 : 0;
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return true;
   }

   private void updateNeighbors(BlockState state, World world, BlockPos pos) {
      world.updateNeighborsAlways(pos, this);
      world.updateNeighborsAlways(pos.offset(getDirection(state).getOpposite()), this);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACE, FACING, POWERED);
   }
}
