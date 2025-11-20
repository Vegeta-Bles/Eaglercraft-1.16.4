package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class TickCriterion extends AbstractCriterion<TickCriterion.Conditions> {
   public static final Identifier ID = new Identifier("tick");

   public TickCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public TickCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      return new TickCriterion.Conditions(_snowman);
   }

   public void trigger(ServerPlayerEntity player) {
      this.test(player, _snowman -> true);
   }

   public static class Conditions extends AbstractCriterionConditions {
      public Conditions(EntityPredicate.Extended player) {
         super(TickCriterion.ID, player);
      }
   }
}
