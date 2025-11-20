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
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.HugeMushroomFeature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

public class HugeBrownMushroomFeature
extends HugeMushroomFeature {
    public HugeBrownMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
        int n = config.foliageRadius;
        for (_snowman = -n; _snowman <= n; ++_snowman) {
            for (_snowman = -n; _snowman <= n; ++_snowman) {
                boolean bl = _snowman == -n;
                _snowman = _snowman == n;
                _snowman = _snowman == -n;
                _snowman = _snowman == n;
                _snowman = bl || _snowman;
                boolean bl2 = _snowman = _snowman || _snowman;
                if (_snowman && _snowman) continue;
                mutable.set(start, _snowman, y, _snowman);
                if (world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) continue;
                _snowman = bl || _snowman && _snowman == 1 - n;
                _snowman = _snowman || _snowman && _snowman == n - 1;
                _snowman = _snowman || _snowman && _snowman == 1 - n;
                _snowman = _snowman || _snowman && _snowman == n - 1;
                this.setBlockState(world, mutable, (BlockState)((BlockState)((BlockState)((BlockState)config.capProvider.getBlockState(random, start).with(MushroomBlock.WEST, _snowman)).with(MushroomBlock.EAST, _snowman)).with(MushroomBlock.NORTH, _snowman)).with(MushroomBlock.SOUTH, _snowman));
            }
        }
    }

    @Override
    protected int getCapSize(int n, int n2, int capSize, int y) {
        return y <= 3 ? 0 : capSize;
    }
}

