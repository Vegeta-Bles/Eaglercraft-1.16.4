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

public class czd implements czb {
   private final float d;
   private final float e;

   public czd(float var1, float var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   public czd(float var1) {
      this.d = _snowman;
      this.e = _snowman;
   }

   public static czd a(float var0, float var1) {
      return new czd(_snowman, _snowman);
   }

   public float b() {
      return this.d;
   }

   public float c() {
      return this.e;
   }

   @Override
   public int a(Random var1) {
      return afm.a(_snowman, afm.d(this.d), afm.d(this.e));
   }

   public float b(Random var1) {
      return afm.a(_snowman, this.d, this.e);
   }

   public boolean a(int var1) {
      return (float)_snowman <= this.e && (float)_snowman >= this.d;
   }

   @Override
   public vk a() {
      return b;
   }

   public static class a implements JsonDeserializer<czd>, JsonSerializer<czd> {
      public a() {
      }

      public czd a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (afd.b(_snowman)) {
            return new czd(afd.e(_snowman, "value"));
         } else {
            JsonObject _snowman = afd.m(_snowman, "value");
            float _snowmanx = afd.l(_snowman, "min");
            float _snowmanxx = afd.l(_snowman, "max");
            return new czd(_snowmanx, _snowmanxx);
         }
      }

      public JsonElement a(czd var1, Type var2, JsonSerializationContext var3) {
         if (_snowman.d == _snowman.e) {
            return new JsonPrimitive(_snowman.d);
         } else {
            JsonObject _snowman = new JsonObject();
            _snowman.addProperty("min", _snowman.d);
            _snowman.addProperty("max", _snowman.e);
            return _snowman;
         }
      }
   }
}
