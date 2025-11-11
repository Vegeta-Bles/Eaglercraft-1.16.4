import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class nl extends ByteToMessageDecoder {
   public nl() {
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      _snowman.markReaderIndex();
      byte[] _snowman = new byte[3];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         if (!_snowman.isReadable()) {
            _snowman.resetReaderIndex();
            return;
         }

         _snowman[_snowmanx] = _snowman.readByte();
         if (_snowman[_snowmanx] >= 0) {
            nf _snowmanxx = new nf(Unpooled.wrappedBuffer(_snowman));

            try {
               int _snowmanxxx = _snowmanxx.i();
               if (_snowman.readableBytes() >= _snowmanxxx) {
                  _snowman.add(_snowman.readBytes(_snowmanxxx));
                  return;
               }

               _snowman.resetReaderIndex();
            } finally {
               _snowmanxx.release();
            }

            return;
         }
      }

      throw new CorruptedFrameException("length wider than 21-bit");
   }
}
