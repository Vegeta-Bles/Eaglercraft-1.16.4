/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.gen.chunk;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FillLayerFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlatChunkGeneratorConfig {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Codec<FlatChunkGeneratorConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(flatChunkGeneratorConfig -> flatChunkGeneratorConfig.biomeRegistry), (App)StructuresConfig.CODEC.fieldOf("structures").forGetter(FlatChunkGeneratorConfig::getStructuresConfig), (App)FlatChunkGeneratorLayer.CODEC.listOf().fieldOf("layers").forGetter(FlatChunkGeneratorConfig::getLayers), (App)Codec.BOOL.fieldOf("lakes").orElse((Object)false).forGetter(flatChunkGeneratorConfig -> flatChunkGeneratorConfig.hasLakes), (App)Codec.BOOL.fieldOf("features").orElse((Object)false).forGetter(flatChunkGeneratorConfig -> flatChunkGeneratorConfig.hasFeatures), (App)Biome.REGISTRY_CODEC.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(flatChunkGeneratorConfig -> Optional.of(flatChunkGeneratorConfig.biome))).apply((Applicative)instance, FlatChunkGeneratorConfig::new)).stable();
    private static final Map<StructureFeature<?>, ConfiguredStructureFeature<?, ?>> STRUCTURE_TO_FEATURES = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put(StructureFeature.MINESHAFT, ConfiguredStructureFeatures.MINESHAFT);
        hashMap.put(StructureFeature.VILLAGE, ConfiguredStructureFeatures.VILLAGE_PLAINS);
        hashMap.put(StructureFeature.STRONGHOLD, ConfiguredStructureFeatures.STRONGHOLD);
        hashMap.put(StructureFeature.SWAMP_HUT, ConfiguredStructureFeatures.SWAMP_HUT);
        hashMap.put(StructureFeature.DESERT_PYRAMID, ConfiguredStructureFeatures.DESERT_PYRAMID);
        hashMap.put(StructureFeature.JUNGLE_PYRAMID, ConfiguredStructureFeatures.JUNGLE_PYRAMID);
        hashMap.put(StructureFeature.IGLOO, ConfiguredStructureFeatures.IGLOO);
        hashMap.put(StructureFeature.OCEAN_RUIN, ConfiguredStructureFeatures.OCEAN_RUIN_COLD);
        hashMap.put(StructureFeature.SHIPWRECK, ConfiguredStructureFeatures.SHIPWRECK);
        hashMap.put(StructureFeature.MONUMENT, ConfiguredStructureFeatures.MONUMENT);
        hashMap.put(StructureFeature.END_CITY, ConfiguredStructureFeatures.END_CITY);
        hashMap.put(StructureFeature.MANSION, ConfiguredStructureFeatures.MANSION);
        hashMap.put(StructureFeature.FORTRESS, ConfiguredStructureFeatures.FORTRESS);
        hashMap.put(StructureFeature.PILLAGER_OUTPOST, ConfiguredStructureFeatures.PILLAGER_OUTPOST);
        hashMap.put(StructureFeature.RUINED_PORTAL, ConfiguredStructureFeatures.RUINED_PORTAL);
        hashMap.put(StructureFeature.BASTION_REMNANT, ConfiguredStructureFeatures.BASTION_REMNANT);
    });
    private final Registry<Biome> biomeRegistry;
    private final StructuresConfig structuresConfig;
    private final List<FlatChunkGeneratorLayer> layers = Lists.newArrayList();
    private Supplier<Biome> biome;
    private final BlockState[] layerBlocks = new BlockState[256];
    private boolean hasNoTerrain;
    private boolean hasFeatures = false;
    private boolean hasLakes = false;

    public FlatChunkGeneratorConfig(Registry<Biome> biomeRegistry, StructuresConfig structuresConfig, List<FlatChunkGeneratorLayer> layers, boolean hasLakes, boolean hasFeatures, Optional<Supplier<Biome>> biome) {
        this(structuresConfig, biomeRegistry);
        if (hasLakes) {
            this.enableLakes();
        }
        if (hasFeatures) {
            this.enableFeatures();
        }
        this.layers.addAll(layers);
        this.updateLayerBlocks();
        if (!biome.isPresent()) {
            LOGGER.error("Unknown biome, defaulting to plains");
            this.biome = () -> biomeRegistry.getOrThrow(BiomeKeys.PLAINS);
        } else {
            this.biome = biome.get();
        }
    }

    public FlatChunkGeneratorConfig(StructuresConfig structuresConfig, Registry<Biome> biomeRegistry) {
        this.biomeRegistry = biomeRegistry;
        this.structuresConfig = structuresConfig;
        this.biome = () -> biomeRegistry.getOrThrow(BiomeKeys.PLAINS);
    }

    public FlatChunkGeneratorConfig withStructuresConfig(StructuresConfig structuresConfig) {
        return this.method_29965(this.layers, structuresConfig);
    }

    public FlatChunkGeneratorConfig method_29965(List<FlatChunkGeneratorLayer> list, StructuresConfig structuresConfig) {
        FlatChunkGeneratorConfig flatChunkGeneratorConfig = new FlatChunkGeneratorConfig(structuresConfig, this.biomeRegistry);
        for (FlatChunkGeneratorLayer flatChunkGeneratorLayer : list) {
            flatChunkGeneratorConfig.layers.add(new FlatChunkGeneratorLayer(flatChunkGeneratorLayer.getThickness(), flatChunkGeneratorLayer.getBlockState().getBlock()));
            flatChunkGeneratorConfig.updateLayerBlocks();
        }
        flatChunkGeneratorConfig.setBiome(this.biome);
        if (this.hasFeatures) {
            flatChunkGeneratorConfig.enableFeatures();
        }
        if (this.hasLakes) {
            flatChunkGeneratorConfig.enableLakes();
        }
        return flatChunkGeneratorConfig;
    }

    public void enableFeatures() {
        this.hasFeatures = true;
    }

    public void enableLakes() {
        this.hasLakes = true;
    }

    public Biome createBiome() {
        boolean bl;
        Biome biome = this.getBiome();
        GenerationSettings _snowman2 = biome.getGenerationSettings();
        GenerationSettings.Builder _snowman3 = new GenerationSettings.Builder().surfaceBuilder(_snowman2.getSurfaceBuilder());
        if (this.hasLakes) {
            _snowman3.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_WATER);
            _snowman3.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_LAVA);
        }
        for (Map.Entry<StructureFeature<?>, StructureConfig> entry : this.structuresConfig.getStructures().entrySet()) {
            _snowman3.structureFeature(_snowman2.method_30978(STRUCTURE_TO_FEATURES.get(entry.getKey())));
        }
        boolean bl2 = bl = (!this.hasNoTerrain || this.biomeRegistry.getKey(biome).equals(Optional.of(BiomeKeys.THE_VOID))) && this.hasFeatures;
        if (bl) {
            List<List<Supplier<ConfiguredFeature<?, ?>>>> list = _snowman2.getFeatures();
            for (int n = 0; n < list.size(); ++n) {
                if (n == GenerationStep.Feature.UNDERGROUND_STRUCTURES.ordinal() || n == GenerationStep.Feature.SURFACE_STRUCTURES.ordinal()) continue;
                List<Supplier<ConfiguredFeature<?, ?>>> list2 = list.get(n);
                for (Supplier<ConfiguredFeature<?, ?>> supplier : list2) {
                    _snowman3.feature(n, supplier);
                }
            }
        }
        BlockState[] blockStateArray = this.getLayerBlocks();
        for (int i = 0; i < blockStateArray.length; ++i) {
            BlockState object = blockStateArray[i];
            if (object == null || Heightmap.Type.MOTION_BLOCKING.getBlockPredicate().test(object)) continue;
            this.layerBlocks[i] = null;
            _snowman3.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, Feature.FILL_LAYER.configure(new FillLayerFeatureConfig(i, object)));
        }
        return new Biome.Builder().precipitation(biome.getPrecipitation()).category(biome.getCategory()).depth(biome.getDepth()).scale(biome.getScale()).temperature(biome.getTemperature()).downfall(biome.getDownfall()).effects(biome.getEffects()).generationSettings(_snowman3.build()).spawnSettings(biome.getSpawnSettings()).build();
    }

    public StructuresConfig getStructuresConfig() {
        return this.structuresConfig;
    }

    public Biome getBiome() {
        return this.biome.get();
    }

    public void setBiome(Supplier<Biome> biome) {
        this.biome = biome;
    }

    public List<FlatChunkGeneratorLayer> getLayers() {
        return this.layers;
    }

    public BlockState[] getLayerBlocks() {
        return this.layerBlocks;
    }

    public void updateLayerBlocks() {
        Arrays.fill(this.layerBlocks, 0, this.layerBlocks.length, null);
        int n = 0;
        for (FlatChunkGeneratorLayer flatChunkGeneratorLayer : this.layers) {
            flatChunkGeneratorLayer.setStartY(n);
            n += flatChunkGeneratorLayer.getThickness();
        }
        this.hasNoTerrain = true;
        for (FlatChunkGeneratorLayer flatChunkGeneratorLayer : this.layers) {
            for (int i = flatChunkGeneratorLayer.getStartY(); i < flatChunkGeneratorLayer.getStartY() + flatChunkGeneratorLayer.getThickness(); ++i) {
                BlockState blockState = flatChunkGeneratorLayer.getBlockState();
                if (blockState.isOf(Blocks.AIR)) continue;
                this.hasNoTerrain = false;
                this.layerBlocks[i] = blockState;
            }
        }
    }

    public static FlatChunkGeneratorConfig getDefaultConfig(Registry<Biome> biomeRegistry) {
        StructuresConfig structuresConfig = new StructuresConfig(Optional.of(StructuresConfig.DEFAULT_STRONGHOLD), Maps.newHashMap((Map)ImmutableMap.of(StructureFeature.VILLAGE, (Object)StructuresConfig.DEFAULT_STRUCTURES.get(StructureFeature.VILLAGE))));
        FlatChunkGeneratorConfig _snowman2 = new FlatChunkGeneratorConfig(structuresConfig, biomeRegistry);
        _snowman2.biome = () -> biomeRegistry.getOrThrow(BiomeKeys.PLAINS);
        _snowman2.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        _snowman2.getLayers().add(new FlatChunkGeneratorLayer(2, Blocks.DIRT));
        _snowman2.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK));
        _snowman2.updateLayerBlocks();
        return _snowman2;
    }
}

