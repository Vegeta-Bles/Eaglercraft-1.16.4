package net.minecraft.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerPinger extends Thread {
   private static final AtomicInteger THREAD_ID = new AtomicInteger(0);
   private static final Logger LOGGER = LogManager.getLogger();
   private final String motd;
   private final DatagramSocket socket;
   private boolean running = true;
   private final String addressPort;

   public LanServerPinger(String motd, String addressPort) throws IOException {
      super("LanServerPinger #" + THREAD_ID.incrementAndGet());
      this.motd = motd;
      this.addressPort = addressPort;
      this.setDaemon(true);
      this.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
      this.socket = new DatagramSocket();
   }

   @Override
   public void run() {
      String _snowman = createAnnouncement(this.motd, this.addressPort);
      byte[] _snowmanx = _snowman.getBytes(StandardCharsets.UTF_8);

      while (!this.isInterrupted() && this.running) {
         try {
            InetAddress _snowmanxx = InetAddress.getByName("224.0.2.60");
            DatagramPacket _snowmanxxx = new DatagramPacket(_snowmanx, _snowmanx.length, _snowmanxx, 4445);
            this.socket.send(_snowmanxxx);
         } catch (IOException var6) {
            LOGGER.warn("LanServerPinger: {}", var6.getMessage());
            break;
         }

         try {
            sleep(1500L);
         } catch (InterruptedException var5) {
         }
      }
   }

   @Override
   public void interrupt() {
      super.interrupt();
      this.running = false;
   }

   public static String createAnnouncement(String motd, String addressPort) {
      return "[MOTD]" + motd + "[/MOTD][AD]" + addressPort + "[/AD]";
   }

   public static String parseAnnouncementMotd(String announcement) {
      int _snowman = announcement.indexOf("[MOTD]");
      if (_snowman < 0) {
         return "missing no";
      } else {
         int _snowmanx = announcement.indexOf("[/MOTD]", _snowman + "[MOTD]".length());
         return _snowmanx < _snowman ? "missing no" : announcement.substring(_snowman + "[MOTD]".length(), _snowmanx);
      }
   }

   public static String parseAnnouncementAddressPort(String announcement) {
      int _snowman = announcement.indexOf("[/MOTD]");
      if (_snowman < 0) {
         return null;
      } else {
         int _snowmanx = announcement.indexOf("[/MOTD]", _snowman + "[/MOTD]".length());
         if (_snowmanx >= 0) {
            return null;
         } else {
            int _snowmanxx = announcement.indexOf("[AD]", _snowman + "[/MOTD]".length());
            if (_snowmanxx < 0) {
               return null;
            } else {
               int _snowmanxxx = announcement.indexOf("[/AD]", _snowmanxx + "[AD]".length());
               return _snowmanxxx < _snowmanxx ? null : announcement.substring(_snowmanxx + "[AD]".length(), _snowmanxxx);
            }
         }
      }
   }
}
