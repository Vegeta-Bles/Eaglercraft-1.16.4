package net.minecraft.network.packet.c2s.play;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.util.math.BlockPos;

public class UpdateSignC2SPacket implements Packet<ServerPlayPacketListener> {
   private BlockPos pos;
   private String[] text;

   public UpdateSignC2SPacket() {
   }

   public UpdateSignC2SPacket(BlockPos pos, String line1, String line2, String line3, String line4) {
      this.pos = pos;
      this.text = new String[]{line1, line2, line3, line4};
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.pos = buf.readBlockPos();
      this.text = new String[4];

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         this.text[_snowman] = buf.readString(384);
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeBlockPos(this.pos);

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         buf.writeString(this.text[_snowman]);
      }
   }

   public void apply(ServerPlayPacketListener _snowman) {
      _snowman.onSignUpdate(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public String[] getText() {
      return this.text;
   }
}
