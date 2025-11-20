package net.minecraft.recipe;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FireworkStarFadeRecipe extends SpecialCraftingRecipe {
   private static final Ingredient INPUT_STAR = Ingredient.ofItems(Items.FIREWORK_STAR);

   public FireworkStarFadeRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      boolean _snowmanxx = false;
      boolean _snowmanxxx = false;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            if (_snowmanxxxxx.getItem() instanceof DyeItem) {
               _snowmanxx = true;
            } else {
               if (!INPUT_STAR.test(_snowmanxxxxx)) {
                  return false;
               }

               if (_snowmanxxx) {
                  return false;
               }

               _snowmanxxx = true;
            }
         }
      }

      return _snowmanxxx && _snowmanxx;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      List<Integer> _snowmanx = Lists.newArrayList();
      ItemStack _snowmanxx = null;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
         Item _snowmanxxxxx = _snowmanxxxx.getItem();
         if (_snowmanxxxxx instanceof DyeItem) {
            _snowmanx.add(((DyeItem)_snowmanxxxxx).getColor().getFireworkColor());
         } else if (INPUT_STAR.test(_snowmanxxxx)) {
            _snowmanxx = _snowmanxxxx.copy();
            _snowmanxx.setCount(1);
         }
      }

      if (_snowmanxx != null && !_snowmanx.isEmpty()) {
         _snowmanxx.getOrCreateSubTag("Explosion").putIntArray("FadeColors", _snowmanx);
         return _snowmanxx;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.FIREWORK_STAR_FADE;
   }
}
