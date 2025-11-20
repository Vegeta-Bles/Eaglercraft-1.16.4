package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class DarkOakSaplingGenerator extends LargeTreeSaplingGenerator {
   public DarkOakSaplingGenerator() {
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random _snowman, boolean _snowman) {
      return null;
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random _snowman) {
      return ConfiguredFeatures.DARK_OAK;
   }
}
