import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class nb extends ByteToMessageDecoder {
   private final Inflater a;
   private int b;

   public nb(int var1) {
      this.b = _snowman;
      this.a = new Inflater();
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      if (_snowman.readableBytes() != 0) {
         nf _snowman = new nf(_snowman);
         int _snowmanx = _snowman.i();
         if (_snowmanx == 0) {
            _snowman.add(_snowman.readBytes(_snowman.readableBytes()));
         } else {
            if (_snowmanx < this.b) {
               throw new DecoderException("Badly compressed packet - size of " + _snowmanx + " is below server threshold of " + this.b);
            }

            if (_snowmanx > 2097152) {
               throw new DecoderException("Badly compressed packet - size of " + _snowmanx + " is larger than protocol maximum of " + 2097152);
            }

            byte[] _snowmanxx = new byte[_snowman.readableBytes()];
            _snowman.readBytes(_snowmanxx);
            this.a.setInput(_snowmanxx);
            byte[] _snowmanxxx = new byte[_snowmanx];
            this.a.inflate(_snowmanxxx);
            _snowman.add(Unpooled.wrappedBuffer(_snowmanxxx));
            this.a.reset();
         }
      }
   }

   public void a(int var1) {
      this.b = _snowman;
   }
}
