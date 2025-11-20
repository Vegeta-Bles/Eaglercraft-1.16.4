package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CactusBlock extends Block {
   public static final IntProperty AGE = Properties.AGE_15;
   protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
   protected static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   protected CactusBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(AGE, Integer.valueOf(0)));
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.breakBlock(pos, true);
      }
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      BlockPos _snowman = pos.up();
      if (world.isAir(_snowman)) {
         int _snowmanx = 1;

         while (world.getBlockState(pos.down(_snowmanx)).isOf(this)) {
            _snowmanx++;
         }

         if (_snowmanx < 3) {
            int _snowmanxx = state.get(AGE);
            if (_snowmanxx == 15) {
               world.setBlockState(_snowman, this.getDefaultState());
               BlockState _snowmanxxx = state.with(AGE, Integer.valueOf(0));
               world.setBlockState(pos, _snowmanxxx, 4);
               _snowmanxxx.neighborUpdate(world, _snowman, this, pos, false);
            } else {
               world.setBlockState(pos, state.with(AGE, Integer.valueOf(_snowmanxx + 1)), 4);
            }
         }
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return COLLISION_SHAPE;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return OUTLINE_SHAPE;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (!state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      for (Direction _snowman : Direction.Type.HORIZONTAL) {
         BlockState _snowmanx = world.getBlockState(pos.offset(_snowman));
         Material _snowmanxx = _snowmanx.getMaterial();
         if (_snowmanxx.isSolid() || world.getFluidState(pos.offset(_snowman)).isIn(FluidTags.LAVA)) {
            return false;
         }
      }

      BlockState _snowmanx = world.getBlockState(pos.down());
      return (_snowmanx.isOf(Blocks.CACTUS) || _snowmanx.isOf(Blocks.SAND) || _snowmanx.isOf(Blocks.RED_SAND)) && !world.getBlockState(pos.up()).getMaterial().isLiquid();
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      entity.damage(DamageSource.CACTUS, 1.0F);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
