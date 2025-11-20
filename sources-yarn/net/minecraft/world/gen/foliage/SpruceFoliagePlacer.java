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

public class SpruceFoliagePlacer extends FoliagePlacer {
   public static final Codec<SpruceFoliagePlacer> CODEC = RecordCodecBuilder.create(
      instance -> fillFoliagePlacerFields(instance)
            .and(UniformIntDistribution.createValidatedCodec(0, 16, 8).fieldOf("trunk_height").forGetter(arg -> arg.trunkHeight))
            .apply(instance, SpruceFoliagePlacer::new)
   );
   private final UniformIntDistribution trunkHeight;

   public SpruceFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, UniformIntDistribution trunkHeight) {
      super(radius, offset);
      this.trunkHeight = trunkHeight;
   }

   @Override
   protected FoliagePlacerType<?> getType() {
      return FoliagePlacerType.SPRUCE_FOLIAGE_PLACER;
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
      BlockPos lv = treeNode.getCenter();
      int m = random.nextInt(2);
      int n = 1;
      int o = 0;

      for (int p = offset; p >= -foliageHeight; p--) {
         this.generateSquare(world, random, config, lv, m, leaves, p, treeNode.isGiantTrunk(), box);
         if (m >= n) {
            m = o;
            o = 1;
            n = Math.min(n + 1, radius + treeNode.getFoliageRadius());
         } else {
            m++;
         }
      }
   }

   @Override
   public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
      return Math.max(4, trunkHeight - this.trunkHeight.getValue(random));
   }

   @Override
   protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
      return baseHeight == dz && y == dz && dz > 0;
   }
}
