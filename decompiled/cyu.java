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

public class cyu implements IntUnaryOperator {
   private final Integer a;
   private final Integer b;
   private final IntUnaryOperator c;

   private cyu(@Nullable Integer var1, @Nullable Integer var2) {
      this.a = _snowman;
      this.b = _snowman;
      if (_snowman == null) {
         if (_snowman == null) {
            this.c = var0 -> var0;
         } else {
            int _snowman = _snowman;
            this.c = var1x -> Math.min(_snowman, var1x);
         }
      } else {
         int _snowman = _snowman;
         if (_snowman == null) {
            this.c = var1x -> Math.max(_snowman, var1x);
         } else {
            int _snowmanx = _snowman;
            this.c = var2x -> afm.a(var2x, _snowman, _snowman);
         }
      }
   }

   public static cyu a(int var0, int var1) {
      return new cyu(_snowman, _snowman);
   }

   public static cyu a(int var0) {
      return new cyu(_snowman, null);
   }

   public static cyu b(int var0) {
      return new cyu(null, _snowman);
   }

   @Override
   public int applyAsInt(int var1) {
      return this.c.applyAsInt(_snowman);
   }

   public static class a implements JsonDeserializer<cyu>, JsonSerializer<cyu> {
      public a() {
      }

      public cyu a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = afd.m(_snowman, "value");
         Integer _snowmanx = _snowman.has("min") ? afd.n(_snowman, "min") : null;
         Integer _snowmanxx = _snowman.has("max") ? afd.n(_snowman, "max") : null;
         return new cyu(_snowmanx, _snowmanxx);
      }

      public JsonElement a(cyu var1, Type var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();
         if (_snowman.b != null) {
            _snowman.addProperty("max", _snowman.b);
         }

         if (_snowman.a != null) {
            _snowman.addProperty("min", _snowman.a);
         }

         return _snowman;
      }
   }
}
