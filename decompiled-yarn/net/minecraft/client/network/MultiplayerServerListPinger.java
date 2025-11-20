package net.minecraft.client.network;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.ServerAddress;
import net.minecraft.network.listener.ClientQueryPacketListener;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryPingC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.query.QueryPongS2CPacket;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiplayerServerListPinger {
   private static final Splitter ZERO_SPLITTER = Splitter.on('\u0000').limit(6);
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<ClientConnection> clientConnections = Collections.synchronizedList(Lists.newArrayList());

   public MultiplayerServerListPinger() {
   }

   public void add(ServerInfo entry, Runnable _snowman) throws UnknownHostException {
      ServerAddress _snowmanx = ServerAddress.parse(entry.address);
      final ClientConnection _snowmanxx = ClientConnection.connect(InetAddress.getByName(_snowmanx.getAddress()), _snowmanx.getPort(), false);
      this.clientConnections.add(_snowmanxx);
      entry.label = new TranslatableText("multiplayer.status.pinging");
      entry.ping = -1L;
      entry.playerListSummary = null;
      _snowmanxx.setPacketListener(new ClientQueryPacketListener() {
         private boolean sentQuery;
         private boolean received;
         private long startTime;

         @Override
         public void onResponse(QueryResponseS2CPacket packet) {
            if (this.received) {
               _snowman.disconnect(new TranslatableText("multiplayer.status.unrequested"));
            } else {
               this.received = true;
               ServerMetadata _snowman = packet.getServerMetadata();
               if (_snowman.getDescription() != null) {
                  entry.label = _snowman.getDescription();
               } else {
                  entry.label = LiteralText.EMPTY;
               }

               if (_snowman.getVersion() != null) {
                  entry.version = new LiteralText(_snowman.getVersion().getGameVersion());
                  entry.protocolVersion = _snowman.getVersion().getProtocolVersion();
               } else {
                  entry.version = new TranslatableText("multiplayer.status.old");
                  entry.protocolVersion = 0;
               }

               if (_snowman.getPlayers() != null) {
                  entry.playerCountLabel = MultiplayerServerListPinger.method_27647(_snowman.getPlayers().getOnlinePlayerCount(), _snowman.getPlayers().getPlayerLimit());
                  List<Text> _snowmanx = Lists.newArrayList();
                  if (ArrayUtils.isNotEmpty(_snowman.getPlayers().getSample())) {
                     for (GameProfile _snowmanxx : _snowman.getPlayers().getSample()) {
                        _snowmanx.add(new LiteralText(_snowmanxx.getName()));
                     }

                     if (_snowman.getPlayers().getSample().length < _snowman.getPlayers().getOnlinePlayerCount()) {
                        _snowmanx.add(new TranslatableText("multiplayer.status.and_more", _snowman.getPlayers().getOnlinePlayerCount() - _snowman.getPlayers().getSample().length));
                     }

                     entry.playerListSummary = _snowmanx;
                  }
               } else {
                  entry.playerCountLabel = new TranslatableText("multiplayer.status.unknown").formatted(Formatting.DARK_GRAY);
               }

               String _snowmanx = null;
               if (_snowman.getFavicon() != null) {
                  String _snowmanxx = _snowman.getFavicon();
                  if (_snowmanxx.startsWith("data:image/png;base64,")) {
                     _snowmanx = _snowmanxx.substring("data:image/png;base64,".length());
                  } else {
                     MultiplayerServerListPinger.LOGGER.error("Invalid server icon (unknown format)");
                  }
               }

               if (!Objects.equals(_snowmanx, entry.getIcon())) {
                  entry.setIcon(_snowmanx);
                  _snowman.run();
               }

               this.startTime = Util.getMeasuringTimeMs();
               _snowman.send(new QueryPingC2SPacket(this.startTime));
               this.sentQuery = true;
            }
         }

         @Override
         public void onPong(QueryPongS2CPacket packet) {
            long _snowman = this.startTime;
            long _snowmanx = Util.getMeasuringTimeMs();
            entry.ping = _snowmanx - _snowman;
            _snowman.disconnect(new TranslatableText("multiplayer.status.finished"));
         }

         @Override
         public void onDisconnected(Text reason) {
            if (!this.sentQuery) {
               MultiplayerServerListPinger.LOGGER.error("Can't ping {}: {}", entry.address, reason.getString());
               entry.label = new TranslatableText("multiplayer.status.cannot_connect").formatted(Formatting.DARK_RED);
               entry.playerCountLabel = LiteralText.EMPTY;
               MultiplayerServerListPinger.this.ping(entry);
            }
         }

         @Override
         public ClientConnection getConnection() {
            return _snowman;
         }
      });

      try {
         _snowmanxx.send(new HandshakeC2SPacket(_snowmanx.getAddress(), _snowmanx.getPort(), NetworkState.STATUS));
         _snowmanxx.send(new QueryRequestC2SPacket());
      } catch (Throwable var6) {
         LOGGER.error(var6);
      }
   }

   private void ping(ServerInfo _snowman) {
      final ServerAddress _snowmanx = ServerAddress.parse(_snowman.address);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)ClientConnection.CLIENT_IO_GROUP.get()))
               .handler(new ChannelInitializer<Channel>() {
                  protected void initChannel(Channel _snowman) throws Exception {
                     try {
                        _snowman.config().setOption(ChannelOption.TCP_NODELAY, true);
                     } catch (ChannelException var3) {
                     }

                     _snowman.pipeline().addLast(new ChannelHandler[]{new SimpleChannelInboundHandler<ByteBuf>() {
                        public void channelActive(ChannelHandlerContext _snowman) throws Exception {
                           super.channelActive(_snowman);
                           ByteBuf _snowmanx = Unpooled.buffer();

                           try {
                              _snowmanx.writeByte(254);
                              _snowmanx.writeByte(1);
                              _snowmanx.writeByte(250);
                              char[] _snowmanxx = "MC|PingHost".toCharArray();
                              _snowmanx.writeShort(_snowmanxx.length);

                              for (char _snowmanxxx : _snowmanxx) {
                                 _snowmanx.writeChar(_snowmanxxx);
                              }

                              _snowmanx.writeShort(7 + 2 * _snowman.getAddress().length());
                              _snowmanx.writeByte(127);
                              _snowmanxx = _snowman.getAddress().toCharArray();
                              _snowmanx.writeShort(_snowmanxx.length);

                              for (char _snowmanxxx : _snowmanxx) {
                                 _snowmanx.writeChar(_snowmanxxx);
                              }

                              _snowmanx.writeInt(_snowman.getPort());
                              _snowman.channel().writeAndFlush(_snowmanx).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                           } finally {
                              _snowmanx.release();
                           }
                        }

                        protected void channelRead0(ChannelHandlerContext _snowman, ByteBuf _snowman) throws Exception {
                           short _snowmanxx = _snowman.readUnsignedByte();
                           if (_snowmanxx == 255) {
                              String _snowmanxxx = new String(_snowman.readBytes(_snowman.readShort() * 2).array(), StandardCharsets.UTF_16BE);
                              String[] _snowmanxxxx = (String[])Iterables.toArray(MultiplayerServerListPinger.ZERO_SPLITTER.split(_snowmanxxx), String.class);
                              if ("§1".equals(_snowmanxxxx[0])) {
                                 int _snowmanxxxxx = MathHelper.parseInt(_snowmanxxxx[1], 0);
                                 String _snowmanxxxxxx = _snowmanxxxx[2];
                                 String _snowmanxxxxxxx = _snowmanxxxx[3];
                                 int _snowmanxxxxxxxx = MathHelper.parseInt(_snowmanxxxx[4], -1);
                                 int _snowmanxxxxxxxxx = MathHelper.parseInt(_snowmanxxxx[5], -1);
                                 _snowman.protocolVersion = -1;
                                 _snowman.version = new LiteralText(_snowmanxxxxxx);
                                 _snowman.label = new LiteralText(_snowmanxxxxxxx);
                                 _snowman.playerCountLabel = MultiplayerServerListPinger.method_27647(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
                              }
                           }

                           _snowman.close();
                        }

                        public void exceptionCaught(ChannelHandlerContext _snowman, Throwable _snowman) throws Exception {
                           _snowman.close();
                        }
                     }});
                  }
               }))
            .channel(NioSocketChannel.class))
         .connect(_snowmanx.getAddress(), _snowmanx.getPort());
   }

   private static Text method_27647(int _snowman, int _snowman) {
      return new LiteralText(Integer.toString(_snowman))
         .append(new LiteralText("/").formatted(Formatting.DARK_GRAY))
         .append(Integer.toString(_snowman))
         .formatted(Formatting.GRAY);
   }

   public void tick() {
      synchronized (this.clientConnections) {
         Iterator<ClientConnection> _snowman = this.clientConnections.iterator();

         while (_snowman.hasNext()) {
            ClientConnection _snowmanx = _snowman.next();
            if (_snowmanx.isOpen()) {
               _snowmanx.tick();
            } else {
               _snowman.remove();
               _snowmanx.handleDisconnection();
            }
         }
      }
   }

   public void cancel() {
      synchronized (this.clientConnections) {
         Iterator<ClientConnection> _snowman = this.clientConnections.iterator();

         while (_snowman.hasNext()) {
            ClientConnection _snowmanx = _snowman.next();
            if (_snowmanx.isOpen()) {
               _snowman.remove();
               _snowmanx.disconnect(new TranslatableText("multiplayer.status.cancelled"));
            }
         }
      }
   }
}
