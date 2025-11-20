/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.server.LanServerPinger;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerQueryManager {
    private static final AtomicInteger THREAD_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();

    public static class LanServerDetector
    extends Thread {
        private final LanServerEntryList entryList;
        private final InetAddress multicastAddress;
        private final MulticastSocket socket;

        public LanServerDetector(LanServerEntryList lanServerEntryList) throws IOException {
            super("LanServerDetector #" + THREAD_ID.incrementAndGet());
            this.entryList = lanServerEntryList;
            this.setDaemon(true);
            this.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
            this.socket = new MulticastSocket(4445);
            this.multicastAddress = InetAddress.getByName("224.0.2.60");
            this.socket.setSoTimeout(5000);
            this.socket.joinGroup(this.multicastAddress);
        }

        @Override
        public void run() {
            byte[] byArray = new byte[1024];
            while (!this.isInterrupted()) {
                DatagramPacket datagramPacket = new DatagramPacket(byArray, byArray.length);
                try {
                    this.socket.receive(datagramPacket);
                }
                catch (SocketTimeoutException _snowman2) {
                    continue;
                }
                catch (IOException _snowman3) {
                    LOGGER.error("Couldn't ping server", (Throwable)_snowman3);
                    break;
                }
                String _snowman4 = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength(), StandardCharsets.UTF_8);
                LOGGER.debug("{}: {}", (Object)datagramPacket.getAddress(), (Object)_snowman4);
                this.entryList.addServer(_snowman4, datagramPacket.getAddress());
            }
            try {
                this.socket.leaveGroup(this.multicastAddress);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.socket.close();
        }
    }

    public static class LanServerEntryList {
        private final List<LanServerInfo> serverEntries = Lists.newArrayList();
        private boolean dirty;

        public synchronized boolean needsUpdate() {
            return this.dirty;
        }

        public synchronized void markClean() {
            this.dirty = false;
        }

        public synchronized List<LanServerInfo> getServers() {
            return Collections.unmodifiableList(this.serverEntries);
        }

        public synchronized void addServer(String string, InetAddress inetAddress) {
            String string2 = LanServerPinger.parseAnnouncementMotd(string);
            _snowman = LanServerPinger.parseAnnouncementAddressPort(string);
            if (_snowman == null) {
                return;
            }
            _snowman = inetAddress.getHostAddress() + ":" + _snowman;
            boolean _snowman2 = false;
            for (LanServerInfo lanServerInfo : this.serverEntries) {
                if (!lanServerInfo.getAddressPort().equals(_snowman)) continue;
                lanServerInfo.updateLastTime();
                _snowman2 = true;
                break;
            }
            if (!_snowman2) {
                this.serverEntries.add(new LanServerInfo(string2, _snowman));
                this.dirty = true;
            }
        }
    }
}

