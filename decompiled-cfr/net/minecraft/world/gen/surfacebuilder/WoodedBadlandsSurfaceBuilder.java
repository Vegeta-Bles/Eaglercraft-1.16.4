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

public class WoodedBadlandsSurfaceBuilder
extends BadlandsSurfaceBuilder {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

    public WoodedBadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
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
                    if (_snowman > 86 + _snowman * 2) {
                        if (_snowman7) {
                            chunk2.setBlockState(_snowman9, Blocks.COARSE_DIRT.getDefaultState(), false);
                        } else {
                            chunk2.setBlockState(_snowman9, Blocks.GRASS_BLOCK.getDefaultState(), false);
                        }
                    } else if (_snowman > n4 + 3 + _snowman) {
                        BlockState blockState4 = _snowman < 64 || _snowman > 127 ? ORANGE_TERRACOTTA : (_snowman7 ? TERRACOTTA : this.calculateLayerBlockState(n, _snowman, n2));
                        chunk2.setBlockState(_snowman9, blockState4, false);
                    } else {
                        chunk2.setBlockState(_snowman9, _snowman5, false);
                        _snowman8 = true;
                    }
                } else {
                    chunk2.setBlockState(_snowman9, _snowman6, false);
                    if (_snowman6 == WHITE_TERRACOTTA) {
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
}

