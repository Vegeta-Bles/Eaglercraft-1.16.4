package net.minecraft.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class MegaPineFoliagePlacer extends FoliagePlacer {
   public static final Codec<MegaPineFoliagePlacer> CODEC = RecordCodecBuilder.create(
      instance -> fillFoliagePlacerFields(instance)
            .and(UniformIntDistribution.createValidatedCodec(0, 16, 8).fieldOf("crown_height").forGetter(arg -> arg.crownHeight))
            .apply(instance, MegaPineFoliagePlacer::new)
   );
   private final UniformIntDistribution crownHeight;

   public MegaPineFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, UniformIntDistribution crownHeight) {
      super(radius, offset);
      this.crownHeight = crownHeight;
   }

   @Override
   protected FoliagePlacerType<?> getType() {
      return FoliagePlacerType.MEGA_PINE_FOLIAGE_PLACER;
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
      int m = 0;

      for (int n = lv.getY() - foliageHeight + offset; n <= lv.getY() + offset; n++) {
         int o = lv.getY() - n;
         int p = radius + treeNode.getFoliageRadius() + MathHelper.floor((float)o / (float)foliageHeight * 3.5F);
         int q;
         if (o > 0 && p == m && (n & 1) == 0) {
            q = p + 1;
         } else {
            q = p;
         }

         this.generateSquare(world, random, config, new BlockPos(lv.getX(), n, lv.getZ()), q, leaves, 0, treeNode.isGiantTrunk(), box);
         m = p;
      }
   }

   @Override
   public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
      return this.crownHeight.getValue(random);
   }

   @Override
   protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
      return baseHeight + y >= 7 ? true : baseHeight * baseHeight + y * y > dz * dz;
   }
}
