package net.minecraft.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class RandomChanceLootCondition implements LootCondition {
   private final float chance;

   private RandomChanceLootCondition(float chance) {
      this.chance = chance;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.RANDOM_CHANCE;
   }

   public boolean test(LootContext _snowman) {
      return _snowman.getRandom().nextFloat() < this.chance;
   }

   public static LootCondition.Builder builder(float chance) {
      return () -> new RandomChanceLootCondition(chance);
   }

   public static class Serializer implements JsonSerializer<RandomChanceLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, RandomChanceLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.addProperty("chance", _snowman.chance);
      }

      public RandomChanceLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         return new RandomChanceLootCondition(JsonHelper.getFloat(_snowman, "chance"));
      }
   }
}
