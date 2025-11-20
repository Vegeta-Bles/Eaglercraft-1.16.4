package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class DiskFeature extends Feature<DiskFeatureConfig> {
   public DiskFeature(Codec<DiskFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DiskFeatureConfig _snowman) {
      boolean _snowmanxxxxx = false;
      int _snowmanxxxxxx = _snowman.radius.getValue(_snowman);

      for (int _snowmanxxxxxxx = _snowman.getX() - _snowmanxxxxxx; _snowmanxxxxxxx <= _snowman.getX() + _snowmanxxxxxx; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowman.getZ() - _snowmanxxxxxx; _snowmanxxxxxxxx <= _snowman.getZ() + _snowmanxxxxxx; _snowmanxxxxxxxx++) {
            int _snowmanxxxxxxxxx = _snowmanxxxxxxx - _snowman.getX();
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx - _snowman.getZ();
            if (_snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx <= _snowmanxxxxxx * _snowmanxxxxxx) {
               for (int _snowmanxxxxxxxxxxx = _snowman.getY() - _snowman.halfHeight; _snowmanxxxxxxxxxxx <= _snowman.getY() + _snowman.halfHeight; _snowmanxxxxxxxxxxx++) {
                  BlockPos _snowmanxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxx);
                  Block _snowmanxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxx).getBlock();

                  for (BlockState _snowmanxxxxxxxxxxxxxx : _snowman.targets) {
                     if (_snowmanxxxxxxxxxxxxxx.isOf(_snowmanxxxxxxxxxxxxx)) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxx, _snowman.state, 2);
                        _snowmanxxxxx = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      return _snowmanxxxxx;
   }
}
