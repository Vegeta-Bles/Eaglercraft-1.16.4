/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class DefaultSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    public DefaultSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        this.generate(random, chunk, biome, n, n2, n3, d, blockState, blockState2, ternarySurfaceConfig.getTopMaterial(), ternarySurfaceConfig.getUnderMaterial(), ternarySurfaceConfig.getUnderwaterMaterial(), n4);
    }

    protected void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState fluidBlock, BlockState topBlock, BlockState underBlock, BlockState underwaterBlock, int seaLevel) {
        BlockState blockState = topBlock;
        _snowman = underBlock;
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        int _snowman3 = -1;
        int _snowman4 = (int)(noise / 3.0 + 3.0 + random.nextDouble() * 0.25);
        int _snowman5 = x & 0xF;
        int _snowman6 = z & 0xF;
        for (int i = height; i >= 0; --i) {
            _snowman2.set(_snowman5, i, _snowman6);
            BlockState blockState2 = chunk.getBlockState(_snowman2);
            if (blockState2.isAir()) {
                _snowman3 = -1;
                continue;
            }
            if (!blockState2.isOf(defaultBlock.getBlock())) continue;
            if (_snowman3 == -1) {
                if (_snowman4 <= 0) {
                    blockState = Blocks.AIR.getDefaultState();
                    _snowman = defaultBlock;
                } else if (i >= seaLevel - 4 && i <= seaLevel + 1) {
                    blockState = topBlock;
                    _snowman = underBlock;
                }
                if (i < seaLevel && (blockState == null || blockState.isAir())) {
                    blockState = biome.getTemperature(_snowman2.set(x, i, z)) < 0.15f ? Blocks.ICE.getDefaultState() : fluidBlock;
                    _snowman2.set(_snowman5, i, _snowman6);
                }
                _snowman3 = _snowman4;
                if (i >= seaLevel - 1) {
                    chunk.setBlockState(_snowman2, blockState, false);
                    continue;
                }
                if (i < seaLevel - 7 - _snowman4) {
                    blockState = Blocks.AIR.getDefaultState();
                    _snowman = defaultBlock;
                    chunk.setBlockState(_snowman2, underwaterBlock, false);
                    continue;
                }
                chunk.setBlockState(_snowman2, _snowman, false);
                continue;
            }
            if (_snowman3 <= 0) continue;
            chunk.setBlockState(_snowman2, _snowman, false);
            if (--_snowman3 != 0 || !_snowman.isOf(Blocks.SAND) || _snowman4 <= 1) continue;
            _snowman3 = random.nextInt(4) + Math.max(0, i - 63);
            _snowman = _snowman.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
        }
    }
}

