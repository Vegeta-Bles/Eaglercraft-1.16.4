package net.minecraft.server;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class BannedIpList extends ServerConfigList<String, BannedIpEntry> {
   public BannedIpList(File _snowman) {
      super(_snowman);
   }

   @Override
   protected ServerConfigEntry<String> fromJson(JsonObject json) {
      return new BannedIpEntry(json);
   }

   public boolean isBanned(SocketAddress ip) {
      String _snowman = this.stringifyAddress(ip);
      return this.contains(_snowman);
   }

   public boolean isBanned(String ip) {
      return this.contains(ip);
   }

   public BannedIpEntry get(SocketAddress _snowman) {
      String _snowmanx = this.stringifyAddress(_snowman);
      return this.get(_snowmanx);
   }

   private String stringifyAddress(SocketAddress _snowman) {
      String _snowmanx = _snowman.toString();
      if (_snowmanx.contains("/")) {
         _snowmanx = _snowmanx.substring(_snowmanx.indexOf(47) + 1);
      }

      if (_snowmanx.contains(":")) {
         _snowmanx = _snowmanx.substring(0, _snowmanx.indexOf(58));
      }

      return _snowmanx;
   }
}
