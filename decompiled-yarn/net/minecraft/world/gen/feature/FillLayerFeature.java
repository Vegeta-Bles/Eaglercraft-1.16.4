package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class FillLayerFeature extends Feature<FillLayerFeatureConfig> {
   public FillLayerFeature(Codec<FillLayerFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, FillLayerFeatureConfig _snowman) {
      BlockPos.Mutable _snowmanxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
            int _snowmanxxxxxxxx = _snowman.getX() + _snowmanxxxxxx;
            int _snowmanxxxxxxxxx = _snowman.getZ() + _snowmanxxxxxxx;
            int _snowmanxxxxxxxxxx = _snowman.height;
            _snowmanxxxxx.set(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx);
            if (_snowman.getBlockState(_snowmanxxxxx).isAir()) {
               _snowman.setBlockState(_snowmanxxxxx, _snowman.state, 2);
            }
         }
      }

      return true;
   }
}
