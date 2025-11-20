package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class IceSpikeFeature extends Feature<DefaultFeatureConfig> {
   public IceSpikeFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      while (_snowman.isAir(_snowman) && _snowman.getY() > 2) {
         _snowman = _snowman.down();
      }

      if (!_snowman.getBlockState(_snowman).isOf(Blocks.SNOW_BLOCK)) {
         return false;
      } else {
         _snowman = _snowman.up(_snowman.nextInt(4));
         int _snowmanxxxxx = _snowman.nextInt(4) + 7;
         int _snowmanxxxxxx = _snowmanxxxxx / 4 + _snowman.nextInt(2);
         if (_snowmanxxxxxx > 1 && _snowman.nextInt(60) == 0) {
            _snowman = _snowman.up(10 + _snowman.nextInt(30));
         }

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
            float _snowmanxxxxxxxx = (1.0F - (float)_snowmanxxxxxxx / (float)_snowmanxxxxx) * (float)_snowmanxxxxxx;
            int _snowmanxxxxxxxxx = MathHelper.ceil(_snowmanxxxxxxxx);

            for (int _snowmanxxxxxxxxxx = -_snowmanxxxxxxxxx; _snowmanxxxxxxxxxx <= _snowmanxxxxxxxxx; _snowmanxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxx = (float)MathHelper.abs(_snowmanxxxxxxxxxx) - 0.25F;

               for (int _snowmanxxxxxxxxxxxx = -_snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
                  float _snowmanxxxxxxxxxxxxx = (float)MathHelper.abs(_snowmanxxxxxxxxxxxx) - 0.25F;
                  if ((_snowmanxxxxxxxxxx == 0 && _snowmanxxxxxxxxxxxx == 0 || !(_snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx > _snowmanxxxxxxxx * _snowmanxxxxxxxx))
                     && (
                        _snowmanxxxxxxxxxx != -_snowmanxxxxxxxxx && _snowmanxxxxxxxxxx != _snowmanxxxxxxxxx && _snowmanxxxxxxxxxxxx != -_snowmanxxxxxxxxx && _snowmanxxxxxxxxxxxx != _snowmanxxxxxxxxx
                           || !(_snowman.nextFloat() > 0.75F)
                     )) {
                     BlockState _snowmanxxxxxxxxxxxxxx = _snowman.getBlockState(_snowman.add(_snowmanxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx));
                     Block _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getBlock();
                     if (_snowmanxxxxxxxxxxxxxx.isAir() || isSoil(_snowmanxxxxxxxxxxxxxxx) || _snowmanxxxxxxxxxxxxxxx == Blocks.SNOW_BLOCK || _snowmanxxxxxxxxxxxxxxx == Blocks.ICE) {
                        this.setBlockState(_snowman, _snowman.add(_snowmanxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx), Blocks.PACKED_ICE.getDefaultState());
                     }

                     if (_snowmanxxxxxxx != 0 && _snowmanxxxxxxxxx > 1) {
                        _snowmanxxxxxxxxxxxxxx = _snowman.getBlockState(_snowman.add(_snowmanxxxxxxxxxx, -_snowmanxxxxxxx, _snowmanxxxxxxxxxxxx));
                        _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getBlock();
                        if (_snowmanxxxxxxxxxxxxxx.isAir() || isSoil(_snowmanxxxxxxxxxxxxxxx) || _snowmanxxxxxxxxxxxxxxx == Blocks.SNOW_BLOCK || _snowmanxxxxxxxxxxxxxxx == Blocks.ICE) {
                           this.setBlockState(_snowman, _snowman.add(_snowmanxxxxxxxxxx, -_snowmanxxxxxxx, _snowmanxxxxxxxxxxxx), Blocks.PACKED_ICE.getDefaultState());
                        }
                     }
                  }
               }
            }
         }

         int _snowmanxxxxxxx = _snowmanxxxxxx - 1;
         if (_snowmanxxxxxxx < 0) {
            _snowmanxxxxxxx = 0;
         } else if (_snowmanxxxxxxx > 1) {
            _snowmanxxxxxxx = 1;
         }

         for (int _snowmanxxxxxxxx = -_snowmanxxxxxxx; _snowmanxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxx++) {
            for (int _snowmanxxxxxxxxx = -_snowmanxxxxxxx; _snowmanxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxx++) {
               BlockPos _snowmanxxxxxxxxxx = _snowman.add(_snowmanxxxxxxxx, -1, _snowmanxxxxxxxxx);
               int _snowmanxxxxxxxxxxx = 50;
               if (Math.abs(_snowmanxxxxxxxx) == 1 && Math.abs(_snowmanxxxxxxxxx) == 1) {
                  _snowmanxxxxxxxxxxx = _snowman.nextInt(5);
               }

               while (_snowmanxxxxxxxxxx.getY() > 50) {
                  BlockState _snowmanxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxx);
                  Block _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getBlock();
                  if (!_snowmanxxxxxxxxxxxxx.isAir()
                     && !isSoil(_snowmanxxxxxxxxxxxxxxxx)
                     && _snowmanxxxxxxxxxxxxxxxx != Blocks.SNOW_BLOCK
                     && _snowmanxxxxxxxxxxxxxxxx != Blocks.ICE
                     && _snowmanxxxxxxxxxxxxxxxx != Blocks.PACKED_ICE) {
                     break;
                  }

                  this.setBlockState(_snowman, _snowmanxxxxxxxxxx, Blocks.PACKED_ICE.getDefaultState());
                  _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.down();
                  if (--_snowmanxxxxxxxxxxx <= 0) {
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.down(_snowman.nextInt(5) + 1);
                     _snowmanxxxxxxxxxxx = _snowman.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}
