package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class ErodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

   public ErodedBadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      double _snowmanxxxxxxxxxxxx = 0.0;
      double _snowmanxxxxxxxxxxxxx = Math.min(Math.abs(_snowman), this.heightCutoffNoise.sample((double)_snowman * 0.25, (double)_snowman * 0.25, false) * 15.0);
      if (_snowmanxxxxxxxxxxxxx > 0.0) {
         double _snowmanxxxxxxxxxxxxxx = 0.001953125;
         double _snowmanxxxxxxxxxxxxxxx = Math.abs(this.heightNoise.sample((double)_snowman * 0.001953125, (double)_snowman * 0.001953125, false));
         _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx * 2.5;
         double _snowmanxxxxxxxxxxxxxxxx = Math.ceil(_snowmanxxxxxxxxxxxxxxx * 50.0) + 14.0;
         if (_snowmanxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
         }

         _snowmanxxxxxxxxxxxx += 64.0;
      }

      int _snowmanxxxxxxxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxxxxxxxx = _snowman & 15;
      BlockState _snowmanxxxxxxxxxxxxxxxx = WHITE_TERRACOTTA;
      SurfaceConfig _snowmanxxxxxxxxxxxxxxxxx = _snowman.getGenerationSettings().getSurfaceConfig();
      BlockState _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getUnderMaterial();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getTopMaterial();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxx = Math.cos(_snowman / 3.0 * Math.PI) > 0.0;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -1;
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = false;
      BlockPos.Mutable _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(_snowman, (int)_snowmanxxxxxxxxxxxx + 1); _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx--) {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
         if (_snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx).isAir() && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx < (int)_snowmanxxxxxxxxxxxx) {
            _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, false);
         }

         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.isAir()) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.isOf(_snowman.getBlock())) {
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx == -1) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = false;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxx <= 0) {
                  _snowmanxxxxxxxxxxxxxxxx = Blocks.AIR.getDefaultState();
                  _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman;
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx <= _snowman + 1) {
                  _snowmanxxxxxxxxxxxxxxxx = WHITE_TERRACOTTA;
                  _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman && (_snowmanxxxxxxxxxxxxxxxx == null || _snowmanxxxxxxxxxxxxxxxx.isAir())) {
                  _snowmanxxxxxxxxxxxxxxxx = _snowman;
               }

               _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx + Math.max(0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowman);
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx >= _snowman - 1) {
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx > _snowman + 3 + _snowmanxxxxxxxxxxxxxxxxxxxxx) {
                     BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx < 64 || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx > 127) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ORANGE_TERRACOTTA;
                     } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxx) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = TERRACOTTA;
                     } else {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.calculateLayerBlockState(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman);
                     }

                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false);
                  } else {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, false);
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = true;
                  }
               } else {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, false);
                  Block _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.getBlock();
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.WHITE_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.ORANGE_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.MAGENTA_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.LIGHT_BLUE_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.YELLOW_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.LIME_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.PINK_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.GRAY_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.LIGHT_GRAY_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.CYAN_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.PURPLE_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.BLUE_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.BROWN_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.GREEN_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.RED_TERRACOTTA
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Blocks.BLACK_TERRACOTTA) {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                  }
               }
            } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx--;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
               } else {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, this.calculateLayerBlockState(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman), false);
               }
            }
         }
      }
   }
}
