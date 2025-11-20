package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SeagrassFeature extends Feature<ProbabilityConfig> {
   public SeagrassFeature(Codec<ProbabilityConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, ProbabilityConfig arg4) {
      boolean bl = false;
      int i = random.nextInt(8) - random.nextInt(8);
      int j = random.nextInt(8) - random.nextInt(8);
      int k = arg.getTopY(Heightmap.Type.OCEAN_FLOOR, arg3.getX() + i, arg3.getZ() + j);
      BlockPos lv = new BlockPos(arg3.getX() + i, k, arg3.getZ() + j);
      if (arg.getBlockState(lv).isOf(Blocks.WATER)) {
         boolean bl2 = random.nextDouble() < (double)arg4.probability;
         BlockState lv2 = bl2 ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
         if (lv2.canPlaceAt(arg, lv)) {
            if (bl2) {
               BlockState lv3 = lv2.with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
               BlockPos lv4 = lv.up();
               if (arg.getBlockState(lv4).isOf(Blocks.WATER)) {
                  arg.setBlockState(lv, lv2, 2);
                  arg.setBlockState(lv4, lv3, 2);
               }
            } else {
               arg.setBlockState(lv, lv2, 2);
            }

            bl = true;
         }
      }

      return bl;
   }
}
