/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class SlabBlock
extends Block
implements Waterloggable {
    public static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

    public SlabBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, false));
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != SlabType.DOUBLE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        switch (slabType) {
            case DOUBLE: {
                return VoxelShapes.fullCube();
            }
            case TOP: {
                return TOP_SHAPE;
            }
        }
        return BOTTOM_SHAPE;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState _snowman2 = ctx.getWorld().getBlockState(blockPos);
        if (_snowman2.isOf(this)) {
            return (BlockState)((BlockState)_snowman2.with(TYPE, SlabType.DOUBLE)).with(WATERLOGGED, false);
        }
        FluidState _snowman3 = ctx.getWorld().getFluidState(blockPos);
        BlockState _snowman4 = (BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, _snowman3.getFluid() == Fluids.WATER);
        Direction _snowman5 = ctx.getSide();
        if (_snowman5 == Direction.DOWN || _snowman5 != Direction.UP && ctx.getHitPos().y - (double)blockPos.getY() > 0.5) {
            return (BlockState)_snowman4.with(TYPE, SlabType.TOP);
        }
        return _snowman4;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType _snowman2 = state.get(TYPE);
        if (_snowman2 == SlabType.DOUBLE || itemStack.getItem() != this.asItem()) {
            return false;
        }
        if (context.canReplaceExisting()) {
            boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5;
            Direction _snowman3 = context.getSide();
            if (_snowman2 == SlabType.BOTTOM) {
                return _snowman3 == Direction.UP || bl && _snowman3.getAxis().isHorizontal();
            }
            return _snowman3 == Direction.DOWN || !bl && _snowman3.getAxis().isHorizontal();
        }
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(TYPE) != SlabType.DOUBLE) {
            return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
        }
        return false;
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        if (state.get(TYPE) != SlabType.DOUBLE) {
            return Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
        }
        return false;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch (type) {
            case LAND: {
                return false;
            }
            case WATER: {
                return world.getFluidState(pos).isIn(FluidTags.WATER);
            }
            case AIR: {
                return false;
            }
        }
        return false;
    }
}

