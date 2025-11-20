package net.minecraft.client.realms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.gui.screen.DisconnectedRealmsScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnection {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Screen onlineScreen;
   private volatile boolean aborted;
   private ClientConnection connection;

   public RealmsConnection(Screen onlineScreen) {
      this.onlineScreen = onlineScreen;
   }

   public void connect(RealmsServer _snowman, String _snowman, int _snowman) {
      final MinecraftClient _snowmanxxx = MinecraftClient.getInstance();
      _snowmanxxx.setConnectedToRealms(true);
      Realms.narrateNow(I18n.translate("mco.connect.success"));
      (new Thread("Realms-connect-task") {
            @Override
            public void run() {
               InetAddress _snowman = null;

               try {
                  _snowman = InetAddress.getByName(_snowman);
                  if (RealmsConnection.this.aborted) {
                     return;
                  }

                  RealmsConnection.this.connection = ClientConnection.connect(_snowman, _snowman, _snowman.options.shouldUseNativeTransport());
                  if (RealmsConnection.this.aborted) {
                     return;
                  }

                  RealmsConnection.this.connection
                     .setPacketListener(new ClientLoginNetworkHandler(RealmsConnection.this.connection, _snowman, RealmsConnection.this.onlineScreen, _snowmanxxx -> {
                     }));
                  if (RealmsConnection.this.aborted) {
                     return;
                  }

                  RealmsConnection.this.connection.send(new HandshakeC2SPacket(_snowman, _snowman, NetworkState.LOGIN));
                  if (RealmsConnection.this.aborted) {
                     return;
                  }

                  RealmsConnection.this.connection.send(new LoginHelloC2SPacket(_snowman.getSession().getProfile()));
                  _snowman.setCurrentServerEntry(_snowman.method_31403(_snowman));
               } catch (UnknownHostException var5) {
                  _snowman.getResourcePackDownloader().clear();
                  if (RealmsConnection.this.aborted) {
                     return;
                  }

                  RealmsConnection.LOGGER.error("Couldn't connect to world", var5);
                  DisconnectedRealmsScreen _snowmanx = new DisconnectedRealmsScreen(
                     RealmsConnection.this.onlineScreen,
                     ScreenTexts.CONNECT_FAILED,
                     new TranslatableText("disconnect.genericReason", "Unknown host '" + _snowman + "'")
                  );
                  _snowman.execute(() -> _snowman.openScreen(_snowman));
               } catch (Exception var6) {
                  _snowman.getResourcePackDownloader().clear();
                  if (RealmsConnection.this.aborted) {
                     return;
                  }

                  RealmsConnection.LOGGER.error("Couldn't connect to world", var6);
                  String _snowmanx = var6.toString();
                  if (_snowman != null) {
                     String _snowmanxx = _snowman + ":" + _snowman;
                     _snowmanx = _snowmanx.replaceAll(_snowmanxx, "");
                  }

                  DisconnectedRealmsScreen _snowmanxx = new DisconnectedRealmsScreen(
                     RealmsConnection.this.onlineScreen, ScreenTexts.CONNECT_FAILED, new TranslatableText("disconnect.genericReason", _snowmanx)
                  );
                  _snowman.execute(() -> _snowman.openScreen(_snowman));
               }
            }
         })
         .start();
   }

   public void abort() {
      this.aborted = true;
      if (this.connection != null && this.connection.isOpen()) {
         this.connection.disconnect(new TranslatableText("disconnect.genericReason"));
         this.connection.handleDisconnection();
      }
   }

   public void tick() {
      if (this.connection != null) {
         if (this.connection.isOpen()) {
            this.connection.tick();
         } else {
            this.connection.handleDisconnection();
         }
      }
   }
}
