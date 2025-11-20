package net.minecraft.world.gen.feature;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;

public class DefaultBiomeFeatures {
   public static void addBadlandsUndergroundStructures(GenerationSettings.Builder arg) {
      arg.structureFeature(ConfiguredStructureFeatures.MINESHAFT_MESA);
      arg.structureFeature(ConfiguredStructureFeatures.STRONGHOLD);
   }

   public static void addDefaultUndergroundStructures(GenerationSettings.Builder arg) {
      arg.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
      arg.structureFeature(ConfiguredStructureFeatures.STRONGHOLD);
   }

   public static void addOceanStructures(GenerationSettings.Builder arg) {
      arg.structureFeature(ConfiguredStructureFeatures.MINESHAFT);
      arg.structureFeature(ConfiguredStructureFeatures.SHIPWRECK);
   }

   public static void addLandCarvers(GenerationSettings.Builder arg) {
      arg.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
      arg.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
   }

   public static void addOceanCarvers(GenerationSettings.Builder arg) {
      arg.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.OCEAN_CAVE);
      arg.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
      arg.carver(GenerationStep.Carver.LIQUID, ConfiguredCarvers.UNDERWATER_CANYON);
      arg.carver(GenerationStep.Carver.LIQUID, ConfiguredCarvers.UNDERWATER_CAVE);
   }

   public static void addDefaultLakes(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_WATER);
      arg.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_LAVA);
   }

   public static void addDesertLakes(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_LAVA);
   }

   public static void addDungeons(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, ConfiguredFeatures.MONSTER_ROOM);
   }

   public static void addMineables(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_DIRT);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_GRAVEL);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_GRANITE);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_DIORITE);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_ANDESITE);
   }

   public static void addDefaultOres(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_COAL);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_IRON);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_GOLD);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_REDSTONE);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_DIAMOND);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_LAPIS);
   }

   public static void addExtraGoldOre(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_GOLD_EXTRA);
   }

   public static void addEmeraldOre(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_EMERALD);
   }

   public static void addInfestedStone(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_INFESTED);
   }

   public static void addDefaultDisks(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.DISK_SAND);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.DISK_CLAY);
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.DISK_GRAVEL);
   }

   public static void addClay(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.DISK_CLAY);
   }

   public static void addMossyRocks(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ConfiguredFeatures.FOREST_ROCK);
   }

   public static void addLargeFerns(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_LARGE_FERN);
   }

   public static void addSweetBerryBushesSnowy(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_BERRY_DECORATED);
   }

   public static void addSweetBerryBushes(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_BERRY_SPARSE);
   }

   public static void addBamboo(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BAMBOO_LIGHT);
   }

   public static void addBambooJungleTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BAMBOO);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BAMBOO_VEGETATION);
   }

   public static void addTaigaTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TAIGA_VEGETATION);
   }

   public static void addWaterBiomeOakTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_WATER);
   }

   public static void addBirchTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_BIRCH);
   }

   public static void addForestTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BIRCH_OTHER);
   }

   public static void addTallBirchTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BIRCH_TALL);
   }

   public static void addSavannaTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_SAVANNA);
   }

   public static void addExtraSavannaTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_SHATTERED_SAVANNA);
   }

   public static void addMountainTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_MOUNTAIN);
   }

   public static void addExtraMountainTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_MOUNTAIN_EDGE);
   }

   public static void addJungleTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_JUNGLE);
   }

   public static void addJungleEdgeTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_JUNGLE_EDGE);
   }

   public static void addBadlandsPlateauTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.OAK_BADLANDS);
   }

   public static void addSnowySpruceTrees(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRUCE_SNOWY);
   }

   public static void addJungleGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_JUNGLE);
   }

   public static void addSavannaTallGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_TALL_GRASS);
   }

   public static void addShatteredSavannaGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_NORMAL);
   }

   public static void addSavannaGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_SAVANNA);
   }

   public static void addBadlandsGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_BADLANDS);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_DEAD_BUSH_BADLANDS);
   }

   public static void addForestFlowers(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FOREST_FLOWER_VEGETATION);
   }

   public static void addForestGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_FOREST);
   }

   public static void addSwampFeatures(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SWAMP_TREE);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FLOWER_SWAMP);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_NORMAL);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_DEAD_BUSH);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_WATERLILLY);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_SWAMP);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.RED_MUSHROOM_SWAMP);
   }

   public static void addMushroomFieldsFeatures(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.MUSHROOM_FIELD_VEGETATION);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_TAIGA);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.RED_MUSHROOM_TAIGA);
   }

   public static void addPlainsFeatures(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PLAIN_VEGETATION);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FLOWER_PLAIN_DECORATED);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_PLAIN);
   }

   public static void addDesertDeadBushes(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_DEAD_BUSH_2);
   }

   public static void addGiantTaigaGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_TAIGA);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_DEAD_BUSH);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_GIANT);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.RED_MUSHROOM_GIANT);
   }

   public static void addDefaultFlowers(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FLOWER_DEFAULT);
   }

   public static void addExtraDefaultFlowers(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.FLOWER_WARM);
   }

   public static void addDefaultGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_BADLANDS);
   }

   public static void addTaigaGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_TAIGA_2);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_TAIGA);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.RED_MUSHROOM_TAIGA);
   }

   public static void addPlainsTallGrass(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_TALL_GRASS_2);
   }

   public static void addDefaultMushrooms(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_NORMAL);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.RED_MUSHROOM_NORMAL);
   }

   public static void addDefaultVegetation(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUGAR_CANE);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_PUMPKIN);
   }

   public static void addBadlandsVegetation(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUGAR_CANE_BADLANDS);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_PUMPKIN);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_CACTUS_DECORATED);
   }

   public static void addJungleVegetation(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_MELON);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.VINES);
   }

   public static void addDesertVegetation(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUGAR_CANE_DESERT);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_PUMPKIN);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_CACTUS_DESERT);
   }

   public static void addSwampVegetation(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUGAR_CANE_SWAMP);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_PUMPKIN);
   }

   public static void addDesertFeatures(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.DESERT_WELL);
   }

   public static void addFossils(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, ConfiguredFeatures.FOSSIL);
   }

   public static void addKelp(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.KELP_COLD);
   }

   public static void addSeagrassOnStone(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_SIMPLE);
   }

   public static void addLessKelp(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.KELP_WARM);
   }

   public static void addSprings(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_WATER);
      arg.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
   }

   public static void addIcebergs(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ConfiguredFeatures.ICEBERG_PACKED);
      arg.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ConfiguredFeatures.ICEBERG_BLUE);
   }

   public static void addBlueIce(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeatures.BLUE_ICE);
   }

   public static void addFrozenTopLayer(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, ConfiguredFeatures.FREEZE_TOP_LAYER);
   }

   public static void addNetherMineables(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_GRAVEL_NETHER);
      arg.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_BLACKSTONE);
      arg.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_GOLD_NETHER);
      arg.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_QUARTZ_NETHER);
      addAncientDebris(arg);
   }

   public static void addAncientDebris(GenerationSettings.Builder arg) {
      arg.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_DEBRIS_LARGE);
      arg.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_DEBRIS_SMALL);
   }

   public static void addFarmAnimals(SpawnSettings.Builder arg) {
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PIG, 10, 4, 4));
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.COW, 8, 4, 4));
   }

   public static void addBats(SpawnSettings.Builder arg) {
      arg.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.BAT, 10, 8, 8));
   }

   public static void addBatsAndMonsters(SpawnSettings.Builder arg) {
      addBats(arg);
      addMonsters(arg, 95, 5, 100);
   }

   public static void addOceanMobs(SpawnSettings.Builder arg, int squidWeight, int squidMaxGroupSize, int codWeight) {
      arg.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, squidWeight, 1, squidMaxGroupSize));
      arg.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.COD, codWeight, 3, 6));
      addBatsAndMonsters(arg);
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, 5, 1, 1));
   }

   public static void addWarmOceanMobs(SpawnSettings.Builder arg, int squidWeight, int squidMinGroupSize) {
      arg.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, squidWeight, squidMinGroupSize, 4));
      arg.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
      arg.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 2, 1, 2));
      addBatsAndMonsters(arg);
   }

   public static void addPlainsMobs(SpawnSettings.Builder arg) {
      addFarmAnimals(arg);
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.HORSE, 5, 2, 6));
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.DONKEY, 1, 1, 3));
      addBatsAndMonsters(arg);
   }

   public static void addSnowyMobs(SpawnSettings.Builder arg) {
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 10, 2, 3));
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.POLAR_BEAR, 1, 1, 2));
      addBats(arg);
      addMonsters(arg, 95, 5, 20);
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.STRAY, 80, 4, 4));
   }

   public static void addDesertMobs(SpawnSettings.Builder arg) {
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
      addBats(arg);
      addMonsters(arg, 19, 1, 100);
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.HUSK, 80, 4, 4));
   }

   public static void addMonsters(SpawnSettings.Builder arg, int zombieWeight, int zombieVillagerWeight, int skeletonWeight) {
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, zombieWeight, 4, 4));
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE_VILLAGER, zombieVillagerWeight, 1, 1));
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, skeletonWeight, 4, 4));
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 100, 4, 4));
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITCH, 5, 1, 1));
   }

   public static void addMushroomMobs(SpawnSettings.Builder arg) {
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.MOOSHROOM, 8, 4, 8));
      addBats(arg);
   }

   public static void addJungleMobs(SpawnSettings.Builder arg) {
      addFarmAnimals(arg);
      arg.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
      addBatsAndMonsters(arg);
   }

   public static void addEndMobs(SpawnSettings.Builder arg) {
      arg.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 10, 4, 4));
   }
}
