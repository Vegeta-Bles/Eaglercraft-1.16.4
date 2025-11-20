/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreFeature
extends Feature<OreFeatureConfig> {
    public OreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, OreFeatureConfig oreFeatureConfig) {
        float f = random.nextFloat() * (float)Math.PI;
        _snowman = (float)oreFeatureConfig.size / 8.0f;
        int _snowman2 = MathHelper.ceil(((float)oreFeatureConfig.size / 16.0f * 2.0f + 1.0f) / 2.0f);
        double _snowman3 = (double)blockPos.getX() + Math.sin(f) * (double)_snowman;
        double _snowman4 = (double)blockPos.getX() - Math.sin(f) * (double)_snowman;
        double _snowman5 = (double)blockPos.getZ() + Math.cos(f) * (double)_snowman;
        double _snowman6 = (double)blockPos.getZ() - Math.cos(f) * (double)_snowman;
        int _snowman7 = 2;
        double _snowman8 = blockPos.getY() + random.nextInt(3) - 2;
        double _snowman9 = blockPos.getY() + random.nextInt(3) - 2;
        int _snowman10 = blockPos.getX() - MathHelper.ceil(_snowman) - _snowman2;
        int _snowman11 = blockPos.getY() - 2 - _snowman2;
        int _snowman12 = blockPos.getZ() - MathHelper.ceil(_snowman) - _snowman2;
        int _snowman13 = 2 * (MathHelper.ceil(_snowman) + _snowman2);
        int _snowman14 = 2 * (2 + _snowman2);
        for (int i = _snowman10; i <= _snowman10 + _snowman13; ++i) {
            for (_snowman = _snowman12; _snowman <= _snowman12 + _snowman13; ++_snowman) {
                if (_snowman11 > structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, i, _snowman)) continue;
                return this.generateVeinPart(structureWorldAccess, random, oreFeatureConfig, _snowman3, _snowman4, _snowman5, _snowman6, _snowman8, _snowman9, _snowman10, _snowman11, _snowman12, _snowman13, _snowman14);
            }
        }
        return false;
    }

    protected boolean generateVeinPart(WorldAccess world, Random random, OreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int size, int n) {
        int n2;
        double _snowman8;
        double _snowman7;
        double _snowman6;
        double _snowman5;
        int n3;
        n2 = 0;
        BitSet bitSet = new BitSet(size * n * size);
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        int _snowman3 = config.size;
        double[] _snowman4 = new double[_snowman3 * 4];
        for (n3 = 0; n3 < _snowman3; ++n3) {
            float f = (float)n3 / (float)_snowman3;
            _snowman5 = MathHelper.lerp((double)f, startX, endX);
            _snowman6 = MathHelper.lerp((double)f, startY, endY);
            _snowman7 = MathHelper.lerp((double)f, startZ, endZ);
            _snowman8 = random.nextDouble() * (double)_snowman3 / 16.0;
            double _snowman9 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0f) * _snowman8 + 1.0) / 2.0;
            _snowman4[n3 * 4 + 0] = _snowman5;
            _snowman4[n3 * 4 + 1] = _snowman6;
            _snowman4[n3 * 4 + 2] = _snowman7;
            _snowman4[n3 * 4 + 3] = _snowman9;
        }
        for (n3 = 0; n3 < _snowman3 - 1; ++n3) {
            if (_snowman4[n3 * 4 + 3] <= 0.0) continue;
            for (_snowman = n3 + 1; _snowman < _snowman3; ++_snowman) {
                if (_snowman4[_snowman * 4 + 3] <= 0.0 || !((_snowman8 = _snowman4[n3 * 4 + 3] - _snowman4[_snowman * 4 + 3]) * _snowman8 > (_snowman5 = _snowman4[n3 * 4 + 0] - _snowman4[_snowman * 4 + 0]) * _snowman5 + (_snowman6 = _snowman4[n3 * 4 + 1] - _snowman4[_snowman * 4 + 1]) * _snowman6 + (_snowman7 = _snowman4[n3 * 4 + 2] - _snowman4[_snowman * 4 + 2]) * _snowman7)) continue;
                if (_snowman8 > 0.0) {
                    _snowman4[_snowman * 4 + 3] = -1.0;
                    continue;
                }
                _snowman4[n3 * 4 + 3] = -1.0;
            }
        }
        for (n3 = 0; n3 < _snowman3; ++n3) {
            double d = _snowman4[n3 * 4 + 3];
            if (d < 0.0) continue;
            _snowman = _snowman4[n3 * 4 + 0];
            _snowman = _snowman4[n3 * 4 + 1];
            _snowman = _snowman4[n3 * 4 + 2];
            int _snowman10 = Math.max(MathHelper.floor(_snowman - d), x);
            int _snowman11 = Math.max(MathHelper.floor(_snowman - d), y);
            int _snowman12 = Math.max(MathHelper.floor(_snowman - d), z);
            int _snowman13 = Math.max(MathHelper.floor(_snowman + d), _snowman10);
            int _snowman14 = Math.max(MathHelper.floor(_snowman + d), _snowman11);
            int _snowman15 = Math.max(MathHelper.floor(_snowman + d), _snowman12);
            for (int i = _snowman10; i <= _snowman13; ++i) {
                double d2 = ((double)i + 0.5 - _snowman) / d;
                if (!(d2 * d2 < 1.0)) continue;
                for (int j = _snowman11; j <= _snowman14; ++j) {
                    double d3 = ((double)j + 0.5 - _snowman) / d;
                    if (!(d2 * d2 + d3 * d3 < 1.0)) continue;
                    for (int k = _snowman12; k <= _snowman15; ++k) {
                        double d4 = ((double)k + 0.5 - _snowman) / d;
                        if (!(d2 * d2 + d3 * d3 + d4 * d4 < 1.0) || bitSet.get(_snowman = i - x + (j - y) * size + (k - z) * size * n)) continue;
                        bitSet.set(_snowman);
                        _snowman2.set(i, j, k);
                        if (!config.target.test(world.getBlockState(_snowman2), random)) continue;
                        world.setBlockState(_snowman2, config.state, 2);
                        ++n2;
                    }
                }
            }
        }
        return n2 > 0;
    }
}

