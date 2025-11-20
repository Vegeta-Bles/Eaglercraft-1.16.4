package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherForestVegetationFeature extends Feature<BlockPileFeatureConfig> {
   public NetherForestVegetationFeature(Codec<BlockPileFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, BlockPileFeatureConfig arg4) {
      return generate(arg, random, arg3, arg4, 8, 4);
   }

   public static boolean generate(WorldAccess world, Random random, BlockPos pos, BlockPileFeatureConfig config, int i, int j) {
      Block lv = world.getBlockState(pos.down()).getBlock();
      if (!lv.isIn(BlockTags.NYLIUM)) {
         return false;
      } else {
         int k = pos.getY();
         if (k >= 1 && k + 1 < 256) {
            int l = 0;

            for (int m = 0; m < i * i; m++) {
               BlockPos lv2 = pos.add(random.nextInt(i) - random.nextInt(i), random.nextInt(j) - random.nextInt(j), random.nextInt(i) - random.nextInt(i));
               BlockState lv3 = config.stateProvider.getBlockState(random, lv2);
               if (world.isAir(lv2) && lv2.getY() > 0 && lv3.canPlaceAt(world, lv2)) {
                  world.setBlockState(lv2, lv3, 2);
                  l++;
               }
            }

            return l > 0;
         } else {
            return false;
         }
      }
   }
}
