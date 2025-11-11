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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dxb {
   private static final Splitter a = Splitter.on('\u0000').limit(6);
   private static final Logger b = LogManager.getLogger();
   private final List<nd> c = Collections.synchronizedList(Lists.newArrayList());

   public dxb() {
   }

   public void a(final dwz var1, final Runnable var2) throws UnknownHostException {
      dwy _snowman = dwy.a(_snowman.b);
      final nd _snowmanx = nd.a(InetAddress.getByName(_snowman.a()), _snowman.b(), false);
      this.c.add(_snowmanx);
      _snowman.d = new of("multiplayer.status.pinging");
      _snowman.e = -1L;
      _snowman.i = null;
      _snowmanx.a(new uk() {
         private boolean e;
         private boolean f;
         private long g;

         @Override
         public void a(um var1x) {
            if (this.f) {
               _snowman.a(new of("multiplayer.status.unrequested"));
            } else {
               this.f = true;
               un _snowman = _snowman.b();
               if (_snowman.a() != null) {
                  _snowman.d = _snowman.a();
               } else {
                  _snowman.d = oe.d;
               }

               if (_snowman.c() != null) {
                  _snowman.g = new oe(_snowman.c().a());
                  _snowman.f = _snowman.c().b();
               } else {
                  _snowman.g = new of("multiplayer.status.old");
                  _snowman.f = 0;
               }

               if (_snowman.b() != null) {
                  _snowman.c = dxb.b(_snowman.b().b(), _snowman.b().a());
                  List<nr> _snowmanx = Lists.newArrayList();
                  if (ArrayUtils.isNotEmpty(_snowman.b().c())) {
                     for (GameProfile _snowmanxx : _snowman.b().c()) {
                        _snowmanx.add(new oe(_snowmanxx.getName()));
                     }

                     if (_snowman.b().c().length < _snowman.b().b()) {
                        _snowmanx.add(new of("multiplayer.status.and_more", _snowman.b().b() - _snowman.b().c().length));
                     }

                     _snowman.i = _snowmanx;
                  }
               } else {
                  _snowman.c = new of("multiplayer.status.unknown").a(k.i);
               }

               String _snowmanx = null;
               if (_snowman.d() != null) {
                  String _snowmanxx = _snowman.d();
                  if (_snowmanxx.startsWith("data:image/png;base64,")) {
                     _snowmanx = _snowmanxx.substring("data:image/png;base64,".length());
                  } else {
                     dxb.b.error("Invalid server icon (unknown format)");
                  }
               }

               if (!Objects.equals(_snowmanx, _snowman.c())) {
                  _snowman.a(_snowmanx);
                  _snowman.run();
               }

               this.g = x.b();
               _snowman.a(new up(this.g));
               this.e = true;
            }
         }

         @Override
         public void a(ul var1x) {
            long _snowman = this.g;
            long _snowmanx = x.b();
            _snowman.e = _snowmanx - _snowman;
            _snowman.a(new of("multiplayer.status.finished"));
         }

         @Override
         public void a(nr var1x) {
            if (!this.e) {
               dxb.b.error("Can't ping {}: {}", _snowman.b, _snowman.getString());
               _snowman.d = new of("multiplayer.status.cannot_connect").a(k.e);
               _snowman.c = oe.d;
               dxb.this.a(_snowman);
            }
         }

         @Override
         public nd a() {
            return _snowman;
         }
      });

      try {
         _snowmanx.a(new tv(_snowman.a(), _snowman.b(), ne.c));
         _snowmanx.a(new uq());
      } catch (Throwable var6) {
         b.error(var6);
      }
   }

   private void a(final dwz var1) {
      final dwy _snowman = dwy.a(_snowman.b);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)nd.d.a())).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel var1x) throws Exception {
            try {
               _snowman.config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (ChannelException var3) {
            }

            _snowman.pipeline().addLast(new ChannelHandler[]{new SimpleChannelInboundHandler<ByteBuf>() {
               public void channelActive(ChannelHandlerContext var1x) throws Exception {
                  super.channelActive(_snowman);
                  ByteBuf _snowman = Unpooled.buffer();

                  try {
                     _snowman.writeByte(254);
                     _snowman.writeByte(1);
                     _snowman.writeByte(250);
                     char[] _snowmanx = "MC|PingHost".toCharArray();
                     _snowman.writeShort(_snowmanx.length);

                     for (char _snowmanxx : _snowmanx) {
                        _snowman.writeChar(_snowmanxx);
                     }

                     _snowman.writeShort(7 + 2 * _snowman.a().length());
                     _snowman.writeByte(127);
                     _snowmanx = _snowman.a().toCharArray();
                     _snowman.writeShort(_snowmanx.length);

                     for (char _snowmanxx : _snowmanx) {
                        _snowman.writeChar(_snowmanxx);
                     }

                     _snowman.writeInt(_snowman.b());
                     _snowman.channel().writeAndFlush(_snowman).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                  } finally {
                     _snowman.release();
                  }
               }

               protected void a(ChannelHandlerContext var1x, ByteBuf var2x) throws Exception {
                  short _snowman = _snowman.readUnsignedByte();
                  if (_snowman == 255) {
                     String _snowmanx = new String(_snowman.readBytes(_snowman.readShort() * 2).array(), StandardCharsets.UTF_16BE);
                     String[] _snowmanxx = (String[])Iterables.toArray(dxb.a.split(_snowmanx), String.class);
                     if ("§1".equals(_snowmanxx[0])) {
                        int _snowmanxxx = afm.a(_snowmanxx[1], 0);
                        String _snowmanxxxx = _snowmanxx[2];
                        String _snowmanxxxxx = _snowmanxx[3];
                        int _snowmanxxxxxx = afm.a(_snowmanxx[4], -1);
                        int _snowmanxxxxxxx = afm.a(_snowmanxx[5], -1);
                        _snowman.f = -1;
                        _snowman.g = new oe(_snowmanxxxx);
                        _snowman.d = new oe(_snowmanxxxxx);
                        _snowman.c = dxb.b(_snowmanxxxxxx, _snowmanxxxxxxx);
                     }
                  }

                  _snowman.close();
               }

               public void exceptionCaught(ChannelHandlerContext var1x, Throwable var2x) throws Exception {
                  _snowman.close();
               }
            }});
         }
      })).channel(NioSocketChannel.class)).connect(_snowman.a(), _snowman.b());
   }

   private static nr b(int var0, int var1) {
      return new oe(Integer.toString(_snowman)).a(new oe("/").a(k.i)).c(Integer.toString(_snowman)).a(k.h);
   }

   public void a() {
      synchronized (this.c) {
         Iterator<nd> _snowman = this.c.iterator();

         while (_snowman.hasNext()) {
            nd _snowmanx = _snowman.next();
            if (_snowmanx.h()) {
               _snowmanx.a();
            } else {
               _snowman.remove();
               _snowmanx.m();
            }
         }
      }
   }

   public void b() {
      synchronized (this.c) {
         Iterator<nd> _snowman = this.c.iterator();

         while (_snowman.hasNext()) {
            nd _snowmanx = _snowman.next();
            if (_snowmanx.h()) {
               _snowman.remove();
               _snowmanx.a(new of("multiplayer.status.cancelled"));
            }
         }
      }
   }
}
