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

   public BadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      int _snowmanxxxxxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxxxxxx = _snowman & 15;
      BlockState _snowmanxxxxxxxxxxxxxx = WHITE_TERRACOTTA;
      SurfaceConfig _snowmanxxxxxxxxxxxxxxx = _snowman.getGenerationSettings().getSurfaceConfig();
      BlockState _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getUnderMaterial();
      BlockState _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getTopMaterial();
      BlockState _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      boolean _snowmanxxxxxxxxxxxxxxxxxxxx = Math.cos(_snowman / 3.0 * Math.PI) > 0.0;
      int _snowmanxxxxxxxxxxxxxxxxxxxxx = -1;
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxx = false;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0;
      BlockPos.Mutable _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx--) {
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < 15) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
            BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isAir()) {
               _snowmanxxxxxxxxxxxxxxxxxxxxx = -1;
            } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isOf(_snowman.getBlock())) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxx == -1) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx = false;
                  if (_snowmanxxxxxxxxxxxxxxxxxxx <= 0) {
                     _snowmanxxxxxxxxxxxxxx = Blocks.AIR.getDefaultState();
                     _snowmanxxxxxxxxxxxxxxxxxx = _snowman;
                  } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx <= _snowman + 1) {
                     _snowmanxxxxxxxxxxxxxx = WHITE_TERRACOTTA;
                     _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman && (_snowmanxxxxxxxxxxxxxx == null || _snowmanxxxxxxxxxxxxxx.isAir())) {
                     _snowmanxxxxxxxxxxxxxx = _snowman;
                  }

                  _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx + Math.max(0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx - _snowman);
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx >= _snowman - 1) {
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx <= _snowman + 3 + _snowmanxxxxxxxxxxxxxxxxxxx) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, false);
                        _snowmanxxxxxxxxxxxxxxxxxxxxxx = true;
                     } else {
                        BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < 64 || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx > 127) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = ORANGE_TERRACOTTA;
                        } else if (_snowmanxxxxxxxxxxxxxxxxxxxx) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = TERRACOTTA;
                        } else {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.calculateLayerBlockState(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman);
                        }

                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, false);
                     }
                  } else {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, false);
                     Block _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.getBlock();
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.WHITE_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.ORANGE_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.MAGENTA_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.LIGHT_BLUE_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.YELLOW_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.LIME_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.PINK_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.GRAY_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.LIGHT_GRAY_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.CYAN_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.PURPLE_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.BLUE_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.BROWN_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.GREEN_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.RED_TERRACOTTA
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.BLACK_TERRACOTTA) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                     }
                  }
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxxx > 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxx--;
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxx) {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                  } else {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, this.calculateLayerBlockState(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman), false);
                  }
               }

               _snowmanxxxxxxxxxxxxxxxxxxxxxxx++;
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
         ChunkRandom _snowman = new ChunkRandom(seed);
         this.heightCutoffNoise = new OctaveSimplexNoiseSampler(_snowman, IntStream.rangeClosed(-3, 0));
         this.heightNoise = new OctaveSimplexNoiseSampler(_snowman, ImmutableList.of(0));
      }

      this.seed = seed;
   }

   protected void initLayerBlocks(long seed) {
      this.layerBlocks = new BlockState[64];
      Arrays.fill(this.layerBlocks, TERRACOTTA);
      ChunkRandom _snowman = new ChunkRandom(seed);
      this.layerNoise = new OctaveSimplexNoiseSampler(_snowman, ImmutableList.of(0));

      for (int _snowmanx = 0; _snowmanx < 64; _snowmanx++) {
         _snowmanx += _snowman.nextInt(5) + 1;
         if (_snowmanx < 64) {
            this.layerBlocks[_snowmanx] = ORANGE_TERRACOTTA;
         }
      }

      int _snowmanxx = _snowman.nextInt(4) + 2;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
         int _snowmanxxxx = _snowman.nextInt(3) + 1;
         int _snowmanxxxxx = _snowman.nextInt(64);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxx + _snowmanxxxxxx < 64 && _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
            this.layerBlocks[_snowmanxxxxx + _snowmanxxxxxx] = YELLOW_TERRACOTTA;
         }
      }

      int _snowmanxxx = _snowman.nextInt(4) + 2;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         int _snowmanxxxxx = _snowman.nextInt(3) + 2;
         int _snowmanxxxxxx = _snowman.nextInt(64);

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxx + _snowmanxxxxxxx < 64 && _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
            this.layerBlocks[_snowmanxxxxxx + _snowmanxxxxxxx] = BROWN_TERRACOTTA;
         }
      }

      int _snowmanxxxx = _snowman.nextInt(4) + 2;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx; _snowmanxxxxx++) {
         int _snowmanxxxxxx = _snowman.nextInt(3) + 1;
         int _snowmanxxxxxxx = _snowman.nextInt(64);

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxx + _snowmanxxxxxxxx < 64 && _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
            this.layerBlocks[_snowmanxxxxxxx + _snowmanxxxxxxxx] = RED_TERRACOTTA;
         }
      }

      int _snowmanxxxxx = _snowman.nextInt(3) + 3;
      int _snowmanxxxxxx = 0;

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
         int _snowmanxxxxxxxx = 1;
         _snowmanxxxxxx += _snowman.nextInt(16) + 4;

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxx + _snowmanxxxxxxxxx < 64 && _snowmanxxxxxxxxx < 1; _snowmanxxxxxxxxx++) {
            this.layerBlocks[_snowmanxxxxxx + _snowmanxxxxxxxxx] = WHITE_TERRACOTTA;
            if (_snowmanxxxxxx + _snowmanxxxxxxxxx > 1 && _snowman.nextBoolean()) {
               this.layerBlocks[_snowmanxxxxxx + _snowmanxxxxxxxxx - 1] = LIGHT_GRAY_TERRACOTTA;
            }

            if (_snowmanxxxxxx + _snowmanxxxxxxxxx < 63 && _snowman.nextBoolean()) {
               this.layerBlocks[_snowmanxxxxxx + _snowmanxxxxxxxxx + 1] = LIGHT_GRAY_TERRACOTTA;
            }
         }
      }
   }

   protected BlockState calculateLayerBlockState(int x, int y, int z) {
      int _snowman = (int)Math.round(this.layerNoise.sample((double)x / 512.0, (double)z / 512.0, false) * 2.0);
      return this.layerBlocks[(y + _snowman + 64) % 64];
   }
}
