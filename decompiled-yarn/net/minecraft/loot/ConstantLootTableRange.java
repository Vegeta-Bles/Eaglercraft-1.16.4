package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public final class ConstantLootTableRange implements LootTableRange {
   private final int value;

   public ConstantLootTableRange(int value) {
      this.value = value;
   }

   @Override
   public int next(Random random) {
      return this.value;
   }

   @Override
   public Identifier getType() {
      return CONSTANT;
   }

   public static ConstantLootTableRange create(int value) {
      return new ConstantLootTableRange(value);
   }

   public static class Serializer implements JsonDeserializer<ConstantLootTableRange>, JsonSerializer<ConstantLootTableRange> {
      public Serializer() {
      }

      public ConstantLootTableRange deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         return new ConstantLootTableRange(JsonHelper.asInt(_snowman, "value"));
      }

      public JsonElement serialize(ConstantLootTableRange _snowman, Type _snowman, JsonSerializationContext _snowman) {
         return new JsonPrimitive(_snowman.value);
      }
   }
}
