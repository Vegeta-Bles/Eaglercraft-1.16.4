/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ComparatorBlockEntity;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public class ComparatorBlock
extends AbstractRedstoneGateBlock
implements BlockEntityProvider {
    public static final EnumProperty<ComparatorMode> MODE = Properties.COMPARATOR_MODE;

    public ComparatorBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(POWERED, false)).with(MODE, ComparatorMode.COMPARE));
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 2;
    }

    @Override
    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ComparatorBlockEntity) {
            return ((ComparatorBlockEntity)blockEntity).getOutputSignal();
        }
        return 0;
    }

    private int calculateOutputSignal(World world, BlockPos pos, BlockState state) {
        if (state.get(MODE) == ComparatorMode.SUBTRACT) {
            return Math.max(this.getPower(world, pos, state) - this.getMaxInputLevelSides(world, pos, state), 0);
        }
        return this.getPower(world, pos, state);
    }

    @Override
    protected boolean hasPower(World world, BlockPos pos, BlockState state) {
        int n = this.getPower(world, pos, state);
        if (n == 0) {
            return false;
        }
        _snowman = this.getMaxInputLevelSides(world, pos, state);
        if (n > _snowman) {
            return true;
        }
        return n == _snowman && state.get(MODE) == ComparatorMode.COMPARE;
    }

    @Override
    protected int getPower(World world, BlockPos pos, BlockState state) {
        int n = super.getPower(world, pos, state);
        Direction _snowman2 = state.get(FACING);
        BlockPos _snowman3 = pos.offset(_snowman2);
        BlockState _snowman4 = world.getBlockState(_snowman3);
        if (_snowman4.hasComparatorOutput()) {
            n = _snowman4.getComparatorOutput(world, _snowman3);
        } else if (n < 15 && _snowman4.isSolidBlock(world, _snowman3)) {
            _snowman3 = _snowman3.offset(_snowman2);
            _snowman4 = world.getBlockState(_snowman3);
            ItemFrameEntity itemFrameEntity = this.getAttachedItemFrame(world, _snowman2, _snowman3);
            int _snowman5 = Math.max(itemFrameEntity == null ? Integer.MIN_VALUE : itemFrameEntity.getComparatorPower(), _snowman4.hasComparatorOutput() ? _snowman4.getComparatorOutput(world, _snowman3) : Integer.MIN_VALUE);
            if (_snowman5 != Integer.MIN_VALUE) {
                n = _snowman5;
            }
        }
        return n;
    }

    @Nullable
    private ItemFrameEntity getAttachedItemFrame(World world, Direction facing, BlockPos pos) {
        List<ItemFrameEntity> list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), itemFrameEntity -> itemFrameEntity != null && itemFrameEntity.getHorizontalFacing() == facing);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.abilities.allowModifyWorld) {
            return ActionResult.PASS;
        }
        float f = (state = (BlockState)state.cycle(MODE)).get(MODE) == ComparatorMode.SUBTRACT ? 0.55f : 0.5f;
        world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3f, f);
        world.setBlockState(pos, state, 2);
        this.update(world, pos, state);
        return ActionResult.success(world.isClient);
    }

    @Override
    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        if (world.getBlockTickScheduler().isTicking(pos, this)) {
            return;
        }
        int n = this.calculateOutputSignal(world, pos, state);
        BlockEntity _snowman2 = world.getBlockEntity(pos);
        int n2 = _snowman = _snowman2 instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)_snowman2).getOutputSignal() : 0;
        if (n != _snowman || state.get(POWERED).booleanValue() != this.hasPower(world, pos, state)) {
            TickPriority tickPriority = this.isTargetNotAligned(world, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
            world.getBlockTickScheduler().schedule(pos, this, 2, tickPriority);
        }
    }

    private void update(World world, BlockPos pos, BlockState state) {
        int _snowman3;
        int n = this.calculateOutputSignal(world, pos, state);
        BlockEntity _snowman2 = world.getBlockEntity(pos);
        _snowman3 = 0;
        if (_snowman2 instanceof ComparatorBlockEntity) {
            ComparatorBlockEntity comparatorBlockEntity = (ComparatorBlockEntity)_snowman2;
            _snowman3 = comparatorBlockEntity.getOutputSignal();
            comparatorBlockEntity.setOutputSignal(n);
        }
        if (_snowman3 != n || state.get(MODE) == ComparatorMode.COMPARE) {
            boolean _snowman4 = this.hasPower(world, pos, state);
            boolean _snowman5 = state.get(POWERED);
            if (_snowman5 && !_snowman4) {
                world.setBlockState(pos, (BlockState)state.with(POWERED, false), 2);
            } else if (!_snowman5 && _snowman4) {
                world.setBlockState(pos, (BlockState)state.with(POWERED, true), 2);
            }
            this.updateTarget(world, pos, state);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.update(world, pos, state);
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ComparatorBlockEntity();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE, POWERED);
    }
}

