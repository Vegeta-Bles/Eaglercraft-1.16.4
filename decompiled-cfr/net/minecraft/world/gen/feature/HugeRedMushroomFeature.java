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

public class HugeRedMushroomFeature
extends HugeMushroomFeature {
    public HugeRedMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
        for (int i = y - 3; i <= y; ++i) {
            _snowman = i < y ? config.foliageRadius : config.foliageRadius - 1;
            _snowman = config.foliageRadius - 2;
            for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                    boolean bl = _snowman == -_snowman;
                    _snowman = _snowman == _snowman;
                    _snowman = _snowman == -_snowman;
                    _snowman = _snowman == _snowman;
                    _snowman = bl || _snowman;
                    boolean bl2 = _snowman = _snowman || _snowman;
                    if (i < y && _snowman == _snowman) continue;
                    mutable.set(start, _snowman, i, _snowman);
                    if (world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) continue;
                    this.setBlockState(world, mutable, (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)config.capProvider.getBlockState(random, start).with(MushroomBlock.UP, i >= y - 1)).with(MushroomBlock.WEST, _snowman < -_snowman)).with(MushroomBlock.EAST, _snowman > _snowman)).with(MushroomBlock.NORTH, _snowman < -_snowman)).with(MushroomBlock.SOUTH, _snowman > _snowman));
                }
            }
        }
    }

    @Override
    protected int getCapSize(int n, int n2, int capSize, int y) {
        _snowman = 0;
        if (y < n2 && y >= n2 - 3) {
            _snowman = capSize;
        } else if (y == n2) {
            _snowman = capSize;
        }
        return _snowman;
    }
}

