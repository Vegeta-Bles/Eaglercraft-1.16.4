package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class UsedEnderEyeCriterion extends AbstractCriterion<UsedEnderEyeCriterion.Conditions> {
   private static final Identifier ID = new Identifier("used_ender_eye");

   public UsedEnderEyeCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public UsedEnderEyeCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      NumberRange.FloatRange _snowmanxxx = NumberRange.FloatRange.fromJson(_snowman.get("distance"));
      return new UsedEnderEyeCriterion.Conditions(_snowman, _snowmanxxx);
   }

   public void trigger(ServerPlayerEntity player, BlockPos strongholdPos) {
      double _snowman = player.getX() - (double)strongholdPos.getX();
      double _snowmanx = player.getZ() - (double)strongholdPos.getZ();
      double _snowmanxx = _snowman * _snowman + _snowmanx * _snowmanx;
      this.test(player, _snowmanxxx -> _snowmanxxx.matches(_snowman));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final NumberRange.FloatRange distance;

      public Conditions(EntityPredicate.Extended player, NumberRange.FloatRange distance) {
         super(UsedEnderEyeCriterion.ID, player);
         this.distance = distance;
      }

      public boolean matches(double distance) {
         return this.distance.testSqrt(distance);
      }
   }
}
