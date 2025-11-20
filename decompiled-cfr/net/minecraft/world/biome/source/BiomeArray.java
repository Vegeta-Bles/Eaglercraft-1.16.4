/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.biome.source;

import javax.annotation.Nullable;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeArray
implements BiomeAccess.Storage {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int HORIZONTAL_SECTION_COUNT = (int)Math.round(Math.log(16.0) / Math.log(2.0)) - 2;
    private static final int VERTICAL_SECTION_COUNT = (int)Math.round(Math.log(256.0) / Math.log(2.0)) - 2;
    public static final int DEFAULT_LENGTH = 1 << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT + VERTICAL_SECTION_COUNT;
    public static final int HORIZONTAL_BIT_MASK = (1 << HORIZONTAL_SECTION_COUNT) - 1;
    public static final int VERTICAL_BIT_MASK = (1 << VERTICAL_SECTION_COUNT) - 1;
    private final IndexedIterable<Biome> field_25831;
    private final Biome[] data;

    public BiomeArray(IndexedIterable<Biome> indexedIterable, Biome[] biomeArray) {
        this.field_25831 = indexedIterable;
        this.data = biomeArray;
    }

    private BiomeArray(IndexedIterable<Biome> indexedIterable) {
        this(indexedIterable, new Biome[DEFAULT_LENGTH]);
    }

    public BiomeArray(IndexedIterable<Biome> indexedIterable, int[] nArray) {
        this(indexedIterable);
        for (int i = 0; i < this.data.length; ++i) {
            _snowman = nArray[i];
            Biome biome = indexedIterable.get(_snowman);
            if (biome == null) {
                LOGGER.warn("Received invalid biome id: " + _snowman);
                this.data[i] = indexedIterable.get(0);
                continue;
            }
            this.data[i] = biome;
        }
    }

    public BiomeArray(IndexedIterable<Biome> indexedIterable, ChunkPos chunkPos, BiomeSource biomeSource) {
        this(indexedIterable);
        int n = chunkPos.getStartX() >> 2;
        _snowman = chunkPos.getStartZ() >> 2;
        for (_snowman = 0; _snowman < this.data.length; ++_snowman) {
            _snowman = _snowman & HORIZONTAL_BIT_MASK;
            _snowman = _snowman >> HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT & VERTICAL_BIT_MASK;
            _snowman = _snowman >> HORIZONTAL_SECTION_COUNT & HORIZONTAL_BIT_MASK;
            this.data[_snowman] = biomeSource.getBiomeForNoiseGen(n + _snowman, _snowman, _snowman + _snowman);
        }
    }

    public BiomeArray(IndexedIterable<Biome> indexedIterable, ChunkPos chunkPos, BiomeSource biomeSource, @Nullable int[] nArray) {
        this(indexedIterable);
        int n = chunkPos.getStartX() >> 2;
        _snowman = chunkPos.getStartZ() >> 2;
        if (nArray != null) {
            for (_snowman = 0; _snowman < nArray.length; ++_snowman) {
                this.data[_snowman] = indexedIterable.get(nArray[_snowman]);
                if (this.data[_snowman] != null) continue;
                _snowman = _snowman & HORIZONTAL_BIT_MASK;
                _snowman = _snowman >> HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT & VERTICAL_BIT_MASK;
                _snowman = _snowman >> HORIZONTAL_SECTION_COUNT & HORIZONTAL_BIT_MASK;
                this.data[_snowman] = biomeSource.getBiomeForNoiseGen(n + _snowman, _snowman, _snowman + _snowman);
            }
        } else {
            for (_snowman = 0; _snowman < this.data.length; ++_snowman) {
                _snowman = _snowman & HORIZONTAL_BIT_MASK;
                _snowman = _snowman >> HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT & VERTICAL_BIT_MASK;
                _snowman = _snowman >> HORIZONTAL_SECTION_COUNT & HORIZONTAL_BIT_MASK;
                this.data[_snowman] = biomeSource.getBiomeForNoiseGen(n + _snowman, _snowman, _snowman + _snowman);
            }
        }
    }

    public int[] toIntArray() {
        int[] nArray = new int[this.data.length];
        for (int i = 0; i < this.data.length; ++i) {
            nArray[i] = this.field_25831.getRawId(this.data[i]);
        }
        return nArray;
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        int n = biomeX & HORIZONTAL_BIT_MASK;
        _snowman = MathHelper.clamp(biomeY, 0, VERTICAL_BIT_MASK);
        _snowman = biomeZ & HORIZONTAL_BIT_MASK;
        return this.data[_snowman << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT | _snowman << HORIZONTAL_SECTION_COUNT | n];
    }
}

