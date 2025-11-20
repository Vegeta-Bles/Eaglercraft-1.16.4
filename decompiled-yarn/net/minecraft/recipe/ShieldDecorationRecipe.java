package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ShieldDecorationRecipe extends SpecialCraftingRecipe {
   public ShieldDecorationRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      ItemStack _snowmanxx = ItemStack.EMPTY;
      ItemStack _snowmanxxx = ItemStack.EMPTY;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            if (_snowmanxxxxx.getItem() instanceof BannerItem) {
               if (!_snowmanxxx.isEmpty()) {
                  return false;
               }

               _snowmanxxx = _snowmanxxxxx;
            } else {
               if (_snowmanxxxxx.getItem() != Items.SHIELD) {
                  return false;
               }

               if (!_snowmanxx.isEmpty()) {
                  return false;
               }

               if (_snowmanxxxxx.getSubTag("BlockEntityTag") != null) {
                  return false;
               }

               _snowmanxx = _snowmanxxxxx;
            }
         }
      }

      return !_snowmanxx.isEmpty() && !_snowmanxxx.isEmpty();
   }

   public ItemStack craft(CraftingInventory _snowman) {
      ItemStack _snowmanx = ItemStack.EMPTY;
      ItemStack _snowmanxx = ItemStack.EMPTY;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
         if (!_snowmanxxxx.isEmpty()) {
            if (_snowmanxxxx.getItem() instanceof BannerItem) {
               _snowmanx = _snowmanxxxx;
            } else if (_snowmanxxxx.getItem() == Items.SHIELD) {
               _snowmanxx = _snowmanxxxx.copy();
            }
         }
      }

      if (_snowmanxx.isEmpty()) {
         return _snowmanxx;
      } else {
         CompoundTag _snowmanxxxx = _snowmanx.getSubTag("BlockEntityTag");
         CompoundTag _snowmanxxxxx = _snowmanxxxx == null ? new CompoundTag() : _snowmanxxxx.copy();
         _snowmanxxxxx.putInt("Base", ((BannerItem)_snowmanx.getItem()).getColor().getId());
         _snowmanxx.putSubTag("BlockEntityTag", _snowmanxxxxx);
         return _snowmanxx;
      }
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHIELD_DECORATION;
   }
}
