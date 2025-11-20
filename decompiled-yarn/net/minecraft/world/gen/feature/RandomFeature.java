package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class RandomFeature extends Feature<RandomFeatureConfig> {
   public RandomFeature(Codec<RandomFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, RandomFeatureConfig _snowman) {
      for (RandomFeatureEntry _snowmanxxxxx : _snowman.features) {
         if (_snowman.nextFloat() < _snowmanxxxxx.chance) {
            return _snowmanxxxxx.generate(_snowman, _snowman, _snowman, _snowman);
         }
      }

      return _snowman.defaultFeature.get().generate(_snowman, _snowman, _snowman, _snowman);
   }
}
