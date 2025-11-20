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

   public boolean test(LootContext _snowman) {
      ServerWorld _snowmanx = _snowman.getWorld();
      return this.raining != null && this.raining != _snowmanx.isRaining() ? false : this.thundering == null || this.thundering == _snowmanx.isThundering();
   }

   public static class Serializer implements JsonSerializer<WeatherCheckLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, WeatherCheckLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.addProperty("raining", _snowman.raining);
         _snowman.addProperty("thundering", _snowman.thundering);
      }

      public WeatherCheckLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         Boolean _snowmanxx = _snowman.has("raining") ? JsonHelper.getBoolean(_snowman, "raining") : null;
         Boolean _snowmanxxx = _snowman.has("thundering") ? JsonHelper.getBoolean(_snowman, "thundering") : null;
         return new WeatherCheckLootCondition(_snowmanxx, _snowmanxxx);
      }
   }
}
