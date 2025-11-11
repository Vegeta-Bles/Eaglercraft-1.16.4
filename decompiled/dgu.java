import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgu extends dhc {
   private static final Logger c = LogManager.getLogger();
   private static final JsonParser d = new JsonParser();
   public long a;
   public List<String> b;

   public dgu() {
   }

   public static dgu a(JsonObject var0) {
      dgu _snowman = new dgu();

      try {
         _snowman.a = dip.a("serverId", _snowman, -1L);
         String _snowmanx = dip.a("playerList", _snowman, null);
         if (_snowmanx != null) {
            JsonElement _snowmanxx = d.parse(_snowmanx);
            if (_snowmanxx.isJsonArray()) {
               _snowman.b = a(_snowmanxx.getAsJsonArray());
            } else {
               _snowman.b = Lists.newArrayList();
            }
         } else {
            _snowman.b = Lists.newArrayList();
         }
      } catch (Exception var4) {
         c.error("Could not parse RealmsServerPlayerList: " + var4.getMessage());
      }

      return _snowman;
   }

   private static List<String> a(JsonArray var0) {
      List<String> _snowman = Lists.newArrayList();

      for (JsonElement _snowmanx : _snowman) {
         try {
            _snowman.add(_snowmanx.getAsString());
         } catch (Exception var5) {
         }
      }

      return _snowman;
   }
}
