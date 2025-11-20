package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class OreFeature extends Feature<OreFeatureConfig> {
   public OreFeature(Codec<OreFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, OreFeatureConfig _snowman) {
      float _snowmanxxxxx = _snowman.nextFloat() * (float) Math.PI;
      float _snowmanxxxxxx = (float)_snowman.size / 8.0F;
      int _snowmanxxxxxxx = MathHelper.ceil(((float)_snowman.size / 16.0F * 2.0F + 1.0F) / 2.0F);
      double _snowmanxxxxxxxx = (double)_snowman.getX() + Math.sin((double)_snowmanxxxxx) * (double)_snowmanxxxxxx;
      double _snowmanxxxxxxxxx = (double)_snowman.getX() - Math.sin((double)_snowmanxxxxx) * (double)_snowmanxxxxxx;
      double _snowmanxxxxxxxxxx = (double)_snowman.getZ() + Math.cos((double)_snowmanxxxxx) * (double)_snowmanxxxxxx;
      double _snowmanxxxxxxxxxxx = (double)_snowman.getZ() - Math.cos((double)_snowmanxxxxx) * (double)_snowmanxxxxxx;
      int _snowmanxxxxxxxxxxxx = 2;
      double _snowmanxxxxxxxxxxxxx = (double)(_snowman.getY() + _snowman.nextInt(3) - 2);
      double _snowmanxxxxxxxxxxxxxx = (double)(_snowman.getY() + _snowman.nextInt(3) - 2);
      int _snowmanxxxxxxxxxxxxxxx = _snowman.getX() - MathHelper.ceil(_snowmanxxxxxx) - _snowmanxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxx = _snowman.getY() - 2 - _snowmanxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxx = _snowman.getZ() - MathHelper.ceil(_snowmanxxxxxx) - _snowmanxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxx = 2 * (MathHelper.ceil(_snowmanxxxxxx) + _snowmanxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxx = 2 * (2 + _snowmanxxxxxxx);

      for (int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
            if (_snowmanxxxxxxxxxxxxxxxx <= _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx)) {
               return this.generateVeinPart(
                  _snowman,
                  _snowman,
                  _snowman,
                  _snowmanxxxxxxxx,
                  _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxx
               );
            }
         }
      }

      return false;
   }

   protected boolean generateVeinPart(
      WorldAccess world,
      Random random,
      OreFeatureConfig config,
      double startX,
      double endX,
      double startZ,
      double endZ,
      double startY,
      double endY,
      int x,
      int y,
      int z,
      int size,
      int _snowman
   ) {
      int _snowmanx = 0;
      BitSet _snowmanxx = new BitSet(size * _snowman * size);
      BlockPos.Mutable _snowmanxxx = new BlockPos.Mutable();
      int _snowmanxxxx = config.size;
      double[] _snowmanxxxxx = new double[_snowmanxxxx * 4];

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
         float _snowmanxxxxxxx = (float)_snowmanxxxxxx / (float)_snowmanxxxx;
         double _snowmanxxxxxxxx = MathHelper.lerp((double)_snowmanxxxxxxx, startX, endX);
         double _snowmanxxxxxxxxx = MathHelper.lerp((double)_snowmanxxxxxxx, startY, endY);
         double _snowmanxxxxxxxxxx = MathHelper.lerp((double)_snowmanxxxxxxx, startZ, endZ);
         double _snowmanxxxxxxxxxxx = random.nextDouble() * (double)_snowmanxxxx / 16.0;
         double _snowmanxxxxxxxxxxxx = ((double)(MathHelper.sin((float) Math.PI * _snowmanxxxxxxx) + 1.0F) * _snowmanxxxxxxxxxxx + 1.0) / 2.0;
         _snowmanxxxxx[_snowmanxxxxxx * 4 + 0] = _snowmanxxxxxxxx;
         _snowmanxxxxx[_snowmanxxxxxx * 4 + 1] = _snowmanxxxxxxxxx;
         _snowmanxxxxx[_snowmanxxxxxx * 4 + 2] = _snowmanxxxxxxxxxx;
         _snowmanxxxxx[_snowmanxxxxxx * 4 + 3] = _snowmanxxxxxxxxxxxx;
      }

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx - 1; _snowmanxxxxxx++) {
         if (!(_snowmanxxxxx[_snowmanxxxxxx * 4 + 3] <= 0.0)) {
            for (int _snowmanxxxxxxx = _snowmanxxxxxx + 1; _snowmanxxxxxxx < _snowmanxxxx; _snowmanxxxxxxx++) {
               if (!(_snowmanxxxxx[_snowmanxxxxxxx * 4 + 3] <= 0.0)) {
                  double _snowmanxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxx * 4 + 0] - _snowmanxxxxx[_snowmanxxxxxxx * 4 + 0];
                  double _snowmanxxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxx * 4 + 1] - _snowmanxxxxx[_snowmanxxxxxxx * 4 + 1];
                  double _snowmanxxxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxx * 4 + 2] - _snowmanxxxxx[_snowmanxxxxxxx * 4 + 2];
                  double _snowmanxxxxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxx * 4 + 3] - _snowmanxxxxx[_snowmanxxxxxxx * 4 + 3];
                  if (_snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx > _snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx) {
                     if (_snowmanxxxxxxxxxxx > 0.0) {
                        _snowmanxxxxx[_snowmanxxxxxxx * 4 + 3] = -1.0;
                     } else {
                        _snowmanxxxxx[_snowmanxxxxxx * 4 + 3] = -1.0;
                     }
                  }
               }
            }
         }
      }

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxx++) {
         double _snowmanxxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxxxx * 4 + 3];
         if (!(_snowmanxxxxxxxxx < 0.0)) {
            double _snowmanxxxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxxxx * 4 + 0];
            double _snowmanxxxxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxxxx * 4 + 1];
            double _snowmanxxxxxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxxxx * 4 + 2];
            int _snowmanxxxxxxxxxxxxx = Math.max(MathHelper.floor(_snowmanxxxxxxxxxx - _snowmanxxxxxxxxx), x);
            int _snowmanxxxxxxxxxxxxxx = Math.max(MathHelper.floor(_snowmanxxxxxxxxxxx - _snowmanxxxxxxxxx), y);
            int _snowmanxxxxxxxxxxxxxxx = Math.max(MathHelper.floor(_snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxx), z);
            int _snowmanxxxxxxxxxxxxxxxx = Math.max(MathHelper.floor(_snowmanxxxxxxxxxx + _snowmanxxxxxxxxx), _snowmanxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxx = Math.max(MathHelper.floor(_snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx), _snowmanxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxx = Math.max(MathHelper.floor(_snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxx), _snowmanxxxxxxxxxxxxxxx);

            for (int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxxx + 0.5 - _snowmanxxxxxxxxxx) / _snowmanxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx < 1.0) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
                     double _snowmanxxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxxxxx + 0.5 - _snowmanxxxxxxxxxxx) / _snowmanxxxxxxxxx;
                     if (_snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxx < 1.0) {
                        for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxx++) {
                           double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.5 - _snowmanxxxxxxxxxxxx) / _snowmanxxxxxxxxx;
                           if (_snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx
                                 + _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxx
                                 + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx
                              < 1.0) {
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx
                                 - x
                                 + (_snowmanxxxxxxxxxxxxxxxxxxxxx - y) * size
                                 + (_snowmanxxxxxxxxxxxxxxxxxxxxxxx - z) * size * _snowman;
                              if (!_snowmanxx.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                                 _snowmanxx.set(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
                                 _snowmanxxx.set(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                                 if (config.target.test(world.getBlockState(_snowmanxxx), random)) {
                                    world.setBlockState(_snowmanxxx, config.state, 2);
                                    _snowmanx++;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return _snowmanx > 0;
   }
}
