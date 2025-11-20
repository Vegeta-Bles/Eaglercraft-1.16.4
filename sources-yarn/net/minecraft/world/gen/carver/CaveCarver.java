package net.minecraft.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;

public class CaveCarver extends Carver<ProbabilityConfig> {
   public CaveCarver(Codec<ProbabilityConfig> codec, int i) {
      super(codec, i);
   }

   public boolean shouldCarve(Random random, int i, int j, ProbabilityConfig arg) {
      return random.nextFloat() <= arg.probability;
   }

   public boolean carve(Chunk arg, Function<BlockPos, Biome> function, Random random, int i, int j, int k, int l, int m, BitSet bitSet, ProbabilityConfig arg2) {
      int n = (this.getBranchFactor() * 2 - 1) * 16;
      int o = random.nextInt(random.nextInt(random.nextInt(this.getMaxCaveCount()) + 1) + 1);

      for (int p = 0; p < o; p++) {
         double d = (double)(j * 16 + random.nextInt(16));
         double e = (double)this.getCaveY(random);
         double f = (double)(k * 16 + random.nextInt(16));
         int q = 1;
         if (random.nextInt(4) == 0) {
            double g = 0.5;
            float h = 1.0F + random.nextFloat() * 6.0F;
            this.carveCave(arg, function, random.nextLong(), i, l, m, d, e, f, h, 0.5, bitSet);
            q += random.nextInt(4);
         }

         for (int r = 0; r < q; r++) {
            float s = random.nextFloat() * (float) (Math.PI * 2);
            float t = (random.nextFloat() - 0.5F) / 4.0F;
            float u = this.getTunnelSystemWidth(random);
            int v = n - random.nextInt(n / 4);
            int w = 0;
            this.carveTunnels(arg, function, random.nextLong(), i, l, m, d, e, f, u, s, t, 0, v, this.getTunnelSystemHeightWidthRatio(), bitSet);
         }
      }

      return true;
   }

   protected int getMaxCaveCount() {
      return 15;
   }

   protected float getTunnelSystemWidth(Random random) {
      float f = random.nextFloat() * 2.0F + random.nextFloat();
      if (random.nextInt(10) == 0) {
         f *= random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
      }

      return f;
   }

   protected double getTunnelSystemHeightWidthRatio() {
      return 1.0;
   }

   protected int getCaveY(Random random) {
      return random.nextInt(random.nextInt(120) + 8);
   }

   protected void carveCave(
      Chunk chunk,
      Function<BlockPos, Biome> posToBiome,
      long seed,
      int seaLevel,
      int mainChunkX,
      int mainChunkZ,
      double x,
      double y,
      double z,
      float yaw,
      double yawPitchRatio,
      BitSet carvingMask
   ) {
      double m = 1.5 + (double)(MathHelper.sin((float) (Math.PI / 2)) * yaw);
      double n = m * yawPitchRatio;
      this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x + 1.0, y, z, m, n, carvingMask);
   }

   protected void carveTunnels(
      Chunk chunk,
      Function<BlockPos, Biome> postToBiome,
      long seed,
      int seaLevel,
      int mainChunkX,
      int mainChunkZ,
      double x,
      double y,
      double z,
      float width,
      float yaw,
      float pitch,
      int branchStartIndex,
      int branchCount,
      double yawPitchRatio,
      BitSet carvingMask
   ) {
      Random random = new Random(seed);
      int q = random.nextInt(branchCount / 2) + branchCount / 4;
      boolean bl = random.nextInt(6) == 0;
      float r = 0.0F;
      float s = 0.0F;

      for (int t = branchStartIndex; t < branchCount; t++) {
         double u = 1.5 + (double)(MathHelper.sin((float) Math.PI * (float)t / (float)branchCount) * width);
         double v = u * yawPitchRatio;
         float w = MathHelper.cos(pitch);
         x += (double)(MathHelper.cos(yaw) * w);
         y += (double)MathHelper.sin(pitch);
         z += (double)(MathHelper.sin(yaw) * w);
         pitch *= bl ? 0.92F : 0.7F;
         pitch += s * 0.1F;
         yaw += r * 0.1F;
         s *= 0.9F;
         r *= 0.75F;
         s += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         r += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (t == q && width > 1.0F) {
            this.carveTunnels(
               chunk,
               postToBiome,
               random.nextLong(),
               seaLevel,
               mainChunkX,
               mainChunkZ,
               x,
               y,
               z,
               random.nextFloat() * 0.5F + 0.5F,
               yaw - (float) (Math.PI / 2),
               pitch / 3.0F,
               t,
               branchCount,
               1.0,
               carvingMask
            );
            this.carveTunnels(
               chunk,
               postToBiome,
               random.nextLong(),
               seaLevel,
               mainChunkX,
               mainChunkZ,
               x,
               y,
               z,
               random.nextFloat() * 0.5F + 0.5F,
               yaw + (float) (Math.PI / 2),
               pitch / 3.0F,
               t,
               branchCount,
               1.0,
               carvingMask
            );
            return;
         }

         if (random.nextInt(4) != 0) {
            if (!this.canCarveBranch(mainChunkX, mainChunkZ, x, z, t, branchCount, width)) {
               return;
            }

            this.carveRegion(chunk, postToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, u, v, carvingMask);
         }
      }
   }

   @Override
   protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
      return scaledRelativeY <= -0.7 || scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 1.0;
   }
}
