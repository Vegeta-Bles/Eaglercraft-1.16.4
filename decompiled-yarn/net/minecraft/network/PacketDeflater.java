package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class PacketDeflater extends MessageToByteEncoder<ByteBuf> {
   private final byte[] deflateBuffer = new byte[8192];
   private final Deflater deflater;
   private int compressionThreshold;

   public PacketDeflater(int _snowman) {
      this.compressionThreshold = _snowman;
      this.deflater = new Deflater();
   }

   protected void encode(ChannelHandlerContext _snowman, ByteBuf _snowman, ByteBuf _snowman) throws Exception {
      int _snowmanxxx = _snowman.readableBytes();
      PacketByteBuf _snowmanxxxx = new PacketByteBuf(_snowman);
      if (_snowmanxxx < this.compressionThreshold) {
         _snowmanxxxx.writeVarInt(0);
         _snowmanxxxx.writeBytes(_snowman);
      } else {
         byte[] _snowmanxxxxx = new byte[_snowmanxxx];
         _snowman.readBytes(_snowmanxxxxx);
         _snowmanxxxx.writeVarInt(_snowmanxxxxx.length);
         this.deflater.setInput(_snowmanxxxxx, 0, _snowmanxxx);
         this.deflater.finish();

         while (!this.deflater.finished()) {
            int _snowmanxxxxxx = this.deflater.deflate(this.deflateBuffer);
            _snowmanxxxx.writeBytes(this.deflateBuffer, 0, _snowmanxxxxxx);
         }

         this.deflater.reset();
      }
   }

   public void setCompressionThreshold(int compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
   }
}
