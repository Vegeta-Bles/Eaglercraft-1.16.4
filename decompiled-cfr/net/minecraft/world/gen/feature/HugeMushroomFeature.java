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
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

public abstract class HugeMushroomFeature
extends Feature<HugeMushroomFeatureConfig> {
    public HugeMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
        super(codec);
    }

    protected void generateStem(WorldAccess world, Random random, BlockPos pos, HugeMushroomFeatureConfig config, int height, BlockPos.Mutable mutable) {
        for (int i = 0; i < height; ++i) {
            mutable.set(pos).move(Direction.UP, i);
            if (world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) continue;
            this.setBlockState(world, mutable, config.stemProvider.getBlockState(random, pos));
        }
    }

    protected int getHeight(Random random) {
        int n = random.nextInt(3) + 4;
        if (random.nextInt(12) == 0) {
            n *= 2;
        }
        return n;
    }

    protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
        int n = pos.getY();
        if (n < 1 || n + height + 1 >= 256) {
            return false;
        }
        Block _snowman2 = world.getBlockState(pos.down()).getBlock();
        if (!HugeMushroomFeature.isSoil(_snowman2) && !_snowman2.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return false;
        }
        for (_snowman = 0; _snowman <= height; ++_snowman) {
            _snowman = this.getCapSize(-1, -1, config.foliageRadius, _snowman);
            for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                    BlockState blockState = world.getBlockState(mutable.set(pos, _snowman, _snowman, _snowman));
                    if (blockState.isAir() || blockState.isIn(BlockTags.LEAVES)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, HugeMushroomFeatureConfig hugeMushroomFeatureConfig) {
        int n = this.getHeight(random);
        if (!this.canGenerate(structureWorldAccess, blockPos, n, _snowman = new BlockPos.Mutable(), hugeMushroomFeatureConfig)) {
            return false;
        }
        this.generateCap(structureWorldAccess, random, blockPos, n, _snowman, hugeMushroomFeatureConfig);
        this.generateStem(structureWorldAccess, random, blockPos, hugeMushroomFeatureConfig, n, _snowman);
        return true;
    }

    protected abstract int getCapSize(int var1, int var2, int var3, int var4);

    protected abstract void generateCap(WorldAccess var1, Random var2, BlockPos var3, int var4, BlockPos.Mutable var5, HugeMushroomFeatureConfig var6);
}

