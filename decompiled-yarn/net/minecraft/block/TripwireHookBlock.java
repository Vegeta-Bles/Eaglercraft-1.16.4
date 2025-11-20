package net.minecraft.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class TripwireHookBlock extends Block {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final BooleanProperty POWERED = Properties.POWERED;
   public static final BooleanProperty ATTACHED = Properties.ATTACHED;
   protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
   protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

   public TripwireHookBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)).with(ATTACHED, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      switch ((Direction)state.get(FACING)) {
         case EAST:
         default:
            return WEST_SHAPE;
         case WEST:
            return EAST_SHAPE;
         case SOUTH:
            return NORTH_SHAPE;
         case NORTH:
            return SOUTH_SHAPE;
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      Direction _snowman = state.get(FACING);
      BlockPos _snowmanx = pos.offset(_snowman.getOpposite());
      BlockState _snowmanxx = world.getBlockState(_snowmanx);
      return _snowman.getAxis().isHorizontal() && _snowmanxx.isSideSolidFullSquare(world, _snowmanx, _snowman);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)
         ? Blocks.AIR.getDefaultState()
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = this.getDefaultState().with(POWERED, Boolean.valueOf(false)).with(ATTACHED, Boolean.valueOf(false));
      WorldView _snowmanx = ctx.getWorld();
      BlockPos _snowmanxx = ctx.getBlockPos();
      Direction[] _snowmanxxx = ctx.getPlacementDirections();

      for (Direction _snowmanxxxx : _snowmanxxx) {
         if (_snowmanxxxx.getAxis().isHorizontal()) {
            Direction _snowmanxxxxx = _snowmanxxxx.getOpposite();
            _snowman = _snowman.with(FACING, _snowmanxxxxx);
            if (_snowman.canPlaceAt(_snowmanx, _snowmanxx)) {
               return _snowman;
            }
         }
      }

      return null;
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      this.update(world, pos, state, false, false, -1, null);
   }

   public void update(World world, BlockPos pos, BlockState state, boolean beingRemoved, boolean _snowman, int _snowman, @Nullable BlockState _snowman) {
      Direction _snowmanxxx = state.get(FACING);
      boolean _snowmanxxxx = state.get(ATTACHED);
      boolean _snowmanxxxxx = state.get(POWERED);
      boolean _snowmanxxxxxx = !beingRemoved;
      boolean _snowmanxxxxxxx = false;
      int _snowmanxxxxxxxx = 0;
      BlockState[] _snowmanxxxxxxxxx = new BlockState[42];

      for (int _snowmanxxxxxxxxxx = 1; _snowmanxxxxxxxxxx < 42; _snowmanxxxxxxxxxx++) {
         BlockPos _snowmanxxxxxxxxxxx = pos.offset(_snowmanxxx, _snowmanxxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxx.isOf(Blocks.TRIPWIRE_HOOK)) {
            if (_snowmanxxxxxxxxxxxx.get(FACING) == _snowmanxxx.getOpposite()) {
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxx;
            }
            break;
         }

         if (!_snowmanxxxxxxxxxxxx.isOf(Blocks.TRIPWIRE) && _snowmanxxxxxxxxxx != _snowman) {
            _snowmanxxxxxxxxx[_snowmanxxxxxxxxxx] = null;
            _snowmanxxxxxx = false;
         } else {
            if (_snowmanxxxxxxxxxx == _snowman) {
               _snowmanxxxxxxxxxxxx = (BlockState)MoreObjects.firstNonNull(_snowman, _snowmanxxxxxxxxxxxx);
            }

            boolean _snowmanxxxxxxxxxxxxx = !_snowmanxxxxxxxxxxxx.get(TripwireBlock.DISARMED);
            boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.get(TripwireBlock.POWERED);
            _snowmanxxxxxxx |= _snowmanxxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxx[_snowmanxxxxxxxxxx] = _snowmanxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxx == _snowman) {
               world.getBlockTickScheduler().schedule(pos, this, 10);
               _snowmanxxxxxx &= _snowmanxxxxxxxxxxxxx;
            }
         }
      }

      _snowmanxxxxxx &= _snowmanxxxxxxxx > 1;
      _snowmanxxxxxxx &= _snowmanxxxxxx;
      BlockState _snowmanxxxxxxxxxx = this.getDefaultState().with(ATTACHED, Boolean.valueOf(_snowmanxxxxxx)).with(POWERED, Boolean.valueOf(_snowmanxxxxxxx));
      if (_snowmanxxxxxxxx > 0) {
         BlockPos _snowmanxxxxxxxxxxxxx = pos.offset(_snowmanxxx, _snowmanxxxxxxxx);
         Direction _snowmanxxxxxxxxxxxxxx = _snowmanxxx.getOpposite();
         world.setBlockState(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxx.with(FACING, _snowmanxxxxxxxxxxxxxx), 3);
         this.updateNeighborsOnAxis(world, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
         this.playSound(world, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxx);
      }

      this.playSound(world, pos, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxx);
      if (!beingRemoved) {
         world.setBlockState(pos, _snowmanxxxxxxxxxx.with(FACING, _snowmanxxx), 3);
         if (_snowman) {
            this.updateNeighborsOnAxis(world, pos, _snowmanxxx);
         }
      }

      if (_snowmanxxxx != _snowmanxxxxxx) {
         for (int _snowmanxxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
            BlockPos _snowmanxxxxxxxxxxxxxx = pos.offset(_snowmanxxx, _snowmanxxxxxxxxxxxxx);
            BlockState _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxxxx];
            if (_snowmanxxxxxxxxxxxxxxx != null) {
               world.setBlockState(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.with(ATTACHED, Boolean.valueOf(_snowmanxxxxxx)), 3);
               if (!world.getBlockState(_snowmanxxxxxxxxxxxxxx).isAir()) {
               }
            }
         }
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      this.update(world, pos, state, false, true, -1, null);
   }

   private void playSound(World world, BlockPos pos, boolean attached, boolean on, boolean detached, boolean off) {
      if (on && !off) {
         world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 0.4F, 0.6F);
      } else if (!on && off) {
         world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF, SoundCategory.BLOCKS, 0.4F, 0.5F);
      } else if (attached && !detached) {
         world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_ATTACH, SoundCategory.BLOCKS, 0.4F, 0.7F);
      } else if (!attached && detached) {
         world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_DETACH, SoundCategory.BLOCKS, 0.4F, 1.2F / (world.random.nextFloat() * 0.2F + 0.9F));
      }
   }

   private void updateNeighborsOnAxis(World world, BlockPos pos, Direction direction) {
      world.updateNeighborsAlways(pos, this);
      world.updateNeighborsAlways(pos.offset(direction.getOpposite()), this);
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!moved && !state.isOf(newState.getBlock())) {
         boolean _snowman = state.get(ATTACHED);
         boolean _snowmanx = state.get(POWERED);
         if (_snowman || _snowmanx) {
            this.update(world, pos, state, true, false, -1, null);
         }

         if (_snowmanx) {
            world.updateNeighborsAlways(pos, this);
            world.updateNeighborsAlways(pos.offset(state.get(FACING).getOpposite()), this);
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
      if (!state.get(POWERED)) {
         return 0;
      } else {
         return state.get(FACING) == direction ? 15 : 0;
      }
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return true;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, POWERED, ATTACHED);
   }
}
