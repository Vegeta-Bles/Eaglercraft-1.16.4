package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class HugeMushroomFeature extends Feature<HugeMushroomFeatureConfig> {
   public HugeMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
      super(codec);
   }

   protected void generateStem(WorldAccess world, Random random, BlockPos pos, HugeMushroomFeatureConfig config, int height, BlockPos.Mutable arg4) {
      for (int j = 0; j < height; j++) {
         arg4.set(pos).move(Direction.UP, j);
         if (!world.getBlockState(arg4).isOpaqueFullCube(world, arg4)) {
            this.setBlockState(world, arg4, config.stemProvider.getBlockState(random, pos));
         }
      }
   }

   protected int getHeight(Random random) {
      int i = random.nextInt(3) + 4;
      if (random.nextInt(12) == 0) {
         i *= 2;
      }

      return i;
   }

   protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable arg3, HugeMushroomFeatureConfig config) {
      int j = pos.getY();
      if (j >= 1 && j + height + 1 < 256) {
         Block lv = world.getBlockState(pos.down()).getBlock();
         if (!isSoil(lv) && !lv.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return false;
         } else {
            for (int k = 0; k <= height; k++) {
               int l = this.getCapSize(-1, -1, config.foliageRadius, k);

               for (int m = -l; m <= l; m++) {
                  for (int n = -l; n <= l; n++) {
                     BlockState lv2 = world.getBlockState(arg3.set(pos, m, k, n));
                     if (!lv2.isAir() && !lv2.isIn(BlockTags.LEAVES)) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, HugeMushroomFeatureConfig arg4) {
      int i = this.getHeight(random);
      BlockPos.Mutable lv = new BlockPos.Mutable();
      if (!this.canGenerate(arg, arg3, i, lv, arg4)) {
         return false;
      } else {
         this.generateCap(arg, random, arg3, i, lv, arg4);
         this.generateStem(arg, random, arg3, arg4, i, lv);
         return true;
      }
   }

   protected abstract int getCapSize(int i, int j, int capSize, int y);

   protected abstract void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config);
}
