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
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aax {
   private static final Logger d = LogManager.getLogger();
   public static final afi<NioEventLoopGroup> a = new afi<>(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build())
   );
   public static final afi<EpollEventLoopGroup> b = new afi<>(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build())
   );
   private final MinecraftServer e;
   public volatile boolean c;
   private final List<ChannelFuture> f = Collections.synchronizedList(Lists.newArrayList());
   private final List<nd> g = Collections.synchronizedList(Lists.newArrayList());

   public aax(MinecraftServer var1) {
      this.e = _snowman;
      this.c = true;
   }

   public void a(@Nullable InetAddress var1, int var2) throws IOException {
      synchronized (this.f) {
         Class<? extends ServerSocketChannel> _snowman;
         afi<? extends EventLoopGroup> _snowmanx;
         if (Epoll.isAvailable() && this.e.l()) {
            _snowman = EpollServerSocketChannel.class;
            _snowmanx = b;
            d.info("Using epoll channel type");
         } else {
            _snowman = NioServerSocketChannel.class;
            _snowmanx = a;
            d.info("Using default channel type");
         }

         this.f
            .add(
               ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(_snowman))
                     .childHandler(
                        new ChannelInitializer<Channel>() {
                           protected void initChannel(Channel var1) throws Exception {
                              try {
                                 _snowman.config().setOption(ChannelOption.TCP_NODELAY, true);
                              } catch (ChannelException var4) {
                              }

                              _snowman.pipeline()
                                 .addLast("timeout", new ReadTimeoutHandler(30))
                                 .addLast("legacy_query", new aav(aax.this))
                                 .addLast("splitter", new nl())
                                 .addLast("decoder", new ng(ok.a))
                                 .addLast("prepender", new nm())
                                 .addLast("encoder", new nh(ok.b));
                              int _snowman = aax.this.e.k();
                              nd _snowmanx = (nd)(_snowman > 0 ? new nj(_snowman) : new nd(ok.a));
                              aax.this.g.add(_snowmanx);
                              _snowman.pipeline().addLast("packet_handler", _snowmanx);
                              _snowmanx.a(new aaz(aax.this.e, _snowmanx));
                           }
                        }
                     )
                     .group(_snowmanx.a())
                     .localAddress(_snowman, _snowman))
                  .bind()
                  .syncUninterruptibly()
            );
      }
   }

   public SocketAddress a() {
      ChannelFuture _snowman;
      synchronized (this.f) {
         _snowman = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel var1) throws Exception {
               nd _snowman = new nd(ok.a);
               _snowman.a(new aaw(aax.this.e, _snowman));
               aax.this.g.add(_snowman);
               _snowman.pipeline().addLast("packet_handler", _snowman);
            }
         }).group((EventLoopGroup)a.a()).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
         this.f.add(_snowman);
      }

      return _snowman.channel().localAddress();
   }

   public void b() {
      this.c = false;

      for (ChannelFuture _snowman : this.f) {
         try {
            _snowman.channel().close().sync();
         } catch (InterruptedException var4) {
            d.error("Interrupted whilst closing channel");
         }
      }
   }

   public void c() {
      synchronized (this.g) {
         Iterator<nd> _snowman = this.g.iterator();

         while (_snowman.hasNext()) {
            nd _snowmanx = _snowman.next();
            if (!_snowmanx.i()) {
               if (_snowmanx.h()) {
                  try {
                     _snowmanx.a();
                  } catch (Exception var7) {
                     if (_snowmanx.d()) {
                        throw new u(l.a(var7, "Ticking memory connection"));
                     }

                     d.warn("Failed to handle packet for {}", _snowmanx.c(), var7);
                     nr _snowmanxx = new oe("Internal server error");
                     _snowmanx.a(new pm(_snowmanxx), var2x -> _snowman.a(_snowman));
                     _snowmanx.l();
                  }
               } else {
                  _snowman.remove();
                  _snowmanx.m();
               }
            }
         }
      }
   }

   public MinecraftServer d() {
      return this.e;
   }
}
