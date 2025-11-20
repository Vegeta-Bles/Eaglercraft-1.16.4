package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerNetworkIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LegacyQueryHandler extends ChannelInboundHandlerAdapter {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ServerNetworkIo networkIo;

   public LegacyQueryHandler(ServerNetworkIo networkIo) {
      this.networkIo = networkIo;
   }

   public void channelRead(ChannelHandlerContext _snowman, Object _snowman) throws Exception {
      ByteBuf _snowmanxx = (ByteBuf)_snowman;
      _snowmanxx.markReaderIndex();
      boolean _snowmanxxx = true;

      try {
         try {
            if (_snowmanxx.readUnsignedByte() != 254) {
               return;
            }

            InetSocketAddress _snowmanxxxx = (InetSocketAddress)_snowman.channel().remoteAddress();
            MinecraftServer _snowmanxxxxx = this.networkIo.getServer();
            int _snowmanxxxxxx = _snowmanxx.readableBytes();
            switch (_snowmanxxxxxx) {
               case 0:
                  LOGGER.debug("Ping: (<1.3.x) from {}:{}", _snowmanxxxx.getAddress(), _snowmanxxxx.getPort());
                  String _snowmanxxxxxxx = String.format("%s§%d§%d", _snowmanxxxxx.getServerMotd(), _snowmanxxxxx.getCurrentPlayerCount(), _snowmanxxxxx.getMaxPlayerCount());
                  this.reply(_snowman, this.toBuffer(_snowmanxxxxxxx));
                  break;
               case 1:
                  if (_snowmanxx.readUnsignedByte() != 1) {
                     return;
                  }

                  LOGGER.debug("Ping: (1.4-1.5.x) from {}:{}", _snowmanxxxx.getAddress(), _snowmanxxxx.getPort());
                  String _snowmanxxxxxxxx = String.format(
                     "§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                     127,
                     _snowmanxxxxx.getVersion(),
                     _snowmanxxxxx.getServerMotd(),
                     _snowmanxxxxx.getCurrentPlayerCount(),
                     _snowmanxxxxx.getMaxPlayerCount()
                  );
                  this.reply(_snowman, this.toBuffer(_snowmanxxxxxxxx));
                  break;
               default:
                  boolean _snowmanxxxxxxxx = _snowmanxx.readUnsignedByte() == 1;
                  _snowmanxxxxxxxx &= _snowmanxx.readUnsignedByte() == 250;
                  _snowmanxxxxxxxx &= "MC|PingHost".equals(new String(_snowmanxx.readBytes(_snowmanxx.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                  int _snowmanxxxxxxxxx = _snowmanxx.readUnsignedShort();
                  _snowmanxxxxxxxx &= _snowmanxx.readUnsignedByte() >= 73;
                  _snowmanxxxxxxxx &= 3 + _snowmanxx.readBytes(_snowmanxx.readShort() * 2).array().length + 4 == _snowmanxxxxxxxxx;
                  _snowmanxxxxxxxx &= _snowmanxx.readInt() <= 65535;
                  _snowmanxxxxxxxx &= _snowmanxx.readableBytes() == 0;
                  if (!_snowmanxxxxxxxx) {
                     return;
                  }

                  LOGGER.debug("Ping: (1.6) from {}:{}", _snowmanxxxx.getAddress(), _snowmanxxxx.getPort());
                  String _snowmanxxxxxxxxxx = String.format(
                     "§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                     127,
                     _snowmanxxxxx.getVersion(),
                     _snowmanxxxxx.getServerMotd(),
                     _snowmanxxxxx.getCurrentPlayerCount(),
                     _snowmanxxxxx.getMaxPlayerCount()
                  );
                  ByteBuf _snowmanxxxxxxxxxxx = this.toBuffer(_snowmanxxxxxxxxxx);

                  try {
                     this.reply(_snowman, _snowmanxxxxxxxxxxx);
                  } finally {
                     _snowmanxxxxxxxxxxx.release();
                  }
            }

            _snowmanxx.release();
            _snowmanxxx = false;
         } catch (RuntimeException var21) {
         }
      } finally {
         if (_snowmanxxx) {
            _snowmanxx.resetReaderIndex();
            _snowman.channel().pipeline().remove("legacy_query");
            _snowman.fireChannelRead(_snowman);
         }
      }
   }

   private void reply(ChannelHandlerContext ctx, ByteBuf buf) {
      ctx.pipeline().firstContext().writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
   }

   private ByteBuf toBuffer(String s) {
      ByteBuf _snowman = Unpooled.buffer();
      _snowman.writeByte(255);
      char[] _snowmanx = s.toCharArray();
      _snowman.writeShort(_snowmanx.length);

      for (char _snowmanxx : _snowmanx) {
         _snowman.writeChar(_snowmanxx);
      }

      return _snowman;
   }
}
