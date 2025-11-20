package net.minecraft.inventory;

import java.util.Set;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Clearable;

public interface Inventory extends Clearable {
   int size();

   boolean isEmpty();

   ItemStack getStack(int slot);

   ItemStack removeStack(int slot, int amount);

   ItemStack removeStack(int slot);

   void setStack(int slot, ItemStack stack);

   default int getMaxCountPerStack() {
      return 64;
   }

   void markDirty();

   boolean canPlayerUse(PlayerEntity player);

   default void onOpen(PlayerEntity player) {
   }

   default void onClose(PlayerEntity player) {
   }

   default boolean isValid(int slot, ItemStack stack) {
      return true;
   }

   default int count(Item item) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < this.size(); _snowmanx++) {
         ItemStack _snowmanxx = this.getStack(_snowmanx);
         if (_snowmanxx.getItem().equals(item)) {
            _snowman += _snowmanxx.getCount();
         }
      }

      return _snowman;
   }

   default boolean containsAny(Set<Item> items) {
      for (int _snowman = 0; _snowman < this.size(); _snowman++) {
         ItemStack _snowmanx = this.getStack(_snowman);
         if (items.contains(_snowmanx.getItem()) && _snowmanx.getCount() > 0) {
            return true;
         }
      }

      return false;
   }
}
