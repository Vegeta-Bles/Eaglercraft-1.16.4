/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import net.minecraft.server.rcon.RconBase;
import net.minecraft.server.rcon.RconClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconListener
extends RconBase {
    private static final Logger SERVER_LOGGER = LogManager.getLogger();
    private final ServerSocket listener;
    private final String password;
    private final List<RconClient> clients = Lists.newArrayList();
    private final DedicatedServer server;

    private RconListener(DedicatedServer dedicatedServer, ServerSocket serverSocket, String string) {
        super("RCON Listener");
        this.server = dedicatedServer;
        this.listener = serverSocket;
        this.password = string;
    }

    private void removeStoppedClients() {
        this.clients.removeIf(rconClient -> !rconClient.isRunning());
    }

    @Override
    public void run() {
        try {
            while (this.running) {
                try {
                    Socket socket = this.listener.accept();
                    RconClient _snowman2 = new RconClient(this.server, this.password, socket);
                    _snowman2.start();
                    this.clients.add(_snowman2);
                    this.removeStoppedClients();
                }
                catch (SocketTimeoutException socketTimeoutException) {
                    this.removeStoppedClients();
                }
                catch (IOException iOException) {
                    if (!this.running) continue;
                    SERVER_LOGGER.info("IO exception: ", (Throwable)iOException);
                }
            }
        }
        finally {
            this.closeSocket(this.listener);
        }
    }

    @Nullable
    public static RconListener create(DedicatedServer server) {
        ServerPropertiesHandler serverPropertiesHandler = server.getProperties();
        String _snowman2 = server.getHostname();
        if (_snowman2.isEmpty()) {
            _snowman2 = "0.0.0.0";
        }
        if (0 >= (_snowman = serverPropertiesHandler.rconPort) || 65535 < _snowman) {
            SERVER_LOGGER.warn("Invalid rcon port {} found in server.properties, rcon disabled!", (Object)_snowman);
            return null;
        }
        String _snowman3 = serverPropertiesHandler.rconPassword;
        if (_snowman3.isEmpty()) {
            SERVER_LOGGER.warn("No rcon password set in server.properties, rcon disabled!");
            return null;
        }
        try {
            ServerSocket serverSocket = new ServerSocket(_snowman, 0, InetAddress.getByName(_snowman2));
            serverSocket.setSoTimeout(500);
            RconListener _snowman4 = new RconListener(server, serverSocket, _snowman3);
            if (!_snowman4.start()) {
                return null;
            }
            SERVER_LOGGER.info("RCON running on {}:{}", (Object)_snowman2, (Object)_snowman);
            return _snowman4;
        }
        catch (IOException iOException) {
            SERVER_LOGGER.warn("Unable to initialise RCON on {}:{}", (Object)_snowman2, (Object)_snowman, (Object)iOException);
            return null;
        }
    }

    @Override
    public void stop() {
        this.running = false;
        this.closeSocket(this.listener);
        super.stop();
        for (RconClient rconClient : this.clients) {
            if (!rconClient.isRunning()) continue;
            rconClient.stop();
        }
        this.clients.clear();
    }

    private void closeSocket(ServerSocket socket) {
        SERVER_LOGGER.debug("closeSocket: {}", (Object)socket);
        try {
            socket.close();
        }
        catch (IOException iOException) {
            SERVER_LOGGER.warn("Failed to close socket", (Throwable)iOException);
        }
    }
}

