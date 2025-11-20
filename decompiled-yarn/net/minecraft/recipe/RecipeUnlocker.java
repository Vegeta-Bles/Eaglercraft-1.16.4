package net.minecraft.recipe;

import java.util.Collections;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public interface RecipeUnlocker {
   void setLastRecipe(@Nullable Recipe<?> recipe);

   @Nullable
   Recipe<?> getLastRecipe();

   default void unlockLastRecipe(PlayerEntity player) {
      Recipe<?> _snowman = this.getLastRecipe();
      if (_snowman != null && !_snowman.isIgnoredInRecipeBook()) {
         player.unlockRecipes(Collections.singleton(_snowman));
         this.setLastRecipe(null);
      }
   }

   default boolean shouldCraftRecipe(World _snowman, ServerPlayerEntity player, Recipe<?> _snowman) {
      if (!_snowman.isIgnoredInRecipeBook() && _snowman.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING) && !player.getRecipeBook().contains(_snowman)) {
         return false;
      } else {
         this.setLastRecipe(_snowman);
         return true;
      }
   }
}
