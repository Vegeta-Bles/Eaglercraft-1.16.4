package net.minecraft.village;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class MerchantInventory implements Inventory {
   private final Merchant merchant;
   private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
   @Nullable
   private TradeOffer tradeOffer;
   private int recipeIndex;
   private int merchantRewardedExperience;

   public MerchantInventory(Merchant _snowman) {
      this.merchant = _snowman;
   }

   @Override
   public int size() {
      return this.inventory.size();
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack _snowman : this.inventory) {
         if (!_snowman.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getStack(int slot) {
      return this.inventory.get(slot);
   }

   @Override
   public ItemStack removeStack(int slot, int amount) {
      ItemStack _snowman = this.inventory.get(slot);
      if (slot == 2 && !_snowman.isEmpty()) {
         return Inventories.splitStack(this.inventory, slot, _snowman.getCount());
      } else {
         ItemStack _snowmanx = Inventories.splitStack(this.inventory, slot, amount);
         if (!_snowmanx.isEmpty() && this.needRecipeUpdate(slot)) {
            this.updateRecipes();
         }

         return _snowmanx;
      }
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
      this.tradeOffer = null;
      ItemStack _snowman;
      ItemStack _snowmanx;
      if (this.inventory.get(0).isEmpty()) {
         _snowman = this.inventory.get(1);
         _snowmanx = ItemStack.EMPTY;
      } else {
         _snowman = this.inventory.get(0);
         _snowmanx = this.inventory.get(1);
      }

      if (_snowman.isEmpty()) {
         this.setStack(2, ItemStack.EMPTY);
         this.merchantRewardedExperience = 0;
      } else {
         TradeOfferList _snowmanxx = this.merchant.getOffers();
         if (!_snowmanxx.isEmpty()) {
            TradeOffer _snowmanxxx = _snowmanxx.getValidOffer(_snowman, _snowmanx, this.recipeIndex);
            if (_snowmanxxx == null || _snowmanxxx.isDisabled()) {
               this.tradeOffer = _snowmanxxx;
               _snowmanxxx = _snowmanxx.getValidOffer(_snowmanx, _snowman, this.recipeIndex);
            }

            if (_snowmanxxx != null && !_snowmanxxx.isDisabled()) {
               this.tradeOffer = _snowmanxxx;
               this.setStack(2, _snowmanxxx.getSellItem());
               this.merchantRewardedExperience = _snowmanxxx.getMerchantExperience();
            } else {
               this.setStack(2, ItemStack.EMPTY);
               this.merchantRewardedExperience = 0;
            }
         }

         this.merchant.onSellingItem(this.getStack(2));
      }
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
