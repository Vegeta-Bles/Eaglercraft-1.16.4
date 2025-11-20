/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.rcon;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.rcon.BufferHelper;
import net.minecraft.server.rcon.RconBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconClient
extends RconBase {
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
        }
        catch (Exception exception) {
            this.running = false;
        }
        this.password = password;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            while (this.running) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(this.socket.getInputStream());
                int _snowman2 = bufferedInputStream.read(this.packetBuffer, 0, 1460);
                if (10 > _snowman2) {
                    return;
                }
                int _snowman3 = 0;
                int _snowman4 = BufferHelper.getIntLE(this.packetBuffer, 0, _snowman2);
                if (_snowman4 != _snowman2 - 4) {
                    return;
                }
                int _snowman5 = BufferHelper.getIntLE(this.packetBuffer, _snowman3 += 4, _snowman2);
                int _snowman6 = BufferHelper.getIntLE(this.packetBuffer, _snowman3 += 4);
                _snowman3 += 4;
                switch (_snowman6) {
                    case 3: {
                        String string = BufferHelper.getString(this.packetBuffer, _snowman3, _snowman2);
                        _snowman3 += string.length();
                        if (!string.isEmpty() && string.equals(this.password)) {
                            this.authenticated = true;
                            this.respond(_snowman5, 2, "");
                            break;
                        }
                        this.authenticated = false;
                        this.fail();
                        break;
                    }
                    case 2: {
                        if (this.authenticated) {
                            String string = BufferHelper.getString(this.packetBuffer, _snowman3, _snowman2);
                            try {
                                this.respond(_snowman5, this.server.executeRconCommand(string));
                            }
                            catch (Exception _snowman7) {
                                this.respond(_snowman5, "Error executing: " + string + " (" + _snowman7.getMessage() + ")");
                            }
                            break;
                        }
                        this.fail();
                        break;
                    }
                    default: {
                        this.respond(_snowman5, String.format("Unknown request %s", Integer.toHexString(_snowman6)));
                    }
                }
            }
        }
        catch (IOException bufferedInputStream) {
        }
        catch (Exception exception) {
            LOGGER.error("Exception whilst parsing RCON input", (Throwable)exception);
        }
        finally {
            this.close();
            LOGGER.info("Thread {} shutting down", (Object)this.description);
            this.running = false;
        }
    }

    private void respond(int sessionToken, int responseType, String message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1248);
        DataOutputStream _snowman2 = new DataOutputStream(byteArrayOutputStream);
        byte[] _snowman3 = message.getBytes(StandardCharsets.UTF_8);
        _snowman2.writeInt(Integer.reverseBytes(_snowman3.length + 10));
        _snowman2.writeInt(Integer.reverseBytes(sessionToken));
        _snowman2.writeInt(Integer.reverseBytes(responseType));
        _snowman2.write(_snowman3);
        _snowman2.write(0);
        _snowman2.write(0);
        this.socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
    }

    private void fail() throws IOException {
        this.respond(-1, 2, "");
    }

    private void respond(int sessionToken, String message) throws IOException {
        int n = message.length();
        do {
            _snowman = 4096 <= n ? 4096 : n;
            this.respond(sessionToken, 0, message.substring(0, _snowman));
        } while (0 != (n = (message = message.substring(_snowman)).length()));
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
        }
        catch (IOException iOException) {
            LOGGER.warn("Failed to close socket", (Throwable)iOException);
        }
    }
}

