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
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class IceSpikeFeature
extends Feature<DefaultFeatureConfig> {
    public IceSpikeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, ChunkGenerator chunkGenerator, Random random, BlockPos _snowman22, DefaultFeatureConfig defaultFeatureConfig) {
        int _snowman5;
        int n;
        StructureWorldAccess structureWorldAccess2;
        BlockPos _snowman22;
        while (structureWorldAccess2.isAir(_snowman22) && _snowman22.getY() > 2) {
            _snowman22 = _snowman22.down();
        }
        if (!structureWorldAccess2.getBlockState(_snowman22).isOf(Blocks.SNOW_BLOCK)) {
            return false;
        }
        _snowman22 = _snowman22.up(random.nextInt(4));
        int _snowman3 = random.nextInt(4) + 7;
        int _snowman4 = _snowman3 / 4 + random.nextInt(2);
        if (_snowman4 > 1 && random.nextInt(60) == 0) {
            _snowman22 = _snowman22.up(10 + random.nextInt(30));
        }
        for (n = 0; n < _snowman3; ++n) {
            float f = (1.0f - (float)n / (float)_snowman3) * (float)_snowman4;
            _snowman5 = MathHelper.ceil(f);
            for (int i = -_snowman5; i <= _snowman5; ++i) {
                float f2 = (float)MathHelper.abs(i) - 0.25f;
                for (int j = -_snowman5; j <= _snowman5; ++j) {
                    float f3 = (float)MathHelper.abs(j) - 0.25f;
                    if ((i != 0 || j != 0) && f2 * f2 + f3 * f3 > f * f || (i == -_snowman5 || i == _snowman5 || j == -_snowman5 || j == _snowman5) && random.nextFloat() > 0.75f) continue;
                    BlockState _snowman6 = structureWorldAccess2.getBlockState(_snowman22.add(i, n, j));
                    Block _snowman7 = _snowman6.getBlock();
                    if (_snowman6.isAir() || IceSpikeFeature.isSoil(_snowman7) || _snowman7 == Blocks.SNOW_BLOCK || _snowman7 == Blocks.ICE) {
                        this.setBlockState(structureWorldAccess2, _snowman22.add(i, n, j), Blocks.PACKED_ICE.getDefaultState());
                    }
                    if (n == 0 || _snowman5 <= 1) continue;
                    _snowman6 = structureWorldAccess2.getBlockState(_snowman22.add(i, -n, j));
                    _snowman7 = _snowman6.getBlock();
                    if (!_snowman6.isAir() && !IceSpikeFeature.isSoil(_snowman7) && _snowman7 != Blocks.SNOW_BLOCK && _snowman7 != Blocks.ICE) continue;
                    this.setBlockState(structureWorldAccess2, _snowman22.add(i, -n, j), Blocks.PACKED_ICE.getDefaultState());
                }
            }
        }
        n = _snowman4 - 1;
        if (n < 0) {
            n = 0;
        } else if (n > 1) {
            n = 1;
        }
        for (_snowman = -n; _snowman <= n; ++_snowman) {
            block5: for (_snowman5 = -n; _snowman5 <= n; ++_snowman5) {
                BlockPos _snowman10 = _snowman22.add(_snowman, -1, _snowman5);
                int _snowman8 = 50;
                if (Math.abs(_snowman) == 1 && Math.abs(_snowman5) == 1) {
                    _snowman8 = random.nextInt(5);
                }
                while (_snowman10.getY() > 50) {
                    BlockState blockState = structureWorldAccess2.getBlockState(_snowman10);
                    Block _snowman9 = blockState.getBlock();
                    if (!blockState.isAir() && !IceSpikeFeature.isSoil(_snowman9) && _snowman9 != Blocks.SNOW_BLOCK && _snowman9 != Blocks.ICE && _snowman9 != Blocks.PACKED_ICE) continue block5;
                    this.setBlockState(structureWorldAccess2, _snowman10, Blocks.PACKED_ICE.getDefaultState());
                    _snowman10 = _snowman10.down();
                    if (--_snowman8 > 0) continue;
                    _snowman10 = _snowman10.down(random.nextInt(5) + 1);
                    _snowman8 = random.nextInt(5);
                }
            }
        }
        return true;
    }
}

