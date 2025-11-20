package net.minecraft.network.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class PacketEncryptor extends MessageToByteEncoder<ByteBuf> {
   private final PacketEncryptionManager manager;

   public PacketEncryptor(Cipher _snowman) {
      this.manager = new PacketEncryptionManager(_snowman);
   }

   protected void encode(ChannelHandlerContext _snowman, ByteBuf _snowman, ByteBuf _snowman) throws Exception {
      this.manager.encrypt(_snowman, _snowman);
   }
}
