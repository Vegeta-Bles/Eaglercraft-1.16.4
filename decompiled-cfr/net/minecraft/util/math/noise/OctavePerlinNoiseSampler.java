/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
 *  it.unimi.dsi.fastutil.ints.IntRBTreeSet
 *  it.unimi.dsi.fastutil.ints.IntSortedSet
 *  javax.annotation.Nullable
 */
package net.minecraft.util.math.noise;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;

public class OctavePerlinNoiseSampler
implements NoiseSampler {
    private final PerlinNoiseSampler[] octaveSamplers;
    private final DoubleList field_26445;
    private final double field_20659;
    private final double field_20660;

    public OctavePerlinNoiseSampler(ChunkRandom random, IntStream octaves) {
        this(random, (List)octaves.boxed().collect(ImmutableList.toImmutableList()));
    }

    public OctavePerlinNoiseSampler(ChunkRandom random, List<Integer> octaves) {
        this(random, (IntSortedSet)new IntRBTreeSet(octaves));
    }

    public static OctavePerlinNoiseSampler method_30847(ChunkRandom chunkRandom, int n, DoubleList doubleList) {
        return new OctavePerlinNoiseSampler(chunkRandom, (Pair<Integer, DoubleList>)Pair.of((Object)n, (Object)doubleList));
    }

    private static Pair<Integer, DoubleList> method_30848(IntSortedSet intSortedSet) {
        if (intSortedSet.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int n = -intSortedSet.firstInt();
        _snowman = n + (_snowman = intSortedSet.lastInt()) + 1;
        if (_snowman < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        DoubleArrayList _snowman2 = new DoubleArrayList(new double[_snowman]);
        IntBidirectionalIterator _snowman3 = intSortedSet.iterator();
        while (_snowman3.hasNext()) {
            _snowman = _snowman3.nextInt();
            _snowman2.set(_snowman + n, 1.0);
        }
        return Pair.of((Object)(-n), (Object)_snowman2);
    }

    private OctavePerlinNoiseSampler(ChunkRandom random, IntSortedSet octaves) {
        this(random, OctavePerlinNoiseSampler.method_30848(octaves));
    }

    private OctavePerlinNoiseSampler(ChunkRandom chunkRandom2, Pair<Integer, DoubleList> pair) {
        int n;
        int n2 = (Integer)pair.getFirst();
        this.field_26445 = (DoubleList)pair.getSecond();
        PerlinNoiseSampler _snowman2 = new PerlinNoiseSampler(chunkRandom2);
        _snowman = this.field_26445.size();
        n = -n2;
        this.octaveSamplers = new PerlinNoiseSampler[_snowman];
        if (n >= 0 && n < _snowman && (_snowman = this.field_26445.getDouble(n)) != 0.0) {
            this.octaveSamplers[n] = _snowman2;
        }
        for (_snowman = n - 1; _snowman >= 0; --_snowman) {
            ChunkRandom chunkRandom2;
            if (_snowman < _snowman) {
                double d = this.field_26445.getDouble(_snowman);
                if (d != 0.0) {
                    this.octaveSamplers[_snowman] = new PerlinNoiseSampler(chunkRandom2);
                    continue;
                }
                chunkRandom2.consume(262);
                continue;
            }
            chunkRandom2.consume(262);
        }
        if (n < _snowman - 1) {
            long l = (long)(_snowman2.sample(0.0, 0.0, 0.0, 0.0, 0.0) * 9.223372036854776E18);
            ChunkRandom _snowman3 = new ChunkRandom(l);
            for (int i = n + 1; i < _snowman; ++i) {
                if (i >= 0) {
                    double d = this.field_26445.getDouble(i);
                    if (d != 0.0) {
                        this.octaveSamplers[i] = new PerlinNoiseSampler(_snowman3);
                        continue;
                    }
                    _snowman3.consume(262);
                    continue;
                }
                _snowman3.consume(262);
            }
        }
        this.field_20660 = Math.pow(2.0, -n);
        this.field_20659 = Math.pow(2.0, _snowman - 1) / (Math.pow(2.0, _snowman) - 1.0);
    }

    public double sample(double x, double y, double z) {
        return this.sample(x, y, z, 0.0, 0.0, false);
    }

    public double sample(double x, double y, double z, double d, double d2, boolean bl) {
        double d3 = 0.0;
        _snowman = this.field_20660;
        _snowman = this.field_20659;
        for (int i = 0; i < this.octaveSamplers.length; ++i) {
            PerlinNoiseSampler perlinNoiseSampler = this.octaveSamplers[i];
            if (perlinNoiseSampler != null) {
                d3 += this.field_26445.getDouble(i) * perlinNoiseSampler.sample(OctavePerlinNoiseSampler.maintainPrecision(x * _snowman), bl ? -perlinNoiseSampler.originY : OctavePerlinNoiseSampler.maintainPrecision(y * _snowman), OctavePerlinNoiseSampler.maintainPrecision(z * _snowman), d * _snowman, d2 * _snowman) * _snowman;
            }
            _snowman *= 2.0;
            _snowman /= 2.0;
        }
        return d3;
    }

    @Nullable
    public PerlinNoiseSampler getOctave(int octave) {
        return this.octaveSamplers[this.octaveSamplers.length - 1 - octave];
    }

    public static double maintainPrecision(double d) {
        return d - (double)MathHelper.lfloor(d / 3.3554432E7 + 0.5) * 3.3554432E7;
    }

    @Override
    public double sample(double x, double y, double d, double d2) {
        return this.sample(x, y, 0.0, d, d2, false);
    }
}

