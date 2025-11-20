/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class RepeaterBlock
extends AbstractRedstoneGateBlock {
    public static final BooleanProperty LOCKED = Properties.LOCKED;
    public static final IntProperty DELAY = Properties.DELAY;

    protected RepeaterBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(DELAY, 1)).with(LOCKED, false)).with(POWERED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.abilities.allowModifyWorld) {
            return ActionResult.PASS;
        }
        world.setBlockState(pos, (BlockState)state.cycle(DELAY), 3);
        return ActionResult.success(world.isClient);
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return state.get(DELAY) * 2;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        return (BlockState)blockState.with(LOCKED, this.isLocked(ctx.getWorld(), ctx.getBlockPos(), blockState));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (!world.isClient() && direction.getAxis() != state.get(FACING).getAxis()) {
            return (BlockState)state.with(LOCKED, this.isLocked(world, pos, state));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean isLocked(WorldView worldView, BlockPos pos, BlockState state) {
        return this.getMaxInputLevelSides(worldView, pos, state) > 0;
    }

    @Override
    protected boolean isValidInput(BlockState state) {
        return RepeaterBlock.isRedstoneGate(state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(POWERED).booleanValue()) {
            return;
        }
        Direction direction = state.get(FACING);
        double _snowman2 = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
        double _snowman3 = (double)pos.getY() + 0.4 + (random.nextDouble() - 0.5) * 0.2;
        double _snowman4 = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
        float _snowman5 = -5.0f;
        if (random.nextBoolean()) {
            _snowman5 = state.get(DELAY) * 2 - 1;
        }
        double _snowman6 = (_snowman5 /= 16.0f) * (float)direction.getOffsetX();
        double _snowman7 = _snowman5 * (float)direction.getOffsetZ();
        world.addParticle(DustParticleEffect.RED, _snowman2 + _snowman6, _snowman3, _snowman4 + _snowman7, 0.0, 0.0, 0.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, DELAY, LOCKED, POWERED);
    }
}

