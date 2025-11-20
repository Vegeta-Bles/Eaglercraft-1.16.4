package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class IcebergFeature extends Feature<SingleStateFeatureConfig> {
   public IcebergFeature(Codec<SingleStateFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, SingleStateFeatureConfig _snowman) {
      _snowman = new BlockPos(_snowman.getX(), _snowman.getSeaLevel(), _snowman.getZ());
      boolean _snowmanxxxxx = _snowman.nextDouble() > 0.7;
      BlockState _snowmanxxxxxx = _snowman.state;
      double _snowmanxxxxxxx = _snowman.nextDouble() * 2.0 * Math.PI;
      int _snowmanxxxxxxxx = 11 - _snowman.nextInt(5);
      int _snowmanxxxxxxxxx = 3 + _snowman.nextInt(3);
      boolean _snowmanxxxxxxxxxx = _snowman.nextDouble() > 0.7;
      int _snowmanxxxxxxxxxxx = 11;
      int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx ? _snowman.nextInt(6) + 6 : _snowman.nextInt(15) + 3;
      if (!_snowmanxxxxxxxxxx && _snowman.nextDouble() > 0.9) {
         _snowmanxxxxxxxxxxxx += _snowman.nextInt(19) + 7;
      }

      int _snowmanxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxxx + _snowman.nextInt(11), 18);
      int _snowmanxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxxx + _snowman.nextInt(7) - _snowman.nextInt(5), 11);
      int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx ? _snowmanxxxxxxxx : 11;

      for (int _snowmanxxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx
                  ? this.method_13417(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx)
                  : this.method_13419(_snowman, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxx) {
                  this.method_13426(
                     _snowman,
                     _snowman,
                     _snowman,
                     _snowmanxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxxx,
                     _snowmanxxxxx,
                     _snowmanxxxxxx
                  );
               }
            }
         }
      }

      this.method_13418(_snowman, _snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxx);

      for (int _snowmanxxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxxxxxxxxx > -_snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxx--) {
               int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx
                  ? MathHelper.ceil((float)_snowmanxxxxxxxxxxxxxxx * (1.0F - (float)Math.pow((double)_snowmanxxxxxxxxxxxxxxxxxxx, 2.0) / ((float)_snowmanxxxxxxxxxxxxx * 8.0F)))
                  : _snowmanxxxxxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxxxxxxxx = this.method_13427(_snowman, -_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxx) {
                  this.method_13426(
                     _snowman,
                     _snowman,
                     _snowman,
                     _snowmanxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxxx,
                     _snowmanxxxxx,
                     _snowmanxxxxxx
                  );
               }
            }
         }
      }

      boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx ? _snowman.nextDouble() > 0.1 : _snowman.nextDouble() > 0.7;
      if (_snowmanxxxxxxxxxxxxxxxx) {
         this.method_13428(_snowman, _snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx);
      }

      return true;
   }

   private void method_13428(Random _snowman, WorldAccess _snowman, int _snowman, int _snowman, BlockPos _snowman, boolean _snowman, int _snowman, double _snowman, int _snowman) {
      int _snowmanxxxxxxxxx = _snowman.nextBoolean() ? -1 : 1;
      int _snowmanxxxxxxxxxx = _snowman.nextBoolean() ? -1 : 1;
      int _snowmanxxxxxxxxxxx = _snowman.nextInt(Math.max(_snowman / 2 - 2, 1));
      if (_snowman.nextBoolean()) {
         _snowmanxxxxxxxxxxx = _snowman / 2 + 1 - _snowman.nextInt(Math.max(_snowman - _snowman / 2 - 1, 1));
      }

      int _snowmanxxxxxxxxxxxx = _snowman.nextInt(Math.max(_snowman / 2 - 2, 1));
      if (_snowman.nextBoolean()) {
         _snowmanxxxxxxxxxxxx = _snowman / 2 + 1 - _snowman.nextInt(Math.max(_snowman - _snowman / 2 - 1, 1));
      }

      if (_snowman) {
         _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxx = _snowman.nextInt(Math.max(_snowman - 5, 1));
      }

      BlockPos _snowmanxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxx * _snowmanxxxxxxxxxxx, 0, _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxxx);
      double _snowmanxxxxxxxxxxxxxx = _snowman ? _snowman + (Math.PI / 2) : _snowman.nextDouble() * 2.0 * Math.PI;

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < _snowman - 3; _snowmanxxxxxxxxxxxxxxx++) {
         int _snowmanxxxxxxxxxxxxxxxx = this.method_13419(_snowman, _snowmanxxxxxxxxxxxxxxx, _snowman, _snowman);
         this.method_13415(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowman, _snowman, false, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowman, _snowman);
      }

      for (int _snowmanxxxxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxxxxx > -_snowman + _snowman.nextInt(5); _snowmanxxxxxxxxxxxxxxx--) {
         int _snowmanxxxxxxxxxxxxxxxx = this.method_13427(_snowman, -_snowmanxxxxxxxxxxxxxxx, _snowman, _snowman);
         this.method_13415(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowman, _snowman, true, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowman, _snowman);
      }
   }

   private void method_13415(int _snowman, int _snowman, BlockPos _snowman, WorldAccess _snowman, boolean _snowman, double _snowman, BlockPos _snowman, int _snowman, int _snowman) {
      int _snowmanxxxxxxxxx = _snowman + 1 + _snowman / 3;
      int _snowmanxxxxxxxxxx = Math.min(_snowman - 3, 3) + _snowman / 2 - 1;

      for (int _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxx = -_snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
            double _snowmanxxxxxxxxxxxxx = this.method_13424(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowman);
            if (_snowmanxxxxxxxxxxxxx < 0.0) {
               BlockPos _snowmanxxxxxxxxxxxxxx = _snowman.add(_snowmanxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxx);
               Block _snowmanxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxx).getBlock();
               if (this.isSnowyOrIcy(_snowmanxxxxxxxxxxxxxxx) || _snowmanxxxxxxxxxxxxxxx == Blocks.SNOW_BLOCK) {
                  if (_snowman) {
                     this.setBlockState(_snowman, _snowmanxxxxxxxxxxxxxx, Blocks.WATER.getDefaultState());
                  } else {
                     this.setBlockState(_snowman, _snowmanxxxxxxxxxxxxxx, Blocks.AIR.getDefaultState());
                     this.clearSnowAbove(_snowman, _snowmanxxxxxxxxxxxxxx);
                  }
               }
            }
         }
      }
   }

   private void clearSnowAbove(WorldAccess world, BlockPos pos) {
      if (world.getBlockState(pos.up()).isOf(Blocks.SNOW)) {
         this.setBlockState(world, pos.up(), Blocks.AIR.getDefaultState());
      }
   }

   private void method_13426(WorldAccess _snowman, Random _snowman, BlockPos _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, boolean _snowman, int _snowman, double _snowman, boolean _snowman, BlockState _snowman) {
      double _snowmanxxxxxxxxxxxxxx = _snowman ? this.method_13424(_snowman, _snowman, BlockPos.ORIGIN, _snowman, this.method_13416(_snowman, _snowman, _snowman), _snowman) : this.method_13421(_snowman, _snowman, BlockPos.ORIGIN, _snowman, _snowman);
      if (_snowmanxxxxxxxxxxxxxx < 0.0) {
         BlockPos _snowmanxxxxxxxxxxxxxxx = _snowman.add(_snowman, _snowman, _snowman);
         double _snowmanxxxxxxxxxxxxxxxx = _snowman ? -0.5 : (double)(-6 - _snowman.nextInt(3));
         if (_snowmanxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxx && _snowman.nextDouble() > 0.9) {
            return;
         }

         this.method_13425(_snowmanxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman - _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private void method_13425(BlockPos _snowman, WorldAccess _snowman, Random _snowman, int _snowman, int _snowman, boolean _snowman, boolean _snowman, BlockState _snowman) {
      BlockState _snowmanxxxxxxxx = _snowman.getBlockState(_snowman);
      if (_snowmanxxxxxxxx.getMaterial() == Material.AIR || _snowmanxxxxxxxx.isOf(Blocks.SNOW_BLOCK) || _snowmanxxxxxxxx.isOf(Blocks.ICE) || _snowmanxxxxxxxx.isOf(Blocks.WATER)) {
         boolean _snowmanxxxxxxxxx = !_snowman || _snowman.nextDouble() > 0.05;
         int _snowmanxxxxxxxxxx = _snowman ? 3 : 2;
         if (_snowman && !_snowmanxxxxxxxx.isOf(Blocks.WATER) && (double)_snowman <= (double)_snowman.nextInt(Math.max(1, _snowman / _snowmanxxxxxxxxxx)) + (double)_snowman * 0.6 && _snowmanxxxxxxxxx) {
            this.setBlockState(_snowman, _snowman, Blocks.SNOW_BLOCK.getDefaultState());
         } else {
            this.setBlockState(_snowman, _snowman, _snowman);
         }
      }
   }

   private int method_13416(int _snowman, int _snowman, int _snowman) {
      int _snowmanxxx = _snowman;
      if (_snowman > 0 && _snowman - _snowman <= 3) {
         _snowmanxxx = _snowman - (4 - (_snowman - _snowman));
      }

      return _snowmanxxx;
   }

   private double method_13421(int _snowman, int _snowman, BlockPos _snowman, int _snowman, Random _snowman) {
      float _snowmanxxxxx = 10.0F * MathHelper.clamp(_snowman.nextFloat(), 0.2F, 0.8F) / (float)_snowman;
      return (double)_snowmanxxxxx + Math.pow((double)(_snowman - _snowman.getX()), 2.0) + Math.pow((double)(_snowman - _snowman.getZ()), 2.0) - Math.pow((double)_snowman, 2.0);
   }

   private double method_13424(int _snowman, int _snowman, BlockPos _snowman, int _snowman, int _snowman, double _snowman) {
      return Math.pow(((double)(_snowman - _snowman.getX()) * Math.cos(_snowman) - (double)(_snowman - _snowman.getZ()) * Math.sin(_snowman)) / (double)_snowman, 2.0)
         + Math.pow(((double)(_snowman - _snowman.getX()) * Math.sin(_snowman) + (double)(_snowman - _snowman.getZ()) * Math.cos(_snowman)) / (double)_snowman, 2.0)
         - 1.0;
   }

   private int method_13419(Random _snowman, int _snowman, int _snowman, int _snowman) {
      float _snowmanxxxx = 3.5F - _snowman.nextFloat();
      float _snowmanxxxxx = (1.0F - (float)Math.pow((double)_snowman, 2.0) / ((float)_snowman * _snowmanxxxx)) * (float)_snowman;
      if (_snowman > 15 + _snowman.nextInt(5)) {
         int _snowmanxxxxxx = _snowman < 3 + _snowman.nextInt(6) ? _snowman / 2 : _snowman;
         _snowmanxxxxx = (1.0F - (float)_snowmanxxxxxx / ((float)_snowman * _snowmanxxxx * 0.4F)) * (float)_snowman;
      }

      return MathHelper.ceil(_snowmanxxxxx / 2.0F);
   }

   private int method_13417(int _snowman, int _snowman, int _snowman) {
      float _snowmanxxx = 1.0F;
      float _snowmanxxxx = (1.0F - (float)Math.pow((double)_snowman, 2.0) / ((float)_snowman * 1.0F)) * (float)_snowman;
      return MathHelper.ceil(_snowmanxxxx / 2.0F);
   }

   private int method_13427(Random _snowman, int _snowman, int _snowman, int _snowman) {
      float _snowmanxxxx = 1.0F + _snowman.nextFloat() / 2.0F;
      float _snowmanxxxxx = (1.0F - (float)_snowman / ((float)_snowman * _snowmanxxxx)) * (float)_snowman;
      return MathHelper.ceil(_snowmanxxxxx / 2.0F);
   }

   private boolean isSnowyOrIcy(Block block) {
      return block == Blocks.PACKED_ICE || block == Blocks.SNOW_BLOCK || block == Blocks.BLUE_ICE;
   }

   private boolean isAirBelow(BlockView world, BlockPos pos) {
      return world.getBlockState(pos.down()).getMaterial() == Material.AIR;
   }

   private void method_13418(WorldAccess world, BlockPos pos, int _snowman, int _snowman, boolean _snowman, int _snowman) {
      int _snowmanxxxx = _snowman ? _snowman : _snowman / 2;

      for (int _snowmanxxxxx = -_snowmanxxxx; _snowmanxxxxx <= _snowmanxxxx; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = -_snowmanxxxx; _snowmanxxxxxx <= _snowmanxxxx; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= _snowman; _snowmanxxxxxxx++) {
               BlockPos _snowmanxxxxxxxx = pos.add(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
               Block _snowmanxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxx).getBlock();
               if (this.isSnowyOrIcy(_snowmanxxxxxxxxx) || _snowmanxxxxxxxxx == Blocks.SNOW) {
                  if (this.isAirBelow(world, _snowmanxxxxxxxx)) {
                     this.setBlockState(world, _snowmanxxxxxxxx, Blocks.AIR.getDefaultState());
                     this.setBlockState(world, _snowmanxxxxxxxx.up(), Blocks.AIR.getDefaultState());
                  } else if (this.isSnowyOrIcy(_snowmanxxxxxxxxx)) {
                     Block[] _snowmanxxxxxxxxxx = new Block[]{
                        world.getBlockState(_snowmanxxxxxxxx.west()).getBlock(),
                        world.getBlockState(_snowmanxxxxxxxx.east()).getBlock(),
                        world.getBlockState(_snowmanxxxxxxxx.north()).getBlock(),
                        world.getBlockState(_snowmanxxxxxxxx.south()).getBlock()
                     };
                     int _snowmanxxxxxxxxxxx = 0;

                     for (Block _snowmanxxxxxxxxxxxx : _snowmanxxxxxxxxxx) {
                        if (!this.isSnowyOrIcy(_snowmanxxxxxxxxxxxx)) {
                           _snowmanxxxxxxxxxxx++;
                        }
                     }

                     if (_snowmanxxxxxxxxxxx >= 3) {
                        this.setBlockState(world, _snowmanxxxxxxxx, Blocks.AIR.getDefaultState());
                     }
                  }
               }
            }
         }
      }
   }
}
