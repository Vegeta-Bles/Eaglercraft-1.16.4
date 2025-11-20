package net.minecraft.server.network;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.s2c.play.UnlockRecipesS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.recipe.book.RecipeBookOptions;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipeBook extends RecipeBook {
   private static final Logger LOGGER = LogManager.getLogger();

   public ServerRecipeBook() {
   }

   public int unlockRecipes(Collection<Recipe<?>> recipes, ServerPlayerEntity player) {
      List<Identifier> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (Recipe<?> _snowmanxx : recipes) {
         Identifier _snowmanxxx = _snowmanxx.getId();
         if (!this.recipes.contains(_snowmanxxx) && !_snowmanxx.isIgnoredInRecipeBook()) {
            this.add(_snowmanxxx);
            this.display(_snowmanxxx);
            _snowman.add(_snowmanxxx);
            Criteria.RECIPE_UNLOCKED.trigger(player, _snowmanxx);
            _snowmanx++;
         }
      }

      this.sendUnlockRecipesPacket(UnlockRecipesS2CPacket.Action.ADD, player, _snowman);
      return _snowmanx;
   }

   public int lockRecipes(Collection<Recipe<?>> recipes, ServerPlayerEntity player) {
      List<Identifier> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (Recipe<?> _snowmanxx : recipes) {
         Identifier _snowmanxxx = _snowmanxx.getId();
         if (this.recipes.contains(_snowmanxxx)) {
            this.remove(_snowmanxxx);
            _snowman.add(_snowmanxxx);
            _snowmanx++;
         }
      }

      this.sendUnlockRecipesPacket(UnlockRecipesS2CPacket.Action.REMOVE, player, _snowman);
      return _snowmanx;
   }

   private void sendUnlockRecipesPacket(UnlockRecipesS2CPacket.Action action, ServerPlayerEntity player, List<Identifier> recipeIds) {
      player.networkHandler.sendPacket(new UnlockRecipesS2CPacket(action, recipeIds, Collections.emptyList(), this.getOptions()));
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      this.getOptions().toTag(_snowman);
      ListTag _snowmanx = new ListTag();

      for (Identifier _snowmanxx : this.recipes) {
         _snowmanx.add(StringTag.of(_snowmanxx.toString()));
      }

      _snowman.put("recipes", _snowmanx);
      ListTag _snowmanxx = new ListTag();

      for (Identifier _snowmanxxx : this.toBeDisplayed) {
         _snowmanxx.add(StringTag.of(_snowmanxxx.toString()));
      }

      _snowman.put("toBeDisplayed", _snowmanxx);
      return _snowman;
   }

   public void fromTag(CompoundTag tag, RecipeManager _snowman) {
      this.setOptions(RecipeBookOptions.fromTag(tag));
      ListTag _snowmanx = tag.getList("recipes", 8);
      this.handleList(_snowmanx, this::add, _snowman);
      ListTag _snowmanxx = tag.getList("toBeDisplayed", 8);
      this.handleList(_snowmanxx, this::display, _snowman);
   }

   private void handleList(ListTag list, Consumer<Recipe<?>> handler, RecipeManager _snowman) {
      for (int _snowmanx = 0; _snowmanx < list.size(); _snowmanx++) {
         String _snowmanxx = list.getString(_snowmanx);

         try {
            Identifier _snowmanxxx = new Identifier(_snowmanxx);
            Optional<? extends Recipe<?>> _snowmanxxxx = _snowman.get(_snowmanxxx);
            if (!_snowmanxxxx.isPresent()) {
               LOGGER.error("Tried to load unrecognized recipe: {} removed now.", _snowmanxxx);
            } else {
               handler.accept((Recipe<?>)_snowmanxxxx.get());
            }
         } catch (InvalidIdentifierException var8) {
            LOGGER.error("Tried to load improperly formatted recipe: {} removed now.", _snowmanxx);
         }
      }
   }

   public void sendInitRecipesPacket(ServerPlayerEntity player) {
      player.networkHandler.sendPacket(new UnlockRecipesS2CPacket(UnlockRecipesS2CPacket.Action.INIT, this.recipes, this.toBeDisplayed, this.getOptions()));
   }
}
