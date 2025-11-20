package net.minecraft.server.rcon;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryResponseHandler extends RconBase {
   private static final Logger field_23963 = LogManager.getLogger();
   private long lastQueryTime;
   private final int queryPort;
   private final int port;
   private final int maxPlayerCount;
   private final String motd;
   private final String levelName;
   private DatagramSocket socket;
   private final byte[] packetBuffer = new byte[1460];
   private String ip;
   private String hostname;
   private final Map<SocketAddress, QueryResponseHandler.Query> queries;
   private final DataStreamHelper data;
   private long lastResponseTime;
   private final DedicatedServer field_23964;

   private QueryResponseHandler(DedicatedServer server, int queryPort) {
      super("Query Listener");
      this.field_23964 = server;
      this.queryPort = queryPort;
      this.hostname = server.getHostname();
      this.port = server.getPort();
      this.motd = server.getMotd();
      this.maxPlayerCount = server.getMaxPlayerCount();
      this.levelName = server.getLevelName();
      this.lastResponseTime = 0L;
      this.ip = "0.0.0.0";
      if (!this.hostname.isEmpty() && !this.ip.equals(this.hostname)) {
         this.ip = this.hostname;
      } else {
         this.hostname = "0.0.0.0";

         try {
            InetAddress _snowman = InetAddress.getLocalHost();
            this.ip = _snowman.getHostAddress();
         } catch (UnknownHostException var4) {
            field_23963.warn("Unable to determine local host IP, please set server-ip in server.properties", var4);
         }
      }

      this.data = new DataStreamHelper(1460);
      this.queries = Maps.newHashMap();
   }

   @Nullable
   public static QueryResponseHandler create(DedicatedServer server) {
      int _snowman = server.getProperties().queryPort;
      if (0 < _snowman && 65535 >= _snowman) {
         QueryResponseHandler _snowmanx = new QueryResponseHandler(server, _snowman);
         return !_snowmanx.start() ? null : _snowmanx;
      } else {
         field_23963.warn("Invalid query port {} found in server.properties (queries disabled)", _snowman);
         return null;
      }
   }

   private void reply(byte[] buf, DatagramPacket _snowman) throws IOException {
      this.socket.send(new DatagramPacket(buf, buf.length, _snowman.getSocketAddress()));
   }

   private boolean handle(DatagramPacket packet) throws IOException {
      byte[] _snowman = packet.getData();
      int _snowmanx = packet.getLength();
      SocketAddress _snowmanxx = packet.getSocketAddress();
      field_23963.debug("Packet len {} [{}]", _snowmanx, _snowmanxx);
      if (3 <= _snowmanx && -2 == _snowman[0] && -3 == _snowman[1]) {
         field_23963.debug("Packet '{}' [{}]", BufferHelper.toHex(_snowman[2]), _snowmanxx);
         switch (_snowman[2]) {
            case 0:
               if (!this.isValidQuery(packet)) {
                  field_23963.debug("Invalid challenge [{}]", _snowmanxx);
                  return false;
               } else if (15 == _snowmanx) {
                  this.reply(this.createRulesReply(packet), packet);
                  field_23963.debug("Rules [{}]", _snowmanxx);
               } else {
                  DataStreamHelper _snowmanxxx = new DataStreamHelper(1460);
                  _snowmanxxx.write(0);
                  _snowmanxxx.write(this.getMessageBytes(packet.getSocketAddress()));
                  _snowmanxxx.writeBytes(this.motd);
                  _snowmanxxx.writeBytes("SMP");
                  _snowmanxxx.writeBytes(this.levelName);
                  _snowmanxxx.writeBytes(Integer.toString(this.field_23964.getCurrentPlayerCount()));
                  _snowmanxxx.writeBytes(Integer.toString(this.maxPlayerCount));
                  _snowmanxxx.writeShort((short)this.port);
                  _snowmanxxx.writeBytes(this.ip);
                  this.reply(_snowmanxxx.bytes(), packet);
                  field_23963.debug("Status [{}]", _snowmanxx);
               }
            default:
               return true;
            case 9:
               this.createQuery(packet);
               field_23963.debug("Challenge [{}]", _snowmanxx);
               return true;
         }
      } else {
         field_23963.debug("Invalid packet [{}]", _snowmanxx);
         return false;
      }
   }

   private byte[] createRulesReply(DatagramPacket packet) throws IOException {
      long _snowman = Util.getMeasuringTimeMs();
      if (_snowman < this.lastResponseTime + 5000L) {
         byte[] _snowmanx = this.data.bytes();
         byte[] _snowmanxx = this.getMessageBytes(packet.getSocketAddress());
         _snowmanx[1] = _snowmanxx[0];
         _snowmanx[2] = _snowmanxx[1];
         _snowmanx[3] = _snowmanxx[2];
         _snowmanx[4] = _snowmanxx[3];
         return _snowmanx;
      } else {
         this.lastResponseTime = _snowman;
         this.data.reset();
         this.data.write(0);
         this.data.write(this.getMessageBytes(packet.getSocketAddress()));
         this.data.writeBytes("splitnum");
         this.data.write(128);
         this.data.write(0);
         this.data.writeBytes("hostname");
         this.data.writeBytes(this.motd);
         this.data.writeBytes("gametype");
         this.data.writeBytes("SMP");
         this.data.writeBytes("game_id");
         this.data.writeBytes("MINECRAFT");
         this.data.writeBytes("version");
         this.data.writeBytes(this.field_23964.getVersion());
         this.data.writeBytes("plugins");
         this.data.writeBytes(this.field_23964.getPlugins());
         this.data.writeBytes("map");
         this.data.writeBytes(this.levelName);
         this.data.writeBytes("numplayers");
         this.data.writeBytes("" + this.field_23964.getCurrentPlayerCount());
         this.data.writeBytes("maxplayers");
         this.data.writeBytes("" + this.maxPlayerCount);
         this.data.writeBytes("hostport");
         this.data.writeBytes("" + this.port);
         this.data.writeBytes("hostip");
         this.data.writeBytes(this.ip);
         this.data.write(0);
         this.data.write(1);
         this.data.writeBytes("player_");
         this.data.write(0);
         String[] _snowmanx = this.field_23964.getPlayerNames();

         for (String _snowmanxx : _snowmanx) {
            this.data.writeBytes(_snowmanxx);
         }

         this.data.write(0);
         return this.data.bytes();
      }
   }

   private byte[] getMessageBytes(SocketAddress _snowman) {
      return this.queries.get(_snowman).getMessageBytes();
   }

   private Boolean isValidQuery(DatagramPacket _snowman) {
      SocketAddress _snowmanx = _snowman.getSocketAddress();
      if (!this.queries.containsKey(_snowmanx)) {
         return false;
      } else {
         byte[] _snowmanxx = _snowman.getData();
         return this.queries.get(_snowmanx).getId() == BufferHelper.getIntBE(_snowmanxx, 7, _snowman.getLength());
      }
   }

   private void createQuery(DatagramPacket _snowman) throws IOException {
      QueryResponseHandler.Query _snowmanx = new QueryResponseHandler.Query(_snowman);
      this.queries.put(_snowman.getSocketAddress(), _snowmanx);
      this.reply(_snowmanx.getReplyBuf(), _snowman);
   }

   private void cleanUp() {
      if (this.running) {
         long _snowman = Util.getMeasuringTimeMs();
         if (_snowman >= this.lastQueryTime + 30000L) {
            this.lastQueryTime = _snowman;
            this.queries.values().removeIf(_snowmanx -> _snowmanx.startedBefore(_snowman));
         }
      }
   }

   @Override
   public void run() {
      field_23963.info("Query running on {}:{}", this.hostname, this.queryPort);
      this.lastQueryTime = Util.getMeasuringTimeMs();
      DatagramPacket _snowman = new DatagramPacket(this.packetBuffer, this.packetBuffer.length);

      try {
         while (this.running) {
            try {
               this.socket.receive(_snowman);
               this.cleanUp();
               this.handle(_snowman);
            } catch (SocketTimeoutException var8) {
               this.cleanUp();
            } catch (PortUnreachableException var9) {
            } catch (IOException var10) {
               this.handleIoException(var10);
            }
         }
      } finally {
         field_23963.debug("closeSocket: {}:{}", this.hostname, this.queryPort);
         this.socket.close();
      }
   }

   @Override
   public boolean start() {
      if (this.running) {
         return true;
      } else {
         return !this.initialize() ? false : super.start();
      }
   }

   private void handleIoException(Exception e) {
      if (this.running) {
         field_23963.warn("Unexpected exception", e);
         if (!this.initialize()) {
            field_23963.error("Failed to recover from exception, shutting down!");
            this.running = false;
         }
      }
   }

   private boolean initialize() {
      try {
         this.socket = new DatagramSocket(this.queryPort, InetAddress.getByName(this.hostname));
         this.socket.setSoTimeout(500);
         return true;
      } catch (Exception var2) {
         field_23963.warn("Unable to initialise query system on {}:{}", this.hostname, this.queryPort, var2);
         return false;
      }
   }

   static class Query {
      private final long startTime = new Date().getTime();
      private final int id;
      private final byte[] messageBytes;
      private final byte[] replyBuf;
      private final String message;

      public Query(DatagramPacket _snowman) {
         byte[] _snowmanx = _snowman.getData();
         this.messageBytes = new byte[4];
         this.messageBytes[0] = _snowmanx[3];
         this.messageBytes[1] = _snowmanx[4];
         this.messageBytes[2] = _snowmanx[5];
         this.messageBytes[3] = _snowmanx[6];
         this.message = new String(this.messageBytes, StandardCharsets.UTF_8);
         this.id = new Random().nextInt(16777216);
         this.replyBuf = String.format("\t%s%d\u0000", this.message, this.id).getBytes(StandardCharsets.UTF_8);
      }

      public Boolean startedBefore(long lastQueryTime) {
         return this.startTime < lastQueryTime;
      }

      public int getId() {
         return this.id;
      }

      public byte[] getReplyBuf() {
         return this.replyBuf;
      }

      public byte[] getMessageBytes() {
         return this.messageBytes;
      }
   }
}
