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
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class SeaPickleFeature
extends Feature<CountConfig> {
    public SeaPickleFeature(Codec<CountConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, CountConfig countConfig) {
        int n = 0;
        _snowman = countConfig.getCount().getValue(random);
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            _snowman = random.nextInt(8) - random.nextInt(8);
            _snowman = random.nextInt(8) - random.nextInt(8);
            _snowman = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + _snowman, blockPos.getZ() + _snowman);
            BlockPos blockPos2 = new BlockPos(blockPos.getX() + _snowman, _snowman, blockPos.getZ() + _snowman);
            BlockState _snowman2 = (BlockState)Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, random.nextInt(4) + 1);
            if (!structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) || !_snowman2.canPlaceAt(structureWorldAccess, blockPos2)) continue;
            structureWorldAccess.setBlockState(blockPos2, _snowman2, 2);
            ++n;
        }
        return n > 0;
    }
}

