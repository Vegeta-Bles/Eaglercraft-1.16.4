package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class HugeFungusFeature extends Feature<HugeFungusFeatureConfig> {
   public HugeFungusFeature(Codec<HugeFungusFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, HugeFungusFeatureConfig _snowman) {
      Block _snowmanxxxxx = _snowman.validBaseBlock.getBlock();
      BlockPos _snowmanxxxxxx = null;
      Block _snowmanxxxxxxx = _snowman.getBlockState(_snowman.down()).getBlock();
      if (_snowmanxxxxxxx == _snowmanxxxxx) {
         _snowmanxxxxxx = _snowman;
      }

      if (_snowmanxxxxxx == null) {
         return false;
      } else {
         int _snowmanxxxxxxxx = MathHelper.nextInt(_snowman, 4, 13);
         if (_snowman.nextInt(12) == 0) {
            _snowmanxxxxxxxx *= 2;
         }

         if (!_snowman.planted) {
            int _snowmanxxxxxxxxx = _snowman.getWorldHeight();
            if (_snowmanxxxxxx.getY() + _snowmanxxxxxxxx + 1 >= _snowmanxxxxxxxxx) {
               return false;
            }
         }

         boolean _snowmanxxxxxxxxx = !_snowman.planted && _snowman.nextFloat() < 0.06F;
         _snowman.setBlockState(_snowman, Blocks.AIR.getDefaultState(), 4);
         this.generateStem(_snowman, _snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         this.generateHat(_snowman, _snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         return true;
      }
   }

   private static boolean method_24866(WorldAccess _snowman, BlockPos _snowman, boolean _snowman) {
      return _snowman.testBlockState(_snowman, _snowmanxxxxx -> {
         Material _snowmanxx = _snowmanxxxxx.getMaterial();
         return _snowmanxxxxx.getMaterial().isReplaceable() || _snowman && _snowmanxx == Material.PLANT;
      });
   }

   private void generateStem(WorldAccess world, Random random, HugeFungusFeatureConfig config, BlockPos _snowman, int stemHeight, boolean thickStem) {
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();
      BlockState _snowmanxx = config.stemState;
      int _snowmanxxx = thickStem ? 1 : 0;

      for (int _snowmanxxxx = -_snowmanxxx; _snowmanxxxx <= _snowmanxxx; _snowmanxxxx++) {
         for (int _snowmanxxxxx = -_snowmanxxx; _snowmanxxxxx <= _snowmanxxx; _snowmanxxxxx++) {
            boolean _snowmanxxxxxx = thickStem && MathHelper.abs(_snowmanxxxx) == _snowmanxxx && MathHelper.abs(_snowmanxxxxx) == _snowmanxxx;

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < stemHeight; _snowmanxxxxxxx++) {
               _snowmanx.set(_snowman, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx);
               if (method_24866(world, _snowmanx, true)) {
                  if (config.planted) {
                     if (!world.getBlockState(_snowmanx.down()).isAir()) {
                        world.breakBlock(_snowmanx, true);
                     }

                     world.setBlockState(_snowmanx, _snowmanxx, 3);
                  } else if (_snowmanxxxxxx) {
                     if (random.nextFloat() < 0.1F) {
                        this.setBlockState(world, _snowmanx, _snowmanxx);
                     }
                  } else {
                     this.setBlockState(world, _snowmanx, _snowmanxx);
                  }
               }
            }
         }
      }
   }

   private void generateHat(WorldAccess world, Random random, HugeFungusFeatureConfig config, BlockPos _snowman, int hatHeight, boolean thickStem) {
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();
      boolean _snowmanxx = config.hatState.isOf(Blocks.NETHER_WART_BLOCK);
      int _snowmanxxx = Math.min(random.nextInt(1 + hatHeight / 3) + 5, hatHeight);
      int _snowmanxxxx = hatHeight - _snowmanxxx;

      for (int _snowmanxxxxx = _snowmanxxxx; _snowmanxxxxx <= hatHeight; _snowmanxxxxx++) {
         int _snowmanxxxxxx = _snowmanxxxxx < hatHeight - random.nextInt(3) ? 2 : 1;
         if (_snowmanxxx > 8 && _snowmanxxxxx < _snowmanxxxx + 4) {
            _snowmanxxxxxx = 3;
         }

         if (thickStem) {
            _snowmanxxxxxx++;
         }

         for (int _snowmanxxxxxxx = -_snowmanxxxxxx; _snowmanxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = -_snowmanxxxxxx; _snowmanxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxx++) {
               boolean _snowmanxxxxxxxxx = _snowmanxxxxxxx == -_snowmanxxxxxx || _snowmanxxxxxxx == _snowmanxxxxxx;
               boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxxx == -_snowmanxxxxxx || _snowmanxxxxxxxx == _snowmanxxxxxx;
               boolean _snowmanxxxxxxxxxxx = !_snowmanxxxxxxxxx && !_snowmanxxxxxxxxxx && _snowmanxxxxx != hatHeight;
               boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx && _snowmanxxxxxxxxxx;
               boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxx < _snowmanxxxx + 3;
               _snowmanx.set(_snowman, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx);
               if (method_24866(world, _snowmanx, false)) {
                  if (config.planted && !world.getBlockState(_snowmanx.down()).isAir()) {
                     world.breakBlock(_snowmanx, true);
                  }

                  if (_snowmanxxxxxxxxxxxxx) {
                     if (!_snowmanxxxxxxxxxxx) {
                        this.tryGenerateVines(world, random, _snowmanx, config.hatState, _snowmanxx);
                     }
                  } else if (_snowmanxxxxxxxxxxx) {
                     this.generateHatBlock(world, random, config, _snowmanx, 0.1F, 0.2F, _snowmanxx ? 0.1F : 0.0F);
                  } else if (_snowmanxxxxxxxxxxxx) {
                     this.generateHatBlock(world, random, config, _snowmanx, 0.01F, 0.7F, _snowmanxx ? 0.083F : 0.0F);
                  } else {
                     this.generateHatBlock(world, random, config, _snowmanx, 5.0E-4F, 0.98F, _snowmanxx ? 0.07F : 0.0F);
                  }
               }
            }
         }
      }
   }

   private void generateHatBlock(
      WorldAccess world, Random random, HugeFungusFeatureConfig config, BlockPos.Mutable pos, float decorationChance, float generationChance, float vineChance
   ) {
      if (random.nextFloat() < decorationChance) {
         this.setBlockState(world, pos, config.decorationState);
      } else if (random.nextFloat() < generationChance) {
         this.setBlockState(world, pos, config.hatState);
         if (random.nextFloat() < vineChance) {
            generateVines(pos, world, random);
         }
      }
   }

   private void tryGenerateVines(WorldAccess world, Random random, BlockPos pos, BlockState state, boolean _snowman) {
      if (world.getBlockState(pos.down()).isOf(state.getBlock())) {
         this.setBlockState(world, pos, state);
      } else if ((double)random.nextFloat() < 0.15) {
         this.setBlockState(world, pos, state);
         if (_snowman && random.nextInt(11) == 0) {
            generateVines(pos, world, random);
         }
      }
   }

   private static void generateVines(BlockPos pos, WorldAccess world, Random random) {
      BlockPos.Mutable _snowman = pos.mutableCopy().move(Direction.DOWN);
      if (world.isAir(_snowman)) {
         int _snowmanx = MathHelper.nextInt(random, 1, 5);
         if (random.nextInt(7) == 0) {
            _snowmanx *= 2;
         }

         int _snowmanxx = 23;
         int _snowmanxxx = 25;
         WeepingVinesFeature.generateVineColumn(world, random, _snowman, _snowmanx, 23, 25);
      }
   }
}
