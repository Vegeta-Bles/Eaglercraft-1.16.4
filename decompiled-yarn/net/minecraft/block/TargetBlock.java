package net.minecraft.block;

import java.util.Random;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TargetBlock extends Block {
   private static final IntProperty POWER = Properties.POWER;

   public TargetBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(POWER, Integer.valueOf(0)));
   }

   @Override
   public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      int _snowman = trigger(world, state, hit, projectile);
      Entity _snowmanx = projectile.getOwner();
      if (_snowmanx instanceof ServerPlayerEntity) {
         ServerPlayerEntity _snowmanxx = (ServerPlayerEntity)_snowmanx;
         _snowmanxx.incrementStat(Stats.TARGET_HIT);
         Criteria.TARGET_HIT.trigger(_snowmanxx, projectile, hit.getPos(), _snowman);
      }
   }

   private static int trigger(WorldAccess world, BlockState state, BlockHitResult _snowman, Entity _snowman) {
      int _snowmanxx = calculatePower(_snowman, _snowman.getPos());
      int _snowmanxxx = _snowman instanceof PersistentProjectileEntity ? 20 : 8;
      if (!world.getBlockTickScheduler().isScheduled(_snowman.getBlockPos(), state.getBlock())) {
         setPower(world, state, _snowmanxx, _snowman.getBlockPos(), _snowmanxxx);
      }

      return _snowmanxx;
   }

   private static int calculatePower(BlockHitResult _snowman, Vec3d pos) {
      Direction _snowmanx = _snowman.getSide();
      double _snowmanxx = Math.abs(MathHelper.fractionalPart(pos.x) - 0.5);
      double _snowmanxxx = Math.abs(MathHelper.fractionalPart(pos.y) - 0.5);
      double _snowmanxxxx = Math.abs(MathHelper.fractionalPart(pos.z) - 0.5);
      Direction.Axis _snowmanxxxxx = _snowmanx.getAxis();
      double _snowmanxxxxxx;
      if (_snowmanxxxxx == Direction.Axis.Y) {
         _snowmanxxxxxx = Math.max(_snowmanxx, _snowmanxxxx);
      } else if (_snowmanxxxxx == Direction.Axis.Z) {
         _snowmanxxxxxx = Math.max(_snowmanxx, _snowmanxxx);
      } else {
         _snowmanxxxxxx = Math.max(_snowmanxxx, _snowmanxxxx);
      }

      return Math.max(1, MathHelper.ceil(15.0 * MathHelper.clamp((0.5 - _snowmanxxxxxx) / 0.5, 0.0, 1.0)));
   }

   private static void setPower(WorldAccess world, BlockState state, int power, BlockPos pos, int delay) {
      world.setBlockState(pos, state.with(POWER, Integer.valueOf(power)), 3);
      world.getBlockTickScheduler().schedule(pos, state.getBlock(), delay);
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (state.get(POWER) != 0) {
         world.setBlockState(pos, state.with(POWER, Integer.valueOf(0)), 3);
      }
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return state.get(POWER);
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return true;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(POWER);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!world.isClient() && !state.isOf(oldState.getBlock())) {
         if (state.get(POWER) > 0 && !world.getBlockTickScheduler().isScheduled(pos, this)) {
            world.setBlockState(pos, state.with(POWER, Integer.valueOf(0)), 18);
         }
      }
   }
}
