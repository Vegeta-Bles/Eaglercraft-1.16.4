package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ForestRockFeature extends Feature<SingleStateFeatureConfig> {
   public ForestRockFeature(Codec<SingleStateFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, SingleStateFeatureConfig arg4) {
      while (arg3.getY() > 3) {
         if (!arg.isAir(arg3.down())) {
            Block lv = arg.getBlockState(arg3.down()).getBlock();
            if (isSoil(lv) || isStone(lv)) {
               break;
            }
         }

         arg3 = arg3.down();
      }

      if (arg3.getY() <= 3) {
         return false;
      } else {
         for (int i = 0; i < 3; i++) {
            int j = random.nextInt(2);
            int k = random.nextInt(2);
            int l = random.nextInt(2);
            float f = (float)(j + k + l) * 0.333F + 0.5F;

            for (BlockPos lv2 : BlockPos.iterate(arg3.add(-j, -k, -l), arg3.add(j, k, l))) {
               if (lv2.getSquaredDistance(arg3) <= (double)(f * f)) {
                  arg.setBlockState(lv2, arg4.state, 4);
               }
            }

            arg3 = arg3.add(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
         }

         return true;
      }
   }
}
