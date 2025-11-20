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
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class SeagrassFeature
extends Feature<ProbabilityConfig> {
    public SeagrassFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, ProbabilityConfig probabilityConfig) {
        boolean bl = false;
        int _snowman2 = random.nextInt(8) - random.nextInt(8);
        int _snowman3 = random.nextInt(8) - random.nextInt(8);
        int _snowman4 = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + _snowman2, blockPos.getZ() + _snowman3);
        BlockPos _snowman5 = new BlockPos(blockPos.getX() + _snowman2, _snowman4, blockPos.getZ() + _snowman3);
        if (structureWorldAccess.getBlockState(_snowman5).isOf(Blocks.WATER)) {
            _snowman = random.nextDouble() < (double)probabilityConfig.probability;
            BlockState blockState = _snowman = _snowman ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
            if (_snowman.canPlaceAt(structureWorldAccess, _snowman5)) {
                if (_snowman) {
                    BlockState blockState2 = (BlockState)_snowman.with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
                    BlockPos _snowman6 = _snowman5.up();
                    if (structureWorldAccess.getBlockState(_snowman6).isOf(Blocks.WATER)) {
                        structureWorldAccess.setBlockState(_snowman5, _snowman, 2);
                        structureWorldAccess.setBlockState(_snowman6, blockState2, 2);
                    }
                } else {
                    structureWorldAccess.setBlockState(_snowman5, _snowman, 2);
                }
                bl = true;
            }
        }
        return bl;
    }
}

