/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TripwireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class TripwireHookBlock
extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty ATTACHED = Properties.ATTACHED;
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

    public TripwireHookBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(POWERED, false)).with(ATTACHED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            default: {
                return WEST_SHAPE;
            }
            case WEST: {
                return EAST_SHAPE;
            }
            case SOUTH: {
                return NORTH_SHAPE;
            }
            case NORTH: 
        }
        return SOUTH_SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos _snowman2 = pos.offset(direction.getOpposite());
        BlockState _snowman3 = world.getBlockState(_snowman2);
        return direction.getAxis().isHorizontal() && _snowman3.isSideSolidFullSquare(world, _snowman2, direction);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = (BlockState)((BlockState)this.getDefaultState().with(POWERED, false)).with(ATTACHED, false);
        World _snowman2 = ctx.getWorld();
        BlockPos _snowman3 = ctx.getBlockPos();
        for (Direction direction : _snowman = ctx.getPlacementDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = (BlockState)blockState.with(FACING, _snowman = direction.getOpposite())).canPlaceAt(_snowman2, _snowman3)) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        this.update(world, pos, state, false, false, -1, null);
    }

    public void update(World world, BlockPos pos, BlockState state, boolean beingRemoved, boolean bl, int n, @Nullable BlockState blockState) {
        Object _snowman8;
        BlockPos blockPos;
        Direction direction = state.get(FACING);
        boolean _snowman2 = state.get(ATTACHED);
        boolean _snowman3 = state.get(POWERED);
        boolean _snowman4 = !beingRemoved;
        boolean _snowman5 = false;
        int _snowman6 = 0;
        BlockState[] _snowman7 = new BlockState[42];
        for (int i = 1; i < 42; ++i) {
            blockPos = pos.offset(direction, i);
            _snowman8 = world.getBlockState(blockPos);
            if (((AbstractBlock.AbstractBlockState)_snowman8).isOf(Blocks.TRIPWIRE_HOOK)) {
                if (((State)_snowman8).get(FACING) != direction.getOpposite()) break;
                _snowman6 = i;
                break;
            }
            if (((AbstractBlock.AbstractBlockState)_snowman8).isOf(Blocks.TRIPWIRE) || i == n) {
                if (i == n) {
                    _snowman8 = (BlockState)MoreObjects.firstNonNull((Object)blockState, (Object)_snowman8);
                }
                boolean bl2 = ((State)_snowman8).get(TripwireBlock.DISARMED) == false;
                _snowman = ((State)_snowman8).get(TripwireBlock.POWERED);
                _snowman5 |= bl2 && _snowman;
                _snowman7[i] = _snowman8;
                if (i != n) continue;
                world.getBlockTickScheduler().schedule(pos, this, 10);
                _snowman4 &= bl2;
                continue;
            }
            _snowman7[i] = null;
            _snowman4 = false;
        }
        BlockState blockState2 = (BlockState)((BlockState)this.getDefaultState().with(ATTACHED, _snowman4)).with(POWERED, _snowman5 &= (_snowman4 &= _snowman6 > 1));
        if (_snowman6 > 0) {
            blockPos = pos.offset(direction, _snowman6);
            _snowman8 = direction.getOpposite();
            world.setBlockState(blockPos, (BlockState)blockState2.with(FACING, _snowman8), 3);
            this.updateNeighborsOnAxis(world, blockPos, (Direction)_snowman8);
            this.playSound(world, blockPos, _snowman4, _snowman5, _snowman2, _snowman3);
        }
        this.playSound(world, pos, _snowman4, _snowman5, _snowman2, _snowman3);
        if (!beingRemoved) {
            world.setBlockState(pos, (BlockState)blockState2.with(FACING, direction), 3);
            if (bl) {
                this.updateNeighborsOnAxis(world, pos, direction);
            }
        }
        if (_snowman2 != _snowman4) {
            for (int i = 1; i < _snowman6; ++i) {
                _snowman8 = pos.offset(direction, i);
                BlockState blockState3 = _snowman7[i];
                if (blockState3 == null) continue;
                world.setBlockState((BlockPos)_snowman8, (BlockState)blockState3.with(ATTACHED, _snowman4), 3);
                if (world.getBlockState((BlockPos)_snowman8).isAir()) continue;
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.update(world, pos, state, false, true, -1, null);
    }

    private void playSound(World world, BlockPos pos, boolean attached, boolean on, boolean detached, boolean off) {
        if (on && !off) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 0.4f, 0.6f);
        } else if (!on && off) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF, SoundCategory.BLOCKS, 0.4f, 0.5f);
        } else if (attached && !detached) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_ATTACH, SoundCategory.BLOCKS, 0.4f, 0.7f);
        } else if (!attached && detached) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_DETACH, SoundCategory.BLOCKS, 0.4f, 1.2f / (world.random.nextFloat() * 0.2f + 0.9f));
        }
    }

    private void updateNeighborsOnAxis(World world, BlockPos pos, Direction direction) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(direction.getOpposite()), this);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved || state.isOf(newState.getBlock())) {
            return;
        }
        boolean bl = state.get(ATTACHED);
        _snowman = state.get(POWERED);
        if (bl || _snowman) {
            this.update(world, pos, state, true, false, -1, null);
        }
        if (_snowman) {
            world.updateNeighborsAlways(pos, this);
            world.updateNeighborsAlways(pos.offset(state.get(FACING).getOpposite()), this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) != false ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (!state.get(POWERED).booleanValue()) {
            return 0;
        }
        if (state.get(FACING) == direction) {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, ATTACHED);
    }
}

