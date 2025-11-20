package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class EntityPassengersSetS2CPacket implements Packet<ClientPlayPacketListener> {
   private int id;
   private int[] passengerIds;

   public EntityPassengersSetS2CPacket() {
   }

   public EntityPassengersSetS2CPacket(Entity entity) {
      this.id = entity.getEntityId();
      List<Entity> _snowman = entity.getPassengerList();
      this.passengerIds = new int[_snowman.size()];

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         this.passengerIds[_snowmanx] = _snowman.get(_snowmanx).getEntityId();
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.id = buf.readVarInt();
      this.passengerIds = buf.readIntArray();
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.id);
      buf.writeIntArray(this.passengerIds);
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onEntityPassengersSet(this);
   }

   public int[] getPassengerIds() {
      return this.passengerIds;
   }

   public int getId() {
      return this.id;
   }
}
