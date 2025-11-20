package net.minecraft.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class GenericContainerScreenHandler extends ScreenHandler {
   private final Inventory inventory;
   private final int rows;

   private GenericContainerScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, int rows) {
      this(type, syncId, playerInventory, new SimpleInventory(9 * rows), rows);
   }

   public static GenericContainerScreenHandler createGeneric9x1(int syncId, PlayerInventory playerInventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, 1);
   }

   public static GenericContainerScreenHandler createGeneric9x2(int syncId, PlayerInventory playerInventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X2, syncId, playerInventory, 2);
   }

   public static GenericContainerScreenHandler createGeneric9x3(int syncId, PlayerInventory playerInventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, 3);
   }

   public static GenericContainerScreenHandler createGeneric9x4(int syncId, PlayerInventory playerInventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, 4);
   }

   public static GenericContainerScreenHandler createGeneric9x5(int syncId, PlayerInventory playerInventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X5, syncId, playerInventory, 5);
   }

   public static GenericContainerScreenHandler createGeneric9x6(int syncId, PlayerInventory playerInventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X6, syncId, playerInventory, 6);
   }

   public static GenericContainerScreenHandler createGeneric9x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, inventory, 3);
   }

   public static GenericContainerScreenHandler createGeneric9x6(int syncId, PlayerInventory playerInventory, Inventory inventory) {
      return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X6, syncId, playerInventory, inventory, 6);
   }

   public GenericContainerScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
      super(type, syncId);
      checkSize(inventory, rows * 9);
      this.inventory = inventory;
      this.rows = rows;
      inventory.onOpen(playerInventory.player);
      int _snowman = (this.rows - 4) * 18;

      for (int _snowmanx = 0; _snowmanx < this.rows; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            this.addSlot(new Slot(inventory, _snowmanxx + _snowmanx * 9, 8 + _snowmanxx * 18, 18 + _snowmanx * 18));
         }
      }

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            this.addSlot(new Slot(playerInventory, _snowmanxx + _snowmanx * 9 + 9, 8 + _snowmanxx * 18, 103 + _snowmanx * 18 + _snowman));
         }
      }

      for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
         this.addSlot(new Slot(playerInventory, _snowmanx, 8 + _snowmanx * 18, 161 + _snowman));
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
         if (index < this.rows * 9) {
            if (!this.insertItem(_snowmanxx, this.rows * 9, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 0, this.rows * 9, false)) {
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

   public Inventory getInventory() {
      return this.inventory;
   }

   public int getRows() {
      return this.rows;
   }
}
