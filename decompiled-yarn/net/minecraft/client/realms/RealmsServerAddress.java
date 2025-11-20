package net.minecraft.client.realms;

import net.minecraft.network.ServerAddress;

public class RealmsServerAddress {
   private final String host;
   private final int port;

   protected RealmsServerAddress(String host, int port) {
      this.host = host;
      this.port = port;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public static RealmsServerAddress parseString(String _snowman) {
      ServerAddress _snowmanx = ServerAddress.parse(_snowman);
      return new RealmsServerAddress(_snowmanx.getAddress(), _snowmanx.getPort());
   }
}
