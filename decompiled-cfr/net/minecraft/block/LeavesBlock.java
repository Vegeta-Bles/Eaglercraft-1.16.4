/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class LeavesBlock
extends Block {
    public static final IntProperty DISTANCE = Properties.DISTANCE_1_7;
    public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

    public LeavesBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(DISTANCE, 7)).with(PERSISTENT, false));
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(DISTANCE) == 7 && state.get(PERSISTENT) == false;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(PERSISTENT).booleanValue() && state.get(DISTANCE) == 7) {
            LeavesBlock.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, LeavesBlock.updateDistanceFromLogs(state, world, pos), 3);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        int n = LeavesBlock.getDistanceFromLog(newState) + 1;
        if (n != 1 || state.get(DISTANCE) != n) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
        return state;
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        int n = 7;
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            _snowman2.set(pos, direction);
            n = Math.min(n, LeavesBlock.getDistanceFromLog(world.getBlockState(_snowman2)) + 1);
            if (n == 1) break;
        }
        return (BlockState)state.with(DISTANCE, n);
    }

    private static int getDistanceFromLog(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        }
        if (state.getBlock() instanceof LeavesBlock) {
            return state.get(DISTANCE);
        }
        return 7;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.hasRain(pos.up())) {
            return;
        }
        if (random.nextInt(15) != 1) {
            return;
        }
        BlockPos blockPos = pos.down();
        BlockState _snowman2 = world.getBlockState(blockPos);
        if (_snowman2.isOpaque() && _snowman2.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            return;
        }
        double _snowman3 = (double)pos.getX() + random.nextDouble();
        double _snowman4 = (double)pos.getY() - 0.05;
        double _snowman5 = (double)pos.getZ() + random.nextDouble();
        world.addParticle(ParticleTypes.DRIPPING_WATER, _snowman3, _snowman4, _snowman5, 0.0, 0.0, 0.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return LeavesBlock.updateDistanceFromLogs((BlockState)this.getDefaultState().with(PERSISTENT, true), ctx.getWorld(), ctx.getBlockPos());
    }
}

