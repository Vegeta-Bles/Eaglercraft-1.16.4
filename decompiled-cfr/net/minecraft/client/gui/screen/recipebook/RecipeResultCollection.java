/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.book.RecipeBook;

public class RecipeResultCollection {
    private final List<Recipe<?>> recipes;
    private final boolean singleOutput;
    private final Set<Recipe<?>> craftableRecipes = Sets.newHashSet();
    private final Set<Recipe<?>> fittingRecipes = Sets.newHashSet();
    private final Set<Recipe<?>> unlockedRecipes = Sets.newHashSet();

    public RecipeResultCollection(List<Recipe<?>> list) {
        this.recipes = ImmutableList.copyOf(list);
        this.singleOutput = list.size() <= 1 ? true : RecipeResultCollection.method_30295(list);
    }

    private static boolean method_30295(List<Recipe<?>> list) {
        int n = list.size();
        ItemStack _snowman2 = list.get(0).getOutput();
        for (_snowman = 1; _snowman < n; ++_snowman) {
            ItemStack itemStack = list.get(_snowman).getOutput();
            if (ItemStack.areItemsEqualIgnoreDamage(_snowman2, itemStack) && ItemStack.areTagsEqual(_snowman2, itemStack)) continue;
            return false;
        }
        return true;
    }

    public boolean isInitialized() {
        return !this.unlockedRecipes.isEmpty();
    }

    public void initialize(RecipeBook recipeBook) {
        for (Recipe<?> recipe : this.recipes) {
            if (!recipeBook.contains(recipe)) continue;
            this.unlockedRecipes.add(recipe);
        }
    }

    public void computeCraftables(RecipeFinder recipeFinder, int gridWidth, int gridHeight, RecipeBook recipeBook) {
        for (Recipe<?> recipe : this.recipes) {
            boolean bl = _snowman = recipe.fits(gridWidth, gridHeight) && recipeBook.contains(recipe);
            if (_snowman) {
                this.fittingRecipes.add(recipe);
            } else {
                this.fittingRecipes.remove(recipe);
            }
            if (_snowman && recipeFinder.findRecipe(recipe, null)) {
                this.craftableRecipes.add(recipe);
                continue;
            }
            this.craftableRecipes.remove(recipe);
        }
    }

    public boolean isCraftable(Recipe<?> recipe) {
        return this.craftableRecipes.contains(recipe);
    }

    public boolean hasCraftableRecipes() {
        return !this.craftableRecipes.isEmpty();
    }

    public boolean hasFittingRecipes() {
        return !this.fittingRecipes.isEmpty();
    }

    public List<Recipe<?>> getAllRecipes() {
        return this.recipes;
    }

    public List<Recipe<?>> getResults(boolean craftableOnly) {
        ArrayList arrayList = Lists.newArrayList();
        Set<Recipe<?>> _snowman2 = craftableOnly ? this.craftableRecipes : this.fittingRecipes;
        for (Recipe<?> recipe : this.recipes) {
            if (!_snowman2.contains(recipe)) continue;
            arrayList.add(recipe);
        }
        return arrayList;
    }

    public List<Recipe<?>> getRecipes(boolean craftable) {
        ArrayList arrayList = Lists.newArrayList();
        for (Recipe<?> recipe : this.recipes) {
            if (!this.fittingRecipes.contains(recipe) || this.craftableRecipes.contains(recipe) != craftable) continue;
            arrayList.add(recipe);
        }
        return arrayList;
    }

    public boolean hasSingleOutput() {
        return this.singleOutput;
    }
}

