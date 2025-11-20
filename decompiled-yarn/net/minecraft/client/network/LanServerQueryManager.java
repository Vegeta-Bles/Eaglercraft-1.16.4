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
import net.minecraft.server.LanServerPinger;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerQueryManager {
   private static final AtomicInteger THREAD_ID = new AtomicInteger(0);
   private static final Logger LOGGER = LogManager.getLogger();

   public static class LanServerDetector extends Thread {
      private final LanServerQueryManager.LanServerEntryList entryList;
      private final InetAddress multicastAddress;
      private final MulticastSocket socket;

      public LanServerDetector(LanServerQueryManager.LanServerEntryList _snowman) throws IOException {
         super("LanServerDetector #" + LanServerQueryManager.THREAD_ID.incrementAndGet());
         this.entryList = _snowman;
         this.setDaemon(true);
         this.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LanServerQueryManager.LOGGER));
         this.socket = new MulticastSocket(4445);
         this.multicastAddress = InetAddress.getByName("224.0.2.60");
         this.socket.setSoTimeout(5000);
         this.socket.joinGroup(this.multicastAddress);
      }

      @Override
      public void run() {
         byte[] _snowman = new byte[1024];

         while (!this.isInterrupted()) {
            DatagramPacket _snowmanx = new DatagramPacket(_snowman, _snowman.length);

            try {
               this.socket.receive(_snowmanx);
            } catch (SocketTimeoutException var5) {
               continue;
            } catch (IOException var6) {
               LanServerQueryManager.LOGGER.error("Couldn't ping server", var6);
               break;
            }

            String _snowmanxx = new String(_snowmanx.getData(), _snowmanx.getOffset(), _snowmanx.getLength(), StandardCharsets.UTF_8);
            LanServerQueryManager.LOGGER.debug("{}: {}", _snowmanx.getAddress(), _snowmanxx);
            this.entryList.addServer(_snowmanxx, _snowmanx.getAddress());
         }

         try {
            this.socket.leaveGroup(this.multicastAddress);
         } catch (IOException var4) {
         }

         this.socket.close();
      }
   }

   public static class LanServerEntryList {
      private final List<LanServerInfo> serverEntries = Lists.newArrayList();
      private boolean dirty;

      public LanServerEntryList() {
      }

      public synchronized boolean needsUpdate() {
         return this.dirty;
      }

      public synchronized void markClean() {
         this.dirty = false;
      }

      public synchronized List<LanServerInfo> getServers() {
         return Collections.unmodifiableList(this.serverEntries);
      }

      public synchronized void addServer(String _snowman, InetAddress _snowman) {
         String _snowmanxx = LanServerPinger.parseAnnouncementMotd(_snowman);
         String _snowmanxxx = LanServerPinger.parseAnnouncementAddressPort(_snowman);
         if (_snowmanxxx != null) {
            _snowmanxxx = _snowman.getHostAddress() + ":" + _snowmanxxx;
            boolean _snowmanxxxx = false;

            for (LanServerInfo _snowmanxxxxx : this.serverEntries) {
               if (_snowmanxxxxx.getAddressPort().equals(_snowmanxxx)) {
                  _snowmanxxxxx.updateLastTime();
                  _snowmanxxxx = true;
                  break;
               }
            }

            if (!_snowmanxxxx) {
               this.serverEntries.add(new LanServerInfo(_snowmanxx, _snowmanxxx));
               this.dirty = true;
            }
         }
      }
   }
}
