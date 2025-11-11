import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class nd extends SimpleChannelInboundHandler<oj<?>> {
   private static final Logger g = LogManager.getLogger();
   public static final Marker a = MarkerManager.getMarker("NETWORK");
   public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
   public static final AttributeKey<ne> c = AttributeKey.valueOf("protocol");
   public static final afi<NioEventLoopGroup> d = new afi<>(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build())
   );
   public static final afi<EpollEventLoopGroup> e = new afi<>(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build())
   );
   public static final afi<DefaultEventLoopGroup> f = new afi<>(
      () -> new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build())
   );
   private final ok h;
   private final Queue<nd.a> i = Queues.newConcurrentLinkedQueue();
   private Channel j;
   private SocketAddress k;
   private ni l;
   private nr m;
   private boolean n;
   private boolean o;
   private int p;
   private int q;
   private float r;
   private float s;
   private int t;
   private boolean u;

   public nd(ok var1) {
      this.h = _snowman;
   }

   public void channelActive(ChannelHandlerContext var1) throws Exception {
      super.channelActive(_snowman);
      this.j = _snowman.channel();
      this.k = this.j.remoteAddress();

      try {
         this.a(ne.a);
      } catch (Throwable var3) {
         g.fatal(var3);
      }
   }

   public void a(ne var1) {
      this.j.attr(c).set(_snowman);
      this.j.config().setAutoRead(true);
      g.debug("Enabled auto read");
   }

   public void channelInactive(ChannelHandlerContext var1) throws Exception {
      this.a(new of("disconnect.endOfStream"));
   }

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) {
      if (_snowman instanceof nk) {
         g.debug("Skipping packet due to errors", _snowman.getCause());
      } else {
         boolean _snowman = !this.u;
         this.u = true;
         if (this.j.isOpen()) {
            if (_snowman instanceof TimeoutException) {
               g.debug("Timeout", _snowman);
               this.a(new of("disconnect.timeout"));
            } else {
               nr _snowmanx = new of("disconnect.genericReason", "Internal Exception: " + _snowman);
               if (_snowman) {
                  g.debug("Failed to sent packet", _snowman);
                  this.a(new pm(_snowmanx), var2x -> this.a(_snowman));
                  this.l();
               } else {
                  g.debug("Double fault", _snowman);
                  this.a(_snowmanx);
               }
            }
         }
      }
   }

   protected void a(ChannelHandlerContext var1, oj<?> var2) throws Exception {
      if (this.j.isOpen()) {
         try {
            a(_snowman, this.l);
         } catch (vu var4) {
         }

         this.p++;
      }
   }

   private static <T extends ni> void a(oj<T> var0, ni var1) {
      _snowman.a((T)_snowman);
   }

   public void a(ni var1) {
      Validate.notNull(_snowman, "packetListener", new Object[0]);
      this.l = _snowman;
   }

   public void a(oj<?> var1) {
      this.a(_snowman, null);
   }

   public void a(oj<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      if (this.h()) {
         this.p();
         this.b(_snowman, _snowman);
      } else {
         this.i.add(new nd.a(_snowman, _snowman));
      }
   }

   private void b(oj<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      ne _snowman = ne.a(_snowman);
      ne _snowmanx = (ne)this.j.attr(c).get();
      this.q++;
      if (_snowmanx != _snowman) {
         g.debug("Disabled auto read");
         this.j.config().setAutoRead(false);
      }

      if (this.j.eventLoop().inEventLoop()) {
         if (_snowman != _snowmanx) {
            this.a(_snowman);
         }

         ChannelFuture _snowmanxx = this.j.writeAndFlush(_snowman);
         if (_snowman != null) {
            _snowmanxx.addListener(_snowman);
         }

         _snowmanxx.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
      } else {
         this.j.eventLoop().execute(() -> {
            if (_snowman != _snowman) {
               this.a(_snowman);
            }

            ChannelFuture _snowmanxx = this.j.writeAndFlush(_snowman);
            if (_snowman != null) {
               _snowmanxx.addListener(_snowman);
            }

            _snowmanxx.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
         });
      }
   }

   private void p() {
      if (this.j != null && this.j.isOpen()) {
         synchronized (this.i) {
            nd.a _snowman;
            while ((_snowman = this.i.poll()) != null) {
               this.b(_snowman.a, _snowman.b);
            }
         }
      }
   }

   public void a() {
      this.p();
      if (this.l instanceof aba) {
         ((aba)this.l).b();
      }

      if (this.l instanceof aay) {
         ((aay)this.l).b();
      }

      if (this.j != null) {
         this.j.flush();
      }

      if (this.t++ % 20 == 0) {
         this.b();
      }
   }

   protected void b() {
      this.s = afm.g(0.75F, (float)this.q, this.s);
      this.r = afm.g(0.75F, (float)this.p, this.r);
      this.q = 0;
      this.p = 0;
   }

   public SocketAddress c() {
      return this.k;
   }

   public void a(nr var1) {
      if (this.j.isOpen()) {
         this.j.close().awaitUninterruptibly();
         this.m = _snowman;
      }
   }

   public boolean d() {
      return this.j instanceof LocalChannel || this.j instanceof LocalServerChannel;
   }

   public static nd a(InetAddress var0, int var1, boolean var2) {
      final nd _snowman = new nd(ok.b);
      Class<? extends SocketChannel> _snowmanx;
      afi<? extends EventLoopGroup> _snowmanxx;
      if (Epoll.isAvailable() && _snowman) {
         _snowmanx = EpollSocketChannel.class;
         _snowmanxx = e;
      } else {
         _snowmanx = NioSocketChannel.class;
         _snowmanxx = d;
      }

      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(_snowmanxx.a()))
               .handler(
                  new ChannelInitializer<Channel>() {
                     protected void initChannel(Channel var1) throws Exception {
                        try {
                           _snowman.config().setOption(ChannelOption.TCP_NODELAY, true);
                        } catch (ChannelException var3x) {
                        }

                        _snowman.pipeline()
                           .addLast("timeout", new ReadTimeoutHandler(30))
                           .addLast("splitter", new nl())
                           .addLast("decoder", new ng(ok.b))
                           .addLast("prepender", new nm())
                           .addLast("encoder", new nh(ok.a))
                           .addLast("packet_handler", _snowman);
                     }
                  }
               ))
            .channel(_snowmanx))
         .connect(_snowman, _snowman)
         .syncUninterruptibly();
      return _snowman;
   }

   public static nd a(SocketAddress var0) {
      final nd _snowman = new nd(ok.b);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)f.a())).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel var1x) throws Exception {
            _snowman.pipeline().addLast("packet_handler", _snowman);
         }
      })).channel(LocalChannel.class)).connect(_snowman).syncUninterruptibly();
      return _snowman;
   }

   public void a(Cipher var1, Cipher var2) {
      this.n = true;
      this.j.pipeline().addBefore("splitter", "decrypt", new mz(_snowman));
      this.j.pipeline().addBefore("prepender", "encrypt", new na(_snowman));
   }

   public boolean g() {
      return this.n;
   }

   public boolean h() {
      return this.j != null && this.j.isOpen();
   }

   public boolean i() {
      return this.j == null;
   }

   public ni j() {
      return this.l;
   }

   @Nullable
   public nr k() {
      return this.m;
   }

   public void l() {
      this.j.config().setAutoRead(false);
   }

   public void a(int var1) {
      if (_snowman >= 0) {
         if (this.j.pipeline().get("decompress") instanceof nb) {
            ((nb)this.j.pipeline().get("decompress")).a(_snowman);
         } else {
            this.j.pipeline().addBefore("decoder", "decompress", new nb(_snowman));
         }

         if (this.j.pipeline().get("compress") instanceof nc) {
            ((nc)this.j.pipeline().get("compress")).a(_snowman);
         } else {
            this.j.pipeline().addBefore("encoder", "compress", new nc(_snowman));
         }
      } else {
         if (this.j.pipeline().get("decompress") instanceof nb) {
            this.j.pipeline().remove("decompress");
         }

         if (this.j.pipeline().get("compress") instanceof nc) {
            this.j.pipeline().remove("compress");
         }
      }
   }

   public void m() {
      if (this.j != null && !this.j.isOpen()) {
         if (this.o) {
            g.warn("handleDisconnection() called twice");
         } else {
            this.o = true;
            if (this.k() != null) {
               this.j().a(this.k());
            } else if (this.j() != null) {
               this.j().a(new of("multiplayer.disconnect.generic"));
            }
         }
      }
   }

   public float n() {
      return this.r;
   }

   public float o() {
      return this.s;
   }

   static class a {
      private final oj<?> a;
      @Nullable
      private final GenericFutureListener<? extends Future<? super Void>> b;

      public a(oj<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
