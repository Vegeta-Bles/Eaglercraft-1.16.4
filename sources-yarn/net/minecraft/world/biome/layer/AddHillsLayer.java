package net.minecraft.world.biome.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.Util;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.layer.util.NorthWestCoordinateTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum AddHillsLayer implements MergingLayer, NorthWestCoordinateTransformer {
   INSTANCE;

   private static final Logger LOGGER = LogManager.getLogger();
   private static final Int2IntMap field_26727 = Util.make(new Int2IntOpenHashMap(), int2IntOpenHashMap -> {
      int2IntOpenHashMap.put(1, 129);
      int2IntOpenHashMap.put(2, 130);
      int2IntOpenHashMap.put(3, 131);
      int2IntOpenHashMap.put(4, 132);
      int2IntOpenHashMap.put(5, 133);
      int2IntOpenHashMap.put(6, 134);
      int2IntOpenHashMap.put(12, 140);
      int2IntOpenHashMap.put(21, 149);
      int2IntOpenHashMap.put(23, 151);
      int2IntOpenHashMap.put(27, 155);
      int2IntOpenHashMap.put(28, 156);
      int2IntOpenHashMap.put(29, 157);
      int2IntOpenHashMap.put(30, 158);
      int2IntOpenHashMap.put(32, 160);
      int2IntOpenHashMap.put(33, 161);
      int2IntOpenHashMap.put(34, 162);
      int2IntOpenHashMap.put(35, 163);
      int2IntOpenHashMap.put(36, 164);
      int2IntOpenHashMap.put(37, 165);
      int2IntOpenHashMap.put(38, 166);
      int2IntOpenHashMap.put(39, 167);
   });

   private AddHillsLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
      int k = sampler1.sample(this.transformX(x + 1), this.transformZ(z + 1));
      int l = sampler2.sample(this.transformX(x + 1), this.transformZ(z + 1));
      if (k > 255) {
         LOGGER.debug("old! {}", k);
      }

      int m = (l - 2) % 29;
      if (!BiomeLayers.isShallowOcean(k) && l >= 2 && m == 1) {
         return field_26727.getOrDefault(k, k);
      } else {
         if (context.nextInt(3) == 0 || m == 0) {
            int n = k;
            if (k == 2) {
               n = 17;
            } else if (k == 4) {
               n = 18;
            } else if (k == 27) {
               n = 28;
            } else if (k == 29) {
               n = 1;
            } else if (k == 5) {
               n = 19;
            } else if (k == 32) {
               n = 33;
            } else if (k == 30) {
               n = 31;
            } else if (k == 1) {
               n = context.nextInt(3) == 0 ? 18 : 4;
            } else if (k == 12) {
               n = 13;
            } else if (k == 21) {
               n = 22;
            } else if (k == 168) {
               n = 169;
            } else if (k == 0) {
               n = 24;
            } else if (k == 45) {
               n = 48;
            } else if (k == 46) {
               n = 49;
            } else if (k == 10) {
               n = 50;
            } else if (k == 3) {
               n = 34;
            } else if (k == 35) {
               n = 36;
            } else if (BiomeLayers.areSimilar(k, 38)) {
               n = 37;
            } else if ((k == 24 || k == 48 || k == 49 || k == 50) && context.nextInt(3) == 0) {
               n = context.nextInt(2) == 0 ? 1 : 4;
            }

            if (m == 0 && n != k) {
               n = field_26727.getOrDefault(n, k);
            }

            if (n != k) {
               int o = 0;
               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 1), this.transformZ(z + 0)), k)) {
                  o++;
               }

               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 2), this.transformZ(z + 1)), k)) {
                  o++;
               }

               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 0), this.transformZ(z + 1)), k)) {
                  o++;
               }

               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 1), this.transformZ(z + 2)), k)) {
                  o++;
               }

               if (o >= 3) {
                  return n;
               }
            }
         }

         return k;
      }
   }
}
