/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.math.noise;

import java.util.Random;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;

public final class PerlinNoiseSampler {
    private final byte[] permutations;
    public final double originX;
    public final double originY;
    public final double originZ;

    public PerlinNoiseSampler(Random random) {
        int n;
        this.originX = random.nextDouble() * 256.0;
        this.originY = random.nextDouble() * 256.0;
        this.originZ = random.nextDouble() * 256.0;
        this.permutations = new byte[256];
        for (n = 0; n < 256; ++n) {
            this.permutations[n] = (byte)n;
        }
        for (n = 0; n < 256; ++n) {
            _snowman = random.nextInt(256 - n);
            byte by = this.permutations[n];
            this.permutations[n] = this.permutations[n + _snowman];
            this.permutations[n + _snowman] = by;
        }
    }

    public double sample(double x, double y, double z, double d, double d2) {
        _snowman = x + this.originX;
        _snowman = y + this.originY;
        _snowman = z + this.originZ;
        int n = MathHelper.floor(_snowman);
        _snowman = MathHelper.floor(_snowman);
        _snowman = MathHelper.floor(_snowman);
        double _snowman2 = _snowman - (double)n;
        double _snowman3 = _snowman - (double)_snowman;
        double _snowman4 = _snowman - (double)_snowman;
        double _snowman5 = MathHelper.perlinFade(_snowman2);
        double _snowman6 = MathHelper.perlinFade(_snowman3);
        double _snowman7 = MathHelper.perlinFade(_snowman4);
        if (d != 0.0) {
            double d3 = Math.min(d2, _snowman3);
            d4 = (double)MathHelper.floor(d3 / d) * d;
        } else {
            double d4 = 0.0;
        }
        return this.sample(n, _snowman, _snowman, _snowman2, _snowman3 - d4, _snowman4, _snowman5, _snowman6, _snowman7);
    }

    private static double grad(int hash, double x, double y, double z) {
        int n = hash & 0xF;
        return SimplexNoiseSampler.dot(SimplexNoiseSampler.gradients[n], x, y, z);
    }

    private int getGradient(int hash) {
        return this.permutations[hash & 0xFF] & 0xFF;
    }

    public double sample(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX, double fadeLocalY, double fadeLocalZ) {
        int n = this.getGradient(sectionX) + sectionY;
        _snowman = this.getGradient(n) + sectionZ;
        _snowman = this.getGradient(n + 1) + sectionZ;
        _snowman = this.getGradient(sectionX + 1) + sectionY;
        _snowman = this.getGradient(_snowman) + sectionZ;
        _snowman = this.getGradient(_snowman + 1) + sectionZ;
        double _snowman2 = PerlinNoiseSampler.grad(this.getGradient(_snowman), localX, localY, localZ);
        double _snowman3 = PerlinNoiseSampler.grad(this.getGradient(_snowman), localX - 1.0, localY, localZ);
        double _snowman4 = PerlinNoiseSampler.grad(this.getGradient(_snowman), localX, localY - 1.0, localZ);
        double _snowman5 = PerlinNoiseSampler.grad(this.getGradient(_snowman), localX - 1.0, localY - 1.0, localZ);
        double _snowman6 = PerlinNoiseSampler.grad(this.getGradient(_snowman + 1), localX, localY, localZ - 1.0);
        double _snowman7 = PerlinNoiseSampler.grad(this.getGradient(_snowman + 1), localX - 1.0, localY, localZ - 1.0);
        double _snowman8 = PerlinNoiseSampler.grad(this.getGradient(_snowman + 1), localX, localY - 1.0, localZ - 1.0);
        double _snowman9 = PerlinNoiseSampler.grad(this.getGradient(_snowman + 1), localX - 1.0, localY - 1.0, localZ - 1.0);
        return MathHelper.lerp3(fadeLocalX, fadeLocalY, fadeLocalZ, _snowman2, _snowman3, _snowman4, _snowman5, _snowman6, _snowman7, _snowman8, _snowman9);
    }
}

