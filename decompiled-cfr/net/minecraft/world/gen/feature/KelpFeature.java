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
import net.minecraft.block.KelpBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class KelpFeature
extends Feature<DefaultFeatureConfig> {
    public KelpFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        int n = 0;
        _snowman = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
        BlockPos _snowman2 = new BlockPos(blockPos.getX(), _snowman, blockPos.getZ());
        if (structureWorldAccess.getBlockState(_snowman2).isOf(Blocks.WATER)) {
            BlockState blockState = Blocks.KELP.getDefaultState();
            _snowman = Blocks.KELP_PLANT.getDefaultState();
            int _snowman3 = 1 + random.nextInt(10);
            for (int i = 0; i <= _snowman3; ++i) {
                if (structureWorldAccess.getBlockState(_snowman2).isOf(Blocks.WATER) && structureWorldAccess.getBlockState(_snowman2.up()).isOf(Blocks.WATER) && _snowman.canPlaceAt(structureWorldAccess, _snowman2)) {
                    if (i == _snowman3) {
                        structureWorldAccess.setBlockState(_snowman2, (BlockState)blockState.with(KelpBlock.AGE, random.nextInt(4) + 20), 2);
                        ++n;
                    } else {
                        structureWorldAccess.setBlockState(_snowman2, _snowman, 2);
                    }
                } else if (i > 0) {
                    BlockPos blockPos2 = _snowman2.down();
                    if (!blockState.canPlaceAt(structureWorldAccess, blockPos2) || structureWorldAccess.getBlockState(blockPos2.down()).isOf(Blocks.KELP)) break;
                    structureWorldAccess.setBlockState(blockPos2, (BlockState)blockState.with(KelpBlock.AGE, random.nextInt(4) + 20), 2);
                    ++n;
                    break;
                }
                _snowman2 = _snowman2.up();
            }
        }
        return n > 0;
    }
}

