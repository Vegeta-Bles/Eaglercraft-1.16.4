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
import net.minecraft.world.gen.surfacebuilder.BadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class ErodedBadlandsSurfaceBuilder
extends BadlandsSurfaceBuilder {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

    public ErodedBadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk2, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        double d2 = 0.0;
        _snowman = Math.min(Math.abs(d), this.heightCutoffNoise.sample((double)n * 0.25, (double)n2 * 0.25, false) * 15.0);
        if (_snowman > 0.0) {
            _snowman = 0.001953125;
            d2 = _snowman * _snowman * 2.5;
            _snowman = Math.abs(this.heightNoise.sample((double)n * 0.001953125, (double)n2 * 0.001953125, false));
            _snowman = Math.ceil(_snowman * 50.0) + 14.0;
            if (d2 > _snowman) {
                d2 = _snowman;
            }
            d2 += 64.0;
        }
        int _snowman2 = n & 0xF;
        int _snowman3 = n2 & 0xF;
        BlockState _snowman4 = WHITE_TERRACOTTA;
        SurfaceConfig _snowman5 = biome.getGenerationSettings().getSurfaceConfig();
        BlockState _snowman6 = _snowman5.getUnderMaterial();
        BlockState _snowman7 = _snowman5.getTopMaterial();
        BlockState _snowman8 = _snowman6;
        int _snowman9 = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
        boolean _snowman10 = Math.cos(d / 3.0 * Math.PI) > 0.0;
        int _snowman11 = -1;
        boolean _snowman12 = false;
        BlockPos.Mutable _snowman13 = new BlockPos.Mutable();
        for (int i = Math.max(n3, (int)d2 + 1); i >= 0; --i) {
            _snowman13.set(_snowman2, i, _snowman3);
            if (chunk2.getBlockState(_snowman13).isAir() && i < (int)d2) {
                chunk2.setBlockState(_snowman13, blockState, false);
            }
            if ((_snowman = chunk2.getBlockState(_snowman13)).isAir()) {
                _snowman11 = -1;
                continue;
            }
            if (!_snowman.isOf(blockState.getBlock())) continue;
            if (_snowman11 == -1) {
                Chunk chunk2;
                Object _snowman14;
                _snowman12 = false;
                if (_snowman9 <= 0) {
                    _snowman4 = Blocks.AIR.getDefaultState();
                    _snowman8 = blockState;
                } else if (i >= n4 - 4 && i <= n4 + 1) {
                    _snowman4 = WHITE_TERRACOTTA;
                    _snowman8 = _snowman6;
                }
                if (i < n4 && (_snowman4 == null || _snowman4.isAir())) {
                    _snowman4 = blockState2;
                }
                _snowman11 = _snowman9 + Math.max(0, i - n4);
                if (i >= n4 - 1) {
                    if (i > n4 + 3 + _snowman9) {
                        _snowman14 = i < 64 || i > 127 ? ORANGE_TERRACOTTA : (_snowman10 ? TERRACOTTA : this.calculateLayerBlockState(n, i, n2));
                        chunk2.setBlockState(_snowman13, (BlockState)_snowman14, false);
                        continue;
                    }
                    chunk2.setBlockState(_snowman13, _snowman7, false);
                    _snowman12 = true;
                    continue;
                }
                chunk2.setBlockState(_snowman13, _snowman8, false);
                _snowman14 = _snowman8.getBlock();
                if (_snowman14 != Blocks.WHITE_TERRACOTTA && _snowman14 != Blocks.ORANGE_TERRACOTTA && _snowman14 != Blocks.MAGENTA_TERRACOTTA && _snowman14 != Blocks.LIGHT_BLUE_TERRACOTTA && _snowman14 != Blocks.YELLOW_TERRACOTTA && _snowman14 != Blocks.LIME_TERRACOTTA && _snowman14 != Blocks.PINK_TERRACOTTA && _snowman14 != Blocks.GRAY_TERRACOTTA && _snowman14 != Blocks.LIGHT_GRAY_TERRACOTTA && _snowman14 != Blocks.CYAN_TERRACOTTA && _snowman14 != Blocks.PURPLE_TERRACOTTA && _snowman14 != Blocks.BLUE_TERRACOTTA && _snowman14 != Blocks.BROWN_TERRACOTTA && _snowman14 != Blocks.GREEN_TERRACOTTA && _snowman14 != Blocks.RED_TERRACOTTA && _snowman14 != Blocks.BLACK_TERRACOTTA) continue;
                chunk2.setBlockState(_snowman13, ORANGE_TERRACOTTA, false);
                continue;
            }
            if (_snowman11 <= 0) continue;
            --_snowman11;
            if (_snowman12) {
                chunk2.setBlockState(_snowman13, ORANGE_TERRACOTTA, false);
                continue;
            }
            chunk2.setBlockState(_snowman13, this.calculateLayerBlockState(n, i, n2), false);
        }
    }
}

