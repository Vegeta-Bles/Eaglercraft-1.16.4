/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HorizontalConnectingBlock
extends Block
implements Waterloggable {
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter(entry -> ((Direction)entry.getKey()).getAxis().isHorizontal()).collect(Util.toMap());
    protected final VoxelShape[] collisionShapes;
    protected final VoxelShape[] boundingShapes;
    private final Object2IntMap<BlockState> SHAPE_INDEX_CACHE = new Object2IntOpenHashMap();

    protected HorizontalConnectingBlock(float radius1, float radius2, float boundingHeight1, float boundingHeight2, float collisionHeight, AbstractBlock.Settings settings) {
        super(settings);
        this.collisionShapes = this.createShapes(radius1, radius2, collisionHeight, 0.0f, collisionHeight);
        this.boundingShapes = this.createShapes(radius1, radius2, boundingHeight1, 0.0f, boundingHeight2);
        for (BlockState blockState : this.stateManager.getStates()) {
            this.getShapeIndex(blockState);
        }
    }

    protected VoxelShape[] createShapes(float radius1, float radius2, float height1, float offset2, float height2) {
        float f = 8.0f - radius1;
        _snowman = 8.0f + radius1;
        _snowman = 8.0f - radius2;
        _snowman = 8.0f + radius2;
        VoxelShape _snowman2 = Block.createCuboidShape(f, 0.0, f, _snowman, height1, _snowman);
        VoxelShape _snowman3 = Block.createCuboidShape(_snowman, offset2, 0.0, _snowman, height2, _snowman);
        VoxelShape _snowman4 = Block.createCuboidShape(_snowman, offset2, _snowman, _snowman, height2, 16.0);
        VoxelShape _snowman5 = Block.createCuboidShape(0.0, offset2, _snowman, _snowman, height2, _snowman);
        VoxelShape _snowman6 = Block.createCuboidShape(_snowman, offset2, _snowman, 16.0, height2, _snowman);
        VoxelShape _snowman7 = VoxelShapes.union(_snowman3, _snowman6);
        VoxelShape _snowman8 = VoxelShapes.union(_snowman4, _snowman5);
        VoxelShape[] _snowman9 = new VoxelShape[]{VoxelShapes.empty(), _snowman4, _snowman5, _snowman8, _snowman3, VoxelShapes.union(_snowman4, _snowman3), VoxelShapes.union(_snowman5, _snowman3), VoxelShapes.union(_snowman8, _snowman3), _snowman6, VoxelShapes.union(_snowman4, _snowman6), VoxelShapes.union(_snowman5, _snowman6), VoxelShapes.union(_snowman8, _snowman6), _snowman7, VoxelShapes.union(_snowman4, _snowman7), VoxelShapes.union(_snowman5, _snowman7), VoxelShapes.union(_snowman8, _snowman7)};
        for (int i = 0; i < 16; ++i) {
            _snowman9[i] = VoxelShapes.union(_snowman2, _snowman9[i]);
        }
        return _snowman9;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.get(WATERLOGGED) == false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.boundingShapes[this.getShapeIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.collisionShapes[this.getShapeIndex(state)];
    }

    private static int getDirectionMask(Direction dir) {
        return 1 << dir.getHorizontal();
    }

    protected int getShapeIndex(BlockState state) {
        return this.SHAPE_INDEX_CACHE.computeIntIfAbsent((Object)state, blockState -> {
            int n = 0;
            if (blockState.get(NORTH).booleanValue()) {
                n |= HorizontalConnectingBlock.getDirectionMask(Direction.NORTH);
            }
            if (blockState.get(EAST).booleanValue()) {
                n |= HorizontalConnectingBlock.getDirectionMask(Direction.EAST);
            }
            if (blockState.get(SOUTH).booleanValue()) {
                n |= HorizontalConnectingBlock.getDirectionMask(Direction.SOUTH);
            }
            if (blockState.get(WEST).booleanValue()) {
                n |= HorizontalConnectingBlock.getDirectionMask(Direction.WEST);
            }
            return n;
        });
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
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
}

