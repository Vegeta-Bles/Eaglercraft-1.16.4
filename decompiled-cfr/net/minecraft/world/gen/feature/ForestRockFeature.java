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
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;

public class ForestRockFeature
extends Feature<SingleStateFeatureConfig> {
    public ForestRockFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos2, SingleStateFeatureConfig singleStateFeatureConfig) {
        BlockPos blockPos2;
        while (blockPos2.getY() > 3 && (structureWorldAccess.isAir(blockPos2.down()) || !ForestRockFeature.isSoil(_snowman = structureWorldAccess.getBlockState(blockPos2.down()).getBlock()) && !ForestRockFeature.isStone(_snowman))) {
            blockPos2 = blockPos2.down();
        }
        if (blockPos2.getY() <= 3) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            _snowman = random.nextInt(2);
            _snowman = random.nextInt(2);
            _snowman = random.nextInt(2);
            float f = (float)(_snowman + _snowman + _snowman) * 0.333f + 0.5f;
            for (BlockPos blockPos3 : BlockPos.iterate(blockPos2.add(-_snowman, -_snowman, -_snowman), blockPos2.add(_snowman, _snowman, _snowman))) {
                if (!(blockPos3.getSquaredDistance(blockPos2) <= (double)(f * f))) continue;
                structureWorldAccess.setBlockState(blockPos3, singleStateFeatureConfig.state, 4);
            }
            blockPos2 = blockPos2.add(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
        }
        return true;
    }
}

