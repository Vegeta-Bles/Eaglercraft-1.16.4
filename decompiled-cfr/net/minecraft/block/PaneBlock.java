/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalConnectingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class PaneBlock
extends HorizontalConnectingBlock {
    protected PaneBlock(AbstractBlock.Settings settings) {
        super(1.0f, 1.0f, 16.0f, 16.0f, 16.0f, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos _snowman2 = ctx.getBlockPos();
        FluidState _snowman3 = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos _snowman4 = _snowman2.north();
        BlockPos _snowman5 = _snowman2.south();
        BlockPos _snowman6 = _snowman2.west();
        BlockPos _snowman7 = _snowman2.east();
        BlockState _snowman8 = world.getBlockState(_snowman4);
        BlockState _snowman9 = world.getBlockState(_snowman5);
        BlockState _snowman10 = world.getBlockState(_snowman6);
        BlockState _snowman11 = world.getBlockState(_snowman7);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(NORTH, this.connectsTo(_snowman8, _snowman8.isSideSolidFullSquare(world, _snowman4, Direction.SOUTH)))).with(SOUTH, this.connectsTo(_snowman9, _snowman9.isSideSolidFullSquare(world, _snowman5, Direction.NORTH)))).with(WEST, this.connectsTo(_snowman10, _snowman10.isSideSolidFullSquare(world, _snowman6, Direction.EAST)))).with(EAST, this.connectsTo(_snowman11, _snowman11.isSideSolidFullSquare(world, _snowman7, Direction.WEST)))).with(WATERLOGGED, _snowman3.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction.getAxis().isHorizontal()) {
            return (BlockState)state.with((Property)FACING_PROPERTIES.get(direction), this.connectsTo(newState, newState.isSideSolidFullSquare(world, posFrom, direction.getOpposite())));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            if (!direction.getAxis().isHorizontal()) {
                return true;
            }
            if (((Boolean)state.get((Property)FACING_PROPERTIES.get(direction))).booleanValue() && ((Boolean)stateFrom.get((Property)FACING_PROPERTIES.get(direction.getOpposite()))).booleanValue()) {
                return true;
            }
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    public final boolean connectsTo(BlockState state, boolean bl) {
        Block block = state.getBlock();
        return !PaneBlock.cannotConnect(block) && bl || block instanceof PaneBlock || block.isIn(BlockTags.WALLS);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}

