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
import net.minecraft.block.SnowyBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class FreezeTopLayerFeature
extends Feature<DefaultFeatureConfig> {
    public FreezeTopLayerFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        _snowman = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                _snowman = blockPos.getX() + i;
                _snowman = blockPos.getZ() + _snowman;
                _snowman = structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING, _snowman, _snowman);
                mutable.set(_snowman, _snowman, _snowman);
                _snowman.set(mutable).move(Direction.DOWN, 1);
                Biome biome = structureWorldAccess.getBiome(mutable);
                if (biome.canSetIce(structureWorldAccess, _snowman, false)) {
                    structureWorldAccess.setBlockState(_snowman, Blocks.ICE.getDefaultState(), 2);
                }
                if (!biome.canSetSnow(structureWorldAccess, mutable)) continue;
                structureWorldAccess.setBlockState(mutable, Blocks.SNOW.getDefaultState(), 2);
                BlockState _snowman2 = structureWorldAccess.getBlockState(_snowman);
                if (!_snowman2.contains(SnowyBlock.SNOWY)) continue;
                structureWorldAccess.setBlockState(_snowman, (BlockState)_snowman2.with(SnowyBlock.SNOWY, true), 2);
            }
        }
        return true;
    }
}

