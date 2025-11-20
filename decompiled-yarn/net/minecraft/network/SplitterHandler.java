package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class SplitterHandler extends ByteToMessageDecoder {
   public SplitterHandler() {
   }

   protected void decode(ChannelHandlerContext _snowman, ByteBuf _snowman, List<Object> _snowman) throws Exception {
      _snowman.markReaderIndex();
      byte[] _snowmanxxx = new byte[3];

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.length; _snowmanxxxx++) {
         if (!_snowman.isReadable()) {
            _snowman.resetReaderIndex();
            return;
         }

         _snowmanxxx[_snowmanxxxx] = _snowman.readByte();
         if (_snowmanxxx[_snowmanxxxx] >= 0) {
            PacketByteBuf _snowmanxxxxx = new PacketByteBuf(Unpooled.wrappedBuffer(_snowmanxxx));

            try {
               int _snowmanxxxxxx = _snowmanxxxxx.readVarInt();
               if (_snowman.readableBytes() >= _snowmanxxxxxx) {
                  _snowman.add(_snowman.readBytes(_snowmanxxxxxx));
                  return;
               }

               _snowman.resetReaderIndex();
            } finally {
               _snowmanxxxxx.release();
            }

            return;
         }
      }

      throw new CorruptedFrameException("length wider than 21-bit");
   }
}
