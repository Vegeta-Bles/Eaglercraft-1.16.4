package net.minecraft.recipe;

import java.util.Iterator;
import net.minecraft.util.math.MathHelper;

public interface RecipeGridAligner<T> {
   default void alignRecipeToGrid(int gridWidth, int gridHeight, int gridOutputSlot, Recipe<?> recipe, Iterator<T> inputs, int amount) {
      int _snowman = gridWidth;
      int _snowmanx = gridHeight;
      if (recipe instanceof ShapedRecipe) {
         ShapedRecipe _snowmanxx = (ShapedRecipe)recipe;
         _snowman = _snowmanxx.getWidth();
         _snowmanx = _snowmanxx.getHeight();
      }

      int _snowmanxx = 0;

      for (int _snowmanxxx = 0; _snowmanxxx < gridHeight; _snowmanxxx++) {
         if (_snowmanxx == gridOutputSlot) {
            _snowmanxx++;
         }

         boolean _snowmanxxxx = (float)_snowmanx < (float)gridHeight / 2.0F;
         int _snowmanxxxxx = MathHelper.floor((float)gridHeight / 2.0F - (float)_snowmanx / 2.0F);
         if (_snowmanxxxx && _snowmanxxxxx > _snowmanxxx) {
            _snowmanxx += gridWidth;
            _snowmanxxx++;
         }

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < gridWidth; _snowmanxxxxxx++) {
            if (!inputs.hasNext()) {
               return;
            }

            _snowmanxxxx = (float)_snowman < (float)gridWidth / 2.0F;
            _snowmanxxxxx = MathHelper.floor((float)gridWidth / 2.0F - (float)_snowman / 2.0F);
            int _snowmanxxxxxxx = _snowman;
            boolean _snowmanxxxxxxxx = _snowmanxxxxxx < _snowman;
            if (_snowmanxxxx) {
               _snowmanxxxxxxx = _snowmanxxxxx + _snowman;
               _snowmanxxxxxxxx = _snowmanxxxxx <= _snowmanxxxxxx && _snowmanxxxxxx < _snowmanxxxxx + _snowman;
            }

            if (_snowmanxxxxxxxx) {
               this.acceptAlignedInput(inputs, _snowmanxx, amount, _snowmanxxx, _snowmanxxxxxx);
            } else if (_snowmanxxxxxxx == _snowmanxxxxxx) {
               _snowmanxx += gridWidth - _snowmanxxxxxx;
               break;
            }

            _snowmanxx++;
         }
      }
   }

   void acceptAlignedInput(Iterator<T> inputs, int slot, int amount, int gridX, int gridY);
}
