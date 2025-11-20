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
      Recipe<?> lv = this.getLastRecipe();
      if (lv != null && !lv.isIgnoredInRecipeBook()) {
         player.unlockRecipes(Collections.singleton(lv));
         this.setLastRecipe(null);
      }
   }

   default boolean shouldCraftRecipe(World arg, ServerPlayerEntity player, Recipe<?> arg3) {
      if (!arg3.isIgnoredInRecipeBook() && arg.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING) && !player.getRecipeBook().contains(arg3)) {
         return false;
      } else {
         this.setLastRecipe(arg3);
         return true;
      }
   }
}
