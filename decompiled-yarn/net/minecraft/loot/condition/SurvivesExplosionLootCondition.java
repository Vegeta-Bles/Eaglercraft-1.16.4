package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;

public class SurvivesExplosionLootCondition implements LootCondition {
   private static final SurvivesExplosionLootCondition INSTANCE = new SurvivesExplosionLootCondition();

   private SurvivesExplosionLootCondition() {
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.SURVIVES_EXPLOSION;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.EXPLOSION_RADIUS);
   }

   public boolean test(LootContext _snowman) {
      Float _snowmanx = _snowman.get(LootContextParameters.EXPLOSION_RADIUS);
      if (_snowmanx != null) {
         Random _snowmanxx = _snowman.getRandom();
         float _snowmanxxx = 1.0F / _snowmanx;
         return _snowmanxx.nextFloat() <= _snowmanxxx;
      } else {
         return true;
      }
   }

   public static LootCondition.Builder builder() {
      return () -> INSTANCE;
   }

   public static class Serializer implements JsonSerializer<SurvivesExplosionLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SurvivesExplosionLootCondition _snowman, JsonSerializationContext _snowman) {
      }

      public SurvivesExplosionLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         return SurvivesExplosionLootCondition.INSTANCE;
      }
   }
}
