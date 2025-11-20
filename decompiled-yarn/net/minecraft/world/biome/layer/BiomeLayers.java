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
   private static final Int2IntMap field_26709 = Util.make(new Int2IntOpenHashMap(), _snowman -> {
      method_31117(_snowman, BiomeLayers.Category.BEACH, 16);
      method_31117(_snowman, BiomeLayers.Category.BEACH, 26);
      method_31117(_snowman, BiomeLayers.Category.DESERT, 2);
      method_31117(_snowman, BiomeLayers.Category.DESERT, 17);
      method_31117(_snowman, BiomeLayers.Category.DESERT, 130);
      method_31117(_snowman, BiomeLayers.Category.EXTREME_HILLS, 131);
      method_31117(_snowman, BiomeLayers.Category.EXTREME_HILLS, 162);
      method_31117(_snowman, BiomeLayers.Category.EXTREME_HILLS, 20);
      method_31117(_snowman, BiomeLayers.Category.EXTREME_HILLS, 3);
      method_31117(_snowman, BiomeLayers.Category.EXTREME_HILLS, 34);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 27);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 28);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 29);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 157);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 132);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 4);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 155);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 156);
      method_31117(_snowman, BiomeLayers.Category.FOREST, 18);
      method_31117(_snowman, BiomeLayers.Category.ICY, 140);
      method_31117(_snowman, BiomeLayers.Category.ICY, 13);
      method_31117(_snowman, BiomeLayers.Category.ICY, 12);
      method_31117(_snowman, BiomeLayers.Category.JUNGLE, 168);
      method_31117(_snowman, BiomeLayers.Category.JUNGLE, 169);
      method_31117(_snowman, BiomeLayers.Category.JUNGLE, 21);
      method_31117(_snowman, BiomeLayers.Category.JUNGLE, 23);
      method_31117(_snowman, BiomeLayers.Category.JUNGLE, 22);
      method_31117(_snowman, BiomeLayers.Category.JUNGLE, 149);
      method_31117(_snowman, BiomeLayers.Category.JUNGLE, 151);
      method_31117(_snowman, BiomeLayers.Category.MESA, 37);
      method_31117(_snowman, BiomeLayers.Category.MESA, 165);
      method_31117(_snowman, BiomeLayers.Category.MESA, 167);
      method_31117(_snowman, BiomeLayers.Category.MESA, 166);
      method_31117(_snowman, BiomeLayers.Category.BADLANDS_PLATEAU, 39);
      method_31117(_snowman, BiomeLayers.Category.BADLANDS_PLATEAU, 38);
      method_31117(_snowman, BiomeLayers.Category.MUSHROOM, 14);
      method_31117(_snowman, BiomeLayers.Category.MUSHROOM, 15);
      method_31117(_snowman, BiomeLayers.Category.NONE, 25);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 46);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 49);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 50);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 48);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 24);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 47);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 10);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 45);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 0);
      method_31117(_snowman, BiomeLayers.Category.OCEAN, 44);
      method_31117(_snowman, BiomeLayers.Category.PLAINS, 1);
      method_31117(_snowman, BiomeLayers.Category.PLAINS, 129);
      method_31117(_snowman, BiomeLayers.Category.RIVER, 11);
      method_31117(_snowman, BiomeLayers.Category.RIVER, 7);
      method_31117(_snowman, BiomeLayers.Category.SAVANNA, 35);
      method_31117(_snowman, BiomeLayers.Category.SAVANNA, 36);
      method_31117(_snowman, BiomeLayers.Category.SAVANNA, 163);
      method_31117(_snowman, BiomeLayers.Category.SAVANNA, 164);
      method_31117(_snowman, BiomeLayers.Category.SWAMP, 6);
      method_31117(_snowman, BiomeLayers.Category.SWAMP, 134);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 160);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 161);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 32);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 33);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 30);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 31);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 158);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 5);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 19);
      method_31117(_snowman, BiomeLayers.Category.TAIGA, 133);
   });

   private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(
      long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider
   ) {
      LayerFactory<T> _snowman = parent;

      for (int _snowmanx = 0; _snowmanx < count; _snowmanx++) {
         _snowman = layer.create(contextProvider.apply(seed + (long)_snowmanx), _snowman);
      }

      return _snowman;
   }

   private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(
      boolean old, int biomeSize, int riverSize, LongFunction<C> contextProvider
   ) {
      LayerFactory<T> _snowman = ContinentLayer.INSTANCE.create(contextProvider.apply(1L));
      _snowman = ScaleLayer.FUZZY.create(contextProvider.apply(2000L), _snowman);
      _snowman = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(1L), _snowman);
      _snowman = ScaleLayer.NORMAL.create(contextProvider.apply(2001L), _snowman);
      _snowman = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(2L), _snowman);
      _snowman = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(50L), _snowman);
      _snowman = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(70L), _snowman);
      _snowman = AddIslandLayer.INSTANCE.create(contextProvider.apply(2L), _snowman);
      LayerFactory<T> _snowmanx = OceanTemperatureLayer.INSTANCE.create(contextProvider.apply(2L));
      _snowmanx = stack(2001L, ScaleLayer.NORMAL, _snowmanx, 6, contextProvider);
      _snowman = AddColdClimatesLayer.INSTANCE.create(contextProvider.apply(2L), _snowman);
      _snowman = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(3L), _snowman);
      _snowman = AddClimateLayers.AddTemperateBiomesLayer.INSTANCE.create(contextProvider.apply(2L), _snowman);
      _snowman = AddClimateLayers.AddCoolBiomesLayer.INSTANCE.create(contextProvider.apply(2L), _snowman);
      _snowman = AddClimateLayers.AddSpecialBiomesLayer.INSTANCE.create(contextProvider.apply(3L), _snowman);
      _snowman = ScaleLayer.NORMAL.create(contextProvider.apply(2002L), _snowman);
      _snowman = ScaleLayer.NORMAL.create(contextProvider.apply(2003L), _snowman);
      _snowman = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(4L), _snowman);
      _snowman = AddMushroomIslandLayer.INSTANCE.create(contextProvider.apply(5L), _snowman);
      _snowman = AddDeepOceanLayer.INSTANCE.create(contextProvider.apply(4L), _snowman);
      _snowman = stack(1000L, ScaleLayer.NORMAL, _snowman, 0, contextProvider);
      LayerFactory<T> var6 = stack(1000L, ScaleLayer.NORMAL, _snowman, 0, contextProvider);
      var6 = SimpleLandNoiseLayer.INSTANCE.create(contextProvider.apply(100L), var6);
      LayerFactory<T> var7 = new SetBaseBiomesLayer(old).create(contextProvider.apply(200L), _snowman);
      var7 = AddBambooJungleLayer.INSTANCE.create(contextProvider.apply(1001L), var7);
      var7 = stack(1000L, ScaleLayer.NORMAL, var7, 2, contextProvider);
      var7 = EaseBiomeEdgeLayer.INSTANCE.create(contextProvider.apply(1000L), var7);
      LayerFactory<T> var8 = stack(1000L, ScaleLayer.NORMAL, var6, 2, contextProvider);
      var7 = AddHillsLayer.INSTANCE.create(contextProvider.apply(1000L), var7, var8);
      var6 = stack(1000L, ScaleLayer.NORMAL, var6, 2, contextProvider);
      var6 = stack(1000L, ScaleLayer.NORMAL, var6, riverSize, contextProvider);
      var6 = NoiseToRiverLayer.INSTANCE.create(contextProvider.apply(1L), var6);
      var6 = SmoothLayer.INSTANCE.create(contextProvider.apply(1000L), var6);
      var7 = AddSunflowerPlainsLayer.INSTANCE.create(contextProvider.apply(1001L), var7);

      for (int _snowmanxx = 0; _snowmanxx < biomeSize; _snowmanxx++) {
         var7 = ScaleLayer.NORMAL.create(contextProvider.apply((long)(1000 + _snowmanxx)), var7);
         if (_snowmanxx == 0) {
            var7 = IncreaseEdgeCurvatureLayer.INSTANCE.create(contextProvider.apply(3L), var7);
         }

         if (_snowmanxx == 1 || biomeSize == 1) {
            var7 = AddEdgeBiomesLayer.INSTANCE.create(contextProvider.apply(1000L), var7);
         }
      }

      var7 = SmoothLayer.INSTANCE.create(contextProvider.apply(1000L), var7);
      var7 = AddRiversLayer.INSTANCE.create(contextProvider.apply(100L), var7, var6);
      return ApplyOceanTemperatureLayer.INSTANCE.create(contextProvider.apply(100L), var7, _snowmanx);
   }

   public static BiomeLayerSampler build(long seed, boolean old, int biomeSize, int riverSize) {
      int _snowman = 25;
      LayerFactory<CachingLayerSampler> _snowmanx = build(old, biomeSize, riverSize, salt -> new CachingLayerContext(25, seed, salt));
      return new BiomeLayerSampler(_snowmanx);
   }

   public static boolean areSimilar(int id1, int id2) {
      return id1 == id2 ? true : field_26709.get(id1) == field_26709.get(id2);
   }

   private static void method_31117(Int2IntOpenHashMap _snowman, BiomeLayers.Category _snowman, int _snowman) {
      _snowman.put(_snowman, _snowman.ordinal());
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
