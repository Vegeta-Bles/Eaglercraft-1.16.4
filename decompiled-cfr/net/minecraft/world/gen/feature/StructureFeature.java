/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.HashBiMap
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.BastionRemnantFeature;
import net.minecraft.world.gen.feature.BuriedTreasureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.DesertPyramidFeature;
import net.minecraft.world.gen.feature.EndCityFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.IglooFeature;
import net.minecraft.world.gen.feature.JungleTempleFeature;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.feature.NetherFortressFeature;
import net.minecraft.world.gen.feature.NetherFossilFeature;
import net.minecraft.world.gen.feature.OceanMonumentFeature;
import net.minecraft.world.gen.feature.OceanRuinFeature;
import net.minecraft.world.gen.feature.OceanRuinFeatureConfig;
import net.minecraft.world.gen.feature.PillagerOutpostFeature;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import net.minecraft.world.gen.feature.RuinedPortalFeatureConfig;
import net.minecraft.world.gen.feature.ShipwreckFeature;
import net.minecraft.world.gen.feature.ShipwreckFeatureConfig;
import net.minecraft.world.gen.feature.StrongholdFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.feature.SwampHutFeature;
import net.minecraft.world.gen.feature.VillageFeature;
import net.minecraft.world.gen.feature.WoodlandMansionFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructureFeature<C extends FeatureConfig> {
    public static final BiMap<String, StructureFeature<?>> STRUCTURES = HashBiMap.create();
    private static final Map<StructureFeature<?>, GenerationStep.Feature> STRUCTURE_TO_GENERATION_STEP = Maps.newHashMap();
    private static final Logger LOGGER = LogManager.getLogger();
    public static final StructureFeature<StructurePoolFeatureConfig> PILLAGER_OUTPOST = StructureFeature.register("Pillager_Outpost", new PillagerOutpostFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<MineshaftFeatureConfig> MINESHAFT = StructureFeature.register("Mineshaft", new MineshaftFeature(MineshaftFeatureConfig.CODEC), GenerationStep.Feature.UNDERGROUND_STRUCTURES);
    public static final StructureFeature<DefaultFeatureConfig> MANSION = StructureFeature.register("Mansion", new WoodlandMansionFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<DefaultFeatureConfig> JUNGLE_PYRAMID = StructureFeature.register("Jungle_Pyramid", new JungleTempleFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<DefaultFeatureConfig> DESERT_PYRAMID = StructureFeature.register("Desert_Pyramid", new DesertPyramidFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<DefaultFeatureConfig> IGLOO = StructureFeature.register("Igloo", new IglooFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<RuinedPortalFeatureConfig> RUINED_PORTAL = StructureFeature.register("Ruined_Portal", new RuinedPortalFeature(RuinedPortalFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<ShipwreckFeatureConfig> SHIPWRECK = StructureFeature.register("Shipwreck", new ShipwreckFeature(ShipwreckFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final SwampHutFeature SWAMP_HUT = StructureFeature.register("Swamp_Hut", new SwampHutFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<DefaultFeatureConfig> STRONGHOLD = StructureFeature.register("Stronghold", new StrongholdFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.STRONGHOLDS);
    public static final StructureFeature<DefaultFeatureConfig> MONUMENT = StructureFeature.register("Monument", new OceanMonumentFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<OceanRuinFeatureConfig> OCEAN_RUIN = StructureFeature.register("Ocean_Ruin", new OceanRuinFeature(OceanRuinFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<DefaultFeatureConfig> FORTRESS = StructureFeature.register("Fortress", new NetherFortressFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.UNDERGROUND_DECORATION);
    public static final StructureFeature<DefaultFeatureConfig> END_CITY = StructureFeature.register("EndCity", new EndCityFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<ProbabilityConfig> BURIED_TREASURE = StructureFeature.register("Buried_Treasure", new BuriedTreasureFeature(ProbabilityConfig.CODEC), GenerationStep.Feature.UNDERGROUND_STRUCTURES);
    public static final StructureFeature<StructurePoolFeatureConfig> VILLAGE = StructureFeature.register("Village", new VillageFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<DefaultFeatureConfig> NETHER_FOSSIL = StructureFeature.register("Nether_Fossil", new NetherFossilFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.UNDERGROUND_DECORATION);
    public static final StructureFeature<StructurePoolFeatureConfig> BASTION_REMNANT = StructureFeature.register("Bastion_Remnant", new BastionRemnantFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final List<StructureFeature<?>> JIGSAW_STRUCTURES = ImmutableList.of(PILLAGER_OUTPOST, VILLAGE, NETHER_FOSSIL);
    private static final Identifier JIGSAW_ID = new Identifier("jigsaw");
    private static final Map<Identifier, Identifier> field_25839 = ImmutableMap.builder().put((Object)new Identifier("nvi"), (Object)JIGSAW_ID).put((Object)new Identifier("pcp"), (Object)JIGSAW_ID).put((Object)new Identifier("bastionremnant"), (Object)JIGSAW_ID).put((Object)new Identifier("runtime"), (Object)JIGSAW_ID).build();
    private final Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> codec;

    private static <F extends StructureFeature<?>> F register(String name, F structureFeature, GenerationStep.Feature step) {
        STRUCTURES.put((Object)name.toLowerCase(Locale.ROOT), structureFeature);
        STRUCTURE_TO_GENERATION_STEP.put(structureFeature, step);
        return (F)Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structureFeature);
    }

    public StructureFeature(Codec<C> codec) {
        this.codec = codec.fieldOf("config").xmap(featureConfig -> new ConfiguredStructureFeature<FeatureConfig, StructureFeature>(this, (FeatureConfig)featureConfig), configuredStructureFeature -> configuredStructureFeature.config).codec();
    }

    public GenerationStep.Feature getGenerationStep() {
        return STRUCTURE_TO_GENERATION_STEP.get(this);
    }

    public static void method_28664() {
    }

    @Nullable
    public static StructureStart<?> readStructureStart(StructureManager manager, CompoundTag tag, long worldSeed) {
        String string = tag.getString("id");
        if ("INVALID".equals(string)) {
            return StructureStart.DEFAULT;
        }
        StructureFeature<?> _snowman2 = Registry.STRUCTURE_FEATURE.get(new Identifier(string.toLowerCase(Locale.ROOT)));
        if (_snowman2 == null) {
            LOGGER.error("Unknown feature id: {}", (Object)string);
            return null;
        }
        int _snowman3 = tag.getInt("ChunkX");
        int _snowman4 = tag.getInt("ChunkZ");
        int _snowman5 = tag.getInt("references");
        BlockBox _snowman6 = tag.contains("BB") ? new BlockBox(tag.getIntArray("BB")) : BlockBox.empty();
        ListTag _snowman7 = tag.getList("Children", 10);
        try {
            StructureStart<?> structureStart = super.createStart(_snowman3, _snowman4, _snowman6, _snowman5, worldSeed);
            for (int i = 0; i < _snowman7.size(); ++i) {
                CompoundTag compoundTag = _snowman7.getCompound(i);
                String _snowman8 = compoundTag.getString("id").toLowerCase(Locale.ROOT);
                Identifier _snowman9 = new Identifier(_snowman8);
                Identifier _snowman10 = field_25839.getOrDefault(_snowman9, _snowman9);
                StructurePieceType _snowman11 = Registry.STRUCTURE_PIECE.get(_snowman10);
                if (_snowman11 == null) {
                    LOGGER.error("Unknown structure piece id: {}", (Object)_snowman10);
                    continue;
                }
                try {
                    StructurePiece structurePiece = _snowman11.load(manager, compoundTag);
                    structureStart.getChildren().add(structurePiece);
                    continue;
                }
                catch (Exception exception) {
                    LOGGER.error("Exception loading structure piece with id {}", (Object)_snowman10, (Object)exception);
                }
            }
            return structureStart;
        }
        catch (Exception exception) {
            LOGGER.error("Failed Start with id {}", (Object)string, (Object)exception);
            return null;
        }
    }

    public Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> getCodec() {
        return this.codec;
    }

    public ConfiguredStructureFeature<C, ? extends StructureFeature<C>> configure(C config) {
        return new ConfiguredStructureFeature<C, StructureFeature>(this, config);
    }

    @Nullable
    public BlockPos locateStructure(WorldView world, StructureAccessor structureAccessor, BlockPos searchStartPos, int searchRadius, boolean skipExistingChunks, long worldSeed, StructureConfig config) {
        int n = config.getSpacing();
        _snowman = searchStartPos.getX() >> 4;
        _snowman = searchStartPos.getZ() >> 4;
        ChunkRandom _snowman2 = new ChunkRandom();
        block0: for (_snowman = 0; _snowman <= searchRadius; ++_snowman) {
            for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                boolean bl = _snowman == -_snowman || _snowman == _snowman;
                for (int i = -_snowman; i <= _snowman; ++i) {
                    boolean bl2 = _snowman = i == -_snowman || i == _snowman;
                    if (!bl && !_snowman) continue;
                    _snowman = _snowman + n * _snowman;
                    _snowman = _snowman + n * i;
                    ChunkPos chunkPos = this.getStartChunk(config, worldSeed, _snowman2, _snowman, _snowman);
                    Chunk _snowman3 = world.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_STARTS);
                    StructureStart<?> _snowman4 = structureAccessor.getStructureStart(ChunkSectionPos.from(_snowman3.getPos(), 0), this, _snowman3);
                    if (_snowman4 != null && _snowman4.hasChildren()) {
                        if (skipExistingChunks && _snowman4.isInExistingChunk()) {
                            _snowman4.incrementReferences();
                            return _snowman4.getPos();
                        }
                        if (!skipExistingChunks) {
                            return _snowman4.getPos();
                        }
                    }
                    if (_snowman == 0) break;
                }
                if (_snowman == 0) continue block0;
            }
        }
        return null;
    }

    protected boolean isUniformDistribution() {
        return true;
    }

    public final ChunkPos getStartChunk(StructureConfig config, long worldSeed, ChunkRandom placementRandom, int chunkX, int chunkY) {
        int n = config.getSpacing();
        _snowman = config.getSeparation();
        _snowman = Math.floorDiv(chunkX, n);
        _snowman = Math.floorDiv(chunkY, n);
        placementRandom.setRegionSeed(worldSeed, _snowman, _snowman, config.getSalt());
        if (this.isUniformDistribution()) {
            _snowman = placementRandom.nextInt(n - _snowman);
            _snowman = placementRandom.nextInt(n - _snowman);
        } else {
            _snowman = (placementRandom.nextInt(n - _snowman) + placementRandom.nextInt(n - _snowman)) / 2;
            _snowman = (placementRandom.nextInt(n - _snowman) + placementRandom.nextInt(n - _snowman)) / 2;
        }
        return new ChunkPos(_snowman * n + _snowman, _snowman * n + _snowman);
    }

    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, ChunkRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, C config) {
        return true;
    }

    private StructureStart<C> createStart(int chunkX, int chunkZ, BlockBox boundingBox, int referenceCount, long worldSeed) {
        return this.getStructureStartFactory().create(this, chunkX, chunkZ, boundingBox, referenceCount, worldSeed);
    }

    public StructureStart<?> tryPlaceStart(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, BiomeSource biomeSource, StructureManager structureManager, long worldSeed, ChunkPos chunkPos, Biome biome, int referenceCount, ChunkRandom chunkRandom, StructureConfig structureConfig, C c) {
        ChunkPos chunkPos2 = this.getStartChunk(structureConfig, worldSeed, chunkRandom, chunkPos.x, chunkPos.z);
        if (chunkPos.x == chunkPos2.x && chunkPos.z == chunkPos2.z && this.shouldStartAt(chunkGenerator, biomeSource, worldSeed, chunkRandom, chunkPos.x, chunkPos.z, biome, chunkPos2, c)) {
            StructureStart<C> structureStart = this.createStart(chunkPos.x, chunkPos.z, BlockBox.empty(), referenceCount, worldSeed);
            structureStart.init(dynamicRegistryManager, chunkGenerator, structureManager, chunkPos.x, chunkPos.z, biome, c);
            if (structureStart.hasChildren()) {
                return structureStart;
            }
        }
        return StructureStart.DEFAULT;
    }

    public abstract StructureStartFactory<C> getStructureStartFactory();

    public String getName() {
        return (String)STRUCTURES.inverse().get((Object)this);
    }

    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return ImmutableList.of();
    }

    public List<SpawnSettings.SpawnEntry> getCreatureSpawns() {
        return ImmutableList.of();
    }

    public static interface StructureStartFactory<C extends FeatureConfig> {
        public StructureStart<C> create(StructureFeature<C> var1, int var2, int var3, BlockBox var4, int var5, long var6);
    }
}

