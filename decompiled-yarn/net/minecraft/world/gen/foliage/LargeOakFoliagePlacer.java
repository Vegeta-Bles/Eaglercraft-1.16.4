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

public class LargeOakFoliagePlacer extends BlobFoliagePlacer {
   public static final Codec<LargeOakFoliagePlacer> CODEC = RecordCodecBuilder.create(_snowman -> createCodec(_snowman).apply(_snowman, LargeOakFoliagePlacer::new));

   public LargeOakFoliagePlacer(UniformIntDistribution _snowman, UniformIntDistribution _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected FoliagePlacerType<?> getType() {
      return FoliagePlacerType.FANCY_FOLIAGE_PLACER;
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
      for (int _snowman = offset; _snowman >= offset - foliageHeight; _snowman--) {
         int _snowmanx = radius + (_snowman != offset && _snowman != offset - foliageHeight ? 1 : 0);
         this.generateSquare(world, random, config, treeNode.getCenter(), _snowmanx, leaves, _snowman, treeNode.isGiantTrunk(), box);
      }
   }

   @Override
   protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
      return MathHelper.square((float)baseHeight + 0.5F) + MathHelper.square((float)y + 0.5F) > (float)(dz * dz);
   }
}
