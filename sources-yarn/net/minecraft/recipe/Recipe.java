package net.minecraft.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public interface Recipe<C extends Inventory> {
   boolean matches(C inv, World world);

   ItemStack craft(C inv);

   @Environment(EnvType.CLIENT)
   boolean fits(int width, int height);

   ItemStack getOutput();

   default DefaultedList<ItemStack> getRemainingStacks(C arg) {
      DefaultedList<ItemStack> lv = DefaultedList.ofSize(arg.size(), ItemStack.EMPTY);

      for (int i = 0; i < lv.size(); i++) {
         Item lv2 = arg.getStack(i).getItem();
         if (lv2.hasRecipeRemainder()) {
            lv.set(i, new ItemStack(lv2.getRecipeRemainder()));
         }
      }

      return lv;
   }

   default DefaultedList<Ingredient> getPreviewInputs() {
      return DefaultedList.of();
   }

   default boolean isIgnoredInRecipeBook() {
      return false;
   }

   @Environment(EnvType.CLIENT)
   default String getGroup() {
      return "";
   }

   @Environment(EnvType.CLIENT)
   default ItemStack getRecipeKindIcon() {
      return new ItemStack(Blocks.CRAFTING_TABLE);
   }

   Identifier getId();

   RecipeSerializer<?> getSerializer();

   RecipeType<?> getType();
}
