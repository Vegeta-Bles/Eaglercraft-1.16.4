package net.minecraft.inventory;

import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class EnderChestInventory extends SimpleInventory {
   private EnderChestBlockEntity activeBlockEntity;

   public EnderChestInventory() {
      super(27);
   }

   public void setActiveBlockEntity(EnderChestBlockEntity blockEntity) {
      this.activeBlockEntity = blockEntity;
   }

   @Override
   public void readTags(ListTag tags) {
      for (int _snowman = 0; _snowman < this.size(); _snowman++) {
         this.setStack(_snowman, ItemStack.EMPTY);
      }

      for (int _snowman = 0; _snowman < tags.size(); _snowman++) {
         CompoundTag _snowmanx = tags.getCompound(_snowman);
         int _snowmanxx = _snowmanx.getByte("Slot") & 255;
         if (_snowmanxx >= 0 && _snowmanxx < this.size()) {
            this.setStack(_snowmanxx, ItemStack.fromTag(_snowmanx));
         }
      }
   }

   @Override
   public ListTag getTags() {
      ListTag _snowman = new ListTag();

      for (int _snowmanx = 0; _snowmanx < this.size(); _snowmanx++) {
         ItemStack _snowmanxx = this.getStack(_snowmanx);
         if (!_snowmanxx.isEmpty()) {
            CompoundTag _snowmanxxx = new CompoundTag();
            _snowmanxxx.putByte("Slot", (byte)_snowmanx);
            _snowmanxx.toTag(_snowmanxxx);
            _snowman.add(_snowmanxxx);
         }
      }

      return _snowman;
   }

   @Override
   public boolean canPlayerUse(PlayerEntity player) {
      return this.activeBlockEntity != null && !this.activeBlockEntity.canPlayerUse(player) ? false : super.canPlayerUse(player);
   }

   @Override
   public void onOpen(PlayerEntity player) {
      if (this.activeBlockEntity != null) {
         this.activeBlockEntity.onOpen();
      }

      super.onOpen(player);
   }

   @Override
   public void onClose(PlayerEntity player) {
      if (this.activeBlockEntity != null) {
         this.activeBlockEntity.onClose();
      }

      super.onClose(player);
      this.activeBlockEntity = null;
   }
}
