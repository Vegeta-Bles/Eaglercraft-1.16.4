package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class OperatorList extends ServerConfigList<GameProfile, OperatorEntry> {
   public OperatorList(File file) {
      super(file);
   }

   @Override
   protected ServerConfigEntry<GameProfile> fromJson(JsonObject json) {
      return new OperatorEntry(json);
   }

   @Override
   public String[] getNames() {
      String[] strings = new String[this.values().size()];
      int i = 0;

      for (ServerConfigEntry<GameProfile> lv : this.values()) {
         strings[i++] = lv.getKey().getName();
      }

      return strings;
   }

   public boolean isOp(GameProfile profile) {
      OperatorEntry lv = this.get(profile);
      return lv != null ? lv.canBypassPlayerLimit() : false;
   }

   protected String toString(GameProfile gameProfile) {
      return gameProfile.getId().toString();
   }
}
