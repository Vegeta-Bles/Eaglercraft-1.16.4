import com.google.gson.JsonObject;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgk extends dhc {
   private static final Logger f = LogManager.getLogger();
   public String a;
   public String b;
   public String c;
   public String d;
   public Date e;

   public dgk() {
   }

   public static dgk a(JsonObject var0) {
      dgk _snowman = new dgk();

      try {
         _snowman.a = dip.a("invitationId", _snowman, "");
         _snowman.b = dip.a("worldName", _snowman, "");
         _snowman.c = dip.a("worldOwnerName", _snowman, "");
         _snowman.d = dip.a("worldOwnerUuid", _snowman, "");
         _snowman.e = dip.a("date", _snowman);
      } catch (Exception var3) {
         f.error("Could not parse PendingInvite: " + var3.getMessage());
      }

      return _snowman;
   }
}
