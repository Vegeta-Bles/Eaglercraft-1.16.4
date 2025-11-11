import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adk extends adi {
   private static final Logger d = LogManager.getLogger();
   private boolean e;
   private final Socket f;
   private final byte[] g = new byte[1460];
   private final String h;
   private final vy i;

   adk(vy var1, String var2, Socket var3) {
      super("RCON Client " + _snowman.getInetAddress());
      this.i = _snowman;
      this.f = _snowman;

      try {
         this.f.setSoTimeout(0);
      } catch (Exception var5) {
         this.a = false;
      }

      this.h = _snowman;
   }

   @Override
   public void run() {
      try {
         try {
            while (this.a) {
               BufferedInputStream _snowman = new BufferedInputStream(this.f.getInputStream());
               int _snowmanx = _snowman.read(this.g, 0, 1460);
               if (10 > _snowmanx) {
                  return;
               }

               int _snowmanxx = 0;
               int _snowmanxxx = adf.b(this.g, 0, _snowmanx);
               if (_snowmanxxx != _snowmanx - 4) {
                  return;
               }

               _snowmanxx += 4;
               int _snowmanxxxx = adf.b(this.g, _snowmanxx, _snowmanx);
               _snowmanxx += 4;
               int _snowmanxxxxx = adf.a(this.g, _snowmanxx);
               _snowmanxx += 4;
               switch (_snowmanxxxxx) {
                  case 2:
                     if (this.e) {
                        String _snowmanxxxxxx = adf.a(this.g, _snowmanxx, _snowmanx);

                        try {
                           this.a(_snowmanxxxx, this.i.a(_snowmanxxxxxx));
                        } catch (Exception var15) {
                           this.a(_snowmanxxxx, "Error executing: " + _snowmanxxxxxx + " (" + var15.getMessage() + ")");
                        }
                        break;
                     }

                     this.d();
                     break;
                  case 3:
                     String _snowmanxxxxxx = adf.a(this.g, _snowmanxx, _snowmanx);
                     _snowmanxx += _snowmanxxxxxx.length();
                     if (!_snowmanxxxxxx.isEmpty() && _snowmanxxxxxx.equals(this.h)) {
                        this.e = true;
                        this.a(_snowmanxxxx, 2, "");
                        break;
                     }

                     this.e = false;
                     this.d();
                     break;
                  default:
                     this.a(_snowmanxxxx, String.format("Unknown request %s", Integer.toHexString(_snowmanxxxxx)));
               }
            }

            return;
         } catch (IOException var16) {
         } catch (Exception var17) {
            d.error("Exception whilst parsing RCON input", var17);
         }
      } finally {
         this.e();
         d.info("Thread {} shutting down", this.b);
         this.a = false;
      }
   }

   private void a(int var1, int var2, String var3) throws IOException {
      ByteArrayOutputStream _snowman = new ByteArrayOutputStream(1248);
      DataOutputStream _snowmanx = new DataOutputStream(_snowman);
      byte[] _snowmanxx = _snowman.getBytes(StandardCharsets.UTF_8);
      _snowmanx.writeInt(Integer.reverseBytes(_snowmanxx.length + 10));
      _snowmanx.writeInt(Integer.reverseBytes(_snowman));
      _snowmanx.writeInt(Integer.reverseBytes(_snowman));
      _snowmanx.write(_snowmanxx);
      _snowmanx.write(0);
      _snowmanx.write(0);
      this.f.getOutputStream().write(_snowman.toByteArray());
   }

   private void d() throws IOException {
      this.a(-1, 2, "");
   }

   private void a(int var1, String var2) throws IOException {
      int _snowman = _snowman.length();

      do {
         int _snowmanx = 4096 <= _snowman ? 4096 : _snowman;
         this.a(_snowman, 0, _snowman.substring(0, _snowmanx));
         _snowman = _snowman.substring(_snowmanx);
         _snowman = _snowman.length();
      } while (0 != _snowman);
   }

   @Override
   public void b() {
      this.a = false;
      this.e();
      super.b();
   }

   private void e() {
      try {
         this.f.close();
      } catch (IOException var2) {
         d.warn("Failed to close socket", var2);
      }
   }
}
