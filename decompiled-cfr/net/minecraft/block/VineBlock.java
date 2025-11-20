/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class VineBlock
extends Block {
    public static final BooleanProperty UP = ConnectingBlock.UP;
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter(entry -> entry.getKey() != Direction.DOWN).collect(Util.toMap());
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> field_26659;

    public VineBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(UP, false)).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false));
        this.field_26659 = ImmutableMap.copyOf(this.stateManager.getStates().stream().collect(Collectors.toMap(Function.identity(), VineBlock::method_31018)));
    }

    private static VoxelShape method_31018(BlockState blockState) {
        VoxelShape voxelShape = VoxelShapes.empty();
        if (blockState.get(UP).booleanValue()) {
            voxelShape = UP_SHAPE;
        }
        if (blockState.get(NORTH).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, SOUTH_SHAPE);
        }
        if (blockState.get(SOUTH).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, NORTH_SHAPE);
        }
        if (blockState.get(EAST).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, WEST_SHAPE);
        }
        if (blockState.get(WEST).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, EAST_SHAPE);
        }
        return voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.field_26659.get(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.hasAdjacentBlocks(this.getPlacementShape(state, world, pos));
    }

    private boolean hasAdjacentBlocks(BlockState state) {
        return this.getAdjacentBlockCount(state) > 0;
    }

    private int getAdjacentBlockCount(BlockState state) {
        int n = 0;
        for (BooleanProperty booleanProperty : FACING_PROPERTIES.values()) {
            if (!state.get(booleanProperty).booleanValue()) continue;
            ++n;
        }
        return n;
    }

    private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
        if (side == Direction.DOWN) {
            return false;
        }
        BlockPos blockPos = pos.offset(side);
        if (VineBlock.shouldConnectTo(world, blockPos, side)) {
            return true;
        }
        if (side.getAxis() != Direction.Axis.Y) {
            BooleanProperty booleanProperty = FACING_PROPERTIES.get(side);
            BlockState _snowman2 = world.getBlockState(pos.up());
            return _snowman2.isOf(this) && _snowman2.get(booleanProperty) != false;
        }
        return false;
    }

    public static boolean shouldConnectTo(BlockView world, BlockPos pos, Direction direction) {
        BlockState blockState = world.getBlockState(pos);
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos), direction.getOpposite());
    }

    private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        if (state.get(UP).booleanValue()) {
            state = (BlockState)state.with(UP, VineBlock.shouldConnectTo(world, blockPos, Direction.DOWN));
        }
        AbstractBlock.AbstractBlockState _snowman2 = null;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BooleanProperty booleanProperty = VineBlock.getFacingProperty(direction);
            if (!state.get(booleanProperty).booleanValue()) continue;
            boolean _snowman3 = this.shouldHaveSide(world, pos, direction);
            if (!_snowman3) {
                if (_snowman2 == null) {
                    _snowman2 = world.getBlockState(blockPos);
                }
                _snowman3 = _snowman2.isOf(this) && _snowman2.get(booleanProperty) != false;
            }
            state = (BlockState)state.with(booleanProperty, _snowman3);
        }
        return state;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction == Direction.DOWN) {
            return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
        }
        BlockState blockState = this.getPlacementShape(state, world, pos);
        if (!this.hasAdjacentBlocks(blockState)) {
            return Blocks.AIR.getDefaultState();
        }
        return blockState;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.random.nextInt(4) != 0) {
            return;
        }
        Direction direction = Direction.random(random);
        BlockPos _snowman2 = pos.up();
        if (direction.getAxis().isHorizontal() && !state.get(VineBlock.getFacingProperty(direction)).booleanValue()) {
            if (!this.canGrowAt(world, pos)) {
                return;
            }
            BlockPos blockPos = pos.offset(direction);
            BlockState _snowman3 = world.getBlockState(blockPos);
            if (_snowman3.isAir()) {
                Direction direction2 = direction.rotateYClockwise();
                _snowman = direction.rotateYCounterclockwise();
                boolean _snowman4 = state.get(VineBlock.getFacingProperty(direction2));
                boolean _snowman5 = state.get(VineBlock.getFacingProperty(_snowman));
                BlockPos _snowman6 = blockPos.offset(direction2);
                BlockPos _snowman7 = blockPos.offset(_snowman);
                if (_snowman4 && VineBlock.shouldConnectTo(world, _snowman6, direction2)) {
                    world.setBlockState(blockPos, (BlockState)this.getDefaultState().with(VineBlock.getFacingProperty(direction2), true), 2);
                } else if (_snowman5 && VineBlock.shouldConnectTo(world, _snowman7, _snowman)) {
                    world.setBlockState(blockPos, (BlockState)this.getDefaultState().with(VineBlock.getFacingProperty(_snowman), true), 2);
                } else {
                    _snowman = direction.getOpposite();
                    if (_snowman4 && world.isAir(_snowman6) && VineBlock.shouldConnectTo(world, pos.offset(direction2), _snowman)) {
                        world.setBlockState(_snowman6, (BlockState)this.getDefaultState().with(VineBlock.getFacingProperty(_snowman), true), 2);
                    } else if (_snowman5 && world.isAir(_snowman7) && VineBlock.shouldConnectTo(world, pos.offset(_snowman), _snowman)) {
                        world.setBlockState(_snowman7, (BlockState)this.getDefaultState().with(VineBlock.getFacingProperty(_snowman), true), 2);
                    } else if ((double)world.random.nextFloat() < 0.05 && VineBlock.shouldConnectTo(world, blockPos.up(), Direction.UP)) {
                        world.setBlockState(blockPos, (BlockState)this.getDefaultState().with(UP, true), 2);
                    }
                }
            } else if (VineBlock.shouldConnectTo(world, blockPos, direction)) {
                world.setBlockState(pos, (BlockState)state.with(VineBlock.getFacingProperty(direction), true), 2);
            }
            return;
        }
        if (direction == Direction.UP && pos.getY() < 255) {
            if (this.shouldHaveSide(world, pos, direction)) {
                world.setBlockState(pos, (BlockState)state.with(UP, true), 2);
                return;
            }
            if (world.isAir(_snowman2)) {
                if (!this.canGrowAt(world, pos)) {
                    return;
                }
                BlockState _snowman8 = state;
                for (Direction direction3 : Direction.Type.HORIZONTAL) {
                    if (!random.nextBoolean() && VineBlock.shouldConnectTo(world, _snowman2.offset(direction3), Direction.UP)) continue;
                    _snowman8 = (BlockState)_snowman8.with(VineBlock.getFacingProperty(direction3), false);
                }
                if (this.hasHorizontalSide(_snowman8)) {
                    world.setBlockState(_snowman2, _snowman8, 2);
                }
                return;
            }
        }
        if (pos.getY() > 0 && ((_snowman = world.getBlockState(_snowman = pos.down())).isAir() || _snowman.isOf(this)) && (_snowman = _snowman.isAir() ? this.getDefaultState() : _snowman) != (_snowman = this.getGrownState(state, _snowman, random)) && this.hasHorizontalSide(_snowman)) {
            world.setBlockState(_snowman, _snowman, 2);
        }
    }

    private BlockState getGrownState(BlockState above, BlockState state, Random random) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (!random.nextBoolean() || !above.get(_snowman = VineBlock.getFacingProperty(direction)).booleanValue()) continue;
            state = (BlockState)state.with(_snowman, true);
        }
        return state;
    }

    private boolean hasHorizontalSide(BlockState state) {
        return state.get(NORTH) != false || state.get(EAST) != false || state.get(SOUTH) != false || state.get(WEST) != false;
    }

    private boolean canGrowAt(BlockView world, BlockPos pos) {
        int n = 4;
        Iterable<BlockPos> _snowman2 = BlockPos.iterate(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
        _snowman = 5;
        for (BlockPos blockPos : _snowman2) {
            if (!world.getBlockState(blockPos).isOf(this) || --_snowman > 0) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.isOf(this)) {
            return this.getAdjacentBlockCount(blockState) < FACING_PROPERTIES.size();
        }
        return super.canReplace(state, context);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState;
        BlockState blockState2 = ctx.getWorld().getBlockState(ctx.getBlockPos());
        boolean _snowman2 = blockState2.isOf(this);
        blockState = _snowman2 ? blockState2 : this.getDefaultState();
        for (Direction direction : ctx.getPlacementDirections()) {
            if (direction == Direction.DOWN) continue;
            BooleanProperty booleanProperty = VineBlock.getFacingProperty(direction);
            boolean bl = _snowman = _snowman2 && blockState2.get(booleanProperty) != false;
            if (_snowman || !this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), direction)) continue;
            return (BlockState)blockState.with(booleanProperty, true);
        }
        return _snowman2 ? blockState : null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(SOUTH))).with(EAST, state.get(WEST))).with(SOUTH, state.get(NORTH))).with(WEST, state.get(EAST));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(EAST))).with(EAST, state.get(SOUTH))).with(SOUTH, state.get(WEST))).with(WEST, state.get(NORTH));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(WEST))).with(EAST, state.get(NORTH))).with(SOUTH, state.get(EAST))).with(WEST, state.get(SOUTH));
            }
        }
        return state;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)state.with(NORTH, state.get(SOUTH))).with(SOUTH, state.get(NORTH));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)state.with(EAST, state.get(WEST))).with(WEST, state.get(EAST));
            }
        }
        return super.mirror(state, mirror);
    }

    public static BooleanProperty getFacingProperty(Direction direction) {
        return FACING_PROPERTIES.get(direction);
    }
}

