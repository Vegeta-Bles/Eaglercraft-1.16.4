import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgs extends dhc {
   private static final Logger b = LogManager.getLogger();
   public List<dgq> a;

   public dgs() {
   }

   public static dgs a(String var0) {
      dgs _snowman = new dgs();
      _snowman.a = Lists.newArrayList();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(_snowman).getAsJsonObject();
         if (_snowmanxx.get("servers").isJsonArray()) {
            JsonArray _snowmanxxx = _snowmanxx.get("servers").getAsJsonArray();
            Iterator<JsonElement> _snowmanxxxx = _snowmanxxx.iterator();

            while (_snowmanxxxx.hasNext()) {
               _snowman.a.add(dgq.a(_snowmanxxxx.next().getAsJsonObject()));
            }
         }
      } catch (Exception var6) {
         b.error("Could not parse McoServerList: " + var6.getMessage());
      }

      return _snowman;
   }
}
