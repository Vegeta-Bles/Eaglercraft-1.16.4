package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SeaPickleFeature extends Feature<CountConfig> {
   public SeaPickleFeature(Codec<CountConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, CountConfig arg4) {
      int i = 0;
      int j = arg4.getCount().getValue(random);

      for (int k = 0; k < j; k++) {
         int l = random.nextInt(8) - random.nextInt(8);
         int m = random.nextInt(8) - random.nextInt(8);
         int n = arg.getTopY(Heightmap.Type.OCEAN_FLOOR, arg3.getX() + l, arg3.getZ() + m);
         BlockPos lv = new BlockPos(arg3.getX() + l, n, arg3.getZ() + m);
         BlockState lv2 = Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(random.nextInt(4) + 1));
         if (arg.getBlockState(lv).isOf(Blocks.WATER) && lv2.canPlaceAt(arg, lv)) {
            arg.setBlockState(lv, lv2, 2);
            i++;
         }
      }

      return i > 0;
   }
}
