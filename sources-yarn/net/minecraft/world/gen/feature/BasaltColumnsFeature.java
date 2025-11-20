package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BasaltColumnsFeature extends Feature<BasaltColumnsFeatureConfig> {
   private static final ImmutableList<Block> field_24132 = ImmutableList.of(
      Blocks.LAVA,
      Blocks.BEDROCK,
      Blocks.MAGMA_BLOCK,
      Blocks.SOUL_SAND,
      Blocks.NETHER_BRICKS,
      Blocks.NETHER_BRICK_FENCE,
      Blocks.NETHER_BRICK_STAIRS,
      Blocks.NETHER_WART,
      Blocks.CHEST,
      Blocks.SPAWNER
   );

   public BasaltColumnsFeature(Codec<BasaltColumnsFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, BasaltColumnsFeatureConfig arg4) {
      int i = arg2.getSeaLevel();
      if (!method_30379(arg, i, arg3.mutableCopy())) {
         return false;
      } else {
         int j = arg4.getHeight().getValue(random);
         boolean bl = random.nextFloat() < 0.9F;
         int k = Math.min(j, bl ? 5 : 8);
         int l = bl ? 50 : 15;
         boolean bl2 = false;

         for (BlockPos lv : BlockPos.iterateRandomly(random, l, arg3.getX() - k, arg3.getY(), arg3.getZ() - k, arg3.getX() + k, arg3.getY(), arg3.getZ() + k)) {
            int m = j - lv.getManhattanDistance(arg3);
            if (m >= 0) {
               bl2 |= this.method_27096(arg, i, lv, m, arg4.getReach().getValue(random));
            }
         }

         return bl2;
      }
   }

   private boolean method_27096(WorldAccess arg, int i, BlockPos arg2, int j, int k) {
      boolean bl = false;

      for (BlockPos lv : BlockPos.iterate(arg2.getX() - k, arg2.getY(), arg2.getZ() - k, arg2.getX() + k, arg2.getY(), arg2.getZ() + k)) {
         int l = lv.getManhattanDistance(arg2);
         BlockPos lv2 = method_27095(arg, i, lv) ? method_27094(arg, i, lv.mutableCopy(), l) : method_27098(arg, lv.mutableCopy(), l);
         if (lv2 != null) {
            int m = j - l / 2;

            for (BlockPos.Mutable lv3 = lv2.mutableCopy(); m >= 0; m--) {
               if (method_27095(arg, i, lv3)) {
                  this.setBlockState(arg, lv3, Blocks.BASALT.getDefaultState());
                  lv3.move(Direction.UP);
                  bl = true;
               } else {
                  if (!arg.getBlockState(lv3).isOf(Blocks.BASALT)) {
                     break;
                  }

                  lv3.move(Direction.UP);
               }
            }
         }
      }

      return bl;
   }

   @Nullable
   private static BlockPos method_27094(WorldAccess arg, int i, BlockPos.Mutable arg2, int j) {
      while (arg2.getY() > 1 && j > 0) {
         j--;
         if (method_30379(arg, i, arg2)) {
            return arg2;
         }

         arg2.move(Direction.DOWN);
      }

      return null;
   }

   private static boolean method_30379(WorldAccess arg, int i, BlockPos.Mutable arg2) {
      if (!method_27095(arg, i, arg2)) {
         return false;
      } else {
         BlockState lv = arg.getBlockState(arg2.move(Direction.DOWN));
         arg2.move(Direction.UP);
         return !lv.isAir() && !field_24132.contains(lv.getBlock());
      }
   }

   @Nullable
   private static BlockPos method_27098(WorldAccess arg, BlockPos.Mutable arg2, int i) {
      while (arg2.getY() < arg.getHeight() && i > 0) {
         i--;
         BlockState lv = arg.getBlockState(arg2);
         if (field_24132.contains(lv.getBlock())) {
            return null;
         }

         if (lv.isAir()) {
            return arg2;
         }

         arg2.move(Direction.UP);
      }

      return null;
   }

   private static boolean method_27095(WorldAccess arg, int i, BlockPos arg2) {
      BlockState lv = arg.getBlockState(arg2);
      return lv.isAir() || lv.isOf(Blocks.LAVA) && arg2.getY() <= i;
   }
}
