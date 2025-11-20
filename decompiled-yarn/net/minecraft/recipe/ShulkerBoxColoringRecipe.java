package net.minecraft.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ShulkerBoxColoringRecipe extends SpecialCraftingRecipe {
   public ShulkerBoxColoringRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      int _snowmanxx = 0;
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            if (Block.getBlockFromItem(_snowmanxxxxx.getItem()) instanceof ShulkerBoxBlock) {
               _snowmanxx++;
            } else {
               if (!(_snowmanxxxxx.getItem() instanceof DyeItem)) {
                  return false;
               }

               _snowmanxxx++;
            }

            if (_snowmanxxx > 1 || _snowmanxx > 1) {
               return false;
            }
         }
      }

      return _snowmanxx == 1 && _snowmanxxx == 1;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      ItemStack _snowmanx = ItemStack.EMPTY;
      DyeItem _snowmanxx = (DyeItem)Items.WHITE_DYE;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
         if (!_snowmanxxxx.isEmpty()) {
            Item _snowmanxxxxx = _snowmanxxxx.getItem();
            if (Block.getBlockFromItem(_snowmanxxxxx) instanceof ShulkerBoxBlock) {
               _snowmanx = _snowmanxxxx;
            } else if (_snowmanxxxxx instanceof DyeItem) {
               _snowmanxx = (DyeItem)_snowmanxxxxx;
            }
         }
      }

      ItemStack _snowmanxxxx = ShulkerBoxBlock.getItemStack(_snowmanxx.getColor());
      if (_snowmanx.hasTag()) {
         _snowmanxxxx.setTag(_snowmanx.getTag().copy());
      }

      return _snowmanxxxx;
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHULKER_BOX;
   }
}
