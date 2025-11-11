import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adl extends adi {
   private static final Logger d = LogManager.getLogger();
   private final ServerSocket e;
   private final String f;
   private final List<adk> g = Lists.newArrayList();
   private final vy h;

   private adl(vy var1, ServerSocket var2, String var3) {
      super("RCON Listener");
      this.h = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   private void d() {
      this.g.removeIf(var0 -> !var0.c());
   }

   @Override
   public void run() {
      try {
         while (this.a) {
            try {
               Socket _snowman = this.e.accept();
               adk _snowmanx = new adk(this.h, this.f, _snowman);
               _snowmanx.a();
               this.g.add(_snowmanx);
               this.d();
            } catch (SocketTimeoutException var7) {
               this.d();
            } catch (IOException var8) {
               if (this.a) {
                  d.info("IO exception: ", var8);
               }
            }
         }
      } finally {
         this.a(this.e);
      }
   }

   @Nullable
   public static adl a(vy var0) {
      zh _snowman = _snowman.g_();
      String _snowmanx = _snowman.h_();
      if (_snowmanx.isEmpty()) {
         _snowmanx = "0.0.0.0";
      }

      int _snowmanxx = _snowman.u;
      if (0 < _snowmanxx && 65535 >= _snowmanxx) {
         String _snowmanxxx = _snowman.v;
         if (_snowmanxxx.isEmpty()) {
            d.warn("No rcon password set in server.properties, rcon disabled!");
            return null;
         } else {
            try {
               ServerSocket _snowmanxxxx = new ServerSocket(_snowmanxx, 0, InetAddress.getByName(_snowmanx));
               _snowmanxxxx.setSoTimeout(500);
               adl _snowmanxxxxx = new adl(_snowman, _snowmanxxxx, _snowmanxxx);
               if (!_snowmanxxxxx.a()) {
                  return null;
               } else {
                  d.info("RCON running on {}:{}", _snowmanx, _snowmanxx);
                  return _snowmanxxxxx;
               }
            } catch (IOException var7) {
               d.warn("Unable to initialise RCON on {}:{}", _snowmanx, _snowmanxx, var7);
               return null;
            }
         }
      } else {
         d.warn("Invalid rcon port {} found in server.properties, rcon disabled!", _snowmanxx);
         return null;
      }
   }

   @Override
   public void b() {
      this.a = false;
      this.a(this.e);
      super.b();

      for (adk _snowman : this.g) {
         if (_snowman.c()) {
            _snowman.b();
         }
      }

      this.g.clear();
   }

   private void a(ServerSocket var1) {
      d.debug("closeSocket: {}", _snowman);

      try {
         _snowman.close();
      } catch (IOException var3) {
         d.warn("Failed to close socket", var3);
      }
   }
}
