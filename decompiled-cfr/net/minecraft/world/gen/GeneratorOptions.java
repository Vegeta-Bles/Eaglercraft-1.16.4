/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonObject
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.gen;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.DebugChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneratorOptions {
    public static final Codec<GeneratorOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.LONG.fieldOf("seed").stable().forGetter(GeneratorOptions::getSeed), (App)Codec.BOOL.fieldOf("generate_features").orElse((Object)true).stable().forGetter(GeneratorOptions::shouldGenerateStructures), (App)Codec.BOOL.fieldOf("bonus_chest").orElse((Object)false).stable().forGetter(GeneratorOptions::hasBonusChest), (App)SimpleRegistry.createRegistryCodec(Registry.DIMENSION_OPTIONS, Lifecycle.stable(), DimensionOptions.CODEC).xmap(DimensionOptions::method_29569, Function.identity()).fieldOf("dimensions").forGetter(GeneratorOptions::getDimensions), (App)Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(generatorOptions -> generatorOptions.legacyCustomOptions)).apply((Applicative)instance, instance.stable(GeneratorOptions::new))).comapFlatMap(GeneratorOptions::method_28610, Function.identity());
    private static final Logger LOGGER = LogManager.getLogger();
    private final long seed;
    private final boolean generateStructures;
    private final boolean bonusChest;
    private final SimpleRegistry<DimensionOptions> options;
    private final Optional<String> legacyCustomOptions;

    private DataResult<GeneratorOptions> method_28610() {
        DimensionOptions dimensionOptions = this.options.get(DimensionOptions.OVERWORLD);
        if (dimensionOptions == null) {
            return DataResult.error((String)"Overworld settings missing");
        }
        if (this.method_28611()) {
            return DataResult.success((Object)this, (Lifecycle)Lifecycle.stable());
        }
        return DataResult.success((Object)this);
    }

    private boolean method_28611() {
        return DimensionOptions.method_29567(this.seed, this.options);
    }

    public GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> simpleRegistry) {
        this(seed, generateStructures, bonusChest, simpleRegistry, Optional.empty());
        DimensionOptions dimensionOptions = simpleRegistry.get(DimensionOptions.OVERWORLD);
        if (dimensionOptions == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
    }

    private GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> simpleRegistry, Optional<String> legacyCustomOptions) {
        this.seed = seed;
        this.generateStructures = generateStructures;
        this.bonusChest = bonusChest;
        this.options = simpleRegistry;
        this.legacyCustomOptions = legacyCustomOptions;
    }

    public static GeneratorOptions method_31112(DynamicRegistryManager dynamicRegistryManager) {
        MutableRegistry<Biome> mutableRegistry = dynamicRegistryManager.get(Registry.BIOME_KEY);
        int _snowman2 = "North Carolina".hashCode();
        MutableRegistry<DimensionType> _snowman3 = dynamicRegistryManager.get(Registry.DIMENSION_TYPE_KEY);
        MutableRegistry<ChunkGeneratorSettings> _snowman4 = dynamicRegistryManager.get(Registry.NOISE_SETTINGS_WORLDGEN);
        return new GeneratorOptions(_snowman2, true, true, GeneratorOptions.method_28608(_snowman3, DimensionType.createDefaultDimensionOptions(_snowman3, mutableRegistry, _snowman4, _snowman2), GeneratorOptions.createOverworldGenerator(mutableRegistry, _snowman4, _snowman2)));
    }

    public static GeneratorOptions getDefaultOptions(Registry<DimensionType> registry, Registry<Biome> registry2, Registry<ChunkGeneratorSettings> registry3) {
        long l = new Random().nextLong();
        return new GeneratorOptions(l, true, false, GeneratorOptions.method_28608(registry, DimensionType.createDefaultDimensionOptions(registry, registry2, registry3, l), GeneratorOptions.createOverworldGenerator(registry2, registry3, l)));
    }

    public static NoiseChunkGenerator createOverworldGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
        return new NoiseChunkGenerator(new VanillaLayeredBiomeSource(seed, false, false, biomeRegistry), seed, () -> chunkGeneratorSettingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD));
    }

    public long getSeed() {
        return this.seed;
    }

    public boolean shouldGenerateStructures() {
        return this.generateStructures;
    }

    public boolean hasBonusChest() {
        return this.bonusChest;
    }

    public static SimpleRegistry<DimensionOptions> method_28608(Registry<DimensionType> registry, SimpleRegistry<DimensionOptions> simpleRegistry, ChunkGenerator chunkGenerator) {
        DimensionOptions dimensionOptions = simpleRegistry.get(DimensionOptions.OVERWORLD);
        Supplier<DimensionType> _snowman2 = () -> dimensionOptions == null ? registry.getOrThrow(DimensionType.OVERWORLD_REGISTRY_KEY) : dimensionOptions.getDimensionType();
        return GeneratorOptions.method_29962(simpleRegistry, _snowman2, chunkGenerator);
    }

    public static SimpleRegistry<DimensionOptions> method_29962(SimpleRegistry<DimensionOptions> simpleRegistry, Supplier<DimensionType> supplier, ChunkGenerator chunkGenerator) {
        SimpleRegistry<DimensionOptions> simpleRegistry2 = new SimpleRegistry<DimensionOptions>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());
        simpleRegistry2.add(DimensionOptions.OVERWORLD, new DimensionOptions(supplier, chunkGenerator), Lifecycle.stable());
        for (Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions> entry : simpleRegistry.getEntries()) {
            RegistryKey<DimensionOptions> registryKey = entry.getKey();
            if (registryKey == DimensionOptions.OVERWORLD) continue;
            simpleRegistry2.add(registryKey, entry.getValue(), simpleRegistry.getEntryLifecycle(entry.getValue()));
        }
        return simpleRegistry2;
    }

    public SimpleRegistry<DimensionOptions> getDimensions() {
        return this.options;
    }

    public ChunkGenerator getChunkGenerator() {
        DimensionOptions dimensionOptions = this.options.get(DimensionOptions.OVERWORLD);
        if (dimensionOptions == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
        return dimensionOptions.getChunkGenerator();
    }

    public ImmutableSet<RegistryKey<World>> getWorlds() {
        return (ImmutableSet)this.getDimensions().getEntries().stream().map(entry -> RegistryKey.of(Registry.DIMENSION, ((RegistryKey)entry.getKey()).getValue())).collect(ImmutableSet.toImmutableSet());
    }

    public boolean isDebugWorld() {
        return this.getChunkGenerator() instanceof DebugChunkGenerator;
    }

    public boolean isFlatWorld() {
        return this.getChunkGenerator() instanceof FlatChunkGenerator;
    }

    public boolean isLegacyCustomizedType() {
        return this.legacyCustomOptions.isPresent();
    }

    public GeneratorOptions withBonusChest() {
        return new GeneratorOptions(this.seed, this.generateStructures, true, this.options, this.legacyCustomOptions);
    }

    public GeneratorOptions toggleGenerateStructures() {
        return new GeneratorOptions(this.seed, !this.generateStructures, this.bonusChest, this.options);
    }

    public GeneratorOptions toggleBonusChest() {
        return new GeneratorOptions(this.seed, this.generateStructures, !this.bonusChest, this.options);
    }

    public static GeneratorOptions fromProperties(DynamicRegistryManager dynamicRegistryManager2, Properties properties) {
        DynamicRegistryManager dynamicRegistryManager2;
        String string2 = (String)MoreObjects.firstNonNull((Object)((String)properties.get("generator-settings")), (Object)"");
        properties.put("generator-settings", string2);
        _snowman = (String)MoreObjects.firstNonNull((Object)((String)properties.get("level-seed")), (Object)"");
        properties.put("level-seed", _snowman);
        _snowman = (String)properties.get("generate-structures");
        boolean _snowman2 = _snowman == null || Boolean.parseBoolean(_snowman);
        properties.put("generate-structures", Objects.toString(_snowman2));
        _snowman = (String)properties.get("level-type");
        _snowman = Optional.ofNullable(_snowman).map(string -> string.toLowerCase(Locale.ROOT)).orElse("default");
        properties.put("level-type", _snowman);
        long _snowman3 = new Random().nextLong();
        if (!_snowman.isEmpty()) {
            try {
                long l = Long.parseLong(_snowman);
                if (l != 0L) {
                    _snowman3 = l;
                }
            }
            catch (NumberFormatException numberFormatException) {
                _snowman3 = _snowman.hashCode();
            }
        }
        MutableRegistry<DimensionType> _snowman4 = dynamicRegistryManager2.get(Registry.DIMENSION_TYPE_KEY);
        MutableRegistry<Biome> _snowman5 = dynamicRegistryManager2.get(Registry.BIOME_KEY);
        MutableRegistry<ChunkGeneratorSettings> _snowman6 = dynamicRegistryManager2.get(Registry.NOISE_SETTINGS_WORLDGEN);
        SimpleRegistry<DimensionOptions> _snowman7 = DimensionType.createDefaultDimensionOptions(_snowman4, _snowman5, _snowman6, _snowman3);
        switch (_snowman) {
            case "flat": {
                JsonObject jsonObject = !string2.isEmpty() ? JsonHelper.deserialize(string2) : new JsonObject();
                Dynamic _snowman8 = new Dynamic((DynamicOps)JsonOps.INSTANCE, (Object)jsonObject);
                return new GeneratorOptions(_snowman3, _snowman2, false, GeneratorOptions.method_28608(_snowman4, _snowman7, new FlatChunkGenerator(FlatChunkGeneratorConfig.CODEC.parse(_snowman8).resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).orElseGet(() -> FlatChunkGeneratorConfig.getDefaultConfig(_snowman5)))));
            }
            case "debug_all_block_states": {
                return new GeneratorOptions(_snowman3, _snowman2, false, GeneratorOptions.method_28608(_snowman4, _snowman7, new DebugChunkGenerator(_snowman5)));
            }
            case "amplified": {
                return new GeneratorOptions(_snowman3, _snowman2, false, GeneratorOptions.method_28608(_snowman4, _snowman7, new NoiseChunkGenerator(new VanillaLayeredBiomeSource(_snowman3, false, false, _snowman5), _snowman3, () -> _snowman6.getOrThrow(ChunkGeneratorSettings.AMPLIFIED))));
            }
            case "largebiomes": {
                return new GeneratorOptions(_snowman3, _snowman2, false, GeneratorOptions.method_28608(_snowman4, _snowman7, new NoiseChunkGenerator(new VanillaLayeredBiomeSource(_snowman3, false, true, _snowman5), _snowman3, () -> _snowman6.getOrThrow(ChunkGeneratorSettings.OVERWORLD))));
            }
        }
        return new GeneratorOptions(_snowman3, _snowman2, false, GeneratorOptions.method_28608(_snowman4, _snowman7, GeneratorOptions.createOverworldGenerator(_snowman5, _snowman6, _snowman3)));
    }

    public GeneratorOptions withHardcore(boolean hardcore, OptionalLong seed) {
        SimpleRegistry<DimensionOptions> simpleRegistry;
        long l = seed.orElse(this.seed);
        if (seed.isPresent()) {
            simpleRegistry = new SimpleRegistry<DimensionOptions>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());
            long _snowman2 = seed.getAsLong();
            for (Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions> entry : this.options.getEntries()) {
                RegistryKey<DimensionOptions> registryKey = entry.getKey();
                simpleRegistry.add(registryKey, new DimensionOptions(entry.getValue().getDimensionTypeSupplier(), entry.getValue().getChunkGenerator().withSeed(_snowman2)), this.options.getEntryLifecycle(entry.getValue()));
            }
        } else {
            simpleRegistry = this.options;
        }
        GeneratorOptions _snowman3 = this.isDebugWorld() ? new GeneratorOptions(l, false, false, simpleRegistry) : new GeneratorOptions(l, this.shouldGenerateStructures(), this.hasBonusChest() && !hardcore, simpleRegistry);
        return _snowman3;
    }
}

