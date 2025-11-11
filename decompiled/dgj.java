import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Set;

public class dgj extends dhc {
   public Set<String> a = Sets.newHashSet();

   public dgj() {
   }

   public static dgj a(String var0) {
      dgj _snowman = new dgj();
      JsonParser _snowmanx = new JsonParser();

      try {
         JsonElement _snowmanxx = _snowmanx.parse(_snowman);
         JsonObject _snowmanxxx = _snowmanxx.getAsJsonObject();
         JsonElement _snowmanxxxx = _snowmanxxx.get("ops");
         if (_snowmanxxxx.isJsonArray()) {
            for (JsonElement _snowmanxxxxx : _snowmanxxxx.getAsJsonArray()) {
               _snowman.a.add(_snowmanxxxxx.getAsString());
            }
         }
      } catch (Exception var8) {
      }

      return _snowman;
   }
}
