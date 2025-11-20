package net.minecraft.network.packet.s2c.play;

import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.IOException;
import java.util.function.BiConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.ChunkSection;

public class ChunkDeltaUpdateS2CPacket implements Packet<ClientPlayPacketListener> {
   private ChunkSectionPos sectionPos;
   private short[] positions;
   private BlockState[] blockStates;
   private boolean field_26749;

   public ChunkDeltaUpdateS2CPacket() {
   }

   public ChunkDeltaUpdateS2CPacket(ChunkSectionPos sectionPos, ShortSet _snowman, ChunkSection section, boolean _snowman) {
      this.sectionPos = sectionPos;
      this.field_26749 = _snowman;
      this.allocateBuffers(_snowman.size());
      int _snowmanxx = 0;

      for (ShortIterator var6 = _snowman.iterator(); var6.hasNext(); _snowmanxx++) {
         short _snowmanxxx = (Short)var6.next();
         this.positions[_snowmanxx] = _snowmanxxx;
         this.blockStates[_snowmanxx] = section.getBlockState(
            ChunkSectionPos.unpackLocalX(_snowmanxxx), ChunkSectionPos.unpackLocalY(_snowmanxxx), ChunkSectionPos.unpackLocalZ(_snowmanxxx)
         );
      }
   }

   private void allocateBuffers(int positionCount) {
      this.positions = new short[positionCount];
      this.blockStates = new BlockState[positionCount];
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.sectionPos = ChunkSectionPos.from(buf.readLong());
      this.field_26749 = buf.readBoolean();
      int _snowman = buf.readVarInt();
      this.allocateBuffers(_snowman);

      for (int _snowmanx = 0; _snowmanx < this.positions.length; _snowmanx++) {
         long _snowmanxx = buf.readVarLong();
         this.positions[_snowmanx] = (short)((int)(_snowmanxx & 4095L));
         this.blockStates[_snowmanx] = Block.STATE_IDS.get((int)(_snowmanxx >>> 12));
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeLong(this.sectionPos.asLong());
      buf.writeBoolean(this.field_26749);
      buf.writeVarInt(this.positions.length);

      for (int _snowman = 0; _snowman < this.positions.length; _snowman++) {
         buf.writeVarLong((long)(Block.getRawIdFromState(this.blockStates[_snowman]) << 12 | this.positions[_snowman]));
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onChunkDeltaUpdate(this);
   }

   public void visitUpdates(BiConsumer<BlockPos, BlockState> _snowman) {
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (int _snowmanxx = 0; _snowmanxx < this.positions.length; _snowmanxx++) {
         short _snowmanxxx = this.positions[_snowmanxx];
         _snowmanx.set(this.sectionPos.unpackBlockX(_snowmanxxx), this.sectionPos.unpackBlockY(_snowmanxxx), this.sectionPos.unpackBlockZ(_snowmanxxx));
         _snowman.accept(_snowmanx, this.blockStates[_snowmanxx]);
      }
   }

   public boolean method_31179() {
      return this.field_26749;
   }
}
