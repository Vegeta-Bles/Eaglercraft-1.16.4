package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class TargetHitCriterion extends AbstractCriterion<TargetHitCriterion.Conditions> {
   private static final Identifier ID = new Identifier("target_hit");

   public TargetHitCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public TargetHitCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      NumberRange.IntRange _snowmanxxx = NumberRange.IntRange.fromJson(_snowman.get("signal_strength"));
      EntityPredicate.Extended _snowmanxxxx = EntityPredicate.Extended.getInJson(_snowman, "projectile", _snowman);
      return new TargetHitCriterion.Conditions(_snowman, _snowmanxxx, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player, Entity projectile, Vec3d hitPos, int signalStrength) {
      LootContext _snowman = EntityPredicate.createAdvancementEntityLootContext(player, projectile);
      this.test(player, _snowmanxxx -> _snowmanxxx.test(_snowman, hitPos, signalStrength));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final NumberRange.IntRange signalStrength;
      private final EntityPredicate.Extended projectile;

      public Conditions(EntityPredicate.Extended player, NumberRange.IntRange signalStrength, EntityPredicate.Extended projectile) {
         super(TargetHitCriterion.ID, player);
         this.signalStrength = signalStrength;
         this.projectile = projectile;
      }

      public static TargetHitCriterion.Conditions create(NumberRange.IntRange signalStrength, EntityPredicate.Extended _snowman) {
         return new TargetHitCriterion.Conditions(EntityPredicate.Extended.EMPTY, signalStrength, _snowman);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("signal_strength", this.signalStrength.toJson());
         _snowman.add("projectile", this.projectile.toJson(predicateSerializer));
         return _snowman;
      }

      public boolean test(LootContext projectileContext, Vec3d hitPos, int signalStrength) {
         return !this.signalStrength.test(signalStrength) ? false : this.projectile.test(projectileContext);
      }
   }
}
