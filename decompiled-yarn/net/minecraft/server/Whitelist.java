package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class Whitelist extends ServerConfigList<GameProfile, WhitelistEntry> {
   public Whitelist(File _snowman) {
      super(_snowman);
   }

   @Override
   protected ServerConfigEntry<GameProfile> fromJson(JsonObject json) {
      return new WhitelistEntry(json);
   }

   public boolean isAllowed(GameProfile profile) {
      return this.contains(profile);
   }

   @Override
   public String[] getNames() {
      String[] _snowman = new String[this.values().size()];
      int _snowmanx = 0;

      for (ServerConfigEntry<GameProfile> _snowmanxx : this.values()) {
         _snowman[_snowmanx++] = _snowmanxx.getKey().getName();
      }

      return _snowman;
   }

   protected String toString(GameProfile _snowman) {
      return _snowman.getId().toString();
   }
}
