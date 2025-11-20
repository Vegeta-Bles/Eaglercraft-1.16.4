package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RedstoneTorchBlock extends TorchBlock {
   public static final BooleanProperty LIT = Properties.LIT;
   private static final Map<BlockView, List<RedstoneTorchBlock.BurnoutEntry>> BURNOUT_MAP = new WeakHashMap<>();

   protected RedstoneTorchBlock(AbstractBlock.Settings _snowman) {
      super(_snowman, DustParticleEffect.RED);
      this.setDefaultState(this.stateManager.getDefaultState().with(LIT, Boolean.valueOf(true)));
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      for (Direction _snowman : Direction.values()) {
         world.updateNeighborsAlways(pos.offset(_snowman), this);
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!moved) {
         for (Direction _snowman : Direction.values()) {
            world.updateNeighborsAlways(pos.offset(_snowman), this);
         }
      }
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return state.get(LIT) && Direction.UP != direction ? 15 : 0;
   }

   protected boolean shouldUnpower(World world, BlockPos pos, BlockState state) {
      return world.isEmittingRedstonePower(pos.down(), Direction.DOWN);
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      boolean _snowman = this.shouldUnpower(world, pos, state);
      List<RedstoneTorchBlock.BurnoutEntry> _snowmanx = BURNOUT_MAP.get(world);

      while (_snowmanx != null && !_snowmanx.isEmpty() && world.getTime() - _snowmanx.get(0).time > 60L) {
         _snowmanx.remove(0);
      }

      if (state.get(LIT)) {
         if (_snowman) {
            world.setBlockState(pos, state.with(LIT, Boolean.valueOf(false)), 3);
            if (isBurnedOut(world, pos, true)) {
               world.syncWorldEvent(1502, pos, 0);
               world.getBlockTickScheduler().schedule(pos, world.getBlockState(pos).getBlock(), 160);
            }
         }
      } else if (!_snowman && !isBurnedOut(world, pos, false)) {
         world.setBlockState(pos, state.with(LIT, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (state.get(LIT) == this.shouldUnpower(world, pos, state) && !world.getBlockTickScheduler().isTicking(pos, this)) {
         world.getBlockTickScheduler().schedule(pos, this, 2);
      }
   }

   @Override
   public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return direction == Direction.DOWN ? state.getWeakRedstonePower(world, pos, direction) : 0;
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return true;
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(LIT)) {
         double _snowman = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
         double _snowmanx = (double)pos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2;
         double _snowmanxx = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
         world.addParticle(this.particle, _snowman, _snowmanx, _snowmanxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(LIT);
   }

   private static boolean isBurnedOut(World world, BlockPos pos, boolean addNew) {
      List<RedstoneTorchBlock.BurnoutEntry> _snowman = BURNOUT_MAP.computeIfAbsent(world, _snowmanx -> Lists.newArrayList());
      if (addNew) {
         _snowman.add(new RedstoneTorchBlock.BurnoutEntry(pos.toImmutable(), world.getTime()));
      }

      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         RedstoneTorchBlock.BurnoutEntry _snowmanxxx = _snowman.get(_snowmanxx);
         if (_snowmanxxx.pos.equals(pos)) {
            if (++_snowmanx >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public static class BurnoutEntry {
      private final BlockPos pos;
      private final long time;

      public BurnoutEntry(BlockPos pos, long time) {
         this.pos = pos;
         this.time = time;
      }
   }
}
