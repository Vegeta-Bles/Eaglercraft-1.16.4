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
   private static final Int2IntMap field_26727 = Util.make(new Int2IntOpenHashMap(), _snowman -> {
      _snowman.put(1, 129);
      _snowman.put(2, 130);
      _snowman.put(3, 131);
      _snowman.put(4, 132);
      _snowman.put(5, 133);
      _snowman.put(6, 134);
      _snowman.put(12, 140);
      _snowman.put(21, 149);
      _snowman.put(23, 151);
      _snowman.put(27, 155);
      _snowman.put(28, 156);
      _snowman.put(29, 157);
      _snowman.put(30, 158);
      _snowman.put(32, 160);
      _snowman.put(33, 161);
      _snowman.put(34, 162);
      _snowman.put(35, 163);
      _snowman.put(36, 164);
      _snowman.put(37, 165);
      _snowman.put(38, 166);
      _snowman.put(39, 167);
   });

   private AddHillsLayer() {
   }

   @Override
   public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
      int _snowman = sampler1.sample(this.transformX(x + 1), this.transformZ(z + 1));
      int _snowmanx = sampler2.sample(this.transformX(x + 1), this.transformZ(z + 1));
      if (_snowman > 255) {
         LOGGER.debug("old! {}", _snowman);
      }

      int _snowmanxx = (_snowmanx - 2) % 29;
      if (!BiomeLayers.isShallowOcean(_snowman) && _snowmanx >= 2 && _snowmanxx == 1) {
         return field_26727.getOrDefault(_snowman, _snowman);
      } else {
         if (context.nextInt(3) == 0 || _snowmanxx == 0) {
            int _snowmanxxx = _snowman;
            if (_snowman == 2) {
               _snowmanxxx = 17;
            } else if (_snowman == 4) {
               _snowmanxxx = 18;
            } else if (_snowman == 27) {
               _snowmanxxx = 28;
            } else if (_snowman == 29) {
               _snowmanxxx = 1;
            } else if (_snowman == 5) {
               _snowmanxxx = 19;
            } else if (_snowman == 32) {
               _snowmanxxx = 33;
            } else if (_snowman == 30) {
               _snowmanxxx = 31;
            } else if (_snowman == 1) {
               _snowmanxxx = context.nextInt(3) == 0 ? 18 : 4;
            } else if (_snowman == 12) {
               _snowmanxxx = 13;
            } else if (_snowman == 21) {
               _snowmanxxx = 22;
            } else if (_snowman == 168) {
               _snowmanxxx = 169;
            } else if (_snowman == 0) {
               _snowmanxxx = 24;
            } else if (_snowman == 45) {
               _snowmanxxx = 48;
            } else if (_snowman == 46) {
               _snowmanxxx = 49;
            } else if (_snowman == 10) {
               _snowmanxxx = 50;
            } else if (_snowman == 3) {
               _snowmanxxx = 34;
            } else if (_snowman == 35) {
               _snowmanxxx = 36;
            } else if (BiomeLayers.areSimilar(_snowman, 38)) {
               _snowmanxxx = 37;
            } else if ((_snowman == 24 || _snowman == 48 || _snowman == 49 || _snowman == 50) && context.nextInt(3) == 0) {
               _snowmanxxx = context.nextInt(2) == 0 ? 1 : 4;
            }

            if (_snowmanxx == 0 && _snowmanxxx != _snowman) {
               _snowmanxxx = field_26727.getOrDefault(_snowmanxxx, _snowman);
            }

            if (_snowmanxxx != _snowman) {
               int _snowmanxxxx = 0;
               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 1), this.transformZ(z + 0)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 2), this.transformZ(z + 1)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 0), this.transformZ(z + 1)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 1), this.transformZ(z + 2)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (_snowmanxxxx >= 3) {
                  return _snowmanxxx;
               }
            }
         }

         return _snowman;
      }
   }
}
