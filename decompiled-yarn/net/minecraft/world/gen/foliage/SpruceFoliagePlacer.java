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
      _snowman -> fillFoliagePlacerFields(_snowman)
            .and(UniformIntDistribution.createValidatedCodec(0, 16, 8).fieldOf("trunk_height").forGetter(_snowmanx -> _snowmanx.trunkHeight))
            .apply(_snowman, SpruceFoliagePlacer::new)
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
      BlockPos _snowman = treeNode.getCenter();
      int _snowmanx = random.nextInt(2);
      int _snowmanxx = 1;
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = offset; _snowmanxxxx >= -foliageHeight; _snowmanxxxx--) {
         this.generateSquare(world, random, config, _snowman, _snowmanx, leaves, _snowmanxxxx, treeNode.isGiantTrunk(), box);
         if (_snowmanx >= _snowmanxx) {
            _snowmanx = _snowmanxxx;
            _snowmanxxx = 1;
            _snowmanxx = Math.min(_snowmanxx + 1, radius + treeNode.getFoliageRadius());
         } else {
            _snowmanx++;
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
