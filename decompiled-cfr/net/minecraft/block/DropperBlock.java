/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class DropperBlock
extends DispenserBlock {
    private static final DispenserBehavior BEHAVIOR = new ItemDispenserBehavior();

    public DropperBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected DispenserBehavior getBehaviorForItem(ItemStack stack) {
        return BEHAVIOR;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new DropperBlockEntity();
    }

    @Override
    protected void dispense(ServerWorld serverWorld, BlockPos pos) {
        ItemStack itemStack;
        BlockPointerImpl blockPointerImpl = new BlockPointerImpl(serverWorld, pos);
        DispenserBlockEntity _snowman2 = (DispenserBlockEntity)blockPointerImpl.getBlockEntity();
        int _snowman3 = _snowman2.chooseNonEmptySlot();
        if (_snowman3 < 0) {
            serverWorld.syncWorldEvent(1001, pos, 0);
            return;
        }
        ItemStack _snowman4 = _snowman2.getStack(_snowman3);
        if (_snowman4.isEmpty()) {
            return;
        }
        Direction _snowman5 = serverWorld.getBlockState(pos).get(FACING);
        Inventory _snowman6 = HopperBlockEntity.getInventoryAt(serverWorld, pos.offset(_snowman5));
        if (_snowman6 == null) {
            itemStack = BEHAVIOR.dispense(blockPointerImpl, _snowman4);
        } else {
            itemStack = HopperBlockEntity.transfer(_snowman2, _snowman6, _snowman4.copy().split(1), _snowman5.getOpposite());
            if (itemStack.isEmpty()) {
                itemStack = _snowman4.copy();
                itemStack.decrement(1);
            } else {
                itemStack = _snowman4.copy();
            }
        }
        _snowman2.setStack(_snowman3, itemStack);
    }
}

