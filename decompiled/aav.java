import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aav extends ChannelInboundHandlerAdapter {
   private static final Logger a = LogManager.getLogger();
   private final aax b;

   public aav(aax var1) {
      this.b = _snowman;
   }

   public void channelRead(ChannelHandlerContext var1, Object var2) throws Exception {
      ByteBuf _snowman = (ByteBuf)_snowman;
      _snowman.markReaderIndex();
      boolean _snowmanx = true;

      try {
         try {
            if (_snowman.readUnsignedByte() != 254) {
               return;
            }

            InetSocketAddress _snowmanxx = (InetSocketAddress)_snowman.channel().remoteAddress();
            MinecraftServer _snowmanxxx = this.b.d();
            int _snowmanxxxx = _snowman.readableBytes();
            switch (_snowmanxxxx) {
               case 0:
                  a.debug("Ping: (<1.3.x) from {}:{}", _snowmanxx.getAddress(), _snowmanxx.getPort());
                  String _snowmanxxxxx = String.format("%s§%d§%d", _snowmanxxx.ab(), _snowmanxxx.I(), _snowmanxxx.J());
                  this.a(_snowman, this.a(_snowmanxxxxx));
                  break;
               case 1:
                  if (_snowman.readUnsignedByte() != 1) {
                     return;
                  }

                  a.debug("Ping: (1.4-1.5.x) from {}:{}", _snowmanxx.getAddress(), _snowmanxx.getPort());
                  String _snowmanxxxxxx = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, _snowmanxxx.H(), _snowmanxxx.ab(), _snowmanxxx.I(), _snowmanxxx.J());
                  this.a(_snowman, this.a(_snowmanxxxxxx));
                  break;
               default:
                  boolean _snowmanxxxxxx = _snowman.readUnsignedByte() == 1;
                  _snowmanxxxxxx &= _snowman.readUnsignedByte() == 250;
                  _snowmanxxxxxx &= "MC|PingHost".equals(new String(_snowman.readBytes(_snowman.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                  int _snowmanxxxxxxx = _snowman.readUnsignedShort();
                  _snowmanxxxxxx &= _snowman.readUnsignedByte() >= 73;
                  _snowmanxxxxxx &= 3 + _snowman.readBytes(_snowman.readShort() * 2).array().length + 4 == _snowmanxxxxxxx;
                  _snowmanxxxxxx &= _snowman.readInt() <= 65535;
                  _snowmanxxxxxx &= _snowman.readableBytes() == 0;
                  if (!_snowmanxxxxxx) {
                     return;
                  }

                  a.debug("Ping: (1.6) from {}:{}", _snowmanxx.getAddress(), _snowmanxx.getPort());
                  String _snowmanxxxxxxxx = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, _snowmanxxx.H(), _snowmanxxx.ab(), _snowmanxxx.I(), _snowmanxxx.J());
                  ByteBuf _snowmanxxxxxxxxx = this.a(_snowmanxxxxxxxx);

                  try {
                     this.a(_snowman, _snowmanxxxxxxxxx);
                  } finally {
                     _snowmanxxxxxxxxx.release();
                  }
            }

            _snowman.release();
            _snowmanx = false;
         } catch (RuntimeException var21) {
         }
      } finally {
         if (_snowmanx) {
            _snowman.resetReaderIndex();
            _snowman.channel().pipeline().remove("legacy_query");
            _snowman.fireChannelRead(_snowman);
         }
      }
   }

   private void a(ChannelHandlerContext var1, ByteBuf var2) {
      _snowman.pipeline().firstContext().writeAndFlush(_snowman).addListener(ChannelFutureListener.CLOSE);
   }

   private ByteBuf a(String var1) {
      ByteBuf _snowman = Unpooled.buffer();
      _snowman.writeByte(255);
      char[] _snowmanx = _snowman.toCharArray();
      _snowman.writeShort(_snowmanx.length);

      for (char _snowmanxx : _snowmanx) {
         _snowman.writeChar(_snowmanxx);
      }

      return _snowman;
   }
}
