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

public class AcaciaFoliagePlacer extends FoliagePlacer {
   public static final Codec<AcaciaFoliagePlacer> CODEC = RecordCodecBuilder.create(_snowman -> fillFoliagePlacerFields(_snowman).apply(_snowman, AcaciaFoliagePlacer::new));

   public AcaciaFoliagePlacer(UniformIntDistribution _snowman, UniformIntDistribution _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected FoliagePlacerType<?> getType() {
      return FoliagePlacerType.ACACIA_FOLIAGE_PLACER;
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
      boolean _snowman = treeNode.isGiantTrunk();
      BlockPos _snowmanx = treeNode.getCenter().up(offset);
      this.generateSquare(world, random, config, _snowmanx, radius + treeNode.getFoliageRadius(), leaves, -1 - foliageHeight, _snowman, box);
      this.generateSquare(world, random, config, _snowmanx, radius - 1, leaves, -foliageHeight, _snowman, box);
      this.generateSquare(world, random, config, _snowmanx, radius + treeNode.getFoliageRadius() - 1, leaves, 0, _snowman, box);
   }

   @Override
   public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
      return 0;
   }

   @Override
   protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
      return dx == 0 ? (baseHeight > 1 || y > 1) && baseHeight != 0 && y != 0 : baseHeight == dz && y == dz && dz > 0;
   }
}
