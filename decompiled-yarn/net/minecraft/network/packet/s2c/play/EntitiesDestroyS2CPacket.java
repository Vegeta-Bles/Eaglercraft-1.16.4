package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class EntitiesDestroyS2CPacket implements Packet<ClientPlayPacketListener> {
   private int[] entityIds;

   public EntitiesDestroyS2CPacket() {
   }

   public EntitiesDestroyS2CPacket(int... entityIds) {
      this.entityIds = entityIds;
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.entityIds = new int[buf.readVarInt()];

      for (int _snowman = 0; _snowman < this.entityIds.length; _snowman++) {
         this.entityIds[_snowman] = buf.readVarInt();
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.entityIds.length);

      for (int _snowman : this.entityIds) {
         buf.writeVarInt(_snowman);
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onEntitiesDestroy(this);
   }

   public int[] getEntityIds() {
      return this.entityIds;
   }
}
