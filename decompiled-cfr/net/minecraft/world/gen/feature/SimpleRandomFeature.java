/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;

public class SimpleRandomFeature
extends Feature<SimpleRandomFeatureConfig> {
    public SimpleRandomFeature(Codec<SimpleRandomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, SimpleRandomFeatureConfig simpleRandomFeatureConfig) {
        int n = random.nextInt(simpleRandomFeatureConfig.features.size());
        ConfiguredFeature<?, ?> _snowman2 = simpleRandomFeatureConfig.features.get(n).get();
        return _snowman2.generate(structureWorldAccess, chunkGenerator, random, blockPos);
    }
}

