package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class WhitelistEntry extends ServerConfigEntry<GameProfile> {
   public WhitelistEntry(GameProfile profile) {
      super(profile);
   }

   public WhitelistEntry(JsonObject json) {
      super(profileFromJson(json));
   }

   @Override
   protected void fromJson(JsonObject json) {
      if (this.getKey() != null) {
         json.addProperty("uuid", this.getKey().getId() == null ? "" : this.getKey().getId().toString());
         json.addProperty("name", this.getKey().getName());
      }
   }

   private static GameProfile profileFromJson(JsonObject json) {
      if (json.has("uuid") && json.has("name")) {
         String _snowman = json.get("uuid").getAsString();

         UUID _snowmanx;
         try {
            _snowmanx = UUID.fromString(_snowman);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(_snowmanx, json.get("name").getAsString());
      } else {
         return null;
      }
   }
}
