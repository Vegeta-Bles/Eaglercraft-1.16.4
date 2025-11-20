/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

public class ChunkDataS2CPacket
implements Packet<ClientPlayPacketListener> {
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
        ChunkPos chunkPos = chunk.getPos();
        this.chunkX = chunkPos.x;
        this.chunkZ = chunkPos.z;
        this.isFullChunk = includedSectionsMask == 65535;
        this.heightmaps = new CompoundTag();
        for (Map.Entry<Heightmap.Type, Heightmap> entry : chunk.getHeightmaps()) {
            if (!entry.getKey().shouldSendToClient()) continue;
            this.heightmaps.put(entry.getKey().getName(), new LongArrayTag(entry.getValue().asLongArray()));
        }
        if (this.isFullChunk) {
            this.biomeArray = chunk.getBiomeArray().toIntArray();
        }
        this.data = new byte[this.getDataSize(chunk, includedSectionsMask)];
        this.verticalStripBitmask = this.writeData(new PacketByteBuf(this.getWriteBuffer()), chunk, includedSectionsMask);
        this.blockEntities = Lists.newArrayList();
        for (Map.Entry<Object, Object> entry : chunk.getBlockEntities().entrySet()) {
            BlockPos blockPos = (BlockPos)entry.getKey();
            BlockEntity _snowman2 = (BlockEntity)entry.getValue();
            int _snowman3 = blockPos.getY() >> 4;
            if (!this.isFullChunk() && (includedSectionsMask & 1 << _snowman3) == 0) continue;
            CompoundTag _snowman4 = _snowman2.toInitialChunkDataTag();
            this.blockEntities.add(_snowman4);
        }
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        int n;
        this.chunkX = buf.readInt();
        this.chunkZ = buf.readInt();
        this.isFullChunk = buf.readBoolean();
        this.verticalStripBitmask = buf.readVarInt();
        this.heightmaps = buf.readCompoundTag();
        if (this.isFullChunk) {
            this.biomeArray = buf.readIntArray(BiomeArray.DEFAULT_LENGTH);
        }
        if ((n = buf.readVarInt()) > 0x200000) {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
        }
        this.data = new byte[n];
        buf.readBytes(this.data);
        _snowman = buf.readVarInt();
        this.blockEntities = Lists.newArrayList();
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            this.blockEntities.add(buf.readCompoundTag());
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
        for (CompoundTag compoundTag : this.blockEntities) {
            buf.writeCompoundTag(compoundTag);
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onChunkData(this);
    }

    public PacketByteBuf getReadBuffer() {
        return new PacketByteBuf(Unpooled.wrappedBuffer((byte[])this.data));
    }

    private ByteBuf getWriteBuffer() {
        ByteBuf byteBuf = Unpooled.wrappedBuffer((byte[])this.data);
        byteBuf.writerIndex(0);
        return byteBuf;
    }

    public int writeData(PacketByteBuf packetByteBuf, WorldChunk chunk, int includedSectionsMask) {
        int n = 0;
        ChunkSection[] _snowman2 = chunk.getSectionArray();
        _snowman = _snowman2.length;
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            ChunkSection chunkSection = _snowman2[_snowman];
            if (chunkSection == WorldChunk.EMPTY_SECTION || this.isFullChunk() && chunkSection.isEmpty() || (includedSectionsMask & 1 << _snowman) == 0) continue;
            n |= 1 << _snowman;
            chunkSection.toPacket(packetByteBuf);
        }
        return n;
    }

    protected int getDataSize(WorldChunk chunk, int includedSectionsMark) {
        int n = 0;
        ChunkSection[] _snowman2 = chunk.getSectionArray();
        _snowman = _snowman2.length;
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            ChunkSection chunkSection = _snowman2[_snowman];
            if (chunkSection == WorldChunk.EMPTY_SECTION || this.isFullChunk() && chunkSection.isEmpty() || (includedSectionsMark & 1 << _snowman) == 0) continue;
            n += chunkSection.getPacketSize();
        }
        return n;
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

