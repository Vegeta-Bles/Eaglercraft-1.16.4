package net.minecraft.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.util.collection.DefaultedList;

public class CraftingResultSlot extends Slot {
   private final CraftingInventory input;
   private final PlayerEntity player;
   private int amount;

   public CraftingResultSlot(PlayerEntity player, CraftingInventory input, Inventory inventory, int index, int x, int y) {
      super(inventory, index, x, y);
      this.player = player;
      this.input = input;
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
   protected void onTake(int amount) {
      this.amount += amount;
   }

   @Override
   protected void onCrafted(ItemStack stack) {
      if (this.amount > 0) {
         stack.onCraft(this.player.world, this.player, this.amount);
      }

      if (this.inventory instanceof RecipeUnlocker) {
         ((RecipeUnlocker)this.inventory).unlockLastRecipe(this.player);
      }

      this.amount = 0;
   }

   @Override
   public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
      this.onCrafted(stack);
      DefaultedList<ItemStack> _snowman = player.world.getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, this.input, player.world);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         ItemStack _snowmanxx = this.input.getStack(_snowmanx);
         ItemStack _snowmanxxx = _snowman.get(_snowmanx);
         if (!_snowmanxx.isEmpty()) {
            this.input.removeStack(_snowmanx, 1);
            _snowmanxx = this.input.getStack(_snowmanx);
         }

         if (!_snowmanxxx.isEmpty()) {
            if (_snowmanxx.isEmpty()) {
               this.input.setStack(_snowmanx, _snowmanxxx);
            } else if (ItemStack.areItemsEqualIgnoreDamage(_snowmanxx, _snowmanxxx) && ItemStack.areTagsEqual(_snowmanxx, _snowmanxxx)) {
               _snowmanxxx.increment(_snowmanxx.getCount());
               this.input.setStack(_snowmanx, _snowmanxxx);
            } else if (!this.player.inventory.insertStack(_snowmanxxx)) {
               this.player.dropItem(_snowmanxxx, false);
            }
         }
      }

      return stack;
   }
}
