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
      Object2ByteLinkedOpenHashMap<Block.NeighborGroup> object2ByteLinkedOpenHashMap = new Object2ByteLinkedOpenHashMap<Block.NeighborGroup>(200) {
         protected void rehash(int i) {
         }
      };
      object2ByteLinkedOpenHashMap.defaultReturnValue((byte)127);
      return object2ByteLinkedOpenHashMap;
   });
   private final Map<FluidState, VoxelShape> shapeCache = Maps.newIdentityHashMap();

   public FlowableFluid() {
   }

   @Override
   protected void appendProperties(StateManager.Builder<Fluid, FluidState> arg) {
      arg.add(FALLING);
   }

   @Override
   public Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state) {
      double d = 0.0;
      double e = 0.0;
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (Direction lv2 : Direction.Type.HORIZONTAL) {
         lv.set(pos, lv2);
         FluidState lv3 = world.getFluidState(lv);
         if (this.method_15748(lv3)) {
            float f = lv3.getHeight();
            float g = 0.0F;
            if (f == 0.0F) {
               if (!world.getBlockState(lv).getMaterial().blocksMovement()) {
                  BlockPos lv4 = lv.down();
                  FluidState lv5 = world.getFluidState(lv4);
                  if (this.method_15748(lv5)) {
                     f = lv5.getHeight();
                     if (f > 0.0F) {
                        g = state.getHeight() - (f - 0.8888889F);
                     }
                  }
               }
            } else if (f > 0.0F) {
               g = state.getHeight() - f;
            }

            if (g != 0.0F) {
               d += (double)((float)lv2.getOffsetX() * g);
               e += (double)((float)lv2.getOffsetZ() * g);
            }
         }
      }

      Vec3d lv6 = new Vec3d(d, 0.0, e);
      if (state.get(FALLING)) {
         for (Direction lv7 : Direction.Type.HORIZONTAL) {
            lv.set(pos, lv7);
            if (this.method_15749(world, lv, lv7) || this.method_15749(world, lv.up(), lv7)) {
               lv6 = lv6.normalize().add(0.0, -6.0, 0.0);
               break;
            }
         }
      }

      return lv6.normalize();
   }

   private boolean method_15748(FluidState state) {
      return state.isEmpty() || state.getFluid().matchesType(this);
   }

   protected boolean method_15749(BlockView world, BlockPos pos, Direction arg3) {
      BlockState lv = world.getBlockState(pos);
      FluidState lv2 = world.getFluidState(pos);
      if (lv2.getFluid().matchesType(this)) {
         return false;
      } else if (arg3 == Direction.UP) {
         return true;
      } else {
         return lv.getMaterial() == Material.ICE ? false : lv.isSideSolidFullSquare(world, pos, arg3);
      }
   }

   protected void tryFlow(WorldAccess world, BlockPos fluidPos, FluidState state) {
      if (!state.isEmpty()) {
         BlockState lv = world.getBlockState(fluidPos);
         BlockPos lv2 = fluidPos.down();
         BlockState lv3 = world.getBlockState(lv2);
         FluidState lv4 = this.getUpdatedState(world, lv2, lv3);
         if (this.canFlow(world, fluidPos, lv, Direction.DOWN, lv2, lv3, world.getFluidState(lv2), lv4.getFluid())) {
            this.flow(world, lv2, lv3, Direction.DOWN, lv4);
            if (this.method_15740(world, fluidPos) >= 3) {
               this.method_15744(world, fluidPos, state, lv);
            }
         } else if (state.isStill() || !this.method_15736(world, lv4.getFluid(), fluidPos, lv, lv2, lv3)) {
            this.method_15744(world, fluidPos, state, lv);
         }
      }
   }

   private void method_15744(WorldAccess world, BlockPos pos, FluidState fluidState, BlockState blockState) {
      int i = fluidState.getLevel() - this.getLevelDecreasePerBlock(world);
      if (fluidState.get(FALLING)) {
         i = 7;
      }

      if (i > 0) {
         Map<Direction, FluidState> map = this.getSpread(world, pos, blockState);

         for (Entry<Direction, FluidState> entry : map.entrySet()) {
            Direction lv = entry.getKey();
            FluidState lv2 = entry.getValue();
            BlockPos lv3 = pos.offset(lv);
            BlockState lv4 = world.getBlockState(lv3);
            if (this.canFlow(world, pos, blockState, lv, lv3, lv4, world.getFluidState(lv3), lv2.getFluid())) {
               this.flow(world, lv3, lv4, lv, lv2);
            }
         }
      }
   }

   protected FluidState getUpdatedState(WorldView world, BlockPos pos, BlockState state) {
      int i = 0;
      int j = 0;

      for (Direction lv : Direction.Type.HORIZONTAL) {
         BlockPos lv2 = pos.offset(lv);
         BlockState lv3 = world.getBlockState(lv2);
         FluidState lv4 = lv3.getFluidState();
         if (lv4.getFluid().matchesType(this) && this.receivesFlow(lv, world, pos, state, lv2, lv3)) {
            if (lv4.isStill()) {
               j++;
            }

            i = Math.max(i, lv4.getLevel());
         }
      }

      if (this.isInfinite() && j >= 2) {
         BlockState lv5 = world.getBlockState(pos.down());
         FluidState lv6 = lv5.getFluidState();
         if (lv5.getMaterial().isSolid() || this.isMatchingAndStill(lv6)) {
            return this.getStill(false);
         }
      }

      BlockPos lv7 = pos.up();
      BlockState lv8 = world.getBlockState(lv7);
      FluidState lv9 = lv8.getFluidState();
      if (!lv9.isEmpty() && lv9.getFluid().matchesType(this) && this.receivesFlow(Direction.UP, world, pos, state, lv7, lv8)) {
         return this.getFlowing(8, true);
      } else {
         int k = i - this.getLevelDecreasePerBlock(world);
         return k <= 0 ? Fluids.EMPTY.getDefaultState() : this.getFlowing(k, false);
      }
   }

   private boolean receivesFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
      Object2ByteLinkedOpenHashMap<Block.NeighborGroup> object2ByteLinkedOpenHashMap2;
      if (!state.getBlock().hasDynamicBounds() && !fromState.getBlock().hasDynamicBounds()) {
         object2ByteLinkedOpenHashMap2 = field_15901.get();
      } else {
         object2ByteLinkedOpenHashMap2 = null;
      }

      Block.NeighborGroup lv;
      if (object2ByteLinkedOpenHashMap2 != null) {
         lv = new Block.NeighborGroup(state, fromState, face);
         byte b = object2ByteLinkedOpenHashMap2.getAndMoveToFirst(lv);
         if (b != 127) {
            return b != 0;
         }
      } else {
         lv = null;
      }

      VoxelShape lv3 = state.getCollisionShape(world, pos);
      VoxelShape lv4 = fromState.getCollisionShape(world, fromPos);
      boolean bl = !VoxelShapes.adjacentSidesCoverSquare(lv3, lv4, face);
      if (object2ByteLinkedOpenHashMap2 != null) {
         if (object2ByteLinkedOpenHashMap2.size() == 200) {
            object2ByteLinkedOpenHashMap2.removeLastByte();
         }

         object2ByteLinkedOpenHashMap2.putAndMoveToFirst(lv, (byte)(bl ? 1 : 0));
      }

      return bl;
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

   private static short method_15747(BlockPos arg, BlockPos arg2) {
      int i = arg2.getX() - arg.getX();
      int j = arg2.getZ() - arg.getZ();
      return (short)((i + 128 & 0xFF) << 8 | j + 128 & 0xFF);
   }

   protected int method_15742(
      WorldView world,
      BlockPos arg2,
      int i,
      Direction arg3,
      BlockState arg4,
      BlockPos arg5,
      Short2ObjectMap<Pair<BlockState, FluidState>> short2ObjectMap,
      Short2BooleanMap short2BooleanMap
   ) {
      int j = 1000;

      for (Direction lv : Direction.Type.HORIZONTAL) {
         if (lv != arg3) {
            BlockPos lv2 = arg2.offset(lv);
            short s = method_15747(arg5, lv2);
            Pair<BlockState, FluidState> pair = (Pair<BlockState, FluidState>)short2ObjectMap.computeIfAbsent(s, ix -> {
               BlockState lvx = world.getBlockState(lv2);
               return Pair.of(lvx, lvx.getFluidState());
            });
            BlockState lv3 = (BlockState)pair.getFirst();
            FluidState lv4 = (FluidState)pair.getSecond();
            if (this.canFlowThrough(world, this.getFlowing(), arg2, arg4, lv, lv2, lv3, lv4)) {
               boolean bl = short2BooleanMap.computeIfAbsent(s, ix -> {
                  BlockPos lvx = lv2.down();
                  BlockState lv2x = world.getBlockState(lvx);
                  return this.method_15736(world, this.getFlowing(), lv2, lv3, lvx, lv2x);
               });
               if (bl) {
                  return i;
               }

               if (i < this.getFlowSpeed(world)) {
                  int k = this.method_15742(world, lv2, i + 1, lv.getOpposite(), lv3, arg5, short2ObjectMap, short2BooleanMap);
                  if (k < j) {
                     j = k;
                  }
               }
            }
         }
      }

      return j;
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
      int i = 0;

      for (Direction lv : Direction.Type.HORIZONTAL) {
         BlockPos lv2 = pos.offset(lv);
         FluidState lv3 = world.getFluidState(lv2);
         if (this.isMatchingAndStill(lv3)) {
            i++;
         }
      }

      return i;
   }

   protected Map<Direction, FluidState> getSpread(WorldView world, BlockPos pos, BlockState state) {
      int i = 1000;
      Map<Direction, FluidState> map = Maps.newEnumMap(Direction.class);
      Short2ObjectMap<Pair<BlockState, FluidState>> short2ObjectMap = new Short2ObjectOpenHashMap();
      Short2BooleanMap short2BooleanMap = new Short2BooleanOpenHashMap();

      for (Direction lv : Direction.Type.HORIZONTAL) {
         BlockPos lv2 = pos.offset(lv);
         short s = method_15747(pos, lv2);
         Pair<BlockState, FluidState> pair = (Pair<BlockState, FluidState>)short2ObjectMap.computeIfAbsent(s, ix -> {
            BlockState lvx = world.getBlockState(lv2);
            return Pair.of(lvx, lvx.getFluidState());
         });
         BlockState lv3 = (BlockState)pair.getFirst();
         FluidState lv4 = (FluidState)pair.getSecond();
         FluidState lv5 = this.getUpdatedState(world, lv2, lv3);
         if (this.canFlowThrough(world, lv5.getFluid(), pos, state, lv, lv2, lv3, lv4)) {
            BlockPos lv6 = lv2.down();
            boolean bl = short2BooleanMap.computeIfAbsent(s, ix -> {
               BlockState lvx = world.getBlockState(lv6);
               return this.method_15736(world, this.getFlowing(), lv2, lv3, lv6, lvx);
            });
            int j;
            if (bl) {
               j = 0;
            } else {
               j = this.method_15742(world, lv2, 1, lv.getOpposite(), lv3, pos, short2ObjectMap, short2BooleanMap);
            }

            if (j < i) {
               map.clear();
            }

            if (j <= i) {
               map.put(lv, lv5);
               i = j;
            }
         }
      }

      return map;
   }

   private boolean canFill(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
      Block lv = state.getBlock();
      if (lv instanceof FluidFillable) {
         return ((FluidFillable)lv).canFillWithFluid(world, pos, state, fluid);
      } else if (!(lv instanceof DoorBlock) && !lv.isIn(BlockTags.SIGNS) && lv != Blocks.LADDER && lv != Blocks.SUGAR_CANE && lv != Blocks.BUBBLE_COLUMN) {
         Material lv2 = state.getMaterial();
         return lv2 != Material.PORTAL && lv2 != Material.STRUCTURE_VOID && lv2 != Material.UNDERWATER_PLANT && lv2 != Material.REPLACEABLE_UNDERWATER_PLANT
            ? !lv2.blocksMovement()
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
      FluidState arg7,
      Fluid arg8
   ) {
      return arg7.canBeReplacedWith(world, flowTo, arg8, flowDirection)
         && this.receivesFlow(flowDirection, world, fluidPos, fluidBlockState, flowTo, flowToBlockState)
         && this.canFill(world, flowTo, flowToBlockState, arg8);
   }

   protected abstract int getLevelDecreasePerBlock(WorldView world);

   protected int getNextTickDelay(World world, BlockPos pos, FluidState oldState, FluidState newState) {
      return this.getTickRate(world);
   }

   @Override
   public void onScheduledTick(World world, BlockPos pos, FluidState state) {
      if (!state.isStill()) {
         FluidState lv = this.getUpdatedState(world, pos, world.getBlockState(pos));
         int i = this.getNextTickDelay(world, pos, state, lv);
         if (lv.isEmpty()) {
            state = lv;
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
         } else if (!lv.equals(state)) {
            state = lv;
            BlockState lv2 = lv.getBlockState();
            world.setBlockState(pos, lv2, 2);
            world.getFluidTickScheduler().schedule(pos, lv.getFluid(), i);
            world.updateNeighborsAlways(pos, lv2.getBlock());
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
         : this.shapeCache.computeIfAbsent(state, arg3 -> VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, (double)arg3.getHeight(world, pos), 1.0));
   }
}
