/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.screen;

import javax.annotation.Nullable;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.ItemTags;

public class BeaconScreenHandler
extends ScreenHandler {
    private final Inventory payment = new SimpleInventory(this, 1){
        final /* synthetic */ BeaconScreenHandler field_17291;
        {
            this.field_17291 = beaconScreenHandler;
            super(n);
        }

        public boolean isValid(int slot, ItemStack stack) {
            return stack.getItem().isIn(ItemTags.BEACON_PAYMENT_ITEMS);
        }

        public int getMaxCountPerStack() {
            return 1;
        }
    };
    private final PaymentSlot paymentSlot;
    private final ScreenHandlerContext context;
    private final PropertyDelegate propertyDelegate;

    public BeaconScreenHandler(int syncId, Inventory inventory) {
        this(syncId, inventory, new ArrayPropertyDelegate(3), ScreenHandlerContext.EMPTY);
    }

    public BeaconScreenHandler(int syncId, Inventory inventory, PropertyDelegate propertyDelegate, ScreenHandlerContext context) {
        super(ScreenHandlerType.BEACON, syncId);
        BeaconScreenHandler.checkDataCount(propertyDelegate, 3);
        this.propertyDelegate = propertyDelegate;
        this.context = context;
        this.paymentSlot = new PaymentSlot(this.payment, 0, 136, 110);
        this.addSlot(this.paymentSlot);
        this.addProperties(propertyDelegate);
        int n = 36;
        _snowman = 137;
        for (_snowman = 0; _snowman < 3; ++_snowman) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(inventory, _snowman + _snowman * 9 + 9, 36 + _snowman * 18, 137 + _snowman * 18));
            }
        }
        for (_snowman = 0; _snowman < 9; ++_snowman) {
            this.addSlot(new Slot(inventory, _snowman, 36 + _snowman * 18, 195));
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        if (player.world.isClient) {
            return;
        }
        ItemStack itemStack = this.paymentSlot.takeStack(this.paymentSlot.getMaxItemCount());
        if (!itemStack.isEmpty()) {
            player.dropItem(itemStack, false);
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return BeaconScreenHandler.canUse(this.context, player, Blocks.BEACON);
    }

    @Override
    public void setProperty(int id, int value) {
        super.setProperty(id, value);
        this.sendContentUpdates();
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            if (index == 0) {
                if (!this.insertItem(_snowman, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(_snowman, itemStack);
            } else if (!this.paymentSlot.hasStack() && this.paymentSlot.canInsert(_snowman) && _snowman.getCount() == 1 ? !this.insertItem(_snowman, 0, 1, false) : (index >= 1 && index < 28 ? !this.insertItem(_snowman, 28, 37, false) : (index >= 28 && index < 37 ? !this.insertItem(_snowman, 1, 28, false) : !this.insertItem(_snowman, 1, 37, false)))) {
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

    public int getProperties() {
        return this.propertyDelegate.get(0);
    }

    @Nullable
    public StatusEffect getPrimaryEffect() {
        return StatusEffect.byRawId(this.propertyDelegate.get(1));
    }

    @Nullable
    public StatusEffect getSecondaryEffect() {
        return StatusEffect.byRawId(this.propertyDelegate.get(2));
    }

    public void setEffects(int primaryEffectId, int secondaryEffectId) {
        if (this.paymentSlot.hasStack()) {
            this.propertyDelegate.set(1, primaryEffectId);
            this.propertyDelegate.set(2, secondaryEffectId);
            this.paymentSlot.takeStack(1);
        }
    }

    public boolean hasPayment() {
        return !this.payment.getStack(0).isEmpty();
    }

    class PaymentSlot
    extends Slot {
        public PaymentSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem().isIn(ItemTags.BEACON_PAYMENT_ITEMS);
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }
    }
}

