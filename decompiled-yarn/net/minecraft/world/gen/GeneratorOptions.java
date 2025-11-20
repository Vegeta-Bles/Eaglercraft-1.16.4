package net.minecraft.world.gen;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
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
   public static final Codec<GeneratorOptions> CODEC = RecordCodecBuilder.create(
         _snowman -> _snowman.group(
                  Codec.LONG.fieldOf("seed").stable().forGetter(GeneratorOptions::getSeed),
                  Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(GeneratorOptions::shouldGenerateStructures),
                  Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(GeneratorOptions::hasBonusChest),
                  SimpleRegistry.createRegistryCodec(Registry.DIMENSION_OPTIONS, Lifecycle.stable(), DimensionOptions.CODEC)
                     .xmap(DimensionOptions::method_29569, Function.identity())
                     .fieldOf("dimensions")
                     .forGetter(GeneratorOptions::getDimensions),
                  Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(_snowmanx -> _snowmanx.legacyCustomOptions)
               )
               .apply(_snowman, _snowman.stable(GeneratorOptions::new))
      )
      .comapFlatMap(GeneratorOptions::method_28610, Function.identity());
   private static final Logger LOGGER = LogManager.getLogger();
   private final long seed;
   private final boolean generateStructures;
   private final boolean bonusChest;
   private final SimpleRegistry<DimensionOptions> options;
   private final Optional<String> legacyCustomOptions;

   private DataResult<GeneratorOptions> method_28610() {
      DimensionOptions _snowman = this.options.get(DimensionOptions.OVERWORLD);
      if (_snowman == null) {
         return DataResult.error("Overworld settings missing");
      } else {
         return this.method_28611() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
      }
   }

   private boolean method_28611() {
      return DimensionOptions.method_29567(this.seed, this.options);
   }

   public GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> _snowman) {
      this(seed, generateStructures, bonusChest, _snowman, Optional.empty());
      DimensionOptions _snowmanx = _snowman.get(DimensionOptions.OVERWORLD);
      if (_snowmanx == null) {
         throw new IllegalStateException("Overworld settings missing");
      }
   }

   private GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> _snowman, Optional<String> legacyCustomOptions) {
      this.seed = seed;
      this.generateStructures = generateStructures;
      this.bonusChest = bonusChest;
      this.options = _snowman;
      this.legacyCustomOptions = legacyCustomOptions;
   }

   public static GeneratorOptions method_31112(DynamicRegistryManager _snowman) {
      Registry<Biome> _snowmanx = _snowman.get(Registry.BIOME_KEY);
      int _snowmanxx = "North Carolina".hashCode();
      Registry<DimensionType> _snowmanxxx = _snowman.get(Registry.DIMENSION_TYPE_KEY);
      Registry<ChunkGeneratorSettings> _snowmanxxxx = _snowman.get(Registry.NOISE_SETTINGS_WORLDGEN);
      return new GeneratorOptions(
         (long)_snowmanxx,
         true,
         true,
         method_28608(_snowmanxxx, DimensionType.createDefaultDimensionOptions(_snowmanxxx, _snowmanx, _snowmanxxxx, (long)_snowmanxx), createOverworldGenerator(_snowmanx, _snowmanxxxx, (long)_snowmanxx))
      );
   }

   public static GeneratorOptions getDefaultOptions(Registry<DimensionType> _snowman, Registry<Biome> _snowman, Registry<ChunkGeneratorSettings> _snowman) {
      long _snowmanxxx = new Random().nextLong();
      return new GeneratorOptions(
         _snowmanxxx, true, false, method_28608(_snowman, DimensionType.createDefaultDimensionOptions(_snowman, _snowman, _snowman, _snowmanxxx), createOverworldGenerator(_snowman, _snowman, _snowmanxxx))
      );
   }

   public static NoiseChunkGenerator createOverworldGenerator(
      Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed
   ) {
      return new NoiseChunkGenerator(
         new VanillaLayeredBiomeSource(seed, false, false, biomeRegistry),
         seed,
         () -> chunkGeneratorSettingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD)
      );
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

   public static SimpleRegistry<DimensionOptions> method_28608(Registry<DimensionType> _snowman, SimpleRegistry<DimensionOptions> _snowman, ChunkGenerator _snowman) {
      DimensionOptions _snowmanxxx = _snowman.get(DimensionOptions.OVERWORLD);
      Supplier<DimensionType> _snowmanxxxx = () -> _snowman == null ? _snowman.getOrThrow(DimensionType.OVERWORLD_REGISTRY_KEY) : _snowman.getDimensionType();
      return method_29962(_snowman, _snowmanxxxx, _snowman);
   }

   public static SimpleRegistry<DimensionOptions> method_29962(SimpleRegistry<DimensionOptions> _snowman, Supplier<DimensionType> _snowman, ChunkGenerator _snowman) {
      SimpleRegistry<DimensionOptions> _snowmanxxx = new SimpleRegistry<>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());
      _snowmanxxx.add(DimensionOptions.OVERWORLD, new DimensionOptions(_snowman, _snowman), Lifecycle.stable());

      for (Entry<RegistryKey<DimensionOptions>, DimensionOptions> _snowmanxxxx : _snowman.getEntries()) {
         RegistryKey<DimensionOptions> _snowmanxxxxx = _snowmanxxxx.getKey();
         if (_snowmanxxxxx != DimensionOptions.OVERWORLD) {
            _snowmanxxx.add(_snowmanxxxxx, _snowmanxxxx.getValue(), _snowman.getEntryLifecycle(_snowmanxxxx.getValue()));
         }
      }

      return _snowmanxxx;
   }

   public SimpleRegistry<DimensionOptions> getDimensions() {
      return this.options;
   }

   public ChunkGenerator getChunkGenerator() {
      DimensionOptions _snowman = this.options.get(DimensionOptions.OVERWORLD);
      if (_snowman == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         return _snowman.getChunkGenerator();
      }
   }

   public ImmutableSet<RegistryKey<World>> getWorlds() {
      return this.getDimensions()
         .getEntries()
         .stream()
         .map(_snowman -> RegistryKey.of(Registry.DIMENSION, _snowman.getKey().getValue()))
         .collect(ImmutableSet.toImmutableSet());
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

   public static GeneratorOptions fromProperties(DynamicRegistryManager _snowman, Properties _snowman) {
      String _snowmanxx = (String)MoreObjects.firstNonNull((String)_snowman.get("generator-settings"), "");
      _snowman.put("generator-settings", _snowmanxx);
      String _snowmanxxx = (String)MoreObjects.firstNonNull((String)_snowman.get("level-seed"), "");
      _snowman.put("level-seed", _snowmanxxx);
      String _snowmanxxxx = (String)_snowman.get("generate-structures");
      boolean _snowmanxxxxx = _snowmanxxxx == null || Boolean.parseBoolean(_snowmanxxxx);
      _snowman.put("generate-structures", Objects.toString(_snowmanxxxxx));
      String _snowmanxxxxxx = (String)_snowman.get("level-type");
      String _snowmanxxxxxxx = Optional.ofNullable(_snowmanxxxxxx).map(_snowmanxxxxxxxx -> _snowmanxxxxxxxx.toLowerCase(Locale.ROOT)).orElse("default");
      _snowman.put("level-type", _snowmanxxxxxxx);
      long _snowmanxxxxxxxx = new Random().nextLong();
      if (!_snowmanxxx.isEmpty()) {
         try {
            long _snowmanxxxxxxxxx = Long.parseLong(_snowmanxxx);
            if (_snowmanxxxxxxxxx != 0L) {
               _snowmanxxxxxxxx = _snowmanxxxxxxxxx;
            }
         } catch (NumberFormatException var18) {
            _snowmanxxxxxxxx = (long)_snowmanxxx.hashCode();
         }
      }

      Registry<DimensionType> _snowmanxxxxxxxxx = _snowman.get(Registry.DIMENSION_TYPE_KEY);
      Registry<Biome> _snowmanxxxxxxxxxx = _snowman.get(Registry.BIOME_KEY);
      Registry<ChunkGeneratorSettings> _snowmanxxxxxxxxxxx = _snowman.get(Registry.NOISE_SETTINGS_WORLDGEN);
      SimpleRegistry<DimensionOptions> _snowmanxxxxxxxxxxxx = DimensionType.createDefaultDimensionOptions(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxx);
      switch (_snowmanxxxxxxx) {
         case "flat":
            JsonObject _snowmanxxxxxxxxxxxxx = !_snowmanxx.isEmpty() ? JsonHelper.deserialize(_snowmanxx) : new JsonObject();
            Dynamic<JsonElement> _snowmanxxxxxxxxxxxxxx = new Dynamic(JsonOps.INSTANCE, _snowmanxxxxxxxxxxxxx);
            return new GeneratorOptions(
               _snowmanxxxxxxxx,
               _snowmanxxxxx,
               false,
               method_28608(
                  _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  new FlatChunkGenerator(
                     FlatChunkGeneratorConfig.CODEC
                        .parse(_snowmanxxxxxxxxxxxxxx)
                        .resultOrPartial(LOGGER::error)
                        .orElseGet(() -> FlatChunkGeneratorConfig.getDefaultConfig(_snowman))
                  )
               )
            );
         case "debug_all_block_states":
            return new GeneratorOptions(_snowmanxxxxxxxx, _snowmanxxxxx, false, method_28608(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx, new DebugChunkGenerator(_snowmanxxxxxxxxxx)));
         case "amplified":
            return new GeneratorOptions(
               _snowmanxxxxxxxx,
               _snowmanxxxxx,
               false,
               method_28608(
                  _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  new NoiseChunkGenerator(
                     new VanillaLayeredBiomeSource(_snowmanxxxxxxxx, false, false, _snowmanxxxxxxxxxx), _snowmanxxxxxxxx, () -> _snowman.getOrThrow(ChunkGeneratorSettings.AMPLIFIED)
                  )
               )
            );
         case "largebiomes":
            return new GeneratorOptions(
               _snowmanxxxxxxxx,
               _snowmanxxxxx,
               false,
               method_28608(
                  _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  new NoiseChunkGenerator(
                     new VanillaLayeredBiomeSource(_snowmanxxxxxxxx, false, true, _snowmanxxxxxxxxxx), _snowmanxxxxxxxx, () -> _snowman.getOrThrow(ChunkGeneratorSettings.OVERWORLD)
                  )
               )
            );
         default:
            return new GeneratorOptions(
               _snowmanxxxxxxxx, _snowmanxxxxx, false, method_28608(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx, createOverworldGenerator(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxx))
            );
      }
   }

   public GeneratorOptions withHardcore(boolean hardcore, OptionalLong seed) {
      long _snowman = seed.orElse(this.seed);
      SimpleRegistry<DimensionOptions> _snowmanx;
      if (seed.isPresent()) {
         _snowmanx = new SimpleRegistry<>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());
         long _snowmanxx = seed.getAsLong();

         for (Entry<RegistryKey<DimensionOptions>, DimensionOptions> _snowmanxxx : this.options.getEntries()) {
            RegistryKey<DimensionOptions> _snowmanxxxx = _snowmanxxx.getKey();
            _snowmanx.add(
               _snowmanxxxx,
               new DimensionOptions(_snowmanxxx.getValue().getDimensionTypeSupplier(), _snowmanxxx.getValue().getChunkGenerator().withSeed(_snowmanxx)),
               this.options.getEntryLifecycle(_snowmanxxx.getValue())
            );
         }
      } else {
         _snowmanx = this.options;
      }

      GeneratorOptions _snowmanxx;
      if (this.isDebugWorld()) {
         _snowmanxx = new GeneratorOptions(_snowman, false, false, _snowmanx);
      } else {
         _snowmanxx = new GeneratorOptions(_snowman, this.shouldGenerateStructures(), this.hasBonusChest() && !hardcore, _snowmanx);
      }

      return _snowmanxx;
   }
}
