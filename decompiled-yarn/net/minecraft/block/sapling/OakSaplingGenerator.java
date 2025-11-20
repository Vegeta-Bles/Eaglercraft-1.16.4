package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class OakSaplingGenerator extends SaplingGenerator {
   public OakSaplingGenerator() {
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random _snowman, boolean _snowman) {
      if (_snowman.nextInt(10) == 0) {
         return _snowman ? ConfiguredFeatures.FANCY_OAK_BEES_005 : ConfiguredFeatures.FANCY_OAK;
      } else {
         return _snowman ? ConfiguredFeatures.OAK_BEES_005 : ConfiguredFeatures.OAK;
      }
   }
}
