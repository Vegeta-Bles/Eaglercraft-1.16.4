package net.minecraft.loot.operator;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.function.IntUnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;

public class BoundedIntUnaryOperator implements IntUnaryOperator {
   private final Integer min;
   private final Integer max;
   private final IntUnaryOperator operator;

   private BoundedIntUnaryOperator(@Nullable Integer min, @Nullable Integer max) {
      this.min = min;
      this.max = max;
      if (min == null) {
         if (max == null) {
            this.operator = _snowman -> _snowman;
         } else {
            int _snowman = max;
            this.operator = _snowmanx -> Math.min(_snowman, _snowmanx);
         }
      } else {
         int _snowman = min;
         if (max == null) {
            this.operator = _snowmanx -> Math.max(_snowman, _snowmanx);
         } else {
            int _snowmanx = max;
            this.operator = _snowmanxx -> MathHelper.clamp(_snowmanxx, _snowman, _snowman);
         }
      }
   }

   public static BoundedIntUnaryOperator create(int min, int max) {
      return new BoundedIntUnaryOperator(min, max);
   }

   public static BoundedIntUnaryOperator createMin(int min) {
      return new BoundedIntUnaryOperator(min, null);
   }

   public static BoundedIntUnaryOperator createMax(int max) {
      return new BoundedIntUnaryOperator(null, max);
   }

   @Override
   public int applyAsInt(int value) {
      return this.operator.applyAsInt(value);
   }

   public static class Serializer implements JsonDeserializer<BoundedIntUnaryOperator>, JsonSerializer<BoundedIntUnaryOperator> {
      public Serializer() {
      }

      public BoundedIntUnaryOperator deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "value");
         Integer _snowmanxxxx = _snowmanxxx.has("min") ? JsonHelper.getInt(_snowmanxxx, "min") : null;
         Integer _snowmanxxxxx = _snowmanxxx.has("max") ? JsonHelper.getInt(_snowmanxxx, "max") : null;
         return new BoundedIntUnaryOperator(_snowmanxxxx, _snowmanxxxxx);
      }

      public JsonElement serialize(BoundedIntUnaryOperator _snowman, Type _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();
         if (_snowman.max != null) {
            _snowmanxxx.addProperty("max", _snowman.max);
         }

         if (_snowman.min != null) {
            _snowmanxxx.addProperty("min", _snowman.min);
         }

         return _snowmanxxx;
      }
   }
}
