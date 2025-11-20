/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DeltaFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class DeltaFeature
extends Feature<DeltaFeatureConfig> {
    private static final ImmutableList<Block> field_24133 = ImmutableList.of((Object)Blocks.BEDROCK, (Object)Blocks.NETHER_BRICKS, (Object)Blocks.NETHER_BRICK_FENCE, (Object)Blocks.NETHER_BRICK_STAIRS, (Object)Blocks.NETHER_WART, (Object)Blocks.CHEST, (Object)Blocks.SPAWNER);
    private static final Direction[] DIRECTIONS = Direction.values();

    public DeltaFeature(Codec<DeltaFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DeltaFeatureConfig deltaFeatureConfig) {
        boolean _snowman7 = false;
        _snowman = random.nextDouble() < 0.9;
        int _snowman2 = _snowman ? deltaFeatureConfig.getRimSize().getValue(random) : 0;
        int _snowman3 = _snowman ? deltaFeatureConfig.getRimSize().getValue(random) : 0;
        _snowman = _snowman && _snowman2 != 0 && _snowman3 != 0;
        int _snowman4 = deltaFeatureConfig.getSize().getValue(random);
        int _snowman5 = deltaFeatureConfig.getSize().getValue(random);
        int _snowman6 = Math.max(_snowman4, _snowman5);
        for (BlockPos blockPos2 : BlockPos.iterateOutwards(blockPos, _snowman4, 0, _snowman5)) {
            StructureWorldAccess structureWorldAccess2;
            if (blockPos2.getManhattanDistance(blockPos) > _snowman6) break;
            if (!DeltaFeature.method_27103(structureWorldAccess2, blockPos2, deltaFeatureConfig)) continue;
            if (_snowman) {
                _snowman7 = true;
                this.setBlockState(structureWorldAccess2, blockPos2, deltaFeatureConfig.getRim());
            }
            if (!DeltaFeature.method_27103(structureWorldAccess2, _snowman = blockPos2.add(_snowman2, 0, _snowman3), deltaFeatureConfig)) continue;
            _snowman7 = true;
            this.setBlockState(structureWorldAccess2, _snowman, deltaFeatureConfig.getContents());
        }
        return _snowman7;
    }

    private static boolean method_27103(WorldAccess worldAccess, BlockPos blockPos, DeltaFeatureConfig deltaFeatureConfig) {
        BlockState blockState = worldAccess.getBlockState(blockPos);
        if (blockState.isOf(deltaFeatureConfig.getContents().getBlock())) {
            return false;
        }
        if (field_24133.contains((Object)blockState.getBlock())) {
            return false;
        }
        for (Direction direction : DIRECTIONS) {
            boolean bl = worldAccess.getBlockState(blockPos.offset(direction)).isAir();
            if ((!bl || direction == Direction.UP) && (bl || direction != Direction.UP)) continue;
            return false;
        }
        return true;
    }
}

