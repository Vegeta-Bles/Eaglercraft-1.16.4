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
      _snowman -> fillFoliagePlacerFields(_snowman)
            .and(UniformIntDistribution.createValidatedCodec(0, 16, 8).fieldOf("crown_height").forGetter(_snowmanx -> _snowmanx.crownHeight))
            .apply(_snowman, MegaPineFoliagePlacer::new)
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
      BlockPos _snowman = treeNode.getCenter();
      int _snowmanx = 0;

      for (int _snowmanxx = _snowman.getY() - foliageHeight + offset; _snowmanxx <= _snowman.getY() + offset; _snowmanxx++) {
         int _snowmanxxx = _snowman.getY() - _snowmanxx;
         int _snowmanxxxx = radius + treeNode.getFoliageRadius() + MathHelper.floor((float)_snowmanxxx / (float)foliageHeight * 3.5F);
         int _snowmanxxxxx;
         if (_snowmanxxx > 0 && _snowmanxxxx == _snowmanx && (_snowmanxx & 1) == 0) {
            _snowmanxxxxx = _snowmanxxxx + 1;
         } else {
            _snowmanxxxxx = _snowmanxxxx;
         }

         this.generateSquare(world, random, config, new BlockPos(_snowman.getX(), _snowmanxx, _snowman.getZ()), _snowmanxxxxx, leaves, 0, treeNode.isGiantTrunk(), box);
         _snowmanx = _snowmanxxxx;
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
