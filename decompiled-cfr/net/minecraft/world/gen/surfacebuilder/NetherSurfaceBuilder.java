/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class NetherSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState GLOWSTONE = Blocks.SOUL_SAND.getDefaultState();
    protected long seed;
    protected OctavePerlinNoiseSampler noise;

    public NetherSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        int n5 = n4;
        _snowman = n & 0xF;
        _snowman = n2 & 0xF;
        double _snowman2 = 0.03125;
        boolean _snowman3 = this.noise.sample((double)n * 0.03125, (double)n2 * 0.03125, 0.0) * 75.0 + random.nextDouble() > 0.0;
        boolean _snowman4 = this.noise.sample((double)n * 0.03125, 109.0, (double)n2 * 0.03125) * 75.0 + random.nextDouble() > 0.0;
        _snowman = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
        BlockPos.Mutable _snowman5 = new BlockPos.Mutable();
        _snowman8 = -1;
        BlockState _snowman6 = ternarySurfaceConfig.getTopMaterial();
        BlockState _snowman7 = ternarySurfaceConfig.getUnderMaterial();
        for (_snowman = 127; _snowman >= 0; --_snowman) {
            int _snowman8;
            _snowman5.set(_snowman, _snowman, _snowman);
            BlockState blockState3 = chunk.getBlockState(_snowman5);
            if (blockState3.isAir()) {
                _snowman8 = -1;
                continue;
            }
            if (!blockState3.isOf(blockState.getBlock())) continue;
            if (_snowman8 == -1) {
                boolean bl = false;
                if (_snowman <= 0) {
                    bl = true;
                    _snowman7 = ternarySurfaceConfig.getUnderMaterial();
                } else if (_snowman >= n5 - 4 && _snowman <= n5 + 1) {
                    _snowman6 = ternarySurfaceConfig.getTopMaterial();
                    _snowman7 = ternarySurfaceConfig.getUnderMaterial();
                    if (_snowman4) {
                        _snowman6 = GRAVEL;
                        _snowman7 = ternarySurfaceConfig.getUnderMaterial();
                    }
                    if (_snowman3) {
                        _snowman6 = GLOWSTONE;
                        _snowman7 = GLOWSTONE;
                    }
                }
                if (_snowman < n5 && bl) {
                    _snowman6 = blockState2;
                }
                _snowman8 = _snowman;
                if (_snowman >= n5 - 1) {
                    chunk.setBlockState(_snowman5, _snowman6, false);
                    continue;
                }
                chunk.setBlockState(_snowman5, _snowman7, false);
                continue;
            }
            if (_snowman8 <= 0) continue;
            --_snowman8;
            chunk.setBlockState(_snowman5, _snowman7, false);
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.noise == null) {
            this.noise = new OctavePerlinNoiseSampler(new ChunkRandom(seed), IntStream.rangeClosed(-3, 0));
        }
        this.seed = seed;
    }
}

