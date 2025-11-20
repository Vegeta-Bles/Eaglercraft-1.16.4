package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.DecoderHandler;
import net.minecraft.network.LegacyQueryHandler;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.RateLimitedConnection;
import net.minecraft.network.SizePrepender;
import net.minecraft.network.SplitterHandler;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.server.network.IntegratedServerHandshakeNetworkHandler;
import net.minecraft.server.network.ServerHandshakeNetworkHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Lazy;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerNetworkIo {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Lazy<NioEventLoopGroup> DEFAULT_CHANNEL = new Lazy<>(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build())
   );
   public static final Lazy<EpollEventLoopGroup> EPOLL_CHANNEL = new Lazy<>(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build())
   );
   private final MinecraftServer server;
   public volatile boolean active;
   private final List<ChannelFuture> channels = Collections.synchronizedList(Lists.newArrayList());
   private final List<ClientConnection> connections = Collections.synchronizedList(Lists.newArrayList());

   public ServerNetworkIo(MinecraftServer server) {
      this.server = server;
      this.active = true;
   }

   public void bind(@Nullable InetAddress address, int port) throws IOException {
      synchronized (this.channels) {
         Class<? extends ServerSocketChannel> _snowman;
         Lazy<? extends EventLoopGroup> _snowmanx;
         if (Epoll.isAvailable() && this.server.isUsingNativeTransport()) {
            _snowman = EpollServerSocketChannel.class;
            _snowmanx = EPOLL_CHANNEL;
            LOGGER.info("Using epoll channel type");
         } else {
            _snowman = NioServerSocketChannel.class;
            _snowmanx = DEFAULT_CHANNEL;
            LOGGER.info("Using default channel type");
         }

         this.channels
            .add(
               ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(_snowman))
                     .childHandler(
                        new ChannelInitializer<Channel>() {
                           protected void initChannel(Channel _snowman) throws Exception {
                              try {
                                 _snowman.config().setOption(ChannelOption.TCP_NODELAY, true);
                              } catch (ChannelException var4) {
                              }

                              _snowman.pipeline()
                                 .addLast("timeout", new ReadTimeoutHandler(30))
                                 .addLast("legacy_query", new LegacyQueryHandler(ServerNetworkIo.this))
                                 .addLast("splitter", new SplitterHandler())
                                 .addLast("decoder", new DecoderHandler(NetworkSide.SERVERBOUND))
                                 .addLast("prepender", new SizePrepender())
                                 .addLast("encoder", new PacketEncoder(NetworkSide.CLIENTBOUND));
                              int _snowmanx = ServerNetworkIo.this.server.getRateLimit();
                              ClientConnection _snowmanxx = (ClientConnection)(_snowmanx > 0 ? new RateLimitedConnection(_snowmanx) : new ClientConnection(NetworkSide.SERVERBOUND));
                              ServerNetworkIo.this.connections.add(_snowmanxx);
                              _snowman.pipeline().addLast("packet_handler", _snowmanxx);
                              _snowmanxx.setPacketListener(new ServerHandshakeNetworkHandler(ServerNetworkIo.this.server, _snowmanxx));
                           }
                        }
                     )
                     .group(_snowmanx.get())
                     .localAddress(address, port))
                  .bind()
                  .syncUninterruptibly()
            );
      }
   }

   public SocketAddress bindLocal() {
      ChannelFuture _snowman;
      synchronized (this.channels) {
         _snowman = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel _snowman) throws Exception {
               ClientConnection _snowmanx = new ClientConnection(NetworkSide.SERVERBOUND);
               _snowmanx.setPacketListener(new IntegratedServerHandshakeNetworkHandler(ServerNetworkIo.this.server, _snowmanx));
               ServerNetworkIo.this.connections.add(_snowmanx);
               _snowman.pipeline().addLast("packet_handler", _snowmanx);
            }
         }).group((EventLoopGroup)DEFAULT_CHANNEL.get()).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
         this.channels.add(_snowman);
      }

      return _snowman.channel().localAddress();
   }

   public void stop() {
      this.active = false;

      for (ChannelFuture _snowman : this.channels) {
         try {
            _snowman.channel().close().sync();
         } catch (InterruptedException var4) {
            LOGGER.error("Interrupted whilst closing channel");
         }
      }
   }

   public void tick() {
      synchronized (this.connections) {
         Iterator<ClientConnection> _snowman = this.connections.iterator();

         while (_snowman.hasNext()) {
            ClientConnection _snowmanx = _snowman.next();
            if (!_snowmanx.hasChannel()) {
               if (_snowmanx.isOpen()) {
                  try {
                     _snowmanx.tick();
                  } catch (Exception var7) {
                     if (_snowmanx.isLocal()) {
                        throw new CrashException(CrashReport.create(var7, "Ticking memory connection"));
                     }

                     LOGGER.warn("Failed to handle packet for {}", _snowmanx.getAddress(), var7);
                     Text _snowmanxx = new LiteralText("Internal server error");
                     _snowmanx.send(new DisconnectS2CPacket(_snowmanxx), _snowmanxxx -> _snowman.disconnect(_snowman));
                     _snowmanx.disableAutoRead();
                  }
               } else {
                  _snowman.remove();
                  _snowmanx.handleDisconnection();
               }
            }
         }
      }
   }

   public MinecraftServer getServer() {
      return this.server;
   }
}
