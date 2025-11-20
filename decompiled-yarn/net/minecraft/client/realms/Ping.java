package net.minecraft.client.realms;

import com.google.common.collect.Lists;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.realms.dto.RegionPingResult;
import net.minecraft.util.Util;

public class Ping {
   public static List<RegionPingResult> ping(Ping.Region... regions) {
      for (Ping.Region _snowman : regions) {
         ping(_snowman.endpoint);
      }

      List<RegionPingResult> _snowman = Lists.newArrayList();

      for (Ping.Region _snowmanx : regions) {
         _snowman.add(new RegionPingResult(_snowmanx.name, ping(_snowmanx.endpoint)));
      }

      _snowman.sort(Comparator.comparingInt(RegionPingResult::getPing));
      return _snowman;
   }

   private static int ping(String host) {
      int _snowman = 700;
      long _snowmanx = 0L;
      Socket _snowmanxx = null;

      for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
         try {
            SocketAddress _snowmanxxxx = new InetSocketAddress(host, 80);
            _snowmanxx = new Socket();
            long _snowmanxxxxx = now();
            _snowmanxx.connect(_snowmanxxxx, 700);
            _snowmanx += now() - _snowmanxxxxx;
         } catch (Exception var12) {
            _snowmanx += 700L;
         } finally {
            close(_snowmanxx);
         }
      }

      return (int)((double)_snowmanx / 5.0);
   }

   private static void close(Socket socket) {
      try {
         if (socket != null) {
            socket.close();
         }
      } catch (Throwable var2) {
      }
   }

   private static long now() {
      return Util.getMeasuringTimeMs();
   }

   public static List<RegionPingResult> pingAllRegions() {
      return ping(Ping.Region.values());
   }

   static enum Region {
      US_EAST_1("us-east-1", "ec2.us-east-1.amazonaws.com"),
      US_WEST_2("us-west-2", "ec2.us-west-2.amazonaws.com"),
      US_WEST_1("us-west-1", "ec2.us-west-1.amazonaws.com"),
      EU_WEST_1("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
      AP_SOUTHEAST_1("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
      AP_SOUTHEAST_2("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
      AP_NORTHEAST_1("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
      SA_EAST_1("sa-east-1", "ec2.sa-east-1.amazonaws.com");

      private final String name;
      private final String endpoint;

      private Region(String name, String endpoint) {
         this.name = name;
         this.endpoint = endpoint;
      }
   }
}
