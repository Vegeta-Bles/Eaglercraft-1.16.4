package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.collection.DefaultedList;

public class InventoryS2CPacket implements Packet<ClientPlayPacketListener> {
   private int syncId;
   private List<ItemStack> contents;

   public InventoryS2CPacket() {
   }

   public InventoryS2CPacket(int syncId, DefaultedList<ItemStack> contents) {
      this.syncId = syncId;
      this.contents = DefaultedList.ofSize(contents.size(), ItemStack.EMPTY);

      for (int _snowman = 0; _snowman < this.contents.size(); _snowman++) {
         this.contents.set(_snowman, contents.get(_snowman).copy());
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.syncId = buf.readUnsignedByte();
      int _snowman = buf.readShort();
      this.contents = DefaultedList.ofSize(_snowman, ItemStack.EMPTY);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.contents.set(_snowmanx, buf.readItemStack());
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeByte(this.syncId);
      buf.writeShort(this.contents.size());

      for (ItemStack _snowman : this.contents) {
         buf.writeItemStack(_snowman);
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onInventory(this);
   }

   public int getSyncId() {
      return this.syncId;
   }

   public List<ItemStack> getContents() {
      return this.contents;
   }
}
