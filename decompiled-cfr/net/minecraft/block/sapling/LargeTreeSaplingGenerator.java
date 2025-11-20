/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public abstract class LargeTreeSaplingGenerator
extends SaplingGenerator {
    @Override
    public boolean generate(ServerWorld serverWorld2, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random) {
        ServerWorld serverWorld2;
        for (int i = 0; i >= -1; --i) {
            for (_snowman = 0; _snowman >= -1; --_snowman) {
                if (!LargeTreeSaplingGenerator.canGenerateLargeTree(blockState, serverWorld2, blockPos, i, _snowman)) continue;
                return this.generateLargeTree(serverWorld2, chunkGenerator, blockPos, blockState, random, i, _snowman);
            }
        }
        return super.generate(serverWorld2, chunkGenerator, blockPos, blockState, random);
    }

    @Nullable
    protected abstract ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random var1);

    public boolean generateLargeTree(ServerWorld serverWorld, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random, int n, int n2) {
        ConfiguredFeature<TreeFeatureConfig, ?> configuredFeature = this.createLargeTreeFeature(random);
        if (configuredFeature == null) {
            return false;
        }
        ((TreeFeatureConfig)configuredFeature.config).ignoreFluidCheck();
        BlockState _snowman2 = Blocks.AIR.getDefaultState();
        serverWorld.setBlockState(blockPos.add(n, 0, n2), _snowman2, 4);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2), _snowman2, 4);
        serverWorld.setBlockState(blockPos.add(n, 0, n2 + 1), _snowman2, 4);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2 + 1), _snowman2, 4);
        if (configuredFeature.generate(serverWorld, chunkGenerator, random, blockPos.add(n, 0, n2))) {
            return true;
        }
        serverWorld.setBlockState(blockPos.add(n, 0, n2), blockState, 4);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2), blockState, 4);
        serverWorld.setBlockState(blockPos.add(n, 0, n2 + 1), blockState, 4);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2 + 1), blockState, 4);
        return false;
    }

    public static boolean canGenerateLargeTree(BlockState state, BlockView world, BlockPos pos, int x, int z) {
        Block block = state.getBlock();
        return block == world.getBlockState(pos.add(x, 0, z)).getBlock() && block == world.getBlockState(pos.add(x + 1, 0, z)).getBlock() && block == world.getBlockState(pos.add(x, 0, z + 1)).getBlock() && block == world.getBlockState(pos.add(x + 1, 0, z + 1)).getBlock();
    }
}

