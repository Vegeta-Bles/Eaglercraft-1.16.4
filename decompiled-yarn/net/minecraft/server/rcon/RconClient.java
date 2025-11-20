package net.minecraft.server.rcon;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconClient extends RconBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private boolean authenticated;
   private final Socket socket;
   private final byte[] packetBuffer = new byte[1460];
   private final String password;
   private final DedicatedServer server;

   RconClient(DedicatedServer server, String password, Socket socket) {
      super("RCON Client " + socket.getInetAddress());
      this.server = server;
      this.socket = socket;

      try {
         this.socket.setSoTimeout(0);
      } catch (Exception var5) {
         this.running = false;
      }

      this.password = password;
   }

   @Override
   public void run() {
      try {
         try {
            while (this.running) {
               BufferedInputStream _snowman = new BufferedInputStream(this.socket.getInputStream());
               int _snowmanx = _snowman.read(this.packetBuffer, 0, 1460);
               if (10 > _snowmanx) {
                  return;
               }

               int _snowmanxx = 0;
               int _snowmanxxx = BufferHelper.getIntLE(this.packetBuffer, 0, _snowmanx);
               if (_snowmanxxx != _snowmanx - 4) {
                  return;
               }

               _snowmanxx += 4;
               int _snowmanxxxx = BufferHelper.getIntLE(this.packetBuffer, _snowmanxx, _snowmanx);
               _snowmanxx += 4;
               int _snowmanxxxxx = BufferHelper.getIntLE(this.packetBuffer, _snowmanxx);
               _snowmanxx += 4;
               switch (_snowmanxxxxx) {
                  case 2:
                     if (this.authenticated) {
                        String _snowmanxxxxxx = BufferHelper.getString(this.packetBuffer, _snowmanxx, _snowmanx);

                        try {
                           this.respond(_snowmanxxxx, this.server.executeRconCommand(_snowmanxxxxxx));
                        } catch (Exception var15) {
                           this.respond(_snowmanxxxx, "Error executing: " + _snowmanxxxxxx + " (" + var15.getMessage() + ")");
                        }
                        break;
                     }

                     this.fail();
                     break;
                  case 3:
                     String _snowmanxxxxxx = BufferHelper.getString(this.packetBuffer, _snowmanxx, _snowmanx);
                     _snowmanxx += _snowmanxxxxxx.length();
                     if (!_snowmanxxxxxx.isEmpty() && _snowmanxxxxxx.equals(this.password)) {
                        this.authenticated = true;
                        this.respond(_snowmanxxxx, 2, "");
                        break;
                     }

                     this.authenticated = false;
                     this.fail();
                     break;
                  default:
                     this.respond(_snowmanxxxx, String.format("Unknown request %s", Integer.toHexString(_snowmanxxxxx)));
               }
            }

            return;
         } catch (IOException var16) {
         } catch (Exception var17) {
            LOGGER.error("Exception whilst parsing RCON input", var17);
         }
      } finally {
         this.close();
         LOGGER.info("Thread {} shutting down", this.description);
         this.running = false;
      }
   }

   private void respond(int sessionToken, int responseType, String message) throws IOException {
      ByteArrayOutputStream _snowman = new ByteArrayOutputStream(1248);
      DataOutputStream _snowmanx = new DataOutputStream(_snowman);
      byte[] _snowmanxx = message.getBytes(StandardCharsets.UTF_8);
      _snowmanx.writeInt(Integer.reverseBytes(_snowmanxx.length + 10));
      _snowmanx.writeInt(Integer.reverseBytes(sessionToken));
      _snowmanx.writeInt(Integer.reverseBytes(responseType));
      _snowmanx.write(_snowmanxx);
      _snowmanx.write(0);
      _snowmanx.write(0);
      this.socket.getOutputStream().write(_snowman.toByteArray());
   }

   private void fail() throws IOException {
      this.respond(-1, 2, "");
   }

   private void respond(int sessionToken, String message) throws IOException {
      int _snowman = message.length();

      do {
         int _snowmanx = 4096 <= _snowman ? 4096 : _snowman;
         this.respond(sessionToken, 0, message.substring(0, _snowmanx));
         message = message.substring(_snowmanx);
         _snowman = message.length();
      } while (0 != _snowman);
   }

   @Override
   public void stop() {
      this.running = false;
      this.close();
      super.stop();
   }

   private void close() {
      try {
         this.socket.close();
      } catch (IOException var2) {
         LOGGER.warn("Failed to close socket", var2);
      }
   }
}
