/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.level.storage.AlphaChunkDataArray;

public class AlphaChunkIo {
    public static AlphaChunk readAlphaChunk(CompoundTag tag) {
        int n = tag.getInt("xPos");
        _snowman = tag.getInt("zPos");
        AlphaChunk _snowman2 = new AlphaChunk(n, _snowman);
        _snowman2.blocks = tag.getByteArray("Blocks");
        _snowman2.data = new AlphaChunkDataArray(tag.getByteArray("Data"), 7);
        _snowman2.skyLight = new AlphaChunkDataArray(tag.getByteArray("SkyLight"), 7);
        _snowman2.blockLight = new AlphaChunkDataArray(tag.getByteArray("BlockLight"), 7);
        _snowman2.heightMap = tag.getByteArray("HeightMap");
        _snowman2.terrainPopulated = tag.getBoolean("TerrainPopulated");
        _snowman2.entities = tag.getList("Entities", 10);
        _snowman2.blockEntities = tag.getList("TileEntities", 10);
        _snowman2.blockTicks = tag.getList("TileTicks", 10);
        try {
            _snowman2.lastUpdate = tag.getLong("LastUpdate");
        }
        catch (ClassCastException _snowman3) {
            _snowman2.lastUpdate = tag.getInt("LastUpdate");
        }
        return _snowman2;
    }

    public static void convertAlphaChunk(DynamicRegistryManager.Impl impl, AlphaChunk alphaChunk, CompoundTag compoundTag2, BiomeSource biomeSource) {
        CompoundTag compoundTag2;
        compoundTag2.putInt("xPos", alphaChunk.x);
        compoundTag2.putInt("zPos", alphaChunk.z);
        compoundTag2.putLong("LastUpdate", alphaChunk.lastUpdate);
        int[] nArray = new int[alphaChunk.heightMap.length];
        for (int i = 0; i < alphaChunk.heightMap.length; ++i) {
            nArray[i] = alphaChunk.heightMap[i];
        }
        compoundTag2.putIntArray("HeightMap", nArray);
        compoundTag2.putBoolean("TerrainPopulated", alphaChunk.terrainPopulated);
        ListTag _snowman2 = new ListTag();
        for (int i = 0; i < 8; ++i) {
            boolean bl = true;
            for (int j = 0; j < 16 && bl; ++j) {
                block3: for (_snowman = 0; _snowman < 16 && bl; ++_snowman) {
                    for (_snowman = 0; _snowman < 16; ++_snowman) {
                        _snowman = j << 11 | _snowman << 7 | _snowman + (i << 4);
                        j = alphaChunk.blocks[_snowman];
                        if (j == 0) continue;
                        bl = false;
                        continue block3;
                    }
                }
            }
            if (bl) continue;
            byte[] _snowman3 = new byte[4096];
            ChunkNibbleArray _snowman4 = new ChunkNibbleArray();
            ChunkNibbleArray _snowman5 = new ChunkNibbleArray();
            ChunkNibbleArray _snowman6 = new ChunkNibbleArray();
            for (int j = 0; j < 16; ++j) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    for (_snowman = 0; _snowman < 16; ++_snowman) {
                        _snowman = j << 11 | _snowman << 7 | _snowman + (i << 4);
                        byte by = alphaChunk.blocks[_snowman];
                        _snowman3[_snowman << 8 | _snowman << 4 | j] = (byte)(by & 0xFF);
                        _snowman4.set(j, _snowman, _snowman, alphaChunk.data.get(j, _snowman + (i << 4), _snowman));
                        _snowman5.set(j, _snowman, _snowman, alphaChunk.skyLight.get(j, _snowman + (i << 4), _snowman));
                        _snowman6.set(j, _snowman, _snowman, alphaChunk.blockLight.get(j, _snowman + (i << 4), _snowman));
                    }
                }
            }
            CompoundTag compoundTag3 = new CompoundTag();
            compoundTag3.putByte("Y", (byte)(i & 0xFF));
            compoundTag3.putByteArray("Blocks", _snowman3);
            compoundTag3.putByteArray("Data", _snowman4.asByteArray());
            compoundTag3.putByteArray("SkyLight", _snowman5.asByteArray());
            compoundTag3.putByteArray("BlockLight", _snowman6.asByteArray());
            _snowman2.add(compoundTag3);
        }
        compoundTag2.put("Sections", _snowman2);
        compoundTag2.putIntArray("Biomes", new BiomeArray(impl.get(Registry.BIOME_KEY), new ChunkPos(alphaChunk.x, alphaChunk.z), biomeSource).toIntArray());
        compoundTag2.put("Entities", alphaChunk.entities);
        compoundTag2.put("TileEntities", alphaChunk.blockEntities);
        if (alphaChunk.blockTicks != null) {
            compoundTag2.put("TileTicks", alphaChunk.blockTicks);
        }
        compoundTag2.putBoolean("convertedFromAlphaFormat", true);
    }

    public static class AlphaChunk {
        public long lastUpdate;
        public boolean terrainPopulated;
        public byte[] heightMap;
        public AlphaChunkDataArray blockLight;
        public AlphaChunkDataArray skyLight;
        public AlphaChunkDataArray data;
        public byte[] blocks;
        public ListTag entities;
        public ListTag blockEntities;
        public ListTag blockTicks;
        public final int x;
        public final int z;

        public AlphaChunk(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }
}

