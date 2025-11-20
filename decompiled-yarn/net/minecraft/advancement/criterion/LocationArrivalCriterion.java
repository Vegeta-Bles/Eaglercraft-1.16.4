package net.minecraft.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class LocationArrivalCriterion extends AbstractCriterion<LocationArrivalCriterion.Conditions> {
   private final Identifier id;

   public LocationArrivalCriterion(Identifier id) {
      this.id = id;
   }

   @Override
   public Identifier getId() {
      return this.id;
   }

   public LocationArrivalCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      JsonObject _snowmanxxx = JsonHelper.getObject(_snowman, "location", _snowman);
      LocationPredicate _snowmanxxxx = LocationPredicate.fromJson(_snowmanxxx);
      return new LocationArrivalCriterion.Conditions(this.id, _snowman, _snowmanxxxx);
   }

   public void trigger(ServerPlayerEntity player) {
      this.test(player, _snowmanx -> _snowmanx.matches(player.getServerWorld(), player.getX(), player.getY(), player.getZ()));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final LocationPredicate location;

      public Conditions(Identifier id, EntityPredicate.Extended player, LocationPredicate location) {
         super(id, player);
         this.location = location;
      }

      public static LocationArrivalCriterion.Conditions create(LocationPredicate location) {
         return new LocationArrivalCriterion.Conditions(Criteria.LOCATION.id, EntityPredicate.Extended.EMPTY, location);
      }

      public static LocationArrivalCriterion.Conditions createSleptInBed() {
         return new LocationArrivalCriterion.Conditions(Criteria.SLEPT_IN_BED.id, EntityPredicate.Extended.EMPTY, LocationPredicate.ANY);
      }

      public static LocationArrivalCriterion.Conditions createHeroOfTheVillage() {
         return new LocationArrivalCriterion.Conditions(Criteria.HERO_OF_THE_VILLAGE.id, EntityPredicate.Extended.EMPTY, LocationPredicate.ANY);
      }

      public boolean matches(ServerWorld world, double x, double y, double z) {
         return this.location.test(world, x, y, z);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         _snowman.add("location", this.location.toJson());
         return _snowman;
      }
   }
}
