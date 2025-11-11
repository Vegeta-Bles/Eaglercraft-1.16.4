import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgh extends dhc {
   private static final Logger b = LogManager.getLogger();
   public List<dgg> a;

   public dgh() {
   }

   public static dgh a(String var0) {
      JsonParser _snowman = new JsonParser();
      dgh _snowmanx = new dgh();
      _snowmanx.a = Lists.newArrayList();

      try {
         JsonElement _snowmanxx = _snowman.parse(_snowman).getAsJsonObject().get("backups");
         if (_snowmanxx.isJsonArray()) {
            Iterator<JsonElement> _snowmanxxx = _snowmanxx.getAsJsonArray().iterator();

            while (_snowmanxxx.hasNext()) {
               _snowmanx.a.add(dgg.a(_snowmanxxx.next()));
            }
         }
      } catch (Exception var5) {
         b.error("Could not parse BackupList: " + var5.getMessage());
      }

      return _snowmanx;
   }
}
