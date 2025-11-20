package net.minecraft.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BookCloningRecipe extends SpecialCraftingRecipe {
   public BookCloningRecipe(Identifier arg) {
      super(arg);
   }

   public boolean matches(CraftingInventory arg, World arg2) {
      int i = 0;
      ItemStack lv = ItemStack.EMPTY;

      for (int j = 0; j < arg.size(); j++) {
         ItemStack lv2 = arg.getStack(j);
         if (!lv2.isEmpty()) {
            if (lv2.getItem() == Items.WRITTEN_BOOK) {
               if (!lv.isEmpty()) {
                  return false;
               }

               lv = lv2;
            } else {
               if (lv2.getItem() != Items.WRITABLE_BOOK) {
                  return false;
               }

               i++;
            }
         }
      }

      return !lv.isEmpty() && lv.hasTag() && i > 0;
   }

   public ItemStack craft(CraftingInventory arg) {
      int i = 0;
      ItemStack lv = ItemStack.EMPTY;

      for (int j = 0; j < arg.size(); j++) {
         ItemStack lv2 = arg.getStack(j);
         if (!lv2.isEmpty()) {
            if (lv2.getItem() == Items.WRITTEN_BOOK) {
               if (!lv.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               lv = lv2;
            } else {
               if (lv2.getItem() != Items.WRITABLE_BOOK) {
                  return ItemStack.EMPTY;
               }

               i++;
            }
         }
      }

      if (!lv.isEmpty() && lv.hasTag() && i >= 1 && WrittenBookItem.getGeneration(lv) < 2) {
         ItemStack lv3 = new ItemStack(Items.WRITTEN_BOOK, i);
         CompoundTag lv4 = lv.getTag().copy();
         lv4.putInt("generation", WrittenBookItem.getGeneration(lv) + 1);
         lv3.setTag(lv4);
         return lv3;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory arg) {
      DefaultedList<ItemStack> lv = DefaultedList.ofSize(arg.size(), ItemStack.EMPTY);

      for (int i = 0; i < lv.size(); i++) {
         ItemStack lv2 = arg.getStack(i);
         if (lv2.getItem().hasRecipeRemainder()) {
            lv.set(i, new ItemStack(lv2.getItem().getRecipeRemainder()));
         } else if (lv2.getItem() instanceof WrittenBookItem) {
            ItemStack lv3 = lv2.copy();
            lv3.setCount(1);
            lv.set(i, lv3);
            break;
         }
      }

      return lv;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.BOOK_CLONING;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean fits(int width, int height) {
      return width >= 3 && height >= 3;
   }
}
