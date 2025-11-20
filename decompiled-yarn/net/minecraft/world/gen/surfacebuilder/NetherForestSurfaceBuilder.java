package net.minecraft.world.gen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public class NetherForestSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
   protected long seed;
   private OctavePerlinNoiseSampler surfaceNoise;

   public NetherForestSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      int _snowmanxxxxxxxxxxxx = _snowman;
      int _snowmanxxxxxxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxxxxxxx = _snowman & 15;
      double _snowmanxxxxxxxxxxxxxxx = this.surfaceNoise.sample((double)_snowman * 0.1, (double)_snowman, (double)_snowman * 0.1);
      boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx > 0.15 + _snowman.nextDouble() * 0.35;
      double _snowmanxxxxxxxxxxxxxxxxx = this.surfaceNoise.sample((double)_snowman * 0.1, 109.0, (double)_snowman * 0.1);
      boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx > 0.25 + _snowman.nextDouble() * 0.9;
      int _snowmanxxxxxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      BlockPos.Mutable _snowmanxxxxxxxxxxxxxxxxxxxx = new BlockPos.Mutable();
      int _snowmanxxxxxxxxxxxxxxxxxxxxx = -1;
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderMaterial();

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 127; _snowmanxxxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxx--) {
         _snowmanxxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getTopMaterial();
         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.isAir()) {
            _snowmanxxxxxxxxxxxxxxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.isOf(_snowman.getBlock())) {
            if (_snowmanxxxxxxxxxxxxxxxxxxxxx == -1) {
               boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = false;
               if (_snowmanxxxxxxxxxxxxxxxxxxx <= 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = true;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderMaterial();
               }

               if (_snowmanxxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderMaterial();
               } else if (_snowmanxxxxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getUnderwaterMaterial();
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman;
               }

               _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxx - 1) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, false);
               } else {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, false);
               }
            } else if (_snowmanxxxxxxxxxxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxxxxxxxxxxxx--;
               _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, false);
            }
         }
      }
   }

   @Override
   public void initSeed(long seed) {
      if (this.seed != seed || this.surfaceNoise == null) {
         this.surfaceNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed), ImmutableList.of(0));
      }

      this.seed = seed;
   }
}
