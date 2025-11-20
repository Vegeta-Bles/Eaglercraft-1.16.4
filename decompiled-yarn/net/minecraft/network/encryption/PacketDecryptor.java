package net.minecraft.network.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class PacketDecryptor extends MessageToMessageDecoder<ByteBuf> {
   private final PacketEncryptionManager manager;

   public PacketDecryptor(Cipher _snowman) {
      this.manager = new PacketEncryptionManager(_snowman);
   }

   protected void decode(ChannelHandlerContext _snowman, ByteBuf _snowman, List<Object> _snowman) throws Exception {
      _snowman.add(this.manager.decrypt(_snowman, _snowman));
   }
}
