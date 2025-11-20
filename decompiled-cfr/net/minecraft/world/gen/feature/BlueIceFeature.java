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
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class BlueIceFeature
extends Feature<DefaultFeatureConfig> {
    public BlueIceFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        if (blockPos.getY() > structureWorldAccess.getSeaLevel() - 1) {
            return false;
        }
        if (!structureWorldAccess.getBlockState(blockPos).isOf(Blocks.WATER) && !structureWorldAccess.getBlockState(blockPos.down()).isOf(Blocks.WATER)) {
            return false;
        }
        boolean bl = false;
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN || !structureWorldAccess.getBlockState(blockPos.offset(direction)).isOf(Blocks.PACKED_ICE)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            return false;
        }
        structureWorldAccess.setBlockState(blockPos, Blocks.BLUE_ICE.getDefaultState(), 2);
        block1: for (int i = 0; i < 200; ++i) {
            BlockPos blockPos2;
            int n = random.nextInt(5) - random.nextInt(6);
            _snowman = 3;
            if (n < 2) {
                _snowman += n / 2;
            }
            if (_snowman < 1 || (_snowman = structureWorldAccess.getBlockState(blockPos2 = blockPos.add(random.nextInt(_snowman) - random.nextInt(_snowman), n, random.nextInt(_snowman) - random.nextInt(_snowman)))).getMaterial() != Material.AIR && !_snowman.isOf(Blocks.WATER) && !_snowman.isOf(Blocks.PACKED_ICE) && !_snowman.isOf(Blocks.ICE)) continue;
            for (Direction direction : Direction.values()) {
                BlockState blockState = structureWorldAccess.getBlockState(blockPos2.offset(direction));
                if (!blockState.isOf(Blocks.BLUE_ICE)) continue;
                structureWorldAccess.setBlockState(blockPos2, Blocks.BLUE_ICE.getDefaultState(), 2);
                continue block1;
            }
        }
        return true;
    }
}

