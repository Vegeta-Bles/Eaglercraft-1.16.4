import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class eni {
   private static final AtomicInteger a = new AtomicInteger(0);
   private static final Logger b = LogManager.getLogger();

   public static class a extends Thread {
      private final eni.b a;
      private final InetAddress b;
      private final MulticastSocket c;

      public a(eni.b var1) throws IOException {
         super("LanServerDetector #" + eni.a.incrementAndGet());
         this.a = _snowman;
         this.setDaemon(true);
         this.setUncaughtExceptionHandler(new o(eni.b));
         this.c = new MulticastSocket(4445);
         this.b = InetAddress.getByName("224.0.2.60");
         this.c.setSoTimeout(5000);
         this.c.joinGroup(this.b);
      }

      @Override
      public void run() {
         byte[] _snowman = new byte[1024];

         while (!this.isInterrupted()) {
            DatagramPacket _snowmanx = new DatagramPacket(_snowman, _snowman.length);

            try {
               this.c.receive(_snowmanx);
            } catch (SocketTimeoutException var5) {
               continue;
            } catch (IOException var6) {
               eni.b.error("Couldn't ping server", var6);
               break;
            }

            String _snowmanxx = new String(_snowmanx.getData(), _snowmanx.getOffset(), _snowmanx.getLength(), StandardCharsets.UTF_8);
            eni.b.debug("{}: {}", _snowmanx.getAddress(), _snowmanxx);
            this.a.a(_snowmanxx, _snowmanx.getAddress());
         }

         try {
            this.c.leaveGroup(this.b);
         } catch (IOException var4) {
         }

         this.c.close();
      }
   }

   public static class b {
      private final List<enh> a = Lists.newArrayList();
      private boolean b;

      public b() {
      }

      public synchronized boolean a() {
         return this.b;
      }

      public synchronized void b() {
         this.b = false;
      }

      public synchronized List<enh> c() {
         return Collections.unmodifiableList(this.a);
      }

      public synchronized void a(String var1, InetAddress var2) {
         String _snowman = enj.a(_snowman);
         String _snowmanx = enj.b(_snowman);
         if (_snowmanx != null) {
            _snowmanx = _snowman.getHostAddress() + ":" + _snowmanx;
            boolean _snowmanxx = false;

            for (enh _snowmanxxx : this.a) {
               if (_snowmanxxx.b().equals(_snowmanx)) {
                  _snowmanxxx.c();
                  _snowmanxx = true;
                  break;
               }
            }

            if (!_snowmanxx) {
               this.a.add(new enh(_snowman, _snowmanx));
               this.b = true;
            }
         }
      }
   }
}
