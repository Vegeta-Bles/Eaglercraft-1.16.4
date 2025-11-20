package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class BannedPlayerList extends ServerConfigList<GameProfile, BannedPlayerEntry> {
   public BannedPlayerList(File _snowman) {
      super(_snowman);
   }

   @Override
   protected ServerConfigEntry<GameProfile> fromJson(JsonObject json) {
      return new BannedPlayerEntry(json);
   }

   public boolean contains(GameProfile _snowman) {
      return this.contains(_snowman);
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
