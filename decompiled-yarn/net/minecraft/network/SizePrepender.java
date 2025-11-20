package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class SizePrepender extends MessageToByteEncoder<ByteBuf> {
   public SizePrepender() {
   }

   protected void encode(ChannelHandlerContext _snowman, ByteBuf _snowman, ByteBuf _snowman) throws Exception {
      int _snowmanxxx = _snowman.readableBytes();
      int _snowmanxxxx = PacketByteBuf.getVarIntSizeBytes(_snowmanxxx);
      if (_snowmanxxxx > 3) {
         throw new IllegalArgumentException("unable to fit " + _snowmanxxx + " into " + 3);
      } else {
         PacketByteBuf _snowmanxxxxx = new PacketByteBuf(_snowman);
         _snowmanxxxxx.ensureWritable(_snowmanxxxx + _snowmanxxx);
         _snowmanxxxxx.writeVarInt(_snowmanxxx);
         _snowmanxxxxx.writeBytes(_snowman, _snowman.readerIndex(), _snowmanxxx);
      }
   }
}
