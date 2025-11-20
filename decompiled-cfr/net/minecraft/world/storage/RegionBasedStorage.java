/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.ThrowableDeliverer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.RegionFile;

public final class RegionBasedStorage
implements AutoCloseable {
    private final Long2ObjectLinkedOpenHashMap<RegionFile> cachedRegionFiles = new Long2ObjectLinkedOpenHashMap();
    private final File directory;
    private final boolean dsync;

    RegionBasedStorage(File directory, boolean dsync) {
        this.directory = directory;
        this.dsync = dsync;
    }

    private RegionFile getRegionFile(ChunkPos pos) throws IOException {
        long l = ChunkPos.toLong(pos.getRegionX(), pos.getRegionZ());
        RegionFile _snowman2 = (RegionFile)this.cachedRegionFiles.getAndMoveToFirst(l);
        if (_snowman2 != null) {
            return _snowman2;
        }
        if (this.cachedRegionFiles.size() >= 256) {
            ((RegionFile)this.cachedRegionFiles.removeLast()).close();
        }
        if (!this.directory.exists()) {
            this.directory.mkdirs();
        }
        File _snowman3 = new File(this.directory, "r." + pos.getRegionX() + "." + pos.getRegionZ() + ".mca");
        RegionFile _snowman4 = new RegionFile(_snowman3, this.directory, this.dsync);
        this.cachedRegionFiles.putAndMoveToFirst(l, (Object)_snowman4);
        return _snowman4;
    }

    @Nullable
    public CompoundTag getTagAt(ChunkPos pos) throws IOException {
        RegionFile regionFile = this.getRegionFile(pos);
        try (DataInputStream _snowman2 = regionFile.getChunkInputStream(pos);){
            if (_snowman2 == null) {
                CompoundTag compoundTag = null;
                return compoundTag;
            }
            CompoundTag compoundTag = NbtIo.read(_snowman2);
            return compoundTag;
        }
    }

    protected void write(ChunkPos pos, CompoundTag tag) throws IOException {
        RegionFile regionFile = this.getRegionFile(pos);
        try (DataOutputStream _snowman2 = regionFile.getChunkOutputStream(pos);){
            NbtIo.write(tag, (DataOutput)_snowman2);
        }
    }

    @Override
    public void close() throws IOException {
        ThrowableDeliverer<IOException> throwableDeliverer = new ThrowableDeliverer<IOException>();
        for (RegionFile regionFile : this.cachedRegionFiles.values()) {
            try {
                regionFile.close();
            }
            catch (IOException iOException) {
                throwableDeliverer.add(iOException);
            }
        }
        throwableDeliverer.deliver();
    }

    public void method_26982() throws IOException {
        for (RegionFile regionFile : this.cachedRegionFiles.values()) {
            regionFile.method_26981();
        }
    }
}

