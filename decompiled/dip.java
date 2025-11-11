import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;

public class dip {
   public static String a(String var0, JsonObject var1, String var2) {
      JsonElement _snowman = _snowman.get(_snowman);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? _snowman : _snowman.getAsString();
      } else {
         return _snowman;
      }
   }

   public static int a(String var0, JsonObject var1, int var2) {
      JsonElement _snowman = _snowman.get(_snowman);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? _snowman : _snowman.getAsInt();
      } else {
         return _snowman;
      }
   }

   public static long a(String var0, JsonObject var1, long var2) {
      JsonElement _snowman = _snowman.get(_snowman);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? _snowman : _snowman.getAsLong();
      } else {
         return _snowman;
      }
   }

   public static boolean a(String var0, JsonObject var1, boolean var2) {
      JsonElement _snowman = _snowman.get(_snowman);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? _snowman : _snowman.getAsBoolean();
      } else {
         return _snowman;
      }
   }

   public static Date a(String var0, JsonObject var1) {
      JsonElement _snowman = _snowman.get(_snowman);
      return _snowman != null ? new Date(Long.parseLong(_snowman.getAsString())) : new Date();
   }
}
