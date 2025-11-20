/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.biome.source;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;

public class TheEndBiomeSource
extends BiomeSource {
    public static final Codec<TheEndBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(theEndBiomeSource -> theEndBiomeSource.biomeRegistry), (App)Codec.LONG.fieldOf("seed").stable().forGetter(theEndBiomeSource -> theEndBiomeSource.seed)).apply((Applicative)instance, instance.stable(TheEndBiomeSource::new)));
    private final SimplexNoiseSampler noise;
    private final Registry<Biome> biomeRegistry;
    private final long seed;
    private final Biome centerBiome;
    private final Biome highlandsBiome;
    private final Biome midlandsBiome;
    private final Biome smallIslandsBiome;
    private final Biome barrensBiome;

    public TheEndBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        this(biomeRegistry, seed, biomeRegistry.getOrThrow(BiomeKeys.THE_END), biomeRegistry.getOrThrow(BiomeKeys.END_HIGHLANDS), biomeRegistry.getOrThrow(BiomeKeys.END_MIDLANDS), biomeRegistry.getOrThrow(BiomeKeys.SMALL_END_ISLANDS), biomeRegistry.getOrThrow(BiomeKeys.END_BARRENS));
    }

    private TheEndBiomeSource(Registry<Biome> biomeRegistry, long seed, Biome centerBiome, Biome highlandsBiome, Biome midlandsBiome, Biome smallIslandsBiome, Biome barrensBiome) {
        super((List<Biome>)ImmutableList.of((Object)centerBiome, (Object)highlandsBiome, (Object)midlandsBiome, (Object)smallIslandsBiome, (Object)barrensBiome));
        this.biomeRegistry = biomeRegistry;
        this.seed = seed;
        this.centerBiome = centerBiome;
        this.highlandsBiome = highlandsBiome;
        this.midlandsBiome = midlandsBiome;
        this.smallIslandsBiome = smallIslandsBiome;
        this.barrensBiome = barrensBiome;
        ChunkRandom chunkRandom = new ChunkRandom(seed);
        chunkRandom.consume(17292);
        this.noise = new SimplexNoiseSampler(chunkRandom);
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new TheEndBiomeSource(this.biomeRegistry, seed, this.centerBiome, this.highlandsBiome, this.midlandsBiome, this.smallIslandsBiome, this.barrensBiome);
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        int n = biomeX >> 2;
        _snowman = biomeZ >> 2;
        if ((long)n * (long)n + (long)_snowman * (long)_snowman <= 4096L) {
            return this.centerBiome;
        }
        float _snowman2 = TheEndBiomeSource.getNoiseAt(this.noise, n * 2 + 1, _snowman * 2 + 1);
        if (_snowman2 > 40.0f) {
            return this.highlandsBiome;
        }
        if (_snowman2 >= 0.0f) {
            return this.midlandsBiome;
        }
        if (_snowman2 < -20.0f) {
            return this.smallIslandsBiome;
        }
        return this.barrensBiome;
    }

    public boolean matches(long seed) {
        return this.seed == seed;
    }

    public static float getNoiseAt(SimplexNoiseSampler simplexNoiseSampler, int n, int n2) {
        _snowman = n / 2;
        _snowman = n2 / 2;
        _snowman = n % 2;
        _snowman = n2 % 2;
        float _snowman6 = 100.0f - MathHelper.sqrt(n * n + n2 * n2) * 8.0f;
        _snowman6 = MathHelper.clamp(_snowman6, -100.0f, 80.0f);
        for (int i = -12; i <= 12; ++i) {
            for (_snowman = -12; _snowman <= 12; ++_snowman) {
                long l = _snowman + i;
                _snowman = _snowman + _snowman;
                if (l * l + _snowman * _snowman <= 4096L || !(simplexNoiseSampler.sample(l, _snowman) < (double)-0.9f)) continue;
                float _snowman2 = (MathHelper.abs(l) * 3439.0f + MathHelper.abs(_snowman) * 147.0f) % 13.0f + 9.0f;
                float _snowman3 = _snowman - i * 2;
                float _snowman4 = _snowman - _snowman * 2;
                float _snowman5 = 100.0f - MathHelper.sqrt(_snowman3 * _snowman3 + _snowman4 * _snowman4) * _snowman2;
                _snowman5 = MathHelper.clamp(_snowman5, -100.0f, 80.0f);
                _snowman6 = Math.max(_snowman6, _snowman5);
            }
        }
        return _snowman6;
    }
}

