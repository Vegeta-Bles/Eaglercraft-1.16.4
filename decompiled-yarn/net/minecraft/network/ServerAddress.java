package net.minecraft.network;

import com.mojang.datafixers.util.Pair;
import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
   private final String address;
   private final int port;

   private ServerAddress(String address, int _snowman) {
      this.address = address;
      this.port = _snowman;
   }

   public String getAddress() {
      try {
         return IDN.toASCII(this.address);
      } catch (IllegalArgumentException var2) {
         return "";
      }
   }

   public int getPort() {
      return this.port;
   }

   public static ServerAddress parse(String address) {
      if (address == null) {
         return null;
      } else {
         String[] _snowman = address.split(":");
         if (address.startsWith("[")) {
            int _snowmanx = address.indexOf("]");
            if (_snowmanx > 0) {
               String _snowmanxx = address.substring(1, _snowmanx);
               String _snowmanxxx = address.substring(_snowmanx + 1).trim();
               if (_snowmanxxx.startsWith(":") && !_snowmanxxx.isEmpty()) {
                  _snowmanxxx = _snowmanxxx.substring(1);
                  _snowman = new String[]{_snowmanxx, _snowmanxxx};
               } else {
                  _snowman = new String[]{_snowmanxx};
               }
            }
         }

         if (_snowman.length > 2) {
            _snowman = new String[]{address};
         }

         String _snowmanx = _snowman[0];
         int _snowmanxx = _snowman.length > 1 ? portOrDefault(_snowman[1], 25565) : 25565;
         if (_snowmanxx == 25565) {
            Pair<String, Integer> _snowmanxxx = resolveServer(_snowmanx);
            _snowmanx = (String)_snowmanxxx.getFirst();
            _snowmanxx = (Integer)_snowmanxxx.getSecond();
         }

         return new ServerAddress(_snowmanx, _snowmanxx);
      }
   }

   private static Pair<String, Integer> resolveServer(String address) {
      try {
         String _snowman = "com.sun.jndi.dns.DnsContextFactory";
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable<String, String> _snowmanx = new Hashtable<>();
         _snowmanx.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         _snowmanx.put("java.naming.provider.url", "dns:");
         _snowmanx.put("com.sun.jndi.dns.timeout.retries", "1");
         DirContext _snowmanxx = new InitialDirContext(_snowmanx);
         Attributes _snowmanxxx = _snowmanxx.getAttributes("_minecraft._tcp." + address, new String[]{"SRV"});
         Attribute _snowmanxxxx = _snowmanxxx.get("srv");
         if (_snowmanxxxx != null) {
            String[] _snowmanxxxxx = _snowmanxxxx.get().toString().split(" ", 4);
            return Pair.of(_snowmanxxxxx[3], portOrDefault(_snowmanxxxxx[2], 25565));
         }
      } catch (Throwable var7) {
      }

      return Pair.of(address, 25565);
   }

   private static int portOrDefault(String port, int def) {
      try {
         return Integer.parseInt(port.trim());
      } catch (Exception var3) {
         return def;
      }
   }
}
