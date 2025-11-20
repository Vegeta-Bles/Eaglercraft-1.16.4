package net.minecraft.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MapCloningRecipe extends SpecialCraftingRecipe {
   public MapCloningRecipe(Identifier arg) {
      super(arg);
   }

   public boolean matches(CraftingInventory arg, World arg2) {
      int i = 0;
      ItemStack lv = ItemStack.EMPTY;

      for (int j = 0; j < arg.size(); j++) {
         ItemStack lv2 = arg.getStack(j);
         if (!lv2.isEmpty()) {
            if (lv2.getItem() == Items.FILLED_MAP) {
               if (!lv.isEmpty()) {
                  return false;
               }

               lv = lv2;
            } else {
               if (lv2.getItem() != Items.MAP) {
                  return false;
               }

               i++;
            }
         }
      }

      return !lv.isEmpty() && i > 0;
   }

   public ItemStack craft(CraftingInventory arg) {
      int i = 0;
      ItemStack lv = ItemStack.EMPTY;

      for (int j = 0; j < arg.size(); j++) {
         ItemStack lv2 = arg.getStack(j);
         if (!lv2.isEmpty()) {
            if (lv2.getItem() == Items.FILLED_MAP) {
               if (!lv.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               lv = lv2;
            } else {
               if (lv2.getItem() != Items.MAP) {
                  return ItemStack.EMPTY;
               }

               i++;
            }
         }
      }

      if (!lv.isEmpty() && i >= 1) {
         ItemStack lv3 = lv.copy();
         lv3.setCount(i + 1);
         return lv3;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean fits(int width, int height) {
      return width >= 3 && height >= 3;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.MAP_CLONING;
   }
}
