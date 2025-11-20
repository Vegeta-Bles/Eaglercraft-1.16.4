package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class IcePatchFeature extends DiskFeature {
   public IcePatchFeature(Codec<DiskFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DiskFeatureConfig _snowman) {
      while (_snowman.isAir(_snowman) && _snowman.getY() > 2) {
         _snowman = _snowman.down();
      }

      return !_snowman.getBlockState(_snowman).isOf(Blocks.SNOW_BLOCK) ? false : super.generate(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
