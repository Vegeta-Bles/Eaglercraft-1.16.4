package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.dynamic.RegistryElementCodec;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Biome {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<Biome> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Biome.Weather.CODEC.forGetter(_snowmanx -> _snowmanx.weather),
               Biome.Category.CODEC.fieldOf("category").forGetter(_snowmanx -> _snowmanx.category),
               Codec.FLOAT.fieldOf("depth").forGetter(_snowmanx -> _snowmanx.depth),
               Codec.FLOAT.fieldOf("scale").forGetter(_snowmanx -> _snowmanx.scale),
               BiomeEffects.CODEC.fieldOf("effects").forGetter(_snowmanx -> _snowmanx.effects),
               GenerationSettings.CODEC.forGetter(_snowmanx -> _snowmanx.generationSettings),
               SpawnSettings.CODEC.forGetter(_snowmanx -> _snowmanx.spawnSettings)
            )
            .apply(_snowman, Biome::new)
   );
   public static final Codec<Biome> field_26633 = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Biome.Weather.CODEC.forGetter(_snowmanx -> _snowmanx.weather),
               Biome.Category.CODEC.fieldOf("category").forGetter(_snowmanx -> _snowmanx.category),
               Codec.FLOAT.fieldOf("depth").forGetter(_snowmanx -> _snowmanx.depth),
               Codec.FLOAT.fieldOf("scale").forGetter(_snowmanx -> _snowmanx.scale),
               BiomeEffects.CODEC.fieldOf("effects").forGetter(_snowmanx -> _snowmanx.effects)
            )
            .apply(_snowman, (_snowmanx, _snowmanxxxxx, _snowmanxxxx, _snowmanxxx, _snowmanxx) -> new Biome(_snowmanx, _snowmanxxxxx, _snowmanxxxx, _snowmanxxx, _snowmanxx, GenerationSettings.INSTANCE, SpawnSettings.INSTANCE))
   );
   public static final Codec<Supplier<Biome>> REGISTRY_CODEC = RegistryElementCodec.of(Registry.BIOME_KEY, CODEC);
   public static final Codec<List<Supplier<Biome>>> field_26750 = RegistryElementCodec.method_31194(Registry.BIOME_KEY, CODEC);
   private final Map<Integer, List<StructureFeature<?>>> structures = Registry.STRUCTURE_FEATURE
      .stream()
      .collect(Collectors.groupingBy(_snowman -> _snowman.getGenerationStep().ordinal()));
   private static final OctaveSimplexNoiseSampler TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(1234L), ImmutableList.of(0));
   private static final OctaveSimplexNoiseSampler FROZEN_OCEAN_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(3456L), ImmutableList.of(-2, -1, 0));
   public static final OctaveSimplexNoiseSampler FOLIAGE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(2345L), ImmutableList.of(0));
   private final Biome.Weather weather;
   private final GenerationSettings generationSettings;
   private final SpawnSettings spawnSettings;
   private final float depth;
   private final float scale;
   private final Biome.Category category;
   private final BiomeEffects effects;
   private final ThreadLocal<Long2FloatLinkedOpenHashMap> temperatureCache = ThreadLocal.withInitial(() -> Util.make(() -> {
         Long2FloatLinkedOpenHashMap _snowman = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
            protected void rehash(int _snowman) {
            }
         };
         _snowman.defaultReturnValue(Float.NaN);
         return _snowman;
      }));

   private Biome(
      Biome.Weather weather,
      Biome.Category category,
      float depth,
      float scale,
      BiomeEffects effects,
      GenerationSettings generationSettings,
      SpawnSettings spawnSettings
   ) {
      this.weather = weather;
      this.generationSettings = generationSettings;
      this.spawnSettings = spawnSettings;
      this.category = category;
      this.depth = depth;
      this.scale = scale;
      this.effects = effects;
   }

   public int getSkyColor() {
      return this.effects.getSkyColor();
   }

   public SpawnSettings getSpawnSettings() {
      return this.spawnSettings;
   }

   public Biome.Precipitation getPrecipitation() {
      return this.weather.precipitation;
   }

   public boolean hasHighHumidity() {
      return this.getDownfall() > 0.85F;
   }

   private float computeTemperature(BlockPos pos) {
      float _snowman = this.weather.temperatureModifier.getModifiedTemperature(pos, this.getTemperature());
      if (pos.getY() > 64) {
         float _snowmanx = (float)(TEMPERATURE_NOISE.sample((double)((float)pos.getX() / 8.0F), (double)((float)pos.getZ() / 8.0F), false) * 4.0);
         return _snowman - (_snowmanx + (float)pos.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return _snowman;
      }
   }

   public final float getTemperature(BlockPos blockPos) {
      long _snowman = blockPos.asLong();
      Long2FloatLinkedOpenHashMap _snowmanx = this.temperatureCache.get();
      float _snowmanxx = _snowmanx.get(_snowman);
      if (!Float.isNaN(_snowmanxx)) {
         return _snowmanxx;
      } else {
         float _snowmanxxx = this.computeTemperature(blockPos);
         if (_snowmanx.size() == 1024) {
            _snowmanx.removeFirstFloat();
         }

         _snowmanx.put(_snowman, _snowmanxxx);
         return _snowmanxxx;
      }
   }

   public boolean canSetIce(WorldView world, BlockPos blockPos) {
      return this.canSetIce(world, blockPos, true);
   }

   public boolean canSetIce(WorldView world, BlockPos pos, boolean doWaterCheck) {
      if (this.getTemperature(pos) >= 0.15F) {
         return false;
      } else {
         if (pos.getY() >= 0 && pos.getY() < 256 && world.getLightLevel(LightType.BLOCK, pos) < 10) {
            BlockState _snowman = world.getBlockState(pos);
            FluidState _snowmanx = world.getFluidState(pos);
            if (_snowmanx.getFluid() == Fluids.WATER && _snowman.getBlock() instanceof FluidBlock) {
               if (!doWaterCheck) {
                  return true;
               }

               boolean _snowmanxx = world.isWater(pos.west()) && world.isWater(pos.east()) && world.isWater(pos.north()) && world.isWater(pos.south());
               if (!_snowmanxx) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean canSetSnow(WorldView world, BlockPos blockPos) {
      if (this.getTemperature(blockPos) >= 0.15F) {
         return false;
      } else {
         if (blockPos.getY() >= 0 && blockPos.getY() < 256 && world.getLightLevel(LightType.BLOCK, blockPos) < 10) {
            BlockState _snowman = world.getBlockState(blockPos);
            if (_snowman.isAir() && Blocks.SNOW.getDefaultState().canPlaceAt(world, blockPos)) {
               return true;
            }
         }

         return false;
      }
   }

   public GenerationSettings getGenerationSettings() {
      return this.generationSettings;
   }

   public void generateFeatureStep(
      StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, ChunkRegion region, long populationSeed, ChunkRandom random, BlockPos pos
   ) {
      List<List<Supplier<ConfiguredFeature<?, ?>>>> _snowman = this.generationSettings.getFeatures();
      int _snowmanx = GenerationStep.Feature.values().length;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         int _snowmanxxx = 0;
         if (structureAccessor.shouldGenerateStructures()) {
            for (StructureFeature<?> _snowmanxxxx : this.structures.getOrDefault(_snowmanxx, Collections.emptyList())) {
               random.setDecoratorSeed(populationSeed, _snowmanxxx, _snowmanxx);
               int _snowmanxxxxx = pos.getX() >> 4;
               int _snowmanxxxxxx = pos.getZ() >> 4;
               int _snowmanxxxxxxx = _snowmanxxxxx << 4;
               int _snowmanxxxxxxxx = _snowmanxxxxxx << 4;

               try {
                  structureAccessor.getStructuresWithChildren(ChunkSectionPos.from(pos), _snowmanxxxx)
                     .forEach(
                        _snowmanxxxxxxxxx -> _snowmanxxxxxxxxx.generateStructure(
                              region, structureAccessor, chunkGenerator, random, new BlockBox(_snowman, _snowman, _snowman + 15, _snowman + 15), new ChunkPos(_snowman, _snowman)
                           )
                     );
               } catch (Exception var21) {
                  CrashReport _snowmanxxxxxxxxx = CrashReport.create(var21, "Feature placement");
                  _snowmanxxxxxxxxx.addElement("Feature").add("Id", Registry.STRUCTURE_FEATURE.getId(_snowmanxxxx)).add("Description", () -> _snowman.toString());
                  throw new CrashException(_snowmanxxxxxxxxx);
               }

               _snowmanxxx++;
            }
         }

         if (_snowman.size() > _snowmanxx) {
            for (Supplier<ConfiguredFeature<?, ?>> _snowmanxxxx : _snowman.get(_snowmanxx)) {
               ConfiguredFeature<?, ?> _snowmanxxxxx = _snowmanxxxx.get();
               random.setDecoratorSeed(populationSeed, _snowmanxxx, _snowmanxx);

               try {
                  _snowmanxxxxx.generate(region, chunkGenerator, random, pos);
               } catch (Exception var22) {
                  CrashReport _snowmanxxxxxx = CrashReport.create(var22, "Feature placement");
                  _snowmanxxxxxx.addElement("Feature")
                     .add("Id", Registry.FEATURE.getId(_snowmanxxxxx.feature))
                     .add("Config", _snowmanxxxxx.config)
                     .add("Description", () -> _snowman.feature.toString());
                  throw new CrashException(_snowmanxxxxxx);
               }

               _snowmanxxx++;
            }
         }
      }
   }

   public int getFogColor() {
      return this.effects.getFogColor();
   }

   public int getGrassColorAt(double x, double z) {
      int _snowman = this.effects.getGrassColor().orElseGet(this::getDefaultGrassColor);
      return this.effects.getGrassColorModifier().getModifiedGrassColor(x, z, _snowman);
   }

   private int getDefaultGrassColor() {
      double _snowman = (double)MathHelper.clamp(this.weather.temperature, 0.0F, 1.0F);
      double _snowmanx = (double)MathHelper.clamp(this.weather.downfall, 0.0F, 1.0F);
      return GrassColors.getColor(_snowman, _snowmanx);
   }

   public int getFoliageColor() {
      return this.effects.getFoliageColor().orElseGet(this::getDefaultFoliageColor);
   }

   private int getDefaultFoliageColor() {
      double _snowman = (double)MathHelper.clamp(this.weather.temperature, 0.0F, 1.0F);
      double _snowmanx = (double)MathHelper.clamp(this.weather.downfall, 0.0F, 1.0F);
      return FoliageColors.getColor(_snowman, _snowmanx);
   }

   public void buildSurface(
      Random random, Chunk chunk, int x, int z, int worldHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed
   ) {
      ConfiguredSurfaceBuilder<?> _snowman = this.generationSettings.getSurfaceBuilder().get();
      _snowman.initSeed(seed);
      _snowman.generate(random, chunk, this, x, z, worldHeight, noise, defaultBlock, defaultFluid, seaLevel, seed);
   }

   public final float getDepth() {
      return this.depth;
   }

   public final float getDownfall() {
      return this.weather.downfall;
   }

   public final float getScale() {
      return this.scale;
   }

   public final float getTemperature() {
      return this.weather.temperature;
   }

   public BiomeEffects getEffects() {
      return this.effects;
   }

   public final int getWaterColor() {
      return this.effects.getWaterColor();
   }

   public final int getWaterFogColor() {
      return this.effects.getWaterFogColor();
   }

   public Optional<BiomeParticleConfig> getParticleConfig() {
      return this.effects.getParticleConfig();
   }

   public Optional<SoundEvent> getLoopSound() {
      return this.effects.getLoopSound();
   }

   public Optional<BiomeMoodSound> getMoodSound() {
      return this.effects.getMoodSound();
   }

   public Optional<BiomeAdditionsSound> getAdditionsSound() {
      return this.effects.getAdditionsSound();
   }

   public Optional<MusicSound> getMusic() {
      return this.effects.getMusic();
   }

   public final Biome.Category getCategory() {
      return this.category;
   }

   @Override
   public String toString() {
      Identifier _snowman = BuiltinRegistries.BIOME.getId(this);
      return _snowman == null ? super.toString() : _snowman.toString();
   }

   public static class Builder {
      @Nullable
      private Biome.Precipitation precipitation;
      @Nullable
      private Biome.Category category;
      @Nullable
      private Float depth;
      @Nullable
      private Float scale;
      @Nullable
      private Float temperature;
      private Biome.TemperatureModifier temperatureModifier = Biome.TemperatureModifier.NONE;
      @Nullable
      private Float downfall;
      @Nullable
      private BiomeEffects specialEffects;
      @Nullable
      private SpawnSettings spawnSettings;
      @Nullable
      private GenerationSettings generationSettings;

      public Builder() {
      }

      public Biome.Builder precipitation(Biome.Precipitation precipitation) {
         this.precipitation = precipitation;
         return this;
      }

      public Biome.Builder category(Biome.Category category) {
         this.category = category;
         return this;
      }

      public Biome.Builder depth(float depth) {
         this.depth = depth;
         return this;
      }

      public Biome.Builder scale(float scale) {
         this.scale = scale;
         return this;
      }

      public Biome.Builder temperature(float temperature) {
         this.temperature = temperature;
         return this;
      }

      public Biome.Builder downfall(float downfall) {
         this.downfall = downfall;
         return this;
      }

      public Biome.Builder effects(BiomeEffects effects) {
         this.specialEffects = effects;
         return this;
      }

      public Biome.Builder spawnSettings(SpawnSettings spawnSettings) {
         this.spawnSettings = spawnSettings;
         return this;
      }

      public Biome.Builder generationSettings(GenerationSettings generationSettings) {
         this.generationSettings = generationSettings;
         return this;
      }

      public Biome.Builder temperatureModifier(Biome.TemperatureModifier temperatureModifier) {
         this.temperatureModifier = temperatureModifier;
         return this;
      }

      public Biome build() {
         if (this.precipitation != null
            && this.category != null
            && this.depth != null
            && this.scale != null
            && this.temperature != null
            && this.downfall != null
            && this.specialEffects != null
            && this.spawnSettings != null
            && this.generationSettings != null) {
            return new Biome(
               new Biome.Weather(this.precipitation, this.temperature, this.temperatureModifier, this.downfall),
               this.category,
               this.depth,
               this.scale,
               this.specialEffects,
               this.generationSettings,
               this.spawnSettings
            );
         } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
         }
      }

      @Override
      public String toString() {
         return "BiomeBuilder{\nprecipitation="
            + this.precipitation
            + ",\nbiomeCategory="
            + this.category
            + ",\ndepth="
            + this.depth
            + ",\nscale="
            + this.scale
            + ",\ntemperature="
            + this.temperature
            + ",\ntemperatureModifier="
            + this.temperatureModifier
            + ",\ndownfall="
            + this.downfall
            + ",\nspecialEffects="
            + this.specialEffects
            + ",\nmobSpawnSettings="
            + this.spawnSettings
            + ",\ngenerationSettings="
            + this.generationSettings
            + ",\n"
            + '}';
      }
   }

   public static enum Category implements StringIdentifiable {
      NONE("none"),
      TAIGA("taiga"),
      EXTREME_HILLS("extreme_hills"),
      JUNGLE("jungle"),
      MESA("mesa"),
      PLAINS("plains"),
      SAVANNA("savanna"),
      ICY("icy"),
      THEEND("the_end"),
      BEACH("beach"),
      FOREST("forest"),
      OCEAN("ocean"),
      DESERT("desert"),
      RIVER("river"),
      SWAMP("swamp"),
      MUSHROOM("mushroom"),
      NETHER("nether");

      public static final Codec<Biome.Category> CODEC = StringIdentifiable.createCodec(Biome.Category::values, Biome.Category::byName);
      private static final Map<String, Biome.Category> BY_NAME = Arrays.stream(values())
         .collect(Collectors.toMap(Biome.Category::getName, category -> (Biome.Category)category));
      private final String name;

      private Category(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public static Biome.Category byName(String name) {
         return BY_NAME.get(name);
      }

      @Override
      public String asString() {
         return this.name;
      }
   }

   public static class MixedNoisePoint {
      public static final Codec<Biome.MixedNoisePoint> CODEC = RecordCodecBuilder.create(
         _snowman -> _snowman.group(
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("temperature").forGetter(_snowmanx -> _snowmanx.temperature),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("humidity").forGetter(_snowmanx -> _snowmanx.humidity),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("altitude").forGetter(_snowmanx -> _snowmanx.altitude),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("weirdness").forGetter(_snowmanx -> _snowmanx.weirdness),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("offset").forGetter(_snowmanx -> _snowmanx.weight)
               )
               .apply(_snowman, Biome.MixedNoisePoint::new)
      );
      private final float temperature;
      private final float humidity;
      private final float altitude;
      private final float weirdness;
      private final float weight;

      public MixedNoisePoint(float temperature, float humidity, float altitude, float weirdness, float weight) {
         this.temperature = temperature;
         this.humidity = humidity;
         this.altitude = altitude;
         this.weirdness = weirdness;
         this.weight = weight;
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else if (object != null && this.getClass() == object.getClass()) {
            Biome.MixedNoisePoint _snowman = (Biome.MixedNoisePoint)object;
            if (Float.compare(_snowman.temperature, this.temperature) != 0) {
               return false;
            } else if (Float.compare(_snowman.humidity, this.humidity) != 0) {
               return false;
            } else {
               return Float.compare(_snowman.altitude, this.altitude) != 0 ? false : Float.compare(_snowman.weirdness, this.weirdness) == 0;
            }
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.temperature != 0.0F ? Float.floatToIntBits(this.temperature) : 0;
         _snowman = 31 * _snowman + (this.humidity != 0.0F ? Float.floatToIntBits(this.humidity) : 0);
         _snowman = 31 * _snowman + (this.altitude != 0.0F ? Float.floatToIntBits(this.altitude) : 0);
         return 31 * _snowman + (this.weirdness != 0.0F ? Float.floatToIntBits(this.weirdness) : 0);
      }

      public float calculateDistanceTo(Biome.MixedNoisePoint other) {
         return (this.temperature - other.temperature) * (this.temperature - other.temperature)
            + (this.humidity - other.humidity) * (this.humidity - other.humidity)
            + (this.altitude - other.altitude) * (this.altitude - other.altitude)
            + (this.weirdness - other.weirdness) * (this.weirdness - other.weirdness)
            + (this.weight - other.weight) * (this.weight - other.weight);
      }
   }

   public static enum Precipitation implements StringIdentifiable {
      NONE("none"),
      RAIN("rain"),
      SNOW("snow");

      public static final Codec<Biome.Precipitation> CODEC = StringIdentifiable.createCodec(Biome.Precipitation::values, Biome.Precipitation::byName);
      private static final Map<String, Biome.Precipitation> BY_NAME = Arrays.stream(values())
         .collect(Collectors.toMap(Biome.Precipitation::getName, precipitation -> (Biome.Precipitation)precipitation));
      private final String name;

      private Precipitation(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public static Biome.Precipitation byName(String name) {
         return BY_NAME.get(name);
      }

      @Override
      public String asString() {
         return this.name;
      }
   }

   public static enum TemperatureModifier implements StringIdentifiable {
      NONE("none") {
         @Override
         public float getModifiedTemperature(BlockPos pos, float temperature) {
            return temperature;
         }
      },
      FROZEN("frozen") {
         @Override
         public float getModifiedTemperature(BlockPos pos, float temperature) {
            double _snowman = Biome.FROZEN_OCEAN_NOISE.sample((double)pos.getX() * 0.05, (double)pos.getZ() * 0.05, false) * 7.0;
            double _snowmanx = Biome.FOLIAGE_NOISE.sample((double)pos.getX() * 0.2, (double)pos.getZ() * 0.2, false);
            double _snowmanxx = _snowman + _snowmanx;
            if (_snowmanxx < 0.3) {
               double _snowmanxxx = Biome.FOLIAGE_NOISE.sample((double)pos.getX() * 0.09, (double)pos.getZ() * 0.09, false);
               if (_snowmanxxx < 0.8) {
                  return 0.2F;
               }
            }

            return temperature;
         }
      };

      private final String name;
      public static final Codec<Biome.TemperatureModifier> CODEC = StringIdentifiable.createCodec(
         Biome.TemperatureModifier::values, Biome.TemperatureModifier::byName
      );
      private static final Map<String, Biome.TemperatureModifier> BY_NAME = Arrays.stream(values())
         .collect(Collectors.toMap(Biome.TemperatureModifier::getName, temperatureModifier -> (Biome.TemperatureModifier)temperatureModifier));

      public abstract float getModifiedTemperature(BlockPos pos, float temperature);

      private TemperatureModifier(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      @Override
      public String asString() {
         return this.name;
      }

      public static Biome.TemperatureModifier byName(String name) {
         return BY_NAME.get(name);
      }
   }

   static class Weather {
      public static final MapCodec<Biome.Weather> CODEC = RecordCodecBuilder.mapCodec(
         _snowman -> _snowman.group(
                  Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(_snowmanx -> _snowmanx.precipitation),
                  Codec.FLOAT.fieldOf("temperature").forGetter(_snowmanx -> _snowmanx.temperature),
                  Biome.TemperatureModifier.CODEC
                     .optionalFieldOf("temperature_modifier", Biome.TemperatureModifier.NONE)
                     .forGetter(_snowmanx -> _snowmanx.temperatureModifier),
                  Codec.FLOAT.fieldOf("downfall").forGetter(_snowmanx -> _snowmanx.downfall)
               )
               .apply(_snowman, Biome.Weather::new)
      );
      private final Biome.Precipitation precipitation;
      private final float temperature;
      private final Biome.TemperatureModifier temperatureModifier;
      private final float downfall;

      private Weather(Biome.Precipitation precipitation, float temperature, Biome.TemperatureModifier temperatureModifier, float downfall) {
         this.precipitation = precipitation;
         this.temperature = temperature;
         this.temperatureModifier = temperatureModifier;
         this.downfall = downfall;
      }
   }
}
