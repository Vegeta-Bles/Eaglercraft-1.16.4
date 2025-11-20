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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   public static final Codec<GeneratorOptions> CODEC = RecordCodecBuilder.<GeneratorOptions>create(
         instance -> instance.group(
                  Codec.LONG.fieldOf("seed").stable().forGetter(GeneratorOptions::getSeed),
                  Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(GeneratorOptions::shouldGenerateStructures),
                  Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(GeneratorOptions::hasBonusChest),
                  SimpleRegistry.createRegistryCodec(Registry.DIMENSION_OPTIONS, Lifecycle.stable(), DimensionOptions.CODEC)
                     .xmap(DimensionOptions::method_29569, Function.<SimpleRegistry<DimensionOptions>>identity())
                     .fieldOf("dimensions")
                     .forGetter(GeneratorOptions::getDimensions),
                  Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(GeneratorOptions::getLegacyCustomOptionsField)
               )
               .apply(instance, instance.stable(GeneratorOptions::new))
      )
      .comapFlatMap(GeneratorOptions::method_28610, Function.identity());
   private static final Logger LOGGER = LogManager.getLogger();
   private final long seed;
   private final boolean generateStructures;
   private final boolean bonusChest;
   private final SimpleRegistry<DimensionOptions> options;
   private final Optional<String> legacyCustomOptions;

   private DataResult<GeneratorOptions> method_28610() {
      DimensionOptions lv = this.options.get(DimensionOptions.OVERWORLD);
      if (lv == null) {
         return DataResult.error("Overworld settings missing");
      } else {
         return this.method_28611() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
      }
   }

   private boolean method_28611() {
      return DimensionOptions.method_29567(this.seed, this.options);
   }

   public GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> arg) {
      this(seed, generateStructures, bonusChest, arg, Optional.empty());
      DimensionOptions lv = arg.get(DimensionOptions.OVERWORLD);
      if (lv == null) {
         throw new IllegalStateException("Overworld settings missing");
      }
   }

   private GeneratorOptions(
      long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> arg, Optional<String> legacyCustomOptions
   ) {
      this.seed = seed;
      this.generateStructures = generateStructures;
      this.bonusChest = bonusChest;
      this.options = arg;
      this.legacyCustomOptions = legacyCustomOptions;
   }

   public static GeneratorOptions method_31112(DynamicRegistryManager arg) {
      Registry<Biome> lv = arg.get(Registry.BIOME_KEY);
      int i = "North Carolina".hashCode();
      Registry<DimensionType> lv2 = arg.get(Registry.DIMENSION_TYPE_KEY);
      Registry<ChunkGeneratorSettings> lv3 = arg.get(Registry.NOISE_SETTINGS_WORLDGEN);
      return new GeneratorOptions(
         (long)i, true, true, method_28608(lv2, DimensionType.createDefaultDimensionOptions(lv2, lv, lv3, (long)i), createOverworldGenerator(lv, lv3, (long)i))
      );
   }

   public static GeneratorOptions getDefaultOptions(Registry<DimensionType> arg, Registry<Biome> arg2, Registry<ChunkGeneratorSettings> arg3) {
      long l = new Random().nextLong();
      return new GeneratorOptions(
         l, true, false, method_28608(arg, DimensionType.createDefaultDimensionOptions(arg, arg2, arg3, l), createOverworldGenerator(arg2, arg3, l))
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

   public static SimpleRegistry<DimensionOptions> method_28608(Registry<DimensionType> arg, SimpleRegistry<DimensionOptions> arg2, ChunkGenerator arg3) {
      DimensionOptions lv = arg2.get(DimensionOptions.OVERWORLD);
      Supplier<DimensionType> supplier = () -> lv == null ? arg.getOrThrow(DimensionType.OVERWORLD_REGISTRY_KEY) : lv.getDimensionType();
      return method_29962(arg2, supplier, arg3);
   }

   public static SimpleRegistry<DimensionOptions> method_29962(SimpleRegistry<DimensionOptions> arg, Supplier<DimensionType> supplier, ChunkGenerator arg2) {
      SimpleRegistry<DimensionOptions> lv = new SimpleRegistry<>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());
      lv.add(DimensionOptions.OVERWORLD, new DimensionOptions(supplier, arg2), Lifecycle.stable());

      for (Entry<RegistryKey<DimensionOptions>, DimensionOptions> entry : arg.getEntries()) {
         RegistryKey<DimensionOptions> lv2 = entry.getKey();
         if (lv2 != DimensionOptions.OVERWORLD) {
            lv.add(lv2, entry.getValue(), arg.getEntryLifecycle(entry.getValue()));
         }
      }

      return lv;
   }

   public SimpleRegistry<DimensionOptions> getDimensions() {
      return this.options;
   }

   private Optional<String> getLegacyCustomOptionsField() {
      return this.legacyCustomOptions;
   }

   public ChunkGenerator getChunkGenerator() {
      DimensionOptions lv = this.options.get(DimensionOptions.OVERWORLD);
      if (lv == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         return lv.getChunkGenerator();
      }
   }

   public ImmutableSet<RegistryKey<World>> getWorlds() {
      return this.getDimensions()
         .getEntries()
         .stream()
         .map(entry -> RegistryKey.of(Registry.DIMENSION, entry.getKey().getValue()))
         .collect(ImmutableSet.toImmutableSet());
   }

   public boolean isDebugWorld() {
      return this.getChunkGenerator() instanceof DebugChunkGenerator;
   }

   public boolean isFlatWorld() {
      return this.getChunkGenerator() instanceof FlatChunkGenerator;
   }

   @Environment(EnvType.CLIENT)
   public boolean isLegacyCustomizedType() {
      return this.legacyCustomOptions.isPresent();
   }

   public GeneratorOptions withBonusChest() {
      return new GeneratorOptions(this.seed, this.generateStructures, true, this.options, this.legacyCustomOptions);
   }

   @Environment(EnvType.CLIENT)
   public GeneratorOptions toggleGenerateStructures() {
      return new GeneratorOptions(this.seed, !this.generateStructures, this.bonusChest, this.options);
   }

   @Environment(EnvType.CLIENT)
   public GeneratorOptions toggleBonusChest() {
      return new GeneratorOptions(this.seed, this.generateStructures, !this.bonusChest, this.options);
   }

   public static GeneratorOptions fromProperties(DynamicRegistryManager arg, Properties properties) {
      String string = (String)MoreObjects.firstNonNull((String)properties.get("generator-settings"), "");
      properties.put("generator-settings", string);
      String string2 = (String)MoreObjects.firstNonNull((String)properties.get("level-seed"), "");
      properties.put("level-seed", string2);
      String string3 = (String)properties.get("generate-structures");
      boolean bl = string3 == null || Boolean.parseBoolean(string3);
      properties.put("generate-structures", Objects.toString(bl));
      String string4 = (String)properties.get("level-type");
      String string5 = Optional.ofNullable(string4).map(stringx -> stringx.toLowerCase(Locale.ROOT)).orElse("default");
      properties.put("level-type", string5);
      long l = new Random().nextLong();
      if (!string2.isEmpty()) {
         try {
            long m = Long.parseLong(string2);
            if (m != 0L) {
               l = m;
            }
         } catch (NumberFormatException var18) {
            l = (long)string2.hashCode();
         }
      }

      Registry<DimensionType> lv = arg.get(Registry.DIMENSION_TYPE_KEY);
      Registry<Biome> lv2 = arg.get(Registry.BIOME_KEY);
      Registry<ChunkGeneratorSettings> lv3 = arg.get(Registry.NOISE_SETTINGS_WORLDGEN);
      SimpleRegistry<DimensionOptions> lv4 = DimensionType.createDefaultDimensionOptions(lv, lv2, lv3, l);
      switch (string5) {
         case "flat":
            JsonObject jsonObject = !string.isEmpty() ? JsonHelper.deserialize(string) : new JsonObject();
            Dynamic<JsonElement> dynamic = new Dynamic(JsonOps.INSTANCE, jsonObject);
            return new GeneratorOptions(
               l,
               bl,
               false,
               method_28608(
                  lv,
                  lv4,
                  new FlatChunkGenerator(
                     FlatChunkGeneratorConfig.CODEC
                        .parse(dynamic)
                        .resultOrPartial(LOGGER::error)
                        .orElseGet(() -> FlatChunkGeneratorConfig.getDefaultConfig(lv2))
                  )
               )
            );
         case "debug_all_block_states":
            return new GeneratorOptions(l, bl, false, method_28608(lv, lv4, new DebugChunkGenerator(lv2)));
         case "amplified":
            return new GeneratorOptions(
               l,
               bl,
               false,
               method_28608(
                  lv,
                  lv4,
                  new NoiseChunkGenerator(new VanillaLayeredBiomeSource(l, false, false, lv2), l, () -> lv3.getOrThrow(ChunkGeneratorSettings.AMPLIFIED))
               )
            );
         case "largebiomes":
            return new GeneratorOptions(
               l,
               bl,
               false,
               method_28608(
                  lv,
                  lv4,
                  new NoiseChunkGenerator(new VanillaLayeredBiomeSource(l, false, true, lv2), l, () -> lv3.getOrThrow(ChunkGeneratorSettings.OVERWORLD))
               )
            );
         default:
            return new GeneratorOptions(l, bl, false, method_28608(lv, lv4, createOverworldGenerator(lv2, lv3, l)));
      }
   }

   @Environment(EnvType.CLIENT)
   public GeneratorOptions withHardcore(boolean hardcore, OptionalLong seed) {
      long l = seed.orElse(this.seed);
      SimpleRegistry<DimensionOptions> lv;
      if (seed.isPresent()) {
         lv = new SimpleRegistry<>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());
         long m = seed.getAsLong();

         for (Entry<RegistryKey<DimensionOptions>, DimensionOptions> entry : this.options.getEntries()) {
            RegistryKey<DimensionOptions> lv2 = entry.getKey();
            lv.add(
               lv2,
               new DimensionOptions(entry.getValue().getDimensionTypeSupplier(), entry.getValue().getChunkGenerator().withSeed(m)),
               this.options.getEntryLifecycle(entry.getValue())
            );
         }
      } else {
         lv = this.options;
      }

      GeneratorOptions lv4;
      if (this.isDebugWorld()) {
         lv4 = new GeneratorOptions(l, false, false, lv);
      } else {
         lv4 = new GeneratorOptions(l, this.shouldGenerateStructures(), this.hasBonusChest() && !hardcore, lv);
      }

      return lv4;
   }
}
