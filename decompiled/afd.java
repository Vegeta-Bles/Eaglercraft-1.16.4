import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class afd {
   private static final Gson a = new GsonBuilder().create();

   public static boolean a(JsonObject var0, String var1) {
      return !f(_snowman, _snowman) ? false : _snowman.getAsJsonPrimitive(_snowman).isString();
   }

   public static boolean a(JsonElement var0) {
      return !_snowman.isJsonPrimitive() ? false : _snowman.getAsJsonPrimitive().isString();
   }

   public static boolean b(JsonElement var0) {
      return !_snowman.isJsonPrimitive() ? false : _snowman.getAsJsonPrimitive().isNumber();
   }

   public static boolean c(JsonObject var0, String var1) {
      return !f(_snowman, _snowman) ? false : _snowman.getAsJsonPrimitive(_snowman).isBoolean();
   }

   public static boolean d(JsonObject var0, String var1) {
      return !g(_snowman, _snowman) ? false : _snowman.get(_snowman).isJsonArray();
   }

   public static boolean f(JsonObject var0, String var1) {
      return !g(_snowman, _snowman) ? false : _snowman.get(_snowman).isJsonPrimitive();
   }

   public static boolean g(JsonObject var0, String var1) {
      return _snowman == null ? false : _snowman.get(_snowman) != null;
   }

   public static String a(JsonElement var0, String var1) {
      if (_snowman.isJsonPrimitive()) {
         return _snowman.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a string, was " + d(_snowman));
      }
   }

   public static String h(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return a(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find a string");
      }
   }

   public static String a(JsonObject var0, String var1, String var2) {
      return _snowman.has(_snowman) ? a(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static blx b(JsonElement var0, String var1) {
      if (_snowman.isJsonPrimitive()) {
         String _snowman = _snowman.getAsString();
         return gm.T.b(new vk(_snowman)).orElseThrow(() -> new JsonSyntaxException("Expected " + _snowman + " to be an item, was unknown string '" + _snowman + "'"));
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be an item, was " + d(_snowman));
      }
   }

   public static blx i(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return b(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find an item");
      }
   }

   public static boolean c(JsonElement var0, String var1) {
      if (_snowman.isJsonPrimitive()) {
         return _snowman.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a Boolean, was " + d(_snowman));
      }
   }

   public static boolean j(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return c(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find a Boolean");
      }
   }

   public static boolean a(JsonObject var0, String var1, boolean var2) {
      return _snowman.has(_snowman) ? c(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static float e(JsonElement var0, String var1) {
      if (_snowman.isJsonPrimitive() && _snowman.getAsJsonPrimitive().isNumber()) {
         return _snowman.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a Float, was " + d(_snowman));
      }
   }

   public static float l(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return e(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find a Float");
      }
   }

   public static float a(JsonObject var0, String var1, float var2) {
      return _snowman.has(_snowman) ? e(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static long f(JsonElement var0, String var1) {
      if (_snowman.isJsonPrimitive() && _snowman.getAsJsonPrimitive().isNumber()) {
         return _snowman.getAsLong();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a Long, was " + d(_snowman));
      }
   }

   public static long m(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return f(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find a Long");
      }
   }

   public static long a(JsonObject var0, String var1, long var2) {
      return _snowman.has(_snowman) ? f(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static int g(JsonElement var0, String var1) {
      if (_snowman.isJsonPrimitive() && _snowman.getAsJsonPrimitive().isNumber()) {
         return _snowman.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a Int, was " + d(_snowman));
      }
   }

   public static int n(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return g(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find a Int");
      }
   }

   public static int a(JsonObject var0, String var1, int var2) {
      return _snowman.has(_snowman) ? g(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static byte h(JsonElement var0, String var1) {
      if (_snowman.isJsonPrimitive() && _snowman.getAsJsonPrimitive().isNumber()) {
         return _snowman.getAsByte();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a Byte, was " + d(_snowman));
      }
   }

   public static byte a(JsonObject var0, String var1, byte var2) {
      return _snowman.has(_snowman) ? h(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static JsonObject m(JsonElement var0, String var1) {
      if (_snowman.isJsonObject()) {
         return _snowman.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a JsonObject, was " + d(_snowman));
      }
   }

   public static JsonObject t(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return m(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find a JsonObject");
      }
   }

   public static JsonObject a(JsonObject var0, String var1, JsonObject var2) {
      return _snowman.has(_snowman) ? m(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static JsonArray n(JsonElement var0, String var1) {
      if (_snowman.isJsonArray()) {
         return _snowman.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + _snowman + " to be a JsonArray, was " + d(_snowman));
      }
   }

   public static JsonArray u(JsonObject var0, String var1) {
      if (_snowman.has(_snowman)) {
         return n(_snowman.get(_snowman), _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman + ", expected to find a JsonArray");
      }
   }

   @Nullable
   public static JsonArray a(JsonObject var0, String var1, @Nullable JsonArray var2) {
      return _snowman.has(_snowman) ? n(_snowman.get(_snowman), _snowman) : _snowman;
   }

   public static <T> T a(@Nullable JsonElement var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (_snowman != null) {
         return (T)_snowman.deserialize(_snowman, _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman);
      }
   }

   public static <T> T a(JsonObject var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (_snowman.has(_snowman)) {
         return a(_snowman.get(_snowman), _snowman, _snowman, _snowman);
      } else {
         throw new JsonSyntaxException("Missing " + _snowman);
      }
   }

   public static <T> T a(JsonObject var0, String var1, T var2, JsonDeserializationContext var3, Class<? extends T> var4) {
      return _snowman.has(_snowman) ? a(_snowman.get(_snowman), _snowman, _snowman, _snowman) : _snowman;
   }

   public static String d(JsonElement var0) {
      String _snowman = StringUtils.abbreviateMiddle(String.valueOf(_snowman), "...", 10);
      if (_snowman == null) {
         return "null (missing)";
      } else if (_snowman.isJsonNull()) {
         return "null (json)";
      } else if (_snowman.isJsonArray()) {
         return "an array (" + _snowman + ")";
      } else if (_snowman.isJsonObject()) {
         return "an object (" + _snowman + ")";
      } else {
         if (_snowman.isJsonPrimitive()) {
            JsonPrimitive _snowmanx = _snowman.getAsJsonPrimitive();
            if (_snowmanx.isNumber()) {
               return "a number (" + _snowman + ")";
            }

            if (_snowmanx.isBoolean()) {
               return "a boolean (" + _snowman + ")";
            }
         }

         return _snowman;
      }
   }

   @Nullable
   public static <T> T a(Gson var0, Reader var1, Class<T> var2, boolean var3) {
      try {
         JsonReader _snowman = new JsonReader(_snowman);
         _snowman.setLenient(_snowman);
         return (T)_snowman.getAdapter(_snowman).read(_snowman);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   public static <T> T a(Gson var0, Reader var1, TypeToken<T> var2, boolean var3) {
      try {
         JsonReader _snowman = new JsonReader(_snowman);
         _snowman.setLenient(_snowman);
         return (T)_snowman.getAdapter(_snowman).read(_snowman);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   public static <T> T a(Gson var0, String var1, TypeToken<T> var2, boolean var3) {
      return a(_snowman, new StringReader(_snowman), _snowman, _snowman);
   }

   @Nullable
   public static <T> T a(Gson var0, String var1, Class<T> var2, boolean var3) {
      return a(_snowman, new StringReader(_snowman), _snowman, _snowman);
   }

   @Nullable
   public static <T> T a(Gson var0, Reader var1, TypeToken<T> var2) {
      return a(_snowman, _snowman, _snowman, false);
   }

   @Nullable
   public static <T> T a(Gson var0, String var1, TypeToken<T> var2) {
      return a(_snowman, _snowman, _snowman, false);
   }

   @Nullable
   public static <T> T a(Gson var0, Reader var1, Class<T> var2) {
      return a(_snowman, _snowman, _snowman, false);
   }

   @Nullable
   public static <T> T a(Gson var0, String var1, Class<T> var2) {
      return a(_snowman, _snowman, _snowman, false);
   }

   public static JsonObject a(String var0, boolean var1) {
      return a(new StringReader(_snowman), _snowman);
   }

   public static JsonObject a(Reader var0, boolean var1) {
      return a(a, _snowman, JsonObject.class, _snowman);
   }

   public static JsonObject a(String var0) {
      return a(_snowman, false);
   }

   public static JsonObject a(Reader var0) {
      return a(_snowman, false);
   }
}
