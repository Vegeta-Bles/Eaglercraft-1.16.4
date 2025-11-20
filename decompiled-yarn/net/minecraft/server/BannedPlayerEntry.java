package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class BannedPlayerEntry extends BanEntry<GameProfile> {
   public BannedPlayerEntry(GameProfile profile) {
      this(profile, null, null, null, null);
   }

   public BannedPlayerEntry(GameProfile profile, @Nullable Date created, @Nullable String source, @Nullable Date expiry, @Nullable String reason) {
      super(profile, created, source, expiry, reason);
   }

   public BannedPlayerEntry(JsonObject json) {
      super(profileFromJson(json), json);
   }

   @Override
   protected void fromJson(JsonObject json) {
      if (this.getKey() != null) {
         json.addProperty("uuid", this.getKey().getId() == null ? "" : this.getKey().getId().toString());
         json.addProperty("name", this.getKey().getName());
         super.fromJson(json);
      }
   }

   @Override
   public Text toText() {
      GameProfile _snowman = this.getKey();
      return new LiteralText(_snowman.getName() != null ? _snowman.getName() : Objects.toString(_snowman.getId(), "(Unknown)"));
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
