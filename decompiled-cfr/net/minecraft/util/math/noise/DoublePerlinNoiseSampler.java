/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  it.unimi.dsi.fastutil.doubles.DoubleListIterator
 */
package net.minecraft.util.math.noise;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;

public class DoublePerlinNoiseSampler {
    private final double amplitude;
    private final OctavePerlinNoiseSampler firstSampler;
    private final OctavePerlinNoiseSampler secondSampler;

    public static DoublePerlinNoiseSampler method_30846(ChunkRandom chunkRandom, int n, DoubleList doubleList) {
        return new DoublePerlinNoiseSampler(chunkRandom, n, doubleList);
    }

    private DoublePerlinNoiseSampler(ChunkRandom chunkRandom, int n, DoubleList doubleList) {
        int _snowman4;
        this.firstSampler = OctavePerlinNoiseSampler.method_30847(chunkRandom, n, doubleList);
        this.secondSampler = OctavePerlinNoiseSampler.method_30847(chunkRandom, n, doubleList);
        int _snowman3 = Integer.MAX_VALUE;
        _snowman4 = Integer.MIN_VALUE;
        DoubleListIterator _snowman2 = doubleList.iterator();
        while (_snowman2.hasNext()) {
            _snowman = _snowman2.nextIndex();
            double d = _snowman2.nextDouble();
            if (d == 0.0) continue;
            _snowman3 = Math.min(_snowman3, _snowman);
            _snowman4 = Math.max(_snowman4, _snowman);
        }
        this.amplitude = 0.16666666666666666 / DoublePerlinNoiseSampler.createAmplitude(_snowman4 - _snowman3);
    }

    private static double createAmplitude(int octaves) {
        return 0.1 * (1.0 + 1.0 / (double)(octaves + 1));
    }

    public double sample(double x, double y, double z) {
        double d = x * 1.0181268882175227;
        _snowman = y * 1.0181268882175227;
        _snowman = z * 1.0181268882175227;
        return (this.firstSampler.sample(x, y, z) + this.secondSampler.sample(d, _snowman, _snowman)) * this.amplitude;
    }
}

