import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class adc extends acx<GameProfile> {
   public adc(GameProfile var1) {
      super(_snowman);
   }

   public adc(JsonObject var1) {
      super(b(_snowman));
   }

   @Override
   protected void a(JsonObject var1) {
      if (this.g() != null) {
         _snowman.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
         _snowman.addProperty("name", this.g().getName());
      }
   }

   private static GameProfile b(JsonObject var0) {
      if (_snowman.has("uuid") && _snowman.has("name")) {
         String _snowman = _snowman.get("uuid").getAsString();

         UUID _snowmanx;
         try {
            _snowmanx = UUID.fromString(_snowman);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(_snowmanx, _snowman.get("name").getAsString());
      } else {
         return null;
      }
   }
}
