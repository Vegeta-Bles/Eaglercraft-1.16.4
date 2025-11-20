package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TippedArrowRecipe extends SpecialCraftingRecipe {
   public TippedArrowRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      if (_snowman.getWidth() == 3 && _snowman.getHeight() == 3) {
         for (int _snowmanxx = 0; _snowmanxx < _snowman.getWidth(); _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.getHeight(); _snowmanxxx++) {
               ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxx + _snowmanxxx * _snowman.getWidth());
               if (_snowmanxxxx.isEmpty()) {
                  return false;
               }

               Item _snowmanxxxxx = _snowmanxxxx.getItem();
               if (_snowmanxx == 1 && _snowmanxxx == 1) {
                  if (_snowmanxxxxx != Items.LINGERING_POTION) {
                     return false;
                  }
               } else if (_snowmanxxxxx != Items.ARROW) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack craft(CraftingInventory _snowman) {
      ItemStack _snowmanx = _snowman.getStack(1 + _snowman.getWidth());
      if (_snowmanx.getItem() != Items.LINGERING_POTION) {
         return ItemStack.EMPTY;
      } else {
         ItemStack _snowmanxx = new ItemStack(Items.TIPPED_ARROW, 8);
         PotionUtil.setPotion(_snowmanxx, PotionUtil.getPotion(_snowmanx));
         PotionUtil.setCustomPotionEffects(_snowmanxx, PotionUtil.getCustomPotionEffects(_snowmanx));
         return _snowmanxx;
      }
   }

   @Override
   public boolean fits(int width, int height) {
      return width >= 2 && height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.TIPPED_ARROW;
   }
}
