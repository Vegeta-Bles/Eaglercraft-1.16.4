package net.minecraft.world.gen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class FrozenOceanSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   protected static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
   protected static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
   private static final BlockState AIR = Blocks.AIR.getDefaultState();
   private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
   private static final BlockState ICE = Blocks.ICE.getDefaultState();
   private OctaveSimplexNoiseSampler field_15644;
   private OctaveSimplexNoiseSampler field_15642;
   private long seed;

   public FrozenOceanSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

   public void generate(
      Random random, Chunk arg, Biome arg2, int i, int j, int k, double d, BlockState arg3, BlockState arg4, int l, long m, TernarySurfaceConfig arg5
   ) {
      double e = 0.0;
      double f = 0.0;
      BlockPos.Mutable lv = new BlockPos.Mutable();
      float g = arg2.getTemperature(lv.set(i, 63, j));
      double h = Math.min(Math.abs(d), this.field_15644.sample((double)i * 0.1, (double)j * 0.1, false) * 15.0);
      if (h > 1.8) {
         double n = 0.09765625;
         double o = Math.abs(this.field_15642.sample((double)i * 0.09765625, (double)j * 0.09765625, false));
         e = h * h * 1.2;
         double p = Math.ceil(o * 40.0) + 14.0;
         if (e > p) {
            e = p;
         }

         if (g > 0.1F) {
            e -= 2.0;
         }

         if (e > 2.0) {
            f = (double)l - e - 7.0;
            e += (double)l;
         } else {
            e = 0.0;
         }
      }

      int q = i & 15;
      int r = j & 15;
      SurfaceConfig lv2 = arg2.getGenerationSettings().getSurfaceConfig();
      BlockState lv3 = lv2.getUnderMaterial();
      BlockState lv4 = lv2.getTopMaterial();
      BlockState lv5 = lv3;
      BlockState lv6 = lv4;
      int s = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
      int t = -1;
      int u = 0;
      int v = 2 + random.nextInt(4);
      int w = l + 18 + random.nextInt(10);

      for (int x = Math.max(k, (int)e + 1); x >= 0; x--) {
         lv.set(q, x, r);
         if (arg.getBlockState(lv).isAir() && x < (int)e && random.nextDouble() > 0.01) {
            arg.setBlockState(lv, PACKED_ICE, false);
         } else if (arg.getBlockState(lv).getMaterial() == Material.WATER && x > (int)f && x < l && f != 0.0 && random.nextDouble() > 0.15) {
            arg.setBlockState(lv, PACKED_ICE, false);
         }

         BlockState lv7 = arg.getBlockState(lv);
         if (lv7.isAir()) {
            t = -1;
         } else if (lv7.isOf(arg3.getBlock())) {
            if (t == -1) {
               if (s <= 0) {
                  lv6 = AIR;
                  lv5 = arg3;
               } else if (x >= l - 4 && x <= l + 1) {
                  lv6 = lv4;
                  lv5 = lv3;
               }

               if (x < l && (lv6 == null || lv6.isAir())) {
                  if (arg2.getTemperature(lv.set(i, x, j)) < 0.15F) {
                     lv6 = ICE;
                  } else {
                     lv6 = arg4;
                  }
               }

               t = s;
               if (x >= l - 1) {
                  arg.setBlockState(lv, lv6, false);
               } else if (x < l - 7 - s) {
                  lv6 = AIR;
                  lv5 = arg3;
                  arg.setBlockState(lv, GRAVEL, false);
               } else {
                  arg.setBlockState(lv, lv5, false);
               }
            } else if (t > 0) {
               t--;
               arg.setBlockState(lv, lv5, false);
               if (t == 0 && lv5.isOf(Blocks.SAND) && s > 1) {
                  t = random.nextInt(4) + Math.max(0, x - 63);
                  lv5 = lv5.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
               }
            }
         } else if (lv7.isOf(Blocks.PACKED_ICE) && u <= v && x > w) {
            arg.setBlockState(lv, SNOW_BLOCK, false);
            u++;
         }
      }
   }

   @Override
   public void initSeed(long seed) {
      if (this.seed != seed || this.field_15644 == null || this.field_15642 == null) {
         ChunkRandom lv = new ChunkRandom(seed);
         this.field_15644 = new OctaveSimplexNoiseSampler(lv, IntStream.rangeClosed(-3, 0));
         this.field_15642 = new OctaveSimplexNoiseSampler(lv, ImmutableList.of(0));
      }

      this.seed = seed;
   }
}
