import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class my {
   private final Cipher a;
   private byte[] b = new byte[0];
   private byte[] c = new byte[0];

   protected my(Cipher var1) {
      this.a = _snowman;
   }

   private byte[] a(ByteBuf var1) {
      int _snowman = _snowman.readableBytes();
      if (this.b.length < _snowman) {
         this.b = new byte[_snowman];
      }

      _snowman.readBytes(this.b, 0, _snowman);
      return this.b;
   }

   protected ByteBuf a(ChannelHandlerContext var1, ByteBuf var2) throws ShortBufferException {
      int _snowman = _snowman.readableBytes();
      byte[] _snowmanx = this.a(_snowman);
      ByteBuf _snowmanxx = _snowman.alloc().heapBuffer(this.a.getOutputSize(_snowman));
      _snowmanxx.writerIndex(this.a.update(_snowmanx, 0, _snowman, _snowmanxx.array(), _snowmanxx.arrayOffset()));
      return _snowmanxx;
   }

   protected void a(ByteBuf var1, ByteBuf var2) throws ShortBufferException {
      int _snowman = _snowman.readableBytes();
      byte[] _snowmanx = this.a(_snowman);
      int _snowmanxx = this.a.getOutputSize(_snowman);
      if (this.c.length < _snowmanxx) {
         this.c = new byte[_snowmanxx];
      }

      _snowman.writeBytes(this.c, 0, this.a.update(_snowmanx, 0, _snowman, this.c));
   }
}
