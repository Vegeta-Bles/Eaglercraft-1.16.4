import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class nc extends MessageToByteEncoder<ByteBuf> {
   private final byte[] a = new byte[8192];
   private final Deflater b;
   private int c;

   public nc(int var1) {
      this.c = _snowman;
      this.b = new Deflater();
   }

   protected void a(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws Exception {
      int _snowman = _snowman.readableBytes();
      nf _snowmanx = new nf(_snowman);
      if (_snowman < this.c) {
         _snowmanx.d(0);
         _snowmanx.writeBytes(_snowman);
      } else {
         byte[] _snowmanxx = new byte[_snowman];
         _snowman.readBytes(_snowmanxx);
         _snowmanx.d(_snowmanxx.length);
         this.b.setInput(_snowmanxx, 0, _snowman);
         this.b.finish();

         while (!this.b.finished()) {
            int _snowmanxxx = this.b.deflate(this.a);
            _snowmanx.writeBytes(this.a, 0, _snowmanxxx);
         }

         this.b.reset();
      }
   }

   public void a(int var1) {
      this.c = _snowman;
   }
}
