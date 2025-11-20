package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BlueIceFeature extends Feature<DefaultFeatureConfig> {
   public BlueIceFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      if (_snowman.getY() > _snowman.getSeaLevel() - 1) {
         return false;
      } else if (!_snowman.getBlockState(_snowman).isOf(Blocks.WATER) && !_snowman.getBlockState(_snowman.down()).isOf(Blocks.WATER)) {
         return false;
      } else {
         boolean _snowmanxxxxx = false;

         for (Direction _snowmanxxxxxx : Direction.values()) {
            if (_snowmanxxxxxx != Direction.DOWN && _snowman.getBlockState(_snowman.offset(_snowmanxxxxxx)).isOf(Blocks.PACKED_ICE)) {
               _snowmanxxxxx = true;
               break;
            }
         }

         if (!_snowmanxxxxx) {
            return false;
         } else {
            _snowman.setBlockState(_snowman, Blocks.BLUE_ICE.getDefaultState(), 2);

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 200; _snowmanxxxxxxx++) {
               int _snowmanxxxxxxxx = _snowman.nextInt(5) - _snowman.nextInt(6);
               int _snowmanxxxxxxxxx = 3;
               if (_snowmanxxxxxxxx < 2) {
                  _snowmanxxxxxxxxx += _snowmanxxxxxxxx / 2;
               }

               if (_snowmanxxxxxxxxx >= 1) {
                  BlockPos _snowmanxxxxxxxxxx = _snowman.add(_snowman.nextInt(_snowmanxxxxxxxxx) - _snowman.nextInt(_snowmanxxxxxxxxx), _snowmanxxxxxxxx, _snowman.nextInt(_snowmanxxxxxxxxx) - _snowman.nextInt(_snowmanxxxxxxxxx));
                  BlockState _snowmanxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxx.getMaterial() == Material.AIR
                     || _snowmanxxxxxxxxxxx.isOf(Blocks.WATER)
                     || _snowmanxxxxxxxxxxx.isOf(Blocks.PACKED_ICE)
                     || _snowmanxxxxxxxxxxx.isOf(Blocks.ICE)) {
                     for (Direction _snowmanxxxxxxxxxxxx : Direction.values()) {
                        BlockState _snowmanxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxx.offset(_snowmanxxxxxxxxxxxx));
                        if (_snowmanxxxxxxxxxxxxx.isOf(Blocks.BLUE_ICE)) {
                           _snowman.setBlockState(_snowmanxxxxxxxxxx, Blocks.BLUE_ICE.getDefaultState(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
