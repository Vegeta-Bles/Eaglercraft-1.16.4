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
   public CaveCarver(Codec<ProbabilityConfig> _snowman, int _snowman) {
      super(_snowman, _snowman);
   }

   public boolean shouldCarve(Random _snowman, int _snowman, int _snowman, ProbabilityConfig _snowman) {
      return _snowman.nextFloat() <= _snowman.probability;
   }

   public boolean carve(Chunk _snowman, Function<BlockPos, Biome> _snowman, Random _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, BitSet _snowman, ProbabilityConfig _snowman) {
      int _snowmanxxxxxxxxxx = (this.getBranchFactor() * 2 - 1) * 16;
      int _snowmanxxxxxxxxxxx = _snowman.nextInt(_snowman.nextInt(_snowman.nextInt(this.getMaxCaveCount()) + 1) + 1);

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
         double _snowmanxxxxxxxxxxxxx = (double)(_snowman * 16 + _snowman.nextInt(16));
         double _snowmanxxxxxxxxxxxxxx = (double)this.getCaveY(_snowman);
         double _snowmanxxxxxxxxxxxxxxx = (double)(_snowman * 16 + _snowman.nextInt(16));
         int _snowmanxxxxxxxxxxxxxxxx = 1;
         if (_snowman.nextInt(4) == 0) {
            double _snowmanxxxxxxxxxxxxxxxxx = 0.5;
            float _snowmanxxxxxxxxxxxxxxxxxx = 1.0F + _snowman.nextFloat() * 6.0F;
            this.carveCave(_snowman, _snowman, _snowman.nextLong(), _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.5, _snowman);
            _snowmanxxxxxxxxxxxxxxxx += _snowman.nextInt(4);
         }

         for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
            float _snowmanxxxxxxxxxxxxxxxxxx = _snowman.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxxxxxxxxxxxxxxxxxx = (_snowman.nextFloat() - 0.5F) / 4.0F;
            float _snowmanxxxxxxxxxxxxxxxxxxxx = this.getTunnelSystemWidth(_snowman);
            int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx - _snowman.nextInt(_snowmanxxxxxxxxxx / 4);
            int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0;
            this.carveTunnels(
               _snowman,
               _snowman,
               _snowman.nextLong(),
               _snowman,
               _snowman,
               _snowman,
               _snowmanxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxx,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               this.getTunnelSystemHeightWidthRatio(),
               _snowman
            );
         }
      }

      return true;
   }

   protected int getMaxCaveCount() {
      return 15;
   }

   protected float getTunnelSystemWidth(Random random) {
      float _snowman = random.nextFloat() * 2.0F + random.nextFloat();
      if (random.nextInt(10) == 0) {
         _snowman *= random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
      }

      return _snowman;
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
      double _snowman = 1.5 + (double)(MathHelper.sin((float) (Math.PI / 2)) * yaw);
      double _snowmanx = _snowman * yawPitchRatio;
      this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x + 1.0, y, z, _snowman, _snowmanx, carvingMask);
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
      Random _snowman = new Random(seed);
      int _snowmanx = _snowman.nextInt(branchCount / 2) + branchCount / 4;
      boolean _snowmanxx = _snowman.nextInt(6) == 0;
      float _snowmanxxx = 0.0F;
      float _snowmanxxxx = 0.0F;

      for (int _snowmanxxxxx = branchStartIndex; _snowmanxxxxx < branchCount; _snowmanxxxxx++) {
         double _snowmanxxxxxx = 1.5 + (double)(MathHelper.sin((float) Math.PI * (float)_snowmanxxxxx / (float)branchCount) * width);
         double _snowmanxxxxxxx = _snowmanxxxxxx * yawPitchRatio;
         float _snowmanxxxxxxxx = MathHelper.cos(pitch);
         x += (double)(MathHelper.cos(yaw) * _snowmanxxxxxxxx);
         y += (double)MathHelper.sin(pitch);
         z += (double)(MathHelper.sin(yaw) * _snowmanxxxxxxxx);
         pitch *= _snowmanxx ? 0.92F : 0.7F;
         pitch += _snowmanxxxx * 0.1F;
         yaw += _snowmanxxx * 0.1F;
         _snowmanxxxx *= 0.9F;
         _snowmanxxx *= 0.75F;
         _snowmanxxxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 2.0F;
         _snowmanxxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 4.0F;
         if (_snowmanxxxxx == _snowmanx && width > 1.0F) {
            this.carveTunnels(
               chunk,
               postToBiome,
               _snowman.nextLong(),
               seaLevel,
               mainChunkX,
               mainChunkZ,
               x,
               y,
               z,
               _snowman.nextFloat() * 0.5F + 0.5F,
               yaw - (float) (Math.PI / 2),
               pitch / 3.0F,
               _snowmanxxxxx,
               branchCount,
               1.0,
               carvingMask
            );
            this.carveTunnels(
               chunk,
               postToBiome,
               _snowman.nextLong(),
               seaLevel,
               mainChunkX,
               mainChunkZ,
               x,
               y,
               z,
               _snowman.nextFloat() * 0.5F + 0.5F,
               yaw + (float) (Math.PI / 2),
               pitch / 3.0F,
               _snowmanxxxxx,
               branchCount,
               1.0,
               carvingMask
            );
            return;
         }

         if (_snowman.nextInt(4) != 0) {
            if (!this.canCarveBranch(mainChunkX, mainChunkZ, x, z, _snowmanxxxxx, branchCount, width)) {
               return;
            }

            this.carveRegion(chunk, postToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, _snowmanxxxxxx, _snowmanxxxxxxx, carvingMask);
         }
      }
   }

   @Override
   protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
      return scaledRelativeY <= -0.7 || scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 1.0;
   }
}
