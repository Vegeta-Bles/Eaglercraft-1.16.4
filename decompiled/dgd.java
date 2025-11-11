import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgd {
   private static final Logger a = LogManager.getLogger();
   private final String b;
   private final int c;

   private dgd(String var1, int var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public static dgd a(String var0) {
      try {
         JsonParser _snowman = new JsonParser();
         JsonObject _snowmanx = _snowman.parse(_snowman).getAsJsonObject();
         String _snowmanxx = dip.a("errorMsg", _snowmanx, "");
         int _snowmanxxx = dip.a("errorCode", _snowmanx, -1);
         return new dgd(_snowmanxx, _snowmanxxx);
      } catch (Exception var5) {
         a.error("Could not parse RealmsError: " + var5.getMessage());
         a.error("The error was: " + _snowman);
         return new dgd("Failed to parse response from server", -1);
      }
   }

   public String a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }
}
