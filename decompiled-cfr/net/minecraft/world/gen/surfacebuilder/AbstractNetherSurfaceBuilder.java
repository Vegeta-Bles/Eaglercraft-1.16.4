/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public abstract class AbstractNetherSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    private long seed;
    private ImmutableMap<BlockState, OctavePerlinNoiseSampler> surfaceNoises = ImmutableMap.of();
    private ImmutableMap<BlockState, OctavePerlinNoiseSampler> underLavaNoises = ImmutableMap.of();
    private OctavePerlinNoiseSampler shoreNoise;

    public AbstractNetherSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState22, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        int n5 = n4 + 1;
        n7 = n & 0xF;
        _snowman = n2 & 0xF;
        _snowman = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
        _snowman = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
        double _snowman2 = 0.03125;
        boolean _snowman3 = this.shoreNoise.sample((double)n * 0.03125, 109.0, (double)n2 * 0.03125) * 75.0 + random.nextDouble() > 0.0;
        BlockState _snowman4 = (BlockState)this.underLavaNoises.entrySet().stream().max(Comparator.comparing(entry -> ((OctavePerlinNoiseSampler)entry.getValue()).sample(n, n4, n2))).get().getKey();
        BlockState _snowman5 = (BlockState)this.surfaceNoises.entrySet().stream().max(Comparator.comparing(entry -> ((OctavePerlinNoiseSampler)entry.getValue()).sample(n, n4, n2))).get().getKey();
        BlockPos.Mutable _snowman6 = new BlockPos.Mutable();
        BlockState _snowman7 = chunk.getBlockState(_snowman6.set(n7, 128, _snowman));
        for (_snowman = 127; _snowman >= 0; --_snowman) {
            BlockState blockState22;
            int n6;
            _snowman6.set(n7, _snowman, _snowman);
            BlockState blockState3 = chunk.getBlockState(_snowman6);
            if (_snowman7.isOf(blockState.getBlock()) && (blockState3.isAir() || blockState3 == blockState22)) {
                int n7;
                for (n6 = 0; n6 < _snowman; ++n6) {
                    _snowman6.move(Direction.UP);
                    if (!chunk.getBlockState(_snowman6).isOf(blockState.getBlock())) break;
                    chunk.setBlockState(_snowman6, _snowman4, false);
                }
                _snowman6.set(n7, _snowman, _snowman);
            }
            if ((_snowman7.isAir() || _snowman7 == blockState22) && blockState3.isOf(blockState.getBlock())) {
                for (n6 = 0; n6 < _snowman && chunk.getBlockState(_snowman6).isOf(blockState.getBlock()); ++n6) {
                    if (_snowman3 && _snowman >= n5 - 4 && _snowman <= n5 + 1) {
                        chunk.setBlockState(_snowman6, this.getLavaShoreState(), false);
                    } else {
                        chunk.setBlockState(_snowman6, _snowman5, false);
                    }
                    _snowman6.move(Direction.DOWN);
                }
            }
            _snowman7 = blockState3;
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.shoreNoise == null || this.surfaceNoises.isEmpty() || this.underLavaNoises.isEmpty()) {
            this.surfaceNoises = AbstractNetherSurfaceBuilder.createNoisesForStates(this.getSurfaceStates(), seed);
            this.underLavaNoises = AbstractNetherSurfaceBuilder.createNoisesForStates(this.getUnderLavaStates(), seed + (long)this.surfaceNoises.size());
            this.shoreNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed + (long)this.surfaceNoises.size() + (long)this.underLavaNoises.size()), (List<Integer>)ImmutableList.of((Object)0));
        }
        this.seed = seed;
    }

    private static ImmutableMap<BlockState, OctavePerlinNoiseSampler> createNoisesForStates(ImmutableList<BlockState> states, long seed) {
        ImmutableMap.Builder builder = new ImmutableMap.Builder();
        for (BlockState blockState : states) {
            builder.put((Object)blockState, (Object)new OctavePerlinNoiseSampler(new ChunkRandom(seed), (List<Integer>)ImmutableList.of((Object)-4)));
            ++seed;
        }
        return builder.build();
    }

    protected abstract ImmutableList<BlockState> getSurfaceStates();

    protected abstract ImmutableList<BlockState> getUnderLavaStates();

    protected abstract BlockState getLavaShoreState();
}

