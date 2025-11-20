package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class KelpFeature extends Feature<DefaultFeatureConfig> {
   public KelpFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DefaultFeatureConfig arg4) {
      int i = 0;
      int j = arg.getTopY(Heightmap.Type.OCEAN_FLOOR, arg3.getX(), arg3.getZ());
      BlockPos lv = new BlockPos(arg3.getX(), j, arg3.getZ());
      if (arg.getBlockState(lv).isOf(Blocks.WATER)) {
         BlockState lv2 = Blocks.KELP.getDefaultState();
         BlockState lv3 = Blocks.KELP_PLANT.getDefaultState();
         int k = 1 + random.nextInt(10);

         for (int l = 0; l <= k; l++) {
            if (arg.getBlockState(lv).isOf(Blocks.WATER) && arg.getBlockState(lv.up()).isOf(Blocks.WATER) && lv3.canPlaceAt(arg, lv)) {
               if (l == k) {
                  arg.setBlockState(lv, lv2.with(KelpBlock.AGE, Integer.valueOf(random.nextInt(4) + 20)), 2);
                  i++;
               } else {
                  arg.setBlockState(lv, lv3, 2);
               }
            } else if (l > 0) {
               BlockPos lv4 = lv.down();
               if (lv2.canPlaceAt(arg, lv4) && !arg.getBlockState(lv4.down()).isOf(Blocks.KELP)) {
                  arg.setBlockState(lv4, lv2.with(KelpBlock.AGE, Integer.valueOf(random.nextInt(4) + 20)), 2);
                  i++;
               }
               break;
            }

            lv = lv.up();
         }
      }

      return i > 0;
   }
}
