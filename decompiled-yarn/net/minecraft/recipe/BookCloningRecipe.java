package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BookCloningRecipe extends SpecialCraftingRecipe {
   public BookCloningRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      int _snowmanxx = 0;
      ItemStack _snowmanxxx = ItemStack.EMPTY;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            if (_snowmanxxxxx.getItem() == Items.WRITTEN_BOOK) {
               if (!_snowmanxxx.isEmpty()) {
                  return false;
               }

               _snowmanxxx = _snowmanxxxxx;
            } else {
               if (_snowmanxxxxx.getItem() != Items.WRITABLE_BOOK) {
                  return false;
               }

               _snowmanxx++;
            }
         }
      }

      return !_snowmanxxx.isEmpty() && _snowmanxxx.hasTag() && _snowmanxx > 0;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      int _snowmanx = 0;
      ItemStack _snowmanxx = ItemStack.EMPTY;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
         if (!_snowmanxxxx.isEmpty()) {
            if (_snowmanxxxx.getItem() == Items.WRITTEN_BOOK) {
               if (!_snowmanxx.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               _snowmanxx = _snowmanxxxx;
            } else {
               if (_snowmanxxxx.getItem() != Items.WRITABLE_BOOK) {
                  return ItemStack.EMPTY;
               }

               _snowmanx++;
            }
         }
      }

      if (!_snowmanxx.isEmpty() && _snowmanxx.hasTag() && _snowmanx >= 1 && WrittenBookItem.getGeneration(_snowmanxx) < 2) {
         ItemStack _snowmanxxxx = new ItemStack(Items.WRITTEN_BOOK, _snowmanx);
         CompoundTag _snowmanxxxxx = _snowmanxx.getTag().copy();
         _snowmanxxxxx.putInt("generation", WrittenBookItem.getGeneration(_snowmanxx) + 1);
         _snowmanxxxx.setTag(_snowmanxxxxx);
         return _snowmanxxxx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory _snowman) {
      DefaultedList<ItemStack> _snowmanx = DefaultedList.ofSize(_snowman.size(), ItemStack.EMPTY);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         ItemStack _snowmanxxx = _snowman.getStack(_snowmanxx);
         if (_snowmanxxx.getItem().hasRecipeRemainder()) {
            _snowmanx.set(_snowmanxx, new ItemStack(_snowmanxxx.getItem().getRecipeRemainder()));
         } else if (_snowmanxxx.getItem() instanceof WrittenBookItem) {
            ItemStack _snowmanxxxx = _snowmanxxx.copy();
            _snowmanxxxx.setCount(1);
            _snowmanx.set(_snowmanxx, _snowmanxxxx);
            break;
         }
      }

      return _snowmanx;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.BOOK_CLONING;
   }

   @Override
   public boolean fits(int width, int height) {
      return width >= 3 && height >= 3;
   }
}
