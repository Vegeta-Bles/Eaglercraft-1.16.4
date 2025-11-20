package net.minecraft.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class WeatherCheckLootCondition implements LootCondition {
   @Nullable
   private final Boolean raining;
   @Nullable
   private final Boolean thundering;

   private WeatherCheckLootCondition(@Nullable Boolean raining, @Nullable Boolean thundering) {
      this.raining = raining;
      this.thundering = thundering;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.WEATHER_CHECK;
   }

   public boolean test(LootContext arg) {
      ServerWorld lv = arg.getWorld();
      return this.raining != null && this.raining != lv.isRaining() ? false : this.thundering == null || this.thundering == lv.isThundering();
   }

   public static class Serializer implements JsonSerializer<WeatherCheckLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, WeatherCheckLootCondition arg, JsonSerializationContext jsonSerializationContext) {
         jsonObject.addProperty("raining", arg.raining);
         jsonObject.addProperty("thundering", arg.thundering);
      }

      public WeatherCheckLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
         Boolean boolean_ = jsonObject.has("raining") ? JsonHelper.getBoolean(jsonObject, "raining") : null;
         Boolean boolean2 = jsonObject.has("thundering") ? JsonHelper.getBoolean(jsonObject, "thundering") : null;
         return new WeatherCheckLootCondition(boolean_, boolean2);
      }
   }
}
