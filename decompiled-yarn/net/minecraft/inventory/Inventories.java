package net.minecraft.inventory;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.collection.DefaultedList;

public class Inventories {
   public static ItemStack splitStack(List<ItemStack> stacks, int slot, int amount) {
      return slot >= 0 && slot < stacks.size() && !stacks.get(slot).isEmpty() && amount > 0 ? stacks.get(slot).split(amount) : ItemStack.EMPTY;
   }

   public static ItemStack removeStack(List<ItemStack> stacks, int slot) {
      return slot >= 0 && slot < stacks.size() ? stacks.set(slot, ItemStack.EMPTY) : ItemStack.EMPTY;
   }

   public static CompoundTag toTag(CompoundTag tag, DefaultedList<ItemStack> stacks) {
      return toTag(tag, stacks, true);
   }

   public static CompoundTag toTag(CompoundTag tag, DefaultedList<ItemStack> stacks, boolean setIfEmpty) {
      ListTag _snowman = new ListTag();

      for (int _snowmanx = 0; _snowmanx < stacks.size(); _snowmanx++) {
         ItemStack _snowmanxx = stacks.get(_snowmanx);
         if (!_snowmanxx.isEmpty()) {
            CompoundTag _snowmanxxx = new CompoundTag();
            _snowmanxxx.putByte("Slot", (byte)_snowmanx);
            _snowmanxx.toTag(_snowmanxxx);
            _snowman.add(_snowmanxxx);
         }
      }

      if (!_snowman.isEmpty() || setIfEmpty) {
         tag.put("Items", _snowman);
      }

      return tag;
   }

   public static void fromTag(CompoundTag tag, DefaultedList<ItemStack> stacks) {
      ListTag _snowman = tag.getList("Items", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
         int _snowmanxxx = _snowmanxx.getByte("Slot") & 255;
         if (_snowmanxxx >= 0 && _snowmanxxx < stacks.size()) {
            stacks.set(_snowmanxxx, ItemStack.fromTag(_snowmanxx));
         }
      }
   }

   public static int remove(Inventory inventory, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < inventory.size(); _snowmanx++) {
         ItemStack _snowmanxx = inventory.getStack(_snowmanx);
         int _snowmanxxx = remove(_snowmanxx, shouldRemove, maxCount - _snowman, dryRun);
         if (_snowmanxxx > 0 && !dryRun && _snowmanxx.isEmpty()) {
            inventory.setStack(_snowmanx, ItemStack.EMPTY);
         }

         _snowman += _snowmanxxx;
      }

      return _snowman;
   }

   public static int remove(ItemStack stack, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
      if (stack.isEmpty() || !shouldRemove.test(stack)) {
         return 0;
      } else if (dryRun) {
         return stack.getCount();
      } else {
         int _snowman = maxCount < 0 ? stack.getCount() : Math.min(maxCount, stack.getCount());
         stack.decrement(_snowman);
         return _snowman;
      }
   }
}
