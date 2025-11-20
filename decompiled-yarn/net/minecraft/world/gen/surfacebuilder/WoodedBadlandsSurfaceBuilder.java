package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class WoodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

   public WoodedBadlandsSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   @Override
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
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman - 1) {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, false);
                     if (_snowmanxxxxxxxxxxxxxxxxxx == WHITE_TERRACOTTA) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, ORANGE_TERRACOTTA, false);
                     }
                  } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx > 86 + _snowmanxxxxxxxxxxxxxxxxxxx * 2) {
                     if (_snowmanxxxxxxxxxxxxxxxxxxxx) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, Blocks.COARSE_DIRT.getDefaultState(), false);
                     } else {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, Blocks.GRASS_BLOCK.getDefaultState(), false);
                     }
                  } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx <= _snowman + 3 + _snowmanxxxxxxxxxxxxxxxxxxx) {
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
}
