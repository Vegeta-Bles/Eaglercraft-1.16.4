package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.recipe.Recipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class RecipeUnlockedCriterion extends AbstractCriterion<RecipeUnlockedCriterion.Conditions> {
   private static final Identifier ID = new Identifier("recipe_unlocked");

   public RecipeUnlockedCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public RecipeUnlockedCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      Identifier _snowmanxxx = new Identifier(JsonHelper.getString(_snowman, "recipe"));
      return new RecipeUnlockedCriterion.Conditions(_snowman, _snowmanxxx);
   }

   public void trigger(ServerPlayerEntity player, Recipe<?> recipe) {
      this.test(player, _snowmanx -> _snowmanx.matches(recipe));
   }

   public static RecipeUnlockedCriterion.Conditions create(Identifier id) {
      return new RecipeUnlockedCriterion.Conditions(EntityPredicate.Extended.EMPTY, id);
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final Identifier recipe;

      public Conditions(EntityPredicate.Extended player, Identifier recipe) {
         super(RecipeUnlockedCriterion.ID, player);
         this.recipe = recipe;
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.addProperty("recipe", this.recipe.toString());
         return _snowman;
      }

      public boolean matches(Recipe<?> recipe) {
         return this.recipe.equals(recipe.getId());
      }
   }
}
