package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class ErodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

   public ErodedBadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

   @Override
   public void generate(
      Random random, Chunk arg, Biome arg2, int i, int j, int k, double d, BlockState arg3, BlockState arg4, int l, long m, TernarySurfaceConfig arg5
   ) {
      double e = 0.0;
      double f = Math.min(Math.abs(d), this.heightCutoffNoise.sample((double)i * 0.25, (double)j * 0.25, false) * 15.0);
      if (f > 0.0) {
         double g = 0.001953125;
         double h = Math.abs(this.heightNoise.sample((double)i * 0.001953125, (double)j * 0.001953125, false));
         e = f * f * 2.5;
         double n = Math.ceil(h * 50.0) + 14.0;
         if (e > n) {
            e = n;
         }

         e += 64.0;
      }

      int o = i & 15;
      int p = j & 15;
      BlockState lv = WHITE_TERRACOTTA;
      SurfaceConfig lv2 = arg2.getGenerationSettings().getSurfaceConfig();
      BlockState lv3 = lv2.getUnderMaterial();
      BlockState lv4 = lv2.getTopMaterial();
      BlockState lv5 = lv3;
      int q = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
      boolean bl = Math.cos(d / 3.0 * Math.PI) > 0.0;
      int r = -1;
      boolean bl2 = false;
      BlockPos.Mutable lv6 = new BlockPos.Mutable();

      for (int s = Math.max(k, (int)e + 1); s >= 0; s--) {
         lv6.set(o, s, p);
         if (arg.getBlockState(lv6).isAir() && s < (int)e) {
            arg.setBlockState(lv6, arg3, false);
         }

         BlockState lv7 = arg.getBlockState(lv6);
         if (lv7.isAir()) {
            r = -1;
         } else if (lv7.isOf(arg3.getBlock())) {
            if (r == -1) {
               bl2 = false;
               if (q <= 0) {
                  lv = Blocks.AIR.getDefaultState();
                  lv5 = arg3;
               } else if (s >= l - 4 && s <= l + 1) {
                  lv = WHITE_TERRACOTTA;
                  lv5 = lv3;
               }

               if (s < l && (lv == null || lv.isAir())) {
                  lv = arg4;
               }

               r = q + Math.max(0, s - l);
               if (s >= l - 1) {
                  if (s > l + 3 + q) {
                     BlockState lv8;
                     if (s < 64 || s > 127) {
                        lv8 = ORANGE_TERRACOTTA;
                     } else if (bl) {
                        lv8 = TERRACOTTA;
                     } else {
                        lv8 = this.calculateLayerBlockState(i, s, j);
                     }

                     arg.setBlockState(lv6, lv8, false);
                  } else {
                     arg.setBlockState(lv6, lv4, false);
                     bl2 = true;
                  }
               } else {
                  arg.setBlockState(lv6, lv5, false);
                  Block lv11 = lv5.getBlock();
                  if (lv11 == Blocks.WHITE_TERRACOTTA
                     || lv11 == Blocks.ORANGE_TERRACOTTA
                     || lv11 == Blocks.MAGENTA_TERRACOTTA
                     || lv11 == Blocks.LIGHT_BLUE_TERRACOTTA
                     || lv11 == Blocks.YELLOW_TERRACOTTA
                     || lv11 == Blocks.LIME_TERRACOTTA
                     || lv11 == Blocks.PINK_TERRACOTTA
                     || lv11 == Blocks.GRAY_TERRACOTTA
                     || lv11 == Blocks.LIGHT_GRAY_TERRACOTTA
                     || lv11 == Blocks.CYAN_TERRACOTTA
                     || lv11 == Blocks.PURPLE_TERRACOTTA
                     || lv11 == Blocks.BLUE_TERRACOTTA
                     || lv11 == Blocks.BROWN_TERRACOTTA
                     || lv11 == Blocks.GREEN_TERRACOTTA
                     || lv11 == Blocks.RED_TERRACOTTA
                     || lv11 == Blocks.BLACK_TERRACOTTA) {
                     arg.setBlockState(lv6, ORANGE_TERRACOTTA, false);
                  }
               }
            } else if (r > 0) {
               r--;
               if (bl2) {
                  arg.setBlockState(lv6, ORANGE_TERRACOTTA, false);
               } else {
                  arg.setBlockState(lv6, this.calculateLayerBlockState(i, s, j), false);
               }
            }
         }
      }
   }
}
