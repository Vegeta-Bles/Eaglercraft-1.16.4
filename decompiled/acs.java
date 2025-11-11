import com.google.gson.JsonObject;
import java.util.Date;
import javax.annotation.Nullable;

public class acs extends acp<String> {
   public acs(String var1) {
      this(_snowman, null, null, null, null);
   }

   public acs(String var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public nr e() {
      return new oe(this.g());
   }

   public acs(JsonObject var1) {
      super(b(_snowman), _snowman);
   }

   private static String b(JsonObject var0) {
      return _snowman.has("ip") ? _snowman.get("ip").getAsString() : null;
   }

   @Override
   protected void a(JsonObject var1) {
      if (this.g() != null) {
         _snowman.addProperty("ip", this.g());
         super.a(_snowman);
      }
   }
}
