/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class BambooFeature
extends Feature<ProbabilityConfig> {
    private static final BlockState BAMBOO = (BlockState)((BlockState)((BlockState)Blocks.BAMBOO.getDefaultState().with(BambooBlock.AGE, 1)).with(BambooBlock.LEAVES, BambooLeaves.NONE)).with(BambooBlock.STAGE, 0);
    private static final BlockState BAMBOO_TOP_1 = (BlockState)((BlockState)BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE)).with(BambooBlock.STAGE, 1);
    private static final BlockState BAMBOO_TOP_2 = (BlockState)BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE);
    private static final BlockState BAMBOO_TOP_3 = (BlockState)BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.SMALL);

    public BambooFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, ProbabilityConfig probabilityConfig) {
        int n = 0;
        BlockPos.Mutable _snowman2 = blockPos.mutableCopy();
        BlockPos.Mutable _snowman3 = blockPos.mutableCopy();
        if (structureWorldAccess.isAir(_snowman2)) {
            if (Blocks.BAMBOO.getDefaultState().canPlaceAt(structureWorldAccess, _snowman2)) {
                _snowman = random.nextInt(12) + 5;
                if (random.nextFloat() < probabilityConfig.probability) {
                    _snowman = random.nextInt(4) + 1;
                    for (_snowman = blockPos.getX() - _snowman; _snowman <= blockPos.getX() + _snowman; ++_snowman) {
                        for (_snowman = blockPos.getZ() - _snowman; _snowman <= blockPos.getZ() + _snowman; ++_snowman) {
                            _snowman = _snowman - blockPos.getX();
                            if (_snowman * _snowman + (_snowman = _snowman - blockPos.getZ()) * _snowman > _snowman * _snowman) continue;
                            _snowman3.set(_snowman, structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE, _snowman, _snowman) - 1, _snowman);
                            if (!BambooFeature.isSoil(structureWorldAccess.getBlockState(_snowman3).getBlock())) continue;
                            structureWorldAccess.setBlockState(_snowman3, Blocks.PODZOL.getDefaultState(), 2);
                        }
                    }
                }
                for (_snowman = 0; _snowman < _snowman && structureWorldAccess.isAir(_snowman2); ++_snowman) {
                    structureWorldAccess.setBlockState(_snowman2, BAMBOO, 2);
                    _snowman2.move(Direction.UP, 1);
                }
                if (_snowman2.getY() - blockPos.getY() >= 3) {
                    structureWorldAccess.setBlockState(_snowman2, BAMBOO_TOP_1, 2);
                    structureWorldAccess.setBlockState(_snowman2.move(Direction.DOWN, 1), BAMBOO_TOP_2, 2);
                    structureWorldAccess.setBlockState(_snowman2.move(Direction.DOWN, 1), BAMBOO_TOP_3, 2);
                }
            }
            ++n;
        }
        return n > 0;
    }
}

