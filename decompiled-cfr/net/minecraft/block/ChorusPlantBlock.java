/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ChorusPlantBlock
extends ConnectingBlock {
    protected ChorusPlantBlock(AbstractBlock.Settings settings) {
        super(0.3125f, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(UP, false)).with(DOWN, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.withConnectionProperties(ctx.getWorld(), ctx.getBlockPos());
    }

    public BlockState withConnectionProperties(BlockView world, BlockPos pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        _snowman = world.getBlockState(pos.up()).getBlock();
        _snowman = world.getBlockState(pos.north()).getBlock();
        _snowman = world.getBlockState(pos.east()).getBlock();
        _snowman = world.getBlockState(pos.south()).getBlock();
        _snowman = world.getBlockState(pos.west()).getBlock();
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(DOWN, block == this || block == Blocks.CHORUS_FLOWER || block == Blocks.END_STONE)).with(UP, _snowman == this || _snowman == Blocks.CHORUS_FLOWER)).with(NORTH, _snowman == this || _snowman == Blocks.CHORUS_FLOWER)).with(EAST, _snowman == this || _snowman == Blocks.CHORUS_FLOWER)).with(SOUTH, _snowman == this || _snowman == Blocks.CHORUS_FLOWER)).with(WEST, _snowman == this || _snowman == Blocks.CHORUS_FLOWER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (!state.canPlaceAt(world, pos)) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
            return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
        }
        boolean bl = newState.getBlock() == this || newState.isOf(Blocks.CHORUS_FLOWER) || direction == Direction.DOWN && newState.isOf(Blocks.END_STONE);
        return (BlockState)state.with((Property)FACING_PROPERTIES.get(direction), bl);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        boolean _snowman2 = !world.getBlockState(pos.up()).isAir() && !blockState.isAir();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            Block _snowman3 = world.getBlockState(blockPos).getBlock();
            if (_snowman3 != this) continue;
            if (_snowman2) {
                return false;
            }
            Block _snowman4 = world.getBlockState(blockPos.down()).getBlock();
            if (_snowman4 != this && _snowman4 != Blocks.END_STONE) continue;
            return true;
        }
        Block block = blockState.getBlock();
        return block == this || block == Blocks.END_STONE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

