/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

public final class DimensionOptions {
    public static final Codec<DimensionOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)DimensionType.REGISTRY_CODEC.fieldOf("type").forGetter(DimensionOptions::getDimensionTypeSupplier), (App)ChunkGenerator.CODEC.fieldOf("generator").forGetter(DimensionOptions::getChunkGenerator)).apply((Applicative)instance, instance.stable(DimensionOptions::new)));
    public static final RegistryKey<DimensionOptions> OVERWORLD = RegistryKey.of(Registry.DIMENSION_OPTIONS, new Identifier("overworld"));
    public static final RegistryKey<DimensionOptions> NETHER = RegistryKey.of(Registry.DIMENSION_OPTIONS, new Identifier("the_nether"));
    public static final RegistryKey<DimensionOptions> END = RegistryKey.of(Registry.DIMENSION_OPTIONS, new Identifier("the_end"));
    private static final LinkedHashSet<RegistryKey<DimensionOptions>> BASE_DIMENSIONS = Sets.newLinkedHashSet((Iterable)ImmutableList.of(OVERWORLD, NETHER, END));
    private final Supplier<DimensionType> dimensionTypeSupplier;
    private final ChunkGenerator chunkGenerator;

    public DimensionOptions(Supplier<DimensionType> typeSupplier, ChunkGenerator chunkGenerator) {
        this.dimensionTypeSupplier = typeSupplier;
        this.chunkGenerator = chunkGenerator;
    }

    public Supplier<DimensionType> getDimensionTypeSupplier() {
        return this.dimensionTypeSupplier;
    }

    public DimensionType getDimensionType() {
        return this.dimensionTypeSupplier.get();
    }

    public ChunkGenerator getChunkGenerator() {
        return this.chunkGenerator;
    }

    public static SimpleRegistry<DimensionOptions> method_29569(SimpleRegistry<DimensionOptions> simpleRegistry3) {
        SimpleRegistry<DimensionOptions> simpleRegistry2 = new SimpleRegistry<DimensionOptions>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());
        for (RegistryKey registryKey : BASE_DIMENSIONS) {
            DimensionOptions object = simpleRegistry3.get(registryKey);
            if (object == null) continue;
            simpleRegistry2.add(registryKey, object, simpleRegistry3.getEntryLifecycle(object));
        }
        for (Map.Entry entry : simpleRegistry3.getEntries()) {
            RegistryKey registryKey = (RegistryKey)entry.getKey();
            if (BASE_DIMENSIONS.contains(registryKey)) continue;
            simpleRegistry2.add(registryKey, entry.getValue(), simpleRegistry3.getEntryLifecycle((DimensionOptions)entry.getValue()));
        }
        return simpleRegistry2;
    }

    public static boolean method_29567(long seed, SimpleRegistry<DimensionOptions> simpleRegistry) {
        ArrayList arrayList = Lists.newArrayList(simpleRegistry.getEntries());
        if (arrayList.size() != BASE_DIMENSIONS.size()) {
            return false;
        }
        Map.Entry _snowman2 = (Map.Entry)arrayList.get(0);
        Map.Entry _snowman3 = (Map.Entry)arrayList.get(1);
        Map.Entry _snowman4 = (Map.Entry)arrayList.get(2);
        if (_snowman2.getKey() != OVERWORLD || _snowman3.getKey() != NETHER || _snowman4.getKey() != END) {
            return false;
        }
        if (!((DimensionOptions)_snowman2.getValue()).getDimensionType().equals(DimensionType.OVERWORLD) && ((DimensionOptions)_snowman2.getValue()).getDimensionType() != DimensionType.OVERWORLD_CAVES) {
            return false;
        }
        if (!((DimensionOptions)_snowman3.getValue()).getDimensionType().equals(DimensionType.THE_NETHER)) {
            return false;
        }
        if (!((DimensionOptions)_snowman4.getValue()).getDimensionType().equals(DimensionType.THE_END)) {
            return false;
        }
        if (!(((DimensionOptions)_snowman3.getValue()).getChunkGenerator() instanceof NoiseChunkGenerator) || !(((DimensionOptions)_snowman4.getValue()).getChunkGenerator() instanceof NoiseChunkGenerator)) {
            return false;
        }
        NoiseChunkGenerator _snowman5 = (NoiseChunkGenerator)((DimensionOptions)_snowman3.getValue()).getChunkGenerator();
        NoiseChunkGenerator _snowman6 = (NoiseChunkGenerator)((DimensionOptions)_snowman4.getValue()).getChunkGenerator();
        if (!_snowman5.matchesSettings(seed, ChunkGeneratorSettings.NETHER)) {
            return false;
        }
        if (!_snowman6.matchesSettings(seed, ChunkGeneratorSettings.END)) {
            return false;
        }
        if (!(_snowman5.getBiomeSource() instanceof MultiNoiseBiomeSource)) {
            return false;
        }
        MultiNoiseBiomeSource _snowman7 = (MultiNoiseBiomeSource)_snowman5.getBiomeSource();
        if (!_snowman7.matchesInstance(seed)) {
            return false;
        }
        if (!(_snowman6.getBiomeSource() instanceof TheEndBiomeSource)) {
            return false;
        }
        TheEndBiomeSource _snowman8 = (TheEndBiomeSource)_snowman6.getBiomeSource();
        return _snowman8.matches(seed);
    }
}

