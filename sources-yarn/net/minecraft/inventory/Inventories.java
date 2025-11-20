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
      ListTag lv = new ListTag();

      for (int i = 0; i < stacks.size(); i++) {
         ItemStack lv2 = stacks.get(i);
         if (!lv2.isEmpty()) {
            CompoundTag lv3 = new CompoundTag();
            lv3.putByte("Slot", (byte)i);
            lv2.toTag(lv3);
            lv.add(lv3);
         }
      }

      if (!lv.isEmpty() || setIfEmpty) {
         tag.put("Items", lv);
      }

      return tag;
   }

   public static void fromTag(CompoundTag tag, DefaultedList<ItemStack> stacks) {
      ListTag lv = tag.getList("Items", 10);

      for (int i = 0; i < lv.size(); i++) {
         CompoundTag lv2 = lv.getCompound(i);
         int j = lv2.getByte("Slot") & 255;
         if (j >= 0 && j < stacks.size()) {
            stacks.set(j, ItemStack.fromTag(lv2));
         }
      }
   }

   public static int remove(Inventory inventory, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
      int j = 0;

      for (int k = 0; k < inventory.size(); k++) {
         ItemStack lv = inventory.getStack(k);
         int l = remove(lv, shouldRemove, maxCount - j, dryRun);
         if (l > 0 && !dryRun && lv.isEmpty()) {
            inventory.setStack(k, ItemStack.EMPTY);
         }

         j += l;
      }

      return j;
   }

   public static int remove(ItemStack stack, Predicate<ItemStack> shouldRemove, int maxCount, boolean dryRun) {
      if (stack.isEmpty() || !shouldRemove.test(stack)) {
         return 0;
      } else if (dryRun) {
         return stack.getCount();
      } else {
         int j = maxCount < 0 ? stack.getCount() : Math.min(maxCount, stack.getCount());
         stack.decrement(j);
         return j;
      }
   }
}
