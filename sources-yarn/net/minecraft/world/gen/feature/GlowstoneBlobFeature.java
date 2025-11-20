package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class GlowstoneBlobFeature extends Feature<DefaultFeatureConfig> {
   public GlowstoneBlobFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DefaultFeatureConfig arg4) {
      if (!arg.isAir(arg3)) {
         return false;
      } else {
         BlockState lv = arg.getBlockState(arg3.up());
         if (!lv.isOf(Blocks.NETHERRACK) && !lv.isOf(Blocks.BASALT) && !lv.isOf(Blocks.BLACKSTONE)) {
            return false;
         } else {
            arg.setBlockState(arg3, Blocks.GLOWSTONE.getDefaultState(), 2);

            for (int i = 0; i < 1500; i++) {
               BlockPos lv2 = arg3.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
               if (arg.getBlockState(lv2).isAir()) {
                  int j = 0;

                  for (Direction lv3 : Direction.values()) {
                     if (arg.getBlockState(lv2.offset(lv3)).isOf(Blocks.GLOWSTONE)) {
                        j++;
                     }

                     if (j > 1) {
                        break;
                     }
                  }

                  if (j == 1) {
                     arg.setBlockState(lv2, Blocks.GLOWSTONE.getDefaultState(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}
