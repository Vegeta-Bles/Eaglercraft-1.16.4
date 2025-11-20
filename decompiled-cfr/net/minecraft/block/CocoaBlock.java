/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CocoaBlock
extends HorizontalFacingBlock
implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_2;
    protected static final VoxelShape[] AGE_TO_EAST_SHAPE = new VoxelShape[]{Block.createCuboidShape(11.0, 7.0, 6.0, 15.0, 12.0, 10.0), Block.createCuboidShape(9.0, 5.0, 5.0, 15.0, 12.0, 11.0), Block.createCuboidShape(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)};
    protected static final VoxelShape[] AGE_TO_WEST_SHAPE = new VoxelShape[]{Block.createCuboidShape(1.0, 7.0, 6.0, 5.0, 12.0, 10.0), Block.createCuboidShape(1.0, 5.0, 5.0, 7.0, 12.0, 11.0), Block.createCuboidShape(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)};
    protected static final VoxelShape[] AGE_TO_NORTH_SHAPE = new VoxelShape[]{Block.createCuboidShape(6.0, 7.0, 1.0, 10.0, 12.0, 5.0), Block.createCuboidShape(5.0, 5.0, 1.0, 11.0, 12.0, 7.0), Block.createCuboidShape(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)};
    protected static final VoxelShape[] AGE_TO_SOUTH_SHAPE = new VoxelShape[]{Block.createCuboidShape(6.0, 7.0, 11.0, 10.0, 12.0, 15.0), Block.createCuboidShape(5.0, 5.0, 9.0, 11.0, 12.0, 15.0), Block.createCuboidShape(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)};

    public CocoaBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(AGE, 0));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int n;
        if (world.random.nextInt(5) == 0 && (n = state.get(AGE).intValue()) < 2) {
            world.setBlockState(pos, (BlockState)state.with(AGE, n + 1), 2);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Block block = world.getBlockState(pos.offset(state.get(FACING))).getBlock();
        return block.isIn(BlockTags.JUNGLE_LOGS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int n = state.get(AGE);
        switch (state.get(FACING)) {
            case SOUTH: {
                return AGE_TO_SOUTH_SHAPE[n];
            }
            default: {
                return AGE_TO_NORTH_SHAPE[n];
            }
            case WEST: {
                return AGE_TO_WEST_SHAPE[n];
            }
            case EAST: 
        }
        return AGE_TO_EAST_SHAPE[n];
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        World _snowman2 = ctx.getWorld();
        BlockPos _snowman3 = ctx.getBlockPos();
        for (Direction direction : ctx.getPlacementDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = (BlockState)blockState.with(FACING, direction)).canPlaceAt(_snowman2, _snowman3)) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction == state.get(FACING) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 2;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, (BlockState)state.with(AGE, state.get(AGE) + 1), 2);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

