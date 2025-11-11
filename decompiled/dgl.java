import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgl extends dhc {
   private static final Logger b = LogManager.getLogger();
   public List<dgk> a = Lists.newArrayList();

   public dgl() {
   }

   public static dgl a(String var0) {
      dgl _snowman = new dgl();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(_snowman).getAsJsonObject();
         if (_snowmanxx.get("invites").isJsonArray()) {
            Iterator<JsonElement> _snowmanxxx = _snowmanxx.get("invites").getAsJsonArray().iterator();

            while (_snowmanxxx.hasNext()) {
               _snowman.a.add(dgk.a(_snowmanxxx.next().getAsJsonObject()));
            }
         }
      } catch (Exception var5) {
         b.error("Could not parse PendingInvitesList: " + var5.getMessage());
      }

      return _snowman;
   }
}
