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
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;

public class RandomPatchFeature
extends Feature<RandomPatchFeatureConfig> {
    public RandomPatchFeature(Codec<RandomPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, RandomPatchFeatureConfig randomPatchFeatureConfig) {
        BlockState blockState = randomPatchFeatureConfig.stateProvider.getBlockState(random, blockPos);
        BlockPos _snowman2 = randomPatchFeatureConfig.project ? structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, blockPos) : blockPos;
        int _snowman3 = 0;
        BlockPos.Mutable _snowman4 = new BlockPos.Mutable();
        for (int i = 0; i < randomPatchFeatureConfig.tries; ++i) {
            _snowman4.set(_snowman2, random.nextInt(randomPatchFeatureConfig.spreadX + 1) - random.nextInt(randomPatchFeatureConfig.spreadX + 1), random.nextInt(randomPatchFeatureConfig.spreadY + 1) - random.nextInt(randomPatchFeatureConfig.spreadY + 1), random.nextInt(randomPatchFeatureConfig.spreadZ + 1) - random.nextInt(randomPatchFeatureConfig.spreadZ + 1));
            Vec3i vec3i = _snowman4.down();
            BlockState _snowman5 = structureWorldAccess.getBlockState((BlockPos)vec3i);
            if (!structureWorldAccess.isAir(_snowman4) && (!randomPatchFeatureConfig.canReplace || !structureWorldAccess.getBlockState(_snowman4).getMaterial().isReplaceable()) || !blockState.canPlaceAt(structureWorldAccess, _snowman4) || !randomPatchFeatureConfig.whitelist.isEmpty() && !randomPatchFeatureConfig.whitelist.contains(_snowman5.getBlock()) || randomPatchFeatureConfig.blacklist.contains(_snowman5) || randomPatchFeatureConfig.needsWater && !structureWorldAccess.getFluidState(((BlockPos)vec3i).west()).isIn(FluidTags.WATER) && !structureWorldAccess.getFluidState(((BlockPos)vec3i).east()).isIn(FluidTags.WATER) && !structureWorldAccess.getFluidState(((BlockPos)vec3i).north()).isIn(FluidTags.WATER) && !structureWorldAccess.getFluidState(((BlockPos)vec3i).south()).isIn(FluidTags.WATER)) continue;
            randomPatchFeatureConfig.blockPlacer.generate(structureWorldAccess, _snowman4, blockState, random);
            ++_snowman3;
        }
        return _snowman3 > 0;
    }
}

