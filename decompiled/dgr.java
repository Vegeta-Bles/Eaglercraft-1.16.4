import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgr extends dhc {
   private static final Logger d = LogManager.getLogger();
   public String a;
   public String b;
   public String c;

   public dgr() {
   }

   public static dgr a(String var0) {
      JsonParser _snowman = new JsonParser();
      dgr _snowmanx = new dgr();

      try {
         JsonObject _snowmanxx = _snowman.parse(_snowman).getAsJsonObject();
         _snowmanx.a = dip.a("address", _snowmanxx, null);
         _snowmanx.b = dip.a("resourcePackUrl", _snowmanxx, null);
         _snowmanx.c = dip.a("resourcePackHash", _snowmanxx, null);
      } catch (Exception var4) {
         d.error("Could not parse RealmsServerAddress: " + var4.getMessage());
      }

      return _snowmanx;
   }
}
