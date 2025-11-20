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

   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
      byteBuf.markReaderIndex();
      byte[] bs = new byte[3];

      for (int i = 0; i < bs.length; i++) {
         if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return;
         }

         bs[i] = byteBuf.readByte();
         if (bs[i] >= 0) {
            PacketByteBuf lv = new PacketByteBuf(Unpooled.wrappedBuffer(bs));

            try {
               int j = lv.readVarInt();
               if (byteBuf.readableBytes() >= j) {
                  list.add(byteBuf.readBytes(j));
                  return;
               }

               byteBuf.resetReaderIndex();
            } finally {
               lv.release();
            }

            return;
         }
      }

      throw new CorruptedFrameException("length wider than 21-bit");
   }
}
