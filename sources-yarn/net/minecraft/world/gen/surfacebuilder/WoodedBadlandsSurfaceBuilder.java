package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class WoodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

   public WoodedBadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

   @Override
   public void generate(
      Random random, Chunk arg, Biome arg2, int i, int j, int k, double d, BlockState arg3, BlockState arg4, int l, long m, TernarySurfaceConfig arg5
   ) {
      int n = i & 15;
      int o = j & 15;
      BlockState lv = WHITE_TERRACOTTA;
      SurfaceConfig lv2 = arg2.getGenerationSettings().getSurfaceConfig();
      BlockState lv3 = lv2.getUnderMaterial();
      BlockState lv4 = lv2.getTopMaterial();
      BlockState lv5 = lv3;
      int p = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
      boolean bl = Math.cos(d / 3.0 * Math.PI) > 0.0;
      int q = -1;
      boolean bl2 = false;
      int r = 0;
      BlockPos.Mutable lv6 = new BlockPos.Mutable();

      for (int s = k; s >= 0; s--) {
         if (r < 15) {
            lv6.set(n, s, o);
            BlockState lv7 = arg.getBlockState(lv6);
            if (lv7.isAir()) {
               q = -1;
            } else if (lv7.isOf(arg3.getBlock())) {
               if (q == -1) {
                  bl2 = false;
                  if (p <= 0) {
                     lv = Blocks.AIR.getDefaultState();
                     lv5 = arg3;
                  } else if (s >= l - 4 && s <= l + 1) {
                     lv = WHITE_TERRACOTTA;
                     lv5 = lv3;
                  }

                  if (s < l && (lv == null || lv.isAir())) {
                     lv = arg4;
                  }

                  q = p + Math.max(0, s - l);
                  if (s < l - 1) {
                     arg.setBlockState(lv6, lv5, false);
                     if (lv5 == WHITE_TERRACOTTA) {
                        arg.setBlockState(lv6, ORANGE_TERRACOTTA, false);
                     }
                  } else if (s > 86 + p * 2) {
                     if (bl) {
                        arg.setBlockState(lv6, Blocks.COARSE_DIRT.getDefaultState(), false);
                     } else {
                        arg.setBlockState(lv6, Blocks.GRASS_BLOCK.getDefaultState(), false);
                     }
                  } else if (s <= l + 3 + p) {
                     arg.setBlockState(lv6, lv4, false);
                     bl2 = true;
                  } else {
                     BlockState lv8;
                     if (s < 64 || s > 127) {
                        lv8 = ORANGE_TERRACOTTA;
                     } else if (bl) {
                        lv8 = TERRACOTTA;
                     } else {
                        lv8 = this.calculateLayerBlockState(i, s, j);
                     }

                     arg.setBlockState(lv6, lv8, false);
                  }
               } else if (q > 0) {
                  q--;
                  if (bl2) {
                     arg.setBlockState(lv6, ORANGE_TERRACOTTA, false);
                  } else {
                     arg.setBlockState(lv6, this.calculateLayerBlockState(i, s, j), false);
                  }
               }

               r++;
            }
         }
      }
   }
}
