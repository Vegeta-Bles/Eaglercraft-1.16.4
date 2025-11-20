/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowerFeature;

public class GrassBlock
extends SpreadableBlock
implements Fertilizable {
    public GrassBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState _snowman2 = Blocks.GRASS.getDefaultState();
        block0: for (int i = 0; i < 128; ++i) {
            BlockState _snowman6;
            BlockPos blockPos2 = blockPos;
            for (int j = 0; j < i / 16; ++j) {
                if (!world.getBlockState((blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1)).down()).isOf(this) || world.getBlockState(blockPos2).isFullCube(world, blockPos2)) continue block0;
            }
            BlockState _snowman3 = world.getBlockState(blockPos2);
            if (_snowman3.isOf(_snowman2.getBlock()) && random.nextInt(10) == 0) {
                ((Fertilizable)((Object)_snowman2.getBlock())).grow(world, random, blockPos2, _snowman3);
            }
            if (!_snowman3.isAir()) continue;
            if (random.nextInt(8) == 0) {
                List<ConfiguredFeature<?, ?>> list = world.getBiome(blockPos2).getGenerationSettings().getFlowerFeatures();
                if (list.isEmpty()) continue;
                ConfiguredFeature<?, ?> _snowman4 = list.get(0);
                FlowerFeature _snowman5 = (FlowerFeature)_snowman4.feature;
                _snowman6 = _snowman5.getFlowerState(random, blockPos2, _snowman4.getConfig());
            } else {
                _snowman6 = _snowman2;
            }
            if (!_snowman6.canPlaceAt(world, blockPos2)) continue;
            world.setBlockState(blockPos2, _snowman6, 3);
        }
    }
}

