/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class HorseScreenHandler
extends ScreenHandler {
    private final Inventory inventory;
    private final HorseBaseEntity entity;

    public HorseScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, HorseBaseEntity entity) {
        super(null, syncId);
        this.inventory = inventory;
        this.entity = entity;
        int n = 3;
        inventory.onOpen(playerInventory.player);
        _snowman = -18;
        this.addSlot(new Slot(this, inventory, 0, 8, 18, entity){
            final /* synthetic */ HorseBaseEntity field_7838;
            final /* synthetic */ HorseScreenHandler field_7839;
            {
                this.field_7839 = horseScreenHandler;
                this.field_7838 = horseBaseEntity;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.SADDLE && !this.hasStack() && this.field_7838.canBeSaddled();
            }

            public boolean doDrawHoveringEffect() {
                return this.field_7838.canBeSaddled();
            }
        });
        this.addSlot(new Slot(this, inventory, 1, 8, 36, entity){
            final /* synthetic */ HorseBaseEntity field_7840;
            final /* synthetic */ HorseScreenHandler field_7841;
            {
                this.field_7841 = horseScreenHandler;
                this.field_7840 = horseBaseEntity;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return this.field_7840.isHorseArmor(stack);
            }

            public boolean doDrawHoveringEffect() {
                return this.field_7840.hasArmorSlot();
            }

            public int getMaxItemCount() {
                return 1;
            }
        });
        if (entity instanceof AbstractDonkeyEntity && ((AbstractDonkeyEntity)entity).hasChest()) {
            for (_snowman = 0; _snowman < 3; ++_snowman) {
                for (_snowman = 0; _snowman < ((AbstractDonkeyEntity)entity).getInventoryColumns(); ++_snowman) {
                    this.addSlot(new Slot(inventory, 2 + _snowman + _snowman * ((AbstractDonkeyEntity)entity).getInventoryColumns(), 80 + _snowman * 18, 18 + _snowman * 18));
                }
            }
        }
        for (_snowman = 0; _snowman < 3; ++_snowman) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + _snowman * 9 + 9, 8 + _snowman * 18, 102 + _snowman * 18 + -18));
            }
        }
        for (_snowman = 0; _snowman < 9; ++_snowman) {
            this.addSlot(new Slot(playerInventory, _snowman, 8 + _snowman * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player) && this.entity.isAlive() && this.entity.distanceTo(player) < 8.0f;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            int n = this.inventory.size();
            if (index < n) {
                if (!this.insertItem(_snowman, n, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).canInsert(_snowman) && !this.getSlot(1).hasStack()) {
                if (!this.insertItem(_snowman, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).canInsert(_snowman)) {
                if (!this.insertItem(_snowman, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (n <= 2 || !this.insertItem(_snowman, 2, n, false)) {
                _snowman = n;
                _snowman = _snowman = _snowman + 27;
                _snowman = _snowman + 9;
                if (index >= _snowman && index < _snowman ? !this.insertItem(_snowman, _snowman, _snowman, false) : (index >= _snowman && index < _snowman ? !this.insertItem(_snowman, _snowman, _snowman, false) : !this.insertItem(_snowman, _snowman, _snowman, false))) {
                    return ItemStack.EMPTY;
                }
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

