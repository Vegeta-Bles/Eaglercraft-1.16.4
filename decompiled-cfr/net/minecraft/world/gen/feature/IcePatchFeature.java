/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DiskFeature;
import net.minecraft.world.gen.feature.DiskFeatureConfig;

public class IcePatchFeature
extends DiskFeature {
    public IcePatchFeature(Codec<DiskFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos2, DiskFeatureConfig diskFeatureConfig) {
        StructureWorldAccess structureWorldAccess2;
        while (structureWorldAccess2.isAir(blockPos2) && blockPos2.getY() > 2) {
            BlockPos blockPos2 = blockPos2.down();
        }
        if (!structureWorldAccess2.getBlockState(blockPos2).isOf(Blocks.SNOW_BLOCK)) {
            return false;
        }
        return super.generate(structureWorldAccess2, chunkGenerator, random, blockPos2, diskFeatureConfig);
    }
}

