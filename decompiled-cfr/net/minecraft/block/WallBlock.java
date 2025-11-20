/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WallBlock
extends Block
implements Waterloggable {
    public static final BooleanProperty UP = Properties.UP;
    public static final EnumProperty<WallShape> EAST_SHAPE = Properties.EAST_WALL_SHAPE;
    public static final EnumProperty<WallShape> NORTH_SHAPE = Properties.NORTH_WALL_SHAPE;
    public static final EnumProperty<WallShape> SOUTH_SHAPE = Properties.SOUTH_WALL_SHAPE;
    public static final EnumProperty<WallShape> WEST_SHAPE = Properties.WEST_WALL_SHAPE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private final Map<BlockState, VoxelShape> shapeMap;
    private final Map<BlockState, VoxelShape> collisionShapeMap;
    private static final VoxelShape TALL_POST_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape TALL_NORTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
    private static final VoxelShape TALL_SOUTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
    private static final VoxelShape TALL_WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape TALL_EAST_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

    public WallBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(UP, true)).with(NORTH_SHAPE, WallShape.NONE)).with(EAST_SHAPE, WallShape.NONE)).with(SOUTH_SHAPE, WallShape.NONE)).with(WEST_SHAPE, WallShape.NONE)).with(WATERLOGGED, false));
        this.shapeMap = this.getShapeMap(4.0f, 3.0f, 16.0f, 0.0f, 14.0f, 16.0f);
        this.collisionShapeMap = this.getShapeMap(4.0f, 3.0f, 24.0f, 0.0f, 24.0f, 24.0f);
    }

    private static VoxelShape method_24426(VoxelShape voxelShape, WallShape wallShape, VoxelShape voxelShape2, VoxelShape voxelShape3) {
        if (wallShape == WallShape.TALL) {
            return VoxelShapes.union(voxelShape, voxelShape3);
        }
        if (wallShape == WallShape.LOW) {
            return VoxelShapes.union(voxelShape, voxelShape2);
        }
        return voxelShape;
    }

    private Map<BlockState, VoxelShape> getShapeMap(float f, float f2, float f3, float f4, float f5, float f6) {
        _snowman = 8.0f - f;
        _snowman = 8.0f + f;
        _snowman = 8.0f - f2;
        _snowman = 8.0f + f2;
        VoxelShape voxelShape = Block.createCuboidShape(_snowman, 0.0, _snowman, _snowman, f3, _snowman);
        _snowman = Block.createCuboidShape(_snowman, f4, 0.0, _snowman, f5, _snowman);
        _snowman = Block.createCuboidShape(_snowman, f4, _snowman, _snowman, f5, 16.0);
        _snowman = Block.createCuboidShape(0.0, f4, _snowman, _snowman, f5, _snowman);
        _snowman = Block.createCuboidShape(_snowman, f4, _snowman, 16.0, f5, _snowman);
        _snowman = Block.createCuboidShape(_snowman, f4, 0.0, _snowman, f6, _snowman);
        _snowman = Block.createCuboidShape(_snowman, f4, _snowman, _snowman, f6, 16.0);
        _snowman = Block.createCuboidShape(0.0, f4, _snowman, _snowman, f6, _snowman);
        _snowman = Block.createCuboidShape(_snowman, f4, _snowman, 16.0, f6, _snowman);
        ImmutableMap.Builder _snowman2 = ImmutableMap.builder();
        for (Boolean bl : UP.getValues()) {
            for (WallShape wallShape : EAST_SHAPE.getValues()) {
                for (WallShape wallShape2 : NORTH_SHAPE.getValues()) {
                    for (WallShape wallShape3 : WEST_SHAPE.getValues()) {
                        for (WallShape wallShape4 : SOUTH_SHAPE.getValues()) {
                            VoxelShape voxelShape2 = VoxelShapes.empty();
                            voxelShape2 = WallBlock.method_24426(voxelShape2, wallShape, _snowman, _snowman);
                            voxelShape2 = WallBlock.method_24426(voxelShape2, wallShape3, _snowman, _snowman);
                            voxelShape2 = WallBlock.method_24426(voxelShape2, wallShape2, _snowman, _snowman);
                            voxelShape2 = WallBlock.method_24426(voxelShape2, wallShape4, _snowman, _snowman);
                            if (bl.booleanValue()) {
                                voxelShape2 = VoxelShapes.union(voxelShape2, voxelShape);
                            }
                            BlockState _snowman3 = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(UP, bl)).with(EAST_SHAPE, wallShape)).with(WEST_SHAPE, wallShape3)).with(NORTH_SHAPE, wallShape2)).with(SOUTH_SHAPE, wallShape4);
                            _snowman2.put(_snowman3.with(WATERLOGGED, false), (Object)voxelShape2);
                            _snowman2.put(_snowman3.with(WATERLOGGED, true), (Object)voxelShape2);
                        }
                    }
                }
            }
        }
        return _snowman2.build();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapeMap.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.collisionShapeMap.get(state);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    private boolean shouldConnectTo(BlockState state, boolean faceFullSquare, Direction side) {
        Block block = state.getBlock();
        boolean _snowman2 = block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, side);
        return state.isIn(BlockTags.WALLS) || !WallBlock.cannotConnect(block) && faceFullSquare || block instanceof PaneBlock || _snowman2;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos _snowman2 = ctx.getBlockPos();
        FluidState _snowman3 = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos _snowman4 = _snowman2.north();
        BlockPos _snowman5 = _snowman2.east();
        BlockPos _snowman6 = _snowman2.south();
        BlockPos _snowman7 = _snowman2.west();
        BlockPos _snowman8 = _snowman2.up();
        BlockState _snowman9 = world.getBlockState(_snowman4);
        BlockState _snowman10 = world.getBlockState(_snowman5);
        BlockState _snowman11 = world.getBlockState(_snowman6);
        BlockState _snowman12 = world.getBlockState(_snowman7);
        BlockState _snowman13 = world.getBlockState(_snowman8);
        boolean _snowman14 = this.shouldConnectTo(_snowman9, _snowman9.isSideSolidFullSquare(world, _snowman4, Direction.SOUTH), Direction.SOUTH);
        boolean _snowman15 = this.shouldConnectTo(_snowman10, _snowman10.isSideSolidFullSquare(world, _snowman5, Direction.WEST), Direction.WEST);
        boolean _snowman16 = this.shouldConnectTo(_snowman11, _snowman11.isSideSolidFullSquare(world, _snowman6, Direction.NORTH), Direction.NORTH);
        boolean _snowman17 = this.shouldConnectTo(_snowman12, _snowman12.isSideSolidFullSquare(world, _snowman7, Direction.EAST), Direction.EAST);
        BlockState _snowman18 = (BlockState)this.getDefaultState().with(WATERLOGGED, _snowman3.getFluid() == Fluids.WATER);
        return this.method_24422(world, _snowman18, _snowman8, _snowman13, _snowman14, _snowman15, _snowman16, _snowman17);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == Direction.DOWN) {
            return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
        }
        if (direction == Direction.UP) {
            return this.method_24421(world, state, posFrom, newState);
        }
        return this.method_24423(world, pos, state, posFrom, newState, direction);
    }

    private static boolean method_24424(BlockState blockState, Property<WallShape> property) {
        return blockState.get(property) != WallShape.NONE;
    }

    private static boolean method_24427(VoxelShape voxelShape, VoxelShape voxelShape2) {
        return !VoxelShapes.matchesAnywhere(voxelShape2, voxelShape, BooleanBiFunction.ONLY_FIRST);
    }

    private BlockState method_24421(WorldView worldView, BlockState blockState, BlockPos blockPos, BlockState blockState2) {
        boolean bl = WallBlock.method_24424(blockState, NORTH_SHAPE);
        _snowman = WallBlock.method_24424(blockState, EAST_SHAPE);
        _snowman = WallBlock.method_24424(blockState, SOUTH_SHAPE);
        _snowman = WallBlock.method_24424(blockState, WEST_SHAPE);
        return this.method_24422(worldView, blockState, blockPos, blockState2, bl, _snowman, _snowman, _snowman);
    }

    private BlockState method_24423(WorldView worldView, BlockPos blockPos, BlockState blockState, BlockPos blockPos2, BlockState blockState2, Direction direction) {
        _snowman = direction.getOpposite();
        boolean bl = direction == Direction.NORTH ? this.shouldConnectTo(blockState2, blockState2.isSideSolidFullSquare(worldView, blockPos2, _snowman), _snowman) : WallBlock.method_24424(blockState, NORTH_SHAPE);
        _snowman = direction == Direction.EAST ? this.shouldConnectTo(blockState2, blockState2.isSideSolidFullSquare(worldView, blockPos2, _snowman), _snowman) : WallBlock.method_24424(blockState, EAST_SHAPE);
        _snowman = direction == Direction.SOUTH ? this.shouldConnectTo(blockState2, blockState2.isSideSolidFullSquare(worldView, blockPos2, _snowman), _snowman) : WallBlock.method_24424(blockState, SOUTH_SHAPE);
        _snowman = direction == Direction.WEST ? this.shouldConnectTo(blockState2, blockState2.isSideSolidFullSquare(worldView, blockPos2, _snowman), _snowman) : WallBlock.method_24424(blockState, WEST_SHAPE);
        BlockPos _snowman2 = blockPos.up();
        BlockState _snowman3 = worldView.getBlockState(_snowman2);
        return this.method_24422(worldView, blockState, _snowman2, _snowman3, bl, _snowman, _snowman, _snowman);
    }

    private BlockState method_24422(WorldView worldView, BlockState blockState, BlockPos blockPos, BlockState blockState2, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        VoxelShape voxelShape = blockState2.getCollisionShape(worldView, blockPos).getFace(Direction.DOWN);
        BlockState _snowman2 = this.method_24425(blockState, bl, bl2, bl3, bl4, voxelShape);
        return (BlockState)_snowman2.with(UP, this.method_27092(_snowman2, blockState2, voxelShape));
    }

    private boolean method_27092(BlockState blockState, BlockState blockState2, VoxelShape voxelShape) {
        boolean bl = _snowman = blockState2.getBlock() instanceof WallBlock && blockState2.get(UP) != false;
        if (_snowman) {
            return true;
        }
        WallShape wallShape = blockState.get(NORTH_SHAPE);
        _snowman = blockState.get(SOUTH_SHAPE);
        _snowman = blockState.get(EAST_SHAPE);
        _snowman = blockState.get(WEST_SHAPE);
        boolean _snowman2 = _snowman == WallShape.NONE;
        boolean _snowman3 = _snowman == WallShape.NONE;
        boolean _snowman4 = _snowman == WallShape.NONE;
        boolean _snowman5 = wallShape == WallShape.NONE;
        boolean bl2 = _snowman = _snowman5 && _snowman2 && _snowman3 && _snowman4 || _snowman5 != _snowman2 || _snowman3 != _snowman4;
        if (_snowman) {
            return true;
        }
        boolean bl3 = _snowman = wallShape == WallShape.TALL && _snowman == WallShape.TALL || _snowman == WallShape.TALL && _snowman == WallShape.TALL;
        if (_snowman) {
            return false;
        }
        return blockState2.getBlock().isIn(BlockTags.WALL_POST_OVERRIDE) || WallBlock.method_24427(voxelShape, TALL_POST_SHAPE);
    }

    private BlockState method_24425(BlockState blockState, boolean bl, boolean bl2, boolean bl3, boolean bl4, VoxelShape voxelShape) {
        return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH_SHAPE, this.method_24428(bl, voxelShape, TALL_NORTH_SHAPE))).with(EAST_SHAPE, this.method_24428(bl2, voxelShape, TALL_EAST_SHAPE))).with(SOUTH_SHAPE, this.method_24428(bl3, voxelShape, TALL_SOUTH_SHAPE))).with(WEST_SHAPE, this.method_24428(bl4, voxelShape, TALL_WEST_SHAPE));
    }

    private WallShape method_24428(boolean bl, VoxelShape voxelShape, VoxelShape voxelShape2) {
        if (bl) {
            if (WallBlock.method_24427(voxelShape, voxelShape2)) {
                return WallShape.TALL;
            }
            return WallShape.LOW;
        }
        return WallShape.NONE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.get(WATERLOGGED) == false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH_SHAPE, EAST_SHAPE, WEST_SHAPE, SOUTH_SHAPE, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH_SHAPE, state.get(SOUTH_SHAPE))).with(EAST_SHAPE, state.get(WEST_SHAPE))).with(SOUTH_SHAPE, state.get(NORTH_SHAPE))).with(WEST_SHAPE, state.get(EAST_SHAPE));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH_SHAPE, state.get(EAST_SHAPE))).with(EAST_SHAPE, state.get(SOUTH_SHAPE))).with(SOUTH_SHAPE, state.get(WEST_SHAPE))).with(WEST_SHAPE, state.get(NORTH_SHAPE));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH_SHAPE, state.get(WEST_SHAPE))).with(EAST_SHAPE, state.get(NORTH_SHAPE))).with(SOUTH_SHAPE, state.get(EAST_SHAPE))).with(WEST_SHAPE, state.get(SOUTH_SHAPE));
            }
        }
        return state;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)state.with(NORTH_SHAPE, state.get(SOUTH_SHAPE))).with(SOUTH_SHAPE, state.get(NORTH_SHAPE));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)state.with(EAST_SHAPE, state.get(WEST_SHAPE))).with(WEST_SHAPE, state.get(EAST_SHAPE));
            }
        }
        return super.mirror(state, mirror);
    }
}

