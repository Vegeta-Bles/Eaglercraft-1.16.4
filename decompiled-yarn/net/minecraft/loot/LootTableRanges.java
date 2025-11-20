package net.minecraft.loot;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class LootTableRanges {
   private static final Map<Identifier, Class<? extends LootTableRange>> TYPES = Maps.newHashMap();

   public static LootTableRange fromJson(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
      if (json.isJsonPrimitive()) {
         return (LootTableRange)context.deserialize(json, ConstantLootTableRange.class);
      } else {
         JsonObject _snowman = json.getAsJsonObject();
         String _snowmanx = JsonHelper.getString(_snowman, "type", LootTableRange.UNIFORM.toString());
         Class<? extends LootTableRange> _snowmanxx = TYPES.get(new Identifier(_snowmanx));
         if (_snowmanxx == null) {
            throw new JsonParseException("Unknown generator: " + _snowmanx);
         } else {
            return (LootTableRange)context.deserialize(_snowman, _snowmanxx);
         }
      }
   }

   public static JsonElement toJson(LootTableRange range, JsonSerializationContext context) {
      JsonElement _snowman = context.serialize(range);
      if (_snowman.isJsonObject()) {
         _snowman.getAsJsonObject().addProperty("type", range.getType().toString());
      }

      return _snowman;
   }

   static {
      TYPES.put(LootTableRange.UNIFORM, UniformLootTableRange.class);
      TYPES.put(LootTableRange.BINOMIAL, BinomialLootTableRange.class);
      TYPES.put(LootTableRange.CONSTANT, ConstantLootTableRange.class);
   }
}
