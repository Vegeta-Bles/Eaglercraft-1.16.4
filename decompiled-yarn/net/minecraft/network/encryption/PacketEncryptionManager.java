package net.minecraft.network.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class PacketEncryptionManager {
   private final Cipher cipher;
   private byte[] conversionBuffer = new byte[0];
   private byte[] encryptionBuffer = new byte[0];

   protected PacketEncryptionManager(Cipher _snowman) {
      this.cipher = _snowman;
   }

   private byte[] toByteArray(ByteBuf _snowman) {
      int _snowmanx = _snowman.readableBytes();
      if (this.conversionBuffer.length < _snowmanx) {
         this.conversionBuffer = new byte[_snowmanx];
      }

      _snowman.readBytes(this.conversionBuffer, 0, _snowmanx);
      return this.conversionBuffer;
   }

   protected ByteBuf decrypt(ChannelHandlerContext context, ByteBuf _snowman) throws ShortBufferException {
      int _snowmanx = _snowman.readableBytes();
      byte[] _snowmanxx = this.toByteArray(_snowman);
      ByteBuf _snowmanxxx = context.alloc().heapBuffer(this.cipher.getOutputSize(_snowmanx));
      _snowmanxxx.writerIndex(this.cipher.update(_snowmanxx, 0, _snowmanx, _snowmanxxx.array(), _snowmanxxx.arrayOffset()));
      return _snowmanxxx;
   }

   protected void encrypt(ByteBuf buffer, ByteBuf _snowman) throws ShortBufferException {
      int _snowmanx = buffer.readableBytes();
      byte[] _snowmanxx = this.toByteArray(buffer);
      int _snowmanxxx = this.cipher.getOutputSize(_snowmanx);
      if (this.encryptionBuffer.length < _snowmanxxx) {
         this.encryptionBuffer = new byte[_snowmanxxx];
      }

      _snowman.writeBytes(this.encryptionBuffer, 0, this.cipher.update(_snowmanxx, 0, _snowmanx, this.encryptionBuffer));
   }
}
