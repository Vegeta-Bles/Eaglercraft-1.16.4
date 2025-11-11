import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;

public final class cyr implements czb {
   private final int d;

   public cyr(int var1) {
      this.d = _snowman;
   }

   @Override
   public int a(Random var1) {
      return this.d;
   }

   @Override
   public vk a() {
      return a;
   }

   public static cyr a(int var0) {
      return new cyr(_snowman);
   }

   public static class a implements JsonDeserializer<cyr>, JsonSerializer<cyr> {
      public a() {
      }

      public cyr a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return new cyr(afd.g(_snowman, "value"));
      }

      public JsonElement a(cyr var1, Type var2, JsonSerializationContext var3) {
         return new JsonPrimitive(_snowman.d);
      }
   }
}
