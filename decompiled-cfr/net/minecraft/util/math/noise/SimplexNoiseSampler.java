/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.math.noise;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public class SimplexNoiseSampler {
    protected static final int[][] gradients = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}, {1, 1, 0}, {0, -1, 1}, {-1, 1, 0}, {0, -1, -1}};
    private static final double SQRT_3 = Math.sqrt(3.0);
    private static final double SKEW_FACTOR_2D = 0.5 * (SQRT_3 - 1.0);
    private static final double UNSKEW_FACTOR_2D = (3.0 - SQRT_3) / 6.0;
    private final int[] permutations = new int[512];
    public final double originX;
    public final double originY;
    public final double originZ;

    public SimplexNoiseSampler(Random random) {
        int n;
        this.originX = random.nextDouble() * 256.0;
        this.originY = random.nextDouble() * 256.0;
        this.originZ = random.nextDouble() * 256.0;
        for (n = 0; n < 256; ++n) {
            this.permutations[n] = n;
        }
        for (n = 0; n < 256; ++n) {
            _snowman = random.nextInt(256 - n);
            _snowman = this.permutations[n];
            this.permutations[n] = this.permutations[_snowman + n];
            this.permutations[_snowman + n] = _snowman;
        }
    }

    private int getGradient(int hash) {
        return this.permutations[hash & 0xFF];
    }

    protected static double dot(int[] gArr, double x, double y, double z) {
        return (double)gArr[0] * x + (double)gArr[1] * y + (double)gArr[2] * z;
    }

    private double grad(int hash, double x, double y, double z, double d) {
        _snowman = d - x * x - y * y - z * z;
        if (_snowman < 0.0) {
            _snowman = 0.0;
        } else {
            _snowman *= _snowman;
            _snowman = _snowman * _snowman * SimplexNoiseSampler.dot(gradients[hash], x, y, z);
        }
        return _snowman;
    }

    public double sample(double x, double y) {
        double d;
        int n;
        double d2 = (x + y) * SKEW_FACTOR_2D;
        int _snowman2 = MathHelper.floor(x + d2);
        _snowman = (double)_snowman2 - (_snowman = (double)(_snowman2 + (_snowman = MathHelper.floor(y + d2))) * UNSKEW_FACTOR_2D);
        d = x - _snowman;
        if (d > (_snowman = y - (_snowman = (double)_snowman - _snowman))) {
            n = 1;
            _snowman = 0;
        } else {
            n = 0;
            _snowman = 1;
        }
        _snowman = d - (double)n + UNSKEW_FACTOR_2D;
        _snowman = _snowman - (double)_snowman + UNSKEW_FACTOR_2D;
        _snowman = d - 1.0 + 2.0 * UNSKEW_FACTOR_2D;
        _snowman = _snowman - 1.0 + 2.0 * UNSKEW_FACTOR_2D;
        int _snowman3 = _snowman2 & 0xFF;
        int _snowman4 = _snowman & 0xFF;
        int _snowman5 = this.getGradient(_snowman3 + this.getGradient(_snowman4)) % 12;
        int _snowman6 = this.getGradient(_snowman3 + n + this.getGradient(_snowman4 + _snowman)) % 12;
        int _snowman7 = this.getGradient(_snowman3 + 1 + this.getGradient(_snowman4 + 1)) % 12;
        _snowman = this.grad(_snowman5, d, _snowman, 0.0, 0.5);
        _snowman = this.grad(_snowman6, _snowman, _snowman, 0.0, 0.5);
        _snowman = this.grad(_snowman7, _snowman, _snowman, 0.0, 0.5);
        return 70.0 * (_snowman + _snowman + _snowman);
    }

    public double method_22416(double d, double d2, double d3) {
        _snowman = 0.3333333333333333;
        _snowman = (d + d2 + d3) * 0.3333333333333333;
        int n = MathHelper.floor(d + _snowman);
        _snowman = MathHelper.floor(d2 + _snowman);
        _snowman = MathHelper.floor(d3 + _snowman);
        double _snowman2 = 0.16666666666666666;
        double _snowman3 = (double)(n + _snowman + _snowman) * 0.16666666666666666;
        double _snowman4 = (double)n - _snowman3;
        double _snowman5 = (double)_snowman - _snowman3;
        double _snowman6 = (double)_snowman - _snowman3;
        double _snowman7 = d - _snowman4;
        double _snowman8 = d2 - _snowman5;
        double _snowman9 = d3 - _snowman6;
        if (_snowman7 >= _snowman8) {
            if (_snowman8 >= _snowman9) {
                _snowman = 1;
                _snowman = 0;
                _snowman = 0;
                _snowman = 1;
                _snowman = 1;
                _snowman = 0;
            } else if (_snowman7 >= _snowman9) {
                _snowman = 1;
                _snowman = 0;
                _snowman = 0;
                _snowman = 1;
                _snowman = 0;
                _snowman = 1;
            } else {
                _snowman = 0;
                _snowman = 0;
                _snowman = 1;
                _snowman = 1;
                _snowman = 0;
                _snowman = 1;
            }
        } else if (_snowman8 < _snowman9) {
            _snowman = 0;
            _snowman = 0;
            _snowman = 1;
            _snowman = 0;
            _snowman = 1;
            _snowman = 1;
        } else if (_snowman7 < _snowman9) {
            _snowman = 0;
            _snowman = 1;
            _snowman = 0;
            _snowman = 0;
            _snowman = 1;
            _snowman = 1;
        } else {
            _snowman = 0;
            _snowman = 1;
            _snowman = 0;
            _snowman = 1;
            _snowman = 1;
            _snowman = 0;
        }
        double _snowman10 = _snowman7 - (double)_snowman + 0.16666666666666666;
        double _snowman11 = _snowman8 - (double)_snowman + 0.16666666666666666;
        double _snowman12 = _snowman9 - (double)_snowman + 0.16666666666666666;
        double _snowman13 = _snowman7 - (double)_snowman + 0.3333333333333333;
        double _snowman14 = _snowman8 - (double)_snowman + 0.3333333333333333;
        double _snowman15 = _snowman9 - (double)_snowman + 0.3333333333333333;
        double _snowman16 = _snowman7 - 1.0 + 0.5;
        double _snowman17 = _snowman8 - 1.0 + 0.5;
        double _snowman18 = _snowman9 - 1.0 + 0.5;
        _snowman = n & 0xFF;
        _snowman = _snowman & 0xFF;
        _snowman = _snowman & 0xFF;
        _snowman = this.getGradient(_snowman + this.getGradient(_snowman + this.getGradient(_snowman))) % 12;
        _snowman = this.getGradient(_snowman + _snowman + this.getGradient(_snowman + _snowman + this.getGradient(_snowman + _snowman))) % 12;
        _snowman = this.getGradient(_snowman + _snowman + this.getGradient(_snowman + _snowman + this.getGradient(_snowman + _snowman))) % 12;
        _snowman = this.getGradient(_snowman + 1 + this.getGradient(_snowman + 1 + this.getGradient(_snowman + 1))) % 12;
        double _snowman19 = this.grad(_snowman, _snowman7, _snowman8, _snowman9, 0.6);
        double _snowman20 = this.grad(_snowman, _snowman10, _snowman11, _snowman12, 0.6);
        double _snowman21 = this.grad(_snowman, _snowman13, _snowman14, _snowman15, 0.6);
        double _snowman22 = this.grad(_snowman, _snowman16, _snowman17, _snowman18, 0.6);
        return 32.0 * (_snowman19 + _snowman20 + _snowman21 + _snowman22);
    }
}

