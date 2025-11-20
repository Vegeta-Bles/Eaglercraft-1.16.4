package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class JungleSaplingGenerator extends LargeTreeSaplingGenerator {
   public JungleSaplingGenerator() {
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random _snowman, boolean _snowman) {
      return ConfiguredFeatures.JUNGLE_TREE_NO_VINE;
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random _snowman) {
      return ConfiguredFeatures.MEGA_JUNGLE_TREE;
   }
}
