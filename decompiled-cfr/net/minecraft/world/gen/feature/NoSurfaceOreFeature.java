/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class NoSurfaceOreFeature
extends Feature<OreFeatureConfig> {
    NoSurfaceOreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, OreFeatureConfig oreFeatureConfig) {
        int n = random.nextInt(oreFeatureConfig.size + 1);
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            this.getStartPos(_snowman2, random, blockPos, Math.min(_snowman, 7));
            if (!oreFeatureConfig.target.test(structureWorldAccess.getBlockState(_snowman2), random) || this.checkAir(structureWorldAccess, _snowman2)) continue;
            structureWorldAccess.setBlockState(_snowman2, oreFeatureConfig.state, 2);
        }
        return true;
    }

    private void getStartPos(BlockPos.Mutable mutable, Random random, BlockPos pos, int size) {
        int n = this.randomCoord(random, size);
        _snowman = this.randomCoord(random, size);
        _snowman = this.randomCoord(random, size);
        mutable.set(pos, n, _snowman, _snowman);
    }

    private int randomCoord(Random random, int size) {
        return Math.round((random.nextFloat() - random.nextFloat()) * (float)size);
    }

    private boolean checkAir(WorldAccess world, BlockPos pos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            if (!world.getBlockState(mutable).isAir()) continue;
            return true;
        }
        return false;
    }
}

