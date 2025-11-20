/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.inventory;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.collection.DefaultedList;

public class Inventories {
    public static ItemStack splitStack(List<ItemStack> stacks, int slot, int amount) {
        if (slot < 0 || slot >= stacks.size() || stacks.get(slot).isEmpty() || amount <= 0) {
            return ItemStack.EMPTY;
        }
        return stacks.get(slot).split(amount);
    }

    public static ItemStack removeStack(List<ItemStack> stacks, int slot) {
        if (slot < 0 || slot >= stacks.size()) {
            return ItemStack.EMPTY;
        }
        return stacks.set(slot, ItemStack.EMPTY);
    }

    public static CompoundTag toTag(CompoundTag tag, DefaultedList<ItemStack> stacks) {
        return Inventories.toTag(tag, stacks, true);
    }

    public static CompoundTag toTag(CompoundTag tag, DefaultedList<ItemStack> stacks, boolean setIfEmpty) {
        ListTag listTag = new ListTag();
        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack itemStack = stacks.get(i);
            if (itemStack.isEmpty()) continue;
            CompoundTag _snowman2 = new CompoundTag();
            _snowman2.putByte("Slot", (byte)i);
            itemStack.toTag(_snowman2);
            listTag.add(_snowman2);
        }
        if (!listTag.isEmpty() || setIfEmpty) {
            tag.put("Items", listTag);
        }
        return tag;
    }

    public static void fromTag(CompoundTag tag, DefaultedList<ItemStack> stacks) {
        ListTag listTag = tag.getList("Items", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            int _snowman2 = compoundTag.getByte("Slot") & 0xFF;
            if (_snowman2 < 0 || _snowman2 >= stacks.size()) continue;
            stacks.set(_snowman2, ItemStack.fromTag(compoundTag));
        }
    }

    public static int remove(Inventory inventory, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
        int n = 0;
        for (_snowman = 0; _snowman < inventory.size(); ++_snowman) {
            ItemStack itemStack = inventory.getStack(_snowman);
            int _snowman2 = Inventories.remove(itemStack, shouldRemove, maxCount - n, dryRun);
            if (_snowman2 > 0 && !dryRun && itemStack.isEmpty()) {
                inventory.setStack(_snowman, ItemStack.EMPTY);
            }
            n += _snowman2;
        }
        return n;
    }

    public static int remove(ItemStack stack, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
        if (stack.isEmpty() || !shouldRemove.test(stack)) {
            return 0;
        }
        if (dryRun) {
            return stack.getCount();
        }
        int n = maxCount < 0 ? stack.getCount() : Math.min(maxCount, stack.getCount());
        stack.decrement(n);
        return n;
    }
}

