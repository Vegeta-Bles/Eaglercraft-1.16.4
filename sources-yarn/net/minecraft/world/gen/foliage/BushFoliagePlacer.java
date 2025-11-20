package net.minecraft.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class BushFoliagePlacer extends BlobFoliagePlacer {
   public static final Codec<BushFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> createCodec(instance).apply(instance, BushFoliagePlacer::new));

   public BushFoliagePlacer(UniformIntDistribution arg, UniformIntDistribution arg2, int i) {
      super(arg, arg2, i);
   }

   @Override
   protected FoliagePlacerType<?> getType() {
      return FoliagePlacerType.BUSH_FOLIAGE_PLACER;
   }

   @Override
   protected void generate(
      ModifiableTestableWorld world,
      Random random,
      TreeFeatureConfig config,
      int trunkHeight,
      FoliagePlacer.TreeNode treeNode,
      int foliageHeight,
      int radius,
      Set<BlockPos> leaves,
      int offset,
      BlockBox box
   ) {
      for (int m = offset; m >= offset - foliageHeight; m--) {
         int n = radius + treeNode.getFoliageRadius() - 1 - m;
         this.generateSquare(world, random, config, treeNode.getCenter(), n, leaves, m, treeNode.isGiantTrunk(), box);
      }
   }

   @Override
   protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
      return baseHeight == dz && y == dz && random.nextInt(2) == 0;
   }
}
