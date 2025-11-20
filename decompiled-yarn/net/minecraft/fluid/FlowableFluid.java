package net.minecraft.fluid;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class FlowableFluid extends Fluid {
   public static final BooleanProperty FALLING = Properties.FALLING;
   public static final IntProperty LEVEL = Properties.LEVEL_1_8;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.NeighborGroup>> field_15901 = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.NeighborGroup> _snowman = new Object2ByteLinkedOpenHashMap<Block.NeighborGroup>(200) {
         protected void rehash(int _snowman) {
         }
      };
      _snowman.defaultReturnValue((byte)127);
      return _snowman;
   });
   private final Map<FluidState, VoxelShape> shapeCache = Maps.newIdentityHashMap();

   public FlowableFluid() {
   }

   @Override
   protected void appendProperties(StateManager.Builder<Fluid, FluidState> _snowman) {
      _snowman.add(FALLING);
   }

   @Override
   public Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state) {
      double _snowman = 0.0;
      double _snowmanx = 0.0;
      BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();

      for (Direction _snowmanxxx : Direction.Type.HORIZONTAL) {
         _snowmanxx.set(pos, _snowmanxxx);
         FluidState _snowmanxxxx = world.getFluidState(_snowmanxx);
         if (this.method_15748(_snowmanxxxx)) {
            float _snowmanxxxxx = _snowmanxxxx.getHeight();
            float _snowmanxxxxxx = 0.0F;
            if (_snowmanxxxxx == 0.0F) {
               if (!world.getBlockState(_snowmanxx).getMaterial().blocksMovement()) {
                  BlockPos _snowmanxxxxxxx = _snowmanxx.down();
                  FluidState _snowmanxxxxxxxx = world.getFluidState(_snowmanxxxxxxx);
                  if (this.method_15748(_snowmanxxxxxxxx)) {
                     _snowmanxxxxx = _snowmanxxxxxxxx.getHeight();
                     if (_snowmanxxxxx > 0.0F) {
                        _snowmanxxxxxx = state.getHeight() - (_snowmanxxxxx - 0.8888889F);
                     }
                  }
               }
            } else if (_snowmanxxxxx > 0.0F) {
               _snowmanxxxxxx = state.getHeight() - _snowmanxxxxx;
            }

            if (_snowmanxxxxxx != 0.0F) {
               _snowman += (double)((float)_snowmanxxx.getOffsetX() * _snowmanxxxxxx);
               _snowmanx += (double)((float)_snowmanxxx.getOffsetZ() * _snowmanxxxxxx);
            }
         }
      }

      Vec3d _snowmanxxxx = new Vec3d(_snowman, 0.0, _snowmanx);
      if (state.get(FALLING)) {
         for (Direction _snowmanxxxxxxx : Direction.Type.HORIZONTAL) {
            _snowmanxx.set(pos, _snowmanxxxxxxx);
            if (this.method_15749(world, _snowmanxx, _snowmanxxxxxxx) || this.method_15749(world, _snowmanxx.up(), _snowmanxxxxxxx)) {
               _snowmanxxxx = _snowmanxxxx.normalize().add(0.0, -6.0, 0.0);
               break;
            }
         }
      }

      return _snowmanxxxx.normalize();
   }

   private boolean method_15748(FluidState state) {
      return state.isEmpty() || state.getFluid().matchesType(this);
   }

   protected boolean method_15749(BlockView world, BlockPos pos, Direction _snowman) {
      BlockState _snowmanx = world.getBlockState(pos);
      FluidState _snowmanxx = world.getFluidState(pos);
      if (_snowmanxx.getFluid().matchesType(this)) {
         return false;
      } else if (_snowman == Direction.UP) {
         return true;
      } else {
         return _snowmanx.getMaterial() == Material.ICE ? false : _snowmanx.isSideSolidFullSquare(world, pos, _snowman);
      }
   }

   protected void tryFlow(WorldAccess world, BlockPos fluidPos, FluidState state) {
      if (!state.isEmpty()) {
         BlockState _snowman = world.getBlockState(fluidPos);
         BlockPos _snowmanx = fluidPos.down();
         BlockState _snowmanxx = world.getBlockState(_snowmanx);
         FluidState _snowmanxxx = this.getUpdatedState(world, _snowmanx, _snowmanxx);
         if (this.canFlow(world, fluidPos, _snowman, Direction.DOWN, _snowmanx, _snowmanxx, world.getFluidState(_snowmanx), _snowmanxxx.getFluid())) {
            this.flow(world, _snowmanx, _snowmanxx, Direction.DOWN, _snowmanxxx);
            if (this.method_15740(world, fluidPos) >= 3) {
               this.method_15744(world, fluidPos, state, _snowman);
            }
         } else if (state.isStill() || !this.method_15736(world, _snowmanxxx.getFluid(), fluidPos, _snowman, _snowmanx, _snowmanxx)) {
            this.method_15744(world, fluidPos, state, _snowman);
         }
      }
   }

   private void method_15744(WorldAccess world, BlockPos pos, FluidState fluidState, BlockState blockState) {
      int _snowman = fluidState.getLevel() - this.getLevelDecreasePerBlock(world);
      if (fluidState.get(FALLING)) {
         _snowman = 7;
      }

      if (_snowman > 0) {
         Map<Direction, FluidState> _snowmanx = this.getSpread(world, pos, blockState);

         for (Entry<Direction, FluidState> _snowmanxx : _snowmanx.entrySet()) {
            Direction _snowmanxxx = _snowmanxx.getKey();
            FluidState _snowmanxxxx = _snowmanxx.getValue();
            BlockPos _snowmanxxxxx = pos.offset(_snowmanxxx);
            BlockState _snowmanxxxxxx = world.getBlockState(_snowmanxxxxx);
            if (this.canFlow(world, pos, blockState, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, world.getFluidState(_snowmanxxxxx), _snowmanxxxx.getFluid())) {
               this.flow(world, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxx, _snowmanxxxx);
            }
         }
      }
   }

   protected FluidState getUpdatedState(WorldView world, BlockPos pos, BlockState state) {
      int _snowman = 0;
      int _snowmanx = 0;

      for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
         BlockPos _snowmanxxx = pos.offset(_snowmanxx);
         BlockState _snowmanxxxx = world.getBlockState(_snowmanxxx);
         FluidState _snowmanxxxxx = _snowmanxxxx.getFluidState();
         if (_snowmanxxxxx.getFluid().matchesType(this) && this.receivesFlow(_snowmanxx, world, pos, state, _snowmanxxx, _snowmanxxxx)) {
            if (_snowmanxxxxx.isStill()) {
               _snowmanx++;
            }

            _snowman = Math.max(_snowman, _snowmanxxxxx.getLevel());
         }
      }

      if (this.isInfinite() && _snowmanx >= 2) {
         BlockState _snowmanxxx = world.getBlockState(pos.down());
         FluidState _snowmanxxxx = _snowmanxxx.getFluidState();
         if (_snowmanxxx.getMaterial().isSolid() || this.isMatchingAndStill(_snowmanxxxx)) {
            return this.getStill(false);
         }
      }

      BlockPos _snowmanxxx = pos.up();
      BlockState _snowmanxxxx = world.getBlockState(_snowmanxxx);
      FluidState _snowmanxxxxx = _snowmanxxxx.getFluidState();
      if (!_snowmanxxxxx.isEmpty() && _snowmanxxxxx.getFluid().matchesType(this) && this.receivesFlow(Direction.UP, world, pos, state, _snowmanxxx, _snowmanxxxx)) {
         return this.getFlowing(8, true);
      } else {
         int _snowmanxxxxxx = _snowman - this.getLevelDecreasePerBlock(world);
         return _snowmanxxxxxx <= 0 ? Fluids.EMPTY.getDefaultState() : this.getFlowing(_snowmanxxxxxx, false);
      }
   }

   private boolean receivesFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
      Object2ByteLinkedOpenHashMap<Block.NeighborGroup> _snowman;
      if (!state.getBlock().hasDynamicBounds() && !fromState.getBlock().hasDynamicBounds()) {
         _snowman = field_15901.get();
      } else {
         _snowman = null;
      }

      Block.NeighborGroup _snowmanx;
      if (_snowman != null) {
         _snowmanx = new Block.NeighborGroup(state, fromState, face);
         byte _snowmanxx = _snowman.getAndMoveToFirst(_snowmanx);
         if (_snowmanxx != 127) {
            return _snowmanxx != 0;
         }
      } else {
         _snowmanx = null;
      }

      VoxelShape _snowmanxx = state.getCollisionShape(world, pos);
      VoxelShape _snowmanxxx = fromState.getCollisionShape(world, fromPos);
      boolean _snowmanxxxx = !VoxelShapes.adjacentSidesCoverSquare(_snowmanxx, _snowmanxxx, face);
      if (_snowman != null) {
         if (_snowman.size() == 200) {
            _snowman.removeLastByte();
         }

         _snowman.putAndMoveToFirst(_snowmanx, (byte)(_snowmanxxxx ? 1 : 0));
      }

      return _snowmanxxxx;
   }

   public abstract Fluid getFlowing();

   public FluidState getFlowing(int level, boolean falling) {
      return this.getFlowing().getDefaultState().with(LEVEL, Integer.valueOf(level)).with(FALLING, Boolean.valueOf(falling));
   }

   public abstract Fluid getStill();

   public FluidState getStill(boolean falling) {
      return this.getStill().getDefaultState().with(FALLING, Boolean.valueOf(falling));
   }

   protected abstract boolean isInfinite();

   protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
      if (state.getBlock() instanceof FluidFillable) {
         ((FluidFillable)state.getBlock()).tryFillWithFluid(world, pos, state, fluidState);
      } else {
         if (!state.isAir()) {
            this.beforeBreakingBlock(world, pos, state);
         }

         world.setBlockState(pos, fluidState.getBlockState(), 3);
      }
   }

   protected abstract void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state);

   private static short method_15747(BlockPos _snowman, BlockPos _snowman) {
      int _snowmanxx = _snowman.getX() - _snowman.getX();
      int _snowmanxxx = _snowman.getZ() - _snowman.getZ();
      return (short)((_snowmanxx + 128 & 0xFF) << 8 | _snowmanxxx + 128 & 0xFF);
   }

   protected int method_15742(
      WorldView world, BlockPos _snowman, int _snowman, Direction _snowman, BlockState _snowman, BlockPos _snowman, Short2ObjectMap<Pair<BlockState, FluidState>> _snowman, Short2BooleanMap _snowman
   ) {
      int _snowmanxxxxxxx = 1000;

      for (Direction _snowmanxxxxxxxx : Direction.Type.HORIZONTAL) {
         if (_snowmanxxxxxxxx != _snowman) {
            BlockPos _snowmanxxxxxxxxx = _snowman.offset(_snowmanxxxxxxxx);
            short _snowmanxxxxxxxxxx = method_15747(_snowman, _snowmanxxxxxxxxx);
            Pair<BlockState, FluidState> _snowmanxxxxxxxxxxx = (Pair<BlockState, FluidState>)_snowman.computeIfAbsent(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx -> {
               BlockState _snowmanxxxxxxxxxxxxx = world.getBlockState(_snowman);
               return Pair.of(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx.getFluidState());
            });
            BlockState _snowmanxxxxxxxxxxxx = (BlockState)_snowmanxxxxxxxxxxx.getFirst();
            FluidState _snowmanxxxxxxxxxxxxx = (FluidState)_snowmanxxxxxxxxxxx.getSecond();
            if (this.canFlowThrough(world, this.getFlowing(), _snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx)) {
               boolean _snowmanxxxxxxxxxxxxxx = _snowman.computeIfAbsent(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx -> {
                  BlockPos _snowmanxxxxxxxxxxxxxxxx = _snowman.down();
                  BlockState _snowmanxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxxxxxxxxx);
                  return this.method_15736(world, this.getFlowing(), _snowman, _snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
               });
               if (_snowmanxxxxxxxxxxxxxx) {
                  return _snowman;
               }

               if (_snowman < this.getFlowSpeed(world)) {
                  int _snowmanxxxxxxxxxxxxxxx = this.method_15742(world, _snowmanxxxxxxxxx, _snowman + 1, _snowmanxxxxxxxx.getOpposite(), _snowmanxxxxxxxxxxxx, _snowman, _snowman, _snowman);
                  if (_snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxx) {
                     _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
                  }
               }
            }
         }
      }

      return _snowmanxxxxxxx;
   }

   private boolean method_15736(BlockView world, Fluid fluid, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
      if (!this.receivesFlow(Direction.DOWN, world, pos, state, fromPos, fromState)) {
         return false;
      } else {
         return fromState.getFluidState().getFluid().matchesType(this) ? true : this.canFill(world, fromPos, fromState, fluid);
      }
   }

   private boolean canFlowThrough(
      BlockView world, Fluid fluid, BlockPos pos, BlockState state, Direction face, BlockPos fromPos, BlockState fromState, FluidState fluidState
   ) {
      return !this.isMatchingAndStill(fluidState)
         && this.receivesFlow(face, world, pos, state, fromPos, fromState)
         && this.canFill(world, fromPos, fromState, fluid);
   }

   private boolean isMatchingAndStill(FluidState state) {
      return state.getFluid().matchesType(this) && state.isStill();
   }

   protected abstract int getFlowSpeed(WorldView world);

   private int method_15740(WorldView world, BlockPos pos) {
      int _snowman = 0;

      for (Direction _snowmanx : Direction.Type.HORIZONTAL) {
         BlockPos _snowmanxx = pos.offset(_snowmanx);
         FluidState _snowmanxxx = world.getFluidState(_snowmanxx);
         if (this.isMatchingAndStill(_snowmanxxx)) {
            _snowman++;
         }
      }

      return _snowman;
   }

   protected Map<Direction, FluidState> getSpread(WorldView world, BlockPos pos, BlockState state) {
      int _snowman = 1000;
      Map<Direction, FluidState> _snowmanx = Maps.newEnumMap(Direction.class);
      Short2ObjectMap<Pair<BlockState, FluidState>> _snowmanxx = new Short2ObjectOpenHashMap();
      Short2BooleanMap _snowmanxxx = new Short2BooleanOpenHashMap();

      for (Direction _snowmanxxxx : Direction.Type.HORIZONTAL) {
         BlockPos _snowmanxxxxx = pos.offset(_snowmanxxxx);
         short _snowmanxxxxxx = method_15747(pos, _snowmanxxxxx);
         Pair<BlockState, FluidState> _snowmanxxxxxxx = (Pair<BlockState, FluidState>)_snowmanxx.computeIfAbsent(_snowmanxxxxxx, _snowmanxxxxxxxx -> {
            BlockState _snowmanxxxxxxxxx = world.getBlockState(_snowman);
            return Pair.of(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.getFluidState());
         });
         BlockState _snowmanxxxxxxxx = (BlockState)_snowmanxxxxxxx.getFirst();
         FluidState _snowmanxxxxxxxxx = (FluidState)_snowmanxxxxxxx.getSecond();
         FluidState _snowmanxxxxxxxxxx = this.getUpdatedState(world, _snowmanxxxxx, _snowmanxxxxxxxx);
         if (this.canFlowThrough(world, _snowmanxxxxxxxxxx.getFluid(), pos, state, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx)) {
            BlockPos _snowmanxxxxxxxxxxx = _snowmanxxxxx.down();
            boolean _snowmanxxxxxxxxxxxx = _snowmanxxx.computeIfAbsent(_snowmanxxxxxx, _snowmanxxxxxxxxxxxxx -> {
               BlockState _snowmanxxxxxxxxxxxxxx = world.getBlockState(_snowman);
               return this.method_15736(world, this.getFlowing(), _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxxxx);
            });
            int _snowmanxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxx = 0;
            } else {
               _snowmanxxxxxxxxxxxxx = this.method_15742(world, _snowmanxxxxx, 1, _snowmanxxxx.getOpposite(), _snowmanxxxxxxxx, pos, _snowmanxx, _snowmanxxx);
            }

            if (_snowmanxxxxxxxxxxxxx < _snowman) {
               _snowmanx.clear();
            }

            if (_snowmanxxxxxxxxxxxxx <= _snowman) {
               _snowmanx.put(_snowmanxxxx, _snowmanxxxxxxxxxx);
               _snowman = _snowmanxxxxxxxxxxxxx;
            }
         }
      }

      return _snowmanx;
   }

   private boolean canFill(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
      Block _snowman = state.getBlock();
      if (_snowman instanceof FluidFillable) {
         return ((FluidFillable)_snowman).canFillWithFluid(world, pos, state, fluid);
      } else if (!(_snowman instanceof DoorBlock) && !_snowman.isIn(BlockTags.SIGNS) && _snowman != Blocks.LADDER && _snowman != Blocks.SUGAR_CANE && _snowman != Blocks.BUBBLE_COLUMN) {
         Material _snowmanx = state.getMaterial();
         return _snowmanx != Material.PORTAL && _snowmanx != Material.STRUCTURE_VOID && _snowmanx != Material.UNDERWATER_PLANT && _snowmanx != Material.REPLACEABLE_UNDERWATER_PLANT
            ? !_snowmanx.blocksMovement()
            : false;
      } else {
         return false;
      }
   }

   protected boolean canFlow(
      BlockView world,
      BlockPos fluidPos,
      BlockState fluidBlockState,
      Direction flowDirection,
      BlockPos flowTo,
      BlockState flowToBlockState,
      FluidState _snowman,
      Fluid _snowman
   ) {
      return _snowman.canBeReplacedWith(world, flowTo, _snowman, flowDirection)
         && this.receivesFlow(flowDirection, world, fluidPos, fluidBlockState, flowTo, flowToBlockState)
         && this.canFill(world, flowTo, flowToBlockState, _snowman);
   }

   protected abstract int getLevelDecreasePerBlock(WorldView world);

   protected int getNextTickDelay(World world, BlockPos pos, FluidState oldState, FluidState newState) {
      return this.getTickRate(world);
   }

   @Override
   public void onScheduledTick(World world, BlockPos pos, FluidState state) {
      if (!state.isStill()) {
         FluidState _snowman = this.getUpdatedState(world, pos, world.getBlockState(pos));
         int _snowmanx = this.getNextTickDelay(world, pos, state, _snowman);
         if (_snowman.isEmpty()) {
            state = _snowman;
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
         } else if (!_snowman.equals(state)) {
            state = _snowman;
            BlockState _snowmanxx = _snowman.getBlockState();
            world.setBlockState(pos, _snowmanxx, 2);
            world.getFluidTickScheduler().schedule(pos, _snowman.getFluid(), _snowmanx);
            world.updateNeighborsAlways(pos, _snowmanxx.getBlock());
         }
      }

      this.tryFlow(world, pos, state);
   }

   protected static int method_15741(FluidState state) {
      return state.isStill() ? 0 : 8 - Math.min(state.getLevel(), 8) + (state.get(FALLING) ? 8 : 0);
   }

   private static boolean isFluidAboveEqual(FluidState state, BlockView world, BlockPos pos) {
      return state.getFluid().matchesType(world.getFluidState(pos.up()).getFluid());
   }

   @Override
   public float getHeight(FluidState state, BlockView world, BlockPos pos) {
      return isFluidAboveEqual(state, world, pos) ? 1.0F : state.getHeight();
   }

   @Override
   public float getHeight(FluidState state) {
      return (float)state.getLevel() / 9.0F;
   }

   @Override
   public VoxelShape getShape(FluidState state, BlockView world, BlockPos pos) {
      return state.getLevel() == 9 && isFluidAboveEqual(state, world, pos)
         ? VoxelShapes.fullCube()
         : this.shapeCache.computeIfAbsent(state, _snowmanxx -> VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, (double)_snowmanxx.getHeight(world, pos), 1.0));
   }
}
