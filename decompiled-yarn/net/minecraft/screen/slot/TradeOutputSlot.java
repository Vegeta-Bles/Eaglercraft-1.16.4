package net.minecraft.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.village.Merchant;
import net.minecraft.village.MerchantInventory;
import net.minecraft.village.TradeOffer;

public class TradeOutputSlot extends Slot {
   private final MerchantInventory merchantInventory;
   private final PlayerEntity player;
   private int amount;
   private final Merchant merchant;

   public TradeOutputSlot(PlayerEntity player, Merchant merchant, MerchantInventory merchantInventory, int index, int x, int y) {
      super(merchantInventory, index, x, y);
      this.player = player;
      this.merchant = merchant;
      this.merchantInventory = merchantInventory;
   }

   @Override
   public boolean canInsert(ItemStack stack) {
      return false;
   }

   @Override
   public ItemStack takeStack(int amount) {
      if (this.hasStack()) {
         this.amount = this.amount + Math.min(amount, this.getStack().getCount());
      }

      return super.takeStack(amount);
   }

   @Override
   protected void onCrafted(ItemStack stack, int amount) {
      this.amount += amount;
      this.onCrafted(stack);
   }

   @Override
   protected void onCrafted(ItemStack stack) {
      stack.onCraft(this.player.world, this.player, this.amount);
      this.amount = 0;
   }

   @Override
   public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
      this.onCrafted(stack);
      TradeOffer _snowman = this.merchantInventory.getTradeOffer();
      if (_snowman != null) {
         ItemStack _snowmanx = this.merchantInventory.getStack(0);
         ItemStack _snowmanxx = this.merchantInventory.getStack(1);
         if (_snowman.depleteBuyItems(_snowmanx, _snowmanxx) || _snowman.depleteBuyItems(_snowmanxx, _snowmanx)) {
            this.merchant.trade(_snowman);
            player.incrementStat(Stats.TRADED_WITH_VILLAGER);
            this.merchantInventory.setStack(0, _snowmanx);
            this.merchantInventory.setStack(1, _snowmanxx);
         }

         this.merchant.setExperienceFromServer(this.merchant.getExperience() + _snowman.getMerchantExperience());
      }

      return stack;
   }
}
