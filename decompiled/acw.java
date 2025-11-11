import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class acw extends acx<GameProfile> {
   private final int a;
   private final boolean b;

   public acw(GameProfile var1, int var2, boolean var3) {
      super(_snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   public acw(JsonObject var1) {
      super(b(_snowman));
      this.a = _snowman.has("level") ? _snowman.get("level").getAsInt() : 0;
      this.b = _snowman.has("bypassesPlayerLimit") && _snowman.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   @Override
   protected void a(JsonObject var1) {
      if (this.g() != null) {
         _snowman.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
         _snowman.addProperty("name", this.g().getName());
         _snowman.addProperty("level", this.a);
         _snowman.addProperty("bypassesPlayerLimit", this.b);
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
