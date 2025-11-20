/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerPinger
extends Thread {
    private static final AtomicInteger THREAD_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private final String motd;
    private final DatagramSocket socket;
    private boolean running = true;
    private final String addressPort;

    public LanServerPinger(String motd, String addressPort) throws IOException {
        super("LanServerPinger #" + THREAD_ID.incrementAndGet());
        this.motd = motd;
        this.addressPort = addressPort;
        this.setDaemon(true);
        this.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
        this.socket = new DatagramSocket();
    }

    @Override
    public void run() {
        String string = LanServerPinger.createAnnouncement(this.motd, this.addressPort);
        byte[] _snowman2 = string.getBytes(StandardCharsets.UTF_8);
        while (!this.isInterrupted() && this.running) {
            try {
                InetAddress inetAddress = InetAddress.getByName("224.0.2.60");
                DatagramPacket _snowman3 = new DatagramPacket(_snowman2, _snowman2.length, inetAddress, 4445);
                this.socket.send(_snowman3);
            }
            catch (IOException iOException) {
                LOGGER.warn("LanServerPinger: {}", (Object)iOException.getMessage());
                break;
            }
            try {
                LanServerPinger.sleep(1500L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.running = false;
    }

    public static String createAnnouncement(String motd, String addressPort) {
        return "[MOTD]" + motd + "[/MOTD][AD]" + addressPort + "[/AD]";
    }

    public static String parseAnnouncementMotd(String announcement) {
        int n = announcement.indexOf("[MOTD]");
        if (n < 0) {
            return "missing no";
        }
        _snowman = announcement.indexOf("[/MOTD]", n + "[MOTD]".length());
        if (_snowman < n) {
            return "missing no";
        }
        return announcement.substring(n + "[MOTD]".length(), _snowman);
    }

    public static String parseAnnouncementAddressPort(String announcement) {
        int n = announcement.indexOf("[/MOTD]");
        if (n < 0) {
            return null;
        }
        _snowman = announcement.indexOf("[/MOTD]", n + "[/MOTD]".length());
        if (_snowman >= 0) {
            return null;
        }
        _snowman = announcement.indexOf("[AD]", n + "[/MOTD]".length());
        if (_snowman < 0) {
            return null;
        }
        _snowman = announcement.indexOf("[/AD]", _snowman + "[AD]".length());
        if (_snowman < _snowman) {
            return null;
        }
        return announcement.substring(_snowman + "[AD]".length(), _snowman);
    }
}

