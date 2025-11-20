package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ComparatorBlockEntity;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public class ComparatorBlock extends AbstractRedstoneGateBlock implements BlockEntityProvider {
   public static final EnumProperty<ComparatorMode> MODE = Properties.COMPARATOR_MODE;

   public ComparatorBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)).with(MODE, ComparatorMode.COMPARE)
      );
   }

   @Override
   protected int getUpdateDelayInternal(BlockState state) {
      return 2;
   }

   @Override
   protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      return _snowman instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)_snowman).getOutputSignal() : 0;
   }

   private int calculateOutputSignal(World world, BlockPos pos, BlockState state) {
      return state.get(MODE) == ComparatorMode.SUBTRACT
         ? Math.max(this.getPower(world, pos, state) - this.getMaxInputLevelSides(world, pos, state), 0)
         : this.getPower(world, pos, state);
   }

   @Override
   protected boolean hasPower(World world, BlockPos pos, BlockState state) {
      int _snowman = this.getPower(world, pos, state);
      if (_snowman == 0) {
         return false;
      } else {
         int _snowmanx = this.getMaxInputLevelSides(world, pos, state);
         return _snowman > _snowmanx ? true : _snowman == _snowmanx && state.get(MODE) == ComparatorMode.COMPARE;
      }
   }

   @Override
   protected int getPower(World world, BlockPos pos, BlockState state) {
      int _snowman = super.getPower(world, pos, state);
      Direction _snowmanx = state.get(FACING);
      BlockPos _snowmanxx = pos.offset(_snowmanx);
      BlockState _snowmanxxx = world.getBlockState(_snowmanxx);
      if (_snowmanxxx.hasComparatorOutput()) {
         _snowman = _snowmanxxx.getComparatorOutput(world, _snowmanxx);
      } else if (_snowman < 15 && _snowmanxxx.isSolidBlock(world, _snowmanxx)) {
         _snowmanxx = _snowmanxx.offset(_snowmanx);
         _snowmanxxx = world.getBlockState(_snowmanxx);
         ItemFrameEntity _snowmanxxxx = this.getAttachedItemFrame(world, _snowmanx, _snowmanxx);
         int _snowmanxxxxx = Math.max(
            _snowmanxxxx == null ? Integer.MIN_VALUE : _snowmanxxxx.getComparatorPower(),
            _snowmanxxx.hasComparatorOutput() ? _snowmanxxx.getComparatorOutput(world, _snowmanxx) : Integer.MIN_VALUE
         );
         if (_snowmanxxxxx != Integer.MIN_VALUE) {
            _snowman = _snowmanxxxxx;
         }
      }

      return _snowman;
   }

   @Nullable
   private ItemFrameEntity getAttachedItemFrame(World world, Direction facing, BlockPos pos) {
      List<ItemFrameEntity> _snowman = world.getEntitiesByClass(
         ItemFrameEntity.class,
         new Box((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1)),
         _snowmanx -> _snowmanx != null && _snowmanx.getHorizontalFacing() == facing
      );
      return _snowman.size() == 1 ? _snowman.get(0) : null;
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (!player.abilities.allowModifyWorld) {
         return ActionResult.PASS;
      } else {
         state = state.cycle(MODE);
         float _snowman = state.get(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
         world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, _snowman);
         world.setBlockState(pos, state, 2);
         this.update(world, pos, state);
         return ActionResult.success(world.isClient);
      }
   }

   @Override
   protected void updatePowered(World world, BlockPos pos, BlockState state) {
      if (!world.getBlockTickScheduler().isTicking(pos, this)) {
         int _snowman = this.calculateOutputSignal(world, pos, state);
         BlockEntity _snowmanx = world.getBlockEntity(pos);
         int _snowmanxx = _snowmanx instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)_snowmanx).getOutputSignal() : 0;
         if (_snowman != _snowmanxx || state.get(POWERED) != this.hasPower(world, pos, state)) {
            TickPriority _snowmanxxx = this.isTargetNotAligned(world, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
            world.getBlockTickScheduler().schedule(pos, this, 2, _snowmanxxx);
         }
      }
   }

   private void update(World world, BlockPos pos, BlockState state) {
      int _snowman = this.calculateOutputSignal(world, pos, state);
      BlockEntity _snowmanx = world.getBlockEntity(pos);
      int _snowmanxx = 0;
      if (_snowmanx instanceof ComparatorBlockEntity) {
         ComparatorBlockEntity _snowmanxxx = (ComparatorBlockEntity)_snowmanx;
         _snowmanxx = _snowmanxxx.getOutputSignal();
         _snowmanxxx.setOutputSignal(_snowman);
      }

      if (_snowmanxx != _snowman || state.get(MODE) == ComparatorMode.COMPARE) {
         boolean _snowmanxxx = this.hasPower(world, pos, state);
         boolean _snowmanxxxx = state.get(POWERED);
         if (_snowmanxxxx && !_snowmanxxx) {
            world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(false)), 2);
         } else if (!_snowmanxxxx && _snowmanxxx) {
            world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(true)), 2);
         }

         this.updateTarget(world, pos, state);
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      this.update(world, pos, state);
   }

   @Override
   public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
      super.onSyncedBlockEvent(state, world, pos, type, data);
      BlockEntity _snowman = world.getBlockEntity(pos);
      return _snowman != null && _snowman.onSyncedBlockEvent(type, data);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new ComparatorBlockEntity();
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, MODE, POWERED);
   }
}
