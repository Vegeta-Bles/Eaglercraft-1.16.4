package net.minecraft.world.gen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class BadlandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();
   private static final BlockState YELLOW_TERRACOTTA = Blocks.YELLOW_TERRACOTTA.getDefaultState();
   private static final BlockState BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.getDefaultState();
   private static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.getDefaultState();
   private static final BlockState LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
   protected BlockState[] layerBlocks;
   protected long seed;
   protected OctaveSimplexNoiseSampler heightCutoffNoise;
   protected OctaveSimplexNoiseSampler heightNoise;
   protected OctaveSimplexNoiseSampler layerNoise;

   public BadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

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
                  if (s >= l - 1) {
                     if (s <= l + 3 + p) {
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

   @Override
   public void initSeed(long seed) {
      if (this.seed != seed || this.layerBlocks == null) {
         this.initLayerBlocks(seed);
      }

      if (this.seed != seed || this.heightCutoffNoise == null || this.heightNoise == null) {
         ChunkRandom lv = new ChunkRandom(seed);
         this.heightCutoffNoise = new OctaveSimplexNoiseSampler(lv, IntStream.rangeClosed(-3, 0));
         this.heightNoise = new OctaveSimplexNoiseSampler(lv, ImmutableList.of(0));
      }

      this.seed = seed;
   }

   protected void initLayerBlocks(long seed) {
      this.layerBlocks = new BlockState[64];
      Arrays.fill(this.layerBlocks, TERRACOTTA);
      ChunkRandom lv = new ChunkRandom(seed);
      this.layerNoise = new OctaveSimplexNoiseSampler(lv, ImmutableList.of(0));

      for (int i = 0; i < 64; i++) {
         i += lv.nextInt(5) + 1;
         if (i < 64) {
            this.layerBlocks[i] = ORANGE_TERRACOTTA;
         }
      }

      int j = lv.nextInt(4) + 2;

      for (int k = 0; k < j; k++) {
         int m = lv.nextInt(3) + 1;
         int n = lv.nextInt(64);

         for (int o = 0; n + o < 64 && o < m; o++) {
            this.layerBlocks[n + o] = YELLOW_TERRACOTTA;
         }
      }

      int p = lv.nextInt(4) + 2;

      for (int q = 0; q < p; q++) {
         int r = lv.nextInt(3) + 2;
         int s = lv.nextInt(64);

         for (int t = 0; s + t < 64 && t < r; t++) {
            this.layerBlocks[s + t] = BROWN_TERRACOTTA;
         }
      }

      int u = lv.nextInt(4) + 2;

      for (int v = 0; v < u; v++) {
         int w = lv.nextInt(3) + 1;
         int x = lv.nextInt(64);

         for (int y = 0; x + y < 64 && y < w; y++) {
            this.layerBlocks[x + y] = RED_TERRACOTTA;
         }
      }

      int z = lv.nextInt(3) + 3;
      int aa = 0;

      for (int ab = 0; ab < z; ab++) {
         int ac = 1;
         aa += lv.nextInt(16) + 4;

         for (int ad = 0; aa + ad < 64 && ad < 1; ad++) {
            this.layerBlocks[aa + ad] = WHITE_TERRACOTTA;
            if (aa + ad > 1 && lv.nextBoolean()) {
               this.layerBlocks[aa + ad - 1] = LIGHT_GRAY_TERRACOTTA;
            }

            if (aa + ad < 63 && lv.nextBoolean()) {
               this.layerBlocks[aa + ad + 1] = LIGHT_GRAY_TERRACOTTA;
            }
         }
      }
   }

   protected BlockState calculateLayerBlockState(int x, int y, int z) {
      int l = (int)Math.round(this.layerNoise.sample((double)x / 512.0, (double)z / 512.0, false) * 2.0);
      return this.layerBlocks[(y + l + 64) % 64];
   }
}
