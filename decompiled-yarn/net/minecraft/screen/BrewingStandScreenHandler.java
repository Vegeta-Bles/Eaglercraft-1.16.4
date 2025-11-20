package net.minecraft.screen;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class BrewingStandScreenHandler extends ScreenHandler {
   private final Inventory inventory;
   private final PropertyDelegate propertyDelegate;
   private final Slot ingredientSlot;

   public BrewingStandScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(2));
   }

   public BrewingStandScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
      super(ScreenHandlerType.BREWING_STAND, syncId);
      checkSize(inventory, 5);
      checkDataCount(propertyDelegate, 2);
      this.inventory = inventory;
      this.propertyDelegate = propertyDelegate;
      this.addSlot(new BrewingStandScreenHandler.PotionSlot(inventory, 0, 56, 51));
      this.addSlot(new BrewingStandScreenHandler.PotionSlot(inventory, 1, 79, 58));
      this.addSlot(new BrewingStandScreenHandler.PotionSlot(inventory, 2, 102, 51));
      this.ingredientSlot = this.addSlot(new BrewingStandScreenHandler.IngredientSlot(inventory, 3, 79, 17));
      this.addSlot(new BrewingStandScreenHandler.FuelSlot(inventory, 4, 17, 17));
      this.addProperties(propertyDelegate);

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.addSlot(new Slot(playerInventory, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.addSlot(new Slot(playerInventory, _snowman, 8 + _snowman * 18, 142));
      }
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return this.inventory.canPlayerUse(player);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if ((index < 0 || index > 2) && index != 3 && index != 4) {
            if (BrewingStandScreenHandler.FuelSlot.matches(_snowman)) {
               if (this.insertItem(_snowmanxx, 4, 5, false) || this.ingredientSlot.canInsert(_snowmanxx) && !this.insertItem(_snowmanxx, 3, 4, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (this.ingredientSlot.canInsert(_snowmanxx)) {
               if (!this.insertItem(_snowmanxx, 3, 4, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (BrewingStandScreenHandler.PotionSlot.matches(_snowman) && _snowman.getCount() == 1) {
               if (!this.insertItem(_snowmanxx, 0, 3, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 5 && index < 32) {
               if (!this.insertItem(_snowmanxx, 32, 41, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 32 && index < 41) {
               if (!this.insertItem(_snowmanxx, 5, 32, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.insertItem(_snowmanxx, 5, 41, false)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (!this.insertItem(_snowmanxx, 5, 41, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
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

   public int getFuel() {
      return this.propertyDelegate.get(1);
   }

   public int getBrewTime() {
      return this.propertyDelegate.get(0);
   }

   static class FuelSlot extends Slot {
      public FuelSlot(Inventory _snowman, int _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean canInsert(ItemStack stack) {
         return matches(stack);
      }

      public static boolean matches(ItemStack stack) {
         return stack.getItem() == Items.BLAZE_POWDER;
      }

      @Override
      public int getMaxItemCount() {
         return 64;
      }
   }

   static class IngredientSlot extends Slot {
      public IngredientSlot(Inventory _snowman, int _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean canInsert(ItemStack stack) {
         return BrewingRecipeRegistry.isValidIngredient(stack);
      }

      @Override
      public int getMaxItemCount() {
         return 64;
      }
   }

   static class PotionSlot extends Slot {
      public PotionSlot(Inventory _snowman, int _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean canInsert(ItemStack stack) {
         return matches(stack);
      }

      @Override
      public int getMaxItemCount() {
         return 1;
      }

      @Override
      public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
         Potion _snowman = PotionUtil.getPotion(stack);
         if (player instanceof ServerPlayerEntity) {
            Criteria.BREWED_POTION.trigger((ServerPlayerEntity)player, _snowman);
         }

         super.onTakeItem(player, stack);
         return stack;
      }

      public static boolean matches(ItemStack stack) {
         Item _snowman = stack.getItem();
         return _snowman == Items.POTION || _snowman == Items.SPLASH_POTION || _snowman == Items.LINGERING_POTION || _snowman == Items.GLASS_BOTTLE;
      }
   }
}
