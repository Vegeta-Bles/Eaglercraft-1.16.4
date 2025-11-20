package net.minecraft.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class LargeOakTrunkPlacer extends TrunkPlacer {
   public static final Codec<LargeOakTrunkPlacer> CODEC = RecordCodecBuilder.create(
      instance -> method_28904(instance).apply(instance, LargeOakTrunkPlacer::new)
   );

   public LargeOakTrunkPlacer(int i, int j, int k) {
      super(i, j, k);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.FANCY_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      int j = 5;
      int k = trunkHeight + 2;
      int l = MathHelper.floor((double)k * 0.618);
      if (!config.skipFluidCheck) {
         setToDirt(world, pos.down());
      }

      double d = 1.0;
      int m = Math.min(1, MathHelper.floor(1.382 + Math.pow(1.0 * (double)k / 13.0, 2.0)));
      int n = pos.getY() + l;
      int o = k - 5;
      List<LargeOakTrunkPlacer.BranchPosition> list = Lists.newArrayList();
      list.add(new LargeOakTrunkPlacer.BranchPosition(pos.up(o), n));

      for (; o >= 0; o--) {
         float f = this.shouldGenerateBranch(k, o);
         if (!(f < 0.0F)) {
            for (int p = 0; p < m; p++) {
               double e = 1.0;
               double g = 1.0 * (double)f * ((double)random.nextFloat() + 0.328);
               double h = (double)(random.nextFloat() * 2.0F) * Math.PI;
               double q = g * Math.sin(h) + 0.5;
               double r = g * Math.cos(h) + 0.5;
               BlockPos lv = pos.add(q, (double)(o - 1), r);
               BlockPos lv2 = lv.up(5);
               if (this.makeOrCheckBranch(world, random, lv, lv2, false, placedStates, box, config)) {
                  int s = pos.getX() - lv.getX();
                  int t = pos.getZ() - lv.getZ();
                  double u = (double)lv.getY() - Math.sqrt((double)(s * s + t * t)) * 0.381;
                  int v = u > (double)n ? n : (int)u;
                  BlockPos lv3 = new BlockPos(pos.getX(), v, pos.getZ());
                  if (this.makeOrCheckBranch(world, random, lv3, lv, false, placedStates, box, config)) {
                     list.add(new LargeOakTrunkPlacer.BranchPosition(lv, lv3.getY()));
                  }
               }
            }
         }
      }

      this.makeOrCheckBranch(world, random, pos, pos.up(l), true, placedStates, box, config);
      this.makeBranches(world, random, k, pos, list, placedStates, box, config);
      List<FoliagePlacer.TreeNode> list2 = Lists.newArrayList();

      for (LargeOakTrunkPlacer.BranchPosition lv4 : list) {
         if (this.isHighEnough(k, lv4.getEndY() - pos.getY())) {
            list2.add(lv4.node);
         }
      }

      return list2;
   }

   private boolean makeOrCheckBranch(
      ModifiableTestableWorld world,
      Random random,
      BlockPos start,
      BlockPos end,
      boolean make,
      Set<BlockPos> placedStates,
      BlockBox box,
      TreeFeatureConfig config
   ) {
      if (!make && Objects.equals(start, end)) {
         return true;
      } else {
         BlockPos lv = end.add(-start.getX(), -start.getY(), -start.getZ());
         int i = this.getLongestSide(lv);
         float f = (float)lv.getX() / (float)i;
         float g = (float)lv.getY() / (float)i;
         float h = (float)lv.getZ() / (float)i;

         for (int j = 0; j <= i; j++) {
            BlockPos lv2 = start.add((double)(0.5F + (float)j * f), (double)(0.5F + (float)j * g), (double)(0.5F + (float)j * h));
            if (make) {
               setBlockState(world, lv2, config.trunkProvider.getBlockState(random, lv2).with(PillarBlock.AXIS, this.getLogAxis(start, lv2)), box);
               placedStates.add(lv2.toImmutable());
            } else if (!TreeFeature.canTreeReplace(world, lv2)) {
               return false;
            }
         }

         return true;
      }
   }

   private int getLongestSide(BlockPos offset) {
      int i = MathHelper.abs(offset.getX());
      int j = MathHelper.abs(offset.getY());
      int k = MathHelper.abs(offset.getZ());
      return Math.max(i, Math.max(j, k));
   }

   private Direction.Axis getLogAxis(BlockPos branchStart, BlockPos branchEnd) {
      Direction.Axis lv = Direction.Axis.Y;
      int i = Math.abs(branchEnd.getX() - branchStart.getX());
      int j = Math.abs(branchEnd.getZ() - branchStart.getZ());
      int k = Math.max(i, j);
      if (k > 0) {
         if (i == k) {
            lv = Direction.Axis.X;
         } else {
            lv = Direction.Axis.Z;
         }
      }

      return lv;
   }

   private boolean isHighEnough(int treeHeight, int height) {
      return (double)height >= (double)treeHeight * 0.2;
   }

   private void makeBranches(
      ModifiableTestableWorld world,
      Random random,
      int treeHeight,
      BlockPos treePos,
      List<LargeOakTrunkPlacer.BranchPosition> branches,
      Set<BlockPos> placedStates,
      BlockBox box,
      TreeFeatureConfig config
   ) {
      for (LargeOakTrunkPlacer.BranchPosition lv : branches) {
         int j = lv.getEndY();
         BlockPos lv2 = new BlockPos(treePos.getX(), j, treePos.getZ());
         if (!lv2.equals(lv.node.getCenter()) && this.isHighEnough(treeHeight, j - treePos.getY())) {
            this.makeOrCheckBranch(world, random, lv2, lv.node.getCenter(), true, placedStates, box, config);
         }
      }
   }

   private float shouldGenerateBranch(int trunkHeight, int y) {
      if ((float)y < (float)trunkHeight * 0.3F) {
         return -1.0F;
      } else {
         float f = (float)trunkHeight / 2.0F;
         float g = f - (float)y;
         float h = MathHelper.sqrt(f * f - g * g);
         if (g == 0.0F) {
            h = f;
         } else if (Math.abs(g) >= f) {
            return 0.0F;
         }

         return h * 0.5F;
      }
   }

   static class BranchPosition {
      private final FoliagePlacer.TreeNode node;
      private final int endY;

      public BranchPosition(BlockPos pos, int width) {
         this.node = new FoliagePlacer.TreeNode(pos, 0, false);
         this.endY = width;
      }

      public int getEndY() {
         return this.endY;
      }
   }
}
