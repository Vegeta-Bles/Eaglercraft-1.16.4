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

   public boolean shouldCarve(Random _snowman, int _snowman, int _snowman, ProbabilityConfig _snowman) {
      return _snowman.nextFloat() <= _snowman.probability;
   }

   public boolean carve(Chunk _snowman, Function<BlockPos, Biome> _snowman, Random _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, BitSet _snowman, ProbabilityConfig _snowman) {
      int _snowmanxxxxxxxxxx = (this.getBranchFactor() * 2 - 1) * 16;
      double _snowmanxxxxxxxxxxx = (double)(_snowman * 16 + _snowman.nextInt(16));
      double _snowmanxxxxxxxxxxxx = (double)(_snowman.nextInt(_snowman.nextInt(40) + 8) + 20);
      double _snowmanxxxxxxxxxxxxx = (double)(_snowman * 16 + _snowman.nextInt(16));
      float _snowmanxxxxxxxxxxxxxx = _snowman.nextFloat() * (float) (Math.PI * 2);
      float _snowmanxxxxxxxxxxxxxxx = (_snowman.nextFloat() - 0.5F) * 2.0F / 8.0F;
      double _snowmanxxxxxxxxxxxxxxxx = 3.0;
      float _snowmanxxxxxxxxxxxxxxxxx = (_snowman.nextFloat() * 2.0F + _snowman.nextFloat()) * 2.0F;
      int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx - _snowman.nextInt(_snowmanxxxxxxxxxx / 4);
      int _snowmanxxxxxxxxxxxxxxxxxxx = 0;
      this.carveRavine(
         _snowman,
         _snowman,
         _snowman.nextLong(),
         _snowman,
         _snowman,
         _snowman,
         _snowmanxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxx,
         0,
         _snowmanxxxxxxxxxxxxxxxxxx,
         3.0,
         _snowman
      );
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
      Random _snowman = new Random(seed);
      float _snowmanx = 1.0F;

      for (int _snowmanxx = 0; _snowmanxx < 256; _snowmanxx++) {
         if (_snowmanxx == 0 || _snowman.nextInt(3) == 0) {
            _snowmanx = 1.0F + _snowman.nextFloat() * _snowman.nextFloat();
         }

         this.heightToHorizontalStretchFactor[_snowmanxx] = _snowmanx * _snowmanx;
      }

      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;

      for (int _snowmanxxxx = branchStartIndex; _snowmanxxxx < branchCount; _snowmanxxxx++) {
         double _snowmanxxxxx = 1.5 + (double)(MathHelper.sin((float)_snowmanxxxx * (float) Math.PI / (float)branchCount) * width);
         double _snowmanxxxxxx = _snowmanxxxxx * yawPitchRatio;
         _snowmanxxxxx *= (double)_snowman.nextFloat() * 0.25 + 0.75;
         _snowmanxxxxxx *= (double)_snowman.nextFloat() * 0.25 + 0.75;
         float _snowmanxxxxxxx = MathHelper.cos(pitch);
         float _snowmanxxxxxxxx = MathHelper.sin(pitch);
         x += (double)(MathHelper.cos(yaw) * _snowmanxxxxxxx);
         y += (double)_snowmanxxxxxxxx;
         z += (double)(MathHelper.sin(yaw) * _snowmanxxxxxxx);
         pitch *= 0.7F;
         pitch += _snowmanxxx * 0.05F;
         yaw += _snowmanxx * 0.05F;
         _snowmanxxx *= 0.8F;
         _snowmanxx *= 0.5F;
         _snowmanxxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 2.0F;
         _snowmanxx += (_snowman.nextFloat() - _snowman.nextFloat()) * _snowman.nextFloat() * 4.0F;
         if (_snowman.nextInt(4) != 0) {
            if (!this.canCarveBranch(mainChunkX, mainChunkZ, x, z, _snowmanxxxx, branchCount, width)) {
               return;
            }

            this.carveRegion(chunk, posToBiome, seed, seaLevel, mainChunkX, mainChunkZ, x, y, z, _snowmanxxxxx, _snowmanxxxxxx, carvingMask);
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
