import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;

public final class cyp implements czb {
   private final int d;
   private final float e;

   public cyp(int var1, float var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   @Override
   public int a(Random var1) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < this.d; _snowmanx++) {
         if (_snowman.nextFloat() < this.e) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public static cyp a(int var0, float var1) {
      return new cyp(_snowman, _snowman);
   }

   @Override
   public vk a() {
      return c;
   }

   public static class a implements JsonDeserializer<cyp>, JsonSerializer<cyp> {
      public a() {
      }

      public cyp a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = afd.m(_snowman, "value");
         int _snowmanx = afd.n(_snowman, "n");
         float _snowmanxx = afd.l(_snowman, "p");
         return new cyp(_snowmanx, _snowmanxx);
      }

      public JsonElement a(cyp var1, Type var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("n", _snowman.d);
         _snowman.addProperty("p", _snowman.e);
         return _snowman;
      }
   }
}
