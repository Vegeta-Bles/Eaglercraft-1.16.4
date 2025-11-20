package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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

   public RecipeResultCollection(List<Recipe<?>> _snowman) {
      this.recipes = ImmutableList.copyOf(_snowman);
      if (_snowman.size() <= 1) {
         this.singleOutput = true;
      } else {
         this.singleOutput = method_30295(_snowman);
      }
   }

   private static boolean method_30295(List<Recipe<?>> _snowman) {
      int _snowmanx = _snowman.size();
      ItemStack _snowmanxx = _snowman.get(0).getOutput();

      for (int _snowmanxxx = 1; _snowmanxxx < _snowmanx; _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.get(_snowmanxxx).getOutput();
         if (!ItemStack.areItemsEqualIgnoreDamage(_snowmanxx, _snowmanxxxx) || !ItemStack.areTagsEqual(_snowmanxx, _snowmanxxxx)) {
            return false;
         }
      }

      return true;
   }

   public boolean isInitialized() {
      return !this.unlockedRecipes.isEmpty();
   }

   public void initialize(RecipeBook recipeBook) {
      for (Recipe<?> _snowman : this.recipes) {
         if (recipeBook.contains(_snowman)) {
            this.unlockedRecipes.add(_snowman);
         }
      }
   }

   public void computeCraftables(RecipeFinder recipeFinder, int gridWidth, int gridHeight, RecipeBook recipeBook) {
      for (Recipe<?> _snowman : this.recipes) {
         boolean _snowmanx = _snowman.fits(gridWidth, gridHeight) && recipeBook.contains(_snowman);
         if (_snowmanx) {
            this.fittingRecipes.add(_snowman);
         } else {
            this.fittingRecipes.remove(_snowman);
         }

         if (_snowmanx && recipeFinder.findRecipe(_snowman, null)) {
            this.craftableRecipes.add(_snowman);
         } else {
            this.craftableRecipes.remove(_snowman);
         }
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
      List<Recipe<?>> _snowman = Lists.newArrayList();
      Set<Recipe<?>> _snowmanx = craftableOnly ? this.craftableRecipes : this.fittingRecipes;

      for (Recipe<?> _snowmanxx : this.recipes) {
         if (_snowmanx.contains(_snowmanxx)) {
            _snowman.add(_snowmanxx);
         }
      }

      return _snowman;
   }

   public List<Recipe<?>> getRecipes(boolean craftable) {
      List<Recipe<?>> _snowman = Lists.newArrayList();

      for (Recipe<?> _snowmanx : this.recipes) {
         if (this.fittingRecipes.contains(_snowmanx) && this.craftableRecipes.contains(_snowmanx) == craftable) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   public boolean hasSingleOutput() {
      return this.singleOutput;
   }
}
