package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class NetherSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
   private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
   private static final BlockState GLOWSTONE = Blocks.SOUL_SAND.getDefaultState();
   protected long seed;
   protected OctavePerlinNoiseSampler noise;

   public NetherSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      int _snowmanxxxxxxxxxxxx = _snowman;
      int _snowmanxxxxxxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxxxxxxx = _snowman & 15;
      double _snowmanxxxxxxxxxxxxxxx = 0.03125;
      boolean _snowmanxxxxxxxxxxxxxxxx = this.noise.sample((double)_snowman * 0.03125, (double)_snowman * 0.03125, 0.0) * 75.0 + _snowman.nextDouble() > 0.0;
      boolean _snowmanxxxxxxxxxxxxxxxxx = this.noise.sample((double)_snowman * 0.03125, 109.0, (double)_snowman * 0.03125) * 75.0 + _snowman.nextDouble() > 0.0;
      int _snowmanxxxxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      BlockPos.Mutable _snowmanxxxxxxxxxxxxxxxxxxx = new BlockPos.Mutable();
      int _snowmanxxxxxxxxxxxxxxxxxxxx = -1;
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getTopMaterial();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderMaterial();

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 127; _snowmanxxxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxx--) {
         _snowmanxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.isAir()) {
            _snowmanxxxxxxxxxxxxxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.isOf(_snowman.getBlock())) {
            if (_snowmanxxxxxxxxxxxxxxxxxxxx == -1) {
               boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = false;
               if (_snowmanxxxxxxxxxxxxxxxxxx <= 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = true;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderMaterial();
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxx - 4 && _snowmanxxxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxx + 1) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getTopMaterial();
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderMaterial();
                  if (_snowmanxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxx = GRAVEL;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderMaterial();
                  }

                  if (_snowmanxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxx = GLOWSTONE;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxx = GLOWSTONE;
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman;
               }

               _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxx - 1) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, false);
               } else {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, false);
               }
            } else if (_snowmanxxxxxxxxxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxxxxxxxxxxx--;
               _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, false);
            }
         }
      }
   }

   @Override
   public void initSeed(long seed) {
      if (this.seed != seed || this.noise == null) {
         this.noise = new OctavePerlinNoiseSampler(new ChunkRandom(seed), IntStream.rangeClosed(-3, 0));
      }

      this.seed = seed;
   }
}
