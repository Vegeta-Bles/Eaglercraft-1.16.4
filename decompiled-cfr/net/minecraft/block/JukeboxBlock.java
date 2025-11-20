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
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class JukeboxBlock
extends BlockWithEntity {
    public static final BooleanProperty HAS_RECORD = Properties.HAS_RECORD;

    protected JukeboxBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HAS_RECORD, false));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (compoundTag.contains("BlockEntityTag") && (_snowman = compoundTag.getCompound("BlockEntityTag")).contains("RecordItem")) {
            world.setBlockState(pos, (BlockState)state.with(HAS_RECORD, true), 2);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(HAS_RECORD).booleanValue()) {
            this.removeRecord(world, pos);
            state = (BlockState)state.with(HAS_RECORD, false);
            world.setBlockState(pos, state, 2);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public void setRecord(WorldAccess world, BlockPos pos, BlockState state, ItemStack stack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof JukeboxBlockEntity)) {
            return;
        }
        ((JukeboxBlockEntity)blockEntity).setRecord(stack.copy());
        world.setBlockState(pos, (BlockState)state.with(HAS_RECORD, true), 2);
    }

    private void removeRecord(World world, BlockPos pos) {
        if (world.isClient) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof JukeboxBlockEntity)) {
            return;
        }
        JukeboxBlockEntity _snowman2 = (JukeboxBlockEntity)blockEntity;
        ItemStack _snowman3 = _snowman2.getRecord();
        if (_snowman3.isEmpty()) {
            return;
        }
        world.syncWorldEvent(1010, pos, 0);
        _snowman2.clear();
        float _snowman4 = 0.7f;
        double _snowman5 = (double)(world.random.nextFloat() * 0.7f) + (double)0.15f;
        double _snowman6 = (double)(world.random.nextFloat() * 0.7f) + 0.06000000238418579 + 0.6;
        double _snowman7 = (double)(world.random.nextFloat() * 0.7f) + (double)0.15f;
        ItemStack _snowman8 = _snowman3.copy();
        ItemEntity _snowman9 = new ItemEntity(world, (double)pos.getX() + _snowman5, (double)pos.getY() + _snowman6, (double)pos.getZ() + _snowman7, _snowman8);
        _snowman9.setToDefaultPickupDelay();
        world.spawnEntity(_snowman9);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        this.removeRecord(world, pos);
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new JukeboxBlockEntity();
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof JukeboxBlockEntity && (_snowman = ((JukeboxBlockEntity)blockEntity).getRecord().getItem()) instanceof MusicDiscItem) {
            return ((MusicDiscItem)_snowman).getComparatorOutput();
        }
        return 0;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HAS_RECORD);
    }
}

