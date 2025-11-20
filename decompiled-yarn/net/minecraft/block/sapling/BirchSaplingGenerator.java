package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class BirchSaplingGenerator extends SaplingGenerator {
   public BirchSaplingGenerator() {
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random _snowman, boolean _snowman) {
      return _snowman ? ConfiguredFeatures.BIRCH_BEES_005 : ConfiguredFeatures.BIRCH;
   }
}
