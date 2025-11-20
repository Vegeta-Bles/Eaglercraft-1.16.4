package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class MapExtendingRecipe extends ShapedRecipe {
   public MapExtendingRecipe(Identifier _snowman) {
      super(
         _snowman,
         "",
         3,
         3,
         DefaultedList.copyOf(
            Ingredient.EMPTY,
            Ingredient.ofItems(Items.PAPER),
            Ingredient.ofItems(Items.PAPER),
            Ingredient.ofItems(Items.PAPER),
            Ingredient.ofItems(Items.PAPER),
            Ingredient.ofItems(Items.FILLED_MAP),
            Ingredient.ofItems(Items.PAPER),
            Ingredient.ofItems(Items.PAPER),
            Ingredient.ofItems(Items.PAPER),
            Ingredient.ofItems(Items.PAPER)
         ),
         new ItemStack(Items.MAP)
      );
   }

   @Override
   public boolean matches(CraftingInventory _snowman, World _snowman) {
      if (!super.matches(_snowman, _snowman)) {
         return false;
      } else {
         ItemStack _snowmanxx = ItemStack.EMPTY;

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size() && _snowmanxx.isEmpty(); _snowmanxxx++) {
            ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
            if (_snowmanxxxx.getItem() == Items.FILLED_MAP) {
               _snowmanxx = _snowmanxxxx;
            }
         }

         if (_snowmanxx.isEmpty()) {
            return false;
         } else {
            MapState _snowmanxxxx = FilledMapItem.getOrCreateMapState(_snowmanxx, _snowman);
            if (_snowmanxxxx == null) {
               return false;
            } else {
               return this.matches(_snowmanxxxx) ? false : _snowmanxxxx.scale < 4;
            }
         }
      }
   }

   private boolean matches(MapState state) {
      if (state.icons != null) {
         for (MapIcon _snowman : state.icons.values()) {
            if (_snowman.getType() == MapIcon.Type.MANSION || _snowman.getType() == MapIcon.Type.MONUMENT) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public ItemStack craft(CraftingInventory _snowman) {
      ItemStack _snowmanx = ItemStack.EMPTY;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size() && _snowmanx.isEmpty(); _snowmanxx++) {
         ItemStack _snowmanxxx = _snowman.getStack(_snowmanxx);
         if (_snowmanxxx.getItem() == Items.FILLED_MAP) {
            _snowmanx = _snowmanxxx;
         }
      }

      _snowmanx = _snowmanx.copy();
      _snowmanx.setCount(1);
      _snowmanx.getOrCreateTag().putInt("map_scale_direction", 1);
      return _snowmanx;
   }

   @Override
   public boolean isIgnoredInRecipeBook() {
      return true;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.MAP_EXTENDING;
   }
}
