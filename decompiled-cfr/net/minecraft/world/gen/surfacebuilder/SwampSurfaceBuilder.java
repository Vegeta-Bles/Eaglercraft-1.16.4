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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class SwampSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    public SwampSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        double d2 = Biome.FOLIAGE_NOISE.sample((double)n * 0.25, (double)n2 * 0.25, false);
        if (d2 > 0.0) {
            int n5 = n & 0xF;
            _snowman = n2 & 0xF;
            BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
            for (_snowman = n3; _snowman >= 0; --_snowman) {
                _snowman2.set(n5, _snowman, _snowman);
                if (chunk.getBlockState(_snowman2).isAir()) continue;
                if (_snowman != 62 || chunk.getBlockState(_snowman2).isOf(blockState2.getBlock())) break;
                chunk.setBlockState(_snowman2, blockState2, false);
                break;
            }
        }
        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, ternarySurfaceConfig);
    }
}

