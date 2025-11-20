package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EmeraldOreFeature extends Feature<EmeraldOreFeatureConfig> {
   public EmeraldOreFeature(Codec<EmeraldOreFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, EmeraldOreFeatureConfig _snowman) {
      if (_snowman.getBlockState(_snowman).isOf(_snowman.target.getBlock())) {
         _snowman.setBlockState(_snowman, _snowman.state, 2);
      }

      return true;
   }
}
