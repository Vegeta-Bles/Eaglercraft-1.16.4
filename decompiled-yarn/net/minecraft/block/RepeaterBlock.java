package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class RepeaterBlock extends AbstractRedstoneGateBlock {
   public static final BooleanProperty LOCKED = Properties.LOCKED;
   public static final IntProperty DELAY = Properties.DELAY;

   protected RepeaterBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(FACING, Direction.NORTH)
            .with(DELAY, Integer.valueOf(1))
            .with(LOCKED, Boolean.valueOf(false))
            .with(POWERED, Boolean.valueOf(false))
      );
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (!player.abilities.allowModifyWorld) {
         return ActionResult.PASS;
      } else {
         world.setBlockState(pos, state.cycle(DELAY), 3);
         return ActionResult.success(world.isClient);
      }
   }

   @Override
   protected int getUpdateDelayInternal(BlockState state) {
      return state.get(DELAY) * 2;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = super.getPlacementState(ctx);
      return _snowman.with(LOCKED, Boolean.valueOf(this.isLocked(ctx.getWorld(), ctx.getBlockPos(), _snowman)));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return !world.isClient() && direction.getAxis() != state.get(FACING).getAxis()
         ? state.with(LOCKED, Boolean.valueOf(this.isLocked(world, pos, state)))
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean isLocked(WorldView _snowman, BlockPos pos, BlockState state) {
      return this.getMaxInputLevelSides(_snowman, pos, state) > 0;
   }

   @Override
   protected boolean isValidInput(BlockState state) {
      return isRedstoneGate(state);
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(POWERED)) {
         Direction _snowman = state.get(FACING);
         double _snowmanx = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
         double _snowmanxx = (double)pos.getY() + 0.4 + (random.nextDouble() - 0.5) * 0.2;
         double _snowmanxxx = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
         float _snowmanxxxx = -5.0F;
         if (random.nextBoolean()) {
            _snowmanxxxx = (float)(state.get(DELAY) * 2 - 1);
         }

         _snowmanxxxx /= 16.0F;
         double _snowmanxxxxx = (double)(_snowmanxxxx * (float)_snowman.getOffsetX());
         double _snowmanxxxxxx = (double)(_snowmanxxxx * (float)_snowman.getOffsetZ());
         world.addParticle(DustParticleEffect.RED, _snowmanx + _snowmanxxxxx, _snowmanxx, _snowmanxxx + _snowmanxxxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, DELAY, LOCKED, POWERED);
   }
}
