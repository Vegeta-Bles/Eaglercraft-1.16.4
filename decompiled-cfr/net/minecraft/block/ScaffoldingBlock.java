/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ScaffoldingBlock
extends Block
implements Waterloggable {
    private static final VoxelShape NORMAL_OUTLINE_SHAPE;
    private static final VoxelShape BOTTOM_OUTLINE_SHAPE;
    private static final VoxelShape COLLISION_SHAPE;
    private static final VoxelShape OUTLINE_SHAPE;
    public static final IntProperty DISTANCE;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty BOTTOM;

    protected ScaffoldingBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(DISTANCE, 7)).with(WATERLOGGED, false)).with(BOTTOM, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, WATERLOGGED, BOTTOM);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!context.isHolding(state.getBlock().asItem())) {
            return state.get(BOTTOM) != false ? BOTTOM_OUTLINE_SHAPE : NORMAL_OUTLINE_SHAPE;
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() == this.asItem();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World _snowman2 = ctx.getWorld();
        int _snowman3 = ScaffoldingBlock.calculateDistance(_snowman2, blockPos);
        return (BlockState)((BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, _snowman2.getFluidState(blockPos).getFluid() == Fluids.WATER)).with(DISTANCE, _snowman3)).with(BOTTOM, this.shouldBeBottom(_snowman2, blockPos, _snowman3));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (!world.isClient()) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int n = ScaffoldingBlock.calculateDistance(world, pos);
        BlockState _snowman2 = (BlockState)((BlockState)state.with(DISTANCE, n)).with(BOTTOM, this.shouldBeBottom(world, pos, n));
        if (_snowman2.get(DISTANCE) == 7) {
            if (state.get(DISTANCE) == 7) {
                world.spawnEntity(new FallingBlockEntity(world, (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, (BlockState)_snowman2.with(WATERLOGGED, false)));
            } else {
                world.breakBlock(pos, true);
            }
        } else if (state != _snowman2) {
            world.setBlockState(pos, _snowman2, 3);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return ScaffoldingBlock.calculateDistance(world, pos) < 7;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!context.isAbove(VoxelShapes.fullCube(), pos, true) || context.isDescending()) {
            if (state.get(DISTANCE) != 0 && state.get(BOTTOM).booleanValue() && context.isAbove(OUTLINE_SHAPE, pos, true)) {
                return COLLISION_SHAPE;
            }
            return VoxelShapes.empty();
        }
        return NORMAL_OUTLINE_SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    private boolean shouldBeBottom(BlockView world, BlockPos pos, int distance) {
        return distance > 0 && !world.getBlockState(pos.down()).isOf(this);
    }

    public static int calculateDistance(BlockView world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.DOWN);
        BlockState _snowman2 = world.getBlockState(mutable);
        int _snowman3 = 7;
        if (_snowman2.isOf(Blocks.SCAFFOLDING)) {
            _snowman3 = _snowman2.get(DISTANCE);
        } else if (_snowman2.isSideSolidFullSquare(world, mutable, Direction.UP)) {
            return 0;
        }
        Iterator<Direction> iterator = Direction.Type.HORIZONTAL.iterator();
        while (iterator.hasNext() && (!(_snowman = world.getBlockState(mutable.set(pos, _snowman = iterator.next()))).isOf(Blocks.SCAFFOLDING) || (_snowman3 = Math.min(_snowman3, _snowman.get(DISTANCE) + 1)) != 1)) {
        }
        return _snowman3;
    }

    static {
        COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
        OUTLINE_SHAPE = VoxelShapes.fullCube().offset(0.0, -1.0, 0.0);
        DISTANCE = Properties.DISTANCE_0_7;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM = Properties.BOTTOM;
        VoxelShape voxelShape = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        _snowman = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
        _snowman = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
        _snowman = Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
        _snowman = Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
        NORMAL_OUTLINE_SHAPE = VoxelShapes.union(voxelShape, _snowman, _snowman, _snowman, _snowman);
        _snowman = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
        _snowman = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
        _snowman = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
        _snowman = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
        BOTTOM_OUTLINE_SHAPE = VoxelShapes.union(COLLISION_SHAPE, NORMAL_OUTLINE_SHAPE, _snowman, _snowman, _snowman, _snowman);
    }
}

