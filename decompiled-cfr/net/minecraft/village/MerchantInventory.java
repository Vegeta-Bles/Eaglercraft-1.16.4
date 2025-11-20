/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.village;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class MerchantInventory
implements Inventory {
    private final Merchant merchant;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    @Nullable
    private TradeOffer tradeOffer;
    private int recipeIndex;
    private int merchantRewardedExperience;

    public MerchantInventory(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = this.inventory.get(slot);
        if (slot == 2 && !itemStack.isEmpty()) {
            return Inventories.splitStack(this.inventory, slot, itemStack.getCount());
        }
        _snowman = Inventories.splitStack(this.inventory, slot, amount);
        if (!_snowman.isEmpty() && this.needRecipeUpdate(slot)) {
            this.updateRecipes();
        }
        return _snowman;
    }

    private boolean needRecipeUpdate(int slot) {
        return slot == 0 || slot == 1;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (this.needRecipeUpdate(slot)) {
            this.updateRecipes();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.merchant.getCurrentCustomer() == player;
    }

    @Override
    public void markDirty() {
        this.updateRecipes();
    }

    public void updateRecipes() {
        ItemStack itemStack;
        this.tradeOffer = null;
        if (this.inventory.get(0).isEmpty()) {
            itemStack = this.inventory.get(1);
            _snowman = ItemStack.EMPTY;
        } else {
            itemStack = this.inventory.get(0);
            _snowman = this.inventory.get(1);
        }
        if (itemStack.isEmpty()) {
            this.setStack(2, ItemStack.EMPTY);
            this.merchantRewardedExperience = 0;
            return;
        }
        TradeOfferList _snowman2 = this.merchant.getOffers();
        if (!_snowman2.isEmpty()) {
            TradeOffer tradeOffer = _snowman2.getValidOffer(itemStack, _snowman, this.recipeIndex);
            if (tradeOffer == null || tradeOffer.isDisabled()) {
                this.tradeOffer = tradeOffer;
                tradeOffer = _snowman2.getValidOffer(_snowman, itemStack, this.recipeIndex);
            }
            if (tradeOffer != null && !tradeOffer.isDisabled()) {
                this.tradeOffer = tradeOffer;
                this.setStack(2, tradeOffer.getSellItem());
                this.merchantRewardedExperience = tradeOffer.getMerchantExperience();
            } else {
                this.setStack(2, ItemStack.EMPTY);
                this.merchantRewardedExperience = 0;
            }
        }
        this.merchant.onSellingItem(this.getStack(2));
    }

    @Nullable
    public TradeOffer getTradeOffer() {
        return this.tradeOffer;
    }

    public void setRecipeIndex(int index) {
        this.recipeIndex = index;
        this.updateRecipes();
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    public int getMerchantRewardedExperience() {
        return this.merchantRewardedExperience;
    }
}

