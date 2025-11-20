package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;

public class UniformLootTableRange implements LootTableRange {
   private final float min;
   private final float max;

   public UniformLootTableRange(float min, float max) {
      this.min = min;
      this.max = max;
   }

   public UniformLootTableRange(float value) {
      this.min = value;
      this.max = value;
   }

   public static UniformLootTableRange between(float min, float max) {
      return new UniformLootTableRange(min, max);
   }

   public float getMinValue() {
      return this.min;
   }

   public float getMaxValue() {
      return this.max;
   }

   @Override
   public int next(Random random) {
      return MathHelper.nextInt(random, MathHelper.floor(this.min), MathHelper.floor(this.max));
   }

   public float nextFloat(Random random) {
      return MathHelper.nextFloat(random, this.min, this.max);
   }

   public boolean contains(int value) {
      return (float)value <= this.max && (float)value >= this.min;
   }

   @Override
   public Identifier getType() {
      return UNIFORM;
   }

   public static class Serializer implements JsonDeserializer<UniformLootTableRange>, JsonSerializer<UniformLootTableRange> {
      public Serializer() {
      }

      public UniformLootTableRange deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         if (JsonHelper.isNumber(_snowman)) {
            return new UniformLootTableRange(JsonHelper.asFloat(_snowman, "value"));
         } else {
            JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "value");
            float _snowmanxxxx = JsonHelper.getFloat(_snowmanxxx, "min");
            float _snowmanxxxxx = JsonHelper.getFloat(_snowmanxxx, "max");
            return new UniformLootTableRange(_snowmanxxxx, _snowmanxxxxx);
         }
      }

      public JsonElement serialize(UniformLootTableRange _snowman, Type _snowman, JsonSerializationContext _snowman) {
         if (_snowman.min == _snowman.max) {
            return new JsonPrimitive(_snowman.min);
         } else {
            JsonObject _snowmanxxx = new JsonObject();
            _snowmanxxx.addProperty("min", _snowman.min);
            _snowmanxxx.addProperty("max", _snowman.max);
            return _snowmanxxx;
         }
      }
   }
}
