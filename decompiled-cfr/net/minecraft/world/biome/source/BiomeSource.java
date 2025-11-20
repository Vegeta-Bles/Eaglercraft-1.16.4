/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.biome.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.CheckerboardBiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class BiomeSource
implements BiomeAccess.Storage {
    public static final Codec<BiomeSource> CODEC;
    protected final Map<StructureFeature<?>, Boolean> structureFeatures = Maps.newHashMap();
    protected final Set<BlockState> topMaterials = Sets.newHashSet();
    protected final List<Biome> biomes;

    protected BiomeSource(Stream<Supplier<Biome>> stream) {
        this((List)stream.map(Supplier::get).collect(ImmutableList.toImmutableList()));
    }

    protected BiomeSource(List<Biome> biomes) {
        this.biomes = biomes;
    }

    protected abstract Codec<? extends BiomeSource> getCodec();

    public abstract BiomeSource withSeed(long var1);

    public List<Biome> getBiomes() {
        return this.biomes;
    }

    public Set<Biome> getBiomesInArea(int x, int y, int z, int radius) {
        int n = x - radius >> 2;
        _snowman = y - radius >> 2;
        _snowman = z - radius >> 2;
        _snowman = x + radius >> 2;
        _snowman = y + radius >> 2;
        _snowman = z + radius >> 2;
        _snowman = _snowman - n + 1;
        _snowman = _snowman - _snowman + 1;
        _snowman = _snowman - _snowman + 1;
        HashSet _snowman2 = Sets.newHashSet();
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    _snowman = n + _snowman;
                    _snowman = _snowman + _snowman;
                    _snowman = _snowman + _snowman;
                    _snowman2.add(this.getBiomeForNoiseGen(_snowman, _snowman, _snowman));
                }
            }
        }
        return _snowman2;
    }

    @Nullable
    public BlockPos locateBiome(int x, int y, int z, int radius, Predicate<Biome> predicate, Random random) {
        return this.locateBiome(x, y, z, radius, 1, predicate, random, false);
    }

    @Nullable
    public BlockPos locateBiome(int x, int y, int z, int radius, int n, Predicate<Biome> predicate, Random random, boolean bl) {
        int n2 = x >> 2;
        _snowman = z >> 2;
        _snowman = radius >> 2;
        _snowman = y >> 2;
        BlockPos _snowman2 = null;
        _snowman = 0;
        for (_snowman = _snowman = bl ? 0 : _snowman; _snowman <= _snowman; _snowman += n) {
            for (_snowman = -_snowman; _snowman <= _snowman; _snowman += n) {
                boolean bl2 = Math.abs(_snowman) == _snowman;
                for (int i = -_snowman; i <= _snowman; i += n) {
                    if (bl) {
                        int n3 = _snowman = Math.abs(i) == _snowman ? 1 : 0;
                        if (_snowman == 0 && !bl2) continue;
                    }
                    if (!predicate.test(this.getBiomeForNoiseGen(_snowman = n2 + i, _snowman, _snowman = _snowman + _snowman))) continue;
                    if (_snowman2 == null || random.nextInt(_snowman + 1) == 0) {
                        _snowman2 = new BlockPos(_snowman << 2, y, _snowman << 2);
                        if (bl) {
                            return _snowman2;
                        }
                    }
                    ++_snowman;
                }
            }
        }
        return _snowman2;
    }

    public boolean hasStructureFeature(StructureFeature<?> feature) {
        return this.structureFeatures.computeIfAbsent(feature, structureFeature -> this.biomes.stream().anyMatch(biome -> biome.getGenerationSettings().hasStructureFeature((StructureFeature<?>)structureFeature)));
    }

    public Set<BlockState> getTopMaterials() {
        if (this.topMaterials.isEmpty()) {
            for (Biome biome : this.biomes) {
                this.topMaterials.add(biome.getGenerationSettings().getSurfaceConfig().getTopMaterial());
            }
        }
        return this.topMaterials;
    }

    static {
        Registry.register(Registry.BIOME_SOURCE, "fixed", FixedBiomeSource.CODEC);
        Registry.register(Registry.BIOME_SOURCE, "multi_noise", MultiNoiseBiomeSource.CODEC);
        Registry.register(Registry.BIOME_SOURCE, "checkerboard", CheckerboardBiomeSource.CODEC);
        Registry.register(Registry.BIOME_SOURCE, "vanilla_layered", VanillaLayeredBiomeSource.CODEC);
        Registry.register(Registry.BIOME_SOURCE, "the_end", TheEndBiomeSource.CODEC);
        CODEC = Registry.BIOME_SOURCE.dispatchStable(BiomeSource::getCodec, Function.identity());
    }
}

