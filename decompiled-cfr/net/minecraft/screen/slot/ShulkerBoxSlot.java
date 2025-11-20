/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen.slot;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ShulkerBoxSlot
extends Slot {
    public ShulkerBoxSlot(Inventory inventory, int n, int n2, int n3) {
        super(inventory, n, n2, n3);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }
}

