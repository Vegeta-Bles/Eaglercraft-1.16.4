/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.light.LightingProvider;

public class LightUpdateS2CPacket
implements Packet<ClientPlayPacketListener> {
    private int chunkX;
    private int chunkZ;
    private int skyLightMask;
    private int blockLightMask;
    private int filledSkyLightMask;
    private int filledBlockLightMask;
    private List<byte[]> skyLightUpdates;
    private List<byte[]> blockLightUpdates;
    private boolean field_25659;

    public LightUpdateS2CPacket() {
    }

    public LightUpdateS2CPacket(ChunkPos chunkPos, LightingProvider lightingProvider, boolean bl) {
        this.chunkX = chunkPos.x;
        this.chunkZ = chunkPos.z;
        this.field_25659 = bl;
        this.skyLightUpdates = Lists.newArrayList();
        this.blockLightUpdates = Lists.newArrayList();
        for (int i = 0; i < 18; ++i) {
            ChunkNibbleArray chunkNibbleArray = lightingProvider.get(LightType.SKY).getLightSection(ChunkSectionPos.from(chunkPos, -1 + i));
            _snowman = lightingProvider.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(chunkPos, -1 + i));
            if (chunkNibbleArray != null) {
                if (chunkNibbleArray.isUninitialized()) {
                    this.filledSkyLightMask |= 1 << i;
                } else {
                    this.skyLightMask |= 1 << i;
                    this.skyLightUpdates.add((byte[])chunkNibbleArray.asByteArray().clone());
                }
            }
            if (_snowman == null) continue;
            if (_snowman.isUninitialized()) {
                this.filledBlockLightMask |= 1 << i;
                continue;
            }
            this.blockLightMask |= 1 << i;
            this.blockLightUpdates.add((byte[])_snowman.asByteArray().clone());
        }
    }

    public LightUpdateS2CPacket(ChunkPos pos, LightingProvider lightProvider, int skyLightMask, int blockLightMask, boolean bl) {
        this.chunkX = pos.x;
        this.chunkZ = pos.z;
        this.field_25659 = bl;
        this.skyLightMask = skyLightMask;
        this.blockLightMask = blockLightMask;
        this.skyLightUpdates = Lists.newArrayList();
        this.blockLightUpdates = Lists.newArrayList();
        for (int i = 0; i < 18; ++i) {
            ChunkNibbleArray _snowman2;
            if ((this.skyLightMask & 1 << i) != 0) {
                _snowman2 = lightProvider.get(LightType.SKY).getLightSection(ChunkSectionPos.from(pos, -1 + i));
                if (_snowman2 == null || _snowman2.isUninitialized()) {
                    this.skyLightMask &= ~(1 << i);
                    if (_snowman2 != null) {
                        this.filledSkyLightMask |= 1 << i;
                    }
                } else {
                    this.skyLightUpdates.add((byte[])_snowman2.asByteArray().clone());
                }
            }
            if ((this.blockLightMask & 1 << i) == 0) continue;
            _snowman2 = lightProvider.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(pos, -1 + i));
            if (_snowman2 == null || _snowman2.isUninitialized()) {
                this.blockLightMask &= ~(1 << i);
                if (_snowman2 == null) continue;
                this.filledBlockLightMask |= 1 << i;
                continue;
            }
            this.blockLightUpdates.add((byte[])_snowman2.asByteArray().clone());
        }
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        int n;
        this.chunkX = buf.readVarInt();
        this.chunkZ = buf.readVarInt();
        this.field_25659 = buf.readBoolean();
        this.skyLightMask = buf.readVarInt();
        this.blockLightMask = buf.readVarInt();
        this.filledSkyLightMask = buf.readVarInt();
        this.filledBlockLightMask = buf.readVarInt();
        this.skyLightUpdates = Lists.newArrayList();
        for (n = 0; n < 18; ++n) {
            if ((this.skyLightMask & 1 << n) == 0) continue;
            this.skyLightUpdates.add(buf.readByteArray(2048));
        }
        this.blockLightUpdates = Lists.newArrayList();
        for (n = 0; n < 18; ++n) {
            if ((this.blockLightMask & 1 << n) == 0) continue;
            this.blockLightUpdates.add(buf.readByteArray(2048));
        }
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeVarInt(this.chunkX);
        buf.writeVarInt(this.chunkZ);
        buf.writeBoolean(this.field_25659);
        buf.writeVarInt(this.skyLightMask);
        buf.writeVarInt(this.blockLightMask);
        buf.writeVarInt(this.filledSkyLightMask);
        buf.writeVarInt(this.filledBlockLightMask);
        for (byte[] byArray : this.skyLightUpdates) {
            buf.writeByteArray(byArray);
        }
        for (byte[] byArray : this.blockLightUpdates) {
            buf.writeByteArray(byArray);
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onLightUpdate(this);
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public int getSkyLightMask() {
        return this.skyLightMask;
    }

    public int getFilledSkyLightMask() {
        return this.filledSkyLightMask;
    }

    public List<byte[]> getSkyLightUpdates() {
        return this.skyLightUpdates;
    }

    public int getBlockLightMask() {
        return this.blockLightMask;
    }

    public int getFilledBlockLightMask() {
        return this.filledBlockLightMask;
    }

    public List<byte[]> getBlockLightUpdates() {
        return this.blockLightUpdates;
    }

    public boolean method_30006() {
        return this.field_25659;
    }
}

