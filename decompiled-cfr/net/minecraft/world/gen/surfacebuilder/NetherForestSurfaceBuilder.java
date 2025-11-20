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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class NetherForestSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    protected long seed;
    private OctavePerlinNoiseSampler surfaceNoise;

    public NetherForestSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig2) {
        int n5 = n4;
        _snowman = n & 0xF;
        _snowman = n2 & 0xF;
        double _snowman2 = this.surfaceNoise.sample((double)n * 0.1, n4, (double)n2 * 0.1);
        boolean _snowman3 = _snowman2 > 0.15 + random.nextDouble() * 0.35;
        double _snowman4 = this.surfaceNoise.sample((double)n * 0.1, 109.0, (double)n2 * 0.1);
        boolean _snowman5 = _snowman4 > 0.25 + random.nextDouble() * 0.9;
        _snowman = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
        BlockPos.Mutable _snowman6 = new BlockPos.Mutable();
        n6 = -1;
        BlockState _snowman7 = ternarySurfaceConfig2.getUnderMaterial();
        for (_snowman = 127; _snowman >= 0; --_snowman) {
            BlockState blockState3;
            int n6;
            _snowman6.set(_snowman, _snowman, _snowman);
            BlockState _snowman8 = ternarySurfaceConfig2.getTopMaterial();
            blockState3 = chunk.getBlockState(_snowman6);
            if (blockState3.isAir()) {
                n6 = -1;
                continue;
            }
            if (!blockState3.isOf(blockState.getBlock())) continue;
            if (n6 == -1) {
                boolean bl = false;
                if (_snowman <= 0) {
                    bl = true;
                    _snowman7 = ternarySurfaceConfig2.getUnderMaterial();
                }
                if (_snowman3) {
                    _snowman8 = ternarySurfaceConfig2.getUnderMaterial();
                } else if (_snowman5) {
                    TernarySurfaceConfig ternarySurfaceConfig2;
                    _snowman8 = ternarySurfaceConfig2.getUnderwaterMaterial();
                }
                if (_snowman < n5 && bl) {
                    _snowman8 = blockState2;
                }
                n6 = _snowman;
                if (_snowman >= n5 - 1) {
                    chunk.setBlockState(_snowman6, _snowman8, false);
                    continue;
                }
                chunk.setBlockState(_snowman6, _snowman7, false);
                continue;
            }
            if (n6 <= 0) continue;
            --n6;
            chunk.setBlockState(_snowman6, _snowman7, false);
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.surfaceNoise == null) {
            this.surfaceNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed), (List<Integer>)ImmutableList.of((Object)0));
        }
        this.seed = seed;
    }
}

