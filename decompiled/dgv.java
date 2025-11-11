import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgv extends dhc {
   private static final Logger b = LogManager.getLogger();
   public List<dgu> a;

   public dgv() {
   }

   public static dgv a(String var0) {
      dgv _snowman = new dgv();
      _snowman.a = Lists.newArrayList();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(_snowman).getAsJsonObject();
         if (_snowmanxx.get("lists").isJsonArray()) {
            JsonArray _snowmanxxx = _snowmanxx.get("lists").getAsJsonArray();
            Iterator<JsonElement> _snowmanxxxx = _snowmanxxx.iterator();

            while (_snowmanxxxx.hasNext()) {
               _snowman.a.add(dgu.a(_snowmanxxxx.next().getAsJsonObject()));
            }
         }
      } catch (Exception var6) {
         b.error("Could not parse RealmsServerPlayerLists: " + var6.getMessage());
      }

      return _snowman;
   }
}
