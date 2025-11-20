package net.minecraft.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class HopperScreenHandler extends ScreenHandler {
   private final Inventory inventory;

   public HopperScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, new SimpleInventory(5));
   }

   public HopperScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
      super(ScreenHandlerType.HOPPER, syncId);
      this.inventory = inventory;
      checkSize(inventory, 5);
      inventory.onOpen(playerInventory.player);
      int _snowman = 51;

      for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
         this.addSlot(new Slot(inventory, _snowmanx, 44 + _snowmanx * 18, 20));
      }

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            this.addSlot(new Slot(playerInventory, _snowmanxx + _snowmanx * 9 + 9, 8 + _snowmanxx * 18, _snowmanx * 18 + 51));
         }
      }

      for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
         this.addSlot(new Slot(playerInventory, _snowmanx, 8 + _snowmanx * 18, 109));
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
