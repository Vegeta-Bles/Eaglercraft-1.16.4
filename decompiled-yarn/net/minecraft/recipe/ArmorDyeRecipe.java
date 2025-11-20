package net.minecraft.recipe;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ArmorDyeRecipe extends SpecialCraftingRecipe {
   public ArmorDyeRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      ItemStack _snowmanxx = ItemStack.EMPTY;
      List<ItemStack> _snowmanxxx = Lists.newArrayList();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            if (_snowmanxxxxx.getItem() instanceof DyeableItem) {
               if (!_snowmanxx.isEmpty()) {
                  return false;
               }

               _snowmanxx = _snowmanxxxxx;
            } else {
               if (!(_snowmanxxxxx.getItem() instanceof DyeItem)) {
                  return false;
               }

               _snowmanxxx.add(_snowmanxxxxx);
            }
         }
      }

      return !_snowmanxx.isEmpty() && !_snowmanxxx.isEmpty();
   }

   public ItemStack craft(CraftingInventory _snowman) {
      List<DyeItem> _snowmanx = Lists.newArrayList();
      ItemStack _snowmanxx = ItemStack.EMPTY;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
         if (!_snowmanxxxx.isEmpty()) {
            Item _snowmanxxxxx = _snowmanxxxx.getItem();
            if (_snowmanxxxxx instanceof DyeableItem) {
               if (!_snowmanxx.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               _snowmanxx = _snowmanxxxx.copy();
            } else {
               if (!(_snowmanxxxxx instanceof DyeItem)) {
                  return ItemStack.EMPTY;
               }

               _snowmanx.add((DyeItem)_snowmanxxxxx);
            }
         }
      }

      return !_snowmanxx.isEmpty() && !_snowmanx.isEmpty() ? DyeableItem.blendAndSetColor(_snowmanxx, _snowmanx) : ItemStack.EMPTY;
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.ARMOR_DYE;
   }
}
