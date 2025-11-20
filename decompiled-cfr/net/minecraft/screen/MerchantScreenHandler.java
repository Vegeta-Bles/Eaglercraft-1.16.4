/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.TradeOutputSlot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.village.Merchant;
import net.minecraft.village.MerchantInventory;
import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class MerchantScreenHandler
extends ScreenHandler {
    private final Merchant merchant;
    private final MerchantInventory merchantInventory;
    private int levelProgress;
    private boolean leveled;
    private boolean canRefreshTrades;

    public MerchantScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleMerchant(playerInventory.player));
    }

    public MerchantScreenHandler(int syncId, PlayerInventory playerInventory, Merchant merchant) {
        super(ScreenHandlerType.MERCHANT, syncId);
        int n;
        this.merchant = merchant;
        this.merchantInventory = new MerchantInventory(merchant);
        this.addSlot(new Slot(this.merchantInventory, 0, 136, 37));
        this.addSlot(new Slot(this.merchantInventory, 1, 162, 37));
        this.addSlot(new TradeOutputSlot(playerInventory.player, merchant, this.merchantInventory, 2, 220, 37));
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + n * 9 + 9, 108 + _snowman * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 108 + n * 18, 142));
        }
    }

    public void setCanLevel(boolean canLevel) {
        this.leveled = canLevel;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.merchantInventory.updateRecipes();
        super.onContentChanged(inventory);
    }

    public void setRecipeIndex(int index) {
        this.merchantInventory.setRecipeIndex(index);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.merchant.getCurrentCustomer() == player;
    }

    public int getExperience() {
        return this.merchant.getExperience();
    }

    public int getMerchantRewardedExperience() {
        return this.merchantInventory.getMerchantRewardedExperience();
    }

    public void setExperienceFromServer(int experience) {
        this.merchant.setExperienceFromServer(experience);
    }

    public int getLevelProgress() {
        return this.levelProgress;
    }

    public void setLevelProgress(int progress) {
        this.levelProgress = progress;
    }

    public void setRefreshTrades(boolean refreshable) {
        this.canRefreshTrades = refreshable;
    }

    public boolean canRefreshTrades() {
        return this.canRefreshTrades;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return false;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            if (index == 2) {
                if (!this.insertItem(_snowman, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(_snowman, itemStack);
                this.playYesSound();
            } else if (index == 0 || index == 1 ? !this.insertItem(_snowman, 3, 39, false) : (index >= 3 && index < 30 ? !this.insertItem(_snowman, 30, 39, false) : index >= 30 && index < 39 && !this.insertItem(_snowman, 3, 30, false))) {
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

    private void playYesSound() {
        if (!this.merchant.getMerchantWorld().isClient) {
            Entity entity = (Entity)((Object)this.merchant);
            this.merchant.getMerchantWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), this.merchant.getYesSound(), SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.merchant.setCurrentCustomer(null);
        if (this.merchant.getMerchantWorld().isClient) {
            return;
        }
        if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
            ItemStack itemStack = this.merchantInventory.removeStack(0);
            if (!itemStack.isEmpty()) {
                player.dropItem(itemStack, false);
            }
            if (!(itemStack = this.merchantInventory.removeStack(1)).isEmpty()) {
                player.dropItem(itemStack, false);
            }
        } else {
            player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(0));
            player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(1));
        }
    }

    public void switchTo(int recipeIndex) {
        if (this.getRecipes().size() <= recipeIndex) {
            return;
        }
        ItemStack itemStack = this.merchantInventory.getStack(0);
        if (!itemStack.isEmpty()) {
            if (!this.insertItem(itemStack, 3, 39, true)) {
                return;
            }
            this.merchantInventory.setStack(0, itemStack);
        }
        if (!(_snowman = this.merchantInventory.getStack(1)).isEmpty()) {
            if (!this.insertItem(_snowman, 3, 39, true)) {
                return;
            }
            this.merchantInventory.setStack(1, _snowman);
        }
        if (this.merchantInventory.getStack(0).isEmpty() && this.merchantInventory.getStack(1).isEmpty()) {
            _snowman = ((TradeOffer)this.getRecipes().get(recipeIndex)).getAdjustedFirstBuyItem();
            this.autofill(0, _snowman);
            _snowman = ((TradeOffer)this.getRecipes().get(recipeIndex)).getSecondBuyItem();
            this.autofill(1, _snowman);
        }
    }

    private void autofill(int slot, ItemStack stack) {
        if (!stack.isEmpty()) {
            for (int i = 3; i < 39; ++i) {
                ItemStack itemStack = ((Slot)this.slots.get(i)).getStack();
                if (itemStack.isEmpty() || !this.equals(stack, itemStack)) continue;
                _snowman = this.merchantInventory.getStack(slot);
                int _snowman2 = _snowman.isEmpty() ? 0 : _snowman.getCount();
                int _snowman3 = Math.min(stack.getMaxCount() - _snowman2, itemStack.getCount());
                _snowman = itemStack.copy();
                int _snowman4 = _snowman2 + _snowman3;
                itemStack.decrement(_snowman3);
                _snowman.setCount(_snowman4);
                this.merchantInventory.setStack(slot, _snowman);
                if (_snowman4 >= stack.getMaxCount()) break;
            }
        }
    }

    private boolean equals(ItemStack itemStack, ItemStack otherItemStack) {
        return itemStack.getItem() == otherItemStack.getItem() && ItemStack.areTagsEqual(itemStack, otherItemStack);
    }

    public void setOffers(TradeOfferList offers) {
        this.merchant.setOffersFromServer(offers);
    }

    public TradeOfferList getRecipes() {
        return this.merchant.getOffers();
    }

    public boolean isLeveled() {
        return this.leveled;
    }
}

