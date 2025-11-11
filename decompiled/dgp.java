import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgp extends dhc {
   private static final Logger b = LogManager.getLogger();
   public String a;

   public dgp() {
   }

   public static dgp a(String var0) {
      dgp _snowman = new dgp();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(_snowman).getAsJsonObject();
         _snowman.a = dip.a("newsLink", _snowmanxx, null);
      } catch (Exception var4) {
         b.error("Could not parse RealmsNews: " + var4.getMessage());
      }

      return _snowman;
   }
}
