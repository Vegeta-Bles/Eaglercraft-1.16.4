package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class PacketInflater extends ByteToMessageDecoder {
   private final Inflater inflater;
   private int compressionThreshold;

   public PacketInflater(int compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
      this.inflater = new Inflater();
   }

   protected void decode(ChannelHandlerContext _snowman, ByteBuf _snowman, List<Object> _snowman) throws Exception {
      if (_snowman.readableBytes() != 0) {
         PacketByteBuf _snowmanxxx = new PacketByteBuf(_snowman);
         int _snowmanxxxx = _snowmanxxx.readVarInt();
         if (_snowmanxxxx == 0) {
            _snowman.add(_snowmanxxx.readBytes(_snowmanxxx.readableBytes()));
         } else {
            if (_snowmanxxxx < this.compressionThreshold) {
               throw new DecoderException("Badly compressed packet - size of " + _snowmanxxxx + " is below server threshold of " + this.compressionThreshold);
            }

            if (_snowmanxxxx > 2097152) {
               throw new DecoderException("Badly compressed packet - size of " + _snowmanxxxx + " is larger than protocol maximum of " + 2097152);
            }

            byte[] _snowmanxxxxx = new byte[_snowmanxxx.readableBytes()];
            _snowmanxxx.readBytes(_snowmanxxxxx);
            this.inflater.setInput(_snowmanxxxxx);
            byte[] _snowmanxxxxxx = new byte[_snowmanxxxx];
            this.inflater.inflate(_snowmanxxxxxx);
            _snowman.add(Unpooled.wrappedBuffer(_snowmanxxxxxx));
            this.inflater.reset();
         }
      }
   }

   public void setCompressionThreshold(int compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
   }
}
