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
      float var1 = temperature / 3.0F;
      var1 = MathHelper.clamp(var1, -1.0F, 1.0F);
      return MathHelper.hsvToRgb(0.62222224F - var1 * 0.05F, 0.5F + var1 * 0.1F, 1.0F);
   }

   public static Biome createGiantTreeTaiga(float depth, float scale, float temperature, boolean spruce) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowman);
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4));
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
      if (spruce) {
         DefaultBiomeFeatures.addBatsAndMonsters(_snowman);
      } else {
         DefaultBiomeFeatures.addBats(_snowman);
         DefaultBiomeFeatures.addMonsters(_snowman, 100, 25, 100);
      }

      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GIANT_TREE_TAIGA);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanx);
      _snowmanx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanx);
      DefaultBiomeFeatures.addDungeons(_snowmanx);
      DefaultBiomeFeatures.addMossyRocks(_snowmanx);
      DefaultBiomeFeatures.addLargeFerns(_snowmanx);
      DefaultBiomeFeatures.addMineables(_snowmanx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanx);
      _snowmanx.feature(GenerationStep.Feature.VEGETAL_DECORATION, spruce ? ConfiguredFeatures.TREES_GIANT_SPRUCE : ConfiguredFeatures.TREES_GIANT);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanx);
      DefaultBiomeFeatures.addGiantTaigaGrass(_snowmanx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanx);
      DefaultBiomeFeatures.addSprings(_snowmanx);
      DefaultBiomeFeatures.addSweetBerryBushes(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanx.build())
         .build();
   }

   public static Biome createBirchForest(float depth, float scale, boolean tallTrees) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowman);
      DefaultBiomeFeatures.addBatsAndMonsters(_snowman);
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanx);
      _snowmanx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanx);
      DefaultBiomeFeatures.addDungeons(_snowmanx);
      DefaultBiomeFeatures.addForestFlowers(_snowmanx);
      DefaultBiomeFeatures.addMineables(_snowmanx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanx);
      if (tallTrees) {
         DefaultBiomeFeatures.addTallBirchTrees(_snowmanx);
      } else {
         DefaultBiomeFeatures.addBirchTrees(_snowmanx);
      }

      DefaultBiomeFeatures.addDefaultFlowers(_snowmanx);
      DefaultBiomeFeatures.addForestGrass(_snowmanx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanx);
      DefaultBiomeFeatures.addSprings(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanx.build())
         .build();
   }

   public static Biome createJungle() {
      return createJungle(0.1F, 0.2F, 40, 2, 3);
   }

   public static Biome createJungleEdge() {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(_snowman);
      return createJungleFeatures(0.1F, 0.2F, 0.8F, false, true, false, _snowman);
   }

   public static Biome createModifiedJungleEdge() {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(_snowman);
      return createJungleFeatures(0.2F, 0.4F, 0.8F, false, true, true, _snowman);
   }

   public static Biome createModifiedJungle() {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(_snowman);
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 10, 1, 1))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 1));
      return createJungleFeatures(0.2F, 0.4F, 0.9F, false, false, true, _snowman);
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
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(_snowman);
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, parrotWeight, 1, parrotMaxGroupSize))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, ocelotMaxGroupSize))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 1, 1, 2));
      _snowman.playerSpawnFriendly();
      return createJungleFeatures(depth, scale, 0.9F, false, false, false, _snowman);
   }

   private static Biome createBambooJungle(float depth, float scale, int parrotWeight, int parrotMaxGroupSize) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addJungleMobs(_snowman);
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, parrotWeight, 1, parrotMaxGroupSize))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 80, 1, 2))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 1));
      return createJungleFeatures(depth, scale, 0.9F, true, false, false, _snowman);
   }

   private static Biome createJungleFeatures(float depth, float scale, float downfall, boolean _snowman, boolean _snowman, boolean _snowman, SpawnSettings.Builder _snowman) {
      GenerationSettings.Builder _snowmanxxxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      if (!_snowman && !_snowman) {
         _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.JUNGLE_PYRAMID);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxxxx);
      _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_JUNGLE);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxxxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxxxx);
      DefaultBiomeFeatures.addMineables(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxxxx);
      if (_snowman) {
         DefaultBiomeFeatures.addBambooJungleTrees(_snowmanxxxx);
      } else {
         if (!_snowman && !_snowman) {
            DefaultBiomeFeatures.addBamboo(_snowmanxxxx);
         }

         if (_snowman) {
            DefaultBiomeFeatures.addJungleEdgeTrees(_snowmanxxxx);
         } else {
            DefaultBiomeFeatures.addJungleTrees(_snowmanxxxx);
         }
      }

      DefaultBiomeFeatures.addExtraDefaultFlowers(_snowmanxxxx);
      DefaultBiomeFeatures.addJungleGrass(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxxxx);
      DefaultBiomeFeatures.addSprings(_snowmanxxxx);
      DefaultBiomeFeatures.addJungleVegetation(_snowmanxxxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxxxx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanxxxx.build())
         .build();
   }

   public static Biome createMountains(float depth, float scale, ConfiguredSurfaceBuilder<TernarySurfaceConfig> surfaceBuilder, boolean extraTrees) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowman);
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 5, 4, 6));
      DefaultBiomeFeatures.addBatsAndMonsters(_snowman);
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder().surfaceBuilder(surfaceBuilder);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanx);
      _snowmanx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN);
      DefaultBiomeFeatures.addLandCarvers(_snowmanx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanx);
      DefaultBiomeFeatures.addDungeons(_snowmanx);
      DefaultBiomeFeatures.addMineables(_snowmanx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanx);
      if (extraTrees) {
         DefaultBiomeFeatures.addExtraMountainTrees(_snowmanx);
      } else {
         DefaultBiomeFeatures.addMountainTrees(_snowmanx);
      }

      DefaultBiomeFeatures.addDefaultFlowers(_snowmanx);
      DefaultBiomeFeatures.addDefaultGrass(_snowmanx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanx);
      DefaultBiomeFeatures.addSprings(_snowmanx);
      DefaultBiomeFeatures.addEmeraldOre(_snowmanx);
      DefaultBiomeFeatures.addInfestedStone(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanx.build())
         .build();
   }

   public static Biome createDesert(float depth, float scale, boolean _snowman, boolean _snowman, boolean _snowman) {
      SpawnSettings.Builder _snowmanxxx = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addDesertMobs(_snowmanxxx);
      GenerationSettings.Builder _snowmanxxxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.DESERT);
      if (_snowman) {
         _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.VILLAGE_DESERT);
         _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      if (_snowman) {
         _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.DESERT_PYRAMID);
      }

      if (_snowman) {
         DefaultBiomeFeatures.addFossils(_snowmanxxxx);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxxxx);
      _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_DESERT);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxxxx);
      DefaultBiomeFeatures.addDesertLakes(_snowmanxxxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxxxx);
      DefaultBiomeFeatures.addMineables(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultGrass(_snowmanxxxx);
      DefaultBiomeFeatures.addDesertDeadBushes(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxxxx);
      DefaultBiomeFeatures.addDesertVegetation(_snowmanxxxx);
      DefaultBiomeFeatures.addSprings(_snowmanxxxx);
      DefaultBiomeFeatures.addDesertFeatures(_snowmanxxxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxxxx);
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
         .spawnSettings(_snowmanxxx.build())
         .generationSettings(_snowmanxxxx.build())
         .build();
   }

   public static Biome createPlains(boolean _snowman) {
      SpawnSettings.Builder _snowmanx = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addPlainsMobs(_snowmanx);
      if (!_snowman) {
         _snowmanx.playerSpawnFriendly();
      }

      GenerationSettings.Builder _snowmanxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      if (!_snowman) {
         _snowmanxx.structureFeature(ConfiguredStructureFeatures.VILLAGE_PLAINS).structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxx);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxx);
      DefaultBiomeFeatures.addPlainsTallGrass(_snowmanxx);
      if (_snowman) {
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUNFLOWER);
      }

      DefaultBiomeFeatures.addMineables(_snowmanxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxx);
      DefaultBiomeFeatures.addPlainsFeatures(_snowmanxx);
      if (_snowman) {
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUGAR_CANE);
      }

      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxx);
      if (_snowman) {
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_PUMPKIN);
      } else {
         DefaultBiomeFeatures.addDefaultVegetation(_snowmanxx);
      }

      DefaultBiomeFeatures.addSprings(_snowmanxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxx);
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
         .spawnSettings(_snowmanx.build())
         .generationSettings(_snowmanxx.build())
         .build();
   }

   private static Biome composeEndSpawnSettings(GenerationSettings.Builder _snowman) {
      SpawnSettings.Builder _snowmanx = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addEndMobs(_snowmanx);
      return new Biome.Builder()
         .precipitation(Biome.Precipitation.NONE)
         .category(Biome.Category.THEEND)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(0.5F)
         .downfall(0.5F)
         .effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(10518688).skyColor(0).moodSound(BiomeMoodSound.CAVE).build())
         .spawnSettings(_snowmanx.build())
         .generationSettings(_snowman.build())
         .build();
   }

   public static Biome createEndBarrens() {
      GenerationSettings.Builder _snowman = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.END);
      return composeEndSpawnSettings(_snowman);
   }

   public static Biome createTheEnd() {
      GenerationSettings.Builder _snowman = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.END_SPIKE);
      return composeEndSpawnSettings(_snowman);
   }

   public static Biome createEndMidlands() {
      GenerationSettings.Builder _snowman = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .structureFeature(ConfiguredStructureFeatures.END_CITY);
      return composeEndSpawnSettings(_snowman);
   }

   public static Biome createEndHighlands() {
      GenerationSettings.Builder _snowman = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .structureFeature(ConfiguredStructureFeatures.END_CITY)
         .feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.END_GATEWAY)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CHORUS_PLANT);
      return composeEndSpawnSettings(_snowman);
   }

   public static Biome createSmallEndIslands() {
      GenerationSettings.Builder _snowman = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.END)
         .feature(GenerationStep.Feature.RAW_GENERATION, ConfiguredFeatures.END_ISLAND_DECORATED);
      return composeEndSpawnSettings(_snowman);
   }

   public static Biome createMushroomFields(float depth, float scale) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addMushroomMobs(_snowman);
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.MYCELIUM);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanx);
      _snowmanx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanx);
      DefaultBiomeFeatures.addDungeons(_snowmanx);
      DefaultBiomeFeatures.addMineables(_snowmanx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanx);
      DefaultBiomeFeatures.addMushroomFieldsFeatures(_snowmanx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanx);
      DefaultBiomeFeatures.addSprings(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanx.build())
         .build();
   }

   private static Biome composeSavannaGenerationSettings(float depth, float scale, float temperature, boolean _snowman, boolean _snowman, SpawnSettings.Builder _snowman) {
      GenerationSettings.Builder _snowmanxxx = new GenerationSettings.Builder()
         .surfaceBuilder(_snowman ? ConfiguredSurfaceBuilders.SHATTERED_SAVANNA : ConfiguredSurfaceBuilders.GRASS);
      if (!_snowman && !_snowman) {
         _snowmanxxx.structureFeature(ConfiguredStructureFeatures.VILLAGE_SAVANNA).structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxxx);
      _snowmanxxx.structureFeature(_snowman ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxxx);
      if (!_snowman) {
         DefaultBiomeFeatures.addSavannaTallGrass(_snowmanxxx);
      }

      DefaultBiomeFeatures.addMineables(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxxx);
      if (_snowman) {
         DefaultBiomeFeatures.addExtraSavannaTrees(_snowmanxxx);
         DefaultBiomeFeatures.addDefaultFlowers(_snowmanxxx);
         DefaultBiomeFeatures.addShatteredSavannaGrass(_snowmanxxx);
      } else {
         DefaultBiomeFeatures.addSavannaTrees(_snowmanxxx);
         DefaultBiomeFeatures.addExtraDefaultFlowers(_snowmanxxx);
         DefaultBiomeFeatures.addSavannaGrass(_snowmanxxx);
      }

      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxxx);
      DefaultBiomeFeatures.addSprings(_snowmanxxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxxx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanxxx.build())
         .build();
   }

   public static Biome createSavanna(float depth, float scale, float temperature, boolean _snowman, boolean _snowman) {
      SpawnSettings.Builder _snowmanxx = createSavannaSpawnSettings();
      return composeSavannaGenerationSettings(depth, scale, temperature, _snowman, _snowman, _snowmanxx);
   }

   private static SpawnSettings.Builder createSavannaSpawnSettings() {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowman);
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.HORSE, 1, 2, 6))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.DONKEY, 1, 1, 1));
      DefaultBiomeFeatures.addBatsAndMonsters(_snowman);
      return _snowman;
   }

   public static Biome createSavannaPlateau() {
      SpawnSettings.Builder _snowman = createSavannaSpawnSettings();
      _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 8, 4, 4));
      return composeSavannaGenerationSettings(1.5F, 0.025F, 1.0F, true, false, _snowman);
   }

   private static Biome createBadlands(ConfiguredSurfaceBuilder<TernarySurfaceConfig> _snowman, float depth, float scale, boolean _snowman, boolean _snowman) {
      SpawnSettings.Builder _snowmanxxx = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addBatsAndMonsters(_snowmanxxx);
      GenerationSettings.Builder _snowmanxxxx = new GenerationSettings.Builder().surfaceBuilder(_snowman);
      DefaultBiomeFeatures.addBadlandsUndergroundStructures(_snowmanxxxx);
      _snowmanxxxx.structureFeature(_snowman ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxxxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxxxx);
      DefaultBiomeFeatures.addMineables(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxxxx);
      DefaultBiomeFeatures.addExtraGoldOre(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxxxx);
      if (_snowman) {
         DefaultBiomeFeatures.addBadlandsPlateauTrees(_snowmanxxxx);
      }

      DefaultBiomeFeatures.addBadlandsGrass(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxxxx);
      DefaultBiomeFeatures.addBadlandsVegetation(_snowmanxxxx);
      DefaultBiomeFeatures.addSprings(_snowmanxxxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxxxx);
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
         .spawnSettings(_snowmanxxx.build())
         .generationSettings(_snowmanxxxx.build())
         .build();
   }

   public static Biome createNormalBadlands(float depth, float scale, boolean _snowman) {
      return createBadlands(ConfiguredSurfaceBuilders.BADLANDS, depth, scale, _snowman, false);
   }

   public static Biome createWoodedBadlandsPlateau(float depth, float scale) {
      return createBadlands(ConfiguredSurfaceBuilders.WOODED_BADLANDS, depth, scale, true, true);
   }

   public static Biome createErodedBadlands() {
      return createBadlands(ConfiguredSurfaceBuilders.ERODED_BADLANDS, 0.1F, 0.2F, true, false);
   }

   private static Biome createOcean(SpawnSettings.Builder _snowman, int waterColor, int waterFogColor, boolean deep, GenerationSettings.Builder _snowman) {
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowman.build())
         .build();
   }

   private static GenerationSettings.Builder createOceanGenerationSettings(ConfiguredSurfaceBuilder<TernarySurfaceConfig> _snowman, boolean _snowman, boolean _snowman, boolean _snowman) {
      GenerationSettings.Builder _snowmanxxxx = new GenerationSettings.Builder().surfaceBuilder(_snowman);
      ConfiguredStructureFeature<?, ?> _snowmanxxxxx = _snowman ? ConfiguredStructureFeatures.OCEAN_RUIN_WARM : ConfiguredStructureFeatures.OCEAN_RUIN_COLD;
      if (_snowman) {
         if (_snowman) {
            _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.MONUMENT);
         }

         DefaultBiomeFeatures.addOceanStructures(_snowmanxxxx);
         _snowmanxxxx.structureFeature(_snowmanxxxxx);
      } else {
         _snowmanxxxx.structureFeature(_snowmanxxxxx);
         if (_snowman) {
            _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.MONUMENT);
         }

         DefaultBiomeFeatures.addOceanStructures(_snowmanxxxx);
      }

      _snowmanxxxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_OCEAN);
      DefaultBiomeFeatures.addOceanCarvers(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxxxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxxxx);
      DefaultBiomeFeatures.addMineables(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxxxx);
      DefaultBiomeFeatures.addWaterBiomeOakTrees(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultGrass(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxxxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxxxx);
      DefaultBiomeFeatures.addSprings(_snowmanxxxx);
      return _snowmanxxxx;
   }

   public static Biome createColdOcean(boolean deep) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addOceanMobs(_snowman, 3, 4, 15);
      _snowman.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 15, 1, 5));
      boolean _snowmanx = !deep;
      GenerationSettings.Builder _snowmanxx = createOceanGenerationSettings(ConfiguredSurfaceBuilders.GRASS, deep, false, _snowmanx);
      _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ConfiguredFeatures.SEAGRASS_DEEP_COLD : ConfiguredFeatures.SEAGRASS_COLD);
      DefaultBiomeFeatures.addSeagrassOnStone(_snowmanxx);
      DefaultBiomeFeatures.addKelp(_snowmanxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxx);
      return createOcean(_snowman, 4020182, 329011, deep, _snowmanxx);
   }

   public static Biome createNormalOcean(boolean deep) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addOceanMobs(_snowman, 1, 4, 10);
      _snowman.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 1, 1, 2));
      GenerationSettings.Builder _snowmanx = createOceanGenerationSettings(ConfiguredSurfaceBuilders.GRASS, deep, false, true);
      _snowmanx.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ConfiguredFeatures.SEAGRASS_DEEP : ConfiguredFeatures.SEAGRASS_NORMAL);
      DefaultBiomeFeatures.addSeagrassOnStone(_snowmanx);
      DefaultBiomeFeatures.addKelp(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
      return createOcean(_snowman, 4159204, 329011, deep, _snowmanx);
   }

   public static Biome createLukewarmOcean(boolean deep) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      if (deep) {
         DefaultBiomeFeatures.addOceanMobs(_snowman, 8, 4, 8);
      } else {
         DefaultBiomeFeatures.addOceanMobs(_snowman, 10, 2, 15);
      }

      _snowman.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.PUFFERFISH, 5, 1, 3))
         .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8))
         .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 2, 1, 2));
      GenerationSettings.Builder _snowmanx = createOceanGenerationSettings(ConfiguredSurfaceBuilders.OCEAN_SAND, deep, true, false);
      _snowmanx.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ConfiguredFeatures.SEAGRASS_DEEP_WARM : ConfiguredFeatures.SEAGRASS_WARM);
      if (deep) {
         DefaultBiomeFeatures.addSeagrassOnStone(_snowmanx);
      }

      DefaultBiomeFeatures.addLessKelp(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
      return createOcean(_snowman, 4566514, 267827, deep, _snowmanx);
   }

   public static Biome createWarmOcean() {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder().spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.PUFFERFISH, 15, 1, 3));
      DefaultBiomeFeatures.addWarmOceanMobs(_snowman, 10, 4);
      GenerationSettings.Builder _snowmanx = createOceanGenerationSettings(ConfiguredSurfaceBuilders.FULL_SAND, false, true, false)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WARM_OCEAN_VEGETATION)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_WARM)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEA_PICKLE);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
      return createOcean(_snowman, 4445678, 270131, false, _snowmanx);
   }

   public static Biome createDeepWarmOcean() {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addWarmOceanMobs(_snowman, 5, 1);
      _snowman.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, 5, 1, 1));
      GenerationSettings.Builder _snowmanx = createOceanGenerationSettings(ConfiguredSurfaceBuilders.FULL_SAND, true, true, false)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_DEEP_WARM);
      DefaultBiomeFeatures.addSeagrassOnStone(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
      return createOcean(_snowman, 4445678, 270131, true, _snowmanx);
   }

   public static Biome createFrozenOcean(boolean monument) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder()
         .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 1, 1, 4))
         .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 15, 1, 5))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.POLAR_BEAR, 1, 1, 2));
      DefaultBiomeFeatures.addBatsAndMonsters(_snowman);
      _snowman.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, 5, 1, 1));
      float _snowmanx = monument ? 0.5F : 0.0F;
      GenerationSettings.Builder _snowmanxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.FROZEN_OCEAN);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.OCEAN_RUIN_COLD);
      if (monument) {
         _snowmanxx.structureFeature(ConfiguredStructureFeatures.MONUMENT);
      }

      DefaultBiomeFeatures.addOceanStructures(_snowmanxx);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_OCEAN);
      DefaultBiomeFeatures.addOceanCarvers(_snowmanxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxx);
      DefaultBiomeFeatures.addIcebergs(_snowmanxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxx);
      DefaultBiomeFeatures.addBlueIce(_snowmanxx);
      DefaultBiomeFeatures.addMineables(_snowmanxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxx);
      DefaultBiomeFeatures.addWaterBiomeOakTrees(_snowmanxx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanxx);
      DefaultBiomeFeatures.addDefaultGrass(_snowmanxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxx);
      DefaultBiomeFeatures.addSprings(_snowmanxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxx);
      return new Biome.Builder()
         .precipitation(monument ? Biome.Precipitation.RAIN : Biome.Precipitation.SNOW)
         .category(Biome.Category.OCEAN)
         .depth(monument ? -1.8F : -1.0F)
         .scale(0.1F)
         .temperature(_snowmanx)
         .temperatureModifier(Biome.TemperatureModifier.FROZEN)
         .downfall(0.5F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(3750089)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(_snowmanx))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanxx.build())
         .build();
   }

   private static Biome createForest(float depth, float scale, boolean _snowman, SpawnSettings.Builder _snowman) {
      GenerationSettings.Builder _snowmanxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxx);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxx);
      if (_snowman) {
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FOREST_FLOWER_VEGETATION_COMMON);
      } else {
         DefaultBiomeFeatures.addForestFlowers(_snowmanxx);
      }

      DefaultBiomeFeatures.addMineables(_snowmanxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxx);
      if (_snowman) {
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FOREST_FLOWER_TREES);
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FLOWER_FOREST);
         DefaultBiomeFeatures.addDefaultGrass(_snowmanxx);
      } else {
         DefaultBiomeFeatures.addForestTrees(_snowmanxx);
         DefaultBiomeFeatures.addDefaultFlowers(_snowmanxx);
         DefaultBiomeFeatures.addForestGrass(_snowmanxx);
      }

      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxx);
      DefaultBiomeFeatures.addSprings(_snowmanxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanxx.build())
         .build();
   }

   private static SpawnSettings.Builder createForestSpawnSettings() {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowman);
      DefaultBiomeFeatures.addBatsAndMonsters(_snowman);
      return _snowman;
   }

   public static Biome createNormalForest(float depth, float scale) {
      SpawnSettings.Builder _snowman = createForestSpawnSettings()
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4))
         .playerSpawnFriendly();
      return createForest(depth, scale, false, _snowman);
   }

   public static Biome createFlowerForest() {
      SpawnSettings.Builder _snowman = createForestSpawnSettings().spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
      return createForest(0.1F, 0.4F, true, _snowman);
   }

   public static Biome createTaiga(float depth, float scale, boolean _snowman, boolean _snowman, boolean _snowman, boolean _snowman) {
      SpawnSettings.Builder _snowmanxxxx = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowmanxxxx);
      _snowmanxxxx.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
      if (!_snowman && !_snowman) {
         _snowmanxxxx.playerSpawnFriendly();
      }

      DefaultBiomeFeatures.addBatsAndMonsters(_snowmanxxxx);
      float _snowmanxxxxx = _snowman ? -0.5F : 0.25F;
      GenerationSettings.Builder _snowmanxxxxxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      if (_snowman) {
         _snowmanxxxxxx.structureFeature(ConfiguredStructureFeatures.VILLAGE_TAIGA);
         _snowmanxxxxxx.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      if (_snowman) {
         _snowmanxxxxxx.structureFeature(ConfiguredStructureFeatures.IGLOO);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxxxxxx);
      _snowmanxxxxxx.structureFeature(_snowman ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxxxxxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxxxxxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxxxxxx);
      DefaultBiomeFeatures.addLargeFerns(_snowmanxxxxxx);
      DefaultBiomeFeatures.addMineables(_snowmanxxxxxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxxxxxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxxxxxx);
      DefaultBiomeFeatures.addTaigaTrees(_snowmanxxxxxx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanxxxxxx);
      DefaultBiomeFeatures.addTaigaGrass(_snowmanxxxxxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxxxxxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxxxxxx);
      DefaultBiomeFeatures.addSprings(_snowmanxxxxxx);
      if (_snowman) {
         DefaultBiomeFeatures.addSweetBerryBushesSnowy(_snowmanxxxxxx);
      } else {
         DefaultBiomeFeatures.addSweetBerryBushes(_snowmanxxxxxx);
      }

      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxxxxxx);
      return new Biome.Builder()
         .precipitation(_snowman ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
         .category(Biome.Category.TAIGA)
         .depth(depth)
         .scale(scale)
         .temperature(_snowmanxxxxx)
         .downfall(_snowman ? 0.4F : 0.8F)
         .effects(
            new BiomeEffects.Builder()
               .waterColor(_snowman ? 4020182 : 4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(getSkyColor(_snowmanxxxxx))
               .moodSound(BiomeMoodSound.CAVE)
               .build()
         )
         .spawnSettings(_snowmanxxxx.build())
         .generationSettings(_snowmanxxxxxx.build())
         .build();
   }

   public static Biome createDarkForest(float depth, float scale, boolean _snowman) {
      SpawnSettings.Builder _snowmanx = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowmanx);
      DefaultBiomeFeatures.addBatsAndMonsters(_snowmanx);
      GenerationSettings.Builder _snowmanxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.MANSION);
      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxx);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxx);
      _snowmanxx.feature(
         GenerationStep.Feature.VEGETAL_DECORATION, _snowman ? ConfiguredFeatures.DARK_FOREST_VEGETATION_RED : ConfiguredFeatures.DARK_FOREST_VEGETATION_BROWN
      );
      DefaultBiomeFeatures.addForestFlowers(_snowmanxx);
      DefaultBiomeFeatures.addMineables(_snowmanxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanxx);
      DefaultBiomeFeatures.addForestGrass(_snowmanxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxx);
      DefaultBiomeFeatures.addSprings(_snowmanxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxx);
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
         .spawnSettings(_snowmanx.build())
         .generationSettings(_snowmanxx.build())
         .build();
   }

   public static Biome createSwamp(float depth, float scale, boolean _snowman) {
      SpawnSettings.Builder _snowmanx = new SpawnSettings.Builder();
      DefaultBiomeFeatures.addFarmAnimals(_snowmanx);
      DefaultBiomeFeatures.addBatsAndMonsters(_snowmanx);
      _snowmanx.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
      GenerationSettings.Builder _snowmanxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP);
      if (!_snowman) {
         _snowmanxx.structureFeature(ConfiguredStructureFeatures.SWAMP_HUT);
      }

      _snowmanxx.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_SWAMP);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxx);
      if (!_snowman) {
         DefaultBiomeFeatures.addFossils(_snowmanxx);
      }

      DefaultBiomeFeatures.addDefaultLakes(_snowmanxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxx);
      DefaultBiomeFeatures.addMineables(_snowmanxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxx);
      DefaultBiomeFeatures.addClay(_snowmanxx);
      DefaultBiomeFeatures.addSwampFeatures(_snowmanxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxx);
      DefaultBiomeFeatures.addSwampVegetation(_snowmanxx);
      DefaultBiomeFeatures.addSprings(_snowmanxx);
      if (_snowman) {
         DefaultBiomeFeatures.addFossils(_snowmanxx);
      } else {
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_SWAMP);
      }

      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxx);
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
         .spawnSettings(_snowmanx.build())
         .generationSettings(_snowmanxx.build())
         .build();
   }

   public static Biome createSnowyTundra(float depth, float scale, boolean _snowman, boolean _snowman) {
      SpawnSettings.Builder _snowmanxx = new SpawnSettings.Builder().creatureSpawnProbability(0.07F);
      DefaultBiomeFeatures.addSnowyMobs(_snowmanxx);
      GenerationSettings.Builder _snowmanxxx = new GenerationSettings.Builder()
         .surfaceBuilder(_snowman ? ConfiguredSurfaceBuilders.ICE_SPIKES : ConfiguredSurfaceBuilders.GRASS);
      if (!_snowman && !_snowman) {
         _snowmanxxx.structureFeature(ConfiguredStructureFeatures.VILLAGE_SNOWY).structureFeature(ConfiguredStructureFeatures.IGLOO);
      }

      DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanxxx);
      if (!_snowman && !_snowman) {
         _snowmanxxx.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
      }

      _snowmanxxx.structureFeature(_snowman ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxxx);
      if (_snowman) {
         _snowmanxxx.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.ICE_SPIKE);
         _snowmanxxx.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.ICE_PATCH);
      }

      DefaultBiomeFeatures.addMineables(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxxx);
      DefaultBiomeFeatures.addSnowySpruceTrees(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultGrass(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxxx);
      DefaultBiomeFeatures.addSprings(_snowmanxxx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxxx);
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
         .spawnSettings(_snowmanxx.build())
         .generationSettings(_snowmanxxx.build())
         .build();
   }

   public static Biome createRiver(float depth, float scale, float temperature, int waterColor, boolean _snowman) {
      SpawnSettings.Builder _snowmanx = new SpawnSettings.Builder()
         .spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 2, 1, 4))
         .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 5, 1, 5));
      DefaultBiomeFeatures.addBatsAndMonsters(_snowmanx);
      _snowmanx.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, _snowman ? 1 : 100, 1, 1));
      GenerationSettings.Builder _snowmanxx = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
      _snowmanxx.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanxx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanxx);
      DefaultBiomeFeatures.addDungeons(_snowmanxx);
      DefaultBiomeFeatures.addMineables(_snowmanxx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanxx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanxx);
      DefaultBiomeFeatures.addWaterBiomeOakTrees(_snowmanxx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanxx);
      DefaultBiomeFeatures.addDefaultGrass(_snowmanxx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanxx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanxx);
      DefaultBiomeFeatures.addSprings(_snowmanxx);
      if (!_snowman) {
         _snowmanxx.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_RIVER);
      }

      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanxx);
      return new Biome.Builder()
         .precipitation(_snowman ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
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
         .spawnSettings(_snowmanx.build())
         .generationSettings(_snowmanxx.build())
         .build();
   }

   public static Biome createBeach(float depth, float scale, float temperature, float downfall, int waterColor, boolean snowy, boolean stony) {
      SpawnSettings.Builder _snowman = new SpawnSettings.Builder();
      if (!stony && !snowy) {
         _snowman.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.TURTLE, 5, 2, 5));
      }

      DefaultBiomeFeatures.addBatsAndMonsters(_snowman);
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder()
         .surfaceBuilder(stony ? ConfiguredSurfaceBuilders.STONE : ConfiguredSurfaceBuilders.DESERT);
      if (stony) {
         DefaultBiomeFeatures.addDefaultUndergroundStructures(_snowmanx);
      } else {
         _snowmanx.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
         _snowmanx.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
         _snowmanx.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);
      }

      _snowmanx.structureFeature(stony ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);
      DefaultBiomeFeatures.addLandCarvers(_snowmanx);
      DefaultBiomeFeatures.addDefaultLakes(_snowmanx);
      DefaultBiomeFeatures.addDungeons(_snowmanx);
      DefaultBiomeFeatures.addMineables(_snowmanx);
      DefaultBiomeFeatures.addDefaultOres(_snowmanx);
      DefaultBiomeFeatures.addDefaultDisks(_snowmanx);
      DefaultBiomeFeatures.addDefaultFlowers(_snowmanx);
      DefaultBiomeFeatures.addDefaultGrass(_snowmanx);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      DefaultBiomeFeatures.addDefaultVegetation(_snowmanx);
      DefaultBiomeFeatures.addSprings(_snowmanx);
      DefaultBiomeFeatures.addFrozenTopLayer(_snowmanx);
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
         .spawnSettings(_snowman.build())
         .generationSettings(_snowmanx.build())
         .build();
   }

   public static Biome createTheVoid() {
      GenerationSettings.Builder _snowman = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.NOPE);
      _snowman.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, ConfiguredFeatures.VOID_START_PLATFORM);
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
         .generationSettings(_snowman.build())
         .build();
   }

   public static Biome createNetherWastes() {
      SpawnSettings _snowman = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 50, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 2, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PIGLIN, 15, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .build();
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.NETHER)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      _snowmanx.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_SOUL_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_NETHER)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RED_MUSHROOM_NETHER)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED);
      DefaultBiomeFeatures.addNetherMineables(_snowmanx);
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
         .spawnSettings(_snowman)
         .generationSettings(_snowmanx.build())
         .build();
   }

   public static Biome createSoulSandValley() {
      double _snowman = 0.7;
      double _snowmanx = 0.15;
      SpawnSettings _snowmanxx = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 20, 5, 5))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 50, 4, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .spawnCost(EntityType.SKELETON, 0.7, 0.15)
         .spawnCost(EntityType.GHAST, 0.7, 0.15)
         .spawnCost(EntityType.ENDERMAN, 0.7, 0.15)
         .spawnCost(EntityType.STRIDER, 0.7, 0.15)
         .build();
      GenerationSettings.Builder _snowmanxxx = new GenerationSettings.Builder()
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
      DefaultBiomeFeatures.addNetherMineables(_snowmanxxx);
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
         .spawnSettings(_snowmanxx)
         .generationSettings(_snowmanxxx.build())
         .build();
   }

   public static Biome createBasaltDeltas() {
      SpawnSettings _snowman = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 40, 1, 1))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 100, 2, 5))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .build();
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder()
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
      DefaultBiomeFeatures.addAncientDebris(_snowmanx);
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
         .spawnSettings(_snowman)
         .generationSettings(_snowmanx.build())
         .build();
   }

   public static Biome createCrimsonForest() {
      SpawnSettings _snowman = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.HOGLIN, 9, 3, 4))
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PIGLIN, 5, 3, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .build();
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.CRIMSON_FOREST)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      _snowmanx.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
         .feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WEEPING_VINES)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CRIMSON_FUNGI)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.CRIMSON_FOREST_VEGETATION);
      DefaultBiomeFeatures.addNetherMineables(_snowmanx);
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
         .spawnSettings(_snowman)
         .generationSettings(_snowmanx.build())
         .build();
   }

   public static Biome createWarpedForest() {
      SpawnSettings _snowman = new SpawnSettings.Builder()
         .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
         .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
         .spawnCost(EntityType.ENDERMAN, 1.0, 0.12)
         .build();
      GenerationSettings.Builder _snowmanx = new GenerationSettings.Builder()
         .surfaceBuilder(ConfiguredSurfaceBuilders.WARPED_FOREST)
         .structureFeature(ConfiguredStructureFeatures.FORTRESS)
         .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
         .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
         .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
         .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
      DefaultBiomeFeatures.addDefaultMushrooms(_snowmanx);
      _snowmanx.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
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
      DefaultBiomeFeatures.addNetherMineables(_snowmanx);
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
         .spawnSettings(_snowman)
         .generationSettings(_snowmanx.build())
         .build();
   }
}
