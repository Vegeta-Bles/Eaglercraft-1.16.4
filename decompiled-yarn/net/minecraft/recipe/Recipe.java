package net.minecraft.recipe;

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

   boolean fits(int width, int height);

   ItemStack getOutput();

   default DefaultedList<ItemStack> getRemainingStacks(C _snowman) {
      DefaultedList<ItemStack> _snowmanx = DefaultedList.ofSize(_snowman.size(), ItemStack.EMPTY);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         Item _snowmanxxx = _snowman.getStack(_snowmanxx).getItem();
         if (_snowmanxxx.hasRecipeRemainder()) {
            _snowmanx.set(_snowmanxx, new ItemStack(_snowmanxxx.getRecipeRemainder()));
         }
      }

      return _snowmanx;
   }

   default DefaultedList<Ingredient> getPreviewInputs() {
      return DefaultedList.of();
   }

   default boolean isIgnoredInRecipeBook() {
      return false;
   }

   default String getGroup() {
      return "";
   }

   default ItemStack getRecipeKindIcon() {
      return new ItemStack(Blocks.CRAFTING_TABLE);
   }

   Identifier getId();

   RecipeSerializer<?> getSerializer();

   RecipeType<?> getType();
}
