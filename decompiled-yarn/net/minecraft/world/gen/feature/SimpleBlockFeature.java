package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SimpleBlockFeature extends Feature<SimpleBlockFeatureConfig> {
   public SimpleBlockFeature(Codec<SimpleBlockFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, SimpleBlockFeatureConfig _snowman) {
      if (_snowman.placeOn.contains(_snowman.getBlockState(_snowman.down())) && _snowman.placeIn.contains(_snowman.getBlockState(_snowman)) && _snowman.placeUnder.contains(_snowman.getBlockState(_snowman.up()))) {
         _snowman.setBlockState(_snowman, _snowman.toPlace, 2);
         return true;
      } else {
         return false;
      }
   }
}
