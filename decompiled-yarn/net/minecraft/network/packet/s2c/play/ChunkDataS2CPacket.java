package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

public class ChunkDataS2CPacket implements Packet<ClientPlayPacketListener> {
   private int chunkX;
   private int chunkZ;
   private int verticalStripBitmask;
   private CompoundTag heightmaps;
   @Nullable
   private int[] biomeArray;
   private byte[] data;
   private List<CompoundTag> blockEntities;
   private boolean isFullChunk;

   public ChunkDataS2CPacket() {
   }

   public ChunkDataS2CPacket(WorldChunk chunk, int includedSectionsMask) {
      ChunkPos _snowman = chunk.getPos();
      this.chunkX = _snowman.x;
      this.chunkZ = _snowman.z;
      this.isFullChunk = includedSectionsMask == 65535;
      this.heightmaps = new CompoundTag();

      for (Entry<Heightmap.Type, Heightmap> _snowmanx : chunk.getHeightmaps()) {
         if (_snowmanx.getKey().shouldSendToClient()) {
            this.heightmaps.put(_snowmanx.getKey().getName(), new LongArrayTag(_snowmanx.getValue().asLongArray()));
         }
      }

      if (this.isFullChunk) {
         this.biomeArray = chunk.getBiomeArray().toIntArray();
      }

      this.data = new byte[this.getDataSize(chunk, includedSectionsMask)];
      this.verticalStripBitmask = this.writeData(new PacketByteBuf(this.getWriteBuffer()), chunk, includedSectionsMask);
      this.blockEntities = Lists.newArrayList();

      for (Entry<BlockPos, BlockEntity> _snowmanxx : chunk.getBlockEntities().entrySet()) {
         BlockPos _snowmanxxx = _snowmanxx.getKey();
         BlockEntity _snowmanxxxx = _snowmanxx.getValue();
         int _snowmanxxxxx = _snowmanxxx.getY() >> 4;
         if (this.isFullChunk() || (includedSectionsMask & 1 << _snowmanxxxxx) != 0) {
            CompoundTag _snowmanxxxxxx = _snowmanxxxx.toInitialChunkDataTag();
            this.blockEntities.add(_snowmanxxxxxx);
         }
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.chunkX = buf.readInt();
      this.chunkZ = buf.readInt();
      this.isFullChunk = buf.readBoolean();
      this.verticalStripBitmask = buf.readVarInt();
      this.heightmaps = buf.readCompoundTag();
      if (this.isFullChunk) {
         this.biomeArray = buf.readIntArray(BiomeArray.DEFAULT_LENGTH);
      }

      int _snowman = buf.readVarInt();
      if (_snowman > 2097152) {
         throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
      } else {
         this.data = new byte[_snowman];
         buf.readBytes(this.data);
         int _snowmanx = buf.readVarInt();
         this.blockEntities = Lists.newArrayList();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
            this.blockEntities.add(buf.readCompoundTag());
         }
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeInt(this.chunkX);
      buf.writeInt(this.chunkZ);
      buf.writeBoolean(this.isFullChunk);
      buf.writeVarInt(this.verticalStripBitmask);
      buf.writeCompoundTag(this.heightmaps);
      if (this.biomeArray != null) {
         buf.writeIntArray(this.biomeArray);
      }

      buf.writeVarInt(this.data.length);
      buf.writeBytes(this.data);
      buf.writeVarInt(this.blockEntities.size());

      for (CompoundTag _snowman : this.blockEntities) {
         buf.writeCompoundTag(_snowman);
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onChunkData(this);
   }

   public PacketByteBuf getReadBuffer() {
      return new PacketByteBuf(Unpooled.wrappedBuffer(this.data));
   }

   private ByteBuf getWriteBuffer() {
      ByteBuf _snowman = Unpooled.wrappedBuffer(this.data);
      _snowman.writerIndex(0);
      return _snowman;
   }

   public int writeData(PacketByteBuf _snowman, WorldChunk chunk, int includedSectionsMask) {
      int _snowmanx = 0;
      ChunkSection[] _snowmanxx = chunk.getSectionArray();
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = _snowmanxx.length; _snowmanxxx < _snowmanxxxx; _snowmanxxx++) {
         ChunkSection _snowmanxxxxx = _snowmanxx[_snowmanxxx];
         if (_snowmanxxxxx != WorldChunk.EMPTY_SECTION && (!this.isFullChunk() || !_snowmanxxxxx.isEmpty()) && (includedSectionsMask & 1 << _snowmanxxx) != 0) {
            _snowmanx |= 1 << _snowmanxxx;
            _snowmanxxxxx.toPacket(_snowman);
         }
      }

      return _snowmanx;
   }

   protected int getDataSize(WorldChunk chunk, int includedSectionsMark) {
      int _snowman = 0;
      ChunkSection[] _snowmanx = chunk.getSectionArray();
      int _snowmanxx = 0;

      for (int _snowmanxxx = _snowmanx.length; _snowmanxx < _snowmanxxx; _snowmanxx++) {
         ChunkSection _snowmanxxxx = _snowmanx[_snowmanxx];
         if (_snowmanxxxx != WorldChunk.EMPTY_SECTION && (!this.isFullChunk() || !_snowmanxxxx.isEmpty()) && (includedSectionsMark & 1 << _snowmanxx) != 0) {
            _snowman += _snowmanxxxx.getPacketSize();
         }
      }

      return _snowman;
   }

   public int getX() {
      return this.chunkX;
   }

   public int getZ() {
      return this.chunkZ;
   }

   public int getVerticalStripBitmask() {
      return this.verticalStripBitmask;
   }

   public boolean isFullChunk() {
      return this.isFullChunk;
   }

   public CompoundTag getHeightmaps() {
      return this.heightmaps;
   }

   public List<CompoundTag> getBlockEntityTagList() {
      return this.blockEntities;
   }

   @Nullable
   public int[] getBiomeArray() {
      return this.biomeArray;
   }
}
