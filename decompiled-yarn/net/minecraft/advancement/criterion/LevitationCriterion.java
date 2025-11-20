package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.DistancePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class LevitationCriterion extends AbstractCriterion<LevitationCriterion.Conditions> {
   private static final Identifier ID = new Identifier("levitation");

   public LevitationCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public LevitationCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      DistancePredicate _snowmanxxx = DistancePredicate.fromJson(_snowman.get("distance"));
      NumberRange.IntRange _snowmanxxxx = NumberRange.IntRange.fromJson(_snowman.get("duration"));
      return new LevitationCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player, Vec3d startPos, int duration) {
      this.test(player, _snowmanxxx -> _snowmanxxx.matches(player, startPos, duration));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final DistancePredicate distance;
      private final NumberRange.IntRange duration;

      public Conditions(EntityPredicate.Extended player, DistancePredicate distance, NumberRange.IntRange duration) {
         super(LevitationCriterion.ID, player);
         this.distance = distance;
         this.duration = duration;
      }

      public static LevitationCriterion.Conditions create(DistancePredicate distance) {
         return new LevitationCriterion.Conditions(EntityPredicate.Extended.EMPTY, distance, NumberRange.IntRange.ANY);
      }

      public boolean matches(ServerPlayerEntity player, Vec3d startPos, int duration) {
         return !this.distance.test(startPos.x, startPos.y, startPos.z, player.getX(), player.getY(), player.getZ()) ? false : this.duration.test(duration);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("distance", this.distance.toJson());
         _snowman.add("duration", this.duration.toJson());
         return _snowman;
      }
   }
}
