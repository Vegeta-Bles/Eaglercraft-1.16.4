package net.minecraft.world.biome.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.function.LongFunction;
import net.minecraft.util.Util;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;

public class BiomeLayers {
   private static final Int2IntMap field_26709 = Util.make(new Int2IntOpenHashMap(), int2IntOpenHashMap -> {
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.BEACH, 16);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.BEACH, 26);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.DESERT, 2);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.DESERT, 17);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.DESERT, 130);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.EXTREME_HILLS, 131);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.EXTREME_HILLS, 162);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.EXTREME_HILLS, 20);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.EXTREME_HILLS, 3);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.EXTREME_HILLS, 34);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 27);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 28);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 29);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 157);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 132);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 4);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 155);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 156);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.FOREST, 18);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.ICY, 140);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.ICY, 13);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.ICY, 12);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.JUNGLE, 168);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.JUNGLE, 169);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.JUNGLE, 21);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.JUNGLE, 23);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.JUNGLE, 22);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.JUNGLE, 149);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.JUNGLE, 151);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.MESA, 37);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.MESA, 165);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.MESA, 167);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.MESA, 166);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.BADLANDS_PLATEAU, 39);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.BADLANDS_PLATEAU, 38);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.MUSHROOM, 14);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.MUSHROOM, 15);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.NONE, 25);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 46);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 49);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 50);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 48);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 24);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 47);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 10);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 45);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 0);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.OCEAN, 44);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.PLAINS, 1);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.PLAINS, 129);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.RIVER, 11);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.RIVER, 7);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.SAVANNA, 35);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.SAVANNA, 36);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.SAVANNA, 163);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.SAVANNA, 164);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.SWAMP, 6);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.SWAMP, 134);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 160);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 161);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 32);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 33);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 30);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 31);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 158);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 5);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 19);
      method_31117(int2IntOpenHashMap, BiomeLayers.Category.TAIGA, 133);
   });

   private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(
      long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider
   ) {
      LayerFactory<T> lv = parent;

      for (int j = 0; j < count; j++) {
         lv = layer.create(contextProvider.apply(seed + (long)j), lv);
      }

      return lv;
   }

   private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(
      boolean old, int biomeSize, int riverSize, LongFunction<C> contextProvider
   ) {
      LayerFactory<T> lv = ContinentLayer.INSTANCE.create(contextProvider.apply(1L));
      lv = ScaleLayer.FUZZY.create(contextProvider.apply(2000L), lv);
      lv = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(1L), lv);
      lv = ScaleLayer.NORMAL.create(contextProvider.apply(2001L), lv);
      lv = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(2L), lv);
      lv = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(50L), lv);
      lv = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(70L), lv);
      lv = AddIslandLayer.INSTANCE.create(contextProvider.apply(2L), lv);
      LayerFactory<T> lv2 = OceanTemperatureLayer.INSTANCE.create(contextProvider.apply(2L));
      lv2 = stack(2001L, ScaleLayer.NORMAL, lv2, 6, contextProvider);
      lv = AddColdClimatesLayer.INSTANCE.create(contextProvider.apply(2L), lv);
      lv = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(3L), lv);
      lv = AddClimateLayers.AddTemperateBiomesLayer.INSTANCE.create(contextProvider.apply(2L), lv);
      lv = AddClimateLayers.AddCoolBiomesLayer.INSTANCE.create(contextProvider.apply(2L), lv);
      lv = AddClimateLayers.AddSpecialBiomesLayer.INSTANCE.create(contextProvider.apply(3L), lv);
      lv = ScaleLayer.NORMAL.create(contextProvider.apply(2002L), lv);
      lv = ScaleLayer.NORMAL.create(contextProvider.apply(2003L), lv);
      lv = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(4L), lv);
      lv = AddMushroomIslandLayer.INSTANCE.create(contextProvider.apply(5L), lv);
      lv = AddDeepOceanLayer.INSTANCE.create(contextProvider.apply(4L), lv);
      lv = stack(1000L, ScaleLayer.NORMAL, lv, 0, contextProvider);
      LayerFactory<T> lv3 = stack(1000L, ScaleLayer.NORMAL, lv, 0, contextProvider);
      lv3 = SimpleLandNoiseLayer.INSTANCE.create(contextProvider.apply(100L), lv3);
      LayerFactory<T> lv4 = new SetBaseBiomesLayer(old).create(contextProvider.apply(200L), lv);
      lv4 = AddBambooJungleLayer.INSTANCE.create(contextProvider.apply(1001L), lv4);
      lv4 = stack(1000L, ScaleLayer.NORMAL, lv4, 2, contextProvider);
      lv4 = EaseBiomeEdgeLayer.INSTANCE.create(contextProvider.apply(1000L), lv4);
      LayerFactory<T> lv5 = stack(1000L, ScaleLayer.NORMAL, lv3, 2, contextProvider);
      lv4 = AddHillsLayer.INSTANCE.create(contextProvider.apply(1000L), lv4, lv5);
      lv3 = stack(1000L, ScaleLayer.NORMAL, lv3, 2, contextProvider);
      lv3 = stack(1000L, ScaleLayer.NORMAL, lv3, riverSize, contextProvider);
      lv3 = NoiseToRiverLayer.INSTANCE.create(contextProvider.apply(1L), lv3);
      lv3 = SmoothLayer.INSTANCE.create(contextProvider.apply(1000L), lv3);
      lv4 = AddSunflowerPlainsLayer.INSTANCE.create(contextProvider.apply(1001L), lv4);

      for (int k = 0; k < biomeSize; k++) {
         lv4 = ScaleLayer.NORMAL.create(contextProvider.apply((long)(1000 + k)), lv4);
         if (k == 0) {
            lv4 = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(3L), lv4);
         }

         if (k == 1 || biomeSize == 1) {
            lv4 = AddEdgeBiomesLayer.INSTANCE.create(contextProvider.apply(1000L), lv4);
         }
      }

      lv4 = SmoothLayer.INSTANCE.create(contextProvider.apply(1000L), lv4);
      lv4 = AddRiversLayer.INSTANCE.create(contextProvider.apply(100L), lv4, lv3);
      return ApplyOceanTemperatureLayer.INSTANCE.create(contextProvider.apply(100L), lv4, lv2);
   }

   public static BiomeLayerSampler build(long seed, boolean old, int biomeSize, int riverSize) {
      int k = 25;
      LayerFactory<CachingLayerSampler> lv = build(old, biomeSize, riverSize, salt -> new CachingLayerContext(25, seed, salt));
      return new BiomeLayerSampler(lv);
   }

   public static boolean areSimilar(int id1, int id2) {
      return id1 == id2 ? true : field_26709.get(id1) == field_26709.get(id2);
   }

   private static void method_31117(Int2IntOpenHashMap int2IntOpenHashMap, BiomeLayers.Category arg, int i) {
      int2IntOpenHashMap.put(i, arg.ordinal());
   }

   protected static boolean isOcean(int id) {
      return id == 44 || id == 45 || id == 0 || id == 46 || id == 10 || id == 47 || id == 48 || id == 24 || id == 49 || id == 50;
   }

   protected static boolean isShallowOcean(int id) {
      return id == 44 || id == 45 || id == 0 || id == 46 || id == 10;
   }

   static enum Category {
      NONE,
      TAIGA,
      EXTREME_HILLS,
      JUNGLE,
      MESA,
      BADLANDS_PLATEAU,
      PLAINS,
      SAVANNA,
      ICY,
      BEACH,
      FOREST,
      OCEAN,
      DESERT,
      RIVER,
      SWAMP,
      MUSHROOM;

      private Category() {
      }
   }
}
