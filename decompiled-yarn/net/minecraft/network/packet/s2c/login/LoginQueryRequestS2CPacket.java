package net.minecraft.network.packet.s2c.login;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientLoginPacketListener;
import net.minecraft.util.Identifier;

public class LoginQueryRequestS2CPacket implements Packet<ClientLoginPacketListener> {
   private int queryId;
   private Identifier channel;
   private PacketByteBuf payload;

   public LoginQueryRequestS2CPacket() {
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.queryId = buf.readVarInt();
      this.channel = buf.readIdentifier();
      int _snowman = buf.readableBytes();
      if (_snowman >= 0 && _snowman <= 1048576) {
         this.payload = new PacketByteBuf(buf.readBytes(_snowman));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.queryId);
      buf.writeIdentifier(this.channel);
      buf.writeBytes(this.payload.copy());
   }

   public void apply(ClientLoginPacketListener _snowman) {
      _snowman.onQueryRequest(this);
   }

   public int getQueryId() {
      return this.queryId;
   }
}
