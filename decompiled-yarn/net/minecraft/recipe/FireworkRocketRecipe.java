package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FireworkRocketRecipe extends SpecialCraftingRecipe {
   private static final Ingredient PAPER = Ingredient.ofItems(Items.PAPER);
   private static final Ingredient DURATION_MODIFIER = Ingredient.ofItems(Items.GUNPOWDER);
   private static final Ingredient FIREWORK_STAR = Ingredient.ofItems(Items.FIREWORK_STAR);

   public FireworkRocketRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      boolean _snowmanxx = false;
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            if (PAPER.test(_snowmanxxxxx)) {
               if (_snowmanxx) {
                  return false;
               }

               _snowmanxx = true;
            } else if (DURATION_MODIFIER.test(_snowmanxxxxx)) {
               if (++_snowmanxxx > 3) {
                  return false;
               }
            } else if (!FIREWORK_STAR.test(_snowmanxxxxx)) {
               return false;
            }
         }
      }

      return _snowmanxx && _snowmanxxx >= 1;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      ItemStack _snowmanx = new ItemStack(Items.FIREWORK_ROCKET, 3);
      CompoundTag _snowmanxx = _snowmanx.getOrCreateSubTag("Fireworks");
      ListTag _snowmanxxx = new ListTag();
      int _snowmanxxxx = 0;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.size(); _snowmanxxxxx++) {
         ItemStack _snowmanxxxxxx = _snowman.getStack(_snowmanxxxxx);
         if (!_snowmanxxxxxx.isEmpty()) {
            if (DURATION_MODIFIER.test(_snowmanxxxxxx)) {
               _snowmanxxxx++;
            } else if (FIREWORK_STAR.test(_snowmanxxxxxx)) {
               CompoundTag _snowmanxxxxxxx = _snowmanxxxxxx.getSubTag("Explosion");
               if (_snowmanxxxxxxx != null) {
                  _snowmanxxx.add(_snowmanxxxxxxx);
               }
            }
         }
      }

      _snowmanxx.putByte("Flight", (byte)_snowmanxxxx);
      if (!_snowmanxxx.isEmpty()) {
         _snowmanxx.put("Explosions", _snowmanxxx);
      }

      return _snowmanx;
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public ItemStack getOutput() {
      return new ItemStack(Items.FIREWORK_ROCKET);
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.FIREWORK_ROCKET;
   }
}
