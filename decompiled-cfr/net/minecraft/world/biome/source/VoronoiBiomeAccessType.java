/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.source;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeAccessType;
import net.minecraft.world.biome.source.SeedMixer;

public enum VoronoiBiomeAccessType implements BiomeAccessType
{
    INSTANCE;


    @Override
    public Biome getBiome(long seed, int x, int y, int z, BiomeAccess.Storage storage) {
        int _snowman8;
        int _snowman7;
        int _snowman6;
        int n = x - 2;
        _snowman = y - 2;
        _snowman = z - 2;
        _snowman = n >> 2;
        _snowman = _snowman >> 2;
        _snowman = _snowman >> 2;
        double _snowman2 = (double)(n & 3) / 4.0;
        double _snowman3 = (double)(_snowman & 3) / 4.0;
        double _snowman4 = (double)(_snowman & 3) / 4.0;
        double[] _snowman5 = new double[8];
        for (n2 = 0; n2 < 8; ++n2) {
            boolean bl = (n2 & 4) == 0;
            _snowman = (n2 & 2) == 0;
            _snowman6 = (n2 & 1) == 0 ? 1 : 0;
            _snowman7 = bl ? _snowman : _snowman + 1;
            _snowman8 = _snowman ? _snowman : _snowman + 1;
            int _snowman9 = _snowman6 != 0 ? _snowman : _snowman + 1;
            double _snowman10 = bl ? _snowman2 : _snowman2 - 1.0;
            double _snowman11 = _snowman ? _snowman3 : _snowman3 - 1.0;
            double _snowman12 = _snowman6 != 0 ? _snowman4 : _snowman4 - 1.0;
            _snowman5[n2] = VoronoiBiomeAccessType.calcSquaredDistance(seed, _snowman7, _snowman8, _snowman9, _snowman10, _snowman11, _snowman12);
        }
        int n2 = 0;
        double _snowman13 = _snowman5[0];
        for (_snowman6 = 1; _snowman6 < 8; ++_snowman6) {
            if (!(_snowman13 > _snowman5[_snowman6])) continue;
            n2 = _snowman6;
            _snowman13 = _snowman5[_snowman6];
        }
        _snowman6 = (n2 & 4) == 0 ? _snowman : _snowman + 1;
        _snowman7 = (n2 & 2) == 0 ? _snowman : _snowman + 1;
        _snowman8 = (n2 & 1) == 0 ? _snowman : _snowman + 1;
        return storage.getBiomeForNoiseGen(_snowman6, _snowman7, _snowman8);
    }

    private static double calcSquaredDistance(long seed, int x, int y, int z, double xFraction, double yFraction, double zFraction) {
        long l = seed;
        l = SeedMixer.mixSeed(l, x);
        l = SeedMixer.mixSeed(l, y);
        l = SeedMixer.mixSeed(l, z);
        l = SeedMixer.mixSeed(l, x);
        l = SeedMixer.mixSeed(l, y);
        l = SeedMixer.mixSeed(l, z);
        double _snowman2 = VoronoiBiomeAccessType.distribute(l);
        l = SeedMixer.mixSeed(l, seed);
        double _snowman3 = VoronoiBiomeAccessType.distribute(l);
        l = SeedMixer.mixSeed(l, seed);
        double _snowman4 = VoronoiBiomeAccessType.distribute(l);
        return VoronoiBiomeAccessType.square(zFraction + _snowman4) + VoronoiBiomeAccessType.square(yFraction + _snowman3) + VoronoiBiomeAccessType.square(xFraction + _snowman2);
    }

    private static double distribute(long seed) {
        double d = (double)((int)Math.floorMod(seed >> 24, 1024L)) / 1024.0;
        return (d - 0.5) * 0.9;
    }

    private static double square(double d) {
        return d * d;
    }
}

