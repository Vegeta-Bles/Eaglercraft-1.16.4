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
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class BadlandsSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();
    private static final BlockState YELLOW_TERRACOTTA = Blocks.YELLOW_TERRACOTTA.getDefaultState();
    private static final BlockState BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.getDefaultState();
    private static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.getDefaultState();
    private static final BlockState LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
    protected BlockState[] layerBlocks;
    protected long seed;
    protected OctaveSimplexNoiseSampler heightCutoffNoise;
    protected OctaveSimplexNoiseSampler heightNoise;
    protected OctaveSimplexNoiseSampler layerNoise;

    public BadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk2, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        int n5 = n & 0xF;
        _snowman = n2 & 0xF;
        BlockState _snowman2 = WHITE_TERRACOTTA;
        SurfaceConfig _snowman3 = biome.getGenerationSettings().getSurfaceConfig();
        BlockState _snowman4 = _snowman3.getUnderMaterial();
        BlockState _snowman5 = _snowman3.getTopMaterial();
        BlockState _snowman6 = _snowman4;
        _snowman = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
        boolean _snowman7 = Math.cos(d / 3.0 * Math.PI) > 0.0;
        n6 = -1;
        boolean _snowman8 = false;
        _snowman = 0;
        BlockPos.Mutable _snowman9 = new BlockPos.Mutable();
        for (_snowman = n3; _snowman >= 0; --_snowman) {
            int n6;
            if (_snowman >= 15) continue;
            _snowman9.set(n5, _snowman, _snowman);
            BlockState blockState3 = chunk2.getBlockState(_snowman9);
            if (blockState3.isAir()) {
                n6 = -1;
                continue;
            }
            if (!blockState3.isOf(blockState.getBlock())) continue;
            if (n6 == -1) {
                Chunk chunk2;
                Object _snowman10;
                _snowman8 = false;
                if (_snowman <= 0) {
                    _snowman2 = Blocks.AIR.getDefaultState();
                    _snowman6 = blockState;
                } else if (_snowman >= n4 - 4 && _snowman <= n4 + 1) {
                    _snowman2 = WHITE_TERRACOTTA;
                    _snowman6 = _snowman4;
                }
                if (_snowman < n4 && (_snowman2 == null || _snowman2.isAir())) {
                    _snowman2 = blockState2;
                }
                n6 = _snowman + Math.max(0, _snowman - n4);
                if (_snowman >= n4 - 1) {
                    if (_snowman > n4 + 3 + _snowman) {
                        _snowman10 = _snowman < 64 || _snowman > 127 ? ORANGE_TERRACOTTA : (_snowman7 ? TERRACOTTA : this.calculateLayerBlockState(n, _snowman, n2));
                        chunk2.setBlockState(_snowman9, (BlockState)_snowman10, false);
                    } else {
                        chunk2.setBlockState(_snowman9, _snowman5, false);
                        _snowman8 = true;
                    }
                } else {
                    chunk2.setBlockState(_snowman9, _snowman6, false);
                    _snowman10 = _snowman6.getBlock();
                    if (_snowman10 == Blocks.WHITE_TERRACOTTA || _snowman10 == Blocks.ORANGE_TERRACOTTA || _snowman10 == Blocks.MAGENTA_TERRACOTTA || _snowman10 == Blocks.LIGHT_BLUE_TERRACOTTA || _snowman10 == Blocks.YELLOW_TERRACOTTA || _snowman10 == Blocks.LIME_TERRACOTTA || _snowman10 == Blocks.PINK_TERRACOTTA || _snowman10 == Blocks.GRAY_TERRACOTTA || _snowman10 == Blocks.LIGHT_GRAY_TERRACOTTA || _snowman10 == Blocks.CYAN_TERRACOTTA || _snowman10 == Blocks.PURPLE_TERRACOTTA || _snowman10 == Blocks.BLUE_TERRACOTTA || _snowman10 == Blocks.BROWN_TERRACOTTA || _snowman10 == Blocks.GREEN_TERRACOTTA || _snowman10 == Blocks.RED_TERRACOTTA || _snowman10 == Blocks.BLACK_TERRACOTTA) {
                        chunk2.setBlockState(_snowman9, ORANGE_TERRACOTTA, false);
                    }
                }
            } else if (n6 > 0) {
                --n6;
                if (_snowman8) {
                    chunk2.setBlockState(_snowman9, ORANGE_TERRACOTTA, false);
                } else {
                    chunk2.setBlockState(_snowman9, this.calculateLayerBlockState(n, _snowman, n2), false);
                }
            }
            ++_snowman;
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.layerBlocks == null) {
            this.initLayerBlocks(seed);
        }
        if (this.seed != seed || this.heightCutoffNoise == null || this.heightNoise == null) {
            ChunkRandom chunkRandom = new ChunkRandom(seed);
            this.heightCutoffNoise = new OctaveSimplexNoiseSampler(chunkRandom, IntStream.rangeClosed(-3, 0));
            this.heightNoise = new OctaveSimplexNoiseSampler(chunkRandom, (List<Integer>)ImmutableList.of((Object)0));
        }
        this.seed = seed;
    }

    protected void initLayerBlocks(long seed) {
        int _snowman5;
        int _snowman4;
        int _snowman3;
        int _snowman2;
        this.layerBlocks = new BlockState[64];
        Arrays.fill(this.layerBlocks, TERRACOTTA);
        ChunkRandom chunkRandom = new ChunkRandom(seed);
        this.layerNoise = new OctaveSimplexNoiseSampler(chunkRandom, (List<Integer>)ImmutableList.of((Object)0));
        for (_snowman2 = 0; _snowman2 < 64; ++_snowman2) {
            if ((_snowman2 += chunkRandom.nextInt(5) + 1) >= 64) continue;
            this.layerBlocks[_snowman2] = ORANGE_TERRACOTTA;
        }
        _snowman2 = chunkRandom.nextInt(4) + 2;
        for (_snowman3 = 0; _snowman3 < _snowman2; ++_snowman3) {
            _snowman4 = chunkRandom.nextInt(3) + 1;
            _snowman5 = chunkRandom.nextInt(64);
            for (_snowman6 = 0; _snowman5 + _snowman6 < 64 && _snowman6 < _snowman4; ++_snowman6) {
                this.layerBlocks[_snowman5 + _snowman6] = YELLOW_TERRACOTTA;
            }
        }
        _snowman3 = chunkRandom.nextInt(4) + 2;
        for (_snowman4 = 0; _snowman4 < _snowman3; ++_snowman4) {
            _snowman5 = chunkRandom.nextInt(3) + 2;
            _snowman6 = chunkRandom.nextInt(64);
            for (i = 0; _snowman6 + i < 64 && i < _snowman5; ++i) {
                this.layerBlocks[_snowman6 + i] = BROWN_TERRACOTTA;
            }
        }
        _snowman4 = chunkRandom.nextInt(4) + 2;
        for (_snowman5 = 0; _snowman5 < _snowman4; ++_snowman5) {
            _snowman6 = chunkRandom.nextInt(3) + 1;
            i = chunkRandom.nextInt(64);
            for (_snowman = 0; i + _snowman < 64 && _snowman < _snowman6; ++_snowman) {
                this.layerBlocks[i + _snowman] = RED_TERRACOTTA;
            }
        }
        _snowman5 = chunkRandom.nextInt(3) + 3;
        int _snowman6 = 0;
        for (int i = 0; i < _snowman5; ++i) {
            _snowman = 1;
            _snowman6 += chunkRandom.nextInt(16) + 4;
            for (_snowman = 0; _snowman6 + _snowman < 64 && _snowman < 1; ++_snowman) {
                this.layerBlocks[_snowman6 + _snowman] = WHITE_TERRACOTTA;
                if (_snowman6 + _snowman > 1 && chunkRandom.nextBoolean()) {
                    this.layerBlocks[_snowman6 + _snowman - 1] = LIGHT_GRAY_TERRACOTTA;
                }
                if (_snowman6 + _snowman >= 63 || !chunkRandom.nextBoolean()) continue;
                this.layerBlocks[_snowman6 + _snowman + 1] = LIGHT_GRAY_TERRACOTTA;
            }
        }
    }

    protected BlockState calculateLayerBlockState(int x, int y, int z) {
        int n = (int)Math.round(this.layerNoise.sample((double)x / 512.0, (double)z / 512.0, false) * 2.0);
        return this.layerBlocks[(y + n + 64) % 64];
    }
}

