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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructureFeature<C extends FeatureConfig> {
   public static final BiMap<String, StructureFeature<?>> STRUCTURES = HashBiMap.create();
   private static final Map<StructureFeature<?>, GenerationStep.Feature> STRUCTURE_TO_GENERATION_STEP = Maps.newHashMap();
   private static final Logger LOGGER = LogManager.getLogger();
   public static final StructureFeature<StructurePoolFeatureConfig> PILLAGER_OUTPOST = register(
      "Pillager_Outpost", new PillagerOutpostFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<MineshaftFeatureConfig> MINESHAFT = register(
      "Mineshaft", new MineshaftFeature(MineshaftFeatureConfig.CODEC), GenerationStep.Feature.UNDERGROUND_STRUCTURES
   );
   public static final StructureFeature<DefaultFeatureConfig> MANSION = register(
      "Mansion", new WoodlandMansionFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<DefaultFeatureConfig> JUNGLE_PYRAMID = register(
      "Jungle_Pyramid", new JungleTempleFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<DefaultFeatureConfig> DESERT_PYRAMID = register(
      "Desert_Pyramid", new DesertPyramidFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<DefaultFeatureConfig> IGLOO = register(
      "Igloo", new IglooFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<RuinedPortalFeatureConfig> RUINED_PORTAL = register(
      "Ruined_Portal", new RuinedPortalFeature(RuinedPortalFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<ShipwreckFeatureConfig> SHIPWRECK = register(
      "Shipwreck", new ShipwreckFeature(ShipwreckFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final SwampHutFeature SWAMP_HUT = register(
      "Swamp_Hut", new SwampHutFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<DefaultFeatureConfig> STRONGHOLD = register(
      "Stronghold", new StrongholdFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.STRONGHOLDS
   );
   public static final StructureFeature<DefaultFeatureConfig> MONUMENT = register(
      "Monument", new OceanMonumentFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<OceanRuinFeatureConfig> OCEAN_RUIN = register(
      "Ocean_Ruin", new OceanRuinFeature(OceanRuinFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<DefaultFeatureConfig> FORTRESS = register(
      "Fortress", new NetherFortressFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.UNDERGROUND_DECORATION
   );
   public static final StructureFeature<DefaultFeatureConfig> END_CITY = register(
      "EndCity", new EndCityFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<ProbabilityConfig> BURIED_TREASURE = register(
      "Buried_Treasure", new BuriedTreasureFeature(ProbabilityConfig.CODEC), GenerationStep.Feature.UNDERGROUND_STRUCTURES
   );
   public static final StructureFeature<StructurePoolFeatureConfig> VILLAGE = register(
      "Village", new VillageFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final StructureFeature<DefaultFeatureConfig> NETHER_FOSSIL = register(
      "Nether_Fossil", new NetherFossilFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.UNDERGROUND_DECORATION
   );
   public static final StructureFeature<StructurePoolFeatureConfig> BASTION_REMNANT = register(
      "Bastion_Remnant", new BastionRemnantFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES
   );
   public static final List<StructureFeature<?>> JIGSAW_STRUCTURES = ImmutableList.of(PILLAGER_OUTPOST, VILLAGE, NETHER_FOSSIL);
   private static final Identifier JIGSAW_ID = new Identifier("jigsaw");
   private static final Map<Identifier, Identifier> field_25839 = ImmutableMap.builder()
      .put(new Identifier("nvi"), JIGSAW_ID)
      .put(new Identifier("pcp"), JIGSAW_ID)
      .put(new Identifier("bastionremnant"), JIGSAW_ID)
      .put(new Identifier("runtime"), JIGSAW_ID)
      .build();
   private final Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> codec;

   private static <F extends StructureFeature<?>> F register(String name, F structureFeature, GenerationStep.Feature step) {
      STRUCTURES.put(name.toLowerCase(Locale.ROOT), structureFeature);
      STRUCTURE_TO_GENERATION_STEP.put(structureFeature, step);
      return Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structureFeature);
   }

   public StructureFeature(Codec<C> codec) {
      this.codec = codec.fieldOf("config").xmap(_snowman -> new ConfiguredStructureFeature<>(this, (C)_snowman), _snowman -> _snowman.config).codec();
   }

   public GenerationStep.Feature getGenerationStep() {
      return STRUCTURE_TO_GENERATION_STEP.get(this);
   }

   public static void method_28664() {
   }

   @Nullable
   public static StructureStart<?> readStructureStart(StructureManager manager, CompoundTag tag, long worldSeed) {
      String _snowman = tag.getString("id");
      if ("INVALID".equals(_snowman)) {
         return StructureStart.DEFAULT;
      } else {
         StructureFeature<?> _snowmanx = Registry.STRUCTURE_FEATURE.get(new Identifier(_snowman.toLowerCase(Locale.ROOT)));
         if (_snowmanx == null) {
            LOGGER.error("Unknown feature id: {}", _snowman);
            return null;
         } else {
            int _snowmanxx = tag.getInt("ChunkX");
            int _snowmanxxx = tag.getInt("ChunkZ");
            int _snowmanxxxx = tag.getInt("references");
            BlockBox _snowmanxxxxx = tag.contains("BB") ? new BlockBox(tag.getIntArray("BB")) : BlockBox.empty();
            ListTag _snowmanxxxxxx = tag.getList("Children", 10);

            try {
               StructureStart<?> _snowmanxxxxxxx = _snowmanx.createStart(_snowmanxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, worldSeed);

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx.size(); _snowmanxxxxxxxx++) {
                  CompoundTag _snowmanxxxxxxxxx = _snowmanxxxxxx.getCompound(_snowmanxxxxxxxx);
                  String _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getString("id").toLowerCase(Locale.ROOT);
                  Identifier _snowmanxxxxxxxxxxx = new Identifier(_snowmanxxxxxxxxxx);
                  Identifier _snowmanxxxxxxxxxxxx = field_25839.getOrDefault(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                  StructurePieceType _snowmanxxxxxxxxxxxxx = Registry.STRUCTURE_PIECE.get(_snowmanxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxx == null) {
                     LOGGER.error("Unknown structure piece id: {}", _snowmanxxxxxxxxxxxx);
                  } else {
                     try {
                        StructurePiece _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.load(manager, _snowmanxxxxxxxxx);
                        _snowmanxxxxxxx.getChildren().add(_snowmanxxxxxxxxxxxxxx);
                     } catch (Exception var19) {
                        LOGGER.error("Exception loading structure piece with id {}", _snowmanxxxxxxxxxxxx, var19);
                     }
                  }
               }

               return _snowmanxxxxxxx;
            } catch (Exception var20) {
               LOGGER.error("Failed Start with id {}", _snowman, var20);
               return null;
            }
         }
      }
   }

   public Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> getCodec() {
      return this.codec;
   }

   public ConfiguredStructureFeature<C, ? extends StructureFeature<C>> configure(C config) {
      return new ConfiguredStructureFeature<>(this, config);
   }

   @Nullable
   public BlockPos locateStructure(
      WorldView world,
      StructureAccessor structureAccessor,
      BlockPos searchStartPos,
      int searchRadius,
      boolean skipExistingChunks,
      long worldSeed,
      StructureConfig config
   ) {
      int _snowman = config.getSpacing();
      int _snowmanx = searchStartPos.getX() >> 4;
      int _snowmanxx = searchStartPos.getZ() >> 4;
      int _snowmanxxx = 0;

      for (ChunkRandom _snowmanxxxx = new ChunkRandom(); _snowmanxxx <= searchRadius; _snowmanxxx++) {
         for (int _snowmanxxxxx = -_snowmanxxx; _snowmanxxxxx <= _snowmanxxx; _snowmanxxxxx++) {
            boolean _snowmanxxxxxx = _snowmanxxxxx == -_snowmanxxx || _snowmanxxxxx == _snowmanxxx;

            for (int _snowmanxxxxxxx = -_snowmanxxx; _snowmanxxxxxxx <= _snowmanxxx; _snowmanxxxxxxx++) {
               boolean _snowmanxxxxxxxx = _snowmanxxxxxxx == -_snowmanxxx || _snowmanxxxxxxx == _snowmanxxx;
               if (_snowmanxxxxxx || _snowmanxxxxxxxx) {
                  int _snowmanxxxxxxxxx = _snowmanx + _snowman * _snowmanxxxxx;
                  int _snowmanxxxxxxxxxx = _snowmanxx + _snowman * _snowmanxxxxxxx;
                  ChunkPos _snowmanxxxxxxxxxxx = this.getStartChunk(config, worldSeed, _snowmanxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                  Chunk _snowmanxxxxxxxxxxxx = world.getChunk(_snowmanxxxxxxxxxxx.x, _snowmanxxxxxxxxxxx.z, ChunkStatus.STRUCTURE_STARTS);
                  StructureStart<?> _snowmanxxxxxxxxxxxxx = structureAccessor.getStructureStart(ChunkSectionPos.from(_snowmanxxxxxxxxxxxx.getPos(), 0), this, _snowmanxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxx.hasChildren()) {
                     if (skipExistingChunks && _snowmanxxxxxxxxxxxxx.isInExistingChunk()) {
                        _snowmanxxxxxxxxxxxxx.incrementReferences();
                        return _snowmanxxxxxxxxxxxxx.getPos();
                     }

                     if (!skipExistingChunks) {
                        return _snowmanxxxxxxxxxxxxx.getPos();
                     }
                  }

                  if (_snowmanxxx == 0) {
                     break;
                  }
               }
            }

            if (_snowmanxxx == 0) {
               break;
            }
         }
      }

      return null;
   }

   protected boolean isUniformDistribution() {
      return true;
   }

   public final ChunkPos getStartChunk(StructureConfig config, long worldSeed, ChunkRandom placementRandom, int chunkX, int chunkY) {
      int _snowman = config.getSpacing();
      int _snowmanx = config.getSeparation();
      int _snowmanxx = Math.floorDiv(chunkX, _snowman);
      int _snowmanxxx = Math.floorDiv(chunkY, _snowman);
      placementRandom.setRegionSeed(worldSeed, _snowmanxx, _snowmanxxx, config.getSalt());
      int _snowmanxxxx;
      int _snowmanxxxxx;
      if (this.isUniformDistribution()) {
         _snowmanxxxx = placementRandom.nextInt(_snowman - _snowmanx);
         _snowmanxxxxx = placementRandom.nextInt(_snowman - _snowmanx);
      } else {
         _snowmanxxxx = (placementRandom.nextInt(_snowman - _snowmanx) + placementRandom.nextInt(_snowman - _snowmanx)) / 2;
         _snowmanxxxxx = (placementRandom.nextInt(_snowman - _snowmanx) + placementRandom.nextInt(_snowman - _snowmanx)) / 2;
      }

      return new ChunkPos(_snowmanxx * _snowman + _snowmanxxxx, _snowmanxxx * _snowman + _snowmanxxxxx);
   }

   protected boolean shouldStartAt(
      ChunkGenerator chunkGenerator,
      BiomeSource biomeSource,
      long worldSeed,
      ChunkRandom random,
      int chunkX,
      int chunkZ,
      Biome biome,
      ChunkPos chunkPos,
      C config
   ) {
      return true;
   }

   private StructureStart<C> createStart(int chunkX, int chunkZ, BlockBox boundingBox, int referenceCount, long worldSeed) {
      return this.getStructureStartFactory().create(this, chunkX, chunkZ, boundingBox, referenceCount, worldSeed);
   }

   public StructureStart<?> tryPlaceStart(
      DynamicRegistryManager _snowman,
      ChunkGenerator _snowman,
      BiomeSource _snowman,
      StructureManager _snowman,
      long worldSeed,
      ChunkPos _snowman,
      Biome _snowman,
      int referenceCount,
      ChunkRandom _snowman,
      StructureConfig _snowman,
      C _snowman
   ) {
      ChunkPos _snowmanxxxxxxxxx = this.getStartChunk(_snowman, worldSeed, _snowman, _snowman.x, _snowman.z);
      if (_snowman.x == _snowmanxxxxxxxxx.x && _snowman.z == _snowmanxxxxxxxxx.z && this.shouldStartAt(_snowman, _snowman, worldSeed, _snowman, _snowman.x, _snowman.z, _snowman, _snowmanxxxxxxxxx, _snowman)) {
         StructureStart<C> _snowmanxxxxxxxxxx = this.createStart(_snowman.x, _snowman.z, BlockBox.empty(), referenceCount, worldSeed);
         _snowmanxxxxxxxxxx.init(_snowman, _snowman, _snowman, _snowman.x, _snowman.z, _snowman, _snowman);
         if (_snowmanxxxxxxxxxx.hasChildren()) {
            return _snowmanxxxxxxxxxx;
         }
      }

      return StructureStart.DEFAULT;
   }

   public abstract StructureFeature.StructureStartFactory<C> getStructureStartFactory();

   public String getName() {
      return (String)STRUCTURES.inverse().get(this);
   }

   public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
      return ImmutableList.of();
   }

   public List<SpawnSettings.SpawnEntry> getCreatureSpawns() {
      return ImmutableList.of();
   }

   public interface StructureStartFactory<C extends FeatureConfig> {
      StructureStart<C> create(StructureFeature<C> feature, int chunkX, int chunkZ, BlockBox box, int referenceCount, long worldSeed);
   }
}
