import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class mz extends MessageToMessageDecoder<ByteBuf> {
   private final my a;

   public mz(Cipher var1) {
      this.a = new my(_snowman);
   }

   protected void a(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      _snowman.add(this.a.a(_snowman, _snowman));
   }
}
