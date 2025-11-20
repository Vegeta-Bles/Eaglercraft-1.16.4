package net.minecraft.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.ShulkerBoxSlot;
import net.minecraft.screen.slot.Slot;

public class ShulkerBoxScreenHandler extends ScreenHandler {
   private final Inventory inventory;

   public ShulkerBoxScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, new SimpleInventory(27));
   }

   public ShulkerBoxScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
      super(ScreenHandlerType.SHULKER_BOX, syncId);
      checkSize(inventory, 27);
      this.inventory = inventory;
      inventory.onOpen(playerInventory.player);
      int _snowman = 3;
      int _snowmanx = 9;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.addSlot(new ShulkerBoxSlot(inventory, _snowmanxxx + _snowmanxx * 9, 8 + _snowmanxxx * 18, 18 + _snowmanxx * 18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.addSlot(new Slot(playerInventory, _snowmanxxx + _snowmanxx * 9 + 9, 8 + _snowmanxxx * 18, 84 + _snowmanxx * 18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
         this.addSlot(new Slot(playerInventory, _snowmanxx, 8 + _snowmanxx * 18, 142));
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
         if (index < this.inventory.size()) {
            if (!this.insertItem(_snowmanxx, this.inventory.size(), this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 0, this.inventory.size(), false)) {
            return ItemStack.EMPTY;
         }

         if (_snowmanxx.isEmpty()) {
            _snowmanx.setStack(ItemStack.EMPTY);
         } else {
            _snowmanx.markDirty();
         }
      }

      return _snowman;
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.inventory.onClose(player);
   }
}
