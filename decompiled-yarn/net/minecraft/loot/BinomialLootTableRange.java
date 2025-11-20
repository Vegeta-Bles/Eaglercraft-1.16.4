package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public final class BinomialLootTableRange implements LootTableRange {
   private final int n;
   private final float p;

   public BinomialLootTableRange(int n, float p) {
      this.n = n;
      this.p = p;
   }

   @Override
   public int next(Random random) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < this.n; _snowmanx++) {
         if (random.nextFloat() < this.p) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public static BinomialLootTableRange create(int n, float p) {
      return new BinomialLootTableRange(n, p);
   }

   @Override
   public Identifier getType() {
      return BINOMIAL;
   }

   public static class Serializer implements JsonDeserializer<BinomialLootTableRange>, JsonSerializer<BinomialLootTableRange> {
      public Serializer() {
      }

      public BinomialLootTableRange deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "value");
         int _snowmanxxxx = JsonHelper.getInt(_snowmanxxx, "n");
         float _snowmanxxxxx = JsonHelper.getFloat(_snowmanxxx, "p");
         return new BinomialLootTableRange(_snowmanxxxx, _snowmanxxxxx);
      }

      public JsonElement serialize(BinomialLootTableRange _snowman, Type _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();
         _snowmanxxx.addProperty("n", _snowman.n);
         _snowmanxxx.addProperty("p", _snowman.p);
         return _snowmanxxx;
      }
   }
}
