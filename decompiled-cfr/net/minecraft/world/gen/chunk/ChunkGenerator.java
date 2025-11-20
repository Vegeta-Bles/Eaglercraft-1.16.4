/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.gen.chunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.DebugChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.StrongholdConfig;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class ChunkGenerator {
    public static final Codec<ChunkGenerator> CODEC;
    protected final BiomeSource populationSource;
    protected final BiomeSource biomeSource;
    private final StructuresConfig structuresConfig;
    private final long worldSeed;
    private final List<ChunkPos> strongholds = Lists.newArrayList();

    public ChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        this(biomeSource, biomeSource, structuresConfig, 0L);
    }

    public ChunkGenerator(BiomeSource populationSource, BiomeSource biomeSource, StructuresConfig structuresConfig, long worldSeed) {
        this.populationSource = populationSource;
        this.biomeSource = biomeSource;
        this.structuresConfig = structuresConfig;
        this.worldSeed = worldSeed;
    }

    private void generateStrongholdPositions() {
        if (!this.strongholds.isEmpty()) {
            return;
        }
        StrongholdConfig strongholdConfig = this.structuresConfig.getStronghold();
        if (strongholdConfig == null || strongholdConfig.getCount() == 0) {
            return;
        }
        ArrayList _snowman2 = Lists.newArrayList();
        for (Biome biome : this.populationSource.getBiomes()) {
            if (!biome.getGenerationSettings().hasStructureFeature(StructureFeature.STRONGHOLD)) continue;
            _snowman2.add(biome);
        }
        int _snowman3 = strongholdConfig.getDistance();
        int _snowman4 = strongholdConfig.getCount();
        int _snowman5 = strongholdConfig.getSpread();
        Random _snowman6 = new Random();
        _snowman6.setSeed(this.worldSeed);
        double _snowman7 = _snowman6.nextDouble() * Math.PI * 2.0;
        int _snowman8 = 0;
        int _snowman9 = 0;
        for (int i = 0; i < _snowman4; ++i) {
            double d = (double)(4 * _snowman3 + _snowman3 * _snowman9 * 6) + (_snowman6.nextDouble() - 0.5) * ((double)_snowman3 * 2.5);
            int _snowman10 = (int)Math.round(Math.cos(_snowman7) * d);
            int _snowman11 = (int)Math.round(Math.sin(_snowman7) * d);
            BlockPos _snowman12 = this.populationSource.locateBiome((_snowman10 << 4) + 8, 0, (_snowman11 << 4) + 8, 112, _snowman2::contains, _snowman6);
            if (_snowman12 != null) {
                _snowman10 = _snowman12.getX() >> 4;
                _snowman11 = _snowman12.getZ() >> 4;
            }
            this.strongholds.add(new ChunkPos(_snowman10, _snowman11));
            _snowman7 += Math.PI * 2 / (double)_snowman5;
            if (++_snowman8 != _snowman5) continue;
            _snowman8 = 0;
            _snowman5 += 2 * _snowman5 / (++_snowman9 + 1);
            _snowman5 = Math.min(_snowman5, _snowman4 - i);
            _snowman7 += _snowman6.nextDouble() * Math.PI * 2.0;
        }
    }

    protected abstract Codec<? extends ChunkGenerator> getCodec();

    public abstract ChunkGenerator withSeed(long var1);

    public void populateBiomes(Registry<Biome> biomeRegistry, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        ((ProtoChunk)chunk).setBiomes(new BiomeArray(biomeRegistry, chunkPos, this.biomeSource));
    }

    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        BiomeAccess biomeAccess = access.withSource(this.populationSource);
        ChunkRandom _snowman2 = new ChunkRandom();
        int _snowman3 = 8;
        ChunkPos _snowman4 = chunk.getPos();
        int _snowman5 = _snowman4.x;
        int _snowman6 = _snowman4.z;
        GenerationSettings _snowman7 = this.populationSource.getBiomeForNoiseGen(_snowman4.x << 2, 0, _snowman4.z << 2).getGenerationSettings();
        BitSet _snowman8 = ((ProtoChunk)chunk).getOrCreateCarvingMask(carver);
        for (int i = _snowman5 - 8; i <= _snowman5 + 8; ++i) {
            for (_snowman = _snowman6 - 8; _snowman <= _snowman6 + 8; ++_snowman) {
                List<Supplier<ConfiguredCarver<?>>> list = _snowman7.getCarversForStep(carver);
                ListIterator<Supplier<ConfiguredCarver<?>>> _snowman9 = list.listIterator();
                while (_snowman9.hasNext()) {
                    int n = _snowman9.nextIndex();
                    ConfiguredCarver<?> _snowman10 = _snowman9.next().get();
                    _snowman2.setCarverSeed(seed + (long)n, i, _snowman);
                    if (!_snowman10.shouldCarve(_snowman2, i, _snowman)) continue;
                    _snowman10.carve(chunk, biomeAccess::getBiome, _snowman2, this.getSeaLevel(), i, _snowman, _snowman5, _snowman6, _snowman8);
                }
            }
        }
    }

    @Nullable
    public BlockPos locateStructure(ServerWorld world, StructureFeature<?> feature, BlockPos center, int radius, boolean skipExistingChunks) {
        if (!this.populationSource.hasStructureFeature(feature)) {
            return null;
        }
        if (feature == StructureFeature.STRONGHOLD) {
            this.generateStrongholdPositions();
            BlockPos _snowman4 = null;
            double _snowman2 = Double.MAX_VALUE;
            BlockPos.Mutable _snowman3 = new BlockPos.Mutable();
            for (ChunkPos chunkPos : this.strongholds) {
                _snowman3.set((chunkPos.x << 4) + 8, 32, (chunkPos.z << 4) + 8);
                double d = _snowman3.getSquaredDistance(center);
                if (_snowman4 == null) {
                    _snowman4 = new BlockPos(_snowman3);
                    _snowman2 = d;
                    continue;
                }
                if (!(d < _snowman2)) continue;
                _snowman4 = new BlockPos(_snowman3);
                _snowman2 = d;
            }
            return _snowman4;
        }
        StructureConfig structureConfig = this.structuresConfig.getForType(feature);
        if (structureConfig == null) {
            return null;
        }
        return feature.locateStructure(world, world.getStructureAccessor(), center, radius, skipExistingChunks, world.getSeed(), structureConfig);
    }

    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        int n = region.getCenterChunkX();
        _snowman = region.getCenterChunkZ();
        _snowman = n * 16;
        _snowman = _snowman * 16;
        BlockPos _snowman2 = new BlockPos(_snowman, 0, _snowman);
        Biome _snowman3 = this.populationSource.getBiomeForNoiseGen((n << 2) + 2, 2, (_snowman << 2) + 2);
        ChunkRandom _snowman4 = new ChunkRandom();
        long _snowman5 = _snowman4.setPopulationSeed(region.getSeed(), _snowman, _snowman);
        try {
            _snowman3.generateFeatureStep(accessor, this, region, _snowman5, _snowman4, _snowman2);
        }
        catch (Exception _snowman6) {
            CrashReport crashReport = CrashReport.create(_snowman6, "Biome decoration");
            crashReport.addElement("Generation").add("CenterX", n).add("CenterZ", _snowman).add("Seed", _snowman5).add("Biome", _snowman3);
            throw new CrashException(crashReport);
        }
    }

    public abstract void buildSurface(ChunkRegion var1, Chunk var2);

    public void populateEntities(ChunkRegion region) {
    }

    public StructuresConfig getStructuresConfig() {
        return this.structuresConfig;
    }

    public int getSpawnHeight() {
        return 64;
    }

    public BiomeSource getBiomeSource() {
        return this.biomeSource;
    }

    public int getWorldHeight() {
        return 256;
    }

    public List<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        return biome.getSpawnSettings().getSpawnEntry(group);
    }

    public void setStructureStarts(DynamicRegistryManager dynamicRegistryManager, StructureAccessor structureAccessor, Chunk chunk, StructureManager structureManager, long worldSeed) {
        ChunkPos chunkPos = chunk.getPos();
        Biome _snowman2 = this.populationSource.getBiomeForNoiseGen((chunkPos.x << 2) + 2, 0, (chunkPos.z << 2) + 2);
        this.setStructureStart(ConfiguredStructureFeatures.STRONGHOLD, dynamicRegistryManager, structureAccessor, chunk, structureManager, worldSeed, chunkPos, _snowman2);
        for (Supplier<ConfiguredStructureFeature<?, ?>> supplier : _snowman2.getGenerationSettings().getStructureFeatures()) {
            this.setStructureStart(supplier.get(), dynamicRegistryManager, structureAccessor, chunk, structureManager, worldSeed, chunkPos, _snowman2);
        }
    }

    private void setStructureStart(ConfiguredStructureFeature<?, ?> configuredStructureFeature, DynamicRegistryManager dynamicRegistryManager, StructureAccessor structureAccessor, Chunk chunk, StructureManager structureManager, long worldSeed, ChunkPos chunkPos, Biome biome) {
        StructureStart<?> structureStart = structureAccessor.getStructureStart(ChunkSectionPos.from(chunk.getPos(), 0), (StructureFeature<?>)configuredStructureFeature.feature, chunk);
        int _snowman2 = structureStart != null ? structureStart.getReferences() : 0;
        StructureConfig _snowman3 = this.structuresConfig.getForType((StructureFeature<?>)configuredStructureFeature.feature);
        if (_snowman3 != null) {
            _snowman = configuredStructureFeature.tryPlaceStart(dynamicRegistryManager, this, this.populationSource, structureManager, worldSeed, chunkPos, biome, _snowman2, _snowman3);
            structureAccessor.setStructureStart(ChunkSectionPos.from(chunk.getPos(), 0), (StructureFeature<?>)configuredStructureFeature.feature, _snowman, chunk);
        }
    }

    public void addStructureReferences(StructureWorldAccess world, StructureAccessor accessor, Chunk chunk) {
        int n = 8;
        _snowman = chunk.getPos().x;
        _snowman = chunk.getPos().z;
        _snowman = _snowman << 4;
        _snowman = _snowman << 4;
        ChunkSectionPos _snowman2 = ChunkSectionPos.from(chunk.getPos(), 0);
        for (_snowman = _snowman - 8; _snowman <= _snowman + 8; ++_snowman) {
            for (_snowman = _snowman - 8; _snowman <= _snowman + 8; ++_snowman) {
                long l = ChunkPos.toLong(_snowman, _snowman);
                for (StructureStart<?> structureStart : world.getChunk(_snowman, _snowman).getStructureStarts().values()) {
                    try {
                        if (structureStart == StructureStart.DEFAULT || !structureStart.getBoundingBox().intersectsXZ(_snowman, _snowman, _snowman + 15, _snowman + 15)) continue;
                        accessor.addStructureReference(_snowman2, structureStart.getFeature(), l, chunk);
                        DebugInfoSender.sendStructureStart(world, structureStart);
                    }
                    catch (Exception exception) {
                        CrashReport crashReport = CrashReport.create(exception, "Generating structure reference");
                        CrashReportSection _snowman3 = crashReport.addElement("Structure");
                        _snowman3.add("Id", () -> Registry.STRUCTURE_FEATURE.getId(structureStart.getFeature()).toString());
                        _snowman3.add("Name", () -> structureStart.getFeature().getName());
                        _snowman3.add("Class", () -> structureStart.getFeature().getClass().getCanonicalName());
                        throw new CrashException(crashReport);
                    }
                }
            }
        }
    }

    public abstract void populateNoise(WorldAccess var1, StructureAccessor var2, Chunk var3);

    public int getSeaLevel() {
        return 63;
    }

    public abstract int getHeight(int var1, int var2, Heightmap.Type var3);

    public abstract BlockView getColumnSample(int var1, int var2);

    public int getHeightOnGround(int x, int z, Heightmap.Type heightmapType) {
        return this.getHeight(x, z, heightmapType);
    }

    public int getHeightInGround(int x, int z, Heightmap.Type heightmapType) {
        return this.getHeight(x, z, heightmapType) - 1;
    }

    public boolean isStrongholdStartingChunk(ChunkPos chunkPos) {
        this.generateStrongholdPositions();
        return this.strongholds.contains(chunkPos);
    }

    static {
        Registry.register(Registry.CHUNK_GENERATOR, "noise", NoiseChunkGenerator.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR, "flat", FlatChunkGenerator.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR, "debug", DebugChunkGenerator.CODEC);
        CODEC = Registry.CHUNK_GENERATOR.dispatchStable(ChunkGenerator::getCodec, Function.identity());
    }
}

