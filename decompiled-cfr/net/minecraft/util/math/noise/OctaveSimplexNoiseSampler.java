/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  it.unimi.dsi.fastutil.ints.IntRBTreeSet
 *  it.unimi.dsi.fastutil.ints.IntSortedSet
 */
package net.minecraft.util.math.noise;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;

public class OctaveSimplexNoiseSampler
implements NoiseSampler {
    private final SimplexNoiseSampler[] octaveSamplers;
    private final double field_20661;
    private final double field_20662;

    public OctaveSimplexNoiseSampler(ChunkRandom chunkRandom, IntStream intStream) {
        this(chunkRandom, (List)intStream.boxed().collect(ImmutableList.toImmutableList()));
    }

    public OctaveSimplexNoiseSampler(ChunkRandom chunkRandom, List<Integer> list) {
        this(chunkRandom, (IntSortedSet)new IntRBTreeSet(list));
    }

    private OctaveSimplexNoiseSampler(ChunkRandom chunkRandom, IntSortedSet intSortedSet) {
        int n;
        if (intSortedSet.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int n2 = -intSortedSet.firstInt();
        _snowman = n2 + (n = intSortedSet.lastInt()) + 1;
        if (_snowman < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        SimplexNoiseSampler _snowman2 = new SimplexNoiseSampler(chunkRandom);
        _snowman = n;
        this.octaveSamplers = new SimplexNoiseSampler[_snowman];
        if (_snowman >= 0 && _snowman < _snowman && intSortedSet.contains(0)) {
            this.octaveSamplers[_snowman] = _snowman2;
        }
        for (_snowman = _snowman + 1; _snowman < _snowman; ++_snowman) {
            if (_snowman >= 0 && intSortedSet.contains(_snowman - _snowman)) {
                this.octaveSamplers[_snowman] = new SimplexNoiseSampler(chunkRandom);
                continue;
            }
            chunkRandom.consume(262);
        }
        if (n > 0) {
            long l = (long)(_snowman2.method_22416(_snowman2.originX, _snowman2.originY, _snowman2.originZ) * 9.223372036854776E18);
            ChunkRandom _snowman3 = new ChunkRandom(l);
            for (int i = _snowman - 1; i >= 0; --i) {
                if (i < _snowman && intSortedSet.contains(_snowman - i)) {
                    this.octaveSamplers[i] = new SimplexNoiseSampler(_snowman3);
                    continue;
                }
                _snowman3.consume(262);
            }
        }
        this.field_20662 = Math.pow(2.0, n);
        this.field_20661 = 1.0 / (Math.pow(2.0, _snowman) - 1.0);
    }

    public double sample(double x, double y, boolean bl) {
        double d = 0.0;
        _snowman = this.field_20662;
        _snowman = this.field_20661;
        for (SimplexNoiseSampler simplexNoiseSampler : this.octaveSamplers) {
            if (simplexNoiseSampler != null) {
                d += simplexNoiseSampler.sample(x * _snowman + (bl ? simplexNoiseSampler.originX : 0.0), y * _snowman + (bl ? simplexNoiseSampler.originY : 0.0)) * _snowman;
            }
            _snowman /= 2.0;
            _snowman *= 2.0;
        }
        return d;
    }

    @Override
    public double sample(double x, double y, double d, double d2) {
        return this.sample(x, y, true) * 0.55;
    }
}

