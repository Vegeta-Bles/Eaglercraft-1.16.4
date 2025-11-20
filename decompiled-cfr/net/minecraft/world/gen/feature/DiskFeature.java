/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class DiskFeature
extends Feature<DiskFeatureConfig> {
    public DiskFeature(Codec<DiskFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DiskFeatureConfig diskFeatureConfig) {
        boolean bl = false;
        int _snowman2 = diskFeatureConfig.radius.getValue(random);
        for (int i = blockPos.getX() - _snowman2; i <= blockPos.getX() + _snowman2; ++i) {
            for (_snowman = blockPos.getZ() - _snowman2; _snowman <= blockPos.getZ() + _snowman2; ++_snowman) {
                _snowman = i - blockPos.getX();
                if (_snowman * _snowman + (_snowman = _snowman - blockPos.getZ()) * _snowman > _snowman2 * _snowman2) continue;
                block2: for (_snowman = blockPos.getY() - diskFeatureConfig.halfHeight; _snowman <= blockPos.getY() + diskFeatureConfig.halfHeight; ++_snowman) {
                    BlockPos blockPos2 = new BlockPos(i, _snowman, _snowman);
                    Block _snowman3 = structureWorldAccess.getBlockState(blockPos2).getBlock();
                    for (BlockState blockState : diskFeatureConfig.targets) {
                        if (!blockState.isOf(_snowman3)) continue;
                        structureWorldAccess.setBlockState(blockPos2, diskFeatureConfig.state, 2);
                        bl = true;
                        continue block2;
                    }
                }
            }
        }
        return bl;
    }
}

