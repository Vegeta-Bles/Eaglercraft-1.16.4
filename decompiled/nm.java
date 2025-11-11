import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class nm extends MessageToByteEncoder<ByteBuf> {
   public nm() {
   }

   protected void a(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws Exception {
      int _snowman = _snowman.readableBytes();
      int _snowmanx = nf.a(_snowman);
      if (_snowmanx > 3) {
         throw new IllegalArgumentException("unable to fit " + _snowman + " into " + 3);
      } else {
         nf _snowmanxx = new nf(_snowman);
         _snowmanxx.ensureWritable(_snowmanx + _snowman);
         _snowmanxx.d(_snowman);
         _snowmanxx.writeBytes(_snowman, _snowman.readerIndex(), _snowman);
      }
   }
}
