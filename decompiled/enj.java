import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class enj extends Thread {
   private static final AtomicInteger a = new AtomicInteger(0);
   private static final Logger b = LogManager.getLogger();
   private final String c;
   private final DatagramSocket d;
   private boolean e = true;
   private final String f;

   public enj(String var1, String var2) throws IOException {
      super("LanServerPinger #" + a.incrementAndGet());
      this.c = _snowman;
      this.f = _snowman;
      this.setDaemon(true);
      this.setUncaughtExceptionHandler(new o(b));
      this.d = new DatagramSocket();
   }

   @Override
   public void run() {
      String _snowman = a(this.c, this.f);
      byte[] _snowmanx = _snowman.getBytes(StandardCharsets.UTF_8);

      while (!this.isInterrupted() && this.e) {
         try {
            InetAddress _snowmanxx = InetAddress.getByName("224.0.2.60");
            DatagramPacket _snowmanxxx = new DatagramPacket(_snowmanx, _snowmanx.length, _snowmanxx, 4445);
            this.d.send(_snowmanxxx);
         } catch (IOException var6) {
            b.warn("LanServerPinger: {}", var6.getMessage());
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
      this.e = false;
   }

   public static String a(String var0, String var1) {
      return "[MOTD]" + _snowman + "[/MOTD][AD]" + _snowman + "[/AD]";
   }

   public static String a(String var0) {
      int _snowman = _snowman.indexOf("[MOTD]");
      if (_snowman < 0) {
         return "missing no";
      } else {
         int _snowmanx = _snowman.indexOf("[/MOTD]", _snowman + "[MOTD]".length());
         return _snowmanx < _snowman ? "missing no" : _snowman.substring(_snowman + "[MOTD]".length(), _snowmanx);
      }
   }

   public static String b(String var0) {
      int _snowman = _snowman.indexOf("[/MOTD]");
      if (_snowman < 0) {
         return null;
      } else {
         int _snowmanx = _snowman.indexOf("[/MOTD]", _snowman + "[/MOTD]".length());
         if (_snowmanx >= 0) {
            return null;
         } else {
            int _snowmanxx = _snowman.indexOf("[AD]", _snowman + "[/MOTD]".length());
            if (_snowmanxx < 0) {
               return null;
            } else {
               int _snowmanxxx = _snowman.indexOf("[/AD]", _snowmanxx + "[AD]".length());
               return _snowmanxxx < _snowmanxx ? null : _snowman.substring(_snowmanxx + "[AD]".length(), _snowmanxxx);
            }
         }
      }
   }
}
