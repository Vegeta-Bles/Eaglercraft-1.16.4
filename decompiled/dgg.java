import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgg extends dhc {
   private static final Logger f = LogManager.getLogger();
   public String a;
   public Date b;
   public long c;
   private boolean g;
   public Map<String, String> d = Maps.newHashMap();
   public Map<String, String> e = Maps.newHashMap();

   public dgg() {
   }

   public static dgg a(JsonElement var0) {
      JsonObject _snowman = _snowman.getAsJsonObject();
      dgg _snowmanx = new dgg();

      try {
         _snowmanx.a = dip.a("backupId", _snowman, "");
         _snowmanx.b = dip.a("lastModifiedDate", _snowman);
         _snowmanx.c = dip.a("size", _snowman, 0L);
         if (_snowman.has("metadata")) {
            JsonObject _snowmanxx = _snowman.getAsJsonObject("metadata");

            for (Entry<String, JsonElement> _snowmanxxx : _snowmanxx.entrySet()) {
               if (!_snowmanxxx.getValue().isJsonNull()) {
                  _snowmanx.d.put(a(_snowmanxxx.getKey()), _snowmanxxx.getValue().getAsString());
               }
            }
         }
      } catch (Exception var7) {
         f.error("Could not parse Backup: " + var7.getMessage());
      }

      return _snowmanx;
   }

   private static String a(String var0) {
      String[] _snowman = _snowman.split("_");
      StringBuilder _snowmanx = new StringBuilder();

      for (String _snowmanxx : _snowman) {
         if (_snowmanxx != null && _snowmanxx.length() >= 1) {
            if ("of".equals(_snowmanxx)) {
               _snowmanx.append(_snowmanxx).append(" ");
            } else {
               char _snowmanxxx = Character.toUpperCase(_snowmanxx.charAt(0));
               _snowmanx.append(_snowmanxxx).append(_snowmanxx.substring(1)).append(" ");
            }
         }
      }

      return _snowmanx.toString();
   }

   public boolean a() {
      return this.g;
   }

   public void a(boolean var1) {
      this.g = _snowman;
   }
}
