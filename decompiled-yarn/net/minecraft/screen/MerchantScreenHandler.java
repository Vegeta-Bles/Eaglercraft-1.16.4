package net.minecraft.screen;

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
   private int levelProgress;
   private boolean leveled;
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

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.addSlot(new Slot(playerInventory, _snowmanx + _snowman * 9 + 9, 108 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.addSlot(new Slot(playerInventory, _snowman, 108 + _snowman * 18, 142));
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
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if (index == 2) {
            if (!this.insertItem(_snowmanxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
            this.playYesSound();
         } else if (index != 0 && index != 1) {
            if (index >= 3 && index < 30) {
               if (!this.insertItem(_snowmanxx, 30, 39, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 30 && index < 39 && !this.insertItem(_snowmanxx, 3, 30, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (_snowmanxx.isEmpty()) {
            _snowmanx.setStack(ItemStack.EMPTY);
         } else {
            _snowmanx.markDirty();
         }

         if (_snowmanxx.getCount() == _snowman.getCount()) {
            return ItemStack.EMPTY;
         }

         _snowmanx.onTakeItem(player, _snowmanxx);
      }

      return _snowman;
   }

   private void playYesSound() {
      if (!this.merchant.getMerchantWorld().isClient) {
         Entity _snowman = (Entity)this.merchant;
         this.merchant.getMerchantWorld().playSound(_snowman.getX(), _snowman.getY(), _snowman.getZ(), this.merchant.getYesSound(), SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
      }
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.merchant.setCurrentCustomer(null);
      if (!this.merchant.getMerchantWorld().isClient) {
         if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
            ItemStack _snowman = this.merchantInventory.removeStack(0);
            if (!_snowman.isEmpty()) {
               player.dropItem(_snowman, false);
            }

            _snowman = this.merchantInventory.removeStack(1);
            if (!_snowman.isEmpty()) {
               player.dropItem(_snowman, false);
            }
         } else {
            player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(0));
            player.inventory.offerOrDrop(player.world, this.merchantInventory.removeStack(1));
         }
      }
   }

   public void switchTo(int recipeIndex) {
      if (this.getRecipes().size() > recipeIndex) {
         ItemStack _snowman = this.merchantInventory.getStack(0);
         if (!_snowman.isEmpty()) {
            if (!this.insertItem(_snowman, 3, 39, true)) {
               return;
            }

            this.merchantInventory.setStack(0, _snowman);
         }

         ItemStack _snowmanx = this.merchantInventory.getStack(1);
         if (!_snowmanx.isEmpty()) {
            if (!this.insertItem(_snowmanx, 3, 39, true)) {
               return;
            }

            this.merchantInventory.setStack(1, _snowmanx);
         }

         if (this.merchantInventory.getStack(0).isEmpty() && this.merchantInventory.getStack(1).isEmpty()) {
            ItemStack _snowmanxx = this.getRecipes().get(recipeIndex).getAdjustedFirstBuyItem();
            this.autofill(0, _snowmanxx);
            ItemStack _snowmanxxx = this.getRecipes().get(recipeIndex).getSecondBuyItem();
            this.autofill(1, _snowmanxxx);
         }
      }
   }

   private void autofill(int slot, ItemStack stack) {
      if (!stack.isEmpty()) {
         for (int _snowman = 3; _snowman < 39; _snowman++) {
            ItemStack _snowmanx = this.slots.get(_snowman).getStack();
            if (!_snowmanx.isEmpty() && this.equals(stack, _snowmanx)) {
               ItemStack _snowmanxx = this.merchantInventory.getStack(slot);
               int _snowmanxxx = _snowmanxx.isEmpty() ? 0 : _snowmanxx.getCount();
               int _snowmanxxxx = Math.min(stack.getMaxCount() - _snowmanxxx, _snowmanx.getCount());
               ItemStack _snowmanxxxxx = _snowmanx.copy();
               int _snowmanxxxxxx = _snowmanxxx + _snowmanxxxx;
               _snowmanx.decrement(_snowmanxxxx);
               _snowmanxxxxx.setCount(_snowmanxxxxxx);
               this.merchantInventory.setStack(slot, _snowmanxxxxx);
               if (_snowmanxxxxxx >= stack.getMaxCount()) {
                  break;
               }
            }
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
