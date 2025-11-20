package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class SwampSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   public SwampSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

   public void generate(
      Random random, Chunk arg, Biome arg2, int i, int j, int k, double d, BlockState arg3, BlockState arg4, int l, long m, TernarySurfaceConfig arg5
   ) {
      double e = Biome.FOLIAGE_NOISE.sample((double)i * 0.25, (double)j * 0.25, false);
      if (e > 0.0) {
         int n = i & 15;
         int o = j & 15;
         BlockPos.Mutable lv = new BlockPos.Mutable();

         for (int p = k; p >= 0; p--) {
            lv.set(n, p, o);
            if (!arg.getBlockState(lv).isAir()) {
               if (p == 62 && !arg.getBlockState(lv).isOf(arg4.getBlock())) {
                  arg.setBlockState(lv, arg4, false);
               }
               break;
            }
         }
      }

      SurfaceBuilder.DEFAULT.generate(random, arg, arg2, i, j, k, d, arg3, arg4, l, m, arg5);
   }
}
