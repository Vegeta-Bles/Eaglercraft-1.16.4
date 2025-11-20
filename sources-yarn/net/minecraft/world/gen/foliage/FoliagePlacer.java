package net.minecraft.world.gen.foliage;

import com.mojang.datafixers.Products.P2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public abstract class FoliagePlacer {
   public static final Codec<FoliagePlacer> TYPE_CODEC = Registry.FOLIAGE_PLACER_TYPE.dispatch(FoliagePlacer::getType, FoliagePlacerType::getCodec);
   protected final UniformIntDistribution radius;
   protected final UniformIntDistribution offset;

   protected static <P extends FoliagePlacer> P2<Mu<P>, UniformIntDistribution, UniformIntDistribution> fillFoliagePlacerFields(Instance<P> instance) {
      return instance.group(
         UniformIntDistribution.createValidatedCodec(0, 8, 8).fieldOf("radius").forGetter(arg -> arg.radius),
         UniformIntDistribution.createValidatedCodec(0, 8, 8).fieldOf("offset").forGetter(arg -> arg.offset)
      );
   }

   public FoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset) {
      this.radius = radius;
      this.offset = offset;
   }

   protected abstract FoliagePlacerType<?> getType();

   public void generate(
      ModifiableTestableWorld world,
      Random random,
      TreeFeatureConfig config,
      int trunkHeight,
      FoliagePlacer.TreeNode treeNode,
      int foliageHeight,
      int radius,
      Set<BlockPos> leaves,
      BlockBox box
   ) {
      this.generate(world, random, config, trunkHeight, treeNode, foliageHeight, radius, leaves, this.getRandomOffset(random), box);
   }

   protected abstract void generate(
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
   );

   public abstract int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config);

   public int getRandomRadius(Random random, int baseHeight) {
      return this.radius.getValue(random);
   }

   private int getRandomOffset(Random random) {
      return this.offset.getValue(random);
   }

   protected abstract boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk);

   protected boolean isPositionInvalid(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
      int m;
      int n;
      if (giantTrunk) {
         m = Math.min(Math.abs(dx), Math.abs(dx - 1));
         n = Math.min(Math.abs(dz), Math.abs(dz - 1));
      } else {
         m = Math.abs(dx);
         n = Math.abs(dz);
      }

      return this.isInvalidForLeaves(random, m, y, n, radius, giantTrunk);
   }

   protected void generateSquare(
      ModifiableTestableWorld world,
      Random random,
      TreeFeatureConfig config,
      BlockPos pos,
      int radius,
      Set<BlockPos> leaves,
      int y,
      boolean giantTrunk,
      BlockBox box
   ) {
      int k = giantTrunk ? 1 : 0;
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (int l = -radius; l <= radius + k; l++) {
         for (int m = -radius; m <= radius + k; m++) {
            if (!this.isPositionInvalid(random, l, y, m, radius, giantTrunk)) {
               lv.set(pos, l, y, m);
               if (TreeFeature.canReplace(world, lv)) {
                  world.setBlockState(lv, config.leavesProvider.getBlockState(random, lv), 19);
                  box.encompass(new BlockBox(lv, lv));
                  leaves.add(lv.toImmutable());
               }
            }
         }
      }
   }

   public static final class TreeNode {
      private final BlockPos center;
      private final int foliageRadius;
      private final boolean giantTrunk;

      public TreeNode(BlockPos center, int foliageRadius, boolean giantTrunk) {
         this.center = center;
         this.foliageRadius = foliageRadius;
         this.giantTrunk = giantTrunk;
      }

      public BlockPos getCenter() {
         return this.center;
      }

      public int getFoliageRadius() {
         return this.foliageRadius;
      }

      public boolean isGiantTrunk() {
         return this.giantTrunk;
      }
   }
}
