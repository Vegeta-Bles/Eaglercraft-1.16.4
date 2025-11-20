package net.minecraft.block;

import java.util.Random;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class LeavesBlock extends Block {
   public static final IntProperty DISTANCE = Properties.DISTANCE_1_7;
   public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

   public LeavesBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, Integer.valueOf(7)).with(PERSISTENT, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
      return VoxelShapes.empty();
   }

   @Override
   public boolean hasRandomTicks(BlockState state) {
      return state.get(DISTANCE) == 7 && !state.get(PERSISTENT);
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.get(PERSISTENT) && state.get(DISTANCE) == 7) {
         dropStacks(state, world, pos);
         world.removeBlock(pos, false);
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
   }

   @Override
   public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
      return 1;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      int _snowman = getDistanceFromLog(newState) + 1;
      if (_snowman != 1 || state.get(DISTANCE) != _snowman) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      return state;
   }

   private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
      int _snowman = 7;
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (Direction _snowmanxx : Direction.values()) {
         _snowmanx.set(pos, _snowmanxx);
         _snowman = Math.min(_snowman, getDistanceFromLog(world.getBlockState(_snowmanx)) + 1);
         if (_snowman == 1) {
            break;
         }
      }

      return state.with(DISTANCE, Integer.valueOf(_snowman));
   }

   private static int getDistanceFromLog(BlockState state) {
      if (BlockTags.LOGS.contains(state.getBlock())) {
         return 0;
      } else {
         return state.getBlock() instanceof LeavesBlock ? state.get(DISTANCE) : 7;
      }
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (world.hasRain(pos.up())) {
         if (random.nextInt(15) == 1) {
            BlockPos _snowman = pos.down();
            BlockState _snowmanx = world.getBlockState(_snowman);
            if (!_snowmanx.isOpaque() || !_snowmanx.isSideSolidFullSquare(world, _snowman, Direction.UP)) {
               double _snowmanxx = (double)pos.getX() + random.nextDouble();
               double _snowmanxxx = (double)pos.getY() - 0.05;
               double _snowmanxxxx = (double)pos.getZ() + random.nextDouble();
               world.addParticle(ParticleTypes.DRIPPING_WATER, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(DISTANCE, PERSISTENT);
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, Boolean.valueOf(true)), ctx.getWorld(), ctx.getBlockPos());
   }
}
