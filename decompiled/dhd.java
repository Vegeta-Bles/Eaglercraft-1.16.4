import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhd extends dhc {
   private static final Logger d = LogManager.getLogger();
   public String a;
   public String b;
   public String c;

   public dhd() {
   }

   public static dhd a(String var0) {
      JsonParser _snowman = new JsonParser();
      JsonObject _snowmanx = _snowman.parse(_snowman).getAsJsonObject();
      dhd _snowmanxx = new dhd();

      try {
         _snowmanxx.a = dip.a("downloadLink", _snowmanx, "");
         _snowmanxx.b = dip.a("resourcePackUrl", _snowmanx, "");
         _snowmanxx.c = dip.a("resourcePackHash", _snowmanx, "");
      } catch (Exception var5) {
         d.error("Could not parse WorldDownload: " + var5.getMessage());
      }

      return _snowmanxx;
   }
}
