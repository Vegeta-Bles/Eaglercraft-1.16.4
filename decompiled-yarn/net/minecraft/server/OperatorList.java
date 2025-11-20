package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class OperatorList extends ServerConfigList<GameProfile, OperatorEntry> {
   public OperatorList(File _snowman) {
      super(_snowman);
   }

   @Override
   protected ServerConfigEntry<GameProfile> fromJson(JsonObject json) {
      return new OperatorEntry(json);
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

   public boolean isOp(GameProfile profile) {
      OperatorEntry _snowman = this.get(profile);
      return _snowman != null ? _snowman.canBypassPlayerLimit() : false;
   }

   protected String toString(GameProfile _snowman) {
      return _snowman.getId().toString();
   }
}
