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
   private static final Map<Identifier, Identifier> field_25839 = ImmutableMap.<Identifier, Identifier>builder()
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
      this.codec = codec.fieldOf("config").xmap(arg -> new ConfiguredStructureFeature<>(this, (C)arg), arg -> arg.config).codec();
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
      } else {
         StructureFeature<?> lv = Registry.STRUCTURE_FEATURE.get(new Identifier(string.toLowerCase(Locale.ROOT)));
         if (lv == null) {
            LOGGER.error("Unknown feature id: {}", string);
            return null;
         } else {
            int i = tag.getInt("ChunkX");
            int j = tag.getInt("ChunkZ");
            int k = tag.getInt("references");
            BlockBox lv2 = tag.contains("BB") ? new BlockBox(tag.getIntArray("BB")) : BlockBox.empty();
            ListTag lv3 = tag.getList("Children", 10);

            try {
               StructureStart<?> lv4 = lv.createStart(i, j, lv2, k, worldSeed);

               for (int m = 0; m < lv3.size(); m++) {
                  CompoundTag lv5 = lv3.getCompound(m);
                  String string2 = lv5.getString("id").toLowerCase(Locale.ROOT);
                  Identifier lv6 = new Identifier(string2);
                  Identifier lv7 = field_25839.getOrDefault(lv6, lv6);
                  StructurePieceType lv8 = Registry.STRUCTURE_PIECE.get(lv7);
                  if (lv8 == null) {
                     LOGGER.error("Unknown structure piece id: {}", lv7);
                  } else {
                     try {
                        StructurePiece lv9 = lv8.load(manager, lv5);
                        lv4.getChildren().add(lv9);
                     } catch (Exception var19) {
                        LOGGER.error("Exception loading structure piece with id {}", lv7, var19);
                     }
                  }
               }

               return lv4;
            } catch (Exception var20) {
               LOGGER.error("Failed Start with id {}", string, var20);
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
      int j = config.getSpacing();
      int k = searchStartPos.getX() >> 4;
      int m = searchStartPos.getZ() >> 4;
      int n = 0;

      for (ChunkRandom lv = new ChunkRandom(); n <= searchRadius; n++) {
         for (int o = -n; o <= n; o++) {
            boolean bl2 = o == -n || o == n;

            for (int p = -n; p <= n; p++) {
               boolean bl3 = p == -n || p == n;
               if (bl2 || bl3) {
                  int q = k + j * o;
                  int r = m + j * p;
                  ChunkPos lv2 = this.getStartChunk(config, worldSeed, lv, q, r);
                  Chunk lv3 = world.getChunk(lv2.x, lv2.z, ChunkStatus.STRUCTURE_STARTS);
                  StructureStart<?> lv4 = structureAccessor.getStructureStart(ChunkSectionPos.from(lv3.getPos(), 0), this, lv3);
                  if (lv4 != null && lv4.hasChildren()) {
                     if (skipExistingChunks && lv4.isInExistingChunk()) {
                        lv4.incrementReferences();
                        return lv4.getPos();
                     }

                     if (!skipExistingChunks) {
                        return lv4.getPos();
                     }
                  }

                  if (n == 0) {
                     break;
                  }
               }
            }

            if (n == 0) {
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
      int k = config.getSpacing();
      int m = config.getSeparation();
      int n = Math.floorDiv(chunkX, k);
      int o = Math.floorDiv(chunkY, k);
      placementRandom.setRegionSeed(worldSeed, n, o, config.getSalt());
      int p;
      int q;
      if (this.isUniformDistribution()) {
         p = placementRandom.nextInt(k - m);
         q = placementRandom.nextInt(k - m);
      } else {
         p = (placementRandom.nextInt(k - m) + placementRandom.nextInt(k - m)) / 2;
         q = (placementRandom.nextInt(k - m) + placementRandom.nextInt(k - m)) / 2;
      }

      return new ChunkPos(n * k + p, o * k + q);
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
      DynamicRegistryManager arg,
      ChunkGenerator arg2,
      BiomeSource arg3,
      StructureManager arg4,
      long worldSeed,
      ChunkPos arg5,
      Biome arg6,
      int referenceCount,
      ChunkRandom arg7,
      StructureConfig arg8,
      C arg9
   ) {
      ChunkPos lv = this.getStartChunk(arg8, worldSeed, arg7, arg5.x, arg5.z);
      if (arg5.x == lv.x && arg5.z == lv.z && this.shouldStartAt(arg2, arg3, worldSeed, arg7, arg5.x, arg5.z, arg6, lv, arg9)) {
         StructureStart<C> lv2 = this.createStart(arg5.x, arg5.z, BlockBox.empty(), referenceCount, worldSeed);
         lv2.init(arg, arg2, arg4, arg5.x, arg5.z, arg6, arg9);
         if (lv2.hasChildren()) {
            return lv2;
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
