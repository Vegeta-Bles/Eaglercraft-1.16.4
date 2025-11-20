package net.minecraft.recipe;

import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BannerDuplicateRecipe extends SpecialCraftingRecipe {
   public BannerDuplicateRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      DyeColor _snowmanxx = null;
      ItemStack _snowmanxxx = null;
      ItemStack _snowmanxxxx = null;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.size(); _snowmanxxxxx++) {
         ItemStack _snowmanxxxxxx = _snowman.getStack(_snowmanxxxxx);
         Item _snowmanxxxxxxx = _snowmanxxxxxx.getItem();
         if (_snowmanxxxxxxx instanceof BannerItem) {
            BannerItem _snowmanxxxxxxxx = (BannerItem)_snowmanxxxxxxx;
            if (_snowmanxx == null) {
               _snowmanxx = _snowmanxxxxxxxx.getColor();
            } else if (_snowmanxx != _snowmanxxxxxxxx.getColor()) {
               return false;
            }

            int _snowmanxxxxxxxxx = BannerBlockEntity.getPatternCount(_snowmanxxxxxx);
            if (_snowmanxxxxxxxxx > 6) {
               return false;
            }

            if (_snowmanxxxxxxxxx > 0) {
               if (_snowmanxxx != null) {
                  return false;
               }

               _snowmanxxx = _snowmanxxxxxx;
            } else {
               if (_snowmanxxxx != null) {
                  return false;
               }

               _snowmanxxxx = _snowmanxxxxxx;
            }
         }
      }

      return _snowmanxxx != null && _snowmanxxxx != null;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         ItemStack _snowmanxx = _snowman.getStack(_snowmanx);
         if (!_snowmanxx.isEmpty()) {
            int _snowmanxxx = BannerBlockEntity.getPatternCount(_snowmanxx);
            if (_snowmanxxx > 0 && _snowmanxxx <= 6) {
               ItemStack _snowmanxxxx = _snowmanxx.copy();
               _snowmanxxxx.setCount(1);
               return _snowmanxxxx;
            }
         }
      }

      return ItemStack.EMPTY;
   }

   public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory _snowman) {
      DefaultedList<ItemStack> _snowmanx = DefaultedList.ofSize(_snowman.size(), ItemStack.EMPTY);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         ItemStack _snowmanxxx = _snowman.getStack(_snowmanxx);
         if (!_snowmanxxx.isEmpty()) {
            if (_snowmanxxx.getItem().hasRecipeRemainder()) {
               _snowmanx.set(_snowmanxx, new ItemStack(_snowmanxxx.getItem().getRecipeRemainder()));
            } else if (_snowmanxxx.hasTag() && BannerBlockEntity.getPatternCount(_snowmanxxx) > 0) {
               ItemStack _snowmanxxxx = _snowmanxxx.copy();
               _snowmanxxxx.setCount(1);
               _snowmanx.set(_snowmanxx, _snowmanxxxx);
            }
         }
      }

      return _snowmanx;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.BANNER_DUPLICATE;
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }
}
