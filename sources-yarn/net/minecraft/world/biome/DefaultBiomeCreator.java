package net.minecraft.world.biome;

import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class DefaultBiomeCreator {
   private static int getSkyColor(float temperature) {
      float g = temperature / 3.0F;
      g = MathHelper.clamp(g, -1.0F, 1.0F);
      return MathHelper.hsvToRgb(0.62222224F - g * 0.05F, 0.5F + g * 0.1F, 1.0F);
   }

   public static Biome createGiantTreeTaiga(float depth, float scale, float temperature, boolean spruce) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4));
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
      if (spruce) {
         DefaultBiomeFeatures.addBatsAndMonsters(lv);
      } else {
         DefaultBiomeFeatures.addBats(lv);
         DefaultBiomeFeatures.addMonsters(lv, 100, 25, 100);
      }

      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GIANT_TREE_TAIGA);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMossyRocks(lv2);
      DefaultBiomeFeatures.addLargeFerns(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, spruce ? ConfiguredFeatures.TREES_GIANT_SPRUCE : ConfiguredFeatures.TREES_GIANT);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addGiantTaigaGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addSweetBerryBushes(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.TAIGA)
         .depth(depth)
         .scale(scale)
         .temperature(temperature)
         .downfall(0.8F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(temperature))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createBirchForest(float depth, float scale, boolean tallTrees) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addForestFlowers(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      if (tallTrees) {
         DefaultBiomeFeatures.addTallBirchTrees(lv2);
      } else {
         DefaultBiomeFeatures.addBirchTrees(lv2);
      }

      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addForestGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.FOREST)
         .depth(depth)
         .scale(scale)
         .temperature(0.6F)
         .downfall(0.6F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.6F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createJungle() {
      return createJungle(0.1F, 0.2F, 40, 2, 3);
   }

   public static Biome createJungleEdge() {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(lv);
      return createJungleFeatures(0.1F, 0.2F, 0.8F, false, true, false, lv);
   }

   public static Biome createModifiedJungleEdge() {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(lv);
      return createJungleFeatures(0.2F, 0.4F, 0.8F, false, true, true, lv);
   }

   public static Biome createModifiedJungle() {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(lv);
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 10, 1, 1))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 1));
      return createJungleFeatures(0.2F, 0.4F, 0.9F, false, false, true, lv);
   }

   public static Biome createJungleHills() {
      return createJungle(0.45F, 0.3F, 10, 1, 1);
   }

   public static Biome createNormalBambooJungle() {
      return createBambooJungle(0.1F, 0.2F, 40, 2);
   }

   public static Biome createBambooJungleHills() {
      return createBambooJungle(0.45F, 0.3F, 10, 1);
   }

   private static Biome createJungle(float depth, float scale, int parrotWeight, int parrotMaxGroupSize, int ocelotMaxGroupSize) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(lv);
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, parrotWeight, 1, parrotMaxGroupSize))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, ocelotMaxGroupSize))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2));
      lv.playerSpawnFriendly();
      return createJungleFeatures(depth, scale, 0.9F, false, false, false, lv);
   }

   private static Biome createBambooJungle(float depth, float scale, int parrotWeight, int parrotMaxGroupSize) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(lv);
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, parrotWeight, 1, parrotMaxGroupSize))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 80, 1, 2))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 1));
      return createJungleFeatures(depth, scale, 0.9F, true, false, false, lv);
   }

   private static Biome createJungleFeatures(float depth, float scale, float downfall, boolean bl, boolean bl2, boolean bl3, SpawnSettings.Builder arg) {
      GenerationSettings.Builder lv = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      if (!bl2 && !bl3) {
         lv.structureFeature(ConfiguredStructureFeatures.JUNGLE_PYRAMID);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv);
      lv.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_JUNGLE);
      DefaultBiomeFeatures.addLandCarvers(lv);
      DefaultBiomeFeatures.addDefaultLakes(lv);
      DefaultBiomeFeatures.addDungeons(lv);
      DefaultBiomeFeatures.addMineables(lv);
      DefaultBiomeFeatures.addDefaultOres(lv);
      DefaultBiomeFeatures.addDefaultDisks(lv);
      if (bl) {
         DefaultBiomeFeatures.addBambooJungleTrees(lv);
      } else {
         if (!bl2 && !bl3) {
            DefaultBiomeFeatures.addBamboo(lv);
         }

         if (bl2) {
            DefaultBiomeFeatures.addJungleEdgeTrees(lv);
         } else {
            DefaultBiomeFeatures.addJungleTrees(lv);
         }
      }

      DefaultBiomeFeatures.addExtraDefaultFlowers(lv);
      DefaultBiomeFeatures.addJungleGrass(lv);
      DefaultBiomeFeatures.addDefaultMushrooms(lv);
      DefaultBiomeFeatures.addDefaultVegetation(lv);
      DefaultBiomeFeatures.addSprings(lv);
      DefaultBiomeFeatures.addJungleVegetation(lv);
      DefaultBiomeFeatures.addFrozenTopLayer(lv);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.JUNGLE)
         .depth(depth)
         .scale(scale)
         .temperature(0.95F)
         .downfall(downfall)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.95F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(arg.build())
         .generationSettings(lv.build())
         .build();
   }

   public static Biome createMountains(float depth, float scale, ConfiguredSurfaceBuilder<TernarySurfaceConfig> surfaceBuilder, boolean extraTrees) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 5, 4, 6));
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(surfaceBuilder);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      if (extraTrees) {
         DefaultBiomeFeatures.addExtraMountainTrees(lv2);
      } else {
         DefaultBiomeFeatures.addMountainTrees(lv2);
      }

      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addDefaultGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addEmeraldOre(lv2);
      DefaultBiomeFeatures.addInfestedStone(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.EXTREME_HILLS)
         .depth(depth)
         .scale(scale)
         .temperature(0.2F)
         .downfall(0.3F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.2F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createDesert(float depth, float scale, boolean bl, boolean bl2, boolean bl3) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addDesertMobs(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.DESERT);
      if (bl) {
         lv2.structureFeature(ConfiguredStructureFeatures.VILLAGE_DESERT);
         lv2.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      if (bl2) {
         lv2.structureFeature(ConfiguredStructureFeatures.DESERT_PYRAMID);
      }

      if (bl3) {
         DefaultBiomeFeatures.addFossils(lv2);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_DESERT);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDesertLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addDefaultGrass(lv2);
      DefaultBiomeFeatures.addDesertDeadBushes(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDesertVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addDesertFeatures(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.DESERT)
         .depth(depth)
         .scale(scale)
         .temperature(2.0F)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(2.0F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createPlains(boolean bl) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addPlainsMobs(lv);
      if (!bl) {
         lv.playerSpawnFriendly();
      }

      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      if (!bl) {
         lv2.structureFeature(ConfiguredStructureFeatures.VILLAGE_PLAINS).structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addPlainsTallGrass(lv2);
      if (bl) {
         lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUNFLOWER);
      }

      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addPlainsFeatures(lv2);
      if (bl) {
         lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUGAR_CANE);
      }

      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      if (bl) {
         lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_PUMPKIN);
      } else {
         DefaultBiomeFeatures.addDefaultVegetation(lv2);
      }

      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.PLAINS)
         .depth(0.125F)
         .scale(0.05F)
         .temperature(0.8F)
         .downfall(0.4F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.8F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   private static Biome composeEndSpawnSettings(GenerationSettings.Builder arg) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addEndMobs(lv);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.THEEND)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(0.5F)
         .downfall(0.5F)
         .effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(10518688).skyColor(0).moodSound(BiomeMoodSound.CAVE).build())
         .spawnSettings(lv.build())
         .generationSettings(arg.build())
         .build();
   }

   public static Biome createEndBarrens() {
      GenerationSettings.Builder lv = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.END);
      return composeEndSpawnSettings(lv);
   }

   public static Biome createTheEnd() {
      GenerationSettings.Builder lv = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.END_SPIKE);
      return composeEndSpawnSettings(lv);
   }

   public static Biome createEndMidlands() {
      GenerationSettings.Builder lv = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .structureFeature(ConfiguredStructureFeatures.END_CITY);
      return composeEndSpawnSettings(lv);
   }

   public static Biome createEndHighlands() {
      GenerationSettings.Builder lv = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .structureFeature(ConfiguredStructureFeatures.END_CITY)
         .feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.END_GATEWAY)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CHORUS_PLANT);
      return composeEndSpawnSettings(lv);
   }

   public static Biome createSmallEndIslands() {
      GenerationSettings.Builder lv = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .feature(GenerationStep.Feature.RAW_GENERATION, ConfiguredFeatures.END_ISLAND_DECORATED);
      return composeEndSpawnSettings(lv);
   }

   public static Biome createMushroomFields(float depth, float scale) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addMushroomMobs(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.MYCELIUM);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addMushroomFieldsFeatures(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.MUSHROOM)
         .depth(depth)
         .scale(scale)
         .temperature(0.9F)
         .downfall(1.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.9F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   private static Biome composeSavannaGenerationSettings(float depth, float scale, float temperature, boolean bl, boolean bl2, SpawnSettings.Builder arg) {
      GenerationSettings.Builder lv = new GenerationSettings.Builder()
         .surfaceBuilder(bl2 ? ConfiguredSurfaceBuilders.SHATTERED_SAVANNA : ConfiguredSurfaceBuilders.GRASS);
      if (!bl && !bl2) {
         lv.structureFeature(ConfiguredStructureFeatures.VILLAGE_SAVANNA).structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv);
      lv.structureFeature(bl ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv);
      DefaultBiomeFeatures.addDefaultLakes(lv);
      DefaultBiomeFeatures.addDungeons(lv);
      if (!bl2) {
         DefaultBiomeFeatures.addSavannaTallGrass(lv);
      }

      DefaultBiomeFeatures.addMineables(lv);
      DefaultBiomeFeatures.addDefaultOres(lv);
      DefaultBiomeFeatures.addDefaultDisks(lv);
      if (bl2) {
         DefaultBiomeFeatures.addExtraSavannaTrees(lv);
         DefaultBiomeFeatures.addDefaultFlowers(lv);
         DefaultBiomeFeatures.addShatteredSavannaGrass(lv);
      } else {
         DefaultBiomeFeatures.addSavannaTrees(lv);
         DefaultBiomeFeatures.addExtraDefaultFlowers(lv);
         DefaultBiomeFeatures.addSavannaGrass(lv);
      }

      DefaultBiomeFeatures.addDefaultMushrooms(lv);
      DefaultBiomeFeatures.addDefaultVegetation(lv);
      DefaultBiomeFeatures.addSprings(lv);
      DefaultBiomeFeatures.addFrozenTopLayer(lv);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.SAVANNA)
         .depth(depth)
         .scale(scale)
         .temperature(temperature)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(temperature))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(arg.build())
         .generationSettings(lv.build())
         .build();
   }

   public static Biome createSavanna(float depth, float scale, float temperature, boolean bl, boolean bl2) {
      SpawnSettings.Builder lv = createSavannaSpawnSettings();
      return composeSavannaGenerationSettings(depth, scale, temperature, bl, bl2, lv);
   }

   private static SpawnSettings.Builder createSavannaSpawnSettings() {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.HORSE, 1, 2, 6))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.DONKEY, 1, 1, 1));
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      return lv;
   }

   public static Biome createSavannaPlateau() {
      SpawnSettings.Builder lv = createSavannaSpawnSettings();
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 8, 4, 4));
      return composeSavannaGenerationSettings(1.5F, 0.025F, 1.0F, true, false, lv);
   }

   private static Biome createBadlands(ConfiguredSurfaceBuilder<TernarySurfaceConfig> arg, float depth, float scale, boolean bl, boolean bl2) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(arg);
      DefaultBiomeFeatures.addBadlandsUndergroundStructures(lv2);
      lv2.structureFeature(bl ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addExtraGoldOre(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      if (bl2) {
         DefaultBiomeFeatures.addBadlandsPlateauTrees(lv2);
      }

      DefaultBiomeFeatures.addBadlandsGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addBadlandsVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.MESA)
         .depth(depth)
         .scale(scale)
         .temperature(2.0F)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(2.0F))
               .foliageColor(10387789)
               .grassColor(9470285)
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createNormalBadlands(float depth, float scale, boolean bl) {
      return createBadlands(ConfiguredSurfaceBuilders.BADLANDS, depth, scale, bl, false);
   }

   public static Biome createWoodedBadlandsPlateau(float depth, float scale) {
      return createBadlands(ConfiguredSurfaceBuilders.WOODED_BADLANDS, depth, scale, true, true);
   }

   public static Biome createErodedBadlands() {
      return createBadlands(ConfiguredSurfaceBuilders.ERODED_BADLANDS, 0.1F, 0.2F, true, false);
   }

   private static Biome createOcean(SpawnSettings.Builder arg, int waterColor, int waterFogColor, boolean deep, GenerationSettings.Builder arg2) {
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.OCEAN)
         .depth(deep ? -1.8F : -1.0F)
         .scale(0.1F)
         .temperature(0.5F)
         .downfall(0.5F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(waterColor)
               .waterFogColor(waterFogColor)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.5F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(arg.build())
         .generationSettings(arg2.build())
         .build();
   }

   private static GenerationSettings.Builder createOceanGenerationSettings(
      ConfiguredSurfaceBuilder<TernarySurfaceConfig> arg, boolean bl, boolean bl2, boolean bl3
   ) {
      GenerationSettings.Builder lv = new GenerationSettings.Builder().surfaceBuilder(arg);
      ConfiguredStructureFeature<?, ?> lv2 = bl2 ? ConfiguredStructureFeatures.OCEAN_RUIN_WARM : ConfiguredStructureFeatures.OCEAN_RUIN_COLD;
      if (bl3) {
         if (bl) {
            lv.structureFeature(ConfiguredStructureFeatures.MONUMENT);
         }

         DefaultBiomeFeatures.addOceanStructures(lv);
         lv.structureFeature(lv2);
      } else {
         lv.structureFeature(lv2);
         if (bl) {
            lv.structureFeature(ConfiguredStructureFeatures.MONUMENT);
         }

         DefaultBiomeFeatures.addOceanStructures(lv);
      }

      lv.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_OCEAN);
      DefaultBiomeFeatures.addOceanCarvers(lv);
      DefaultBiomeFeatures.addDefaultLakes(lv);
      DefaultBiomeFeatures.addDungeons(lv);
      DefaultBiomeFeatures.addMineables(lv);
      DefaultBiomeFeatures.addDefaultOres(lv);
      DefaultBiomeFeatures.addDefaultDisks(lv);
      DefaultBiomeFeatures.addWaterBiomeOakTrees(lv);
      DefaultBiomeFeatures.addDefaultFlowers(lv);
      DefaultBiomeFeatures.addDefaultGrass(lv);
      DefaultBiomeFeatures.addDefaultMushrooms(lv);
      DefaultBiomeFeatures.addDefaultVegetation(lv);
      DefaultBiomeFeatures.addSprings(lv);
      return lv;
   }

   public static Biome createColdOcean(boolean deep) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addOceanMobs(lv, 3, 4, 15);
      lv.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 15, 1, 5));
      boolean bl2 = !deep;
      GenerationSettings.Builder lv2 = createOceanGenerationSettings(ConfiguredSurfaceBuilders.GRASS, deep, false, bl2);
      lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ConfiguredFeatures.SEAGRASS_DEEP_COLD : ConfiguredFeatures.SEAGRASS_COLD);
      DefaultBiomeFeatures.addSeagrassOnStone(lv2);
      DefaultBiomeFeatures.addKelp(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return createOcean(lv, 4020182, 329011, deep, lv2);
   }

   public static Biome createNormalOcean(boolean deep) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addOceanMobs(lv, 1, 4, 10);
      lv.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 1, 1, 2));
      GenerationSettings.Builder lv2 = createOceanGenerationSettings(ConfiguredSurfaceBuilders.GRASS, deep, false, true);
      lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ConfiguredFeatures.SEAGRASS_DEEP : ConfiguredFeatures.SEAGRASS_NORMAL);
      DefaultBiomeFeatures.addSeagrassOnStone(lv2);
      DefaultBiomeFeatures.addKelp(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return createOcean(lv, 4159204, 329011, deep, lv2);
   }

   public static Biome createLukewarmOcean(boolean deep) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      if (deep) {
         DefaultBiomeFeatures.addOceanMobs(lv, 8, 4, 8);
      } else {
         DefaultBiomeFeatures.addOceanMobs(lv, 10, 2, 15);
      }

      lv.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.PUFFERFISH, 5, 1, 3))
         .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8))
         .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 2, 1, 2));
      GenerationSettings.Builder lv2 = createOceanGenerationSettings(ConfiguredSurfaceBuilders.OCEAN_SAND, deep, true, false);
      lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ConfiguredFeatures.SEAGRASS_DEEP_WARM : ConfiguredFeatures.SEAGRASS_WARM);
      if (deep) {
         DefaultBiomeFeatures.addSeagrassOnStone(lv2);
      }

      DefaultBiomeFeatures.addLessKelp(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return createOcean(lv, 4566514, 267827, deep, lv2);
   }

   public static Biome createWarmOcean() {
      SpawnSettings.Builder lv = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.PUFFERFISH, 15, 1, 3));
      DefaultBiomeFeatures.addWarmOceanMobs(lv, 10, 4);
      GenerationSettings.Builder lv2 = createOceanGenerationSettings(ConfiguredSurfaceBuilders.FULL_SAND, false, true, false)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WARM_OCEAN_VEGETATION)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_WARM)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEA_PICKLE);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return createOcean(lv, 4445678, 270131, false, lv2);
   }

   public static Biome createDeepWarmOcean() {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addWarmOceanMobs(lv, 5, 1);
      lv.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, 5, 1, 1));
      GenerationSettings.Builder lv2 = createOceanGenerationSettings(ConfiguredSurfaceBuilders.FULL_SAND, true, true, false)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_DEEP_WARM);
      DefaultBiomeFeatures.addSeagrassOnStone(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return createOcean(lv, 4445678, 270131, true, lv2);
   }

   public static Biome createFrozenOcean(boolean monument) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder()
         .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 1, 1, 4))
         .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 15, 1, 5))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.POLAR_BEAR, 1, 1, 2));
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      lv.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, 5, 1, 1));
      float f = monument ? 0.5F : 0.0F;
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.FROZEN_OCEAN);
      lv2.structureFeature(ConfiguredStructureFeatures.OCEAN_RUIN_COLD);
      if (monument) {
         lv2.structureFeature(ConfiguredStructureFeatures.MONUMENT);
      }

      DefaultBiomeFeatures.addOceanStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_OCEAN);
      DefaultBiomeFeatures.addOceanCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addIcebergs(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addBlueIce(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addWaterBiomeOakTrees(lv2);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addDefaultGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(monument ? Biome.Precipitation.RAIN : Biome.Precipitation.SNOW)
         .category(Biome.Category.OCEAN)
         .depth(monument ? -1.8F : -1.0F)
         .scale(0.1F)
         .temperature(f)
         .temperatureModifier(Biome.TemperatureModifier.FROZEN)
         .downfall(0.5F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(3750089)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(f))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   private static Biome createForest(float depth, float scale, boolean bl, SpawnSettings.Builder arg) {
      GenerationSettings.Builder lv = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv);
      lv.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv);
      DefaultBiomeFeatures.addDefaultLakes(lv);
      DefaultBiomeFeatures.addDungeons(lv);
      if (bl) {
         lv.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FOREST_FLOWER_VEGETATION_COMMON);
      } else {
         DefaultBiomeFeatures.addForestFlowers(lv);
      }

      DefaultBiomeFeatures.addMineables(lv);
      DefaultBiomeFeatures.addDefaultOres(lv);
      DefaultBiomeFeatures.addDefaultDisks(lv);
      if (bl) {
         lv.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FOREST_FLOWER_TREES);
         lv.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FLOWER_FOREST);
         DefaultBiomeFeatures.addDefaultGrass(lv);
      } else {
         DefaultBiomeFeatures.addForestTrees(lv);
         DefaultBiomeFeatures.addDefaultFlowers(lv);
         DefaultBiomeFeatures.addForestGrass(lv);
      }

      DefaultBiomeFeatures.addDefaultMushrooms(lv);
      DefaultBiomeFeatures.addDefaultVegetation(lv);
      DefaultBiomeFeatures.addSprings(lv);
      DefaultBiomeFeatures.addFrozenTopLayer(lv);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.FOREST)
         .depth(depth)
         .scale(scale)
         .temperature(0.7F)
         .downfall(0.8F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.7F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(arg.build())
         .generationSettings(lv.build())
         .build();
   }

   private static SpawnSettings.Builder createForestSpawnSettings() {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      return lv;
   }

   public static Biome createNormalForest(float depth, float scale) {
      SpawnSettings.Builder lv = createForestSpawnSettings()
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4))
         .playerSpawnFriendly();
      return createForest(depth, scale, false, lv);
   }

   public static Biome createFlowerForest() {
      SpawnSettings.Builder lv = createForestSpawnSettings().spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
      return createForest(0.1F, 0.4F, true, lv);
   }

   public static Biome createTaiga(float depth, float scale, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
      if (!bl && !bl2) {
         lv.playerSpawnFriendly();
      }

      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      float h = bl ? -0.5F : 0.25F;
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      if (bl3) {
         lv2.structureFeature(ConfiguredStructureFeatures.VILLAGE_TAIGA);
         lv2.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      if (bl4) {
         lv2.structureFeature(ConfiguredStructureFeatures.IGLOO);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(bl2 ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addLargeFerns(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addTaigaTrees(lv2);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addTaigaGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      if (bl) {
         DefaultBiomeFeatures.addSweetBerryBushesSnowy(lv2);
      } else {
         DefaultBiomeFeatures.addSweetBerryBushes(lv2);
      }

      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(bl ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
         .category(Biome.Category.TAIGA)
         .depth(depth)
         .scale(scale)
         .temperature(h)
         .downfall(bl ? 0.4F : 0.8F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(bl ? 4020182 : 4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(h))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createDarkForest(float depth, float scale, boolean bl) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      lv2.structureFeature(ConfiguredStructureFeatures.MANSION);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      lv2.feature(
         GenerationStep.Feature.VEGETAL_DECORATION, bl ? ConfiguredFeatures.DARK_FOREST_VEGETATION_RED : ConfiguredFeatures.DARK_FOREST_VEGETATION_BROWN
      );
      DefaultBiomeFeatures.addForestFlowers(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addForestGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.FOREST)
         .depth(depth)
         .scale(scale)
         .temperature(0.7F)
         .downfall(0.8F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.7F))
               .grassColorModifier(BiomeEffects.GrassColorModifier.DARK_FOREST)
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createSwamp(float depth, float scale, boolean bl) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(lv);
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      lv.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP);
      if (!bl) {
         lv2.structureFeature(ConfiguredStructureFeatures.SWAMP_HUT);
      }

      lv2.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_SWAMP);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      if (!bl) {
         DefaultBiomeFeatures.addFossils(lv2);
      }

      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addClay(lv2);
      DefaultBiomeFeatures.addSwampFeatures(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addSwampVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      if (bl) {
         DefaultBiomeFeatures.addFossils(lv2);
      } else {
         lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_SWAMP);
      }

      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.RAIN)
         .category(Biome.Category.SWAMP)
         .depth(depth)
         .scale(scale)
         .temperature(0.8F)
         .downfall(0.9F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(6388580)
               .waterFogColor(2302743)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.8F))
               .foliageColor(6975545)
               .grassColorModifier(BiomeEffects.GrassColorModifier.SWAMP)
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createSnowyTundra(float depth, float scale, boolean bl, boolean bl2) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder().creatureSpawnProbability(0.07F);
      DefaultBiomeFeatures.addSnowyMobs(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder()
         .surfaceBuilder(bl ? ConfiguredSurfaceBuilders.ICE_SPIKES : ConfiguredSurfaceBuilders.GRASS);
      if (!bl && !bl2) {
         lv2.structureFeature(ConfiguredStructureFeatures.VILLAGE_SNOWY).structureFeature(ConfiguredStructureFeatures.IGLOO);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      if (!bl && !bl2) {
         lv2.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      lv2.structureFeature(bl2 ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      if (bl) {
         lv2.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.ICE_SPIKE);
         lv2.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.ICE_PATCH);
      }

      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addSnowySpruceTrees(lv2);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addDefaultGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.SNOW)
         .category(Biome.Category.ICY)
         .depth(depth)
         .scale(scale)
         .temperature(0.0F)
         .downfall(0.5F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.0F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createRiver(float depth, float scale, float temperature, int waterColor, boolean bl) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder()
         .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4))
         .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));
      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      lv.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, bl ? 1 : 100, 1, 1));
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      lv2.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
      lv2.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addWaterBiomeOakTrees(lv2);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addDefaultGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      if (!bl) {
         lv2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);
      }

      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(bl ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
         .category(Biome.Category.RIVER)
         .depth(depth)
         .scale(scale)
         .temperature(temperature)
         .downfall(0.5F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(waterColor)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(temperature))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createBeach(float depth, float scale, float temperature, float downfall, int waterColor, boolean snowy, boolean stony) {
      SpawnSettings.Builder lv = new SpawnSettings.Builder();
      if (!stony && !snowy) {
         lv.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 5, 2, 5));
      }

      DefaultBiomeFeatures.addBatsAndMonsters(lv);
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder()
         .surfaceBuilder(stony ? ConfiguredSurfaceBuilders.STONE : ConfiguredSurfaceBuilders.DESERT);
      if (stony) {
         DefaultBiomeFeatures.addDefaultUndergroundStructures(lv2);
      } else {
         lv2.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
         lv2.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
         lv2.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);
      }

      lv2.structureFeature(stony ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(lv2);
      DefaultBiomeFeatures.addDefaultLakes(lv2);
      DefaultBiomeFeatures.addDungeons(lv2);
      DefaultBiomeFeatures.addMineables(lv2);
      DefaultBiomeFeatures.addDefaultOres(lv2);
      DefaultBiomeFeatures.addDefaultDisks(lv2);
      DefaultBiomeFeatures.addDefaultFlowers(lv2);
      DefaultBiomeFeatures.addDefaultGrass(lv2);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      DefaultBiomeFeatures.addDefaultVegetation(lv2);
      DefaultBiomeFeatures.addSprings(lv2);
      DefaultBiomeFeatures.addFrozenTopLayer(lv2);
      return new Biome.Builder()
         .precipitation(snowy ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
         .category(stony ? Biome.Category.NONE : Biome.Category.BEACH)
         .depth(depth)
         .scale(scale)
         .temperature(temperature)
         .downfall(downfall)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(waterColor)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(temperature))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(lv.build())
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createTheVoid() {
      GenerationSettings.Builder lv = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.NOPE);
      lv.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, ConfiguredFeatures.VOID_START_PLATFORM);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.NONE)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(0.5F)
         .downfall(0.5F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(0.5F))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(SpawnSettings.INSTANCE)
         .generationSettings(lv.build())
         .build();
   }

   public static Biome createNetherWastes() {
      SpawnSettings lv = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 50, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 2, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PIGLIN, 15, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .build();
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.NETHER)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      lv2.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_SOUL_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_NETHER)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RED_MUSHROOM_NETHER)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED);
      DefaultBiomeFeatures.addNetherMineables(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(3344392)
               .skyColor(getSkyColor(2.0F))
               .loopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
               .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
               .additionsSound(new BiomeAdditionsSound(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111))
               .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_NETHER_WASTES))
               .build()
         )
         .spawnSettings(lv)
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createSoulSandValley() {
      double d = 0.7;
      double e = 0.15;
      SpawnSettings lv = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 20, 5, 5))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 50, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .spawnCost(EntityType.SKELETON, 0.7, 0.15)
         .spawnCost(EntityType.GHAST, 0.7, 0.15)
         .spawnCost(EntityType.ENDERMAN, 0.7, 0.15)
         .spawnCost(EntityType.STRIDER, 0.7, 0.15)
         .build();
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.SOUL_SAND_VALLEY)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .structureFeature(ConfiguredStructureFeatures.NETHER_FOSSIL)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA)
         .feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ConfiguredFeatures.BASALT_PILLAR)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_CRIMSON_ROOTS)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_SOUL_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_SOUL_SAND);
      DefaultBiomeFeatures.addNetherMineables(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(1787717)
               .skyColor(getSkyColor(2.0F))
               .particleConfig(new BiomeParticleConfig(ParticleTypes.ASH, 0.00625F))
               .loopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
               .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0))
               .additionsSound(new BiomeAdditionsSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111))
               .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_SOUL_SAND_VALLEY))
               .build()
         )
         .spawnSettings(lv)
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createBasaltDeltas() {
      SpawnSettings lv = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 40, 1, 1))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 100, 2, 5))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .build();
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.BASALT_DELTAS)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.DELTA)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA_DOUBLE)
         .feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.SMALL_BASALT_COLUMNS)
         .feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.LARGE_BASALT_COLUMNS)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.BASALT_BLOBS)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.BLACKSTONE_BLOBS)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_DELTA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_SOUL_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_NETHER)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RED_MUSHROOM_NETHER)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED_DOUBLE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_GOLD_DELTAS)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_QUARTZ_DELTAS);
      DefaultBiomeFeatures.addAncientDebris(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(4341314)
               .fogColor(6840176)
               .skyColor(getSkyColor(2.0F))
               .particleConfig(new BiomeParticleConfig(ParticleTypes.WHITE_ASH, 0.118093334F))
               .loopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
               .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0))
               .additionsSound(new BiomeAdditionsSound(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111))
               .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_BASALT_DELTAS))
               .build()
         )
         .spawnSettings(lv)
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createCrimsonForest() {
      SpawnSettings lv = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.HOGLIN, 9, 3, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PIGLIN, 5, 3, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .build();
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.CRIMSON_FOREST)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      lv2.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WEEPING_VINES)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CRIMSON_FUNGI)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CRIMSON_FOREST_VEGETATION);
      DefaultBiomeFeatures.addNetherMineables(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(3343107)
               .skyColor(getSkyColor(2.0F))
               .particleConfig(new BiomeParticleConfig(ParticleTypes.CRIMSON_SPORE, 0.025F))
               .loopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
               .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0))
               .additionsSound(new BiomeAdditionsSound(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111))
               .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_CRIMSON_FOREST))
               .build()
         )
         .spawnSettings(lv)
         .generationSettings(lv2.build())
         .build();
   }

   public static Biome createWarpedForest() {
      SpawnSettings lv = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .spawnCost(EntityType.ENDERMAN, 1.0, 0.12)
         .build();
      GenerationSettings.Builder lv2 = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.WARPED_FOREST)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
      DefaultBiomeFeatures.addDefaultMushrooms(lv2);
      lv2.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_SOUL_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WARPED_FUNGI)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WARPED_FOREST_VEGETATION)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.NETHER_SPROUTS)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TWISTING_VINES);
      DefaultBiomeFeatures.addNetherMineables(lv2);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(1705242)
               .skyColor(getSkyColor(2.0F))
               .particleConfig(new BiomeParticleConfig(ParticleTypes.WARPED_SPORE, 0.01428F))
               .loopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
               .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0))
               .additionsSound(new BiomeAdditionsSound(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111))
               .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_WARPED_FOREST))
               .build()
         )
         .spawnSettings(lv)
         .generationSettings(lv2.build())
         .build();
   }
}
