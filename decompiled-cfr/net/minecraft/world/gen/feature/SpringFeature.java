/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SpringFeatureConfig;

public class SpringFeature
extends Feature<SpringFeatureConfig> {
    public SpringFeature(Codec<SpringFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, SpringFeatureConfig springFeatureConfig) {
        if (!springFeatureConfig.validBlocks.contains(structureWorldAccess.getBlockState(blockPos.up()).getBlock())) {
            return false;
        }
        if (springFeatureConfig.requiresBlockBelow && !springFeatureConfig.validBlocks.contains(structureWorldAccess.getBlockState(blockPos.down()).getBlock())) {
            return false;
        }
        BlockState blockState = structureWorldAccess.getBlockState(blockPos);
        if (!blockState.isAir() && !springFeatureConfig.validBlocks.contains(blockState.getBlock())) {
            return false;
        }
        int _snowman2 = 0;
        int _snowman3 = 0;
        if (springFeatureConfig.validBlocks.contains(structureWorldAccess.getBlockState(blockPos.west()).getBlock())) {
            ++_snowman3;
        }
        if (springFeatureConfig.validBlocks.contains(structureWorldAccess.getBlockState(blockPos.east()).getBlock())) {
            ++_snowman3;
        }
        if (springFeatureConfig.validBlocks.contains(structureWorldAccess.getBlockState(blockPos.north()).getBlock())) {
            ++_snowman3;
        }
        if (springFeatureConfig.validBlocks.contains(structureWorldAccess.getBlockState(blockPos.south()).getBlock())) {
            ++_snowman3;
        }
        if (springFeatureConfig.validBlocks.contains(structureWorldAccess.getBlockState(blockPos.down()).getBlock())) {
            ++_snowman3;
        }
        int _snowman4 = 0;
        if (structureWorldAccess.isAir(blockPos.west())) {
            ++_snowman4;
        }
        if (structureWorldAccess.isAir(blockPos.east())) {
            ++_snowman4;
        }
        if (structureWorldAccess.isAir(blockPos.north())) {
            ++_snowman4;
        }
        if (structureWorldAccess.isAir(blockPos.south())) {
            ++_snowman4;
        }
        if (structureWorldAccess.isAir(blockPos.down())) {
            ++_snowman4;
        }
        if (_snowman3 == springFeatureConfig.rockCount && _snowman4 == springFeatureConfig.holeCount) {
            structureWorldAccess.setBlockState(blockPos, springFeatureConfig.state.getBlockState(), 2);
            structureWorldAccess.getFluidTickScheduler().schedule(blockPos, springFeatureConfig.state.getFluid(), 0);
            ++_snowman2;
        }
        return _snowman2 > 0;
    }
}

