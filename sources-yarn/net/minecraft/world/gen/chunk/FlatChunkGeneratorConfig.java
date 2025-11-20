package net.minecraft.world.gen.chunk;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
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
   public static final Codec<FlatChunkGeneratorConfig> CODEC = RecordCodecBuilder.create(
         _snowman -> _snowman.group(
                  RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(_snowmanx -> _snowmanx.biomeRegistry),
                  StructuresConfig.CODEC.fieldOf("structures").forGetter(FlatChunkGeneratorConfig::getStructuresConfig),
                  FlatChunkGeneratorLayer.CODEC.listOf().fieldOf("layers").forGetter(FlatChunkGeneratorConfig::getLayers),
                  Codec.BOOL.fieldOf("lakes").orElse(false).forGetter(_snowmanx -> _snowmanx.hasLakes),
                  Codec.BOOL.fieldOf("features").orElse(false).forGetter(_snowmanx -> _snowmanx.hasFeatures),
                  Biome.REGISTRY_CODEC.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(_snowmanx -> Optional.of(_snowmanx.biome))
               )
               .apply(_snowman, FlatChunkGeneratorConfig::new)
      )
      .stable();
   private static final Map<StructureFeature<?>, ConfiguredStructureFeature<?, ?>> STRUCTURE_TO_FEATURES = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(StructureFeature.MINESHAFT, ConfiguredStructureFeatures.MINESHAFT);
      _snowman.put(StructureFeature.VILLAGE, ConfiguredStructureFeatures.VILLAGE_PLAINS);
      _snowman.put(StructureFeature.STRONGHOLD, ConfiguredStructureFeatures.STRONGHOLD);
      _snowman.put(StructureFeature.SWAMP_HUT, ConfiguredStructureFeatures.SWAMP_HUT);
      _snowman.put(StructureFeature.DESERT_PYRAMID, ConfiguredStructureFeatures.DESERT_PYRAMID);
      _snowman.put(StructureFeature.JUNGLE_PYRAMID, ConfiguredStructureFeatures.JUNGLE_PYRAMID);
      _snowman.put(StructureFeature.IGLOO, ConfiguredStructureFeatures.IGLOO);
      _snowman.put(StructureFeature.OCEAN_RUIN, ConfiguredStructureFeatures.OCEAN_RUIN_COLD);
      _snowman.put(StructureFeature.SHIPWRECK, ConfiguredStructureFeatures.SHIPWRECK);
      _snowman.put(StructureFeature.MONUMENT, ConfiguredStructureFeatures.MONUMENT);
      _snowman.put(StructureFeature.END_CITY, ConfiguredStructureFeatures.END_CITY);
      _snowman.put(StructureFeature.MANSION, ConfiguredStructureFeatures.MANSION);
      _snowman.put(StructureFeature.FORTRESS, ConfiguredStructureFeatures.FORTRESS);
      _snowman.put(StructureFeature.PILLAGER_OUTPOST, ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      _snowman.put(StructureFeature.RUINED_PORTAL, ConfiguredStructureFeatures.RUINED_PORTAL);
      _snowman.put(StructureFeature.BASTION_REMNANT, ConfiguredStructureFeatures.BASTION_REMNANT);
   });
   private final Registry<Biome> biomeRegistry;
   private final StructuresConfig structuresConfig;
   private final List<FlatChunkGeneratorLayer> layers = Lists.newArrayList();
   private Supplier<Biome> biome;
   private final BlockState[] layerBlocks = new BlockState[256];
   private boolean hasNoTerrain;
   private boolean hasFeatures = false;
   private boolean hasLakes = false;

   public FlatChunkGeneratorConfig(
      Registry<Biome> biomeRegistry,
      StructuresConfig structuresConfig,
      List<FlatChunkGeneratorLayer> layers,
      boolean hasLakes,
      boolean hasFeatures,
      Optional<Supplier<Biome>> biome
   ) {
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

   public FlatChunkGeneratorConfig method_29965(List<FlatChunkGeneratorLayer> _snowman, StructuresConfig _snowman) {
      FlatChunkGeneratorConfig _snowmanxx = new FlatChunkGeneratorConfig(_snowman, this.biomeRegistry);

      for (FlatChunkGeneratorLayer _snowmanxxx : _snowman) {
         _snowmanxx.layers.add(new FlatChunkGeneratorLayer(_snowmanxxx.getThickness(), _snowmanxxx.getBlockState().getBlock()));
         _snowmanxx.updateLayerBlocks();
      }

      _snowmanxx.setBiome(this.biome);
      if (this.hasFeatures) {
         _snowmanxx.enableFeatures();
      }

      if (this.hasLakes) {
         _snowmanxx.enableLakes();
      }

      return _snowmanxx;
   }

   public void enableFeatures() {
      this.hasFeatures = true;
   }

   public void enableLakes() {
      this.hasLakes = true;
   }

   public Biome createBiome() {
      Biome _snowman = this.getBiome();
      GenerationSettings _snowmanx = _snowman.getGenerationSettings();
      GenerationSettings.Builder _snowmanxx = new GenerationSettings.Builder().surfaceBuilder(_snowmanx.getSurfaceBuilder());
      if (this.hasLakes) {
         _snowmanxx.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_WATER);
         _snowmanxx.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_LAVA);
      }

      for (Entry<StructureFeature<?>, StructureConfig> _snowmanxxx : this.structuresConfig.getStructures().entrySet()) {
         _snowmanxx.structureFeature(_snowmanx.method_30978(STRUCTURE_TO_FEATURES.get(_snowmanxxx.getKey())));
      }

      boolean _snowmanxxx = (!this.hasNoTerrain || this.biomeRegistry.getKey(_snowman).equals(Optional.of(BiomeKeys.THE_VOID))) && this.hasFeatures;
      if (_snowmanxxx) {
         List<List<Supplier<ConfiguredFeature<?, ?>>>> _snowmanxxxx = _snowmanx.getFeatures();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
            if (_snowmanxxxxx != GenerationStep.Feature.UNDERGROUND_STRUCTURES.ordinal() && _snowmanxxxxx != GenerationStep.Feature.SURFACE_STRUCTURES.ordinal()) {
               for (Supplier<ConfiguredFeature<?, ?>> _snowmanxxxxxx : _snowmanxxxx.get(_snowmanxxxxx)) {
                  _snowmanxx.feature(_snowmanxxxxx, _snowmanxxxxxx);
               }
            }
         }
      }

      BlockState[] _snowmanxxxx = this.getLayerBlocks();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx.length; _snowmanxxxxxx++) {
         BlockState _snowmanxxxxxxx = _snowmanxxxx[_snowmanxxxxxx];
         if (_snowmanxxxxxxx != null && !Heightmap.Type.MOTION_BLOCKING.getBlockPredicate().test(_snowmanxxxxxxx)) {
            this.layerBlocks[_snowmanxxxxxx] = null;
            _snowmanxx.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, Feature.FILL_LAYER.configure(new FillLayerFeatureConfig(_snowmanxxxxxx, _snowmanxxxxxxx)));
         }
      }

      return new Biome.Builder()
         .precipitation(_snowman.getPrecipitation())
         .category(_snowman.getCategory())
         .depth(_snowman.getDepth())
         .scale(_snowman.getScale())
         .temperature(_snowman.getTemperature())
         .downfall(_snowman.getDownfall())
         .effects(_snowman.getEffects())
         .generationSettings(_snowmanxx.build())
         .spawnSettings(_snowman.getSpawnSettings())
         .build();
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
      int _snowman = 0;

      for (FlatChunkGeneratorLayer _snowmanx : this.layers) {
         _snowmanx.setStartY(_snowman);
         _snowman += _snowmanx.getThickness();
      }

      this.hasNoTerrain = true;

      for (FlatChunkGeneratorLayer _snowmanx : this.layers) {
         for (int _snowmanxx = _snowmanx.getStartY(); _snowmanxx < _snowmanx.getStartY() + _snowmanx.getThickness(); _snowmanxx++) {
            BlockState _snowmanxxx = _snowmanx.getBlockState();
            if (!_snowmanxxx.isOf(Blocks.AIR)) {
               this.hasNoTerrain = false;
               this.layerBlocks[_snowmanxx] = _snowmanxxx;
            }
         }
      }
   }

   public static FlatChunkGeneratorConfig getDefaultConfig(Registry<Biome> biomeRegistry) {
      StructuresConfig _snowman = new StructuresConfig(
         Optional.of(StructuresConfig.DEFAULT_STRONGHOLD),
         Maps.newHashMap(ImmutableMap.of(StructureFeature.VILLAGE, StructuresConfig.DEFAULT_STRUCTURES.get(StructureFeature.VILLAGE)))
      );
      FlatChunkGeneratorConfig _snowmanx = new FlatChunkGeneratorConfig(_snowman, biomeRegistry);
      _snowmanx.biome = () -> biomeRegistry.getOrThrow(BiomeKeys.PLAINS);
      _snowmanx.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
      _snowmanx.getLayers().add(new FlatChunkGeneratorLayer(2, Blocks.DIRT));
      _snowmanx.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK));
      _snowmanx.updateLayerBlocks();
      return _snowmanx;
   }
}
