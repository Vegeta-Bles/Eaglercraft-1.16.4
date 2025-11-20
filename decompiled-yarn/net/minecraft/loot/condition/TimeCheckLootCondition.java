package net.minecraft.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class TimeCheckLootCondition implements LootCondition {
   @Nullable
   private final Long period;
   private final UniformLootTableRange value;

   private TimeCheckLootCondition(@Nullable Long period, UniformLootTableRange value) {
      this.period = period;
      this.value = value;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.TIME_CHECK;
   }

   public boolean test(LootContext _snowman) {
      ServerWorld _snowmanx = _snowman.getWorld();
      long _snowmanxx = _snowmanx.getTimeOfDay();
      if (this.period != null) {
         _snowmanxx %= this.period;
      }

      return this.value.contains((int)_snowmanxx);
   }

   public static class Serializer implements JsonSerializer<TimeCheckLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, TimeCheckLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.addProperty("period", _snowman.period);
         _snowman.add("value", _snowman.serialize(_snowman.value));
      }

      public TimeCheckLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         Long _snowmanxx = _snowman.has("period") ? JsonHelper.getLong(_snowman, "period") : null;
         UniformLootTableRange _snowmanxxx = JsonHelper.deserialize(_snowman, "value", _snowman, UniformLootTableRange.class);
         return new TimeCheckLootCondition(_snowmanxx, _snowmanxxx);
      }
   }
}
