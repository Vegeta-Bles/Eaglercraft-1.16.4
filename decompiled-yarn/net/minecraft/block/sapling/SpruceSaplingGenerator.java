package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class SpruceSaplingGenerator extends LargeTreeSaplingGenerator {
   public SpruceSaplingGenerator() {
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random _snowman, boolean _snowman) {
      return ConfiguredFeatures.SPRUCE;
   }

   @Nullable
   @Override
   protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random _snowman) {
      return _snowman.nextBoolean() ? ConfiguredFeatures.MEGA_SPRUCE : ConfiguredFeatures.MEGA_PINE;
   }
}
