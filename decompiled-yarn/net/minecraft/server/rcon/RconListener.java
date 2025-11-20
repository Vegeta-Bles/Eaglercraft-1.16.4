package net.minecraft.server.rcon;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconListener extends RconBase {
   private static final Logger SERVER_LOGGER = LogManager.getLogger();
   private final ServerSocket listener;
   private final String password;
   private final List<RconClient> clients = Lists.newArrayList();
   private final DedicatedServer server;

   private RconListener(DedicatedServer _snowman, ServerSocket _snowman, String _snowman) {
      super("RCON Listener");
      this.server = _snowman;
      this.listener = _snowman;
      this.password = _snowman;
   }

   private void removeStoppedClients() {
      this.clients.removeIf(_snowman -> !_snowman.isRunning());
   }

   @Override
   public void run() {
      try {
         while (this.running) {
            try {
               Socket _snowman = this.listener.accept();
               RconClient _snowmanx = new RconClient(this.server, this.password, _snowman);
               _snowmanx.start();
               this.clients.add(_snowmanx);
               this.removeStoppedClients();
            } catch (SocketTimeoutException var7) {
               this.removeStoppedClients();
            } catch (IOException var8) {
               if (this.running) {
                  SERVER_LOGGER.info("IO exception: ", var8);
               }
            }
         }
      } finally {
         this.closeSocket(this.listener);
      }
   }

   @Nullable
   public static RconListener create(DedicatedServer server) {
      ServerPropertiesHandler _snowman = server.getProperties();
      String _snowmanx = server.getHostname();
      if (_snowmanx.isEmpty()) {
         _snowmanx = "0.0.0.0";
      }

      int _snowmanxx = _snowman.rconPort;
      if (0 < _snowmanxx && 65535 >= _snowmanxx) {
         String _snowmanxxx = _snowman.rconPassword;
         if (_snowmanxxx.isEmpty()) {
            SERVER_LOGGER.warn("No rcon password set in server.properties, rcon disabled!");
            return null;
         } else {
            try {
               ServerSocket _snowmanxxxx = new ServerSocket(_snowmanxx, 0, InetAddress.getByName(_snowmanx));
               _snowmanxxxx.setSoTimeout(500);
               RconListener _snowmanxxxxx = new RconListener(server, _snowmanxxxx, _snowmanxxx);
               if (!_snowmanxxxxx.start()) {
                  return null;
               } else {
                  SERVER_LOGGER.info("RCON running on {}:{}", _snowmanx, _snowmanxx);
                  return _snowmanxxxxx;
               }
            } catch (IOException var7) {
               SERVER_LOGGER.warn("Unable to initialise RCON on {}:{}", _snowmanx, _snowmanxx, var7);
               return null;
            }
         }
      } else {
         SERVER_LOGGER.warn("Invalid rcon port {} found in server.properties, rcon disabled!", _snowmanxx);
         return null;
      }
   }

   @Override
   public void stop() {
      this.running = false;
      this.closeSocket(this.listener);
      super.stop();

      for (RconClient _snowman : this.clients) {
         if (_snowman.isRunning()) {
            _snowman.stop();
         }
      }

      this.clients.clear();
   }

   private void closeSocket(ServerSocket socket) {
      SERVER_LOGGER.debug("closeSocket: {}", socket);

      try {
         socket.close();
      } catch (IOException var3) {
         SERVER_LOGGER.warn("Failed to close socket", var3);
      }
   }
}
