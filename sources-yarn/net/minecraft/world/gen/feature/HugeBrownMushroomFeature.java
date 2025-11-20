package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class HugeBrownMushroomFeature extends HugeMushroomFeature {
   public HugeBrownMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
      super(codec);
   }

   @Override
   protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
      int j = config.foliageRadius;

      for (int k = -j; k <= j; k++) {
         for (int l = -j; l <= j; l++) {
            boolean bl = k == -j;
            boolean bl2 = k == j;
            boolean bl3 = l == -j;
            boolean bl4 = l == j;
            boolean bl5 = bl || bl2;
            boolean bl6 = bl3 || bl4;
            if (!bl5 || !bl6) {
               mutable.set(start, k, y, l);
               if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                  boolean bl7 = bl || bl6 && k == 1 - j;
                  boolean bl8 = bl2 || bl6 && k == j - 1;
                  boolean bl9 = bl3 || bl5 && l == 1 - j;
                  boolean bl10 = bl4 || bl5 && l == j - 1;
                  this.setBlockState(
                     world,
                     mutable,
                     config.capProvider
                        .getBlockState(random, start)
                        .with(MushroomBlock.WEST, Boolean.valueOf(bl7))
                        .with(MushroomBlock.EAST, Boolean.valueOf(bl8))
                        .with(MushroomBlock.NORTH, Boolean.valueOf(bl9))
                        .with(MushroomBlock.SOUTH, Boolean.valueOf(bl10))
                  );
               }
            }
         }
      }
   }

   @Override
   protected int getCapSize(int i, int j, int capSize, int y) {
      return y <= 3 ? 0 : capSize;
   }
}
