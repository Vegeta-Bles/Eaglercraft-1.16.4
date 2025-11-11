import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhe extends dhc {
   private static final Logger j = LogManager.getLogger();
   public String a = "";
   public String b = "";
   public String c = "";
   public String d = "";
   public String e = "";
   @Nullable
   public String f;
   public String g = "";
   public String h = "";
   public dhe.a i = dhe.a.a;

   public dhe() {
   }

   public static dhe a(JsonObject var0) {
      dhe _snowman = new dhe();

      try {
         _snowman.a = dip.a("id", _snowman, "");
         _snowman.b = dip.a("name", _snowman, "");
         _snowman.c = dip.a("version", _snowman, "");
         _snowman.d = dip.a("author", _snowman, "");
         _snowman.e = dip.a("link", _snowman, "");
         _snowman.f = dip.a("image", _snowman, null);
         _snowman.g = dip.a("trailer", _snowman, "");
         _snowman.h = dip.a("recommendedPlayers", _snowman, "");
         _snowman.i = dhe.a.valueOf(dip.a("type", _snowman, dhe.a.a.name()));
      } catch (Exception var3) {
         j.error("Could not parse WorldTemplate: " + var3.getMessage());
      }

      return _snowman;
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e;

      private a() {
      }
   }
}
