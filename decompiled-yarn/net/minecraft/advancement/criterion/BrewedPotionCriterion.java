package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.potion.Potion;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class BrewedPotionCriterion extends AbstractCriterion<BrewedPotionCriterion.Conditions> {
   private static final Identifier ID = new Identifier("brewed_potion");

   public BrewedPotionCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public BrewedPotionCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      Potion _snowmanxxx = null;
      if (_snowman.has("potion")) {
         Identifier _snowmanxxxx = new Identifier(JsonHelper.getString(_snowman, "potion"));
         _snowmanxxx = Registry.POTION.getOrEmpty(_snowmanxxxx).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + _snowman + "'"));
      }

      return new BrewedPotionCriterion.Conditions(_snowman, _snowmanxxx);
   }

   public void trigger(ServerPlayerEntity player, Potion potion) {
      this.test(player, _snowmanx -> _snowmanx.matches(potion));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final Potion potion;

      public Conditions(EntityPredicate.Extended player, @Nullable Potion potion) {
         super(BrewedPotionCriterion.ID, player);
         this.potion = potion;
      }

      public static BrewedPotionCriterion.Conditions any() {
         return new BrewedPotionCriterion.Conditions(EntityPredicate.Extended.EMPTY, null);
      }

      public boolean matches(Potion potion) {
         return this.potion == null || this.potion == potion;
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         if (this.potion != null) {
            _snowman.addProperty("potion", Registry.POTION.getId(this.potion).toString());
         }

         return _snowman;
      }
   }
}
