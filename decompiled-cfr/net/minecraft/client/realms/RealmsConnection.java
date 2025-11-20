/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.class_4902;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.realms.Realms;
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

    public void connect(RealmsServer realmsServer, String string, int n) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.setConnectedToRealms(true);
        Realms.narrateNow(I18n.translate("mco.connect.success", new Object[0]));
        new Thread(this, "Realms-connect-task", string, n, minecraftClient, realmsServer){
            final /* synthetic */ String field_11112;
            final /* synthetic */ int field_11114;
            final /* synthetic */ MinecraftClient field_22818;
            final /* synthetic */ RealmsServer field_26928;
            final /* synthetic */ RealmsConnection field_11113;
            {
                this.field_11113 = realmsConnection;
                this.field_11112 = string2;
                this.field_11114 = n;
                this.field_22818 = minecraftClient;
                this.field_26928 = realmsServer;
                super(string);
            }

            public void run() {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName(this.field_11112);
                    if (RealmsConnection.method_25478(this.field_11113)) {
                        return;
                    }
                    RealmsConnection.method_25479(this.field_11113, ClientConnection.connect(inetAddress, this.field_11114, this.field_22818.options.shouldUseNativeTransport()));
                    if (RealmsConnection.method_25478(this.field_11113)) {
                        return;
                    }
                    RealmsConnection.method_25482(this.field_11113).setPacketListener(new ClientLoginNetworkHandler(RealmsConnection.method_25482(this.field_11113), this.field_22818, RealmsConnection.method_25484(this.field_11113), text -> {}));
                    if (RealmsConnection.method_25478(this.field_11113)) {
                        return;
                    }
                    RealmsConnection.method_25482(this.field_11113).send(new HandshakeC2SPacket(this.field_11112, this.field_11114, NetworkState.LOGIN));
                    if (RealmsConnection.method_25478(this.field_11113)) {
                        return;
                    }
                    RealmsConnection.method_25482(this.field_11113).send(new LoginHelloC2SPacket(this.field_22818.getSession().getProfile()));
                    this.field_22818.setCurrentServerEntry(this.field_26928.method_31403(this.field_11112));
                }
                catch (UnknownHostException _snowman2) {
                    this.field_22818.getResourcePackDownloader().clear();
                    if (RealmsConnection.method_25478(this.field_11113)) {
                        return;
                    }
                    RealmsConnection.method_25483().error("Couldn't connect to world", (Throwable)_snowman2);
                    DisconnectedRealmsScreen disconnectedRealmsScreen = new DisconnectedRealmsScreen(RealmsConnection.method_25484(this.field_11113), ScreenTexts.CONNECT_FAILED, new TranslatableText("disconnect.genericReason", "Unknown host '" + this.field_11112 + "'"));
                    this.field_22818.execute(() -> this.field_22818.openScreen(disconnectedRealmsScreen));
                }
                catch (Exception exception) {
                    this.field_22818.getResourcePackDownloader().clear();
                    if (RealmsConnection.method_25478(this.field_11113)) {
                        return;
                    }
                    RealmsConnection.method_25483().error("Couldn't connect to world", (Throwable)exception);
                    String _snowman3 = exception.toString();
                    if (inetAddress != null) {
                        Object object = inetAddress + ":" + this.field_11114;
                        _snowman3 = _snowman3.replaceAll((String)object, "");
                    }
                    object = new DisconnectedRealmsScreen(RealmsConnection.method_25484(this.field_11113), ScreenTexts.CONNECT_FAILED, new TranslatableText("disconnect.genericReason", _snowman3));
                    this.field_22818.execute(() -> class_4902.1.method_25485(this.field_22818, (DisconnectedRealmsScreen)object));
                }
            }

            private static /* synthetic */ void method_25485(MinecraftClient minecraftClient, DisconnectedRealmsScreen disconnectedRealmsScreen) {
                minecraftClient.openScreen(disconnectedRealmsScreen);
            }
        }.start();
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

    static /* synthetic */ boolean method_25478(RealmsConnection realmsConnection) {
        return realmsConnection.aborted;
    }

    static /* synthetic */ ClientConnection method_25479(RealmsConnection realmsConnection, ClientConnection clientConnection) {
        realmsConnection.connection = clientConnection;
        return realmsConnection.connection;
    }

    static /* synthetic */ ClientConnection method_25482(RealmsConnection realmsConnection) {
        return realmsConnection.connection;
    }

    static /* synthetic */ Screen method_25484(RealmsConnection realmsConnection) {
        return realmsConnection.onlineScreen;
    }

    static /* synthetic */ Logger method_25483() {
        return LOGGER;
    }
}

