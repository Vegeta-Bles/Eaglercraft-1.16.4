package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class EffectsChangedCriterion extends AbstractCriterion<EffectsChangedCriterion.Conditions> {
   private static final Identifier ID = new Identifier("effects_changed");

   public EffectsChangedCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public EffectsChangedCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      EntityEffectPredicate _snowmanxxx = EntityEffectPredicate.fromJson(_snowman.get("effects"));
      return new EffectsChangedCriterion.Conditions(_snowman, _snowmanxxx);
   }

   public void trigger(ServerPlayerEntity player) {
      this.test(player, _snowmanx -> _snowmanx.matches(player));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final EntityEffectPredicate effects;

      public Conditions(EntityPredicate.Extended player, EntityEffectPredicate effects) {
         super(EffectsChangedCriterion.ID, player);
         this.effects = effects;
      }

      public static EffectsChangedCriterion.Conditions create(EntityEffectPredicate effects) {
         return new EffectsChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, effects);
      }

      public boolean matches(ServerPlayerEntity player) {
         return this.effects.test((LivingEntity)player);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("effects", this.effects.toJson());
         return _snowman;
      }
   }
}
