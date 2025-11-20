package net.minecraft.village;

import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   public MerchantInventory(Merchant arg) {
      this.merchant = arg;
   }

   @Override
   public int size() {
      return this.inventory.size();
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack lv : this.inventory) {
         if (!lv.isEmpty()) {
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
      ItemStack lv = this.inventory.get(slot);
      if (slot == 2 && !lv.isEmpty()) {
         return Inventories.splitStack(this.inventory, slot, lv.getCount());
      } else {
         ItemStack lv2 = Inventories.splitStack(this.inventory, slot, amount);
         if (!lv2.isEmpty() && this.needRecipeUpdate(slot)) {
            this.updateRecipes();
         }

         return lv2;
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
      ItemStack lv;
      ItemStack lv2;
      if (this.inventory.get(0).isEmpty()) {
         lv = this.inventory.get(1);
         lv2 = ItemStack.EMPTY;
      } else {
         lv = this.inventory.get(0);
         lv2 = this.inventory.get(1);
      }

      if (lv.isEmpty()) {
         this.setStack(2, ItemStack.EMPTY);
         this.merchantRewardedExperience = 0;
      } else {
         TradeOfferList lv5 = this.merchant.getOffers();
         if (!lv5.isEmpty()) {
            TradeOffer lv6 = lv5.getValidOffer(lv, lv2, this.recipeIndex);
            if (lv6 == null || lv6.isDisabled()) {
               this.tradeOffer = lv6;
               lv6 = lv5.getValidOffer(lv2, lv, this.recipeIndex);
            }

            if (lv6 != null && !lv6.isDisabled()) {
               this.tradeOffer = lv6;
               this.setStack(2, lv6.getSellItem());
               this.merchantRewardedExperience = lv6.getMerchantExperience();
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

   @Environment(EnvType.CLIENT)
   public int getMerchantRewardedExperience() {
      return this.merchantRewardedExperience;
   }
}
