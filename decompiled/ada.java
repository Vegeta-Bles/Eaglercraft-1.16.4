import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;

public class ada extends acp<GameProfile> {
   public ada(GameProfile var1) {
      this(_snowman, null, null, null, null);
   }

   public ada(GameProfile var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public ada(JsonObject var1) {
      super(b(_snowman), _snowman);
   }

   @Override
   protected void a(JsonObject var1) {
      if (this.g() != null) {
         _snowman.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
         _snowman.addProperty("name", this.g().getName());
         super.a(_snowman);
      }
   }

   @Override
   public nr e() {
      GameProfile _snowman = this.g();
      return new oe(_snowman.getName() != null ? _snowman.getName() : Objects.toString(_snowman.getId(), "(Unknown)"));
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
