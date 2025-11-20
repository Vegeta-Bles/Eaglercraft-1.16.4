package net.minecraft.network.packet.c2s.play;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.util.Identifier;

public class CustomPayloadC2SPacket implements Packet<ServerPlayPacketListener> {
   public static final Identifier BRAND = new Identifier("brand");
   private Identifier channel;
   private PacketByteBuf data;

   public CustomPayloadC2SPacket() {
   }

   public CustomPayloadC2SPacket(Identifier channel, PacketByteBuf data) {
      this.channel = channel;
      this.data = data;
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.channel = buf.readIdentifier();
      int _snowman = buf.readableBytes();
      if (_snowman >= 0 && _snowman <= 32767) {
         this.data = new PacketByteBuf(buf.readBytes(_snowman));
      } else {
         throw new IOException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeIdentifier(this.channel);
      buf.writeBytes(this.data);
   }

   public void apply(ServerPlayPacketListener _snowman) {
      _snowman.onCustomPayload(this);
      if (this.data != null) {
         this.data.release();
      }
   }
}
