/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 */
package net.minecraft.util;

import javax.annotation.Nonnull;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class CubicSampler {
    private static final double[] DENSITY_CURVE = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

    @Nonnull
    public static Vec3d sampleColor(Vec3d pos, RgbFetcher rgbFetcher) {
        int n = MathHelper.floor(pos.getX());
        _snowman = MathHelper.floor(pos.getY());
        _snowman = MathHelper.floor(pos.getZ());
        double _snowman2 = pos.getX() - (double)n;
        double _snowman3 = pos.getY() - (double)_snowman;
        double _snowman4 = pos.getZ() - (double)_snowman;
        double _snowman5 = 0.0;
        Vec3d _snowman6 = Vec3d.ZERO;
        for (_snowman = 0; _snowman < 6; ++_snowman) {
            double d = MathHelper.lerp(_snowman2, DENSITY_CURVE[_snowman + 1], DENSITY_CURVE[_snowman]);
            int _snowman7 = n - 2 + _snowman;
            for (int i = 0; i < 6; ++i) {
                double d2 = MathHelper.lerp(_snowman3, DENSITY_CURVE[i + 1], DENSITY_CURVE[i]);
                int _snowman8 = _snowman - 2 + i;
                for (int j = 0; j < 6; ++j) {
                    double d3 = MathHelper.lerp(_snowman4, DENSITY_CURVE[j + 1], DENSITY_CURVE[j]);
                    int _snowman9 = _snowman - 2 + j;
                    _snowman = d * d2 * d3;
                    _snowman5 += _snowman;
                    _snowman6 = _snowman6.add(rgbFetcher.fetch(_snowman7, _snowman8, _snowman9).multiply(_snowman));
                }
            }
        }
        _snowman6 = _snowman6.multiply(1.0 / _snowman5);
        return _snowman6;
    }

    public static interface RgbFetcher {
        public Vec3d fetch(int var1, int var2, int var3);
    }
}

