package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MapCloningRecipe extends SpecialCraftingRecipe {
   public MapCloningRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      int _snowmanxx = 0;
      ItemStack _snowmanxxx = ItemStack.EMPTY;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            if (_snowmanxxxxx.getItem() == Items.FILLED_MAP) {
               if (!_snowmanxxx.isEmpty()) {
                  return false;
               }

               _snowmanxxx = _snowmanxxxxx;
            } else {
               if (_snowmanxxxxx.getItem() != Items.MAP) {
                  return false;
               }

               _snowmanxx++;
            }
         }
      }

      return !_snowmanxxx.isEmpty() && _snowmanxx > 0;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      int _snowmanx = 0;
      ItemStack _snowmanxx = ItemStack.EMPTY;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
         if (!_snowmanxxxx.isEmpty()) {
            if (_snowmanxxxx.getItem() == Items.FILLED_MAP) {
               if (!_snowmanxx.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               _snowmanxx = _snowmanxxxx;
            } else {
               if (_snowmanxxxx.getItem() != Items.MAP) {
                  return ItemStack.EMPTY;
               }

               _snowmanx++;
            }
         }
      }

      if (!_snowmanxx.isEmpty() && _snowmanx >= 1) {
         ItemStack _snowmanxxxx = _snowmanxx.copy();
         _snowmanxxxx.setCount(_snowmanx + 1);
         return _snowmanxxxx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public boolean fits(int width, int height) {
      return width >= 3 && height >= 3;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.MAP_CLONING;
   }
}
