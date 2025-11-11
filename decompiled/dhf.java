import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhf extends dhc {
   private static final Logger e = LogManager.getLogger();
   public List<dhe> a;
   public int b;
   public int c;
   public int d;

   public dhf() {
   }

   public dhf(int var1) {
      this.a = Collections.emptyList();
      this.b = 0;
      this.c = _snowman;
      this.d = -1;
   }

   public static dhf a(String var0) {
      dhf _snowman = new dhf();
      _snowman.a = Lists.newArrayList();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(_snowman).getAsJsonObject();
         if (_snowmanxx.get("templates").isJsonArray()) {
            Iterator<JsonElement> _snowmanxxx = _snowmanxx.get("templates").getAsJsonArray().iterator();

            while (_snowmanxxx.hasNext()) {
               _snowman.a.add(dhe.a(_snowmanxxx.next().getAsJsonObject()));
            }
         }

         _snowman.b = dip.a("page", _snowmanxx, 0);
         _snowman.c = dip.a("size", _snowmanxx, 0);
         _snowman.d = dip.a("total", _snowmanxx, 0);
      } catch (Exception var5) {
         e.error("Could not parse WorldTemplatePaginatedList: " + var5.getMessage());
      }

      return _snowman;
   }
}
