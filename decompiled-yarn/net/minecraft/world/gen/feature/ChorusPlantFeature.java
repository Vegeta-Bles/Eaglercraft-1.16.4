package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ChorusPlantFeature extends Feature<DefaultFeatureConfig> {
   public ChorusPlantFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      if (_snowman.isAir(_snowman) && _snowman.getBlockState(_snowman.down()).isOf(Blocks.END_STONE)) {
         ChorusFlowerBlock.generate(_snowman, _snowman, _snowman, 8);
         return true;
      } else {
         return false;
      }
   }
}
