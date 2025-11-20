/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class Generic3x3ContainerScreenHandler
extends ScreenHandler {
    private final Inventory inventory;

    public Generic3x3ContainerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    public Generic3x3ContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlerType.GENERIC_3X3, syncId);
        int n;
        Generic3x3ContainerScreenHandler.checkSize(inventory, 9);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 3; ++_snowman) {
                this.addSlot(new Slot(inventory, _snowman + n * 3, 62 + _snowman * 18, 17 + n * 18));
            }
        }
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + n * 9 + 9, 8 + _snowman * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            if (index < 9 ? !this.insertItem(_snowman, 9, 45, true) : !this.insertItem(_snowman, 0, 9, false)) {
                return ItemStack.EMPTY;
            }
            if (_snowman.isEmpty()) {
                _snowman2.setStack(ItemStack.EMPTY);
            } else {
                _snowman2.markDirty();
            }
            if (_snowman.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            _snowman2.onTakeItem(player, _snowman);
        }
        return itemStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
}

