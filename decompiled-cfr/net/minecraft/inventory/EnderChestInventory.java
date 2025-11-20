/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.inventory;

import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class EnderChestInventory
extends SimpleInventory {
    private EnderChestBlockEntity activeBlockEntity;

    public EnderChestInventory() {
        super(27);
    }

    public void setActiveBlockEntity(EnderChestBlockEntity blockEntity) {
        this.activeBlockEntity = blockEntity;
    }

    @Override
    public void readTags(ListTag tags) {
        int n;
        for (n = 0; n < this.size(); ++n) {
            this.setStack(n, ItemStack.EMPTY);
        }
        for (n = 0; n < tags.size(); ++n) {
            CompoundTag compoundTag = tags.getCompound(n);
            int _snowman2 = compoundTag.getByte("Slot") & 0xFF;
            if (_snowman2 < 0 || _snowman2 >= this.size()) continue;
            this.setStack(_snowman2, ItemStack.fromTag(compoundTag));
        }
    }

    @Override
    public ListTag getTags() {
        ListTag listTag = new ListTag();
        for (int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (itemStack.isEmpty()) continue;
            CompoundTag _snowman2 = new CompoundTag();
            _snowman2.putByte("Slot", (byte)i);
            itemStack.toTag(_snowman2);
            listTag.add(_snowman2);
        }
        return listTag;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.activeBlockEntity != null && !this.activeBlockEntity.canPlayerUse(player)) {
            return false;
        }
        return super.canPlayerUse(player);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (this.activeBlockEntity != null) {
            this.activeBlockEntity.onOpen();
        }
        super.onOpen(player);
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (this.activeBlockEntity != null) {
            this.activeBlockEntity.onClose();
        }
        super.onClose(player);
        this.activeBlockEntity = null;
    }
}

