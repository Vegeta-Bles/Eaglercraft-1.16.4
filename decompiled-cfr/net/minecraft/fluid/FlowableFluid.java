/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.shorts.Short2BooleanMap
 *  it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap
 */
package net.minecraft.fluid;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
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

public abstract class FlowableFluid
extends Fluid {
    public static final BooleanProperty FALLING = Properties.FALLING;
    public static final IntProperty LEVEL = Properties.LEVEL_1_8;
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.NeighborGroup>> field_15901 = ThreadLocal.withInitial(() -> {
        Object2ByteLinkedOpenHashMap<Block.NeighborGroup> object2ByteLinkedOpenHashMap = new Object2ByteLinkedOpenHashMap<Block.NeighborGroup>(200){

            protected void rehash(int n) {
            }
        };
        object2ByteLinkedOpenHashMap.defaultReturnValue((byte)127);
        return object2ByteLinkedOpenHashMap;
    });
    private final Map<FluidState, VoxelShape> shapeCache = Maps.newIdentityHashMap();

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        builder.add(FALLING);
    }

    @Override
    public Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state) {
        double d = 0.0;
        _snowman = 0.0;
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            _snowman2.set(pos, direction);
            Object object = world.getFluidState(_snowman2);
            if (!this.method_15748((FluidState)object)) continue;
            float _snowman3 = ((FluidState)object).getHeight();
            float _snowman4 = 0.0f;
            if (_snowman3 == 0.0f) {
                if (!world.getBlockState(_snowman2).getMaterial().blocksMovement() && this.method_15748(_snowman = world.getFluidState((BlockPos)(_snowman = _snowman2.down()))) && (_snowman3 = _snowman.getHeight()) > 0.0f) {
                    _snowman4 = state.getHeight() - (_snowman3 - 0.8888889f);
                }
            } else if (_snowman3 > 0.0f) {
                _snowman4 = state.getHeight() - _snowman3;
            }
            if (_snowman4 == 0.0f) continue;
            d += (double)((float)direction.getOffsetX() * _snowman4);
            _snowman += (double)((float)direction.getOffsetZ() * _snowman4);
        }
        Vec3d vec3d = new Vec3d(d, 0.0, _snowman);
        if (state.get(FALLING).booleanValue()) {
            for (Object object : Direction.Type.HORIZONTAL) {
                _snowman2.set(pos, (Direction)object);
                if (!this.method_15749(world, _snowman2, (Direction)object) && !this.method_15749(world, (BlockPos)_snowman2.up(), (Direction)object)) continue;
                vec3d = vec3d.normalize().add(0.0, -6.0, 0.0);
                break;
            }
        }
        return vec3d.normalize();
    }

    private boolean method_15748(FluidState state) {
        return state.isEmpty() || state.getFluid().matchesType(this);
    }

    protected boolean method_15749(BlockView world, BlockPos pos, Direction direction) {
        BlockState blockState = world.getBlockState(pos);
        FluidState _snowman2 = world.getFluidState(pos);
        if (_snowman2.getFluid().matchesType(this)) {
            return false;
        }
        if (direction == Direction.UP) {
            return true;
        }
        if (blockState.getMaterial() == Material.ICE) {
            return false;
        }
        return blockState.isSideSolidFullSquare(world, pos, direction);
    }

    protected void tryFlow(WorldAccess world, BlockPos fluidPos, FluidState state) {
        if (state.isEmpty()) {
            return;
        }
        BlockState blockState = world.getBlockState(fluidPos);
        BlockPos _snowman2 = fluidPos.down();
        _snowman = world.getBlockState(_snowman2);
        FluidState _snowman3 = this.getUpdatedState(world, _snowman2, _snowman);
        if (this.canFlow(world, fluidPos, blockState, Direction.DOWN, _snowman2, _snowman, world.getFluidState(_snowman2), _snowman3.getFluid())) {
            this.flow(world, _snowman2, _snowman, Direction.DOWN, _snowman3);
            if (this.method_15740(world, fluidPos) >= 3) {
                this.method_15744(world, fluidPos, state, blockState);
            }
        } else if (state.isStill() || !this.method_15736(world, _snowman3.getFluid(), fluidPos, blockState, _snowman2, _snowman)) {
            this.method_15744(world, fluidPos, state, blockState);
        }
    }

    private void method_15744(WorldAccess world, BlockPos pos, FluidState fluidState, BlockState blockState) {
        int n = fluidState.getLevel() - this.getLevelDecreasePerBlock(world);
        if (fluidState.get(FALLING).booleanValue()) {
            n = 7;
        }
        if (n <= 0) {
            return;
        }
        Map<Direction, FluidState> _snowman2 = this.getSpread(world, pos, blockState);
        for (Map.Entry<Direction, FluidState> entry : _snowman2.entrySet()) {
            Direction direction = entry.getKey();
            FluidState _snowman3 = entry.getValue();
            BlockPos _snowman4 = pos.offset(direction);
            if (!this.canFlow(world, pos, blockState, direction, _snowman4, _snowman = world.getBlockState(_snowman4), world.getFluidState(_snowman4), _snowman3.getFluid())) continue;
            this.flow(world, _snowman4, _snowman, direction, _snowman3);
        }
    }

    protected FluidState getUpdatedState(WorldView world, BlockPos pos, BlockState state) {
        Object object;
        int n;
        int _snowman3 = 0;
        n = 0;
        for (Object object2 : Direction.Type.HORIZONTAL) {
            object = pos.offset((Direction)object2);
            BlockState blockState = world.getBlockState((BlockPos)object);
            FluidState _snowman2 = blockState.getFluidState();
            if (!_snowman2.getFluid().matchesType(this) || !this.receivesFlow((Direction)object2, world, pos, state, (BlockPos)object, blockState)) continue;
            if (_snowman2.isStill()) {
                ++n;
            }
            _snowman3 = Math.max(_snowman3, _snowman2.getLevel());
        }
        if (this.isInfinite() && n >= 2) {
            Object object3 = world.getBlockState(pos.down());
            object2 = ((AbstractBlock.AbstractBlockState)object3).getFluidState();
            if (((AbstractBlock.AbstractBlockState)object3).getMaterial().isSolid() || this.isMatchingAndStill((FluidState)object2)) {
                return this.getStill(false);
            }
        }
        if (!((FluidState)(object = ((AbstractBlock.AbstractBlockState)(object2 = world.getBlockState((BlockPos)(object3 = pos.up())))).getFluidState())).isEmpty() && ((FluidState)object).getFluid().matchesType(this) && this.receivesFlow(Direction.UP, world, pos, state, (BlockPos)object3, (BlockState)object2)) {
            return this.getFlowing(8, true);
        }
        int _snowman4 = _snowman3 - this.getLevelDecreasePerBlock(world);
        if (_snowman4 <= 0) {
            return Fluids.EMPTY.getDefaultState();
        }
        return this.getFlowing(_snowman4, false);
    }

    private boolean receivesFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
        Object2ByteLinkedOpenHashMap<Block.NeighborGroup> object2ByteLinkedOpenHashMap = state.getBlock().hasDynamicBounds() || fromState.getBlock().hasDynamicBounds() ? null : field_15901.get();
        if (object2ByteLinkedOpenHashMap != null) {
            Block.NeighborGroup neighborGroup = new Block.NeighborGroup(state, fromState, face);
            byte _snowman2 = object2ByteLinkedOpenHashMap.getAndMoveToFirst((Object)neighborGroup);
            if (_snowman2 != 127) {
                return _snowman2 != 0;
            }
        } else {
            neighborGroup = null;
        }
        boolean bl = _snowman = !VoxelShapes.adjacentSidesCoverSquare(_snowman = state.getCollisionShape(world, pos), _snowman = fromState.getCollisionShape(world, fromPos), face);
        if (object2ByteLinkedOpenHashMap != null) {
            if (object2ByteLinkedOpenHashMap.size() == 200) {
                object2ByteLinkedOpenHashMap.removeLastByte();
            }
            object2ByteLinkedOpenHashMap.putAndMoveToFirst((Object)neighborGroup, (byte)(_snowman ? 1 : 0));
        }
        return _snowman;
    }

    public abstract Fluid getFlowing();

    public FluidState getFlowing(int level, boolean falling) {
        return (FluidState)((FluidState)this.getFlowing().getDefaultState().with(LEVEL, level)).with(FALLING, falling);
    }

    public abstract Fluid getStill();

    public FluidState getStill(boolean falling) {
        return (FluidState)this.getStill().getDefaultState().with(FALLING, falling);
    }

    protected abstract boolean isInfinite();

    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        if (state.getBlock() instanceof FluidFillable) {
            ((FluidFillable)((Object)state.getBlock())).tryFillWithFluid(world, pos, state, fluidState);
        } else {
            if (!state.isAir()) {
                this.beforeBreakingBlock(world, pos, state);
            }
            world.setBlockState(pos, fluidState.getBlockState(), 3);
        }
    }

    protected abstract void beforeBreakingBlock(WorldAccess var1, BlockPos var2, BlockState var3);

    private static short method_15747(BlockPos blockPos, BlockPos blockPos2) {
        int n = blockPos2.getX() - blockPos.getX();
        _snowman = blockPos2.getZ() - blockPos.getZ();
        return (short)((n + 128 & 0xFF) << 8 | _snowman + 128 & 0xFF);
    }

    protected int method_15742(WorldView world, BlockPos blockPos, int n2, Direction direction, BlockState blockState, BlockPos blockPos2, Short2ObjectMap<Pair<BlockState, FluidState>> short2ObjectMap, Short2BooleanMap short2BooleanMap) {
        int _snowman7 = 1000;
        for (Direction direction2 : Direction.Type.HORIZONTAL) {
            if (direction2 == direction) continue;
            BlockPos blockPos3 = blockPos.offset(direction2);
            short _snowman2 = FlowableFluid.method_15747(blockPos2, blockPos3);
            Pair _snowman3 = (Pair)short2ObjectMap.computeIfAbsent(_snowman2, n -> {
                BlockState blockState = world.getBlockState(blockPos3);
                return Pair.of((Object)blockState, (Object)blockState.getFluidState());
            });
            BlockState _snowman4 = (BlockState)_snowman3.getFirst();
            FluidState _snowman5 = (FluidState)_snowman3.getSecond();
            if (!this.canFlowThrough(world, this.getFlowing(), blockPos, blockState, direction2, blockPos3, _snowman4, _snowman5)) continue;
            boolean _snowman6 = short2BooleanMap.computeIfAbsent(_snowman2, n -> {
                BlockPos blockPos2 = blockPos3.down();
                BlockState _snowman2 = world.getBlockState(blockPos2);
                return this.method_15736(world, this.getFlowing(), blockPos3, _snowman4, blockPos2, _snowman2);
            });
            if (_snowman6) {
                return n2;
            }
            if (n2 >= this.getFlowSpeed(world) || (_snowman = this.method_15742(world, blockPos3, n2 + 1, direction2.getOpposite(), _snowman4, blockPos2, short2ObjectMap, short2BooleanMap)) >= _snowman7) continue;
            _snowman7 = _snowman;
        }
        return _snowman7;
    }

    private boolean method_15736(BlockView world, Fluid fluid, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
        if (!this.receivesFlow(Direction.DOWN, world, pos, state, fromPos, fromState)) {
            return false;
        }
        if (fromState.getFluidState().getFluid().matchesType(this)) {
            return true;
        }
        return this.canFill(world, fromPos, fromState, fluid);
    }

    private boolean canFlowThrough(BlockView world, Fluid fluid, BlockPos pos, BlockState state, Direction face, BlockPos fromPos, BlockState fromState, FluidState fluidState) {
        return !this.isMatchingAndStill(fluidState) && this.receivesFlow(face, world, pos, state, fromPos, fromState) && this.canFill(world, fromPos, fromState, fluid);
    }

    private boolean isMatchingAndStill(FluidState state) {
        return state.getFluid().matchesType(this) && state.isStill();
    }

    protected abstract int getFlowSpeed(WorldView var1);

    private int method_15740(WorldView world, BlockPos pos) {
        int n = 0;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            FluidState _snowman2 = world.getFluidState(blockPos);
            if (!this.isMatchingAndStill(_snowman2)) continue;
            ++n;
        }
        return n;
    }

    protected Map<Direction, FluidState> getSpread(WorldView world, BlockPos pos, BlockState state) {
        int _snowman12 = 1000;
        EnumMap _snowman2 = Maps.newEnumMap(Direction.class);
        Short2ObjectOpenHashMap _snowman3 = new Short2ObjectOpenHashMap();
        Short2BooleanOpenHashMap _snowman4 = new Short2BooleanOpenHashMap();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            short _snowman5 = FlowableFluid.method_15747(pos, blockPos);
            Pair _snowman6 = (Pair)_snowman3.computeIfAbsent(_snowman5, n -> {
                BlockState blockState = world.getBlockState(blockPos);
                return Pair.of((Object)blockState, (Object)blockState.getFluidState());
            });
            BlockState _snowman7 = (BlockState)_snowman6.getFirst();
            FluidState _snowman8 = (FluidState)_snowman6.getSecond();
            FluidState _snowman9 = this.getUpdatedState(world, blockPos, _snowman7);
            if (!this.canFlowThrough(world, _snowman9.getFluid(), pos, state, direction, blockPos, _snowman7, _snowman8)) continue;
            _snowman = blockPos.down();
            boolean _snowman10 = _snowman4.computeIfAbsent(_snowman5, n -> {
                BlockState blockState2 = world.getBlockState(_snowman);
                return this.method_15736(world, this.getFlowing(), blockPos, _snowman7, _snowman, blockState2);
            });
            int _snowman11 = _snowman10 ? 0 : this.method_15742(world, blockPos, 1, direction.getOpposite(), _snowman7, pos, (Short2ObjectMap<Pair<BlockState, FluidState>>)_snowman3, (Short2BooleanMap)_snowman4);
            if (_snowman11 < _snowman12) {
                _snowman2.clear();
            }
            if (_snowman11 > _snowman12) continue;
            _snowman2.put(direction, _snowman9);
            _snowman12 = _snowman11;
        }
        return _snowman2;
    }

    private boolean canFill(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        Block block = state.getBlock();
        if (block instanceof FluidFillable) {
            return ((FluidFillable)((Object)block)).canFillWithFluid(world, pos, state, fluid);
        }
        if (block instanceof DoorBlock || block.isIn(BlockTags.SIGNS) || block == Blocks.LADDER || block == Blocks.SUGAR_CANE || block == Blocks.BUBBLE_COLUMN) {
            return false;
        }
        Material _snowman2 = state.getMaterial();
        if (_snowman2 == Material.PORTAL || _snowman2 == Material.STRUCTURE_VOID || _snowman2 == Material.UNDERWATER_PLANT || _snowman2 == Material.REPLACEABLE_UNDERWATER_PLANT) {
            return false;
        }
        return !_snowman2.blocksMovement();
    }

    protected boolean canFlow(BlockView world, BlockPos fluidPos, BlockState fluidBlockState, Direction flowDirection, BlockPos flowTo, BlockState flowToBlockState, FluidState fluidState, Fluid fluid) {
        return fluidState.canBeReplacedWith(world, flowTo, fluid, flowDirection) && this.receivesFlow(flowDirection, world, fluidPos, fluidBlockState, flowTo, flowToBlockState) && this.canFill(world, flowTo, flowToBlockState, fluid);
    }

    protected abstract int getLevelDecreasePerBlock(WorldView var1);

    protected int getNextTickDelay(World world, BlockPos pos, FluidState oldState, FluidState newState) {
        return this.getTickRate(world);
    }

    @Override
    public void onScheduledTick(World world, BlockPos pos, FluidState state) {
        if (!state.isStill()) {
            FluidState fluidState = this.getUpdatedState(world, pos, world.getBlockState(pos));
            int _snowman2 = this.getNextTickDelay(world, pos, state, fluidState);
            if (fluidState.isEmpty()) {
                state = fluidState;
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            } else if (!fluidState.equals(state)) {
                state = fluidState;
                BlockState blockState = state.getBlockState();
                world.setBlockState(pos, blockState, 2);
                world.getFluidTickScheduler().schedule(pos, state.getFluid(), _snowman2);
                world.updateNeighborsAlways(pos, blockState.getBlock());
            }
        }
        this.tryFlow(world, pos, state);
    }

    protected static int method_15741(FluidState state) {
        if (state.isStill()) {
            return 0;
        }
        return 8 - Math.min(state.getLevel(), 8) + (state.get(FALLING) != false ? 8 : 0);
    }

    private static boolean isFluidAboveEqual(FluidState state, BlockView world, BlockPos pos) {
        return state.getFluid().matchesType(world.getFluidState(pos.up()).getFluid());
    }

    @Override
    public float getHeight(FluidState state, BlockView world, BlockPos pos) {
        if (FlowableFluid.isFluidAboveEqual(state, world, pos)) {
            return 1.0f;
        }
        return state.getHeight();
    }

    @Override
    public float getHeight(FluidState state) {
        return (float)state.getLevel() / 9.0f;
    }

    @Override
    public VoxelShape getShape(FluidState state, BlockView world, BlockPos pos) {
        if (state.getLevel() == 9 && FlowableFluid.isFluidAboveEqual(state, world, pos)) {
            return VoxelShapes.fullCube();
        }
        return this.shapeCache.computeIfAbsent(state, fluidState -> VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, fluidState.getHeight(world, pos), 1.0));
    }
}

