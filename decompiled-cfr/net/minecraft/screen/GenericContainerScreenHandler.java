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

public class GenericContainerScreenHandler
extends ScreenHandler {
    private final Inventory inventory;
    private final int rows;

    private GenericContainerScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, int rows) {
        this(type, syncId, playerInventory, new SimpleInventory(9 * rows), rows);
    }

    public static GenericContainerScreenHandler createGeneric9x1(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, 1);
    }

    public static GenericContainerScreenHandler createGeneric9x2(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X2, syncId, playerInventory, 2);
    }

    public static GenericContainerScreenHandler createGeneric9x3(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, 3);
    }

    public static GenericContainerScreenHandler createGeneric9x4(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, 4);
    }

    public static GenericContainerScreenHandler createGeneric9x5(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X5, syncId, playerInventory, 5);
    }

    public static GenericContainerScreenHandler createGeneric9x6(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X6, syncId, playerInventory, 6);
    }

    public static GenericContainerScreenHandler createGeneric9x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, inventory, 3);
    }

    public static GenericContainerScreenHandler createGeneric9x6(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X6, syncId, playerInventory, inventory, 6);
    }

    public GenericContainerScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
        super(type, syncId);
        GenericContainerScreenHandler.checkSize(inventory, rows * 9);
        this.inventory = inventory;
        this.rows = rows;
        inventory.onOpen(playerInventory.player);
        int n = (this.rows - 4) * 18;
        for (_snowman = 0; _snowman < this.rows; ++_snowman) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(inventory, _snowman + _snowman * 9, 8 + _snowman * 18, 18 + _snowman * 18));
            }
        }
        for (_snowman = 0; _snowman < 3; ++_snowman) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + _snowman * 9 + 9, 8 + _snowman * 18, 103 + _snowman * 18 + n));
            }
        }
        for (_snowman = 0; _snowman < 9; ++_snowman) {
            this.addSlot(new Slot(playerInventory, _snowman, 8 + _snowman * 18, 161 + n));
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
            if (index < this.rows * 9 ? !this.insertItem(_snowman, this.rows * 9, this.slots.size(), true) : !this.insertItem(_snowman, 0, this.rows * 9, false)) {
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

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getRows() {
        return this.rows;
    }
}

