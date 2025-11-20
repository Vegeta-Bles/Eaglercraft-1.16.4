/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class FrozenOceanSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    protected static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
    protected static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState ICE = Blocks.ICE.getDefaultState();
    private OctaveSimplexNoiseSampler field_15644;
    private OctaveSimplexNoiseSampler field_15642;
    private long seed;

    public FrozenOceanSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        double d2 = 0.0;
        _snowman = 0.0;
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        float _snowman3 = biome.getTemperature(_snowman2.set(n, 63, n2));
        _snowman = Math.min(Math.abs(d), this.field_15644.sample((double)n * 0.1, (double)n2 * 0.1, false) * 15.0);
        if (_snowman > 1.8) {
            _snowman = 0.09765625;
            d2 = _snowman * _snowman * 1.2;
            _snowman = Math.abs(this.field_15642.sample((double)n * 0.09765625, (double)n2 * 0.09765625, false));
            _snowman = Math.ceil(_snowman * 40.0) + 14.0;
            if (d2 > _snowman) {
                d2 = _snowman;
            }
            if (_snowman3 > 0.1f) {
                d2 -= 2.0;
            }
            if (d2 > 2.0) {
                _snowman = (double)n4 - d2 - 7.0;
                d2 += (double)n4;
            } else {
                d2 = 0.0;
            }
        }
        int _snowman4 = n & 0xF;
        int _snowman5 = n2 & 0xF;
        SurfaceConfig _snowman6 = biome.getGenerationSettings().getSurfaceConfig();
        BlockState _snowman7 = _snowman6.getUnderMaterial();
        BlockState _snowman8 = _snowman6.getTopMaterial();
        BlockState _snowman9 = _snowman7;
        BlockState _snowman10 = _snowman8;
        int _snowman11 = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
        int _snowman12 = -1;
        int _snowman13 = 0;
        int _snowman14 = 2 + random.nextInt(4);
        int _snowman15 = n4 + 18 + random.nextInt(10);
        for (int i = Math.max(n3, (int)d2 + 1); i >= 0; --i) {
            _snowman2.set(_snowman4, i, _snowman5);
            if (chunk.getBlockState(_snowman2).isAir() && i < (int)d2 && random.nextDouble() > 0.01) {
                chunk.setBlockState(_snowman2, PACKED_ICE, false);
            } else if (chunk.getBlockState(_snowman2).getMaterial() == Material.WATER && i > (int)_snowman && i < n4 && _snowman != 0.0 && random.nextDouble() > 0.15) {
                chunk.setBlockState(_snowman2, PACKED_ICE, false);
            }
            BlockState blockState3 = chunk.getBlockState(_snowman2);
            if (blockState3.isAir()) {
                _snowman12 = -1;
                continue;
            }
            if (blockState3.isOf(blockState.getBlock())) {
                if (_snowman12 == -1) {
                    if (_snowman11 <= 0) {
                        _snowman10 = AIR;
                        _snowman9 = blockState;
                    } else if (i >= n4 - 4 && i <= n4 + 1) {
                        _snowman10 = _snowman8;
                        _snowman9 = _snowman7;
                    }
                    if (i < n4 && (_snowman10 == null || _snowman10.isAir())) {
                        _snowman10 = biome.getTemperature(_snowman2.set(n, i, n2)) < 0.15f ? ICE : blockState2;
                    }
                    _snowman12 = _snowman11;
                    if (i >= n4 - 1) {
                        chunk.setBlockState(_snowman2, _snowman10, false);
                        continue;
                    }
                    if (i < n4 - 7 - _snowman11) {
                        _snowman10 = AIR;
                        _snowman9 = blockState;
                        chunk.setBlockState(_snowman2, GRAVEL, false);
                        continue;
                    }
                    chunk.setBlockState(_snowman2, _snowman9, false);
                    continue;
                }
                if (_snowman12 <= 0) continue;
                chunk.setBlockState(_snowman2, _snowman9, false);
                if (--_snowman12 != 0 || !_snowman9.isOf(Blocks.SAND) || _snowman11 <= 1) continue;
                _snowman12 = random.nextInt(4) + Math.max(0, i - 63);
                _snowman9 = _snowman9.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
                continue;
            }
            if (!blockState3.isOf(Blocks.PACKED_ICE) || _snowman13 > _snowman14 || i <= _snowman15) continue;
            chunk.setBlockState(_snowman2, SNOW_BLOCK, false);
            ++_snowman13;
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.field_15644 == null || this.field_15642 == null) {
            ChunkRandom chunkRandom = new ChunkRandom(seed);
            this.field_15644 = new OctaveSimplexNoiseSampler(chunkRandom, IntStream.rangeClosed(-3, 0));
            this.field_15642 = new OctaveSimplexNoiseSampler(chunkRandom, (List<Integer>)ImmutableList.of((Object)0));
        }
        this.seed = seed;
    }
}

