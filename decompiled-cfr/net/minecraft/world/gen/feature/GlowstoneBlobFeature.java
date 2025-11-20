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
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class GlowstoneBlobFeature
extends Feature<DefaultFeatureConfig> {
    public GlowstoneBlobFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        if (!structureWorldAccess2.isAir(blockPos)) {
            return false;
        }
        BlockState blockState = structureWorldAccess2.getBlockState(blockPos.up());
        if (!(blockState.isOf(Blocks.NETHERRACK) || blockState.isOf(Blocks.BASALT) || blockState.isOf(Blocks.BLACKSTONE))) {
            return false;
        }
        structureWorldAccess2.setBlockState(blockPos, Blocks.GLOWSTONE.getDefaultState(), 2);
        for (int i = 0; i < 1500; ++i) {
            StructureWorldAccess structureWorldAccess2;
            BlockPos blockPos2 = blockPos.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
            if (!structureWorldAccess2.getBlockState(blockPos2).isAir()) continue;
            int _snowman2 = 0;
            for (Direction direction : Direction.values()) {
                if (structureWorldAccess2.getBlockState(blockPos2.offset(direction)).isOf(Blocks.GLOWSTONE)) {
                    ++_snowman2;
                }
                if (_snowman2 > 1) break;
            }
            if (_snowman2 != true) continue;
            structureWorldAccess2.setBlockState(blockPos2, Blocks.GLOWSTONE.getDefaultState(), 2);
        }
        return true;
    }
}

