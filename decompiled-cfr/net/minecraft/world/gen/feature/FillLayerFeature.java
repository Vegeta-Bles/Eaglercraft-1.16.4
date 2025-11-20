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
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FillLayerFeatureConfig;

public class FillLayerFeature
extends Feature<FillLayerFeatureConfig> {
    public FillLayerFeature(Codec<FillLayerFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, FillLayerFeatureConfig fillLayerFeatureConfig) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                _snowman = blockPos.getX() + i;
                _snowman = blockPos.getZ() + _snowman;
                _snowman = fillLayerFeatureConfig.height;
                mutable.set(_snowman, _snowman, _snowman);
                if (!structureWorldAccess.getBlockState(mutable).isAir()) continue;
                structureWorldAccess.setBlockState(mutable, fillLayerFeatureConfig.state, 2);
            }
        }
        return true;
    }
}

