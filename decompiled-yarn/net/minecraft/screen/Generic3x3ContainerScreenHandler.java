package net.minecraft.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class Generic3x3ContainerScreenHandler extends ScreenHandler {
   private final Inventory inventory;

   public Generic3x3ContainerScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, new SimpleInventory(9));
   }

   public Generic3x3ContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
      super(ScreenHandlerType.GENERIC_3X3, syncId);
      checkSize(inventory, 9);
      this.inventory = inventory;
      inventory.onOpen(playerInventory.player);

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            this.addSlot(new Slot(inventory, _snowmanx + _snowman * 3, 62 + _snowmanx * 18, 17 + _snowman * 18));
         }
      }

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
         if (index < 9) {
            if (!this.insertItem(_snowmanxx, 9, 45, true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 0, 9, false)) {
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

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.inventory.onClose(player);
   }
}
