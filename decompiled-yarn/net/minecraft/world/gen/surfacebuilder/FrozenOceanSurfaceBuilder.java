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

   public FrozenOceanSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      double _snowmanxxxxxxxxxxxx = 0.0;
      double _snowmanxxxxxxxxxxxxx = 0.0;
      BlockPos.Mutable _snowmanxxxxxxxxxxxxxx = new BlockPos.Mutable();
      float _snowmanxxxxxxxxxxxxxxx = _snowman.getTemperature(_snowmanxxxxxxxxxxxxxx.set(_snowman, 63, _snowman));
      double _snowmanxxxxxxxxxxxxxxxx = Math.min(Math.abs(_snowman), this.field_15644.sample((double)_snowman * 0.1, (double)_snowman * 0.1, false) * 15.0);
      if (_snowmanxxxxxxxxxxxxxxxx > 1.8) {
         double _snowmanxxxxxxxxxxxxxxxxx = 0.09765625;
         double _snowmanxxxxxxxxxxxxxxxxxx = Math.abs(this.field_15642.sample((double)_snowman * 0.09765625, (double)_snowman * 0.09765625, false));
         _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx * 1.2;
         double _snowmanxxxxxxxxxxxxxxxxxxx = Math.ceil(_snowmanxxxxxxxxxxxxxxxxxx * 40.0) + 14.0;
         if (_snowmanxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
         }

         if (_snowmanxxxxxxxxxxxxxxx > 0.1F) {
            _snowmanxxxxxxxxxxxx -= 2.0;
         }

         if (_snowmanxxxxxxxxxxxx > 2.0) {
            _snowmanxxxxxxxxxxxxx = (double)_snowman - _snowmanxxxxxxxxxxxx - 7.0;
            _snowmanxxxxxxxxxxxx += (double)_snowman;
         } else {
            _snowmanxxxxxxxxxxxx = 0.0;
         }
      }

      int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman & 15;
      SurfaceConfig _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getGenerationSettings().getSurfaceConfig();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.getUnderMaterial();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.getTopMaterial();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -1;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 2 + _snowman.nextInt(4);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman + 18 + _snowman.nextInt(10);

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(_snowman, (int)_snowmanxxxxxxxxxxxx + 1);
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx >= 0;
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx--
      ) {
         _snowmanxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx);
         if (_snowman.getBlockState(_snowmanxxxxxxxxxxxxxx).isAir() && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < (int)_snowmanxxxxxxxxxxxx && _snowman.nextDouble() > 0.01) {
            _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, PACKED_ICE, false);
         } else if (_snowman.getBlockState(_snowmanxxxxxxxxxxxxxx).getMaterial() == Material.WATER
            && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx > (int)_snowmanxxxxxxxxxxxxx
            && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman
            && _snowmanxxxxxxxxxxxxx != 0.0
            && _snowman.nextDouble() > 0.15) {
            _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, PACKED_ICE, false);
         }

         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isAir()) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isOf(_snowman.getBlock())) {
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == -1) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx <= 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = AIR;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman;
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx <= _snowman + 1) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman && (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx == null || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isAir())) {
                  if (_snowman.getTemperature(_snowmanxxxxxxxxxxxxxx.set(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman)) < 0.15F) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = ICE;
                  } else {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman;
                  }
               }

               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx >= _snowman - 1) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, false);
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman - 7 - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = AIR;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman;
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, GRAVEL, false);
               } else {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, false);
               }
            } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx--;
               _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, false);
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == 0 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.isOf(Blocks.SAND) && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx > 1) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.nextInt(4) + Math.max(0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - 63);
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.isOf(Blocks.RED_SAND)
                     ? Blocks.RED_SANDSTONE.getDefaultState()
                     : Blocks.SANDSTONE.getDefaultState();
               }
            }
         } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isOf(Blocks.PACKED_ICE)
            && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
            _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, SNOW_BLOCK, false);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++;
         }
      }
   }

   @Override
   public void initSeed(long seed) {
      if (this.seed != seed || this.field_15644 == null || this.field_15642 == null) {
         ChunkRandom _snowman = new ChunkRandom(seed);
         this.field_15644 = new OctaveSimplexNoiseSampler(_snowman, IntStream.rangeClosed(-3, 0));
         this.field_15642 = new OctaveSimplexNoiseSampler(_snowman, ImmutableList.of(0));
      }

      this.seed = seed;
   }
}
