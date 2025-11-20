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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class GravellyMountainSurfaceBuilder
extends SurfaceBuilder<TernarySurfaceConfig> {
    public GravellyMountainSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l, TernarySurfaceConfig ternarySurfaceConfig) {
        if (d < -1.0 || d > 2.0) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, SurfaceBuilder.GRAVEL_CONFIG);
        } else if (d > 1.0) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, SurfaceBuilder.STONE_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, SurfaceBuilder.GRASS_CONFIG);
        }
    }
}

