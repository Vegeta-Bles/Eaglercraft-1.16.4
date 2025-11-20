package net.minecraft.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.TradeOutputSlot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.village.Merchant;
import net.minecraft.village.MerchantInventory;
import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOfferList;

public class MerchantScreenHandler extends ScreenHandler {
   private final Merchant merchant;
   private final MerchantInventory merchantInventory;
   @Environment(EnvType.CLIENT)
   private int levelProgress;
   @Environment(EnvType.CLIENT)
   private boolean leveled;
   @Environment(EnvType.CLIENT)
   private boolean canRefreshTrades;

   public MerchantScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, new SimpleMerchant(playerInventory.player));
   }

   public MerchantScreenHandler(int syncId, PlayerInventory playerInventory, Merchant merchant) {
      super(ScreenHandlerType.MERCHANT, syncId);
      this.merchant = merchant;
      this.merchantInventory = new MerchantInventory(merchant);
      this.addSlot(new Slot(this.merchantInventory, 0, 136, 37));
      this.addSlot(new Slot(this.merchantInventory, 1, 162, 37));
      this.addSlot(new TradeOutputSlot(playerInventory.player, merchant, this.merchantInventory, 2, 220, 37));

      for (int j = 0; j < 3; j++) {
         for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 108 + k * 18, 84 + j * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(playerInventory, l, 108 + l * 18, 142));
      }
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
   public int getExperience() {
      return this.merchant.getExperience();
   }

   @Environment(EnvType.CLIENT)
   public int getMerchantRewardedExperience() {
      return this.merchantInventory.getMerchantRewardedExperience();
   }

   @Environment(EnvType.CLIENT)
   public void setExperienceFromServer(int experience) {
      this.merchant.setExperienceFromServer(experience);
   }

   @Environment(EnvType.CLIENT)
   public int getLevelProgress() {
      return this.levelProgress;
   }

   @Environment(EnvType.CLIENT)
   public void setLevelProgress(int progress) {
      this.levelProgress = progress;
   }

   @Environment(EnvType.CLIENT)
   public void setRefreshTrades(boolean refreshable) {
      this.canRefreshTrades = refreshable;
   }

   @Environment(EnvType.CLIENT)
   public boolean canRefreshTrades() {
      return this.canRefreshTrades;
   }

   @Override
   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return false;
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack lv = ItemStack.EMPTY;
      Slot lv2 = this.slots.get(index);
      if (lv2 != null && lv2.hasStack()) {
         ItemStack lv3 = lv2.getStack();
         lv = lv3.copy();
         if (index == 2) {
            if (!this.insertItem(lv3, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            lv2.onStackChanged(lv3, lv);
            this.playYesSound();
         } else if (index != 0 && index != 1) {
            if (index >= 3 && index < 30) {
               if (!this.insertItem(lv3, 30, 39, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 30 && index < 39 && !this.insertItem(lv3, 3, 30, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(lv3, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (lv3.isEmpty()) {
            lv2.setStack(ItemStack.EMPTY);
         } else {
            lv2.markDirty();
         }

         if (lv3.getCount() == lv.getCount()) {
            return ItemStack.EMPTY;
         }

         lv2.onTakeItem(player, lv3);
      }

      return lv;
   }

   private void playYesSound() {
      if (!this.merchant.getMerchantWorld().isClient) {
         Entity lv = (Entity)this.merchant;
         this.merchant.getMerchantWorld().playSound(lv.getX(), lv.getY(), lv.getZ(), this.merchant.getYesSound(), SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
      }
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.merchant.setCurrentCustomer(null);
      if (!this.merchant.getMerchantWorld().isClient) {
         if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
            ItemStack lv = this.merchantInventory.removeStack(0);
            if (!lv.isEmpty()) {
               player.dropItem(lv, false);
            }

            lv = this.merchantInventory.removeStack(1);
            if (!lv.isEmpty()) {
               player.dropItem(lv, false);
            }
         } else {
            player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(0));
            player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(1));
         }
      }
   }

   public void switchTo(int recipeIndex) {
      if (this.getRecipes().size() > recipeIndex) {
         ItemStack lv = this.merchantInventory.getStack(0);
         if (!lv.isEmpty()) {
            if (!this.insertItem(lv, 3, 39, true)) {
               return;
            }

            this.merchantInventory.setStack(0, lv);
         }

         ItemStack lv2 = this.merchantInventory.getStack(1);
         if (!lv2.isEmpty()) {
            if (!this.insertItem(lv2, 3, 39, true)) {
               return;
            }

            this.merchantInventory.setStack(1, lv2);
         }

         if (this.merchantInventory.getStack(0).isEmpty() && this.merchantInventory.getStack(1).isEmpty()) {
            ItemStack lv3 = this.getRecipes().get(recipeIndex).getAdjustedFirstBuyItem();
            this.autofill(0, lv3);
            ItemStack lv4 = this.getRecipes().get(recipeIndex).getSecondBuyItem();
            this.autofill(1, lv4);
         }
      }
   }

   private void autofill(int slot, ItemStack stack) {
      if (!stack.isEmpty()) {
         for (int j = 3; j < 39; j++) {
            ItemStack lv = this.slots.get(j).getStack();
            if (!lv.isEmpty() && this.equals(stack, lv)) {
               ItemStack lv2 = this.merchantInventory.getStack(slot);
               int k = lv2.isEmpty() ? 0 : lv2.getCount();
               int l = Math.min(stack.getMaxCount() - k, lv.getCount());
               ItemStack lv3 = lv.copy();
               int m = k + l;
               lv.decrement(l);
               lv3.setCount(m);
               this.merchantInventory.setStack(slot, lv3);
               if (m >= stack.getMaxCount()) {
                  break;
               }
            }
         }
      }
   }

   private boolean equals(ItemStack itemStack, ItemStack otherItemStack) {
      return itemStack.getItem() == otherItemStack.getItem() && ItemStack.areTagsEqual(itemStack, otherItemStack);
   }

   @Environment(EnvType.CLIENT)
   public void setOffers(TradeOfferList offers) {
      this.merchant.setOffersFromServer(offers);
   }

   public TradeOfferList getRecipes() {
      return this.merchant.getOffers();
   }

   @Environment(EnvType.CLIENT)
   public boolean isLeveled() {
      return this.leveled;
   }
}
