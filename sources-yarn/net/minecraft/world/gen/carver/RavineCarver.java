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

public class RavineCarver extends Carver<ProbabilityConfig> {
   private final float[] heightToHorizontalStretchFactor = new float[1024];

   public RavineCarver(Codec<ProbabilityConfig> configCodec) {
      super(configCodec, 256);
   }

   public boolean shouldCarve(Random random, int i, int j, ProbabilityConfig arg) {
      return random.nextFloat() <= arg.probability;
   }

   public boolean carve(Chunk arg, Function<BlockPos, Biome> function, Random random, int i, int j, int k, int l, int m, BitSet bitSet, ProbabilityConfig arg2) {
      int n = (this.getBranchFactor() * 2 - 1) * 16;
      double d = (double)(j * 16 + random.nextInt(16));
      double e = (double)(random.nextInt(random.nextInt(40) + 8) + 20);
      double f = (double)(k * 16 + random.nextInt(16));
      float g = random.nextFloat() * (float) (Math.PI * 2);
      float h = (random.nextFloat() - 0.5F) * 2.0F / 8.0F;
      double o = 3.0;
      float p = (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
      int q = n - random.nextInt(n / 4);
      int r = 0;
      this.carveRavine(arg, function, random.nextLong(), i, l, m, d, e, f, p, g, h, 0, q, 3.0, bitSet);
      return true;
   }

   private void carveRavine(
      Chunk chunk,
      Function<BlockPos, Biome> posToBiome,
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
      float q = 1.0F;

      for (int r = 0; r < 256; r++) {
         if (r == 0 || random.nextInt(3) == 0) {
            q = 1.0F + random.nextFloat() * random.nextFloat();
         }

         this.heightToHorizontalStretchFactor[r] = q * q;
      }

      float s = 0.0F;
      float t = 0.0F;

      for (int u = branchStartIndex; u < branchCount; u++) {
         double v = 1.5 + (double)(MathHelper.sin((float)u * (float) Math.PI / (float)branchCount) * width);
         double w = v * yawPitchRatio;
         v *= (double)random.nextFloat() * 0.25 + 0.75;
         w *= (double)random.nextFloat() * 0.25 + 0.75;
         float xx = MathHelper.cos(pitch);
         float yx = MathHelper.sin(pitch);
         x += (double)(MathHelper.cos(yaw) * xx);
         y += (double)yx;
         z += (double)(MathHelper.sin(yaw) * xx);
         pitch *= 0.7F;
         pitch += t * 0.05F;
         yaw += s * 0.05F;
         t *= 0.8F;
         s *= 0.5F;
         t += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         s += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (random.nextInt(4) != 0) {
            if (!this.canCarveBranch(mainChunkX, mainChunkZ, x, z, u, branchCount, width)) {
               return;
            }

            this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, v, w, carvingMask);
         }
      }
   }

   @Override
   protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
      return (scaledRelativeX * scaledRelativeX + scaledRelativeZ * scaledRelativeZ) * (double)this.heightToHorizontalStretchFactor[y - 1]
            + scaledRelativeY * scaledRelativeY / 6.0
         >= 1.0;
   }
}
