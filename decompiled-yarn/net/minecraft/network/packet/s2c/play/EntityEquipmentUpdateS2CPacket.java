package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.List;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class EntityEquipmentUpdateS2CPacket implements Packet<ClientPlayPacketListener> {
   private int id;
   private final List<Pair<EquipmentSlot, ItemStack>> equipmentList;

   public EntityEquipmentUpdateS2CPacket() {
      this.equipmentList = Lists.newArrayList();
   }

   public EntityEquipmentUpdateS2CPacket(int id, List<Pair<EquipmentSlot, ItemStack>> equipmentList) {
      this.id = id;
      this.equipmentList = equipmentList;
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.id = buf.readVarInt();
      EquipmentSlot[] _snowman = EquipmentSlot.values();

      int _snowmanx;
      do {
         _snowmanx = buf.readByte();
         EquipmentSlot _snowmanxx = _snowman[_snowmanx & 127];
         ItemStack _snowmanxxx = buf.readItemStack();
         this.equipmentList.add(Pair.of(_snowmanxx, _snowmanxxx));
      } while ((_snowmanx & -128) != 0);
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.id);
      int _snowman = this.equipmentList.size();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         Pair<EquipmentSlot, ItemStack> _snowmanxx = this.equipmentList.get(_snowmanx);
         EquipmentSlot _snowmanxxx = (EquipmentSlot)_snowmanxx.getFirst();
         boolean _snowmanxxxx = _snowmanx != _snowman - 1;
         int _snowmanxxxxx = _snowmanxxx.ordinal();
         buf.writeByte(_snowmanxxxx ? _snowmanxxxxx | -128 : _snowmanxxxxx);
         buf.writeItemStack((ItemStack)_snowmanxx.getSecond());
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onEquipmentUpdate(this);
   }

   public int getId() {
      return this.id;
   }

   public List<Pair<EquipmentSlot, ItemStack>> getEquipmentList() {
      return this.equipmentList;
   }
}
