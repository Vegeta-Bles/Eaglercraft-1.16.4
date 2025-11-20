/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.screen;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.ServerAddress;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectScreen
extends Screen {
    private static final AtomicInteger CONNECTOR_THREADS_COUNT = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private ClientConnection connection;
    private boolean connectingCancelled;
    private final Screen parent;
    private Text status = new TranslatableText("connect.connecting");
    private long narratorTimer = -1L;

    public ConnectScreen(Screen parent, MinecraftClient client, ServerInfo entry) {
        super(NarratorManager.EMPTY);
        this.client = client;
        this.parent = parent;
        ServerAddress serverAddress = ServerAddress.parse(entry.address);
        client.disconnect();
        client.setCurrentServerEntry(entry);
        this.connect(serverAddress.getAddress(), serverAddress.getPort());
    }

    public ConnectScreen(Screen parent, MinecraftClient client, String address, int port) {
        super(NarratorManager.EMPTY);
        this.client = client;
        this.parent = parent;
        client.disconnect();
        this.connect(address, port);
    }

    private void connect(String address, int port) {
        LOGGER.info("Connecting to {}, {}", (Object)address, (Object)port);
        Thread thread = new Thread(this, "Server Connector #" + CONNECTOR_THREADS_COUNT.incrementAndGet(), address, port){
            final /* synthetic */ String field_2414;
            final /* synthetic */ int field_2415;
            final /* synthetic */ ConnectScreen field_2416;
            {
                this.field_2416 = connectScreen;
                this.field_2414 = string2;
                this.field_2415 = n;
                super(string);
            }

            public void run() {
                InetAddress inetAddress = null;
                try {
                    if (ConnectScreen.method_2134(this.field_2416)) {
                        return;
                    }
                    inetAddress = InetAddress.getByName(this.field_2414);
                    ConnectScreen.method_2126(this.field_2416, ClientConnection.connect(inetAddress, this.field_2415, this.field_2416.client.options.shouldUseNativeTransport()));
                    ConnectScreen.method_2129(this.field_2416).setPacketListener(new ClientLoginNetworkHandler(ConnectScreen.method_2129(this.field_2416), this.field_2416.client, ConnectScreen.method_2128(this.field_2416), text -> ConnectScreen.method_2132(this.field_2416, text)));
                    ConnectScreen.method_2129(this.field_2416).send(new HandshakeC2SPacket(this.field_2414, this.field_2415, NetworkState.LOGIN));
                    ConnectScreen.method_2129(this.field_2416).send(new LoginHelloC2SPacket(this.field_2416.client.getSession().getProfile()));
                }
                catch (UnknownHostException _snowman2) {
                    if (ConnectScreen.method_2134(this.field_2416)) {
                        return;
                    }
                    ConnectScreen.method_2133().error("Couldn't connect to server", (Throwable)_snowman2);
                    this.field_2416.client.execute(() -> this.field_2416.client.openScreen(new DisconnectedScreen(ConnectScreen.method_2128(this.field_2416), ScreenTexts.CONNECT_FAILED, new TranslatableText("disconnect.genericReason", "Unknown host"))));
                }
                catch (Exception _snowman3) {
                    if (ConnectScreen.method_2134(this.field_2416)) {
                        return;
                    }
                    ConnectScreen.method_2133().error("Couldn't connect to server", (Throwable)_snowman3);
                    String string = inetAddress == null ? _snowman3.toString() : _snowman3.toString().replaceAll(inetAddress + ":" + this.field_2415, "");
                    this.field_2416.client.execute(() -> this.field_2416.client.openScreen(new DisconnectedScreen(ConnectScreen.method_2128(this.field_2416), ScreenTexts.CONNECT_FAILED, new TranslatableText("disconnect.genericReason", string))));
                }
            }
        };
        thread.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
        thread.start();
    }

    private void setStatus(Text status) {
        this.status = status;
    }

    @Override
    public void tick() {
        if (this.connection != null) {
            if (this.connection.isOpen()) {
                this.connection.tick();
            } else {
                this.connection.handleDisconnection();
            }
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, ScreenTexts.CANCEL, buttonWidget -> {
            this.connectingCancelled = true;
            if (this.connection != null) {
                this.connection.disconnect(new TranslatableText("connect.aborted"));
            }
            this.client.openScreen(this.parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        long l = Util.getMeasuringTimeMs();
        if (l - this.narratorTimer > 2000L) {
            this.narratorTimer = l;
            NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.joining").getString());
        }
        ConnectScreen.drawCenteredText(matrices, this.textRenderer, this.status, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    static /* synthetic */ boolean method_2134(ConnectScreen connectScreen) {
        return connectScreen.connectingCancelled;
    }

    static /* synthetic */ ClientConnection method_2126(ConnectScreen connectScreen, ClientConnection clientConnection) {
        connectScreen.connection = clientConnection;
        return connectScreen.connection;
    }

    static /* synthetic */ ClientConnection method_2129(ConnectScreen connectScreen) {
        return connectScreen.connection;
    }

    static /* synthetic */ Screen method_2128(ConnectScreen connectScreen) {
        return connectScreen.parent;
    }

    static /* synthetic */ Logger method_2133() {
        return LOGGER;
    }

    static /* synthetic */ void method_2132(ConnectScreen connectScreen, Text text) {
        connectScreen.setStatus(text);
    }
}

