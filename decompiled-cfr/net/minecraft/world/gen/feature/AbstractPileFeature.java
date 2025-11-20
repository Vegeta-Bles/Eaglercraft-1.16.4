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
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockPileFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class AbstractPileFeature
extends Feature<BlockPileFeatureConfig> {
    public AbstractPileFeature(Codec<BlockPileFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, BlockPileFeatureConfig blockPileFeatureConfig) {
        if (blockPos.getY() < 5) {
            return false;
        }
        int n = 2 + random.nextInt(2);
        _snowman = 2 + random.nextInt(2);
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-n, 0, -_snowman), blockPos.add(n, 1, _snowman))) {
            int n2 = blockPos.getX() - blockPos2.getX();
            if ((float)(n2 * n2 + (_snowman = blockPos.getZ() - blockPos2.getZ()) * _snowman) <= random.nextFloat() * 10.0f - random.nextFloat() * 6.0f) {
                this.addPileBlock(structureWorldAccess, blockPos2, random, blockPileFeatureConfig);
                continue;
            }
            if (!((double)random.nextFloat() < 0.031)) continue;
            this.addPileBlock(structureWorldAccess, blockPos2, random, blockPileFeatureConfig);
        }
        return true;
    }

    private boolean canPlacePileBlock(WorldAccess world, BlockPos pos, Random random) {
        BlockPos blockPos = pos.down();
        BlockState _snowman2 = world.getBlockState(blockPos);
        if (_snowman2.isOf(Blocks.GRASS_PATH)) {
            return random.nextBoolean();
        }
        return _snowman2.isSideSolidFullSquare(world, blockPos, Direction.UP);
    }

    private void addPileBlock(WorldAccess world, BlockPos pos, Random random, BlockPileFeatureConfig config) {
        if (world.isAir(pos) && this.canPlacePileBlock(world, pos, random)) {
            world.setBlockState(pos, config.stateProvider.getBlockState(random, pos), 4);
        }
    }
}

