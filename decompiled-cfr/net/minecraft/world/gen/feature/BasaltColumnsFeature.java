/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.BasaltColumnsFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class BasaltColumnsFeature
extends Feature<BasaltColumnsFeatureConfig> {
    private static final ImmutableList<Block> field_24132 = ImmutableList.of((Object)Blocks.LAVA, (Object)Blocks.BEDROCK, (Object)Blocks.MAGMA_BLOCK, (Object)Blocks.SOUL_SAND, (Object)Blocks.NETHER_BRICKS, (Object)Blocks.NETHER_BRICK_FENCE, (Object)Blocks.NETHER_BRICK_STAIRS, (Object)Blocks.NETHER_WART, (Object)Blocks.CHEST, (Object)Blocks.SPAWNER);

    public BasaltColumnsFeature(Codec<BasaltColumnsFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, BasaltColumnsFeatureConfig basaltColumnsFeatureConfig) {
        int n = chunkGenerator.getSeaLevel();
        if (!BasaltColumnsFeature.method_30379(structureWorldAccess, n, blockPos.mutableCopy())) {
            return false;
        }
        _snowman = basaltColumnsFeatureConfig.getHeight().getValue(random);
        boolean _snowman2 = random.nextFloat() < 0.9f;
        _snowman = Math.min(_snowman, _snowman2 ? 5 : 8);
        _snowman = _snowman2 ? 50 : 15;
        boolean _snowman3 = false;
        for (BlockPos blockPos2 : BlockPos.iterateRandomly(random, _snowman, blockPos.getX() - _snowman, blockPos.getY(), blockPos.getZ() - _snowman, blockPos.getX() + _snowman, blockPos.getY(), blockPos.getZ() + _snowman)) {
            int n2 = _snowman - blockPos2.getManhattanDistance(blockPos);
            if (n2 < 0) continue;
            _snowman3 |= this.method_27096(structureWorldAccess, n, blockPos2, n2, basaltColumnsFeatureConfig.getReach().getValue(random));
        }
        return _snowman3;
    }

    private boolean method_27096(WorldAccess worldAccess2, int n, BlockPos blockPos, int n2, int n3) {
        boolean bl = false;
        block0: for (BlockPos blockPos2 : BlockPos.iterate(blockPos.getX() - n3, blockPos.getY(), blockPos.getZ() - n3, blockPos.getX() + n3, blockPos.getY(), blockPos.getZ() + n3)) {
            int n4 = blockPos2.getManhattanDistance(blockPos);
            BlockPos blockPos3 = _snowman = BasaltColumnsFeature.method_27095(worldAccess2, n, blockPos2) ? BasaltColumnsFeature.method_27094(worldAccess2, n, blockPos2.mutableCopy(), n4) : BasaltColumnsFeature.method_27098(worldAccess2, blockPos2.mutableCopy(), n4);
            if (_snowman == null) continue;
            BlockPos.Mutable _snowman2 = _snowman.mutableCopy();
            for (_snowman = n2 - n4 / 2; _snowman >= 0; --_snowman) {
                WorldAccess worldAccess2;
                if (BasaltColumnsFeature.method_27095(worldAccess2, n, _snowman2)) {
                    this.setBlockState(worldAccess2, _snowman2, Blocks.BASALT.getDefaultState());
                    _snowman2.move(Direction.UP);
                    bl = true;
                    continue;
                }
                if (!worldAccess2.getBlockState(_snowman2).isOf(Blocks.BASALT)) continue block0;
                _snowman2.move(Direction.UP);
            }
        }
        return bl;
    }

    @Nullable
    private static BlockPos method_27094(WorldAccess worldAccess, int n, BlockPos.Mutable mutable, int n2) {
        while (mutable.getY() > 1 && n2 > 0) {
            --n2;
            if (BasaltColumnsFeature.method_30379(worldAccess, n, mutable)) {
                return mutable;
            }
            mutable.move(Direction.DOWN);
        }
        return null;
    }

    private static boolean method_30379(WorldAccess worldAccess, int n, BlockPos.Mutable mutable) {
        if (BasaltColumnsFeature.method_27095(worldAccess, n, mutable)) {
            BlockState blockState = worldAccess.getBlockState(mutable.move(Direction.DOWN));
            mutable.move(Direction.UP);
            return !blockState.isAir() && !field_24132.contains((Object)blockState.getBlock());
        }
        return false;
    }

    @Nullable
    private static BlockPos method_27098(WorldAccess worldAccess, BlockPos.Mutable mutable, int n) {
        while (mutable.getY() < worldAccess.getHeight() && n > 0) {
            --n;
            BlockState blockState = worldAccess.getBlockState(mutable);
            if (field_24132.contains((Object)blockState.getBlock())) {
                return null;
            }
            if (blockState.isAir()) {
                return mutable;
            }
            mutable.move(Direction.UP);
        }
        return null;
    }

    private static boolean method_27095(WorldAccess worldAccess, int n, BlockPos blockPos) {
        BlockState blockState = worldAccess.getBlockState(blockPos);
        return blockState.isAir() || blockState.isOf(Blocks.LAVA) && blockPos.getY() <= n;
    }
}

