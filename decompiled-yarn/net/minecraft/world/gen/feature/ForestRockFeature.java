package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ForestRockFeature extends Feature<SingleStateFeatureConfig> {
   public ForestRockFeature(Codec<SingleStateFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, SingleStateFeatureConfig _snowman) {
      while (_snowman.getY() > 3) {
         if (!_snowman.isAir(_snowman.down())) {
            Block _snowmanxxxxx = _snowman.getBlockState(_snowman.down()).getBlock();
            if (isSoil(_snowmanxxxxx) || isStone(_snowmanxxxxx)) {
               break;
            }
         }

         _snowman = _snowman.down();
      }

      if (_snowman.getY() <= 3) {
         return false;
      } else {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < 3; _snowmanxxxxx++) {
            int _snowmanxxxxxx = _snowman.nextInt(2);
            int _snowmanxxxxxxx = _snowman.nextInt(2);
            int _snowmanxxxxxxxx = _snowman.nextInt(2);
            float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxx + _snowmanxxxxxxx + _snowmanxxxxxxxx) * 0.333F + 0.5F;

            for (BlockPos _snowmanxxxxxxxxxx : BlockPos.iterate(_snowman.add(-_snowmanxxxxxx, -_snowmanxxxxxxx, -_snowmanxxxxxxxx), _snowman.add(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx))) {
               if (_snowmanxxxxxxxxxx.getSquaredDistance(_snowman) <= (double)(_snowmanxxxxxxxxx * _snowmanxxxxxxxxx)) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxx, _snowman.state, 4);
               }
            }

            _snowman = _snowman.add(-1 + _snowman.nextInt(2), -_snowman.nextInt(2), -1 + _snowman.nextInt(2));
         }

         return true;
      }
   }
}
