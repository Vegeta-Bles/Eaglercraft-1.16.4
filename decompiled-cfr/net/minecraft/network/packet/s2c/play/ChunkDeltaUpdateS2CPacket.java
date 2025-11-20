/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.shorts.ShortIterator
 *  it.unimi.dsi.fastutil.shorts.ShortSet
 */
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

public class ChunkDeltaUpdateS2CPacket
implements Packet<ClientPlayPacketListener> {
    private ChunkSectionPos sectionPos;
    private short[] positions;
    private BlockState[] blockStates;
    private boolean field_26749;

    public ChunkDeltaUpdateS2CPacket() {
    }

    public ChunkDeltaUpdateS2CPacket(ChunkSectionPos sectionPos, ShortSet shortSet, ChunkSection section, boolean bl) {
        this.sectionPos = sectionPos;
        this.field_26749 = bl;
        this.allocateBuffers(shortSet.size());
        int n = 0;
        ShortIterator shortIterator = shortSet.iterator();
        while (shortIterator.hasNext()) {
            this.positions[n] = _snowman = ((Short)shortIterator.next()).shortValue();
            this.blockStates[n] = section.getBlockState(ChunkSectionPos.unpackLocalX(_snowman), ChunkSectionPos.unpackLocalY(_snowman), ChunkSectionPos.unpackLocalZ(_snowman));
            ++n;
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
        int n = buf.readVarInt();
        this.allocateBuffers(n);
        for (_snowman = 0; _snowman < this.positions.length; ++_snowman) {
            long l = buf.readVarLong();
            this.positions[_snowman] = (short)(l & 0xFFFL);
            this.blockStates[_snowman] = Block.STATE_IDS.get((int)(l >>> 12));
        }
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeLong(this.sectionPos.asLong());
        buf.writeBoolean(this.field_26749);
        buf.writeVarInt(this.positions.length);
        for (int i = 0; i < this.positions.length; ++i) {
            buf.writeVarLong(Block.getRawIdFromState(this.blockStates[i]) << 12 | this.positions[i]);
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onChunkDeltaUpdate(this);
    }

    public void visitUpdates(BiConsumer<BlockPos, BlockState> biConsumer) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < this.positions.length; ++i) {
            short s = this.positions[i];
            mutable.set(this.sectionPos.unpackBlockX(s), this.sectionPos.unpackBlockY(s), this.sectionPos.unpackBlockZ(s));
            biConsumer.accept(mutable, this.blockStates[i]);
        }
    }

    public boolean method_31179() {
        return this.field_26749;
    }
}

