/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockPileFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class NetherForestVegetationFeature
extends Feature<BlockPileFeatureConfig> {
    public NetherForestVegetationFeature(Codec<BlockPileFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, BlockPileFeatureConfig blockPileFeatureConfig) {
        return NetherForestVegetationFeature.generate(structureWorldAccess, random, blockPos, blockPileFeatureConfig, 8, 4);
    }

    public static boolean generate(WorldAccess world, Random random, BlockPos pos, BlockPileFeatureConfig config, int n, int n2) {
        Block block = world.getBlockState(pos.down()).getBlock();
        if (!block.isIn(BlockTags.NYLIUM)) {
            return false;
        }
        int _snowman2 = pos.getY();
        if (_snowman2 < 1 || _snowman2 + 1 >= 256) {
            return false;
        }
        int _snowman3 = 0;
        for (int i = 0; i < n * n; ++i) {
            BlockPos blockPos = pos.add(random.nextInt(n) - random.nextInt(n), random.nextInt(n2) - random.nextInt(n2), random.nextInt(n) - random.nextInt(n));
            BlockState _snowman4 = config.stateProvider.getBlockState(random, blockPos);
            if (!world.isAir(blockPos) || blockPos.getY() <= 0 || !_snowman4.canPlaceAt(world, blockPos)) continue;
            world.setBlockState(blockPos, _snowman4, 2);
            ++_snowman3;
        }
        return _snowman3 > 0;
    }
}

