package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class DefaultSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   public DefaultSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

   public void generate(
      Random random, Chunk arg, Biome arg2, int i, int j, int k, double d, BlockState arg3, BlockState arg4, int l, long m, TernarySurfaceConfig arg5
   ) {
      this.generate(random, arg, arg2, i, j, k, d, arg3, arg4, arg5.getTopMaterial(), arg5.getUnderMaterial(), arg5.getUnderwaterMaterial(), l);
   }

   protected void generate(
      Random random,
      Chunk chunk,
      Biome biome,
      int x,
      int z,
      int height,
      double noise,
      BlockState defaultBlock,
      BlockState fluidBlock,
      BlockState topBlock,
      BlockState underBlock,
      BlockState underwaterBlock,
      int seaLevel
   ) {
      BlockState lv = topBlock;
      BlockState lv2 = underBlock;
      BlockPos.Mutable lv3 = new BlockPos.Mutable();
      int m = -1;
      int n = (int)(noise / 3.0 + 3.0 + random.nextDouble() * 0.25);
      int o = x & 15;
      int p = z & 15;

      for (int q = height; q >= 0; q--) {
         lv3.set(o, q, p);
         BlockState lv4 = chunk.getBlockState(lv3);
         if (lv4.isAir()) {
            m = -1;
         } else if (lv4.isOf(defaultBlock.getBlock())) {
            if (m == -1) {
               if (n <= 0) {
                  lv = Blocks.AIR.getDefaultState();
                  lv2 = defaultBlock;
               } else if (q >= seaLevel - 4 && q <= seaLevel + 1) {
                  lv = topBlock;
                  lv2 = underBlock;
               }

               if (q < seaLevel && (lv == null || lv.isAir())) {
                  if (biome.getTemperature(lv3.set(x, q, z)) < 0.15F) {
                     lv = Blocks.ICE.getDefaultState();
                  } else {
                     lv = fluidBlock;
                  }

                  lv3.set(o, q, p);
               }

               m = n;
               if (q >= seaLevel - 1) {
                  chunk.setBlockState(lv3, lv, false);
               } else if (q < seaLevel - 7 - n) {
                  lv = Blocks.AIR.getDefaultState();
                  lv2 = defaultBlock;
                  chunk.setBlockState(lv3, underwaterBlock, false);
               } else {
                  chunk.setBlockState(lv3, lv2, false);
               }
            } else if (m > 0) {
               m--;
               chunk.setBlockState(lv3, lv2, false);
               if (m == 0 && lv2.isOf(Blocks.SAND) && n > 1) {
                  m = random.nextInt(4) + Math.max(0, q - 63);
                  lv2 = lv2.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
               }
            }
         }
      }
   }
}
