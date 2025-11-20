package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class LakeFeature extends Feature<SingleStateFeatureConfig> {
   private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

   public LakeFeature(Codec<SingleStateFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, SingleStateFeatureConfig _snowman) {
      while (_snowman.getY() > 5 && _snowman.isAir(_snowman)) {
         _snowman = _snowman.down();
      }

      if (_snowman.getY() <= 4) {
         return false;
      } else {
         _snowman = _snowman.down(4);
         if (_snowman.getStructures(ChunkSectionPos.from(_snowman), StructureFeature.VILLAGE).findAny().isPresent()) {
            return false;
         } else {
            boolean[] _snowmanxxxxx = new boolean[2048];
            int _snowmanxxxxxx = _snowman.nextInt(4) + 4;

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
               double _snowmanxxxxxxxx = _snowman.nextDouble() * 6.0 + 3.0;
               double _snowmanxxxxxxxxx = _snowman.nextDouble() * 4.0 + 2.0;
               double _snowmanxxxxxxxxxx = _snowman.nextDouble() * 6.0 + 3.0;
               double _snowmanxxxxxxxxxxx = _snowman.nextDouble() * (16.0 - _snowmanxxxxxxxx - 2.0) + 1.0 + _snowmanxxxxxxxx / 2.0;
               double _snowmanxxxxxxxxxxxx = _snowman.nextDouble() * (8.0 - _snowmanxxxxxxxxx - 4.0) + 2.0 + _snowmanxxxxxxxxx / 2.0;
               double _snowmanxxxxxxxxxxxxx = _snowman.nextDouble() * (16.0 - _snowmanxxxxxxxxxx - 2.0) + 1.0 + _snowmanxxxxxxxxxx / 2.0;

               for (int _snowmanxxxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxxxx < 15; _snowmanxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxxxxx < 15; _snowmanxxxxxxxxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxxxxxx < 7; _snowmanxxxxxxxxxxxxxxxx++) {
                        double _snowmanxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxx) / (_snowmanxxxxxxxx / 2.0);
                        double _snowmanxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxx) / (_snowmanxxxxxxxxx / 2.0);
                        double _snowmanxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx) / (_snowmanxxxxxxxxxx / 2.0);
                        double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx
                           + _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx
                           + _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxxxxxx < 1.0) {
                           _snowmanxxxxx[(_snowmanxxxxxxxxxxxxxx * 16 + _snowmanxxxxxxxxxxxxxxx) * 8 + _snowmanxxxxxxxxxxxxxxxx] = true;
                        }
                     }
                  }
               }
            }

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 8; _snowmanxxxxxxxxx++) {
                     boolean _snowmanxxxxxxxxxx = !_snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxx]
                        && (
                           _snowmanxxxxxxx < 15 && _snowmanxxxxx[((_snowmanxxxxxxx + 1) * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxx]
                              || _snowmanxxxxxxx > 0 && _snowmanxxxxx[((_snowmanxxxxxxx - 1) * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxx]
                              || _snowmanxxxxxxxx < 15 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx + 1) * 8 + _snowmanxxxxxxxxx]
                              || _snowmanxxxxxxxx > 0 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + (_snowmanxxxxxxxx - 1)) * 8 + _snowmanxxxxxxxxx]
                              || _snowmanxxxxxxxxx < 7 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxx + 1]
                              || _snowmanxxxxxxxxx > 0 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + (_snowmanxxxxxxxxx - 1)]
                        );
                     if (_snowmanxxxxxxxxxx) {
                        Material _snowmanxxxxxxxxxxx = _snowman.getBlockState(_snowman.add(_snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx)).getMaterial();
                        if (_snowmanxxxxxxxxx >= 4 && _snowmanxxxxxxxxxxx.isLiquid()) {
                           return false;
                        }

                        if (_snowmanxxxxxxxxx < 4 && !_snowmanxxxxxxxxxxx.isSolid() && _snowman.getBlockState(_snowman.add(_snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx)) != _snowman.state) {
                           return false;
                        }
                     }
                  }
               }
            }

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 8; _snowmanxxxxxxxxxx++) {
                     if (_snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxxx]) {
                        _snowman.setBlockState(_snowman.add(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxx), _snowmanxxxxxxxxxx >= 4 ? CAVE_AIR : _snowman.state, 2);
                     }
                  }
               }
            }

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxx = 4; _snowmanxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxx++) {
                     if (_snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxxxxx]) {
                        BlockPos _snowmanxxxxxxxxxxxxx = _snowman.add(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxx - 1, _snowmanxxxxxxxx);
                        if (isSoil(_snowman.getBlockState(_snowmanxxxxxxxxxxxxx).getBlock()) && _snowman.getLightLevel(LightType.SKY, _snowman.add(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx)) > 0
                           )
                         {
                           Biome _snowmanxxxxxxxxxxxxxx = _snowman.getBiome(_snowmanxxxxxxxxxxxxx);
                           if (_snowmanxxxxxxxxxxxxxx.getGenerationSettings().getSurfaceConfig().getTopMaterial().isOf(Blocks.MYCELIUM)) {
                              _snowman.setBlockState(_snowmanxxxxxxxxxxxxx, Blocks.MYCELIUM.getDefaultState(), 2);
                           } else {
                              _snowman.setBlockState(_snowmanxxxxxxxxxxxxx, Blocks.GRASS_BLOCK.getDefaultState(), 2);
                           }
                        }
                     }
                  }
               }
            }

            if (_snowman.state.getMaterial() == Material.LAVA) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                  for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxx++) {
                        boolean _snowmanxxxxxxxxxxxxxx = !_snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxxxxxx]
                           && (
                              _snowmanxxxxxxx < 15 && _snowmanxxxxx[((_snowmanxxxxxxx + 1) * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxxxxxx]
                                 || _snowmanxxxxxxx > 0 && _snowmanxxxxx[((_snowmanxxxxxxx - 1) * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxxxxxx]
                                 || _snowmanxxxxxxxx < 15 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx + 1) * 8 + _snowmanxxxxxxxxxxxxx]
                                 || _snowmanxxxxxxxx > 0 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + (_snowmanxxxxxxxx - 1)) * 8 + _snowmanxxxxxxxxxxxxx]
                                 || _snowmanxxxxxxxxxxxxx < 7 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + _snowmanxxxxxxxxxxxxx + 1]
                                 || _snowmanxxxxxxxxxxxxx > 0 && _snowmanxxxxx[(_snowmanxxxxxxx * 16 + _snowmanxxxxxxxx) * 8 + (_snowmanxxxxxxxxxxxxx - 1)]
                           );
                        if (_snowmanxxxxxxxxxxxxxx
                           && (_snowmanxxxxxxxxxxxxx < 4 || _snowman.nextInt(2) != 0)
                           && _snowman.getBlockState(_snowman.add(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxx)).getMaterial().isSolid()) {
                           _snowman.setBlockState(_snowman.add(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxx), Blocks.STONE.getDefaultState(), 2);
                        }
                     }
                  }
               }
            }

            if (_snowman.state.getMaterial() == Material.WATER) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                  for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
                     int _snowmanxxxxxxxxxxxxxx = 4;
                     BlockPos _snowmanxxxxxxxxxxxxxxx = _snowman.add(_snowmanxxxxxxx, 4, _snowmanxxxxxxxx);
                     if (_snowman.getBiome(_snowmanxxxxxxxxxxxxxxx).canSetIce(_snowman, _snowmanxxxxxxxxxxxxxxx, false)) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxx, Blocks.ICE.getDefaultState(), 2);
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
