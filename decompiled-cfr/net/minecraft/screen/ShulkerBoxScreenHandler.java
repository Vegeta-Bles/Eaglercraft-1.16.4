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
import net.minecraft.screen.slot.ShulkerBoxSlot;
import net.minecraft.screen.slot.Slot;

public class ShulkerBoxScreenHandler
extends ScreenHandler {
    private final Inventory inventory;

    public ShulkerBoxScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(27));
    }

    public ShulkerBoxScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlerType.SHULKER_BOX, syncId);
        ShulkerBoxScreenHandler.checkSize(inventory, 27);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        int n = 3;
        _snowman = 9;
        for (_snowman = 0; _snowman < 3; ++_snowman) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new ShulkerBoxSlot(inventory, _snowman + _snowman * 9, 8 + _snowman * 18, 18 + _snowman * 18));
            }
        }
        for (_snowman = 0; _snowman < 3; ++_snowman) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + _snowman * 9 + 9, 8 + _snowman * 18, 84 + _snowman * 18));
            }
        }
        for (_snowman = 0; _snowman < 9; ++_snowman) {
            this.addSlot(new Slot(playerInventory, _snowman, 8 + _snowman * 18, 142));
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
            if (index < this.inventory.size() ? !this.insertItem(_snowman, this.inventory.size(), this.slots.size(), true) : !this.insertItem(_snowman, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (_snowman.isEmpty()) {
                _snowman2.setStack(ItemStack.EMPTY);
            } else {
                _snowman2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
}

