/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import java.util.Iterator;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.math.MathHelper;

public interface RecipeGridAligner<T> {
    default public void alignRecipeToGrid(int gridWidth, int gridHeight, int gridOutputSlot, Recipe<?> recipe, Iterator<T> inputs, int amount) {
        int _snowman3;
        int _snowman2 = gridWidth;
        _snowman3 = gridHeight;
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe)recipe;
            _snowman2 = shapedRecipe.getWidth();
            _snowman3 = shapedRecipe.getHeight();
        }
        int n = 0;
        block0: for (_snowman = 0; _snowman < gridHeight; ++_snowman) {
            if (n == gridOutputSlot) {
                ++n;
            }
            boolean bl = (float)_snowman3 < (float)gridHeight / 2.0f;
            int _snowman4 = MathHelper.floor((float)gridHeight / 2.0f - (float)_snowman3 / 2.0f);
            if (bl && _snowman4 > _snowman) {
                n += gridWidth;
                ++_snowman;
            }
            for (int i = 0; i < gridWidth; ++i) {
                if (!inputs.hasNext()) {
                    return;
                }
                bl = (float)_snowman2 < (float)gridWidth / 2.0f;
                _snowman4 = MathHelper.floor((float)gridWidth / 2.0f - (float)_snowman2 / 2.0f);
                int _snowman5 = _snowman2;
                boolean bl2 = _snowman = i < _snowman2;
                if (bl) {
                    _snowman5 = _snowman4 + _snowman2;
                    boolean bl3 = _snowman = _snowman4 <= i && i < _snowman4 + _snowman2;
                }
                if (_snowman) {
                    this.acceptAlignedInput(inputs, n, amount, _snowman, i);
                } else if (_snowman5 == i) {
                    n += gridWidth - i;
                    continue block0;
                }
                ++n;
            }
        }
    }

    public void acceptAlignedInput(Iterator<T> var1, int var2, int var3, int var4, int var5);
}

