/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
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
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Biome {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Codec<Biome> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Weather.CODEC.forGetter(biome -> biome.weather), (App)Category.CODEC.fieldOf("category").forGetter(biome -> biome.category), (App)Codec.FLOAT.fieldOf("depth").forGetter(biome -> Float.valueOf(biome.depth)), (App)Codec.FLOAT.fieldOf("scale").forGetter(biome -> Float.valueOf(biome.scale)), (App)BiomeEffects.CODEC.fieldOf("effects").forGetter(biome -> biome.effects), (App)GenerationSettings.CODEC.forGetter(biome -> biome.generationSettings), (App)SpawnSettings.CODEC.forGetter(biome -> biome.spawnSettings)).apply((Applicative)instance, Biome::new));
    public static final Codec<Biome> field_26633 = RecordCodecBuilder.create(instance -> instance.group((App)Weather.CODEC.forGetter(biome -> biome.weather), (App)Category.CODEC.fieldOf("category").forGetter(biome -> biome.category), (App)Codec.FLOAT.fieldOf("depth").forGetter(biome -> Float.valueOf(biome.depth)), (App)Codec.FLOAT.fieldOf("scale").forGetter(biome -> Float.valueOf(biome.scale)), (App)BiomeEffects.CODEC.fieldOf("effects").forGetter(biome -> biome.effects)).apply((Applicative)instance, (weather, category, f, f2, biomeEffects) -> new Biome((Weather)weather, (Category)category, f.floatValue(), f2.floatValue(), (BiomeEffects)biomeEffects, GenerationSettings.INSTANCE, SpawnSettings.INSTANCE)));
    public static final Codec<Supplier<Biome>> REGISTRY_CODEC = RegistryElementCodec.of(Registry.BIOME_KEY, CODEC);
    public static final Codec<List<Supplier<Biome>>> field_26750 = RegistryElementCodec.method_31194(Registry.BIOME_KEY, CODEC);
    private final Map<Integer, List<StructureFeature<?>>> structures = Registry.STRUCTURE_FEATURE.stream().collect(Collectors.groupingBy(structureFeature -> structureFeature.getGenerationStep().ordinal()));
    private static final OctaveSimplexNoiseSampler TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(1234L), (List<Integer>)ImmutableList.of((Object)0));
    private static final OctaveSimplexNoiseSampler FROZEN_OCEAN_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(3456L), (List<Integer>)ImmutableList.of((Object)-2, (Object)-1, (Object)0));
    public static final OctaveSimplexNoiseSampler FOLIAGE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(2345L), (List<Integer>)ImmutableList.of((Object)0));
    private final Weather weather;
    private final GenerationSettings generationSettings;
    private final SpawnSettings spawnSettings;
    private final float depth;
    private final float scale;
    private final Category category;
    private final BiomeEffects effects;
    private final ThreadLocal<Long2FloatLinkedOpenHashMap> temperatureCache = ThreadLocal.withInitial(() -> Util.make(() -> {
        Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap = new Long2FloatLinkedOpenHashMap(this, 1024, 0.25f){
            final /* synthetic */ Biome field_20336;
            {
                this.field_20336 = biome;
                super(n, f);
            }

            protected void rehash(int n) {
            }
        };
        long2FloatLinkedOpenHashMap.defaultReturnValue(Float.NaN);
        return long2FloatLinkedOpenHashMap;
    }));

    private Biome(Weather weather, Category category, float depth, float scale, BiomeEffects effects, GenerationSettings generationSettings, SpawnSettings spawnSettings) {
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

    public Precipitation getPrecipitation() {
        return this.weather.precipitation;
    }

    public boolean hasHighHumidity() {
        return this.getDownfall() > 0.85f;
    }

    private float computeTemperature(BlockPos pos) {
        float f = this.weather.temperatureModifier.getModifiedTemperature(pos, this.getTemperature());
        if (pos.getY() > 64) {
            _snowman = (float)(TEMPERATURE_NOISE.sample((float)pos.getX() / 8.0f, (float)pos.getZ() / 8.0f, false) * 4.0);
            return f - (_snowman + (float)pos.getY() - 64.0f) * 0.05f / 30.0f;
        }
        return f;
    }

    public final float getTemperature(BlockPos blockPos) {
        long l = blockPos.asLong();
        Long2FloatLinkedOpenHashMap _snowman2 = this.temperatureCache.get();
        float _snowman3 = _snowman2.get(l);
        if (!Float.isNaN(_snowman3)) {
            return _snowman3;
        }
        float _snowman4 = this.computeTemperature(blockPos);
        if (_snowman2.size() == 1024) {
            _snowman2.removeFirstFloat();
        }
        _snowman2.put(l, _snowman4);
        return _snowman4;
    }

    public boolean canSetIce(WorldView world, BlockPos blockPos) {
        return this.canSetIce(world, blockPos, true);
    }

    public boolean canSetIce(WorldView world, BlockPos pos, boolean doWaterCheck) {
        if (this.getTemperature(pos) >= 0.15f) {
            return false;
        }
        if (pos.getY() >= 0 && pos.getY() < 256 && world.getLightLevel(LightType.BLOCK, pos) < 10) {
            BlockState blockState = world.getBlockState(pos);
            FluidState _snowman2 = world.getFluidState(pos);
            if (_snowman2.getFluid() == Fluids.WATER && blockState.getBlock() instanceof FluidBlock) {
                if (!doWaterCheck) {
                    return true;
                }
                boolean bl = _snowman = world.isWater(pos.west()) && world.isWater(pos.east()) && world.isWater(pos.north()) && world.isWater(pos.south());
                if (!_snowman) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canSetSnow(WorldView world, BlockPos blockPos) {
        BlockState blockState;
        if (this.getTemperature(blockPos) >= 0.15f) {
            return false;
        }
        return blockPos.getY() >= 0 && blockPos.getY() < 256 && world.getLightLevel(LightType.BLOCK, blockPos) < 10 && (blockState = world.getBlockState(blockPos)).isAir() && Blocks.SNOW.getDefaultState().canPlaceAt(world, blockPos);
    }

    public GenerationSettings getGenerationSettings() {
        return this.generationSettings;
    }

    public void generateFeatureStep(StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, ChunkRegion region, long populationSeed, ChunkRandom random, BlockPos pos) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> list = this.generationSettings.getFeatures();
        int _snowman2 = GenerationStep.Feature.values().length;
        for (int i = 0; i < _snowman2; ++i) {
            int n;
            n = 0;
            if (structureAccessor.shouldGenerateStructures()) {
                List list2 = this.structures.getOrDefault(i, Collections.emptyList());
                for (Object object : list2) {
                    random.setDecoratorSeed(populationSeed, n, i);
                    int n2 = pos.getX() >> 4;
                    _snowman = pos.getZ() >> 4;
                    _snowman = n2 << 4;
                    _snowman = _snowman << 4;
                    try {
                        structureAccessor.getStructuresWithChildren(ChunkSectionPos.from(pos), (StructureFeature<?>)object).forEach(structureStart -> structureStart.generateStructure(region, structureAccessor, chunkGenerator, random, new BlockBox(_snowman, _snowman, _snowman + 15, _snowman + 15), new ChunkPos(n2, _snowman)));
                    }
                    catch (Exception _snowman3) {
                        CrashReport crashReport = CrashReport.create(_snowman3, "Feature placement");
                        crashReport.addElement("Feature").add("Id", Registry.STRUCTURE_FEATURE.getId((StructureFeature<?>)object)).add("Description", () -> Biome.method_28406((StructureFeature)object));
                        throw new CrashException(crashReport);
                    }
                    ++n;
                }
            }
            if (list.size() <= i) continue;
            for (Supplier<ConfiguredFeature<?, ?>> supplier : list.get(i)) {
                Object object;
                object = supplier.get();
                random.setDecoratorSeed(populationSeed, n, i);
                try {
                    ((ConfiguredFeature)object).generate(region, chunkGenerator, random, pos);
                }
                catch (Exception _snowman4) {
                    CrashReport crashReport = CrashReport.create(_snowman4, "Feature placement");
                    crashReport.addElement("Feature").add("Id", Registry.FEATURE.getId((Feature<?>)((ConfiguredFeature)object).feature)).add("Config", ((ConfiguredFeature)object).config).add("Description", () -> Biome.method_23348((ConfiguredFeature)object));
                    throw new CrashException(crashReport);
                }
                ++n;
            }
        }
    }

    public int getFogColor() {
        return this.effects.getFogColor();
    }

    public int getGrassColorAt(double x, double z) {
        int n = this.effects.getGrassColor().orElseGet(this::getDefaultGrassColor);
        return this.effects.getGrassColorModifier().getModifiedGrassColor(x, z, n);
    }

    private int getDefaultGrassColor() {
        double d = MathHelper.clamp(this.weather.temperature, 0.0f, 1.0f);
        _snowman = MathHelper.clamp(this.weather.downfall, 0.0f, 1.0f);
        return GrassColors.getColor(d, _snowman);
    }

    public int getFoliageColor() {
        return this.effects.getFoliageColor().orElseGet(this::getDefaultFoliageColor);
    }

    private int getDefaultFoliageColor() {
        double d = MathHelper.clamp(this.weather.temperature, 0.0f, 1.0f);
        _snowman = MathHelper.clamp(this.weather.downfall, 0.0f, 1.0f);
        return FoliageColors.getColor(d, _snowman);
    }

    public void buildSurface(Random random, Chunk chunk, int x, int z, int worldHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed) {
        ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder = this.generationSettings.getSurfaceBuilder().get();
        configuredSurfaceBuilder.initSeed(seed);
        configuredSurfaceBuilder.generate(random, chunk, this, x, z, worldHeight, noise, defaultBlock, defaultFluid, seaLevel, seed);
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

    public final Category getCategory() {
        return this.category;
    }

    public String toString() {
        Identifier identifier = BuiltinRegistries.BIOME.getId(this);
        return identifier == null ? super.toString() : identifier.toString();
    }

    private static /* synthetic */ String method_23348(ConfiguredFeature configuredFeature) throws Exception {
        return configuredFeature.feature.toString();
    }

    private static /* synthetic */ String method_28406(StructureFeature structureFeature) throws Exception {
        return structureFeature.toString();
    }

    static /* synthetic */ OctaveSimplexNoiseSampler method_30772() {
        return FROZEN_OCEAN_NOISE;
    }

    static class Weather {
        public static final MapCodec<Weather> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group((App)Precipitation.CODEC.fieldOf("precipitation").forGetter(weather -> weather.precipitation), (App)Codec.FLOAT.fieldOf("temperature").forGetter(weather -> Float.valueOf(weather.temperature)), (App)TemperatureModifier.CODEC.optionalFieldOf("temperature_modifier", (Object)TemperatureModifier.NONE).forGetter(weather -> weather.temperatureModifier), (App)Codec.FLOAT.fieldOf("downfall").forGetter(weather -> Float.valueOf(weather.downfall))).apply((Applicative)instance, Weather::new));
        private final Precipitation precipitation;
        private final float temperature;
        private final TemperatureModifier temperatureModifier;
        private final float downfall;

        private Weather(Precipitation precipitation, float temperature, TemperatureModifier temperatureModifier, float downfall) {
            this.precipitation = precipitation;
            this.temperature = temperature;
            this.temperatureModifier = temperatureModifier;
            this.downfall = downfall;
        }
    }

    public static class MixedNoisePoint {
        public static final Codec<MixedNoisePoint> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.floatRange((float)-2.0f, (float)2.0f).fieldOf("temperature").forGetter(mixedNoisePoint -> Float.valueOf(mixedNoisePoint.temperature)), (App)Codec.floatRange((float)-2.0f, (float)2.0f).fieldOf("humidity").forGetter(mixedNoisePoint -> Float.valueOf(mixedNoisePoint.humidity)), (App)Codec.floatRange((float)-2.0f, (float)2.0f).fieldOf("altitude").forGetter(mixedNoisePoint -> Float.valueOf(mixedNoisePoint.altitude)), (App)Codec.floatRange((float)-2.0f, (float)2.0f).fieldOf("weirdness").forGetter(mixedNoisePoint -> Float.valueOf(mixedNoisePoint.weirdness)), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("offset").forGetter(mixedNoisePoint -> Float.valueOf(mixedNoisePoint.weight))).apply((Applicative)instance, MixedNoisePoint::new));
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

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            MixedNoisePoint mixedNoisePoint = (MixedNoisePoint)object;
            if (Float.compare(mixedNoisePoint.temperature, this.temperature) != 0) {
                return false;
            }
            if (Float.compare(mixedNoisePoint.humidity, this.humidity) != 0) {
                return false;
            }
            if (Float.compare(mixedNoisePoint.altitude, this.altitude) != 0) {
                return false;
            }
            return Float.compare(mixedNoisePoint.weirdness, this.weirdness) == 0;
        }

        public int hashCode() {
            int n = this.temperature != 0.0f ? Float.floatToIntBits(this.temperature) : 0;
            n = 31 * n + (this.humidity != 0.0f ? Float.floatToIntBits(this.humidity) : 0);
            n = 31 * n + (this.altitude != 0.0f ? Float.floatToIntBits(this.altitude) : 0);
            n = 31 * n + (this.weirdness != 0.0f ? Float.floatToIntBits(this.weirdness) : 0);
            return n;
        }

        public float calculateDistanceTo(MixedNoisePoint other) {
            return (this.temperature - other.temperature) * (this.temperature - other.temperature) + (this.humidity - other.humidity) * (this.humidity - other.humidity) + (this.altitude - other.altitude) * (this.altitude - other.altitude) + (this.weirdness - other.weirdness) * (this.weirdness - other.weirdness) + (this.weight - other.weight) * (this.weight - other.weight);
        }
    }

    public static class Builder {
        @Nullable
        private Precipitation precipitation;
        @Nullable
        private Category category;
        @Nullable
        private Float depth;
        @Nullable
        private Float scale;
        @Nullable
        private Float temperature;
        private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
        @Nullable
        private Float downfall;
        @Nullable
        private BiomeEffects specialEffects;
        @Nullable
        private SpawnSettings spawnSettings;
        @Nullable
        private GenerationSettings generationSettings;

        public Builder precipitation(Precipitation precipitation) {
            this.precipitation = precipitation;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder depth(float depth) {
            this.depth = Float.valueOf(depth);
            return this;
        }

        public Builder scale(float scale) {
            this.scale = Float.valueOf(scale);
            return this;
        }

        public Builder temperature(float temperature) {
            this.temperature = Float.valueOf(temperature);
            return this;
        }

        public Builder downfall(float downfall) {
            this.downfall = Float.valueOf(downfall);
            return this;
        }

        public Builder effects(BiomeEffects effects) {
            this.specialEffects = effects;
            return this;
        }

        public Builder spawnSettings(SpawnSettings spawnSettings) {
            this.spawnSettings = spawnSettings;
            return this;
        }

        public Builder generationSettings(GenerationSettings generationSettings) {
            this.generationSettings = generationSettings;
            return this;
        }

        public Builder temperatureModifier(TemperatureModifier temperatureModifier) {
            this.temperatureModifier = temperatureModifier;
            return this;
        }

        public Biome build() {
            if (this.precipitation == null || this.category == null || this.depth == null || this.scale == null || this.temperature == null || this.downfall == null || this.specialEffects == null || this.spawnSettings == null || this.generationSettings == null) {
                throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
            }
            return new Biome(new Weather(this.precipitation, this.temperature.floatValue(), this.temperatureModifier, this.downfall.floatValue()), this.category, this.depth.floatValue(), this.scale.floatValue(), this.specialEffects, this.generationSettings, this.spawnSettings);
        }

        public String toString() {
            return "BiomeBuilder{\nprecipitation=" + this.precipitation + ",\nbiomeCategory=" + this.category + ",\ndepth=" + this.depth + ",\nscale=" + this.scale + ",\ntemperature=" + this.temperature + ",\ntemperatureModifier=" + this.temperatureModifier + ",\ndownfall=" + this.downfall + ",\nspecialEffects=" + this.specialEffects + ",\nmobSpawnSettings=" + this.spawnSettings + ",\ngenerationSettings=" + this.generationSettings + ",\n" + '}';
        }
    }

    public static enum TemperatureModifier implements StringIdentifiable
    {
        NONE("none"){

            public float getModifiedTemperature(BlockPos pos, float temperature) {
                return temperature;
            }
        }
        ,
        FROZEN("frozen"){

            public float getModifiedTemperature(BlockPos pos, float temperature) {
                double d = Biome.method_30772().sample((double)pos.getX() * 0.05, (double)pos.getZ() * 0.05, false) * 7.0;
                _snowman = d + (_snowman = Biome.FOLIAGE_NOISE.sample((double)pos.getX() * 0.2, (double)pos.getZ() * 0.2, false));
                if (_snowman < 0.3 && (_snowman = Biome.FOLIAGE_NOISE.sample((double)pos.getX() * 0.09, (double)pos.getZ() * 0.09, false)) < 0.8) {
                    return 0.2f;
                }
                return temperature;
            }
        };

        private final String name;
        public static final Codec<TemperatureModifier> CODEC;
        private static final Map<String, TemperatureModifier> BY_NAME;

        public abstract float getModifiedTemperature(BlockPos var1, float var2);

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

        public static TemperatureModifier byName(String name) {
            return BY_NAME.get(name);
        }

        static {
            CODEC = StringIdentifiable.createCodec(TemperatureModifier::values, TemperatureModifier::byName);
            BY_NAME = Arrays.stream(TemperatureModifier.values()).collect(Collectors.toMap(TemperatureModifier::getName, temperatureModifier -> temperatureModifier));
        }
    }

    public static enum Precipitation implements StringIdentifiable
    {
        NONE("none"),
        RAIN("rain"),
        SNOW("snow");

        public static final Codec<Precipitation> CODEC;
        private static final Map<String, Precipitation> BY_NAME;
        private final String name;

        private Precipitation(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static Precipitation byName(String name) {
            return BY_NAME.get(name);
        }

        @Override
        public String asString() {
            return this.name;
        }

        static {
            CODEC = StringIdentifiable.createCodec(Precipitation::values, Precipitation::byName);
            BY_NAME = Arrays.stream(Precipitation.values()).collect(Collectors.toMap(Precipitation::getName, precipitation -> precipitation));
        }
    }

    public static enum Category implements StringIdentifiable
    {
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

        public static final Codec<Category> CODEC;
        private static final Map<String, Category> BY_NAME;
        private final String name;

        private Category(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static Category byName(String name) {
            return BY_NAME.get(name);
        }

        @Override
        public String asString() {
            return this.name;
        }

        static {
            CODEC = StringIdentifiable.createCodec(Category::values, Category::byName);
            BY_NAME = Arrays.stream(Category.values()).collect(Collectors.toMap(Category::getName, category -> category));
        }
    }
}

