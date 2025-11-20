package net.minecraft.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ShieldDecorationRecipe extends SpecialCraftingRecipe {
   public ShieldDecorationRecipe(Identifier arg) {
      super(arg);
   }

   public boolean matches(CraftingInventory arg, World arg2) {
      ItemStack lv = ItemStack.EMPTY;
      ItemStack lv2 = ItemStack.EMPTY;

      for (int i = 0; i < arg.size(); i++) {
         ItemStack lv3 = arg.getStack(i);
         if (!lv3.isEmpty()) {
            if (lv3.getItem() instanceof BannerItem) {
               if (!lv2.isEmpty()) {
                  return false;
               }

               lv2 = lv3;
            } else {
               if (lv3.getItem() != Items.SHIELD) {
                  return false;
               }

               if (!lv.isEmpty()) {
                  return false;
               }

               if (lv3.getSubTag("BlockEntityTag") != null) {
                  return false;
               }

               lv = lv3;
            }
         }
      }

      return !lv.isEmpty() && !lv2.isEmpty();
   }

   public ItemStack craft(CraftingInventory arg) {
      ItemStack lv = ItemStack.EMPTY;
      ItemStack lv2 = ItemStack.EMPTY;

      for (int i = 0; i < arg.size(); i++) {
         ItemStack lv3 = arg.getStack(i);
         if (!lv3.isEmpty()) {
            if (lv3.getItem() instanceof BannerItem) {
               lv = lv3;
            } else if (lv3.getItem() == Items.SHIELD) {
               lv2 = lv3.copy();
            }
         }
      }

      if (lv2.isEmpty()) {
         return lv2;
      } else {
         CompoundTag lv4 = lv.getSubTag("BlockEntityTag");
         CompoundTag lv5 = lv4 == null ? new CompoundTag() : lv4.copy();
         lv5.putInt("Base", ((BannerItem)lv.getItem()).getColor().getId());
         lv2.putSubTag("BlockEntityTag", lv5);
         return lv2;
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHIELD_DECORATION;
   }
}
