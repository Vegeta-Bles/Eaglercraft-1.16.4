package net.minecraft.screen;

import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class HorseScreenHandler extends ScreenHandler {
   private final Inventory inventory;
   private final HorseBaseEntity entity;

   public HorseScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, HorseBaseEntity entity) {
      super(null, syncId);
      this.inventory = inventory;
      this.entity = entity;
      int _snowman = 3;
      inventory.onOpen(playerInventory.player);
      int _snowmanx = -18;
      this.addSlot(new Slot(inventory, 0, 8, 18) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.getItem() == Items.SADDLE && !this.hasStack() && entity.canBeSaddled();
         }

         @Override
         public boolean doDrawHoveringEffect() {
            return entity.canBeSaddled();
         }
      });
      this.addSlot(new Slot(inventory, 1, 8, 36) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return entity.isHorseArmor(stack);
         }

         @Override
         public boolean doDrawHoveringEffect() {
            return entity.hasArmorSlot();
         }

         @Override
         public int getMaxItemCount() {
            return 1;
         }
      });
      if (entity instanceof AbstractDonkeyEntity && ((AbstractDonkeyEntity)entity).hasChest()) {
         for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < ((AbstractDonkeyEntity)entity).getInventoryColumns(); _snowmanxxx++) {
               this.addSlot(new Slot(inventory, 2 + _snowmanxxx + _snowmanxx * ((AbstractDonkeyEntity)entity).getInventoryColumns(), 80 + _snowmanxxx * 18, 18 + _snowmanxx * 18));
            }
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.addSlot(new Slot(playerInventory, _snowmanxxx + _snowmanxx * 9 + 9, 8 + _snowmanxxx * 18, 102 + _snowmanxx * 18 + -18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
         this.addSlot(new Slot(playerInventory, _snowmanxx, 8 + _snowmanxx * 18, 142));
      }
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return this.inventory.canPlayerUse(player) && this.entity.isAlive() && this.entity.distanceTo(player) < 8.0F;
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         int _snowmanxxx = this.inventory.size();
         if (index < _snowmanxxx) {
            if (!this.insertItem(_snowmanxx, _snowmanxxx, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(1).canInsert(_snowmanxx) && !this.getSlot(1).hasStack()) {
            if (!this.insertItem(_snowmanxx, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(0).canInsert(_snowmanxx)) {
            if (!this.insertItem(_snowmanxx, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (_snowmanxxx <= 2 || !this.insertItem(_snowmanxx, 2, _snowmanxxx, false)) {
            int _snowmanxxxx = _snowmanxxx + 27;
            int _snowmanxxxxx = _snowmanxxxx + 9;
            if (index >= _snowmanxxxx && index < _snowmanxxxxx) {
               if (!this.insertItem(_snowmanxx, _snowmanxxx, _snowmanxxxx, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= _snowmanxxx && index < _snowmanxxxx) {
               if (!this.insertItem(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.insertItem(_snowmanxx, _snowmanxxxx, _snowmanxxxx, false)) {
               return ItemStack.EMPTY;
            }

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
