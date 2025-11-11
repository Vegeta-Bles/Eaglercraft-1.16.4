import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dha extends dhc {
   private static final Logger d = LogManager.getLogger();
   public long a;
   public int b;
   public dha.a c = dha.a.a;

   public dha() {
   }

   public static dha a(String var0) {
      dha _snowman = new dha();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(_snowman).getAsJsonObject();
         _snowman.a = dip.a("startDate", _snowmanxx, 0L);
         _snowman.b = dip.a("daysLeft", _snowmanxx, 0);
         _snowman.c = b(dip.a("subscriptionType", _snowmanxx, dha.a.a.name()));
      } catch (Exception var4) {
         d.error("Could not parse Subscription: " + var4.getMessage());
      }

      return _snowman;
   }

   private static dha.a b(String var0) {
      try {
         return dha.a.valueOf(_snowman);
      } catch (Exception var2) {
         return dha.a.a;
      }
   }

   public static enum a {
      a,
      b;

      private a() {
      }
   }
}
